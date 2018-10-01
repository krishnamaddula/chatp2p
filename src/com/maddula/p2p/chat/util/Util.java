package com.maddula.p2p.chat.util;

import java.util.UUID;

import io.vertx.core.AsyncResult;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

public class Util {
	
	public static String generateUniqueAddress() {
		return UUID.randomUUID().toString();
	}
	
	
	public static String generateChannelAddress(String from, String to) {
		return new StringBuffer("chat.channel.from.")
				.append(from)
				.append(".to.")
				.append(to).toString();
	}
	
	
	public void sendMessage(AsyncResult<Message<Object>> message, Vertx vertx) {
		JsonObject output = (JsonObject)message.result().body();
		vertx.eventBus().send(output.getString("address"), output.getString("message"));
		System.out.println("Message from " + output .getString("from") + " Delivered to " + output .getString("to") + "(//)");
	}
	
}
