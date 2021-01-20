package com.mintel.utils.texUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;


public class CommonUtil {

    /**
     * tex图片格式
     */
    private static final String PICTUREMODULE="\\\\quad\\\\includegraphics{pictureName_pictureName}";


    /**
     * 生成.tex文件
     * @param paper 试卷的字符串
     * @param path 生成后放置的路径
     * @return 文件名
     */
    public static String writePaperTex(String paper,String path){

        String paperName= UUID.randomUUID().toString().replaceAll("-", "");
        String paperPath=path+"/"+paperName+".tex";
        File f=new File(paperPath);
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(paperPath));
            //一次写一行
            bw.write(paper);
            bw.newLine(); //换行用
            System.out.println("写入成功");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return paperName+".tex";
    }


    /**
     * 将内容里的img标签转换成tex文件的图片标签
     * @param content 内容
     * @return 转换后的字符串
     */
    public static String picturePathConverter(String content){
    	
        String result=content;
        boolean contentPicture=Pattern.matches(".*<img.*?src=\\s*.*>.*",content);

        if(!contentPicture){
            return content;
        }else {
            List<String> pictureNameList=new ArrayList<>();
            Pattern pattern = Pattern.compile("src=\\s*(\"|\'|’|‘).*?(\"|\'|’|‘)");
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                pictureNameList.add(matcher.group());
            }

            String m=PICTUREMODULE;
            result=result.replaceAll("<img(.*?)src=\\s*(.*?)>",m);

            String tomcatWebappsPath=System.getProperty("catalina.home").replaceAll("\\\\", "/")+"/webapps";
            
            for(int i=0;i<pictureNameList.size();i++){
                result=result.replaceFirst("pictureName_pictureName",tomcatWebappsPath+pictureNameList.get(i).replaceAll("src=\\s*","").replaceAll("\"|\'|’|‘","").replace("../../","/MintelRev/mintmath-middle-tongbu/"));
            }
            return result;
        }
    }


    /**
     * 单元测试图片被放在了两个地方。 人教在/MintelRev下，北师华师在/MintelRev/mintmath-middle-tongbu下
     * @param content
     * @param bookeVsersion
     * @return
     */
	public static String picturePathConverter(String content,String bookeVsersion){
	
        String result=content;
        boolean contentPicture=Pattern.matches(".*<img.*?src=\\s*.*>.*",content);

        if(!contentPicture){
            return content;
        }else {
            List<String> pictureNameList=new ArrayList<>();
            Pattern pattern = Pattern.compile("src=\\s*(\"|\'|’|‘).*?(\"|\'|’|‘)");
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                pictureNameList.add(matcher.group());
            }

            String m=PICTUREMODULE;
            result=result.replaceAll("<img(.*?)src=\\s*(.*?)>",m);

            String tomcatWebappsPath=System.getProperty("catalina.home").replaceAll("\\\\", "/")+"/webapps";
            //String tomcatWebappsPath="E:\\\\apache-tomcat-7.0.73-windows-x64(2)\\\\apache-tomcat-7.0.73\\\\webapps";
            
            if(bookeVsersion.equals("人教")){
            	for(int i=0;i<pictureNameList.size();i++){
	                result=result.replaceFirst("pictureName_pictureName",tomcatWebappsPath+pictureNameList.get(i).replaceAll("src=\\s*","").replaceAll("\"|\'|’|‘","").replace("../../","/MintelRev/"));
	            }
            }else {
            	for(int i=0;i<pictureNameList.size();i++){
	                result=result.replaceFirst("pictureName_pictureName",tomcatWebappsPath+pictureNameList.get(i).replaceAll("src=\\s*","").replaceAll("\"|\'|’|‘","").replace("../../","/MintelRev/mintmath-middle-tongbu/"));
	            }
			}
            
            return result;
        }
	}
    
    /**
     * 获得内容的byte长度，主要为了选择题选项的排版
     * UTF-8 一个中文字符是3个byte，一个英文字符是1个byte
     * 这里定义一段letex代码长15byte（5个中文字），一张图片为24byte（8个中文字）
     * @param content 内容
     * @return 内容的byte长度
     */
    public static int contentLength(String content){

        int length=0;
        //去掉填答案的()
        String temp=content.replaceAll("[(]\\s*[)]","");

        if(temp.matches(".*\\\\[(].*\\\\[)].*")){
            int count=0;
            Pattern pattern = Pattern.compile("\\\\[(].*?\\\\[)]");
            Matcher matcher = pattern.matcher(temp);
            while (matcher.find()) {
                count++;
            }
            length=length+15*count;
            temp=temp.replaceAll("\\\\[(].*?\\\\[)]","");
        }

        if(temp.matches(".*<img.*?src=.*>.*")){
            int count=0;
            Pattern pattern = Pattern.compile("<img(.*?)src=\\s*(.*?)>");
            Matcher matcher = pattern.matcher(temp);
            while (matcher.find()) {
                count++;
            }
            length=length+24*count;
            temp=temp.replaceAll("<img(.*?)src=\\s*(.*?)>","");
        }

        length=length+temp.getBytes().length;

        return length;
    }
    
    /**
     * 
     * @param contents
     * @return
     */
    public String labelReplace(String content) {
    	
    	String result=content.trim().
				replaceAll("&nbsp;", "").
				replaceAll("<br/>", "").replaceAll("<br>", "").
				replaceAll("<u>", "").replaceAll("</u>", "").
				replaceAll("&lt;", "<").replaceAll("&gt;", ">").
				replaceAll("&ne;", "≠").replaceAll("&#8800;", "≠").
				replaceAll("&le;", "≤").replaceAll("&#8804;", "≤").
				replaceAll("&ge;", "≥").replaceAll("&#8805;", "≥").
				replaceAll("&amp;", "&").replaceAll("&quot;", "\"").
				replaceAll("&times;", "×").replaceAll("&divide;", "÷").
				replaceAll("&#247;", "÷").replaceAll("&#8220;", "“").replaceAll("&#8221;", "”").
				replaceAll("&#8756;", "∴").replaceAll("&there4;", "∴").
				replaceAll("&#8756;", "∵").replaceAll("&#8869;", "⊥").
				replaceAll("&#8780;", "≌").replaceAll("&#8765;", "∽").
				replaceAll("&isin;", "∈").replaceAll("&#8712;", "∈").
				replaceAll("&notin;", "∉").replaceAll("&#8713;", "∉").
				replaceAll("&ang;", "∠").replaceAll("&#8736;", "∠").
				replaceAll("&and;", "∧").replaceAll("&#8869;", "∧").
				replaceAll("&or;", "∨").replaceAll("&#8870;", "∨").
				replaceAll("&cap;", "∩").replaceAll("&#8745;", "∩").
				replaceAll("&cup;", "∪").replaceAll("&#8746;", "∪").
				replaceAll("&sup;", "⊃").replaceAll("&#8835;", "⊃").
				replaceAll("&sub;", "⊂").replaceAll("&#8834;", "⊂").
				replaceAll("&sube;", "⊆").replaceAll("&#8838;", "⊆").
				replaceAll("&supe;", "⊇").replaceAll("&#8839;", "⊇").
				replaceAll("&nsub;", "⊄").replaceAll("&#8836;", "⊄").
				replaceAll("&deg;", "°").replaceAll("&#176;", "°");
    	
    	result=result.replaceAll("%", "\\\\%").replaceAll("\\\\\\\\%", "\\\\%");
    	
    	
		return result;
	}

    public static void wordToPdf(String docx_address, String pdf_address) {
    	
		if (getLicense()) {
			try {
	            long old = System.currentTimeMillis();
	            //新建一个pdf文档
	            File file = new File(pdf_address);
	            FileOutputStream os = new FileOutputStream(file);
	            //docx_address是将要被转化的word文档
	            Document doc = new Document(docx_address);
	            //全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
	            doc.save(os, SaveFormat.PDF);
	            long now = System.currentTimeMillis();
	            os.close();
	            //转化用时
	            System.out.println("Word 转 Pdf 共耗时：" + ((now - old) / 1000.0) + "秒");
	        } catch (Exception e) {
	            System.out.println("Word 转 Pdf 失败...");
	            e.printStackTrace();
	        }
		}
	}
    
    public static boolean getLicense() {
		boolean result = false;
		InputStream is = CommonUtil.class.getClassLoader().getResourceAsStream("license.xml");

		License aposeLic = new License();
		try {
			aposeLic.setLicense(is);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
