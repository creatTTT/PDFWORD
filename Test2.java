package com.mintel.utils.texUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Test2 {
	
	/*public static void main(String[] args) {
		MakeTexToPdfUtil util=new MakeTexToPdfUtil();

        ChoiceQuestionEntity entity=new ChoiceQuestionEntity();
        entity.setStem("下面的计算正确吗？(  ) <img height=\"63\" id=\"rId5\" src=\"../../images/98-01-05-10.files/df1435fc-bb9f-429b-928a-5bdfdb5dcd1cimage2.png\" width=\"79\">.某小组6名同学的身高情况如下表，平均身高是( )cm。\\( \\displaystyle \\frac{1}{1\\times 2}+\\frac{1}{2\\times 3}+\\frac{1}{3\\times 4}+\\cdots +\\frac{1}{2018\\times 2019}\\)=( )。");
        String[] options={"<img height=\"63\" id=\"rId5\" src=\"../../images/98-01-05-10.files/df1435fc-bb9f-429b-928a-5bdfdb5dcd1cimage1.png\" width=\"79\">","46","456456"};
        entity.setChoices(options);
        entity.setAnswer("A");
        entity.setRemark("设有\\( x\\)辆汽车，用第一种方案表示这批货物的吨数是(\\( 4x+2\\))吨，用第二种方案表示这批货物的吨数是(\\( 5x-3\\))吨。根据两种方案，货物的总吨数不变，列方程为\\( 4x+2=5x-3\\)，" +
                "解得\\( x=5\\)，即有5辆汽车，那么这批货物一共有4×5＋2＝22(吨)； 故选B。");


        ChoiceQuestionEntity entity2=new ChoiceQuestionEntity();
        entity2.setStem("某小组6名同学的身高情况如下表，平均身高是( )cm。\\( \\displaystyle \\frac{1}{1\\times 2}+\\frac{1}{2\\times 3}+\\frac{1}{3\\times 4}+\\cdots +\\frac{1}{2018\\times 2019}\\)=( )。");
        String[] options2={"\\( \\frac{3090}{100}\\)\\( \\frac{3090}{100}\\)","\\( \\frac{3090}{100}\\)","\\( \\frac{3090}{100}\\)"};
        entity2.setChoices(options2);
        entity2.setAnswer("A");
        entity2.setRemark("设有\\( x\\)辆汽车，用第一种方案表示这批货物的吨数是(\\( 4x+2\\))吨，用第二种方案表示这批货物的吨数是(\\( 5x-3\\))吨。根据两种方案，货物的总吨数不变，列方程为\\( 4x+2=5x-3\\)，" +
                "解得\\( x=5\\)，即有5辆汽车，那么这批货物一共有4×5＋2＝22(吨)； 故选B。");

        ChoiceQuestionEntity entity3=new ChoiceQuestionEntity();
        entity3.setStem("某小组6名同学的身高情况如下表，平均身高是( )cm。\\( \\displaystyle \\frac{1}{1\\times 2}+\\frac{1}{2\\times 3}+\\frac{1}{3\\times 4}+\\cdots +\\frac{1}{2018\\times 2019}\\)=( )。");
        String[] options3={"然后IG利润华农\\( \\frac{3090}{100}\\)\\( \\frac{3090}{100}\\)","IP唐人街哦朋友镜头膜骗人\\( \\frac{3090}{100}\\)","弄丢了退款回娘家\\( \\frac{3090}{100}\\)"};
        entity3.setChoices(options3);
        entity3.setAnswer("A");
        entity3.setRemark("设有\\( x\\)辆汽车，用第一种方案表示这批货物的吨数是(\\( 4x+2\\))吨，用第二种方案表示这批货物的吨数是(\\( 5x-3\\))吨。根据两种方案，货物的总吨数不变，列方程为\\( 4x+2=5x-3\\)，" +
                "解得\\( x=5\\)，即有5辆汽车，那么这批货物一共有4×5＋2＝22(吨)； 故选B。");

        List<ChoiceQuestionEntity> questions=new ArrayList<>();
        questions.add(entity);
        questions.add(entity2);
        questions.add(entity3);

        //String path=util.makeWordPaperOnlyChoiceQuestion(questions,"标题","2020.2.2",true);
        String path=util.makePdfPaperOnlyChoiceQuestion(questions, true, "标题1111111", "", "", "tym", "2020-01-01");
        String command="pandoc "+path+" -o "+path.replace(".tex",".docx");
        System.out.println(command);
        try {
             //Runtime.getRuntime().exec(command);
        	Runtime.getRuntime().exec(command,null,new File("/MintelRevSVN/MintelRev/WebRoot/mintmath-middle-tongbu/tex"));
            System.out.println("================tex-->docx完成。=====================");
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashMap<String,String> map = new HashMap<String, String>();
        map.put("　　","    ");
        System.out.println("./WebRoot/mintmath-middle-tongbu/tex/"+path.replace(".tex",".docx"));
        WordUtil.replaceAll("./WebRoot/mintmath-middle-tongbu/tex/"+path.replace(".tex",".docx"), map);
        
	}*/
	
	public static void main(String[] args) throws IOException {
		/*File file = new File("WebRoot/mintmath-middle-tongbu/tex/28b2c575a88f4186b8c78bf6de7a479f.docx");
		System.out.println(file.getPath());
		System.out.println(file.getAbsolutePath());
		System.out.println(file.getParentFile().getPath());*/
		HashMap<String,String> map = new HashMap<String, String>();
        map.put("　　","    ");
        WordUtil.replaceAll("WebRoot/mintmath-middle-tongbu/tex/6b06f37e5e5c43c58e7cdb259a0e758e.docx", map);
		
	}

}
