package com.maddula.p2p.chat.client.devices;

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
			input.put("to", "ronald");
			vertx.eventBus().send("chat.server.main", input, message -> {
				System.out.println("Connection With Chat Server Acknowledgement");
				
				//Start sending messages to Ron
				JsonObject output = (JsonObject)message.result().body();
				vertx.eventBus().send(output.getString("address"), "Hi Ronald!!");

				
				
				//Disconnect Chat with Ron
				input = new JsonObject();
				input.put("type", "destroy_p2p_chat");
				input.put("deploymentId", output.getString("deploymentId"));
				vertx.eventBus().send("chat.server.main", input);
				
			});
	}

}

