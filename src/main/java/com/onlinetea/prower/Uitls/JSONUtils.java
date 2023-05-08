package com.onlinetea.prower.Uitls;



import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class JSONUtils {
	private static Logger logger = LoggerFactory.getLogger(JSONUtils.class);
	private static ObjectMapper mapper = new ObjectMapper();
	static {
		mapper = new ObjectMapper();
		//优化json属性与class字段匹配不上报错的问题
		mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public static String toJSON(Object bean) {
		if (bean == null) {
			return null;
		}

		try {
			return mapper.writeValueAsString(bean);
		} catch (Exception e) {
			logger.info("JSON序列化出错，bean:{}", bean);
		}
		
		return null;
	}
	
	public static <T> T fromJSON(String json, Class<T> clz) {
		try {
			return mapper.readValue(json, clz);
		} catch (IOException e) {
			logger.info("JSON返序列化出错，json:{}, 反序列化类:{}, {}", json, clz, e.getMessage());
		}
		return null;
	}


	public static <T> T decode(String json, Class<T> clas) {
		if (json == null)
			return null;
		try {
			return mapper.readValue(json, clas);
		} catch (IOException e) {
			return null;
		}
	}

	public static <T> List<T> getList(String json, Class<T> clazz) {
		if (json == null)
			return null;
		TypeFactory t = TypeFactory.defaultInstance();
		try {
			return mapper.readValue(json, t.constructCollectionType(ArrayList.class, clazz));
		} catch (IOException e) {
			return null;
		}
	}

	public static <K, V> Map<K, V> getMap(String json, Class<K> keyType, Class<V> valueType) {
		if (StringUtils.isBlank(json)){
			return null;
		}
		TypeFactory t = TypeFactory.defaultInstance();
		try {
			return mapper.readValue(json, t.constructMapType(HashMap.class, keyType, valueType));
		} catch (IOException e) {
			return null;
		}
	}

	public static String encode(Object obj) {
		if (obj == null)
			return null;
		try {
			return mapper.writeValueAsString(obj);
		} catch (IOException e) {
			return null;
		}
	}

	public static String json2Yaml(String json){
		if (json==null) {
			return null;
		}
		Yaml yaml = new Yaml();
		Map<String, Object> map = (Map<String, Object>) yaml.load(json);
		String yamlStr = yaml.dumpAsMap(map);
		return yamlStr;
	}
}
