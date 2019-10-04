package vn.vmgmedia.infochanel.process;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;

import vn.vmgmedia.infochanel.domain.InformationVideoYoutobe;
import vn.vmgmedia.infochanel.untils.HandleHeaderRequest;
import vn.vmgmedia.infochanel.untils.YoutobeConstant;

public class GetInfomationVideoChanelYoutobe {
	
	private static String chanel = "";

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		GetInfomationVideoChanelYoutobe videChanelYoutobe = new GetInfomationVideoChanelYoutobe();
		String linkChanel = "https://www.youtube.com/user/VEVO";
		
		List<InformationVideoYoutobe> listInforVideo = new ArrayList<InformationVideoYoutobe>();
		
		videChanelYoutobe.getInforChanel(linkChanel, listInforVideo);
		
/*		for (InformationVideoYoutobe informationYoutobe : listInforVideo) {
			System.out.println(informationYoutobe.toString());
		}*/
		
		long endTime   = System.currentTimeMillis();;
		long totalTime = endTime - startTime;
		System.out.println(totalTime);
		System.out.println(listInforVideo.get(listInforVideo.size() - 1).toString());
		System.out.println(listInforVideo.size()+"--done!");
		
	}
	
	/**
	 * @param String linkChanel youtobe
	 * @return void
	 * */
	public void getInforChanel(String linkChanel, List<InformationVideoYoutobe> listInforVideo) {
		String pageContinue = YoutobeConstant.PAGE_FIRST;
		
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
				getConnect.setRequestProperty("x-youtube-client-version", "2.20191003.00.00");
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
					if (pageContinue == YoutobeConstant.PAGE_FIRST) {
						cToken = headerRequest.getTokenFirstContinue(jsonArrayParent);
						pageContinue =headerRequest.getPageFirstContinue(jsonArrayParent);
						chanel = jsonArrayParent.getJSONObject(1).getJSONObject("response").getJSONObject("microformat").getJSONObject("microformatDataRenderer").getString("title");
						getFirstListVideoInfo(jsonArrayParent, listInforVideo);
					} else {
						cToken = headerRequest.getContinueToken(jsonArrayParent);
						pageContinue = headerRequest.getPageContinue(jsonArrayParent);
						getContinueListVideoInfo(jsonArrayParent, listInforVideo);
					}
				}
			} catch (Exception e) {
				System.out.println("Get error");
				e.printStackTrace();
			}
		} while (pageContinue != null);
	}
	
	/** Get info video
	 * @param JSONArray json data
	 * @param List<InformationYoutobe> list video
	 * */
	public void getFirstListVideoInfo(JSONArray jsonArrayParent, List<InformationVideoYoutobe> lisInforVideo) {
		try {
			JSONArray listVideo = jsonArrayParent.getJSONObject(1)
					.getJSONObject("response")
					.getJSONObject("contents")
					.getJSONObject("twoColumnBrowseResultsRenderer")
					.getJSONArray("tabs")
					.getJSONObject(1)
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
			
			for (int j = 0; j < listVideo.length(); j++) {
				InformationVideoYoutobe youtobe = new InformationVideoYoutobe();
				youtobe.setLinkVideo(YoutobeConstant.LINK_YOUTOBE
						.concat(listVideo.getJSONObject(j)
								.getJSONObject("gridVideoRenderer")
								.getString("videoId")));
				
				youtobe.setChanel(chanel);
				
				youtobe.setNameVideo(listVideo.getJSONObject(j)
						.getJSONObject("gridVideoRenderer")
						.getJSONObject("title")
						.getString("simpleText"));
				
				youtobe.setTimeVideo(listVideo.getJSONObject(j)
						.getJSONObject("gridVideoRenderer")
						.getJSONArray("thumbnailOverlays")
						.getJSONObject(0)
						.getJSONObject("thumbnailOverlayTimeStatusRenderer")
						.getJSONObject("text")
						.getString("simpleText"));
				lisInforVideo.add(youtobe);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void getContinueListVideoInfo(JSONArray jsonArrayParent, List<InformationVideoYoutobe> lisInforVideo) {
		try {
			JSONArray listVideo = jsonArrayParent.getJSONObject(1)
					.getJSONObject("response")
					.getJSONObject("continuationContents")
					.getJSONObject("gridContinuation")
					.getJSONArray("items");
			
			for (int j = 0; j < listVideo.length(); j++) {
				InformationVideoYoutobe youtobe = new InformationVideoYoutobe();
				youtobe.setLinkVideo(YoutobeConstant.LINK_YOUTOBE
						.concat(listVideo.getJSONObject(j)
								.getJSONObject("gridVideoRenderer")
								.getString("videoId")));
				
				youtobe.setChanel(chanel);
				
				youtobe.setNameVideo(listVideo.getJSONObject(j)
						.getJSONObject("gridVideoRenderer")
						.getJSONObject("title")
						.getString("simpleText"));
				
				youtobe.setTimeVideo(listVideo.getJSONObject(j)
						.getJSONObject("gridVideoRenderer")
						.getJSONArray("thumbnailOverlays")
						.getJSONObject(0)
						.getJSONObject("thumbnailOverlayTimeStatusRenderer")
						.getJSONObject("text")
						.getString("simpleText"));
				lisInforVideo.add(youtobe);
			}
		} catch (Exception e) {
				// TODO: handle exception
		}
	}
}
