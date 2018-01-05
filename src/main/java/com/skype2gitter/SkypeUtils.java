package com.skype2gitter;

import org.pmw.tinylog.Logger;

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

    public SkypeUtils() {
        this.login = System.getProperty(AppProperties.SKYPE_LOGIN);
        this.password = System.getProperty(AppProperties.SKYPE_PASSWORD);
        if (login.isEmpty() || login == null || password.isEmpty() || password == null) {
            Logger.error("Some of Skype properies are incorrect or missed.");
            System.exit(0);
        }
    }

    public void processSkypeMessages(GitterUtils gitterUtils) {
        try {
            Skype skype = new SkypeBuilder(login, password).withAllResources().build();
            skype.login();
            Logger.info("Skpype listener logged in");
            skype.getEventDispatcher().registerListener(new Listener() {
                @EventHandler
                public void onMessage(MessageReceivedEvent e) {
                    statisticsUtil.increaseReceivedMesagesAmount();
                    try {
                        String message = gitterUtils.generateGitterRequestBody(e.getMessage().getSender().getDisplayName(),
                                e.getMessage().getContent().toString());
                        gitterUtils.sendGitterMessage(message);
                    } catch (ConnectionException ex) {
                        Logger.error(ex);
                    }
                }
            });
            skype.subscribe();
        } catch (ConnectionException | NotParticipatingException | InvalidCredentialsException e) {
            Logger.error(e);
        }
    }
}
