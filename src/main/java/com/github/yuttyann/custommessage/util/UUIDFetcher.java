package com.github.yuttyann.custommessage.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

import com.github.yuttyann.custommessage.json.JsonMap;
import com.github.yuttyann.custommessage.json.WebJson;

public class UUIDFetcher implements Callable<Map<String, UUID>> {

	private static final String PROFILE_URL = "https://api.mojang.com/users/profiles/minecraft/";

	private String json;

	public UUIDFetcher(String name) {
		json = new WebJson(PROFILE_URL + name).getJsonToString();
	}

	@Override
	public Map<String, UUID> call() throws Exception {
		Map<String, UUID> uuidMap = new HashMap<String, UUID>();
		if (json != null) {
			Map<String, Object> jsonMap = new JsonMap(json).get(0);
			String id = (String) jsonMap.get("id");
			String name = (String) jsonMap.get("name");
			UUID uuid = fromString(id);
			uuidMap.put(name, uuid);
		}
		return uuidMap;
	}

	private UUID fromString(String id) {
		return UUID.fromString(id.substring(0, 8) + "-" + id.substring(8, 12) + "-" + id.substring(12, 16) + "-" + id.substring(16, 20) + "-" + id.substring(20, 32));
	}

	public static UUID getUniqueId(String name) throws Exception {
		return new UUIDFetcher(name).call().get(name);
	}
}
