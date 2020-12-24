package com.github.atta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Verifier {
	public static String verify(String inputText, String invTranslatedText) {
		Process process;

		File f = new File("in.txt");
		FileOutputStream fos1 = new FileOutputStream(f);
		OutputStreamWriter dos1 = new OutputStreamWriter(fos1);
		dos1.write(inputText);
		dos1.write(invTranslatedText);
		dos1.close();

		try {
			process = Runtime.getRuntime().exec("python ComputeVerify.py");
			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			String tmp = null;
			while ((line = in.readLine()) != null) {
				tmp = line;
			}
			in.close();
			process.waitFor();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return tmp;
	}
}
