package com.theidiomsbook.model;

public class Idiom {
	private int id;
	private String english, spanish;
	private String english_description, spanish_description;
	private String examples;
	
	public int getId() {
	  return id;
  }
	
	public void setId(int id) {
	  this.id = id;
  }
	
	public String getEnglish() {
	  return english;
  }
	
	public void setEnglish(String english) {
	  this.english = english;
  }
	
	public String getSpanish() {
	  return spanish;
  }
	
	public void setSpanish(String spanish) {
	  this.spanish = spanish;
  }
	
	public String getEnglishDescription() {
	  return english_description;
  }
	
	public void setEnglishDescription(String english_description) {
	  this.english_description = english_description;
  }
	
	public String getSpanishDescription() {
	  return spanish_description;
  }
	
	public void setSpanishDescription(String spanish_description) {
	  this.spanish_description = spanish_description;
  }
	
	public String getExamples() {
	  return examples;
  }
	
	public void setExamples(String examples) {
	  this.examples = examples;
  }
	
}
