package com.theidiomsbook.model;

public class Response implements Comparable<Response> {
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
			Response response = (Response) obj;
			return this.idiomId == response.getIdiomId();
		} catch (ClassCastException e) {
			return false;
		}
	}

	@Override
	public int compareTo(Response response) {
		if (this.idiomId < response.getIdiomId())
			return -1;
		else if (this.idiomId > response.getIdiomId())
			return 1;
		else
			return 0;
	}

}
