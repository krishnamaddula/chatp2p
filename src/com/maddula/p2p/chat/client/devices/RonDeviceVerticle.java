package com.maddula.p2p.chat.client.devices;

import com.maddula.p2p.chat.util.Util;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

public class RonDeviceVerticle extends AbstractVerticle{
	
	JsonObject input;


	@Override
	public void start(Future<Void> future) throws Exception {
		System.out.println("Inside Ron's Device");
		
		input = new JsonObject();
		input.put("type", "create_p2p_chat");
		input.put("from", "ron");
		input.put("to", "steve");
		input.put("message", "Hi Steve!!");
			
		vertx.eventBus().send("chat.server.main", input, message -> {
			System.out.println("Message from " + input.getString("from") + " Delivered to Server (/)");
			new Util().sendMessage(message, vertx);
		});
	}
}
