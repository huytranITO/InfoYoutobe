package vn.vmgmedia.infochanel.domain;

import java.io.Serializable;
import java.util.List;

public class InformationPlaylistChanelYoutobe implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String id;
	
	public String typePlaylist;
	
	public String namePlayList;
	
	public List<InformationVideoYoutobe> lisVideoForPlayListChanel;

	public InformationPlaylistChanelYoutobe(String id, String typePlaylist, String namePlayList,
			List<InformationVideoYoutobe> lisVideoForPlayListChanel) {
		super();
		this.id = id;
		this.typePlaylist = typePlaylist;
		this.namePlayList = namePlayList;
		this.lisVideoForPlayListChanel = lisVideoForPlayListChanel;
	}

	public InformationPlaylistChanelYoutobe() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypePlaylist() {
		return typePlaylist;
	}

	public void setTypePlaylist(String typePlaylist) {
		this.typePlaylist = typePlaylist;
	}

	public String getNamePlayList() {
		return namePlayList;
	}

	public void setNamePlayList(String namePlayList) {
		this.namePlayList = namePlayList;
	}

	public List<InformationVideoYoutobe> getLisVideoForPlayListChanel() {
		return lisVideoForPlayListChanel;
	}

	public void setLisVideoForPlayListChanel(List<InformationVideoYoutobe> lisVideoForPlayListChanel) {
		this.lisVideoForPlayListChanel = lisVideoForPlayListChanel;
	}

	@Override
	public String toString() {
		return "InformationPlaylistChanelYoutobe [id=" + id + ", typePlaylist=" + typePlaylist + ", namePlayList="
				+ namePlayList + "]";
	}
	
	
}
