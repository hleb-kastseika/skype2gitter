package com.last_khajiit.skype2gitter;

import static spark.Spark.*;

public class Skype2gitter{
	public static void main(String[] args){
		final String username = args[0];
		final String password = args[1];
		final String gitterChatName = args[2];
		final String gitterToken = args[3];	
		final String skypeChatUrl = args[4];	
		
		port(Integer.parseInt(System.getenv("PORT")!=null?System.getenv("PORT"):"8080"));
		get("/", (req, res) -> getStatistics());		
		
		SkypeUtils skypeUtils = new SkypeUtils(username, password, skypeChatUrl);
		GitterUtils gitterUtils = new GitterUtils(gitterToken, gitterChatName);
		skypeUtils.processSkypeMessages(gitterUtils);
	}
	
	private static String getStatistics(){
		return "Statistics";
	}
}
