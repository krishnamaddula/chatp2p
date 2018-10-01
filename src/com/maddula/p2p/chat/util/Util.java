package com.maddula.p2p.chat.util;

import java.util.UUID;
import java.util.function.Function;

import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

public class Util {
	
	public static String generateUniqueAddress() {
		return UUID.randomUUID().toString();
	}
}
