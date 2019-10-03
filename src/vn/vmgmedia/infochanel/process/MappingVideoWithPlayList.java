package vn.vmgmedia.infochanel.process;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONException;

import vn.vmgmedia.infochanel.domain.InformationPlaylistChanelYoutobe;
import vn.vmgmedia.infochanel.domain.InformationVideoYoutobe;
import vn.vmgmedia.infochanel.untils.HandleHeaderRequest;
import vn.vmgmedia.infochanel.untils.YoutobeConstant;

public class MappingVideoWithPlayList {
	
	public static void main(String[] args) {
		String linkChanel = "https://www.youtube.com/user/VEVO/playlists";
		
		GetPlaylistChanelYoutobe playlistChanelYoutobe = new GetPlaylistChanelYoutobe();
		
		List<InformationPlaylistChanelYoutobe> listPlaylist = new ArrayList<InformationPlaylistChanelYoutobe>();
		
		playlistChanelYoutobe.getInforPlaylistChanel(linkChanel, listPlaylist);
		
		MappingVideoWithPlayList mappingVideoWithPlayList = new MappingVideoWithPlayList();
		
		
		mappingVideoWithPlayList.mappingVideoBaseOnPlaylist(listPlaylist);
		
		for (InformationPlaylistChanelYoutobe informationPlaylistChanelYoutobe : listPlaylist) {
			System.out.println(informationPlaylistChanelYoutobe.namePlayList +"===="+ informationPlaylistChanelYoutobe.getLisVideoForPlayListChanel().size());
		}
	}
	
	public void mappingVideoBaseOnPlaylist(List<InformationPlaylistChanelYoutobe> listPlaylist) {
		
		for (InformationPlaylistChanelYoutobe informationPlaylistChanelYoutobe : listPlaylist) {
			mapping(informationPlaylistChanelYoutobe);
		}
	}
	
