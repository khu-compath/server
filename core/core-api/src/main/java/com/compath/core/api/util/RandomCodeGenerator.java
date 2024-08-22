package com.compath.core.api.util;

import java.security.SecureRandom;
import java.util.Random;

public class RandomCodeGenerator {
	private static final String ALPHA_NUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final String NUMERIC = "0123456789";

	private static final Random random = new SecureRandom();

	public static String generateAlphanumericCode(int length) {
		return generateCode(length, ALPHA_NUMERIC);
	}

	public static String generateNumericCode(int length) {
		return generateCode(length, NUMERIC);
	}

	private static String generateCode(int length, String characterSet) {
		StringBuilder builder = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			int randomIndex = random.nextInt(characterSet.length());
			builder.append(characterSet.charAt(randomIndex));
		}
		return builder.toString();
	}
}
