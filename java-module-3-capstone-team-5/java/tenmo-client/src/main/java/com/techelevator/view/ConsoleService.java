package com.techelevator.view;


import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.User;
import com.techelevator.tenmo.services.AccountService;

public class ConsoleService {

	private PrintWriter out;
	private Scanner in;
	private NumberFormat money = NumberFormat.getCurrencyInstance();
	private List<Long> allIds = new ArrayList<>();

	public ConsoleService(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output, true);
		this.in = new Scanner(input);
	}

	public Object getChoiceFromOptions(Object[] options) {
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		out.println();
		return choice;
	}

	private Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if (selectedOption > 0 && selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			}
		} catch (NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will be null
		}
		if (choice == null) {
			out.println("\n*** " + userInput + " is not a valid option ***\n");
		}
		return choice;
	}

	private void displayMenuOptions(Object[] options) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			int optionNum = i + 1;
			out.println(optionNum + ") " + options[i]);
		}
		out.print("\nPlease choose an option >>> ");
		out.flush();
	}

	/**
	 * Displays a list of users and returns the single user selected. Boolean determines which prompt (send or request) is displayed to the user
	 * @param allUsers
	 * @param isASend
	 * @return
	 */
	public User displayAllUsers(User[] allUsers, boolean isASend) {
		User selectedUser = null;
		for(int i = 0; i < allUsers.length; i++) {
			System.out.println(allUsers[i].getId() + " " + allUsers[i].getUsername());
		}
		if (isASend) {
			System.out.println("Enter the ID of the user you'd like to send bucks to: ");
		} else {
			System.out.println("Enter the ID of the user you'd like to bother for money: ");
		}
		try {
			int choice = Integer.parseInt(in.nextLine());			
			for(User u : allUsers) {							// Validates data input (user choice is a real user)
				if (u.getId() == choice) {
					selectedUser = u;
				}
			}
		} catch (Exception e) {
			return selectedUser;
		}
		return selectedUser;
	}

	/**
	 * Gets a transfer amount from the user for both requests and sends
	 * @return
	 */
	public double getAmountForTransfer() {
		double amount = 0;
		System.out.println("Enter amount: ");
		try {
			amount = Double.parseDouble(in.nextLine());
		} catch (Exception e) {
			displayInvalidSelectionMessage();
		}
		return amount;
	}

	/**
	 * Shows the transfers that are passed in, matches names from user list with IDs from the transfers to display together
	 * @param allTransfers
	 * @param allUsers
	 */
	public void displayAllTransfers(Transfer[] allTransfers, User[] allUsers) {
		Long transferId = null;
		String fromUsername = null;
		String toUsername = null;
		allIds.clear();       								// Resetting list of IDs for each display of transfers
		System.out.println("*****************");
		System.out.println("ALL TRANSFERS");
		System.out.println("*****************");
		for(Transfer i: allTransfers) {
			for(User u : allUsers) {
				if (i.getAccountFrom() == u.getId()) {			// Equating user ID with account ID
					fromUsername = u.getUsername();				// Matches both account to and account from to a name
				} else if (i.getAccountTo() == u.getId()) {
					toUsername = u.getUsername();
				}
			}

			System.out.println(i.getTransferId() + " " + money.format(i.getAmount()) + " From: " + fromUsername + " To: " + toUsername);
			allIds.add(i.getTransferId());    // Store IDs in a list so that we can show only valid id options in the getTransferFromUserChoice method
		}

		return;
	}

	public String getUserInput(String prompt) {
		out.print(prompt+": ");
		out.flush();
		return in.nextLine();
	}

	public Integer getUserInputInteger(String prompt) {
		Integer result = null;
		do {
			out.print(prompt+": ");
			out.flush();
			String userInput = in.nextLine();
			try {
				result = Integer.parseInt(userInput);
			} catch(NumberFormatException e) {
				out.println("\n*** " + userInput + " is not valid ***\n");
			}
		} while(result == null);
		return result;
	}

	public void displayCurrentBalance(Account account) {
		System.out.println("Your current balance is: " + money.format(account.getBalance()));	
	}

	public void displayInvalidSelectionMessage() {
		System.out.println("Looks like you entered an invalid selection! ");
	}

	/**
	 * Get the transfer id from user selection, validates that the selected transfer exists. 
	 * Gives a different prompt based on whether status of transfer is pending or complete.
	 * @param status
	 * @return
	 */
	public Long getTransferIdFromUserChoice(String status) {
		if (status.equals("pending")) {
			System.out.println("Please enter transfer ID to approve or reject pending request: ");
		} else {
			System.out.println("Which transfer ID would you like to see more information about: ");
		}
		Long transferId = null;
		try {
			transferId = Long.parseLong(in.nextLine());
		} catch(Exception exception) {
			displayInvalidSelectionMessage();
		}
		for (Long id : allIds) {				// List of IDs is instantiated from displayAllTransfers		
			if (id == transferId) {				// displayAllTransfers is always called prior to this method, so list is current
				return transferId;
			} 
		}
		return null;
	}

	/**
	 * Displays transfer that's passed in, uses a list of all users to show username with the transfer info
	 * @param chosenTransfer
	 * @param allUsers
	 */
	public void displayChosenTransfer(Transfer chosenTransfer, User[] allUsers) {
		String fromUsername = "";
		String toUsername = "";
		String status = "";
		String type = "";
		for(User u : allUsers) {
			if (chosenTransfer.getAccountFrom() == u.getId()) {
				fromUsername = u.getUsername();
			} else if (chosenTransfer.getAccountTo() == u.getId()) {
				toUsername = u.getUsername();
			}
		}
// Setting transfer variables as their corresponding strings to display transfer status & type in an understandable way
		if(chosenTransfer.getTransferTypeId() == 1) {				
			type = "Request";
		} else if (chosenTransfer.getTransferTypeId() == 2) {
			type = "Send";
		} 
		
		if(chosenTransfer.getTransferStatusId() == 1) {
			status = "Pending";
		} else if (chosenTransfer.getTransferStatusId() == 2) {
			status = "Approved";
		} else {
			status = "Rejected";
		}

		
		System.out.println("*************************");
		System.out.println("TRANSFER DETAILS FOR #" + chosenTransfer.getTransferId());
		System.out.println("*************************");
		System.out.println(chosenTransfer.getTransferId() + "\n" + money.format(chosenTransfer.getAmount()) + "\nFrom: " + fromUsername + "\nTo: " + toUsername);
		System.out.println("Type: " + type + "\nStatus: " + status);
		return;
	}
	
	/**
	 * Shows a prompt asking user to deal with the transfer request, returns their numeric choice
	 * @return
	 */
	public int approveOrRejectPrompt() {
		int choice = 0;
		System.out.println("Choose\n1) to approve\n2) to reject\n0) to exit");
		try {
		choice = Integer.parseInt(in.nextLine());
		} catch(Exception e) {
			displayInvalidSelectionMessage();
		}
		return choice;
	}

	/**
	 * Provides visual appeal and perhaps additional grade points
	 */
	public void exitGracefully() {
		System.out.println("************************************************");
		System.out.println("THANK YOU FOR USING KAROL AND MATTHIAS' APP!!!!!");
		System.out.println("REMEMBER, THIS IS AMERICA, CODE HOWEVER YOU WANT");
		System.out.println("************************************************");
	}

	public void displayInsufficientFunds() {
		System.out.println("You have insufficient funds!");		
	}

}
