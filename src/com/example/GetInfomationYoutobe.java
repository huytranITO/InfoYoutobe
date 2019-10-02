package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;

public class GetInfomationYoutobe {
	
	private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");

	public static void main(String[] args) {
		String linkYoutobe = "https://www.youtube.com/watch?v=";
		String path = "https://www.youtube.com/user/VEVO/videos?pbj=1";
		try {
			URL url = new URL(path);
			HttpsURLConnection getConnect = (HttpsURLConnection) url.openConnection();
			getConnect.setRequestMethod("GET");
			getConnect.setRequestProperty("x-youtube-client-name", "1");
			getConnect.setRequestProperty("x-youtube-client-version", "2.20191001.08.02");
			getConnect.setDoOutput(true);
			
			int responseCode = getConnect.getResponseCode();
			
			if (responseCode == HttpsURLConnection.HTTP_OK) {
				
				BufferedReader bufferedReader = 
						new BufferedReader(new InputStreamReader(getConnect.getInputStream()));
				
				StringBuffer response = new StringBuffer();
				
				String readLine = null;
				int i=0;
				while ((readLine = bufferedReader.readLine()) != null) {
					i++;
					response.append(readLine);
				}
				
				bufferedReader.close();
				JSONArray jsonArrayParent = new JSONArray(response.toString());
				
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
				
				List<InformationYoutobe> listInfoChanel = new ArrayList<InformationYoutobe>();
				for (int j = 0; j < listVideo.length(); j++) {
					InformationYoutobe youtobe = new InformationYoutobe();
					youtobe.setLinkVideo(linkYoutobe
							.concat(listVideo.getJSONObject(j)
									.getJSONObject("gridVideoRenderer")
									.getString("videoId")));
					
					youtobe.setChanel("abc");
					
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
					listInfoChanel.add(youtobe);
					System.out.println(youtobe.toString());
				}
			System.out.println(listInfoChanel.size());
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Get data fail!");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String decodeUTF8(byte[] bytes) {
		return new String(bytes, UTF8_CHARSET);
	}
}
