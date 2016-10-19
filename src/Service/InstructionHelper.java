package Service;

import java.io.FileWriter;
import java.io.IOException;
import java.security.spec.ECField;
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

public class InstructionHelper {

	public SimpleDateFormat toDateForamt(String biDay) {

		SimpleDateFormat sdf = null;

		if (biDay.contains("-")) {
			sdf = new SimpleDateFormat("dd-MM-yyyy");

		} else if (biDay.contains("/")) {
			sdf = new SimpleDateFormat("dd/MM/yyyy");
		}

		return sdf;
	}

	public int calcuAge(String birthday) {

//		SimpleDateFormat sdf = null;
//
//		if (birthday.contains("-")) {
//			sdf = new SimpleDateFormat("dd-MM-yyyy");
//
//		} else if (birthday.contains("/")) {
//			sdf = new SimpleDateFormat("dd/MM/yyyy");
//		}

		SimpleDateFormat sdf = null;
		sdf = toDateForamt(birthday);

//		if (birthday.isEmpty() || birthday == null) {
//			System.out.println("================================================================");
//		} else {
//			System.out.println("========================" + birthday + "========================");
//		}

		Date now = new Date();
		int age = 0;
		try {
			Date birthDate = toDateForamt(birthday).parse(birthday);

			long nowTime = now.getTime();
			long birthTime = birthDate.getTime();
			long interval = Math.abs(nowTime - birthTime);
			age = (int) (interval / 1000 / 60 / 60 / 24 / 365);

		} catch (ParseException e) {
			System.out.println("birthday not valid");
		}

		return age;
	}
	
	public void updateOrInsertCard(Card card, String[] addInfo) { // add
		for (int i = 0; i < addInfo.length; i++) {

			if (addInfo[i].trim().contains("ID")) {
				card.setId(addInfo[i].trim().substring(3, addInfo[i].trim().length())); // second arg: 9 or addInfo[i].trim().length() ?
			} else if (addInfo[i].trim().contains("name")) {
				card.setName(addInfo[i].trim().substring(5,
						addInfo[i].trim().length()));
			} else if (addInfo[i].trim().contains("birthday")) {
				card.setBirthday(addInfo[i].trim().substring(9,
						addInfo[i].trim().length()));
			} else if (addInfo[i].trim().contains("height")) {
				card.setHeight(addInfo[i].trim().substring(7,
						addInfo[i].trim().length()));
			} else if (addInfo[i].trim().contains("address")) {
				card.setAddress(addInfo[i].trim().substring(8,
						addInfo[i].trim().length()));
			}
		}
	}
	
