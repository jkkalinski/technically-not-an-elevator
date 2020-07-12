package com.techelevator;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**************************************************************************************************************************
 *  This is your Vending Machine Command Line Interface (CLI) class
 *
 *  It is the main process for the Vending Machine
 *
 *  THIS is where most, if not all, of your Vending Machine interactions should be coded
 *  
 *  It is instantiated and invoked from the VendingMachineApp (main() application)
 *  
 *  Your code should be placed in here
 ***************************************************************************************************************************/
import com.techelevator.view.Menu;         // Gain access to Menu class provided for the Capstone

public class VendingMachineCLI {

	// Main menu options defined as constants

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE      = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT          = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS,
			MAIN_MENU_OPTION_PURCHASE,
			MAIN_MENU_OPTION_EXIT
	};
	private static final String PURCHASE_MENU_OPTION_FEED_MONEY			= "Feed money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT 	= "Select product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY,
			PURCHASE_MENU_OPTION_SELECT_PRODUCT,
			PURCHASE_MENU_OPTION_FINISH_TRANSACTION
	};
	private static final String ONE_DOLLAR = "$1.00";
	private static final String TWO_DOLLAR = "$2.00";
	private static final String FIVE_DOLLAR = "$5.00";
	private static final String TEN_DOLLAR = "$10.00";
	private static final String[] DOLLAR_VALUES = { ONE_DOLLAR,
			TWO_DOLLAR,
			FIVE_DOLLAR,
			TEN_DOLLAR
	};


	private Menu vendingMenu;              // Menu object to be used by an instance of this class
	private Transaction moneyStuff;			// Transaction object to be used by an instance of this class

	VendingMachine vendingMachine = new VendingMachine();

	Date date = new Date();														// set up date and time objects for audit log file timestamping
	Timestamp timeStamp = new Timestamp(date.getTime());
	SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");		// format date/time for audit log file
	SimpleDateFormat logFormatter = new SimpleDateFormat("MM-dd-yyyy@HH-mm-ss");	// format date/time for sales report file name

	PrintWriter salesLogger;									// declare printWriter for the sales report csv file
	PrintWriter auditLogger;									// declare printWriter for teh audit log csv file


	public VendingMachineCLI(Menu menu) throws FileNotFoundException {  // Constructor - user will pass a menu for this class to use
		this.vendingMenu = menu;           								// Make the Menu the user object passed, our Menu
		moneyStuff = new Transaction();

		salesLogger = new PrintWriter(new File("sales_report_"+ logFormatter.format(date) + ".txt"));		// create a printwriter for an object of the sales file
		auditLogger = new PrintWriter(new File("Log.txt"));													// create a printwriter for an object of the audit log file
	}
	/**************************************************************************************************************************
	 *  VendingMachineCLI main processing loop
	 *  
	 *  Display the main menu and process option chosen
	 *
	 *  It is invoked from the VendingMachineApp program
	 *
	 *  THIS is where most, if not all, of your Vending Machine objects and interactions 
	 *  should be coded
	 *
	 *  Methods should be defined following run() method and invoked from it
	 * @throws FileNotFoundException 
	 *
	 ***************************************************************************************************************************/

	public void run() throws FileNotFoundException  {

		vendingMachine.startVendingMachine();				// starts vending machine when program is run, which populates the hashmap from the inventory file


		boolean shouldProcess = true;         // Loop control variable

		while(shouldProcess) {                // Loop until user indicates they want to exit

			String choice = (String)vendingMenu.getChoiceFromOptions(MAIN_MENU_OPTIONS);  // Display menu and get choice

			switch(choice) {                  // Process based on user menu choice

			case MAIN_MENU_OPTION_DISPLAY_ITEMS:
				displayItems();           // invoke method to display items in Vending Machine
				break;                    // Exit switch statement

			case MAIN_MENU_OPTION_PURCHASE:
				boolean	purchasing = true;
				while(purchasing) {
					String purchaseChoice = (String)vendingMenu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);	// creates a purchasing sub-menu when user selects Purchase from main menu

					switch(purchaseChoice) {
					case PURCHASE_MENU_OPTION_FEED_MONEY:
						feedMoney();						// invoke method to allow user to select amount of money they want to input
						break;

					case PURCHASE_MENU_OPTION_SELECT_PRODUCT:
						purchaseItems();					// invoke method to allow user to select which slot they want to pick
						break;

					case PURCHASE_MENU_OPTION_FINISH_TRANSACTION:
						giveChange();						// invoke method to take the current balance and dispense in coins
						purchasing = false;
						break;
					}
				}
				break;										// breaks from the purchase main menu option

			case MAIN_MENU_OPTION_EXIT:
				endMethodProcessing();    // Invoke method to perform end of method processing
				shouldProcess = false;    // Set variable to end loop
				break;                    // Exit switch statement
			}

		}
		auditLogger.close();					// closes the audit printWriter
		salesLogger.close();					// closes the sales report printWriter
		return;                               // End method and return to caller


	}
	/********************************************************************************************************
	 * Methods used to perform processing
	 ********************************************************************************************************/
	public void displayItems() {      // static attribute used as method is not associated with specific object instance
		vendingMachine.displayInventory();			// generates visual display of inventory from the csv file
	}

	public void feedMoney() {
		String billChoice = (String)vendingMenu.getChoiceFromOptions(DOLLAR_VALUES);  // displays monetary options for users to select to add to their balance

		switch (billChoice) {
		case ONE_DOLLAR:
			moneyStuff.deposit(1);
			break;
		case TWO_DOLLAR:
			moneyStuff.deposit(2);
			break;
		case FIVE_DOLLAR:
			moneyStuff.deposit(5);
			break;
		case TEN_DOLLAR:
			moneyStuff.deposit(10);
			break;
		}

		auditLogger.println(formatter.format(date) + " FEED MONEY; "   + billChoice + " " + "$" + moneyStuff.getAmtTendered().toString()); // creates a data line in the audit log csv when money is fed into program

		System.out.println("Current balance: $" + moneyStuff.getAmtTendered());			// prints current balance
	}

	public void purchaseItems() {	 // static attribute used as method is not associated with specific object instance

		System.out.println("Current balance: $" + moneyStuff.getAmtTendered());

		String slotChoiceKey = (String)vendingMenu.getChoiceFromOptions(vendingMachine.getSlotKeys().toArray());		// Sets keys as choice options for menu

		Slot slotToPurchase = vendingMachine.getSlotMap().get(slotChoiceKey);											// Stores user slot choice
		if(!slotToPurchase.isSoldOut() && (slotToPurchase.getPrice().compareTo(moneyStuff.getAmtTendered())==-1) || slotToPurchase.getPrice().compareTo(moneyStuff.getAmtTendered())==0) {
			moneyStuff.setAmtOwed(slotToPurchase.getPrice());			//Sets amount owed to the price of the item
			moneyStuff.applyDeposit();									//Takes price of item out of your balance
			System.out.println(slotToPurchase.dispense());									//Decrements quantity of item available
			System.out.println(slotToPurchase.getSnack().returnMessage());					// displays the fun message to the user when their snack is dispensed; "Yum!"
			System.out.println("Current balance: $" + moneyStuff.getAmtTendered());
			auditLogger.println(formatter.format(date) + " " + slotToPurchase.getSnack().toString() + " " + slotChoiceKey +  " " + "$" + moneyStuff.getAmtTendered().toString()); // adds data line of purchase to audit log
		} else {
			System.out.println("Insufficient funds!");
		}
		
	}

	public void giveChange() {
		if (!moneyStuff.getChangeDue().equals(BigDecimal.ZERO)) {
		System.out.println("Your change is: $" + moneyStuff.getChangeDue().setScale(2));
		System.out.println("*Coin noises* Here's " + moneyStuff.changeCoinifier());

		auditLogger.println(formatter.format(date) + " GIVE CHANGE: $"   + moneyStuff.getChangeDue().setScale(2) + " " + "$" + moneyStuff.getAmtTendered().setScale(2).toString());
		}
		}

	public void endMethodProcessing() { // static attribute used as method is not associated with specific object instance
		BigDecimal sales = new BigDecimal(0);															// create sales value to be used to get accumulated sales dollar amount
		for(Map.Entry<String, Slot> entry: vendingMachine.getSlotMap().entrySet()) {   					// foreach loop: create 'entry' map and loop through existing vendingMachine Map								
			salesLogger.println(entry.getValue().getSnack().toString() +  "|" + (5 - (entry.getValue().getQuantity())));   // print to sales report: the name of the snack and the number of times said item was sold
			sales = sales.add(entry.getValue().getPrice().multiply(new BigDecimal((5 - (entry.getValue().getQuantity())))));  // Updates the sales bigdecimal by getting the price of item multipled by the number of times said item was sold
		}

		salesLogger.println("");
		salesLogger.println("Total sales: $" + sales);							// prints grandtotal of sales during every program run

		
	}



}
