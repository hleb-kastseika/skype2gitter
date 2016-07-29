package com.last_khajiit.skype2gitter;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.pmw.tinylog.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GitterUtils{
	private String gitterToken;
	private String gitterChatName;
	private StatisticsUtil statisticsUtil = StatisticsUtil.getInstance();
	private static final int OK_CODE = 200;	

	public GitterUtils(String gitterToken, String gitterChatName){
		this.gitterToken = gitterToken;
		this.gitterChatName = gitterChatName;
	}

	public String getGitterRoomId(String gitterRoomName){
		String id = "";
		try{
			String url = "https://api.gitter.im/v1/rooms?access_token="+gitterToken+"&q="+gitterRoomName;
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			if(con.getResponseCode()==OK_CODE){
				BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();				
				JsonParser jsonParser = new JsonParser();
				JsonObject mainObject = (JsonObject)jsonParser.parse(response.toString());
				JsonArray responseArray = mainObject.getAsJsonArray("results");
				if(responseArray.size() != 0){
					id = responseArray.get(0).getAsJsonObject().get("id").toString();
				}
			}else{
				Logger.error(con.getResponseCode() + con.getResponseMessage());
			}						
		}catch(IOException ex){
			Logger.error(ex);
		}
		return id;
	}
	
	public void sendGitterMessage(String message){
		try{
			String url = "https://api.gitter.im/v1/rooms/"+getGitterRoomId(gitterChatName)+"/chatMessages";
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestProperty("Authorization", "Bearer "+gitterToken);
			con.setDoOutput(true);
			DataOutputStream os = new DataOutputStream(con.getOutputStream());
			os.write(message.getBytes("UTF-8"));
			os.flush();
			os.close();
			if(con.getResponseCode()==OK_CODE) statisticsUtil.increaseSentMesagesAmount();
		}catch(IOException ex){
			Logger.error(ex);
		}
	}
	
	public String generateGitterRequestBody(String senderName, String message){
	    String formatedMessage = "";
		try{
			formatedMessage = "{\"text\":\"" + senderName.replace("\"","\\\"") 
					+ " said in Skype:\\n>" + message.replace("\\", "\\\\").replace("\r\n", "\\n>").replace("\"","\\\"") + "\"}";
			formatedMessage = formatedMessage.replace("<b>","**").replace("</b>","**").replace("<i>","*")
					.replace("</i>","*").replace("<s>","~~").replace("</s>","~~").replace("<pre>","```")
					.replace("</pre>","```").replaceAll("<a[^>]*>(.*?)</a>", "$1");
			return formatedMessage;
		}catch(Exception ex) {
			Logger.error(ex);
	    }
		return formatedMessage;
	}
}
