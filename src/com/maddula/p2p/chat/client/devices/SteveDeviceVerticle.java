package com.maddula.p2p.chat.client.devices;

import com.maddula.p2p.chat.util.Util;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

public class SteveDeviceVerticle extends AbstractVerticle{
	JsonObject input;

	@Override
	public void start() throws Exception {
		System.out.println("Inside Steve's Device");	
			input = new JsonObject();
			input.put("type", "create_p2p_chat");
			input.put("from", "steve");
			input.put("to", "ron");
			input.put("message", "Hi Ronald!!");
			
			vertx.eventBus().send("chat.server.main", input, message -> {
				System.out.println("Message from " + input.getString("from") + " Delivered to Server (/)");
				new Util().sendMessage(message, vertx);

				
				
			/*	//Disconnect Chat with Ron
				input = new JsonObject();
				input.put("type", "destroy_p2p_chat");
				input.put("deploymentId", output.getString("deploymentId"));
				vertx.eventBus().send("chat.server.main", input);*/
				
			});
	}


}

