package com.gehc.apps.fmk.sparkdemo.entity;

public class Book {

	private String id;
	private String author;
	private String title;

	public Book(String id, String author, String title) {
		this.id = id;
		this.author = author;
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("id:").append(id).append(",");
		sb.append("author:").append(author).append(",");
		sb.append("title:").append(title);
		sb.append("}");
		return sb.toString();
	}
}