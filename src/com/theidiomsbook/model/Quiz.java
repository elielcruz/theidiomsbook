package com.theidiomsbook.model;

import java.util.ArrayList;
import java.util.List;

public class Quiz {
	private static Quiz quiz;
	private int currentQuestion;
	private int correctAnswers;
	private int numQuestions;
	private int numResponses;
	private String quizLanguage;
	private List<Response> checkedResponses;
	
	private Quiz () {
		super();
	}
	
	public static Quiz getInstance () {
		if(quiz == null) quiz = new Quiz();
		return quiz;
	}

	public int getCurrentQuestion() {
		return currentQuestion;
	}

	public int getCorrectAnswers() {
		return correctAnswers;
	}

	public List<Response> getCheckedResponses() {
		if (checkedResponses==null)
			checkedResponses = new ArrayList<Response>();
		return checkedResponses;
	}

	public int getNumQuestions() {
	  return numQuestions;
  }
	
	public int getNumResponses() {
	  return numResponses;
  }

	public String getQuizLanguage() {
	  return quizLanguage;
  }

	public int incrementCurrentQuestion() {
		return ++currentQuestion;
	}

	public int incrementCorrectAnswers() {
		return ++correctAnswers;
	}

	public void setCheckedResponses(List<Response> responses) {
		checkedResponses = responses;
	}
	
	public void addCheckedResponse(Response response) {
		getCheckedResponses().add(response);
	}
	
	public void setNumQuestions(int numQuestions) {
	  this.numQuestions = numQuestions;
  }

	public void setNumResponses(int numResponses) {
	  this.numResponses = numResponses;
  }
	
	public void setQuizLanguage(String quizLanguage) {
	  this.quizLanguage = quizLanguage;
  }

	public void reset() {
		currentQuestion = 0;
		correctAnswers = 0;
		numQuestions = 0;
		numResponses = 0;
		quizLanguage = "english";
		getCheckedResponses().clear();
	}
	
	public Response getNewResponseObject () {
		return new Response();
	}

}
