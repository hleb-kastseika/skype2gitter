package com.last_khajiit.skype2gitter;

import static spark.Spark.*;

public class Skype2Gitter{
	public static void main(String[] args){
		final String username = args[0];
		final String password = args[1];
		final String gitterChatName = args[2];
		final String gitterToken = args[3];	

		port(Integer.parseInt(System.getenv("PORT")!=null?System.getenv("PORT"):"8080"));
		get("/", (req, res) -> getStatistics());		

		SkypeUtils skypeUtils = new SkypeUtils(username, password);
		GitterUtils gitterUtils = new GitterUtils(gitterToken, gitterChatName);
		skypeUtils.processSkypeMessages(gitterUtils);
	}
	
	private static String getStatistics(){
		return "Statistics";
	}
}
