package com.techelevator;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class VendingMachine  {

	Map<String, Slot> slotMap = new TreeMap<String, Slot>();		// Made a TreeMap to hold all the slots, identified by their Letter-Number coordinates in alpha-numeric order
	Set<String> slotKeys;
						


	public VendingMachine () {
		slotMap = new TreeMap<String, Slot>();
		slotKeys = slotMap.keySet();
	}

	public void startVendingMachine() throws FileNotFoundException {

		File setupFile = new File("vendingmachine.csv");		//  Tagged inventory text file to be used in Vending Machine
		Scanner inputFile = new Scanner(setupFile);				//	Reads the text file

		while(inputFile.hasNext()) {																			// Loops through the file while there are lines left to read
			String aLine = inputFile.nextLine();																
			String[] lineArray = aLine.split("\\|");															// Split into array around the | character
			Product tempProduct = new Product(lineArray[1],lineArray[3]);										// Make product object that stores a name and type
			Slot tempSlot = new Slot(tempProduct, BigDecimal.valueOf(Double.parseDouble((lineArray[2]))));		// Makes a new slot that holds the actual product object and price as a bigdecimalf
			slotMap.put(lineArray[0], tempSlot);																// Put our new slot into the treemap, which retains sort order :--) 

		}		
		inputFile.close();
		
	}
	
	public void displayInventory() {																			// Made a visually appealing-ish inventory display
		System.out.println("*********************************************************************************************");
		System.out.println("*********************************************************************************************");
		System.out.println("**" + "A1:" + slotMap.get("A1") +" "+ "A2:" + slotMap.get("A2") +" "+ "A3:" + slotMap.get("A3") +" "+ "A4:" + slotMap.get("A4") + "   **");
		System.out.println("");
		System.out.println("*********************************************************************************************");
		System.out.println("*********************************************************************************************");
		System.out.println("");
		System.out.println("**" + "B1:" + slotMap.get("B1") +" "+ "B2:" + slotMap.get("B2") +" "+ "B3:" + slotMap.get("B3") +" "+ "B4:" + slotMap.get("B4") + "                   **");
		System.out.println("*********************************************************************************************");
		System.out.println("*********************************************************************************************");
		System.out.println("");
		System.out.println("**" + "C1:" + slotMap.get("C1") +" "+ "C2:" + slotMap.get("C2") +" "+ "C3" + slotMap.get("C3") +" "+ "C4:" + slotMap.get("C4") + "                    **");
		System.out.println("*********************************************************************************************");
		System.out.println("*********************************************************************************************");
		System.out.println("**" + "D1:" + slotMap.get("D1") +" "+ "D2:" + slotMap.get("D2") +" "+ "D3" + slotMap.get("D3") +" "+ "D4:" + slotMap.get("D4") + "     **");
		System.out.println("");
		System.out.println("*********************************************************************************************");
		System.out.println("******************************" +"|"+"                        " + "|" + "*************************************");
		System.out.println("******************************" +"|"+"                        " + "|" + "*************************************");
		System.out.println("*********************************************************************************************");
	}
		
	
	public Map<String, Slot> getSlotMap() {
		return slotMap;
	}
	public Set<String> getSlotKeys() {
		return slotKeys;
	}


	
}




