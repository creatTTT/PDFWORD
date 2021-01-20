package com.mintel.utils.texUtil;

import java.util.List;

/**
 * Created by Administrator on 2020/6/8 0008.
 */
public class MakeTexToPdfUtil {


    /**
     * BHCexam的文档说明：http://docs.mathcrowd.cn/advances/bhcexam.html#bhcexam-option
     *
     * \\documentclass[answers]{BHCexam}:使用BHCexam文档格式。[answers]是否显示解析，答案(学生版把这段删了就行)。
     * \\usepackage{graphicx}：显示图片的包
     * \\linespread{1.5}：行间距1.5
     * \\setlength{\parindent}{0pt}：段落不添加首空格
     * \\begin{document}:文档开始
     * \\end{document}:文档结束
     * 1.\\title{title_title}：文档的标题；title_title 替换标题的标志字符
     * 2.\\subtitle{subtitle_subtitle}：文档的副标题；subtitle_subtitle 替换副标题的标志字符
     * 3.\\notice{notice_notice}：考试说明
     * 4.\\author{author_author}：作者名
     * 5.\\date{date_date}：日期
     * \\maketitle：是否显示12345点的内容。不显示就删了它。
     */
    private final String PDF_TEMPLATE = "\\documentclass[answers]{BHCexam}\r\n" +
                                           "\\usepackage{hyperref}\r\n" +
                                           "\\usepackage{graphicx}\r\n" +
                                           "\\pagestyle{plain}\r\n" +
                                           "\\linespread{1.5}\r\n" +
                                           "\\setlength{\\parindent}{0pt}\r\n" +
                                           "\\begin{document}\r\n" +
                                           "\\title{title_title}\r\n" +
                                           "\\subtitle{subtitle_subtitle}\r\n" +
                                           "\\notice{notice_notice}\r\n" +
                                           "\\author{author_author}\r\n" +
                                           "\\date{date_date}\r\n" +
                                           "\\maketitle\r\n" +
                                           "\\begin{groups}\r\n"+
                                           "result_result\r\n"+
                                           "\\end{groups}\r\n"+
                                           "\\end{document}\r\n";




    private final String CHOICE_QUESTION_GROUP_TEMPLATE="\\group{选择题}{}\r\n" +
                                                                "\\begin{questions}[p]\r\n"+
                                                                "cResult_cResult \r\n"+
                                                                "\\end{questions}\r\n";

    private final String FILLBLANK_QUESTION_GROUP_TEMPLATE="\\group{填空题}{}\r\n" +
                                                                    "\\begin{questions}[]\r\n"+
                                                                    "fResult_fResult \r\n"+
                                                                    "\\end{questions}\r\n";

    private final String SUBJECTIVE_QUESTION_GROUP_TEMPLATE="\\group{主观题}{}\r\n" +
                                                                "\\begin{questions}[t]\r\n"+
                                                                "sResult_sResult \r\n"+
                                                                "\\end{questions}\r\n";



    private final String CHOICE_QUESTION_TEMPLATE = "\\question[] stem_stem \r\n" +
                                                            "\\fourchoices option_option \r\n" +
                                                            "\\begin{solution}{4cm} \r\n" +
                                                            "\\methodonly remark_remark \r\n" +
                                                            "\\end{solution} \r\n";


    private final String FILLBLANK_QUESTION_TEMPLATE = "\\question[] stem_stem \r\n" +
                                                              "\\begin{solution}{4cm}\r\n" +
                                                              "\\methodonly remark_remark\r\n" +
                                                              "\\end{solution}\r\n";


    private final String SUBJECTIVE_QUESTION_TEMPLATE = "\\question[] stem_stem \r\n" +
                                                                "\\begin{subquestions}\r\n" +
                                                                "\\subquestion subquestion_subquestion \r\n" +
                                                                "\\end{subquestions}\r\n" +
                                                                "\\begin{solution}{8cm}\r\n" +
                                                                "\\methodonly remark_remark \r\n"+
                                                                "\\end{solution}\r\n";



    private final String PICTUREMODULE="\\\\quad\\\\includegraphics{pictureName_pictureName}";



