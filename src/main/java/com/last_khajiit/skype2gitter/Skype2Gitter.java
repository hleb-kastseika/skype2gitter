package com.last_khajiit.skype2gitter;

import static spark.Spark.*;

public class Skype2Gitter{
	private static StatisticsUtil statisticsUtil = StatisticsUtil.getInstance();
	
	public static void main(String[] args){
		if(args.length < 4){
        	System.err.println("ERROR: Not all parameters have been specified!");
        	System.exit(0);
    	}
		final String username = args[0];
		final String password = args[1];
		final String gitterChatName = args[2];
		final String gitterToken = args[3];	

		SkypeUtils skypeUtils = new SkypeUtils(username, password);
		GitterUtils gitterUtils = new GitterUtils(gitterToken, gitterChatName);
		skypeUtils.processSkypeMessages(gitterUtils);
		
		port(Integer.parseInt(System.getenv("PORT")!=null?System.getenv("PORT"):"8080"));
		get("/", (req, res) -> statisticsUtil.getStatisticsAsString());
	}
}