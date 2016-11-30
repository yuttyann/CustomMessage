package com.github.yuttyann.custommessage.json;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.JsonElement;

public class JsonMap implements Map<Integer, Map<String, Object>> {

	private String json;
	private Map<Integer, Map<String, Object>> arrayMap;

	public JsonMap(String json) {
		this.json = json;
		try {
			arrayMap = new HashMap<Integer, Map<String, Object>>();
			Set<Entry<String, JsonElement>> entrySet;
			Map<String, Object> objectMap;
			JSONObject jsonObject;
			JSONParser jsonParser = new JSONParser();
			Object object = jsonParser.parse(json);
			if (json.startsWith("[") && json.endsWith("]")) {
				JSONArray jsonArray = (JSONArray) object;
				int i = 0;
				for (Object object2 : jsonArray) {
					if (object2 == null || object2.equals("")) {
						continue;
					}
					objectMap = new HashMap<String, Object>();
					jsonObject = (JSONObject) jsonParser.parse(object2.toString());
					entrySet = entrySet(jsonObject);
					for(Entry<String, JsonElement> entry : entrySet) {
						objectMap.put(entry.getKey(), entry.getValue());
					}
					arrayMap.put(i, objectMap);
					i++;
				}
			} else {
				objectMap = new HashMap<String, Object>();
				jsonObject = (JSONObject) object;
				entrySet = entrySet(jsonObject);
				for(Entry<String, JsonElement> entry : entrySet) {
					objectMap.put(entry.getKey(), entry.getValue());
				}
				arrayMap.put(0, objectMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private Set<Entry<String, JsonElement>> entrySet(JSONObject JsonObject) {
		return JsonObject.entrySet();
	}

	public JSONArray getJsonArray(Object object, Object key) {
		JSONObject jsonObject = (JSONObject) object;
		return (JSONArray) jsonObject.get(key);
	}

	public JSONArray getJsonArray(JSONObject object, Object key) {
		return (JSONArray) object.get(key);
	}

	public JSONArray getJsonArray(Object object) {
		return (JSONArray) object;
	}

	public Map<Integer, Map<String, Object>> getMap() {
		return arrayMap;
	}

	public String getJson() {
		return json;
	}

	public int size(int index) {
		return arrayMap.get(index).size();
	}

	public boolean isEmpty(int index) {
		return arrayMap.get(index).isEmpty();
	}

	public boolean containsKey(int index, Object key) {
		return arrayMap.get(index).containsKey(key);
	}

	public boolean containsValue(int index, Object value) {
		return arrayMap.get(index).containsValue(value);
	}

	public Object get(int index, Object key) {
		return arrayMap.get(index).get(key);
	}

	public Object put(int index, String key, Object value) {
		return arrayMap.get(index).put(key, value);
	}

	public Object remove(int index, Object key) {
		return arrayMap.get(index).remove(key);
	}

	public void putAll(int index, Map<? extends String, ? extends Object> m) {
		arrayMap.get(index).putAll(m);
	}

	public void clear(int index) {
		arrayMap.get(index).clear();
	}

	public Set<String> keySet(int index) {
		return arrayMap.get(index).keySet();
	}

	public Collection<Object> values(int index) {
		return arrayMap.get(index).values();
	}

	public Set<Entry<String, Object>> entrySet(int index) {
		return arrayMap.get(index).entrySet();
	}

	@Override
	public int size() {
		return arrayMap.size();
	}

	@Override
	public boolean isEmpty() {
		return arrayMap.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return arrayMap.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return arrayMap.containsValue(value);
	}

	@Override
	public Map<String, Object> get(Object key) {
		return arrayMap.get(key);
	}

	@Override
	public Map<String, Object> put(Integer key, Map<String, Object> value) {
		return arrayMap.put(key, value);
	}

	@Override
	public Map<String, Object> remove(Object key) {
		return arrayMap.remove(key);
	}

	@Override
	public void putAll(Map<? extends Integer, ? extends Map<String, Object>> m) {
		arrayMap.putAll(m);
	}

	@Override
	public void clear() {
		arrayMap.clear();
	}

	@Override
	public Set<Integer> keySet() {
		return arrayMap.keySet();
	}

	@Override
	public Collection<Map<String, Object>> values() {
		return arrayMap.values();
	}

	@Override
	public Set<Entry<Integer, Map<String, Object>>> entrySet() {
		return arrayMap.entrySet();
	}
}
