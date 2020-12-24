package com.github.atta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Verifier {
	public static String verify(String inputText, String invTranslatedText) {
		String s = null;

		try {
			Process p = Runtime.getRuntime().exec(new String[] { "python3",
					"./src/main/java/com/github/atta/verify.py", inputText, invTranslatedText });

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			StringBuilder stringBuilder = new StringBuilder();
			while ((s = stdInput.readLine()) != null) {
				stringBuilder.append(s);
			}
			s = stringBuilder.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return s;
	}
}
