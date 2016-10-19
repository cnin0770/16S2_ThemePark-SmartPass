package Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

import Entity.Card;

/**
 * Class for operations of Card
 * 
 * @version 1.2
 * 
 */
public class CardService {

	public HashMap<String, Card> readCardFile(String filePath) {

		HashMap<String, Card> cardMap = new HashMap<String, Card>();

		try {
			File file = new File(filePath);

			if (file.isFile() && file.exists()) { // make a judgement about if  file exists

				Scanner sc = null;
				try {
					sc = new Scanner(new FileReader(filePath));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				// initialize variables
				String lineTxt = null;
				String address = "";
				String attracHistory = "";
				String id = "";
				String name = "";
				String birthday = "";
				String height = "";

				while ((sc.hasNextLine() && (lineTxt = sc.nextLine()) != null)) {

					if (lineTxt.contains("ID")) {
						id = lineTxt.substring(3, lineTxt.length());
						
					} else if (lineTxt.contains("name")) {
						name = lineTxt.substring(5, lineTxt.length());
						
					} else if (lineTxt.contains("birthday")) {
						birthday = lineTxt.substring(9, lineTxt.length());
						
					} else if (lineTxt.contains("height")) {
						height = lineTxt.substring(7, lineTxt.length());
						
					} else if (lineTxt.contains("Spiderman Escape")
							|| lineTxt.contains("Ice Age Adventure")
							|| lineTxt.contains("Canyon Blaster")
							|| lineTxt.contains("4D Theatre")
							|| lineTxt.contains("Flow Rider")
							|| lineTxt.contains("Carousel")) {
						attracHistory += lineTxt + "#";  // may error

					} else {
						if (lineTxt.contains("address")) {
							lineTxt = lineTxt.substring(8, lineTxt.length());
							
						}
						address += lineTxt;  // may error

					}

					if (attracHistory.length() != 0) {
					}

					if (lineTxt.length() == 0 ||!sc.hasNextLine()) {
						
						Card card = new Card();
						card.setId(id.trim());
						card.setName(name.trim());
						card.setHeight(height.trim());
						card.setAddress(address);
						card.setBirthday(birthday.trim());
						card.setAttracVisitHistory(attracHistory);

						cardMap.put(card.getId(), card);

						address = "";
						attracHistory = "";
					}

				}
			}
		} catch (Exception e) {
			System.out.println("ERROR! Error occurs when reading files");
			e.printStackTrace();
		}
		return cardMap;
	}

	public void appendContent(String filePath, String content) {
		try {
			// Open a file writer, but not in mode of appending
			FileWriter writer = new FileWriter(filePath, false);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeCardIntoResultFile(String filePath, HashMap<String, Card> cardMap) {

		Iterator iter = cardMap.entrySet().iterator();
		String content = "";
		while (iter.hasNext()) {
			Entry entry = (Map.Entry) iter.next();
			String id = (String) entry.getKey();
			Card card = (Card) entry.getValue();
			content += "ID " + id + "\r\n";
			content += "name " + card.getName() + "\r\n";
			content += "birthday " + card.getBirthday() + "\r\n";
			if (card.getAddress() != null) {
				content += "address " + card.getAddress() + "\r\n";
			}
			if (card.getHeight() != null) {
				content += "height " + card.getHeight() + "\r\n";
			}
			if (card.getAttracVisitHistory() != null) {
				String[] attracHistory = card.getAttracVisitHistory()
						.split("#");
				for (int k = 0; k < attracHistory.length; k++) {
					content += attracHistory[k] + "\r\n";
				}
			}
			content += "\r\n";
		}

		appendContent(filePath, content);
	}
}
