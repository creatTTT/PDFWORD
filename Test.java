package com.mintel.utils.texUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Test {
	
	public static void main(String[] args) {
		MakeTexToWordUtil util=new MakeTexToWordUtil();

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
        entity3.setAnswer("ABCD");
        entity3.setRemark("设有\\( x\\)辆汽车，用第一种方案表示这批货物的吨数是(\\( 4x+2\\))吨，用第二种方案表示这批货物的吨数是(\\( 5x-3\\))吨。根据两种方案，货物的总吨数不变，列方程为\\( 4x+2=5x-3\\)，" +
                "解得\\( x=5\\)，即有5辆汽车，那么这批货物一共有4×5＋2＝22(吨)； 故选B。");

        List<ChoiceQuestionEntity> questions=new ArrayList<>();
        questions.add(entity);
        questions.add(entity2);
        questions.add(entity3);
        
        
        
        SubjectiveQuestionEntity entity4=new SubjectiveQuestionEntity();
        entity4.setStem("设有\\( x\\)辆汽车，用第一种方案表示这批货物的吨数是(\\( 4x+2\\))吨");
        entity4.setAnswer("设有\\( x\\)辆汽车，用第一种方案表示这批货物的吨数是(\\( 4x+2\\))吨");
        entity4.setRemark("设有\\( x\\)辆汽车，用第一种方案表示这批货物的吨数是(\\( 4x+2\\))吨，用第二种方案表示这批货物的吨数是(\\( 5x-3\\))吨。根据两种方案，货物的总吨数不变，列方程为\\( 4x+2=5x-3\\)，\" +\n" +
                "\"解得\\( x=5\\)，即有5辆汽车，那么这批货物一共有4×5＋2＝22(吨)； 故选B。");

        SubjectiveQuestionEntity entity5=new SubjectiveQuestionEntity();
        entity5.setStem("设有\\( x\\)辆汽车，用第一种方案表示这批货物的吨数是(\\( 4x+2\\))吨");
        entity5.setAnswer("设有\\( x\\)辆汽车，用第一种方案表示这批货物的吨数是(\\( 4x+2\\))吨");
        entity5.setRemark("设有\\( x\\)辆汽车，用第一种方案表示这批货物的吨数是(\\( 4x+2\\))吨，用第二种方案表示这批货物的吨数是(\\( 5x-3\\))吨。根据两种方案，货物的总吨数不变，列方程为\\( 4x+2=5x-3\\)，\" +\n" +
                "\"解得\\( x=5\\)，即有5辆汽车，那么这批货物一共有4×5＋2＝22(吨)； 故选B。");

        List<SubjectiveQuestionEntity> subQuestions=new ArrayList<SubjectiveQuestionEntity>();
        subQuestions.add(entity4);
        subQuestions.add(entity5);
        
        
        for(int i=0;i<3;i++){
        	String path=util.makeWordPaperOnlyChoiceQuestion(questions,"12121","2020.0.0",true);
            String command="pandoc "+path+" -o "+path.replace(".tex",".docx");
            System.out.println(command);
            try {
            	Process process=Runtime.getRuntime().exec(command,null,new File("../MintelRev/WebRoot/mintmath-middle-tongbu/tex"));
            	printMessage(process.getInputStream());
                printMessage(process.getErrorStream());
                int value = process.waitFor();
            	System.out.println("=======tex-->docx完成。========value="+value+"======");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
    			e.printStackTrace();
    		}
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("　　","    ");//第一个""里面是特殊字符
            path = "WebRoot/mintmath-middle-tongbu/tex/" + path.replace(".tex",".docx");
            System.out.println(path);
            System.out.println("path  "+path);
            WordUtil.replaceAll(path, map);
        }
        
        
	}
	
	private static void printMessage(final InputStream input) {
        new Thread(new Runnable() {
            public void run() {
                Reader reader = new InputStreamReader(input);
                BufferedReader bf = new BufferedReader(reader);
                String line = null;
                try {
                    while((line=bf.readLine())!=null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    	e.printStackTrace();
                }
             }
        }).start();
    }

}
