package com.mintel.tongbu.middlemath.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mintel.tongbu.middlemath.bean.UserBean;
import com.mintel.tongbu.middlemath.common.QueryQuestionsInfo;
import com.mintel.utils.Doc2PdfUtil;
import com.mintel.utils.StringUtils;
import com.mintel.utils.ValidatorUtils;
import com.mintel.utils.texUtil.ChoiceQuestionEntity;
import com.mintel.utils.texUtil.CommonUtil;
import com.mintel.utils.texUtil.MakeTexToWordUtil;
import com.mintel.utils.texUtil.WordUtil;

public class NewQuestionDownloadServlet extends HttpServlet{

	
	private static final Logger log = LoggerFactory.getLogger(NewQuestionDownloadServlet.class);
	private static final long serialVersionUID = 1L;
	
	/**
	 * 基础练习，课中讨论题目生成word
	 * 
	 * pandoc-2.9.2.1-linux-amd64.tar.gz
	 * 对于pandoc程序的使用：生产环境pandoc配置的环境变量没有起效(但还是要去配， 参考 https://www.bilibili.com/read/cv2758639/)，
	 * 1. vi ~/.bashrc
	 * 2. 添加alias pandoc="/tu/pandoc/bin/pandoc"   
	 * 3. source ~/.bashrc      #更新
	 * 4. pandoc -v           # 检查是否成功安装
	 * 
	 * 所以执行的command命令需要根据pandoc目录来配置。
	 * 现在的生产环境pandoc目录在 /tu/pandoc/bin/pandoc
	 * 
	 * 开发环境为windows，直接用pandoc就行
	 * 
	 * @param request
	 */
	public void downloadPaper(HttpServletRequest request,HttpServletResponse response) {
		
		String unitid=request.getParameter("unitid");
		unitid = ValidatorUtils.getUnitid(unitid);
		String noduleid=request.getParameter("noduleid");
		noduleid = ValidatorUtils.getNoduleid(noduleid);
		
		String needAnswer="false";
		if(request.getParameter("needAnswer")!=null){
			needAnswer=request.getParameter("needAnswer");
		}
		String moduleName=request.getParameter("moduleName");
		String title=request.getParameter("title");
		
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String day=formatter.format(date);
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String times=formatter2.format(date);
		
		UserBean userBean = (UserBean) request.getSession().getAttribute("UserBean");
        String userId = userBean.getUser_id();
		
        MakeTexToWordUtil util=new MakeTexToWordUtil();
		QueryQuestionsInfo queryInfo=new QueryQuestionsInfo(request);
		List<ChoiceQuestionEntity> choiceEntitys=new ArrayList<ChoiceQuestionEntity>();
		if("basicExercises".equals(moduleName)){
			List<Integer> ids=queryInfo.queryBasicExercisesChoiceQuestionIdsByUnitid(Integer.parseInt(unitid));
			for(int id:ids){
				choiceEntitys.add(queryInfo.getChoiceQestionById(id));
			}
		}else if("discussionInClass".equals(moduleName)){
			List<Integer> ids=queryInfo.queryDiscussionInClassChoiceQuestionIdsByUnitid(userId, Integer.parseInt(noduleid));
			for(int id:ids){
				choiceEntitys.add(queryInfo.getChoiceQestionById(id));
			}
		}
		
		for(int i=0;i<choiceEntitys.size();i++){
			choiceEntitys.get(i).setNo(i+1);
		}
		
		String path=util.makeWordPaperOnlyChoiceQuestion(choiceEntitys,title,day,Boolean.parseBoolean(needAnswer));
        String command="/tu/pandoc/bin/pandoc "+path+" -o "+path.replace(".tex",".docx");
        log.info("time: "+times+"    user:"+userId+" are creating word file.  "+path);
        
        try {
        	log.info("tex->docx command:"+command);
        	Process process=Runtime.getRuntime().exec(command);
        	printMessage(process.getInputStream());
            printMessage(process.getErrorStream());
            int value = process.waitFor();
            log.info(path+"   process value:" +value);//value=0正常
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
			e.printStackTrace();
		}
        HashMap<String,String> map = new HashMap<String, String>();
        map.put("　　","    ");//第一个""里面是特殊字符
        path = path.replace(".tex",".docx");
        
        WordUtil.replaceAll(path, map);
        CommonUtil.wordToPdf(path,path.replaceAll(".docx", ".pdf"));
        
        try {
        	if("basicExercises".equals(moduleName)){
        		download(path.replaceAll(".docx", ".pdf"),request,response,title+"-基础练习.pdf");
        	}else if("discussionInClass".equals(moduleName)){
        		download(path.replaceAll(".docx", ".pdf"),request,response,title+"-课中讨论.pdf");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        
	}
	
	
	public void downloadPaperTask(HttpServletRequest request,HttpServletResponse response) {
		
		String taskId=request.getParameter("taskid");
		String title=request.getParameter("title");
		
		UserBean userBean = (UserBean) request.getSession().getAttribute("UserBean");
        String userId = userBean.getUser_id();
		
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String day=formatter.format(date);
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String times=formatter2.format(date);
		
		String needAnswer="false";
		if(request.getParameter("needAnswer")!=null){
			needAnswer=request.getParameter("needAnswer");
		}
		
		MakeTexToWordUtil util=new MakeTexToWordUtil();
		QueryQuestionsInfo queryInfo=new QueryQuestionsInfo(request);
		
		List<ChoiceQuestionEntity> choiceEntitys=new ArrayList<ChoiceQuestionEntity>();
		
		List<Integer> ids=queryInfo.queryTaskChoiceQuestionIdsByTaskId(Integer.parseInt(taskId));
		
		for(int id:ids){
			choiceEntitys.add(queryInfo.getChoiceQestionById(id));
		}
		
		for(int i=0;i<choiceEntitys.size();i++){
			choiceEntitys.get(i).setNo(i+1);
		}
		
		String path=util.makeWordPaperOnlyChoiceQuestion(choiceEntitys,title,day,Boolean.parseBoolean(needAnswer));
        String command="/tu/pandoc/bin/pandoc "+path+" -o "+path.replace(".tex",".docx");
        log.info("time: "+times+"    user:"+userId+" are creating word file.  "+path);
        
        try {
        	log.info("tex->docx  command:"+command);
        	Process process=Runtime.getRuntime().exec(command);
        	printMessage(process.getInputStream());
            printMessage(process.getErrorStream());
            int value = process.waitFor();
            log.info(path+"   process value:" +value);//value=0正常
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        HashMap<String,String> map = new HashMap<String, String>();
        map.put("　　","    ");//第一个""里面是特殊字符
        path = path.replace(".tex",".docx");
        WordUtil.replaceAll(path, map);
        CommonUtil.wordToPdf(path,path.replaceAll(".docx", ".pdf"));
        
        try {
			download(path.replaceAll(".docx", ".pdf"),request,response,title+"-课后作业.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void downloadPaperUnitTest(HttpServletRequest request,HttpServletResponse response) {
		String title=request.getParameter("title");
		String chapterid=request.getParameter("chapterid");
		
		String needAnswer="false";
		if(request.getParameter("needAnswer")!=null){
			needAnswer=request.getParameter("needAnswer");
		}
		
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String day=formatter.format(date);
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String times=formatter2.format(date);
		
		UserBean userBean = (UserBean) request.getSession().getAttribute("UserBean");
        String userId = userBean.getUser_id();
        
		MakeTexToWordUtil util=new MakeTexToWordUtil();
		QueryQuestionsInfo queryInfo=new QueryQuestionsInfo(request);
		
		List<ChoiceQuestionEntity> choiceEntitys=new ArrayList<ChoiceQuestionEntity>();
		
		List<Integer> ids=queryInfo.queryUnitTestChoiceQuestionIdsByChapterId(Integer.parseInt(chapterid));
		
		for(int id:ids){
			choiceEntitys.add(queryInfo.getUnitTestChoiceQestionById(id));
		}
		for(int i=0;i<choiceEntitys.size();i++){
			choiceEntitys.get(i).setNo(i+1);
		}
		
		String bookVersion=queryInfo.getBookVersionByChapterId(Integer.parseInt(chapterid));
		
		String path=util.makeWordPaperOnlyChoiceQuestion(choiceEntitys,title,day,Boolean.parseBoolean(needAnswer),bookVersion);
        String command="/tu/pandoc/bin/pandoc  "+path+" -o "+path.replace(".tex",".docx");
        log.info("time: "+times+"    user:"+userId+" are creating word file.  "+path);
        
        try {
        	log.info("tex->docx  command:"+command);
        	Process process=Runtime.getRuntime().exec(command);
        	printMessage(process.getInputStream());
            printMessage(process.getErrorStream());
            int value = process.waitFor();
            log.info(path+"   process value:" +value);//value=0正常
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        HashMap<String,String> map = new HashMap<String, String>();
        map.put("　　","    ");//第一个""里面是特殊字符
        path = path.replace(".tex",".docx");
        WordUtil.replaceAll(path, map);
        CommonUtil.wordToPdf(path,path.replaceAll(".docx", ".pdf"));
        
        
        try {
			download(path.replaceAll(".docx", ".pdf"),request,response,title+"-单元测试.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void printMessage(final InputStream input) {
        new Thread(new Runnable() {
            public void run() {
                Reader reader = new InputStreamReader(input);
                BufferedReader bf = new BufferedReader(reader);
                String line = null;
                try {
                    while((line=bf.readLine())!=null) {
                        log.info("======>"+line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
             }
        }).start();
    }
	
	
	public void download(String filePath, HttpServletRequest request,HttpServletResponse response, String showFileName) throws Exception {
		request.setCharacterEncoding("utf-8");
        //获取文件路径  
        filePath = filePath == null ? "" : filePath;  
        //设置向浏览器端传送的文件格式  
        response.setContentType("application/x-download");
        if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
        	showFileName = new String(showFileName.getBytes("UTF-8"), "ISO8859-1");
        } else {
        	if(!StringUtils.isNullOrEmpty(showFileName)&& showFileName.contains("%")){
        		showFileName = URLEncoder.encode(showFileName.split("%")[0], "UTF-8")+"%"+URLEncoder.encode(showFileName.split("%")[1], "UTF-8");
            }else{
            	showFileName = URLEncoder.encode(showFileName, "UTF-8");
            }
        }
        response.setHeader("Content-Disposition", "attachment;filename="+showFileName);
        FileInputStream fis = null;  
        OutputStream os = null;  
        try {  
            os = response.getOutputStream();  
            fis = new FileInputStream(filePath);  
            byte[] b = new byte[1024 * 10];  
            int i = 0;  
            while ((i = fis.read(b)) > 0) {  
                os.write(b, 0, i);  
            }  
            os.flush();  
            os.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (fis != null) {  
                try {  
                    fis.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
            if (os != null) {  
                try {  
                    os.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    } 
	

}
