package com.mintel.utils.texUtil;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2020/6/12 0012.
 */
public class MakeTexToWordUtil {

    /**
     * 用于生成word的内容需要手动拼接。
     * 模板
     */
    private final String WORD_TEMPLATE="\\documentclass [UTF8]{ctexart}\n" +
                                        "\\usepackage{graphicx}\n" +
                                        "\\pagestyle{plain}\n" +
                                        "\\linespread{1.5}\n" +
                                        "\\setlength{\\parindent}{0pt}\n" +
                                        "\\begin {document}\n"+
                                        "\\title{title_title}\n" +
                                        "\\date{date_date}\n" +
                                        "\\maketitle\n"+
                                        "result_result \n"+
                                        "\\end{document}\n";

    private final String PICTUREMODULE="\\\\quad\\\\includegraphics{pictureName_pictureName}";

    private final String TITLEMODULE="\\begin{center}\\Large{title_title}\\end{center}\n";


    /**
     * 全是选择题的方法
     * @param choiceQuestions 选择题list
     * @param title 试卷标题（最好给出）
     * @param date 日期
     * @param needRemark 是否需要答案，解析?
     * @return 生成的tex文件名
     */
    public String makeWordPaperOnlyChoiceQuestion(List<ChoiceQuestionEntity> choiceQuestions,String title,String date,boolean needRemark) {
        StringBuffer sb=new StringBuffer();
        String paper=WORD_TEMPLATE;
        if(title==null || title.equals("")){
            paper=paper.replace("title_title","");
        }else{
            paper=paper.replace("title_title",title);
        }
        if(date==null || date.equals("")){
            paper=paper.replace("date_date","");
        }else{
            paper=paper.replace("date_date",date);
        }
        for(int i=0;i<choiceQuestions.size();i++){
            sb.append(makeChoiceQuesionParagraph(choiceQuestions.get(i),i+1,needRemark));
        }
        paper=paper.replace("result_result",sb.toString());
        String targetPath=System.getProperty("catalina.home")+File.separator+"convertFile";
        String path=CommonUtil.writePaperTex(paper,targetPath);
        System.out.println("path ====================>"+path);
        return targetPath+File.separator+path;
    }
    
    
    public String makeWordPaperOnlyChoiceQuestion(List<ChoiceQuestionEntity> choiceQuestions,String title,String date,boolean needRemark,String bookVersion) {
        StringBuffer sb=new StringBuffer();
        String paper=WORD_TEMPLATE;
        if(title==null || title.equals("")){
            paper=paper.replace("title_title","");
        }else{
            paper=paper.replace("title_title",title);
        }
        if(date==null || date.equals("")){
            paper=paper.replace("date_date","");
        }else{
            paper=paper.replace("date_date",date);
        }
        for(int i=0;i<choiceQuestions.size();i++){
            sb.append(makeChoiceQuesionParagraph(choiceQuestions.get(i),i+1,needRemark,bookVersion));
        }
        paper=paper.replace("result_result",sb.toString());
        String targetPath=System.getProperty("catalina.home")+File.separator+"convertFile";
        String path=CommonUtil.writePaperTex(paper,targetPath);
        return targetPath+File.separator+path;
    }
    
    public String makeWordPaper(List<ChoiceQuestionEntity> choiceQuestions,List<SubjectiveQuestionEntity> subjectiveQuestions,String title,String date,boolean needRemark){
        StringBuffer sb=new StringBuffer();
        String paper=WORD_TEMPLATE;
        if(title==null || title.equals("")){
            paper=paper.replace("title_title","");
        }else{
            paper=paper.replace("title_title",title);
        }
        if(date==null || date.equals("")){
            paper=paper.replace("date_date","");
        }else{
            paper=paper.replace("date_date",date);
        }
        int i=0;
        if(choiceQuestions!=null && choiceQuestions.size()!=0){
            for(;i<choiceQuestions.size();i++){
                sb.append(makeChoiceQuesionParagraph(choiceQuestions.get(i),i+1,needRemark));
            }
        }
        if(subjectiveQuestions!=null && subjectiveQuestions.size()!=0){
            for(int j=0;j<subjectiveQuestions.size();j++){
                sb.append(makeSubjectiveQuestionParagraph(subjectiveQuestions.get(j),i+1,needRemark));
                i++;
            }
        }
        paper=paper.replace("result_result",sb.toString());
        String path=CommonUtil.writePaperTex(paper,"../MintelRev/WebRoot/mintmath-middle-tongbu/tex");
        return path;
    }
    

