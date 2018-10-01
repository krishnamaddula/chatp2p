package com.maddula.p2p.chat.app;

import com.maddula.p2p.chat.client.devices.DeviceControllerVerticle;
import com.maddula.p2p.chat.server.ChatControllerVerticle;

import io.vertx.core.Vertx;

public class App {

	public static void main(String[] args) throws Exception {
		
		Vertx vertx = Vertx.vertx();

		vertx.deployVerticle(new ChatControllerVerticle());
		Thread.sleep(1000);
		/*vertx.deployVerticle(new SteveDeviceVerticle());
		Thread.sleep(1000);
		vertx.deployVerticle(new RonDeviceVerticle());*/
		Thread.sleep(1000);
		vertx.deployVerticle(new DeviceControllerVerticle());
	}

}
