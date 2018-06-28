package com.twitterwrapper.domain;

import java.util.List;

public class Tuite {
	
	private String profile;

	private String textoTuite;

	private List<AttachmentTuite> imgsFiles;
	
	private List<AttachmentTuite> vidsFiles;

	public Tuite() {

	}

	public Tuite(String profile, String textoTuite, List<AttachmentTuite> imgsFiles, List<AttachmentTuite> vidsFiles) {
		super();
		this.profile = profile;
		this.textoTuite = textoTuite;
		this.imgsFiles = imgsFiles;
		this.vidsFiles = vidsFiles;
	}

	
	@Override
	public String toString() {
		String string = "";
		if(this.profile!=null)
			string+=this.profile;
		if(this.textoTuite!=null)
			string+=this.textoTuite;
		if(this.imgsFiles!=null && this.imgsFiles.size()>0) {
			for(AttachmentTuite t: this.imgsFiles) {
				if(t!=null && t.getUrlFile()!=null)
					string+="["+t.getUrlFile()+"]";
			}
		}

		if(this.vidsFiles!=null && this.vidsFiles.size()>0) {
			for(AttachmentTuite t: this.vidsFiles) {
				if(t!=null && t.getUrlFile()!=null)
					string+="["+t.getUrlFile()+"]";
			}
		}
		return string;
	}

	public String getTextoTuite() {
		return textoTuite;
	}

	public void setTextoTuite(String textoTuite) {
		this.textoTuite = textoTuite;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}


	public List<AttachmentTuite> getImgsFiles() {
		return imgsFiles;
	}


	public void setImgsFiles(List<AttachmentTuite> imgsFiles) {
		this.imgsFiles = imgsFiles;
	}


	public List<AttachmentTuite> getVidsFiles() {
		return vidsFiles;
	}


	public void setVidsFiles(List<AttachmentTuite> vidsFiles) {
		this.vidsFiles = vidsFiles;
	}

}
