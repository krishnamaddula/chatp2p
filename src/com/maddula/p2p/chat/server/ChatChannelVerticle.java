package com.maddula.p2p.chat.server;


import io.vertx.core.AbstractVerticle;

public class ChatChannelVerticle extends AbstractVerticle{

	private String address;
	
	public ChatChannelVerticle(String address) {
		super();
		this.address = address;
	}
	
	@Override
	public void start() throws Exception {		
		vertx.eventBus().consumer(this.address, message -> {
			System.out.println(this.address + " received message.." + message.body());
		});
	}

}