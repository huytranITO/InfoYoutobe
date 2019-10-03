package vn.vmgmedia.infochanel.domain;

import java.io.Serializable;

public class InformationVideoYoutobe implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int id;
	
	public String linkVideo;
	
	public String chanel;
	
	public String timeVideo;
	
	public String nameVideo;
	
	public String playList;

	

	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
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



	public String getPlayList() {
		return playList;
	}



	public void setPlayList(String playList) {
		this.playList = playList;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public InformationVideoYoutobe() {
		super();
	}



	public InformationVideoYoutobe(int id, String linkVideo, String chanel, String timeVideo, String nameVideo,
			String playList) {
		super();
		this.id = id;
		this.linkVideo = linkVideo;
		this.chanel = chanel;
		this.timeVideo = timeVideo;
		this.nameVideo = nameVideo;
		this.playList = playList;
	}



	@Override
	public String toString() {
		return "InformationYoutobe" +"----"+ linkVideo +"-----"+ chanel +"------"+ timeVideo +"-------"+ nameVideo;
	}
	
	
	
}
 