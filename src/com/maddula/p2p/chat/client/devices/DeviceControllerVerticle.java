package com.maddula.p2p.chat.client.devices;

import com.maddula.p2p.chat.util.Util;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.core.shareddata.SharedData;
import io.vertx.ext.web.Router;

public class DeviceControllerVerticle extends AbstractVerticle{

	@Override
	public void start() throws Exception {
		Router router = Router.router(vertx);
		
		router.route("/chat/from/:from/to/:to/message/:message").handler(routingContext -> {
			String from = routingContext.request().getParam("from");
			String to = routingContext.request().getParam("to");
			String message = routingContext.request().getParam("message");
			
			SharedData sharedData = vertx.sharedData();
			LocalMap<Long,String> historyMap = sharedData.getLocalMap("chatHistory");
			historyMap.put(System.currentTimeMillis(), "From:"+ from + ":To:"+ to + "Message:"+ message);

			System.out.println("Message from " +from+ " Delivered to Server (/)");
			vertx.eventBus().send(Util.generateChannelAddress(from, to), message);
			System.out.println("Message from " + from + " Delivered to " + to + "(//)");
			
			routingContext.response().putHeader("Content-Type", "text/html").end(from + ": " + message);
		});
		
		router.route("/chat/type/:type/from/:from/to/:to/message/:message").handler(routingContext -> {
			JsonObject input = new JsonObject();
			input.put("type", routingContext.request().getParam("type"));
			input.put("from", routingContext.request().getParam("from"));
			input.put("to", routingContext.request().getParam("to"));
			input.put("message", routingContext.request().getParam("message"));
			
			SharedData sharedData = vertx.sharedData();
			LocalMap<Long,String> historyMap = sharedData.getLocalMap("chatHistory");
			historyMap.put(System.currentTimeMillis(), "From:"+ routingContext.request().getParam("from") + ":To:"+
					routingContext.request().getParam("to") + "Message:"+ routingContext.request().getParam("message"));

			vertx.eventBus().send("chat.server.main", input, message -> {
				System.out.println("Message from " + input.getString("from") + " Delivered to Server (/)");
				new Util().sendMessage(message, vertx);
				String deploymentId = ((JsonObject)message.result().body()).getString("deploymentId");
				 routingContext.response().putHeader("Content-Type", "text/html")
					.end(input.getString("from") + ": " + input.getString("message") + ":DeploymentId:" + deploymentId);
			});
			
		});
		
		
		router.route("/chat/type/:type/Id/:deploymentId").handler(routingContext -> {
			JsonObject input = new JsonObject();
			input.put("type", "destroy_p2p_chat");
			input.put("deploymentId", routingContext.request().getParam("deploymentId"));
			vertx.eventBus().send("chat.server.main", input, message -> {
				if(message.succeeded()) {
					System.out.println("Verticle Undeployed successfully");
					routingContext.response().putHeader("Content-Type", "text/html")
						.end("Remaining verticles::" + vertx.deploymentIDs());
				}
			});
		});
		
		
		router.route("/chat/ids").handler(routingContext -> {
			routingContext.response().putHeader("Content-Type", "text/html")
					.end("Remaining verticles::" + vertx.deploymentIDs());
		});

		router.route("/chat/history/from/:from/to/:to").handler(routingContext -> {
			SharedData sharedData = vertx.sharedData();
			LocalMap<Long,String> historyMap = sharedData.getLocalMap("chatHistory");
			routingContext.response().putHeader("Content-Type", "text/html").end(historyMap.toString());
		});
		
		vertx.createHttpServer().requestHandler(router::accept).listen(7777);
	}
}
