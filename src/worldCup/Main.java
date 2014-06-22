package worldCup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Main {

	public static void main(String args[]) throws InterruptedException {
		URL url;

		int i = 0;
		while (true) {
			try {

				// get URL content
				url = new URL(
						"https://fwctickets.fifa.com/TopsAkaCalls/Calls.aspx/getRefreshChartAvaDem?l=en&c=BRA");
				URLConnection conn = url.openConnection();
				conn.setRequestProperty("Content-Type",
						"application/json; charset=utf-8");
				conn.setRequestProperty("Connection", "keep-alive");
				conn.setRequestProperty("Accept-Language",
						"en-US,en;q=0.8,pt;q=0.6");
				// conn.setRequestProperty("Accept-Encoding",
				// "gzip,deflate,sdch");
				conn.setRequestProperty("Cookie", "teste");
				// conn.setRequestProperty("Accept",
				// "application/json, text/javascript, */*; q=0.01");

				// open the stream and put it into BufferedReader
				BufferedReader br = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));

				String inputLine;

				// use FileWriter to write file
				boolean tem = false;
				String texto = "";
				while ((inputLine = br.readLine()) != null) {
					System.out.println(i++ + inputLine);
					String[] textList = inputLine.split("PRPProductId");
					for (String text : textList) {
						if (text.contains("IMT36") || text.contains("IMT47")
								|| text.contains("IMT55")
								|| text.contains("IMT62")) {
							if (!text.contains("-1") && !text.contains("0")) {
								texto += "JOGO: "
										+ text.split("IMT")[1].substring(0, 2)
										+ " INGRESSO TIPO: "
										+ text.split("PRPCategoryId")[1]
												.substring(5, 7)
										+ " QUANTIDADE: "
										+ text.split("Quantity")[1].substring(
												4, 6) + "\n\n";
								tem = true;
							}
						} else {
							if (text.contains("IMT")) {
								if (!text.contains("-1") && !text.contains("0")) {
									texto += "JOGO: "
											+ text.split("IMT")[1].substring(0,
													2)
											+ " INGRESSO: "
											+ text.split("PRPCategoryId")[1]
													.substring(5, 7)
											+ " QUANTIDADE: "
											+ text.split("Quantity")[1]
													.substring(5, 8) + "\n\n";
								}
							}
						}
					}
				}

				System.out.println(texto);
				if (tem) {
					System.out.println("entrei");
					EmailHelper.sendEmail(texto);
				} else {
					System.out.println("nada");
				}
				Thread.sleep(10000);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
