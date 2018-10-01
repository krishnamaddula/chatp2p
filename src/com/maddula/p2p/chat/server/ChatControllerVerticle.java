package com.maddula.p2p.chat.server;

import com.maddula.p2p.chat.util.Util;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

public class ChatControllerVerticle extends AbstractVerticle{

	@Override
	public void start() throws Exception {
		System.out.println("Inside Chat Controller");
		
		vertx.eventBus().consumer("chat.server.main", message -> {
			String address = Util.generateUniqueAddress();
			JsonObject body = (JsonObject)message.body();	
			
			System.out.println("input is"+ body);
			switch (body.getString("type")) {
				case "create_p2p_chat":		
					vertx.deployVerticle(new ChatChannelVerticle(address), response -> {
						System.out.println("Verticales deployed are :" + vertx.deploymentIDs());
						body.put("address", address);
						body.put("deploymentId", response.result());
						message.reply(body);
					});
					break;
				case "destroy_p2p_chat":		
					vertx.undeploy(body.getString("deploymentId") , response -> {
						System.out.println("Remaining Verticles after closing chat:" + vertx.deploymentIDs());
						if(response.succeeded()) {
							body.put("success", true);
						}else {
							body.put("success", false);
						}
						message.reply(body);
					});
					break;
			}	
		});		
	}

}
