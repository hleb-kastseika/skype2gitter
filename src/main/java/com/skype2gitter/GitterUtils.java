package com.skype2gitter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.pmw.tinylog.Logger;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GitterUtils {
    private String gitterToken;
    private String gitterChatName;
    private StatisticsUtil statisticsUtil = StatisticsUtil.getInstance();
    private static final int OK_CODE = 200;

    public GitterUtils() {
        this.gitterToken = System.getProperty("gitter.token");
        this.gitterChatName = System.getProperty("gitter.chatname");
        if (gitterToken.isEmpty() || gitterToken == null || gitterChatName.isEmpty() || gitterChatName == null) {
            Logger.error("Some of Gitter properies are incorrect or missed.");
            System.exit(0);
        }
    }

    public String getGitterRoomId(String gitterRoomName) {
        String id = "";
        try {
            String url = "https://api.gitter.im/v1/rooms?access_token=" + gitterToken;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            if (con.getResponseCode() == OK_CODE) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                JsonParser jsonParser = new JsonParser();
                JsonArray responseArray = (JsonArray) jsonParser.parse(response.toString());
                if (responseArray.size() > 0) {
                    for (int i = 0; i < responseArray.size(); i++) {
                        JsonObject roomNode = (JsonObject) responseArray.get(i);
                        if (gitterRoomName.equals(roomNode.get("name").toString().replaceAll("\"", ""))) {
                            id = roomNode.get("id").toString().replaceAll("\"", "");
                        }
                    }
                }
            } else {
                Logger.error(con.getResponseCode() + con.getResponseMessage());
            }
        } catch (IOException ex) {
            Logger.error(ex);
        }
        return id;
    }

    public void sendGitterMessage(String message) {
        try {
            String roomId = getGitterRoomId(gitterChatName);
            if (!roomId.isEmpty()) {
                String url = "https://api.gitter.im/v1/rooms/" + roomId + "/chatMessages";
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                con.setRequestProperty("Accept", "application/json");
                con.setRequestProperty("Authorization", "Bearer " + gitterToken);
                con.setDoOutput(true);
                DataOutputStream os = new DataOutputStream(con.getOutputStream());
                os.write(message.getBytes("UTF-8"));
                os.flush();
                os.close();
                if (con.getResponseCode() == OK_CODE) statisticsUtil.increaseSentMesagesAmount();
            } else {
                Logger.error("Room ID is empty! Please, check gitter.chatname property value");
            }
        } catch (IOException ex) {
            Logger.error(ex);
        }
    }

    public String generateGitterRequestBody(String senderName, String message) {
        String formatedMessage = "";
        try {
            formatedMessage = "{\"text\":\"" + senderName.replace("\"", "\\\"")
                    + " said in Skype:\\n>" + message.replace("\\", "\\\\").replace("\r\n", "\\n>").replace("\"", "\\\"") + "\"}";
            formatedMessage = formatedMessage.replace("<b>", "**").replace("</b>", "**").replace("<i>", "*")
                    .replace("</i>", "*").replace("<s>", "~~").replace("</s>", "~~").replace("<pre>", "```")
                    .replace("</pre>", "```").replaceAll("<a[^>]*>(.*?)</a>", "$1");
            return formatedMessage;
        } catch (Exception ex) {
            Logger.error(ex);
        }
        return formatedMessage;
    }
}
