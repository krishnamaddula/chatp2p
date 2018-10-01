package com.maddula.p2p.chat.app;

import com.maddula.p2p.chat.client.devices.RonDeviceVerticle;
import com.maddula.p2p.chat.client.devices.SteveDeviceVerticle;
import com.maddula.p2p.chat.server.ChatControllerVerticle;

import io.vertx.core.Vertx;

public class App {

	public static void main(String[] args) {
		
		Vertx vertx = Vertx.vertx();

		vertx.deployVerticle(new ChatControllerVerticle());
		vertx.deployVerticle(new SteveDeviceVerticle());

		//vertx.deployVerticle(new RonDeviceVerticle());
	}

}
