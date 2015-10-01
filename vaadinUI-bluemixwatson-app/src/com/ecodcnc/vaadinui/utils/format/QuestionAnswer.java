package com.ecodcnc.vaadinui.utils.format;


import java.util.ArrayList;
import java.util.List;


public class QuestionAnswer {
	String question;
	ArrayList<Answer> answerList;
	
	public QuestionAnswer()
	{
		question="";
		answerList = new ArrayList< Answer > ();
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public List<Answer> getAnswerList() {
		return answerList;
	}
	public void setAnswerList( ArrayList<Answer> answerList) {
		this.answerList = answerList;
	}	
	
	public void addAnswer( Answer ans ){
		answerList.add( ans );
	}
}
