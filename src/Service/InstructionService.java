package Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import Entity.Attraction;
import Entity.Card;
import Entity.Constants;

/**
 * Class for operations of Instructions
 * 
 * @version 2.0
 * 
 */
public class InstructionService {

	private InstructionHelper helper = new InstructionHelper();
	
	public void add(String instruction, HashMap<String, Card> cardMap) {
		instruction = instruction.substring(4, instruction.length()).trim();
		String[] addInfo = instruction.split(";");
//		String newId = instruction.substring(3, 9); // fatal error

		String newId = addInfo[0].substring(3); // fatal correction!!!!! 1019 lab
		if (cardMap.containsKey(newId)) {// update record

			Card card = cardMap.get(newId);
			helper.updateOrInsertCard(card, addInfo);

		} else {// add new record
			Card card = new Card();
			helper.updateOrInsertCard(card, addInfo);

			cardMap.put(newId, card);
		}
	}



	public void delete(String instruction, HashMap<String, Card> cardMap) {

		String delId = instruction.substring(10, instruction.length());
		if (cardMap.containsKey(delId)) {// delete record
			cardMap.remove(delId);

		} else {// id does not exist
			System.out.println("ERROR!Can not delete ID " + delId
					+ " because it does not exist!");
		}
	}

	

	

	public void request(String instruction, HashMap<String, Card> cardMap, String filePath) {
		instruction = instruction.substring(8, instruction.length());
		helper.judgeRequest(instruction, cardMap, filePath);

	}

	// query results and write results to file
	public void query(String queryStr, HashMap<String, Card> cardMap,
			String filePath) {
		if (queryStr.contains("name")) {
			String name = queryStr.substring(11, queryStr.length());
			helper.queryByName(name, cardMap, filePath);
			
		} else if (queryStr.contains("ID")) {

			String[] queryInfo = queryStr.substring(6, queryStr.length())
					.split(";");
			queryInfo[0] = queryInfo[0].trim();
			queryInfo[1] = queryInfo[1].trim();
			queryInfo[2] = queryInfo[2].trim().substring(3);

			try {
				helper.queryByID(queryInfo, cardMap, filePath);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else if (queryStr.contains("age")) {
			String[] queryInfo = queryStr.substring(6, queryStr.length()).split(";");
			queryInfo[0] = queryInfo[0].trim();
			queryInfo[1] = queryInfo[1].trim();
			helper.queryByAge(queryInfo, cardMap, filePath);
		}
	}

	

	public void readInstrcutionFile(String instructFilePath,
			HashMap<String, Card> cardMap, String reportFilePath) {
		try {
			String encoding = "utf-8";
			File file = new File(instructFilePath);

			if (file.isFile() && file.exists()) { // make a judgement about if file exists

				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;

				while ((lineTxt = bufferedReader.readLine()) != null) {

					if (lineTxt.startsWith("add ")) {
						add(lineTxt.trim(), cardMap);
					} else if (lineTxt.startsWith("delete ")) {
						delete(lineTxt.trim(), cardMap);
					} else if (lineTxt.startsWith("request ")) {
						request(lineTxt.trim(), cardMap, reportFilePath);

					} else if (lineTxt.startsWith("query ")) {
						query(lineTxt.trim(), cardMap, reportFilePath);

					}
				}
				read.close();
			} else {
				System.out.println("ERROR!Can not find specified file.");
			}
		} catch (Exception e) {
			System.out.println("ERROR!Error occurs when reading files");
			e.printStackTrace();
		}
	}

	
}
