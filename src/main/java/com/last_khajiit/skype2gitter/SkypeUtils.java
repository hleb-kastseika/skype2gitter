package com.last_khajiit.skype2gitter;

import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.SkypeBuilder;
import com.samczsun.skype4j.events.EventHandler;
import com.samczsun.skype4j.events.Listener;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.exceptions.InvalidCredentialsException;
import com.samczsun.skype4j.exceptions.NotParticipatingException;

public class SkypeUtils {
	private String login;
	private String password;
	private StatisticsUtil statisticsUtil = StatisticsUtil.getInstance();	

	public SkypeUtils(String login, String password){
		this.login = login;
		this.password = password;
	}
	
	public void processSkypeMessages(GitterUtils gitterUtils){
		try{
			Skype skype = new SkypeBuilder(login, password).withAllResources().build();
			skype.login();
			System.out.println("Skpype listener logged in");
			skype.getEventDispatcher().registerListener(new Listener(){
				@EventHandler
				public void onMessage(MessageReceivedEvent e){
					statisticsUtil.increaseReceivedMesagesAmount();
					try{							
						String message = gitterUtils.generateGitterRequestBody(e.getMessage().getSender().getDisplayName(), 
								e.getMessage().getContent().toString());
						gitterUtils.sendGitterMessage(message);
					}catch(ConnectionException ex){
						System.out.println(ex.getMessage());
					}
				}
			});
			skype.subscribe();
		}catch(ConnectionException | NotParticipatingException | InvalidCredentialsException e){
            System.out.println(e.getMessage());
        }
	}
}
