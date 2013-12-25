package com.example.erowidexperiencevault;

public class Story {

	private String link;
	private String title;
	private String author;
	private String substances;
	private String date;
	
	public Story(String l, String t, String a, String s, String d){
		link = "http://www.erowid.org/experiences/" + l;
		title = t;
		author = a;
		date = d;
	}

	public String getLink() {
		return link;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getSubstances() {
		return substances;
	}

	public String getDate() {
		return date;
	}
	
}
