package com.maddula.p2p.chat.client.devices;

import com.maddula.p2p.chat.util.Util;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

public class DeviceControllerVerticle extends AbstractVerticle{

	@Override
	public void start() throws Exception {
		Router router = Router.router(vertx);
		router.route("/chat/from/:from/to/:to/message/:message").handler(routingContext -> {
			String from = routingContext.request().getParam("from");
			String to = routingContext.request().getParam("to");
			String message = routingContext.request().getParam("message");

			System.out.println("Message from " +from+ " Delivered to Server (/)");
			vertx.eventBus().send(Util.generateChannelAddress(from, to), message);
			System.out.println("Message from " + from + " Delivered to " + to + "(//)");
			
			routingContext.response().putHeader("Content-Type", "text/html").end(from + ": " + message);
		});
		vertx.createHttpServer().requestHandler(router::accept).listen(7777);
	}
}
