package TPSP16S2;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import Entity.Card;
import Entity.Constants;
import Service.CardService;
import Service.InstructionService;

public class TPSP {
	
	public static void main(String[] args){
		
		Constants cons = new Constants(); // set up attractions
//		System.out.println("-----------------Theme Park Smart-Pass System-----------------");
		
		if(args.length!=4){
			System.out.println("ERROR!Please input right arguments ordered by card file, instruction file, result file and report file.");
		}else{
			String cardFile = args[0];
			String instructionFile = args[1];
			String resultFile = args[2];
			String reportFile = args[3];
			
			HashMap<String,Card> cardMap = new HashMap<String,Card>();
			
			CardService cardService = new CardService();
			cardMap = cardService.readCardFile(cardFile);

			
			InstructionService instructionService = new InstructionService();
			instructionService.readInstrcutionFile(instructionFile, cardMap, reportFile);

			
			cardService.writeCardIntoResultFile(resultFile, cardMap);
		}

	}
}