    /**
     *
     * @param entity 选择题实体
     * @param no 题号
     * @param needRemark 是否需要答案，解析？
     * @return 一道选择题段落
     */
    public String makeChoiceQuesionParagraph(ChoiceQuestionEntity entity,int no,boolean needRemark){
        StringBuffer paragraph=new StringBuffer();
        if(no==1){
        	paragraph.append("\\\\\n");
        }
        paragraph.append(entity.getNo()+". ").append(CommonUtil.picturePathConverter(entity.getStem())+"\\\\\n");

        /**
         * 生成的word一行能显示33个中文字，99byte。
         */
        int choiceCount=entity.getChoices().length;
        int choiceALength=0;
        int choiceBLength=0;
        int choiceCLength=0;
        int choiceDLength=0;
        for(int i=0;i<choiceCount;i++){
            if(i==0){
                choiceALength=CommonUtil.contentLength(entity.getChoices()[i]);
            }else if(i==1){
                choiceBLength=CommonUtil.contentLength(entity.getChoices()[i]);
            }else if(i==2){
                choiceCLength=CommonUtil.contentLength(entity.getChoices()[i]);
            }else if(i==3){
                choiceDLength=CommonUtil.contentLength(entity.getChoices()[i]);
            }
        }
        int choiceLengthCount=0;
        for(String s:entity.getChoices()){
            choiceLengthCount+=CommonUtil.contentLength(s);
        }
        double averageLength=(double)choiceLengthCount/(double)choiceCount;

        if(choiceCount==2 && averageLength<=39){//两个选项在一行
            for (int i=0;i<entity.getChoices().length;i++){
                if(i==0){
                    paragraph.append("A. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 ");
                }else if(i==1){
                    paragraph.append("B. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 ");
                }
            }
        }else if(choiceCount==2 && averageLength>39){//两个选项分两行
            for (int i=0;i<entity.getChoices().length;i++){
                if(i==0){
                    paragraph.append("A. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 \\\\\n");
                }else if(i==1){
                    paragraph.append("B. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 ");
                }
            }
        }else if(choiceCount==3 && averageLength<=22){//三个选项在一行
            for (int i=0;i<entity.getChoices().length;i++){
                if(i==0){
                    paragraph.append("A. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 ");
                }else if(i==1){
                    paragraph.append("B. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 ");
                }else if(i==2){
                    paragraph.append("C. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 ");
                }
            }
        }else if(choiceCount==3 && averageLength>22 && averageLength<=34.5){//三个选项分两行
            for (int i=0;i<entity.getChoices().length;i++){
                if(i==0){
                    paragraph.append("A. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 ");
                }else if(i==1){
                    paragraph.append("B. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 \\\\\n");
                }else if(i==2){
                    paragraph.append("C. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 ");
                }
            }
        }else if(choiceCount==3 && averageLength>34.5){//三个选项分三行
            for (int i=0;i<entity.getChoices().length;i++){
                if(i==0){
                    paragraph.append("A. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 \\\\\n");
                }else if(i==1){
                    paragraph.append("B. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 \\\\\n");
                }else if(i==2){
                    paragraph.append("C. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 ");
                }
            }
        }else if(choiceCount==4 && averageLength<=14){//四个选项一行
            for (int i=0;i<entity.getChoices().length;i++){
                if(i==0){
                    paragraph.append("A. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 ");
                }else if(i==1){
                    paragraph.append("B. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 ");
                }else if(i==2){
                    paragraph.append("C. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 ");
                }else if(i==3){
                    paragraph.append("D. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 ");
                }
            }
        }else if(choiceCount==4 && averageLength>14 && averageLength<=39.5){//四个选项分两行
            for (int i=0;i<entity.getChoices().length;i++){
                if(i==0){
                    paragraph.append("A. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 ");
                }else if(i==1){
                    paragraph.append("B. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 \\\\\n");
                }else if(i==2){
                    paragraph.append("C. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 ");
                }else if(i==3){
                    paragraph.append("D. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 ");
                }
            }
        }else if(choiceCount==4 && averageLength>39.5){//四个选项分四行
            for (int i=0;i<entity.getChoices().length;i++){
                if(i==0){
                    paragraph.append("A. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 \\\\\n");
                }else if(i==1){
                    paragraph.append("B. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 \\\\\n");
                }else if(i==2){
                    paragraph.append("C. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 \\\\\n");
                }else if(i==3){
                    paragraph.append("D. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 ");
                }
            }
        }
        paragraph.append("\\\\\n");
        if(needRemark){
            if(entity.getAnswer()!=null || !entity.getAnswer().equals("")){
                paragraph.append("答案：").append(CommonUtil.picturePathConverter(entity.getAnswer())+"\\\\\n");
            }
            if(entity.getRemark()!=null ||entity.getRemark().equals("")){
            	String remark=CommonUtil.picturePathConverter(entity.getRemark()).replace("####","")+"\\\\\n";
            	if(remark.startsWith("解析：")){
            		remark=remark.replaceFirst("解析：","");
            	}else if (remark.startsWith("解析:")) {
            		remark=remark.replaceFirst("解析:","");
				}
                paragraph.append("解析：").append(remark);
            }
        }
        paragraph.append("\\\\\n");
        return paragraph.toString();
    }
    
