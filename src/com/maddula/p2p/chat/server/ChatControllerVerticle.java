package com.maddula.p2p.chat.server;

import com.maddula.p2p.chat.util.Util;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.core.shareddata.SharedData;

public class ChatControllerVerticle extends AbstractVerticle{

	@Override
	public void start() throws Exception {			 
		vertx.eventBus().consumer("chat.server.main", message -> {
			JsonObject body = (JsonObject)message.body();
			switch (body.getString("type")) {
				case "create_p2p_chat":		
					createChatChannel(message, body);
					break;
				case "destroy_p2p_chat":		
					deleteChatChannel(message, body);
					break;
			}	
		});		
	}

	private void deleteChatChannel(Message<Object> message, JsonObject body) {
		vertx.undeploy(body.getString("deploymentId") , response -> {
			System.out.println("Remaining Verticles after closing chat:" + vertx.deploymentIDs());
			if(response.succeeded()) {
				body.put("success", true);
			}else {
				body.put("success", false);
			}
			message.reply(body);
		});
	}

	private void createChatChannel(Message<Object> message, JsonObject body) {
		String address = Util.generateChannelAddress(body.getString("from"), body.getString("to"));
		vertx.deployVerticle(new ChatChannelVerticle(address), response -> {
			body.put("address", address);
			body.put("deploymentId", response.result());
			message.reply(body);
		});
	}

}
