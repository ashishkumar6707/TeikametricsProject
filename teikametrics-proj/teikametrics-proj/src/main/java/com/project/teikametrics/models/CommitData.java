package com.project.teikametrics.models;

public class CommitData {
	private User author;
	private User committer;
	private String message;
	
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public User getCommitter() {
		return committer;
	}
	public void setCommitter(User committer) {
		this.committer = committer;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	

}