    public String makeChoiceQuesionParagraph(ChoiceQuestionEntity entity,int no,boolean needRemark,String bookVersion){
        StringBuffer paragraph=new StringBuffer();
        if(no==1){
        	paragraph.append("\\\\\n");
        }
        paragraph.append(entity.getNo()+". ").append(CommonUtil.picturePathConverter(entity.getStem(),bookVersion)+"\\\\\n");

        /**
         * 生成的word一行能显示33个中文字，99byte。
         */
        int choiceCount=entity.getChoices().length;
        int choiceALength=0;
        int choiceBLength=0;
        int choiceCLength=0;
        int choiceDLength=0;
        for(int i=0;i<choiceCount;i++){
            if(i==0){
                choiceALength=CommonUtil.contentLength(entity.getChoices()[i]);
            }else if(i==1){
                choiceBLength=CommonUtil.contentLength(entity.getChoices()[i]);
            }else if(i==2){
                choiceCLength=CommonUtil.contentLength(entity.getChoices()[i]);
            }else if(i==3){
                choiceDLength=CommonUtil.contentLength(entity.getChoices()[i]);
            }
        }
        int choiceLengthCount=0;
        for(String s:entity.getChoices()){
            choiceLengthCount+=CommonUtil.contentLength(s);
        }
        double averageLength=(double)choiceLengthCount/(double)choiceCount;

        if(choiceCount==2 && averageLength<=39){//两个选项在一行
            for (int i=0;i<entity.getChoices().length;i++){
                if(i==0){
                    paragraph.append("A. "+CommonUtil.picturePathConverter(entity.getChoices()[i],bookVersion).trim()+"　　 ");
                }else if(i==1){
                    paragraph.append("B. "+CommonUtil.picturePathConverter(entity.getChoices()[i],bookVersion).trim()+"　　 ");
                }
            }
        }else if(choiceCount==2 && averageLength>39){//两个选项分两行
            for (int i=0;i<entity.getChoices().length;i++){
                if(i==0){
                    paragraph.append("A. "+CommonUtil.picturePathConverter(entity.getChoices()[i],bookVersion).trim()+"　　 \\\\\n");
                }else if(i==1){
                    paragraph.append("B. "+CommonUtil.picturePathConverter(entity.getChoices()[i],bookVersion).trim()+"　　 ");
                }
            }
        }else if(choiceCount==3 && averageLength<=22){//三个选项在一行
            for (int i=0;i<entity.getChoices().length;i++){
                if(i==0){
                    paragraph.append("A. "+CommonUtil.picturePathConverter(entity.getChoices()[i],bookVersion).trim()+"　　 ");
                }else if(i==1){
                    paragraph.append("B. "+CommonUtil.picturePathConverter(entity.getChoices()[i],bookVersion).trim()+"　　 ");
                }else if(i==2){
                    paragraph.append("C. "+CommonUtil.picturePathConverter(entity.getChoices()[i],bookVersion).trim()+"　　 ");
                }
            }
        }else if(choiceCount==3 && averageLength>22 && averageLength<=34.5){//三个选项分两行
            for (int i=0;i<entity.getChoices().length;i++){
                if(i==0){
                    paragraph.append("A. "+CommonUtil.picturePathConverter(entity.getChoices()[i],bookVersion).trim()+"　　 ");
                }else if(i==1){
                    paragraph.append("B. "+CommonUtil.picturePathConverter(entity.getChoices()[i],bookVersion).trim()+"　　 \\\\\n");
                }else if(i==2){
                    paragraph.append("C. "+CommonUtil.picturePathConverter(entity.getChoices()[i],bookVersion).trim()+"　　 ");
                }
            }
        }else if(choiceCount==3 && averageLength>34.5){//三个选项分三行
            for (int i=0;i<entity.getChoices().length;i++){
                if(i==0){
                    paragraph.append("A. "+CommonUtil.picturePathConverter(entity.getChoices()[i],bookVersion).trim()+"　　 \\\\\n");
                }else if(i==1){
                    paragraph.append("B. "+CommonUtil.picturePathConverter(entity.getChoices()[i],bookVersion).trim()+"　　 \\\\\n");
                }else if(i==2){
                    paragraph.append("C. "+CommonUtil.picturePathConverter(entity.getChoices()[i],bookVersion).trim()+"　　 ");
                }
            }
        }else if(choiceCount==4 && averageLength<=14){//四个选项一行
            for (int i=0;i<entity.getChoices().length;i++){
                if(i==0){
                    paragraph.append("A. "+CommonUtil.picturePathConverter(entity.getChoices()[i],bookVersion).trim()+"　　 ");
                }else if(i==1){
                    paragraph.append("B. "+CommonUtil.picturePathConverter(entity.getChoices()[i],bookVersion).trim()+"　　 ");
                }else if(i==2){
                    paragraph.append("C. "+CommonUtil.picturePathConverter(entity.getChoices()[i],bookVersion).trim()+"　　 ");
                }else if(i==3){
                    paragraph.append("D. "+CommonUtil.picturePathConverter(entity.getChoices()[i],bookVersion).trim()+"　　 ");
                }
            }
        }else if(choiceCount==4 && averageLength>14 && averageLength<=39.5){//四个选项分两行
            for (int i=0;i<entity.getChoices().length;i++){
                if(i==0){
                    paragraph.append("A. "+CommonUtil.picturePathConverter(entity.getChoices()[i],bookVersion).trim()+"　　 ");
                }else if(i==1){
                    paragraph.append("B. "+CommonUtil.picturePathConverter(entity.getChoices()[i],bookVersion).trim()+"　　 \\\\\n");
                }else if(i==2){
                    paragraph.append("C. "+CommonUtil.picturePathConverter(entity.getChoices()[i],bookVersion).trim()+"　　 ");
                }else if(i==3){
                    paragraph.append("D. "+CommonUtil.picturePathConverter(entity.getChoices()[i],bookVersion).trim()+"　　 ");
                }
            }
        }else if(choiceCount==4 && averageLength>39.5){//四个选项分四行
            for (int i=0;i<entity.getChoices().length;i++){
                if(i==0){
                    paragraph.append("A. "+CommonUtil.picturePathConverter(entity.getChoices()[i],bookVersion).trim()+"　　 \\\\\n");
                }else if(i==1){
                    paragraph.append("B. "+CommonUtil.picturePathConverter(entity.getChoices()[i],bookVersion).trim()+"　　 \\\\\n");
                }else if(i==2){
                    paragraph.append("C. "+CommonUtil.picturePathConverter(entity.getChoices()[i],bookVersion).trim()+"　　 \\\\\n");
                }else if(i==3){
                    paragraph.append("D. "+CommonUtil.picturePathConverter(entity.getChoices()[i],bookVersion).trim()+"　　 ");
                }
            }
        }
        paragraph.append("\\\\\n");
        if(needRemark){
            if(entity.getAnswer()!=null || !entity.getAnswer().equals("")){
                paragraph.append("答案：").append(CommonUtil.picturePathConverter(entity.getAnswer(),bookVersion)+"\\\\\n");
            }
            if(entity.getRemark()!=null ||entity.getRemark().equals("")){
            	String remark=CommonUtil.picturePathConverter(entity.getRemark(),bookVersion).replace("####","")+"\\\\\n";
            	if(remark.startsWith("解析：")){
            		remark=remark.replaceFirst("解析：","");
            	}else if (remark.startsWith("解析:")) {
            		remark=remark.replaceFirst("解析:","");
				}
                paragraph.append("解析：").append(remark);
            }
        }
        paragraph.append("\\\\\n");
        return paragraph.toString();
    }
    
    
    /**
    *
    * @param entity 选择题实体
    * @param no 题号
    * @param needRemark 是否需要答案，解析？
    * @return 一道选择题段落
    */
   public String makeUnitTestChoiceQuesionParagraph(ChoiceQuestionEntity entity,int no,boolean needRemark,String bookVersion){
       StringBuffer paragraph=new StringBuffer();
       if(no==1){
       	paragraph.append("\\\\\n");
       }
       paragraph.append(entity.getNo()+". ").append(CommonUtil.picturePathConverter(entity.getStem())+"\\\\\n");

       /**
        * 生成的word一行能显示33个中文字，99byte。
        */
       int choiceCount=entity.getChoices().length;
       int choiceALength=0;
       int choiceBLength=0;
       int choiceCLength=0;
       int choiceDLength=0;
       for(int i=0;i<choiceCount;i++){
           if(i==0){
               choiceALength=CommonUtil.contentLength(entity.getChoices()[i]);
           }else if(i==1){
               choiceBLength=CommonUtil.contentLength(entity.getChoices()[i]);
           }else if(i==2){
               choiceCLength=CommonUtil.contentLength(entity.getChoices()[i]);
           }else if(i==3){
               choiceDLength=CommonUtil.contentLength(entity.getChoices()[i]);
           }
       }
       int choiceLengthCount=0;
       for(String s:entity.getChoices()){
           choiceLengthCount+=CommonUtil.contentLength(s);
       }
       double averageLength=(double)choiceLengthCount/(double)choiceCount;

       if(choiceCount==2 && averageLength<=39){//两个选项在一行
           for (int i=0;i<entity.getChoices().length;i++){
               if(i==0){
                   paragraph.append("A. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 ");
               }else if(i==1){
                   paragraph.append("B. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 ");
               }
           }
       }else if(choiceCount==2 && averageLength>39){//两个选项分两行
           for (int i=0;i<entity.getChoices().length;i++){
               if(i==0){
                   paragraph.append("A. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 \\\\\n");
               }else if(i==1){
                   paragraph.append("B. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 ");
               }
           }
       }else if(choiceCount==3 && averageLength<=22){//三个选项在一行
           for (int i=0;i<entity.getChoices().length;i++){
               if(i==0){
                   paragraph.append("A. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 ");
               }else if(i==1){
                   paragraph.append("B. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 ");
               }else if(i==2){
                   paragraph.append("C. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 ");
               }
           }
       }else if(choiceCount==3 && averageLength>22 && averageLength<=34.5){//三个选项分两行
           for (int i=0;i<entity.getChoices().length;i++){
               if(i==0){
                   paragraph.append("A. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 ");
               }else if(i==1){
                   paragraph.append("B. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 \\\\\n");
               }else if(i==2){
                   paragraph.append("C. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 ");
               }
           }
       }else if(choiceCount==3 && averageLength>34.5){//三个选项分三行
           for (int i=0;i<entity.getChoices().length;i++){
               if(i==0){
                   paragraph.append("A. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 \\\\\n");
               }else if(i==1){
                   paragraph.append("B. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 \\\\\n");
               }else if(i==2){
                   paragraph.append("C. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 ");
               }
           }
       }else if(choiceCount==4 && averageLength<=14){//四个选项一行
           for (int i=0;i<entity.getChoices().length;i++){
               if(i==0){
                   paragraph.append("A. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 ");
               }else if(i==1){
                   paragraph.append("B. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 ");
               }else if(i==2){
                   paragraph.append("C. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 ");
               }else if(i==3){
                   paragraph.append("D. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 ");
               }
           }
       }else if(choiceCount==4 && averageLength>14 && averageLength<=39.5){//四个选项分两行
           for (int i=0;i<entity.getChoices().length;i++){
               if(i==0){
                   paragraph.append("A. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 ");
               }else if(i==1){
                   paragraph.append("B. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 \\\\\n");
               }else if(i==2){
                   paragraph.append("C. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 ");
               }else if(i==3){
                   paragraph.append("D. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 ");
               }
           }
       }else if(choiceCount==4 && averageLength>39.5){//四个选项分四行
           for (int i=0;i<entity.getChoices().length;i++){
               if(i==0){
                   paragraph.append("A. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 \\\\\n");
               }else if(i==1){
                   paragraph.append("B. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 \\\\\n");
               }else if(i==2){
                   paragraph.append("C. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 \\\\\n");
               }else if(i==3){
                   paragraph.append("D. "+CommonUtil.picturePathConverter(entity.getChoices()[i]).trim()+"　　 ");
               }
           }
       }
       paragraph.append("\\\\\n");
       if(needRemark){
           if(entity.getAnswer()!=null || !entity.getAnswer().equals("")){
               paragraph.append("答案：").append(CommonUtil.picturePathConverter(entity.getAnswer())+"\\\\\n");
           }
           if(entity.getRemark()!=null ||entity.getRemark().equals("")){
           	String remark=CommonUtil.picturePathConverter(entity.getRemark()).replace("####","")+"\\\\\n";
           	if(remark.startsWith("解析：")){
           		remark=remark.replaceFirst("解析：","");
           	}else if (remark.startsWith("解析:")) {
           		remark=remark.replaceFirst("解析:","");
				}
               paragraph.append("解析：").append(remark);
           }
       }
       paragraph.append("\\\\\n");
       return paragraph.toString();
   }

