package com.project.teikametrics.models;

public class Commit {

	private String sha;
	private CommitData commit;
	private String node_id;
	private User author;
	private User committer;
	
	public String getSha() {
		return sha;
	}
	public void setSha(String sha) {
		this.sha = sha;
	}
	public CommitData getCommit() {
		return commit;
	}
	public void setCommit(CommitData commit) {
		this.commit = commit;
	}
	public String getNode_id() {
		return node_id;
	}
	public void setNode_id(String node_id) {
		this.node_id = node_id;
	}
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

}
