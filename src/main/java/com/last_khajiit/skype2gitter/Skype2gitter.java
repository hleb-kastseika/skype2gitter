package com.last_khajiit.skype2gitter;

import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.SkypeBuilder;
import com.samczsun.skype4j.events.Listener;
import com.samczsun.skype4j.events.EventHandler;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.exceptions.NotParticipatingException;
import com.samczsun.skype4j.exceptions.InvalidCredentialsException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.DataOutputStream;
import java.io.IOException;

public class Skype2gitter{		
	public static void main(String[] args){
		final String username = args[0];
		final String password = args[1];
		final String gittetChatId = args[2];
		final String gitterToken = args[3];		
		/*
		final String gitterChatName = args[3]; //TODO implement searaching gitter-chats
		final String skypeChat = ""; //TODO implement searching skype-chats
		*/
		try {
			Skype skype = new SkypeBuilder(username, password).withAllResources().build();
			skype.login();
			System.out.println("Logged in");
			skype.getEventDispatcher().registerListener(new Listener() {
			  @EventHandler
			  public void onMessage(MessageReceivedEvent e){
				try {
					String message = generateGitterRequestBody(e.getMessage().getSender().getDisplayName(), 
							e.getMessage().getContent().toString());
					System.out.println(message);
					sendGitterMessage(gitterToken, gittetChatId, message);
				}catch(ConnectionException ex) {
					System.out.println(ex.getMessage());
				}
			  }
			});
			skype.subscribe();
		}catch(ConnectionException | NotParticipatingException | InvalidCredentialsException e) {
            System.out.println(e.getMessage());
        }
	}
	
	private static void sendGitterMessage(String gitterToken, String gittetChatId, String message){
		try {
			String url = "https://api.gitter.im/v1/rooms/"+gittetChatId+"/chatMessages";
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
			int responseCode = con.getResponseCode();
			System.out.println("Response Code : " + responseCode);
		}catch(IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	private static String generateGitterRequestBody(String senderName, String message){
		String formatedMessage = "{\"text\":\"" + senderName.replace("\"","\\\"") 
				+ " says:\\n>" + message.replace("\"","\\\"") + "\"}";
		formatedMessage = formatedMessage.replace("<b>","**").replace("</b>","**").replace("<i>","*")
				.replace("</i>","*").replace("<s>","~~").replace("</s>","~~").replace("<pre>","```")
				.replace("</pre>","```").replace("\n", " ").replace("\r", " ");	
		//message.replace("</a>","```");		
		return formatedMessage;
	}
}
