package com.example;

import java.io.Serializable;

public class InformationYoutobe implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String linkVideo;
	
	public String chanel;
	
	public String timeVideo;
	
	public String nameVideo;

	public InformationYoutobe(String linkVideo, String chanel, String timeVideo, String nameVideo) {
		super();
		this.linkVideo = linkVideo;
		this.chanel = chanel;
		this.timeVideo = timeVideo;
		this.nameVideo = nameVideo;
	}

	public InformationYoutobe() {
		super();
	}

	public String getLinkVideo() {
		return linkVideo;
	}

	public void setLinkVideo(String linkVideo) {
		this.linkVideo = linkVideo;
	}

	public String getChanel() {
		return chanel;
	}

	public void setChanel(String chanel) {
		this.chanel = chanel;
	}

	public String getTimeVideo() {
		return timeVideo;
	}

	public void setTimeVideo(String timeVideo) {
		this.timeVideo = timeVideo;
	}

	public String getNameVideo() {
		return nameVideo;
	}

	public void setNameVideo(String nameVideo) {
		this.nameVideo = nameVideo;
	}

	@Override
	public String toString() {
		return "InformationYoutobe" +"----"+ linkVideo +"-----"+ chanel +"------"+ timeVideo +"-------"+ nameVideo;
	}
	
	
	
}
 