	public void judgeRequest(String instruction, HashMap<String, Card> cardMap, String filePath) {
		String content = "";
		String[] requestInfo = instruction.split(";");
		String requestId = requestInfo[0].substring(3, requestInfo[0].length());

		if (cardMap.containsKey(requestId)) {// request
			Card card = cardMap.get(requestId);

			String birthday = card.getBirthday();
			int age = calcuAge(birthday);

			try {
				int height = Integer.valueOf(card.getHeight().substring(0, // error
						card.getHeight().length() - 2));

				String attracName = requestInfo[1].trim();
				Attraction attrac = Constants.attracMap.get(attracName);
				String ageRequire = attrac.getAge();
				String heightRequire = attrac.getHeight();
				boolean flag = true;

				if (ageRequire.contains(">=")) {
					if (age < Integer.valueOf(ageRequire.substring(2,
							ageRequire.length()))) {
//						System.out.println("----request " + instruction + "---");
//						System.out.println("Request Denied: " + requestInfo[1]
//								+ " " + requestInfo[2]);
//						System.out.println("Reasons: Age requirement not met");
						flag = false;

						content = "----request " + instruction + "---" + "\r\n" +
								"Request Denied: " + requestInfo[1] + " " + requestInfo[2] + "\r\n" +
								"Reasons: Age requirement not met";

						System.out.println(content);

						content += "\r\n---------------------------------------\r\n\n";

					}
				} else if (ageRequire.contains("<=")) {
					if (age > Integer.valueOf(ageRequire.substring(2,
							ageRequire.length()))) {
//						System.out.println("----request " + instruction + "---");
//						System.out.println("Request Denied: " + requestInfo[1]
//								+ " " + requestInfo[2]);
//						System.out.println("Reasons: Age requirement not met");
						flag = false;

						content = "----request " + instruction + "---" + "\r\n" +
								"Request Denied: " + requestInfo[1] + " " + requestInfo[2] + "\r\n" +
								"Reasons: Age requirement not met";

						System.out.println(content);

						content += "\r\n---------------------------------------\r\n\n";

					}
				}

				if (heightRequire.contains(">=")) {
					if (height < Integer.valueOf(heightRequire.substring(2,
							heightRequire.length()))) {
//						System.out.println("----request " + instruction + "---");
//						System.out.println("Request Denied: " + requestInfo[1]
//								+ " " + requestInfo[2]);
//						System.out.println("Reasons: Height requirement not met");
						flag = false;

						content = "----request " + instruction + "---" + "\r\n" +
								"Request Denied: " + requestInfo[1] + " " + requestInfo[2] + "\r\n" +
								"Reasons: Height requirement not met";

						System.out.println(content);

						content += "\r\n---------------------------------------\r\n\n";

					}
				} else if (heightRequire.contains("<=")) {
					if (height > Integer.valueOf(heightRequire.substring(2,
							heightRequire.length()))) {
//						System.out.println("----request " + instruction + "---");
//						System.out.println("Request Denied: " + requestInfo[1]
//								+ " " + requestInfo[2]);
//						System.out.println("Reasons: Height requirement not met");
						flag = false;

						content = "----request " + instruction + "---" + "\r\n" +
								"Request Denied: " + requestInfo[1] + " " + requestInfo[2]+ "\r\n" +
								"Reasons: Height requirement not met";

						System.out.println(content);

						content += "\r\n---------------------------------------\r\n\n";
					}
				}

				if (flag) {
					String visitHistory = card.getAttracVisitHistory();
					card.setAttracVisitHistory(visitHistory + "\n" + requestInfo[1]
							+ " " + requestInfo[2]);

				}
			} catch (Exception e) {
				e.getMessage();
			}

		} else {// id does not exist
//			System.out.println("----request " + instruction + "---");
//			System.out.println("Request Denied: " + requestInfo[1] + " "
//					+ requestInfo[2]);
//			System.out.println("Reasons: Request ID does not exist");

			content = "----request " + instruction + "---" + "\r\n" +
					"Request Denied: " + requestInfo[1] + " " + requestInfo[2] + "\r\n" +
					"Reasons: Request ID does not exist";

			System.out.println(content);
			content += "\r\n---------------------------------------\r\n\n";

		}

		if (content != "") {
			appendContent(filePath, content);
		}
	}
	
	
	public void queryByName(String name, HashMap<String, Card> cardMap,
			String filePath) {

		Iterator iter = cardMap.entrySet().iterator();
		String content = "";
		while (iter.hasNext()) {
			Entry entry = (Map.Entry) iter.next();
			String id = (String) entry.getKey();
			Card card = (Card) entry.getValue();
			if (name.equals(card.getName())) {
				content += "----query name " + name + "----\r\n";
				if (card.getAttracVisitHistory() != null) {
					String[] attracHist = card.getAttracVisitHistory().split("#");
					for (int i = 0; i < attracHist.length; i++) {
						content += attracHist[i] + "\r\n";
					}
				}
				content += "---------------------------------------\r\n";
			}
		}
		appendContent(filePath, content);
	}

	public void queryByID(String[] queryInfo, HashMap<String, Card> cardMap,
			String filePath) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		HashMap<String, Integer> visitMap = new HashMap<String, Integer>();

		Iterator iter = cardMap.entrySet().iterator();
		while (iter.hasNext()) {
			Entry entry = (Map.Entry) iter.next();
			String id = (String) entry.getKey();
			Card card = (Card) entry.getValue();
			if (queryInfo[2].equals(card.getId())) {
				if (card.getAttracVisitHistory().contains("-")) {
					card.setAttracVisitHistory(card.getAttracVisitHistory()
							.replace("-", "/"));
				}
				Date fromDate = null;
				Date toDate = null;
				try {
					fromDate = toDateForamt(queryInfo[0]).parse(queryInfo[0]);
					toDate = toDateForamt(queryInfo[1]).parse(queryInfo[1]);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				String[] visitHistory = card.getAttracVisitHistory().split("#");
				int num = visitHistory.length;

				for (int i = 0; i < num; i++) {
					if (visitHistory[i].startsWith("4D Theatre")) {
						visitMap.put(
								"4D Theatre",
								getIndex("4D Theatre", visitHistory[i],
										fromDate, toDate, 11));
					} else if (visitHistory[i].startsWith("Spiderman Escape")) {
						visitMap.put(
								"Spiderman Escape",
								getIndex("Spiderman Escape", visitHistory[i],
										fromDate, toDate, 16));
					} else if (visitHistory[i].startsWith("Ice Age Adventure")) {
						visitMap.put(
								"Ice Age Adventure",
								getIndex("Ice Age Adventure", visitHistory[i],
										fromDate, toDate, 16));
					} else if (visitHistory[i].startsWith("Canyon Blaster")) {
						visitMap.put(
								"Canyon Blaster",
								getIndex("Canyon Blaster", visitHistory[i],
										fromDate, toDate, 14));
					} else if (visitHistory[i].startsWith("Flow Rider")) {
						visitMap.put(
								"Flow Rider",
								getIndex("Flow Rider", visitHistory[i],
										fromDate, toDate, 11));
					} else if (visitHistory[i].startsWith("Carousel")) {
						visitMap.put(
								"Carousele",
								getIndex("Carousel", visitHistory[i], fromDate,
										toDate, 9));
					}
				}

				Iterator iterHist = visitMap.entrySet().iterator();
				int totalVisits = 0;
				int mostVisits = 0;
				int secondVisits = 0;

				while (iterHist.hasNext()) {
					Entry entryHist = (Map.Entry) iterHist.next();
					String attracName = (String) entryHist.getKey();
					int index = (int) entryHist.getValue();
					totalVisits += index;
				}

				if (totalVisits != 0) {
					String content = "";
					content += "----query " + queryInfo[0] + "; "
							+ queryInfo[1] + "; ID " + queryInfo[2]
							+ "----\r\n";
					content += "Total visits: " + totalVisits + "\r\n";

					List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(
							visitMap.entrySet());
					Collections.sort(list,
							new Comparator<Map.Entry<String, Integer>>() {
								// Order
								@Override
								public int compare(Entry<String, Integer> o1,
										Entry<String, Integer> o2) {
									return o2.getValue().compareTo(
											o1.getValue());
								}
							});
					int output = 0;

					for (Map.Entry<String, Integer> mapping : list) {
						if (output == 0) {
							content += "Most-visited: " + mapping.getKey()
									+ " " + mapping.getValue() + "\r\n";
							output++;
						} else if (output == 1) {
							content += "2nd-most-visited: " + mapping.getKey()
									+ " " + mapping.getValue() + "\r\n";
							output++;
						}
					}

					content += "---------------------------------------\r\n";
					appendContent(filePath, content);
				}
			}
		}
	}

