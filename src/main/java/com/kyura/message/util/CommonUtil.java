package com.kyura.message.util;

import com.google.i18n.phonenumbers.Phonenumber;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;


public class CommonUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

	private static String key;
	private static String initVector;


	static {
		loadProperties();
	}

	private static void loadProperties() {
		InputStream input = null;
		String activeProfile = System.getProperty(Constant.SPRING_PROFILES_ACTIVE);
		try {
			String fileName = "application.properties";
			if(StringUtils.isNotBlank(activeProfile)) {
				fileName = "application-" + activeProfile + ".properties";
			}
			input = CommonUtil.class.getClassLoader().getResourceAsStream(fileName);
			Properties properties = new Properties();
			properties.load(input);
			CommonUtil.key = properties.getProperty("kyura.app.encrypt.key");
			CommonUtil.initVector = properties.getProperty("kyura.app.encrypt.initvector");

		} catch (IOException ex) {
			LOGGER.error("IOException: [{}]", ex);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException ex) {
					LOGGER.error("IOException: [{}]", ex);
				}
			}
		}
	}
	public static String generateCode(int numericLength, int alphabeticLength) {
		char[] charArr = new StringBuilder().append(randomNumeric(numericLength))
				.append(randomAlphabetic(alphabeticLength))
				.toString()
				.toCharArray();

		Character[] characterArr = ArrayUtils.toObject(charArr);

		List<Character> characterList = Arrays.asList(characterArr);

		Collections.shuffle(characterList);

		return characterList.stream().map(String::valueOf).collect(joining()).toUpperCase();
	}

	public static List<Sort.Order> parseOrders(List<String> strSorts) {
		List<Sort.Order> orders = new ArrayList<>();
		if(!CollectionUtils.isEmpty(strSorts)) {
			for (String s : strSorts) {
				if(s.contains(",") && s.split(",").length == 2) {
					String[] _sort = s.split(",");
					Sort.Direction direction = Sort.Direction.ASC.name().equalsIgnoreCase(_sort[1]) ? Sort.Direction.ASC : Sort.Direction.DESC;
					orders.add(new Sort.Order(direction, _sort[0]));
				}
			}
			return orders;
		}
		return Collections.EMPTY_LIST;
	}

	public static String encrypt(String value) {
		if(StringUtils.isBlank(value)) {
			return null;
		}
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(value.getBytes());
			return Base64.encodeBase64String(encrypted);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static String decrypt(String encrypted) {
		if(StringUtils.isEmpty(encrypted)) {
			return null;
		}
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

			return new String(original);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public static String[] parserE164Number(String e164Number) {
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		PhoneNumber phoneNumber = null;
		try {
			phoneNumber = phoneUtil.parse(e164Number, "");
		} catch (Exception e) {
			return null;
		}
		String countryCode = PhoneNumberUtil.getInstance().getRegionCodeForNumber(phoneNumber);
		String localNumber = phoneUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
		String[] result = {countryCode, localNumber};
		return result;

	}
}
