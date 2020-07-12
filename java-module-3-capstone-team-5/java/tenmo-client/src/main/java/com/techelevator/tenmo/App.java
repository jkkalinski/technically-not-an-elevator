package com.techelevator.tenmo;

import java.text.NumberFormat;
import java.util.Formatter;

import javax.swing.text.NumberFormatter;

import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.User;
import com.techelevator.tenmo.models.UserCredentials;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.view.ConsoleService;

public class App {

	private static final String API_BASE_URL = "http://localhost:8080/";

	private static final String MENU_OPTION_EXIT = "Exit";
	private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };

	private AuthenticatedUser currentUser;
	private ConsoleService console;
	private AuthenticationService authenticationService;
	private AccountService accountService;

	private Account userAccount;
	private Account foreignAccount;
	private User foreignUser;
	NumberFormat money = NumberFormat.getCurrencyInstance();


	public static void main(String[] args) {
		App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL), new AccountService(API_BASE_URL));
		app.run();
	}

	public App(ConsoleService console, AuthenticationService authenticationService, AccountService accountService) {
		this.console = console;
		this.authenticationService = authenticationService;
		this.accountService = accountService;

	}

	public void run() {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");

		registerAndLogin();
		mainMenu();
	}

	private void mainMenu() {
		while(true) {
			String choice = (String)console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if(MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if(MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
			} else if(MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests();
			} else if(MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			} else if(MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestBucks();
			} else if(MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}

	private void viewCurrentBalance() {
		console.displayCurrentBalance(userAccount);
	}

	private void viewTransferHistory() {
		Transfer[] allTransfers;
		String status = "all";
		allTransfers = accountService.listTransfersByUser(currentUser.getUser().getId(), status);		// Returns COMPLETED transfers from API based on the current user's ID 
		User[] allUsers = accountService.listAllUsers();

		console.displayAllTransfers(allTransfers, allUsers);							
		Long id = console.getTransferIdFromUserChoice(status);					// Stores ID of chosen COMPLETED transfer from presented list of transfers
		if (id != null) {
			try {
				Transfer thisTransfer = accountService.getTransferById(id);								// Passes that ID to API to retrieve full transfer info
				console.displayChosenTransfer(thisTransfer, allUsers);
			} catch (Exception e) {
				console.displayInvalidSelectionMessage();
			}
		} 
	}

	private void viewPendingRequests() {										
		Transfer[] allPendingTransfers;
		String status = "pending";
		allPendingTransfers = accountService.listTransfersByUser(currentUser.getUser().getId(), status);// Returns PENDING transfers from API based on the current user's ID 
		User[] allUsers = accountService.listAllUsers();
		console.displayAllTransfers(allPendingTransfers, allUsers);
		Long id = console.getTransferIdFromUserChoice(status);
		if (id != null) {
			try {
				Transfer thisTransfer = accountService.getTransferById(id);								// Passes that ID to API to retrieve full transfer info
				console.displayChosenTransfer(thisTransfer, allUsers);
				approveOrReject(thisTransfer);									// Makes call to member method to approve or reject the PENDING transfer
			} catch (Exception e) {
				console.displayInvalidSelectionMessage();
			}
		} 
	}

	private void sendBucks() {
		User[] allUsers = accountService.listAllUsers();
		foreignUser = console.displayAllUsers(allUsers, true);		// Call console method to store a user who will receive money
		if(foreignUser != null) {									//cases where console service returns a chosen user successfully
			foreignAccount = accountService.getAccountForUsername(foreignUser.getUsername());		// destination account is associated with the destination user
		} else {
			console.displayInvalidSelectionMessage();
			return;
		}
		double transferAmount = console.getAmountForTransfer();			//transfer amount for duration of this send transaction
		if (transferAmount <= 0 || userAccount.getBalance() < transferAmount || userAccount.equals(foreignAccount)) { // Check that transfer isn't negative, transfer is smaller than total sender balance, and transfer isn't to yourself
			console.displayInvalidSelectionMessage();
			return;
		} else {
			updateAccountsAndSendTransfer(transferAmount); // Calls member method to execute transfer 
		}


	}

	/** 
	 * Requests bucks from a selected user, validates that user exists and transfer value isn't zero or negative 
	 * while respecting the requestee's privacy concerning the state of their own finances B) 
	 */
	private void requestBucks() {
		User[] allUsers = accountService.listAllUsers();
		foreignUser = console.displayAllUsers(allUsers, false);
		if(foreignUser != null) {
			foreignAccount = accountService.getAccountForUsername(foreignUser.getUsername());
		} else {
			console.displayInvalidSelectionMessage();
			return;
		}
		double transferAmount = console.getAmountForTransfer();
		if (transferAmount <= 0 || userAccount.equals(foreignAccount)) {		// Will allow a request to be made even if it's for more than the requestee has
			console.displayInvalidSelectionMessage();
			return;
		} else {
			Transfer thisTransfer = new Transfer("Request", "Pending", foreignAccount.getUserId(), userAccount.getUserId(), transferAmount);
			thisTransfer = accountService.createTransfer(thisTransfer);
		}
	}

	/**
	 * Allows user to approve or reject requests made of them
	 * @param thisTransfer
	 */
	private void  approveOrReject(Transfer thisTransfer) {			
		if (currentUser.getUser().getId() == thisTransfer.getAccountTo()) {
			System.out.println("You cant access that!");						// User can't approve their own request
			return;
		}

		int choice = console.approveOrRejectPrompt();

		switch(choice) {
		case 0: 																// Can leave transfer as pending/undecided
			return;
		case 1:						// approves
			if (userAccount.getBalance() < thisTransfer.getAmount()) {			// If user approves but doesn't have sufficient funds, nothing happens
				console.displayInsufficientFunds();
				break;
			} 
			executeRequestedTransfer(thisTransfer);										// Otherwise, transfer is carried out
			break;
		case 2: 					// rejects
			thisTransfer.setTransferStatusId("Rejected");						// Set transfer status to rejected locally
			accountService.updateTransfer(thisTransfer);						// Push updated transfer to the database
			break;
		}
	}

	/**
	 * Takes a transfer amount, effects "to" and "from" account balances by that amount 
	 * @param transferAmount
	 */
	private void updateAccountsAndSendTransfer(double transferAmount) {
		userAccount.setBalance(userAccount.getBalance() - transferAmount);					// Mr. Banker makes the appropriate credits and debits
		foreignAccount.setBalance(foreignAccount.getBalance() + transferAmount);
		console.displayCurrentBalance(userAccount);
		accountService.updateAccount(userAccount);											// Pushing updated accounts to database
		accountService.updateAccount(foreignAccount);
		Transfer thisTransfer = new Transfer("Send", "Approved", userAccount.getUserId(), foreignAccount.getUserId(), transferAmount); // Making new transfer object based on transfer info
		thisTransfer = accountService.createTransfer(thisTransfer); 						// Posting transfer to API
	}

	/**
	 *  Carries a transfer request that has been approved by the requestee
	 * @param thisTransfer
	 */
	private void executeRequestedTransfer(Transfer thisTransfer) {
		thisTransfer.setTransferStatusId("Approved");				// Changing status of transfer object to APPROVED
		accountService.updateTransfer(thisTransfer);				// Sending changed transfer object to the API
		double amount = thisTransfer.getAmount();					
		
		// Instantiating a foreign (not the current) user and the associated account from the transfer object
		foreignUser = accountService.getUsernameById(thisTransfer.getAccountTo());			
		foreignAccount = accountService.getAccountForUsername(foreignUser.getUsername());
		userAccount.setBalance(userAccount.getBalance() - amount);							// Making balance changes for to and from accounts
		foreignAccount.setBalance(foreignAccount.getBalance() + amount);

		accountService.updateAccount(userAccount);								// Putting updated accounts to API
		accountService.updateAccount(foreignAccount);
	}

	private void exitProgram() {
		console.exitGracefully();
		System.exit(0);
	}

	private void registerAndLogin() {
		while(!isAuthenticated()) {
			String choice = (String)console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
		while (!isRegistered) //will keep looping until user is registered
		{
			UserCredentials credentials = collectUserCredentials();
			try {
				authenticationService.register(credentials);
				isRegistered = true;
				System.out.println("Registration successful. You can now login.");
			} catch(AuthenticationServiceException e) {
				System.out.println("REGISTRATION ERROR: "+e.getMessage());
				System.out.println("Please attempt to register again.");
			}
		}
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) //will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
			try {
				currentUser = authenticationService.login(credentials);
				accountService.setAuthToken(currentUser.getToken());
				userAccount = accountService.getAccountForUsername(currentUser.getUser().getUsername());
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: "+e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}

	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}
}
