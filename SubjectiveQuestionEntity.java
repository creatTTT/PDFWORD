package com.mintel.utils.texUtil;

/**
 * Created by Administrator on 2020/6/10 0010.
 */
public class SubjectiveQuestionEntity {

	private int no;
    private String stem;
    private String[] subQuestion;
    private String answer;
    private String remark;

    public SubjectiveQuestionEntity() {
    }

    public SubjectiveQuestionEntity(String stem, String[] subQuestion, String answer, String remark) {
        this.stem = stem;
        this.subQuestion = subQuestion;
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

    public String[] getSubQuestion() {
        return subQuestion;
    }

    public void setSubQuestion(String[] subQuestion) {
        this.subQuestion = subQuestion;
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
