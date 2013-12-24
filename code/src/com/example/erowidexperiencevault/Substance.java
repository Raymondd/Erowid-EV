package com.example.erowidexperiencevault;

public class Substance {
	private String name;
	private int id;
	
	public Substance(String name, int id){
		this.name = name;
		this.id = id;
	}
	
	public String getName(){
		return name;
	}
	
	public int getId(){
		return id;
	}
	
}