	public int getIndex(String attracName, String visitHistory, Date fromDate,
			Date toDate, int offset) {
		int index = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String visitDate[] = visitHistory.substring(offset,
				visitHistory.length()).split(" ");
		for (int j = 0; j < visitDate.length; j++) {
			Date visitDay = null;
			if (visitDate[j].contains("/")) {
				try {
					visitDay = sdf.parse(visitDate[j]);
					if (visitDay.after(fromDate) && visitDay.before(toDate)) {
						index++;
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return index;
	}

	public void queryByAge(String[] queryInfo, HashMap<String, Card> cardMap,
			String filePath) {
		Iterator iter = cardMap.entrySet().iterator();
		int population = 0;
		SimpleDateFormat sdf = null;
		ArrayList<Integer> ageList = new ArrayList<Integer>();

		while (iter.hasNext()) {
			Entry entry = (Map.Entry) iter.next();
			Card card = (Card) entry.getValue();
			// Calculate age
			int age = calcuAge(card.getBirthday());
			if (card.getAttracVisitHistory() != null) {
				if (card.getAttracVisitHistory().contains("-")) {
					card.setAttracVisitHistory(card.getAttracVisitHistory().replace("-", "/"));
				}
				Date fromDate = null;
				Date toDate = null;
				try {
//					fromDate = sdf.parse(queryInfo[0]);
//					toDate = sdf.parse(queryInfo[1]);

					fromDate = toDateForamt(queryInfo[0]).parse(queryInfo[0]);
					toDate = toDateForamt(queryInfo[1]).parse(queryInfo[1]);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				ArrayList<String> visitDateList = new ArrayList<String>();
				String[] visitHistory = card.getAttracVisitHistory().split("#");
				int num = visitHistory.length;
				for (int i = 0; i < num; i++) {
					String[] histSegment = visitHistory[i].split(" ");
					for (int j = 0; j < histSegment.length; j++) {
						if (histSegment[j].contains("/") || histSegment[j].contains("-")) {
							Date visitDay = null;
							try {
								visitDay = toDateForamt(histSegment[j]).parse(histSegment[j]);
								if (visitDay.after(fromDate)&& visitDay.before(toDate)) {
									ageList.add(age);
									population++;
								}
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
					}

				}
			}
		}
		int below8 = 0;
		int over8AndBelow18 = 0;
		int over18AndBelow65 = 0;
		int over65 = 0;

		for (Iterator iterAgeList = ageList.iterator(); iterAgeList.hasNext();) {

			int age = (Integer) iterAgeList.next();
			if (age <= 8) {
				below8++;
			} else if (age > 8 && age <= 18) {
				over8AndBelow18++;
			} else if (age > 18 && age <= 65) {
				over18AndBelow65++;
			} else {
				over65++;
			}
		}

		String content = "";
		if (population > 0) {
			content += "----query " + queryInfo[0] + "; " + queryInfo[1]
					+ "; age----" + "\r\nPopulation size: " + population
					+ "\r\n" + "Age profile\r\nBelow 8: "
					+ ((float) below8 / population) * 100
					+ "%\r\nOver 8 and below 18: "
					+ ((float) over8AndBelow18 / population) * 100
					+ "%\r\nOver 18 and Below 65: "
					+ ((float) over18AndBelow65 / population) * 100
					+ "%\r\nOver 65: " + ((float) over65 / population) * 100
					+ "%\r\n";
			content += "---------------------------------------\r\n";
			appendContent(filePath, content);

		}

	}
	
	public static void appendContent(String filePath, String content) {
		try {
			//  Open a file writer, and in mode of appending
			FileWriter writer = new FileWriter(filePath, true);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
