package com.last_khajiit.skype2gitter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class StatisticsUtil{
    
	private static StatisticsUtil _instance = null;
	private LocalDateTime startTime;
	private long receivedMesagesAmount = 0;
	private long sentMesagesAmount  = 0;
	
	private StatisticsUtil(){
		this.startTime = LocalDateTime.now();
	}

	public static StatisticsUtil getInstance(){
		if (_instance == null)
			_instance = new StatisticsUtil();
		return _instance;
	}
	
	public String getStatisticsAsString(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
		return "Start time: " + startTime.format(formatter)
				+"<br/>Received mesages: " + receivedMesagesAmount
				+"<br/>Sent mesages: " + sentMesagesAmount;
	}

	public void increaseReceivedMesagesAmount(){
		receivedMesagesAmount++;
	}
	
	public void increaseSentMesagesAmount(){
		sentMesagesAmount++;
	}
}
