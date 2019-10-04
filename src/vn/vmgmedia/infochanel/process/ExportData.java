package vn.vmgmedia.infochanel.process;

import java.util.ArrayList;
import java.util.List;

import vn.vmgmedia.infochanel.domain.InformationPlaylistChanelYoutobe;
import vn.vmgmedia.infochanel.domain.InformationVideoYoutobe;

public class ExportData {
	private GetInfomationVideoChanelYoutobe getInfoVideo;
	
	private GetPlaylistChanelYoutobe getPlaylistInfo;
	
	private MappingVideoWithPlayList mappingInfo;
	
	private static List<InformationVideoYoutobe> listVideo ;
	
	private static List<InformationPlaylistChanelYoutobe> listPlaylist;
	
	public static void main(String[] args) {
		
		
		listVideo = new ArrayList<InformationVideoYoutobe>();
		
		listPlaylist = new ArrayList<InformationPlaylistChanelYoutobe>();
		
		

	}
	
	public void getListVideo(List<InformationVideoYoutobe> listVideo, List<InformationPlaylistChanelYoutobe> listPlaylist, String path) {
		getInfoVideo = new GetInfomationVideoChanelYoutobe();
		getPlaylistInfo = new GetPlaylistChanelYoutobe();
		listVideo = new ArrayList<InformationVideoYoutobe>();
		listPlaylist = new ArrayList<InformationPlaylistChanelYoutobe>();
		
	}
	
	public void exportJson() {
		
	}
	
	public void exportExel() {
		
	}
}
