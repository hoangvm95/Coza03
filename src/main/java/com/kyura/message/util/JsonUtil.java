package com.kyura.message.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;

public class JsonUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class); 
	
	/**
	 * Null serialize is used because else Gson will ignore all null fields.
	 */
	private static final ObjectMapper om = new ObjectMapper()
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	static {
		//SimpleDateFormat df = new SimpleDateFormat(DateUtil.YYYY_MM_DD_HH_MM_SS);
		//om.setDateFormat(df);
	}

	/**
	 * Made private because all methods are static and hence do not need object
	 * instantiation
	 */
	private JsonUtil() {
	}

	public static String toJson(Object obj) {
        try {
            return om.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOGGER.error("toJson exception [{}]", obj, e);
            return "";
        }
    }

	/**
	 * Converts a collection of objects using Google's Gson Package
	 * 
	 * @param objCol
	 * @return a json string array
	 */
	public static <T> String toJsonList(List<T> objCol) {
		return toJson(objCol);
	}

	public static <T> T fromJson(String json, Class<T> valueType) {
		try {
			if (StringUtils.isBlank(json)) {
				return valueType.getDeclaredConstructor().newInstance();
			}
			return om.readValue(json, valueType);
		} catch (IOException | InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
			LOGGER.error("fromJson exception json [{}] type [{}]", json, valueType, e);
			return null;
		}
	}
	public static <T> T convertJson(Object object, TypeReference<T> toValTypeReference) {
		try {
			return om.convertValue(object, toValTypeReference);
		} catch (Exception ex) {
			LOGGER.error("Exception when convert json string to object {}" + ex.getMessage());
			return null;
		}
	}


	public static <T> List<T> fromJsonArrayObj(String json, Class<T> valueType) {
		try {
			if (StringUtils.isBlank(json)) {
				return Collections.emptyList();
			}
			return om.readValue(json, om.getTypeFactory().constructCollectionType(List.class, valueType));
		} catch (IOException e) {
			LOGGER.error("fromJsonArrayObj exception json [{}] type [{}]", json, valueType, e);
			return Collections.emptyList();
		}
	}

	public static List<Long> fromJsonArrayLong(String json) {
		return fromJsonArrayObj(json, long.class);
	}
}
