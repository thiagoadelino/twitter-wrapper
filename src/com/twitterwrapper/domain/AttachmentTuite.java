package com.twitterwrapper.domain;

import java.io.File;

public class AttachmentTuite {
	
	private File file;
	
	private String urlFile;
	
	public AttachmentTuite() {
		
	}
	
	public AttachmentTuite(File file, String urlFile) {
		this.file = file;
		this.urlFile = urlFile;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getUrlFile() {
		return urlFile;
	}

	public void setUrlFile(String urlFile) {
		this.urlFile = urlFile;
	}

}
