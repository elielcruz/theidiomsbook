package com.theidiomsbook;

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

	protected class Response implements Comparable<Response> {
		private int idiomId;		
		private String sourceIdiom;
		private String targetIdiom;
		private boolean isTheCorrectAnswer;
		private boolean guessed;
		private String askedIdiom;

		Response() {
			super();
		}

		public int getIdiomId() {
			return idiomId;
		}

		public String getSourceIdiom() {
			return sourceIdiom;
		}

		public String getTargetIdiom() {
			return targetIdiom;
		}

		public boolean isTheCorrectAnswer() {
			return isTheCorrectAnswer;
		}

		public boolean isGuessed() {
			return guessed;
		}

		public String getAskedIdiom() {
	    return askedIdiom;
    }		

		public void setIdiomId(int idiomId) {
			this.idiomId = idiomId;
		}

		public void setSourceIdiom(String idiom) {
			this.sourceIdiom = idiom;
		}

		public void setTargetIdiom(String idiom) {
			this.targetIdiom = idiom;
		}

		public void setIsTheCorrectAnswer(boolean correct) {
			this.isTheCorrectAnswer = correct;
		}

		public void setIsGuessed(boolean guessed) {
			this.guessed = guessed;
		}
		
		public void setAskedIdiom(String idiom) {
	    this.askedIdiom = idiom;
    }
		
		@Override
		public boolean equals(Object obj) {
			try {
				Response response = (Response)obj;
				return this.idiomId == response.getIdiomId();
			} catch (ClassCastException e) {
				return false;
			}
		}

		@Override
    public int compareTo(Response response) {
			if (this.idiomId < response.getIdiomId()) return -1;
			else if (this.idiomId > response.getIdiomId()) return 1;
			else return 0;
    }
 	}
}
