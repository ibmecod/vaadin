package com.ecodcnc.vaadinui;

import java.io.Serializable;


public class Symptoms implements Serializable {
	
	private static final long serialVersionUID = 1L;
	String answer;
	Integer id;

	public Symptoms(Integer id, String answer){
		this.id=id;
		this.answer=answer;
	}
	
	public int getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String Answer) {
		this.answer = Answer;
	}
	
	/* ArrayList<QuestionAnswer> qaList = new ArrayList<QuestionAnswer>();
	ArrayList<String> answers = new ArrayList<String>();
	String table1ColHeaderVal;
	String table2ColHeaderVal;
	ArrayList table1ColVals;
	ArrayList table2ColVals;
	
	public Symptoms(ArrayList qaList) {
		this.qaList=qaList;
		setTabletableColuumns();
	}
	
	public ArrayList<QuestionAnswer> getQaList() {
		return qaList;
	}

	public void setQaList(ArrayList<QuestionAnswer> qaList) {
		this.qaList = qaList;
	}

	public String getTable2ColHeaderVal() {
		return table2ColHeaderVal;
	}

	public void setTable2ColHeaderVal(String table2ColHeaderVal) {
		this.table2ColHeaderVal = table2ColHeaderVal;
	}

	public ArrayList getTable1ColVals() {
		return this.table1ColVals;
	}

	public void setTable1ColVals(ArrayList table1ColVals) {
		this.table1ColVals = table1ColVals;
	}

	public ArrayList getTable2ColVals() {
		return this.table2ColVals;
	}

	public void setTable2ColVals(ArrayList table2ColVals) {
		this.table2ColVals = table2ColVals;
	}

	
	public void setTabletableColuumns() {
		int count=1;
		
		for (QuestionAnswer result : this.qaList) {
			if (count == 1) {
				table1ColHeaderVal = result.getQuestion().toString();
				setTable1ColHeaderVal(table1ColHeaderVal);
				for (Answer answer : result.getAnswerList()) {
					table1ColVals.add( answer.getAnswer());
				}
				setTable1ColVals(table1ColVals);
					
			}else {
				table2ColHeaderVal = result.getQuestion().toString();
				setTable2ColHeaderVal(table2ColHeaderVal);
				
			}
			setTable2ColHeaderVal(table2ColHeaderVal);
			for (Answer answer : result.getAnswerList()) {
				table2ColVals.add( answer.getAnswer());
			}
			setTable2ColVals(table2ColVals);
		}
		
		count ++;
		
	}

	public String getTable1ColHeaderVal() {
		return table1ColHeaderVal;
	}

	public void setTable1ColHeaderVal(String table1ColHeaderVal) {
		this.table1ColHeaderVal = table1ColHeaderVal;
	}*/
	

	

}
