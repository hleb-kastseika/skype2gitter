package com.last_khajiit.skype2gitter;

import com.samczsun.skype4j.Skype;
import com.samczsun.skype4j.SkypeBuilder;
import com.samczsun.skype4j.events.Listener;
import com.samczsun.skype4j.events.EventHandler;
import com.samczsun.skype4j.events.chat.message.MessageReceivedEvent;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.exceptions.NotParticipatingException;
import com.samczsun.skype4j.exceptions.InvalidCredentialsException;

public class Skype2gitter{		
	public static void main(String[] args){
		final String username = args[0];
		final String password = args[1];
		try {
			Skype skype = new SkypeBuilder(username, password).withAllResources().build();
			skype.login();
			System.out.println("Logged in");
			skype.getEventDispatcher().registerListener(new Listener() {
			  @EventHandler
			  public void onMessage(MessageReceivedEvent e) {
				try {
					System.out.println("Got message: " + e.getMessage().getContent() + " sent by " + e.getMessage().getSender().getDisplayName());
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
}
