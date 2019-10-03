package vn.vmgmedia.infochanel.process;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;

import vn.vmgmedia.infochanel.domain.InformationPlaylistChanelYoutobe;
import vn.vmgmedia.infochanel.untils.HandleHeaderRequest;
import vn.vmgmedia.infochanel.untils.YoutobeConstant;

public class GetPlaylistChanelYoutobe {
	
	public static final String LINK_CHANEL = "";
	
	
	public static void main(String[] args) {
		String linkChanel = "https://www.youtube.com/channel/UCnSs1c5P6lMMKUME5aZApiw/playlists";
		List<InformationPlaylistChanelYoutobe> listInforPlaylist = new ArrayList<InformationPlaylistChanelYoutobe>();
		
		GetPlaylistChanelYoutobe youtobe = new GetPlaylistChanelYoutobe();
		youtobe.getInforPlaylistChanel(linkChanel, listInforPlaylist);
		
		for (InformationPlaylistChanelYoutobe playlist : listInforPlaylist) {
			System.out.println(playlist.toString());
		}
		
		System.out.println(listInforPlaylist.size()+"===========");
	}
	
	/**
	 * @param String linkChanel youtobe
	 * @return void
	 * */
	public void getInforPlaylistChanel(String linkChanel, List<InformationPlaylistChanelYoutobe> listInforPlaylist) {
		
		String pageContinue = YoutobeConstant.PAGE_FIRST_PLAYLIST;
		
		String cToken = null;

		do {
			try {
				
				if (cToken != null) {
					linkChanel = "https://www.youtube.com/browse_ajax?ctoken="+cToken
							+"&continuation"+cToken
							+ "&itct="+pageContinue;
				} else {
					linkChanel = linkChanel + pageContinue;
				}
				
				URL url = new URL(linkChanel);
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
					if (pageContinue == YoutobeConstant.PAGE_FIRST_PLAYLIST) {
						cToken = headerRequest.getTokenFirst(jsonArrayParent);
						pageContinue =headerRequest. getPageFirstPlaylist(jsonArrayParent);
						getFirstInfoPlayList(jsonArrayParent, listInforPlaylist);
					} else {
						cToken = headerRequest.getContinueToken(jsonArrayParent);
						pageContinue = headerRequest.getPageContinue(jsonArrayParent);
						getContinueListPlaylistInfo(jsonArrayParent, listInforPlaylist);
					}
					System.out.println(cToken +"1111111111"+ pageContinue);
				
				}
			} catch (Exception e) {
				System.out.println("Get error");
				e.printStackTrace();
			}
		} while (pageContinue != null);
	}
	
	
	public static void getFirstInfoPlayList (JSONArray jsonArrayParent, List<InformationPlaylistChanelYoutobe> lisInfoPlaylist) {
		try {
			JSONArray listPlaylist = jsonArrayParent.getJSONObject(1)
					.getJSONObject("response")
					.getJSONObject("contents")
					.getJSONObject("twoColumnBrowseResultsRenderer")
					.getJSONArray("tabs")
					.getJSONObject(2)
					.getJSONObject("tabRenderer")
					.getJSONObject("content")
					.getJSONObject("sectionListRenderer")
					.getJSONArray("contents")
					.getJSONObject(0)
					.getJSONObject("itemSectionRenderer")
					.getJSONArray("contents")
					.getJSONObject(0)
					.getJSONObject("gridRenderer")
					.getJSONArray("items");
			
			for (int j = 0; j < listPlaylist.length(); j++) {
				InformationPlaylistChanelYoutobe playlist = new InformationPlaylistChanelYoutobe();
				
				playlist.setId(listPlaylist.getJSONObject(j).getJSONObject("gridPlaylistRenderer").getString("playlistId"));
				
				playlist.setTypePlaylist(YoutobeConstant.LIST_CREATED);
				
				playlist.setNamePlayList(listPlaylist.getJSONObject(j).getJSONObject("gridPlaylistRenderer").getJSONObject("title")
						.getJSONArray("runs").getJSONObject(0).getString("text"));
				
				lisInfoPlaylist.add(playlist);
			}	
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void getContinueListPlaylistInfo(JSONArray jsonArrayParent, List<InformationPlaylistChanelYoutobe> lisInfoPLaylist) {
		try {
			JSONArray listPlaylist = jsonArrayParent.getJSONObject(1)
					.getJSONObject("response")
					.getJSONObject("continuationContents")
					.getJSONObject("gridContinuation")
					.getJSONArray("items");
			
			for (int j = 0; j < listPlaylist.length(); j++) {
				InformationPlaylistChanelYoutobe playlist = new InformationPlaylistChanelYoutobe();
				
				playlist.setId(listPlaylist.getJSONObject(j).getJSONObject("gridPlaylistRenderer").getString("playlistId"));
				
				playlist.setTypePlaylist(YoutobeConstant.LIST_CREATED);
				
				playlist.setNamePlayList(listPlaylist.getJSONObject(j).getJSONObject("gridPlaylistRenderer").getJSONObject("title")
						.getJSONArray("runs").getJSONObject(0).getString("text"));
				
				lisInfoPLaylist.add(playlist);
			}
		} catch (Exception e) {
				// TODO: handle exception
		}
	}
}
