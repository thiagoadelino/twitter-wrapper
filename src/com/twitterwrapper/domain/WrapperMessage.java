package com.twitterwrapper.domain;

public class WrapperMessage {

	private String emailRequester;
	
	private String subject;
	
	private String content;

	public WrapperMessage() {
		
	}
	
	public WrapperMessage(String emailRequester, String subject, String content) {
		this.emailRequester = emailRequester;
		this.subject = subject;
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEmailRequester() {
		return emailRequester;
	}

	public void setEmailRequester(String emailRequester) {
		this.emailRequester = emailRequester;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
}