	public void mapping(InformationPlaylistChanelYoutobe playlist) {
		String linkPlaylist = YoutobeConstant.LINK_PLAYLIST;
		
		String pageContinue = YoutobeConstant.FIRST_PAGE;
		
		String cToken = null;
		try {
			do {

				if (cToken != null) {
					linkPlaylist = "https://www.youtube.com/browse_ajax?ctoken="+cToken
							+"&continuation"+cToken
							+ "&itct="+pageContinue;
				} else {
					linkPlaylist = linkPlaylist + playlist.id + pageContinue;
				}
				
				URL url = new URL(linkPlaylist);
				HttpsURLConnection getConnect = (HttpsURLConnection) url.openConnection();
				getConnect.setRequestMethod("GET");
				getConnect.setRequestProperty("x-youtube-client-name", "1");
				getConnect.setRequestProperty("x-youtube-client-version", "2.20191002.00.00");
				getConnect.setDoOutput(true);
				
				int responseCode = getConnect.getResponseCode();
				if (responseCode == HttpsURLConnection.HTTP_OK) {
					
					BufferedReader bufferedReader = 
							new BufferedReader(new InputStreamReader(getConnect.getInputStream()));
					
					StringBuffer response = new StringBuffer();
					
					String readLine = null;
					while ((readLine = bufferedReader.readLine()) != null) {
						
						response.append(readLine);
					}
					
					bufferedReader.close();
					
					JSONArray jsonArrayParent = new JSONArray(response.toString());
					
					HandleHeaderRequest headerRequest = new HandleHeaderRequest();
					// Get info video to list
					if (pageContinue == YoutobeConstant.FIRST_PAGE) {
						cToken = headerRequest.getTokenFirst(jsonArrayParent);
						pageContinue = getPageFirst(jsonArrayParent);
						playlist.setLisVideoForPlayListChanel(getFirstListVideoInfo(jsonArrayParent, playlist.getLisVideoForPlayListChanel()));
					} else {
						cToken = getToken(jsonArrayParent);
						pageContinue = getPage(jsonArrayParent);
						playlist.setLisVideoForPlayListChanel(getListVideoInfo(jsonArrayParent, playlist.getLisVideoForPlayListChanel()));
					}
					
				}
				
			} while (pageContinue != null);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	public List<InformationVideoYoutobe> getFirstListVideoInfo(JSONArray jsonArrayParent, List<InformationVideoYoutobe> lisInforVideo) {
		lisInforVideo = new ArrayList<InformationVideoYoutobe>();
		try {
			JSONArray listVideo = jsonArrayParent.getJSONObject(1)
					.getJSONObject("response")
					.getJSONObject("contents")
					.getJSONObject("twoColumnBrowseResultsRenderer")
					.getJSONArray("tabs")
					.getJSONObject(0)
					.getJSONObject("tabRenderer")
					.getJSONObject("content")
					.getJSONObject("sectionListRenderer")
					.getJSONArray("contents")
					.getJSONObject(0)
					.getJSONObject("itemSectionRenderer")
					.getJSONArray("contents")
					.getJSONObject(0)
					.getJSONObject("playlistVideoListRenderer")
					.getJSONArray("contents");
			
			for (int j = 0; j < listVideo.length(); j++) {
				InformationVideoYoutobe youtobe = new InformationVideoYoutobe();
				youtobe.setId(j);
				
				youtobe.setLinkVideo(YoutobeConstant.LINK_YOUTOBE
						.concat(listVideo.getJSONObject(j)
								.getJSONObject("playlistVideoRenderer")
								.getString("videoId")));
				
				youtobe.setChanel("VEVO");
				
				try {
				youtobe.setNameVideo(listVideo.getJSONObject(j)
						.getJSONObject("playlistVideoRenderer")
						.getJSONObject("title")
						.getString("simpleText"));
				} catch (Exception e) {
					continue;
				}
				
				youtobe.setTimeVideo(listVideo.getJSONObject(j)
						.getJSONObject("playlistVideoRenderer")
						.getJSONArray("thumbnailOverlays")
						.getJSONObject(0)
						.getJSONObject("thumbnailOverlayTimeStatusRenderer")
						.getJSONObject("text")
						.getString("simpleText"));
				lisInforVideo.add(youtobe);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lisInforVideo;
	}
	
	
	public List<InformationVideoYoutobe> getListVideoInfo(JSONArray jsonArrayParent, List<InformationVideoYoutobe> lisInforVideo) {
		lisInforVideo = new ArrayList<InformationVideoYoutobe>();
		try {
			JSONArray listVideo = jsonArrayParent.getJSONObject(1)
					.getJSONObject("response")
					.getJSONObject("continuationContents")
					.getJSONObject("playlistVideoListContinuation")
					.getJSONArray("contents");
			
			for (int j = 0; j < listVideo.length(); j++) {
				InformationVideoYoutobe youtobe = new InformationVideoYoutobe();
				youtobe.setId(j);
				
				youtobe.setLinkVideo(YoutobeConstant.LINK_YOUTOBE
						.concat(listVideo.getJSONObject(j)
								.getJSONObject("playlistVideoRenderer")
								.getString("videoId")));
				
				youtobe.setChanel("VEVO");
				
				youtobe.setNameVideo(listVideo.getJSONObject(j)
						.getJSONObject("playlistVideoRenderer")
						.getJSONObject("title")
						.getJSONObject("simpleText").toString());
				
				youtobe.setTimeVideo(listVideo.getJSONObject(j)
						.getJSONObject("playlistVideoRenderer")
						.getJSONArray("thumbnailOverlays")
						.getJSONObject(0)
						.getJSONObject("thumbnailOverlayTimeStatusRenderer")
						.getJSONObject("text")
						.getJSONObject("simpleText").toString());
				lisInforVideo.add(youtobe);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return lisInforVideo;
	}
	
	
	public String getPageFirst(JSONArray jsonArrayParent) {
		try {
			return jsonArrayParent.getJSONObject(1)
					.getJSONObject("response")
					.getJSONObject("contents")
					.getJSONObject("twoColumnBrowseResultsRenderer")
					.getJSONArray("tabs")
					.getJSONObject(0)
					.getJSONObject("tabRenderer")
					.getJSONObject("content")
					.getJSONObject("sectionListRenderer")
					.getJSONArray("contents")
					.getJSONObject(0)
					.getJSONObject("itemSectionRenderer")
					.getJSONArray("contents")
					.getJSONObject(0)
					.getJSONObject("playlistVideoListRenderer")
					.getJSONArray("continuations")
					.getJSONObject(0)
					.getJSONObject("nextContinuationData")
					.getString("clickTrackingParams");
		} catch (JSONException e) {
			return null;
		}
	}
	
	public String getTokenFirst(JSONArray jsonArrayParent) {
		try {
			return jsonArrayParent.getJSONObject(1)
					.getJSONObject("response")
					.getJSONObject("contents")
					.getJSONObject("twoColumnBrowseResultsRenderer")
					.getJSONArray("tabs")
					.getJSONObject(0)
					.getJSONObject("tabRenderer")
					.getJSONObject("content")
					.getJSONObject("sectionListRenderer")
					.getJSONArray("contents")
					.getJSONObject(0)
					.getJSONObject("itemSectionRenderer")
					.getJSONArray("contents")
					.getJSONObject(0)
					.getJSONObject("playlistVideoListRenderer")
					.getJSONArray("continuations")
					.getJSONObject(0)
					.getJSONObject("nextContinuationData")
					.getString("continuation");
		} catch (JSONException e) {
			return null;
		}
	}
	
	public String getToken(JSONArray jsonArrayParent) {
		try {
			return jsonArrayParent.getJSONObject(1)
					.getJSONObject("response")
					.getJSONObject("continuationContents")
					.getJSONObject("gridContinuation")
					.getJSONArray("continuations")
					.getJSONObject(0)
					.getJSONObject("nextContinuationData")
					.getString("continuation");
		} catch (Exception e) {
			return null;
		}
	}
	
	public String getPage(JSONArray jsonArrayParent) {
		try {
			return jsonArrayParent.getJSONObject(1)
					.getJSONObject("response")
					.getJSONObject("continuationContents")
					.getJSONObject("gridContinuation")
					.getJSONArray("continuations")
					.getJSONObject(0)
					.getJSONObject("nextContinuationData")
					.getString("clickTrackingParams");
		} catch (Exception e) {
			return null;
		}
	}
}