    /**
     * 题目全是选择题的情况。
     * 选项支持3-6个
     * xelatex.exe -synctex=1 -interaction=nonstopmode "54ec222a-0e00-4851-96b8-453192713e07".tex   到tex文件的路径下使用命令行生成pdf。
     * @param cqs 选择题list
     * @param needRemark 是否需要显示答案和解析？
     * @param title 试卷标题 （最好给出）
     * @param subtitle 试卷副标题
     * @param notice 试卷提示/注意事项
     * @param author 作者
     * @param date 日期
     * @return 生成的.tex文件路径
     */
    public String makePdfPaperOnlyChoiceQuestion(List<ChoiceQuestionEntity> cqs,boolean needRemark,String title,String subtitle,String notice,String author,String date) {
        String paper=PDF_TEMPLATE;
        if(needRemark==false){
            paper=paper.replace("[answers]","");
        }
        if(title==null || title.equals("")){
            paper=paper.replace("title_title","");
        }else{
            paper=paper.replace("title_title",title);
        }
        if(subtitle==null || subtitle.equals("")){
            paper=paper.replace("\\subtitle{subtitle_subtitle}","");
        }else{
            paper=paper.replace("subtitle_subtitle",subtitle);
        }
        if(notice==null || notice.equals("")){
            paper=paper.replace("\\notice{notice_notice}","");
        }else{
            paper=paper.replace("notice_notice",notice);
        }
        if(author==null || author.equals("")){
            paper=paper.replace("\\author{author_author}","");
        }else {
            paper=paper.replace("author_author",author);
        }
        if(date==null || date.equals("")){
            paper=paper.replace("date_date","");
        }else{
            paper=paper.replace("date_date",date);
        }
        String paperPath="";
        try {
            paper=paper.replace("result_result",makeChoiceGroupParagraph(cqs));
            paperPath=CommonUtil.writePaperTex(paper,"/MintelRevSVN/MintelRev/WebRoot/mintmath-middle-tongbu/tex");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return paperPath;
    }


    public String makeChoiceGroupParagraph(List<ChoiceQuestionEntity> choiceQuestions) throws Exception {
        String choiceGroupParagraph=CHOICE_QUESTION_GROUP_TEMPLATE;
        String ChoiceQuestionParagraphs="";
        for(ChoiceQuestionEntity entity:choiceQuestions){
            ChoiceQuestionParagraphs=ChoiceQuestionParagraphs+makeChoiceQuestionParagraph(entity);
        }
        return choiceGroupParagraph.replace("cResult_cResult",ChoiceQuestionParagraphs);
    }

    public String makeFillBlankGroupParagraph(List<FillBlankQuestionEntity> fillBlankQuestions){
        String fillBlankGroupParagraph=FILLBLANK_QUESTION_GROUP_TEMPLATE;
        String fillBlankQuestionParagraphs="";
        for(FillBlankQuestionEntity entity:fillBlankQuestions){
            fillBlankQuestionParagraphs=fillBlankQuestionParagraphs+makeFillBlankQuestionParagraph(entity);
        }
        return fillBlankGroupParagraph.replace("fResult_fResult",fillBlankQuestionParagraphs);
    }

    public String makeSubjectiveGroupParagraph(List<SubjectiveQuestionEntity> subjectiveQuestions){
        String subjectiveGroupParagraph=SUBJECTIVE_QUESTION_GROUP_TEMPLATE;
        String subjectiveQuestionParagraphs="";
        for(SubjectiveQuestionEntity entity:subjectiveQuestions){
            subjectiveQuestionParagraphs=subjectiveQuestionParagraphs+makeSubjectiveQuestionParagraph(entity);
        }
        return subjectiveGroupParagraph.replace("sResult_sResult",subjectiveQuestionParagraphs);
    }


    /**
     *
     * @param entity 选择题实体
     * @return 选择题段落
     * @throws Exception
     */
    public String makeChoiceQuestionParagraph(ChoiceQuestionEntity entity) throws Exception {

        String choiceQuestionParagraph=CHOICE_QUESTION_TEMPLATE;

        int choiceCount=entity.getChoices().length;
        if(choiceCount<=2 || choiceCount>=7){
            throw new Exception("选择题的选项数必须为3-6个。");
        }

        String choices="";
        if(choiceCount==2){
            choices="twochoices {op1}{op2}";
        }else if(choiceCount==3){
            choices="threechoices {op1}{op2}{op3}";
        }else if(choiceCount==4){
            choices="fourchoices {op1}{op2}{op3}{op4}";
        }
        choiceQuestionParagraph=choiceQuestionParagraph.replace("fourchoices option_option",choices);

        choiceQuestionParagraph=choiceQuestionParagraph.replace("stem_stem",CommonUtil.picturePathConverter(entity.getStem()))
                                                        .replaceAll("[(]\\s*[)]","\\\\key{answer_answer}")
                                                        .replace("answer_answer",CommonUtil.picturePathConverter(entity.getAnswer()));
        for(int i=0;i<choiceCount;i++){
            if(i==0){
                choiceQuestionParagraph=choiceQuestionParagraph.replace("op1",CommonUtil.picturePathConverter(entity.getChoices()[i]));
            }else if(i==1){
                choiceQuestionParagraph=choiceQuestionParagraph.replace("op2",CommonUtil.picturePathConverter(entity.getChoices()[i]));
            }else if(i==2){
                choiceQuestionParagraph=choiceQuestionParagraph.replace("op3",CommonUtil.picturePathConverter(entity.getChoices()[i]));
            }else if(i==3){
                choiceQuestionParagraph=choiceQuestionParagraph.replace("op4",CommonUtil.picturePathConverter(entity.getChoices()[i]));
            }
        }
        choiceQuestionParagraph=choiceQuestionParagraph.replace("remark_remark",CommonUtil.picturePathConverter(entity.getRemark()));
        return choiceQuestionParagraph;
    }


    /**
     *
     * @param entity FillBlankQuestionEntity
     * @return 填空题段落
     */
    public String makeFillBlankQuestionParagraph(FillBlankQuestionEntity entity){
        String choiceQuestionParagraph=FILLBLANK_QUESTION_TEMPLATE;

        choiceQuestionParagraph=choiceQuestionParagraph.replace("stem_stem",CommonUtil.picturePathConverter(entity.getStem())).replaceAll("_{3,10}","\\\\key{answer_answer}");
        for(int i=0;i<entity.getAnswer().length;i++){
            choiceQuestionParagraph=choiceQuestionParagraph.replaceFirst("answer_answer",CommonUtil.picturePathConverter(entity.getAnswer()[i]).replace("\\","\\\\"));
        }
        choiceQuestionParagraph=choiceQuestionParagraph.replace("remark_remark",CommonUtil.picturePathConverter(entity.getRemark()));

        return choiceQuestionParagraph;
    }


    /**
     *
     * @param entity SubjectiveQuestionEntity
     * @return 主观题、大题段落
     */
    public String makeSubjectiveQuestionParagraph(SubjectiveQuestionEntity entity){
        String subjectiveQuestionParagraph=SUBJECTIVE_QUESTION_TEMPLATE;

        subjectiveQuestionParagraph=subjectiveQuestionParagraph.replace("stem_stem",CommonUtil.picturePathConverter(entity.getStem()));

        int subQuestionCount=entity.getSubQuestion().length;
        String subQuestions="";

        if(subQuestionCount==1){

        }else if(subQuestionCount==2){
            subQuestions="\\subquestion subquestion_subquestion \r\n \\subquestion subquestion_subquestion \r\n";
        }else if(subQuestionCount==3){
            subQuestions="\\subquestion subquestion_subquestion \r\n \\subquestion subquestion_subquestion \r\n" +
                          "\\subquestion subquestion_subquestion \r\n";
        }else if(subQuestionCount==4){
            subQuestions="\\subquestion subquestion_subquestion \r\n \\subquestion subquestion_subquestion \r\n" +
                         "\\subquestion subquestion_subquestion \r\n \\subquestion subquestion_subquestion \r\n";
        }
        subjectiveQuestionParagraph=subjectiveQuestionParagraph.replace("\\subquestion subquestion_subquestion",subQuestions);

        for(int i=0;i<subQuestionCount;i++){
            subjectiveQuestionParagraph=subjectiveQuestionParagraph.replaceFirst("subquestion_subquestion", CommonUtil.picturePathConverter(entity.getSubQuestion()[i]));
        }
        subjectiveQuestionParagraph=subjectiveQuestionParagraph.replace("remark_remark",CommonUtil.picturePathConverter(entity.getRemark()));

        return subjectiveQuestionParagraph;
    }


}
