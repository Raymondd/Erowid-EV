package com.example.erowidexperiencevault;

public class Story {

	private String rating;
	private String link;
	private String title;
	private String author;
	private String substances;
	private String date;
	
	public Story(String r, String l, String t, String a, String s, String d){
		rating = r;
		link = "http://www.erowid.org/experiences/" + l;
		title = t;
		author = a;
		substances = s;
		date = d;
	}

	public String getRating(){
		return rating;
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
