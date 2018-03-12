package de.hubsau.tryjump.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.UUID;

public class UUIDFatcher {

	public static UUID getUUID(String playername) {

		try {
			String output = callURL("https://api.mojang.com/users/profiles/minecraft/" + playername);

			StringBuilder result = new StringBuilder();

			readData(output, result);

			String u = result.toString();

			String uuid = "";

			for (int i = 0; i <= 31; i++) {
				uuid = uuid + u.charAt(i);
				if (i == 7 || i == 11 || i == 15 || i == 19) {
					uuid = uuid + "-";
				}
			}

			return UUID.fromString(uuid);
		} catch (Exception e) {
		}
		return UUID.fromString("b97a2b52-ea2b-4df2-b3c7-01a144db2981");
	}

	private static void readData(String toRead, StringBuilder result) {

		try {

			int i = 7;

			while (i < 200) {
				if (!String.valueOf(toRead.charAt(i)).equalsIgnoreCase("\"")) {

					result.append(String.valueOf(toRead.charAt(i)));

				} else {
					break;
				}

				i++;
			}

		} catch (Exception e) {
		}
	}

	private static String callURL(String URL) {

		try {

			StringBuilder sb = new StringBuilder();
			URLConnection urlConn = null;
			InputStreamReader in = null;
			try {
				URL url = new URL(URL);
				urlConn = url.openConnection();

				if (urlConn != null)
					urlConn.setReadTimeout(60 * 1000);

				if (urlConn != null && urlConn.getInputStream() != null) {
					in = new InputStreamReader(urlConn.getInputStream(), Charset.defaultCharset());
					BufferedReader bufferedReader = new BufferedReader(in);

					if (bufferedReader != null) {
						int cp;

						while ((cp = bufferedReader.read()) != -1) {
							sb.append((char) cp);
						}

						bufferedReader.close();
					}
				}

				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return sb.toString();
		} catch (Exception e) {
		}
		return "null";

	}
}