    /**
     * 填空题构建段落
     * @param entity
     * @param no
     * @param needRemark
     * @return
     */
    public String makeFillBlankQuestionParagraph(FillBlankQuestionEntity entity,int no,boolean needRemark){
    	StringBuffer paragraph=new StringBuffer();
    	if(no==1){
        	paragraph.append("\\\\\n");
        }
        paragraph.append(entity.getNo()+". ").append(CommonUtil.picturePathConverter(entity.getStem())+"\\\\\n");
        if(needRemark){
            if(entity.getAnswer()!=null || !"".equals(entity.getAnswer())){
                paragraph.append("答案：");
                for(int i=0;i<entity.getAnswer().length;i++){
                	paragraph.append(CommonUtil.picturePathConverter((entity.getAnswer())[i]));
                	if(i!=entity.getAnswer().length){
                		paragraph.append("  ,  ");
                	}
                }
                paragraph.append("\\\\\n");
            }
            
            if(entity.getRemark()!=null || !"".equals(entity.getRemark())){
                paragraph.append("解析：").append(CommonUtil.picturePathConverter(entity.getRemark())+"\\\\\n");
            }
        }
        paragraph.append("\\\\\n");
        return paragraph.toString();
    }

    /**
     * 解答题构建段落
     * @param entity
     * @param no
     * @param needRemark
     * @return
     */
    public String makeSubjectiveQuestionParagraph(SubjectiveQuestionEntity entity,int no,boolean needRemark){
    	StringBuffer paragraph=new StringBuffer();
    	if(no==1){
        	paragraph.append("\\\\\n");
        }
        paragraph.append(entity.getNo()+". ").append(CommonUtil.picturePathConverter(entity.getStem())+"\\\\\n");
        if(needRemark){
            if(entity.getAnswer()!=null || !"".equals(entity.getAnswer())){
                paragraph.append("答案：").append(CommonUtil.picturePathConverter(entity.getRemark())+"\\\\\n");
            }
            if(entity.getRemark()!=null || !"".equals(entity.getRemark())){
                paragraph.append("解析：").append(CommonUtil.picturePathConverter(entity.getRemark())+"\\\\\n");
            }
        }
        paragraph.append("\\\\\n");
        return paragraph.toString();
    }


}
