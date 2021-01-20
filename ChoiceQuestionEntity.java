package com.mintel.utils.texUtil;

/**
 * Created by Administrator on 2020/6/8 0008.
 */
public class ChoiceQuestionEntity {

    private int no;  //题号
    private String stem;  // 题干
    private String[] choices; //选项
    private String answer; //答案   A B C D 
    private String remark; //解析

    public ChoiceQuestionEntity() {
    }

    public ChoiceQuestionEntity( String stem, String[] choices, String answer, String remark) {
        this.stem = stem;
        this.choices = choices;
        this.answer = answer;
        this.remark = remark;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getStem() {
        return stem;
    }

    public void setStem(String stem) {
        this.stem = stem;
    }

    public String[] getChoices() {
        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
