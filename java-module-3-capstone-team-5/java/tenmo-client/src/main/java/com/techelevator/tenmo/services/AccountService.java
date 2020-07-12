package com.techelevator.tenmo.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.User;

public class AccountService {

	private String BASE_URL;
	private RestTemplate restTemplate = new RestTemplate();
	private String AUTH_TOKEN = "";

	public AccountService(String url) {
		this.BASE_URL = url;
	}

	public void setAuthToken(String token) {
		AUTH_TOKEN = token;
	}

	/**
	 *  Finds user account based on username, pulls that account from the API/database and returns the user's account object
	 * @param username
	 * @return
	 */
	public Account getAccountForUsername(String username) {				
		Account account = null;
		HttpEntity<Account> entity = makeAuthEntity();
		account = restTemplate
				.exchange(BASE_URL + "/accounts?username=" + username, HttpMethod.GET, entity, Account.class).getBody();
		return account;
	}
	/**
	 * Returns an array of users from API
	 * @return
	 */
	public User[] listAllUsers() {
		User[] allUsers = null;
		HttpEntity<User> entity = makeAuthEntity();
		try {
		allUsers = restTemplate.exchange(BASE_URL + "/users", HttpMethod.GET, entity, User[].class).getBody();
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
		return allUsers;
	}
	
	/**
	 * Returns an array of transfers from API
	 * @return
	 */
	public Transfer[] listAllTransfers() {
		Transfer[] allTransfers = null;
		HttpEntity<Transfer> entity = makeAuthEntity();
		try {
		allTransfers = restTemplate.exchange(BASE_URL + "/transfers", HttpMethod.GET, entity, Transfer[].class).getBody();
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
		return allTransfers;
	}
	
	/**
	 * Returns a transfer object that has the specified transfer_id
	 * @param id
	 * @return
	 */
	public Transfer getTransferById(Long id) {
		Transfer transfer = null;
		HttpEntity<Transfer> entity = makeAuthEntity();
		try {
		transfer = restTemplate.exchange(BASE_URL + "/transfers/" + id, HttpMethod.GET, entity, Transfer.class).getBody();
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
		return transfer;
	}
	
	/**
	 * Get an array of transfer objects associated with a single userId
	 * @param userId
	 * @param status
	 * @return
	 */
	public Transfer[] listTransfersByUser(int userId, String status) {
		Transfer[] allTransfers = null;
		HttpEntity<Transfer> entity = makeAuthEntity();
		try {
		allTransfers = restTemplate.exchange(BASE_URL + "/users/transfers?userid=" + userId + "&status=" + status, HttpMethod.GET, entity, Transfer[].class).getBody();
		} catch(Exception exception) {
			System.out.println(exception.getMessage());
		}
		return allTransfers;
	}
	
	/**
	 * Updates transfer in database with transfer information from the transfer object
	 * @param transfer
	 */
	public void updateTransfer(Transfer transfer) {
		HttpEntity<Transfer> entity = makeTransferEntity(transfer);
		try {
		restTemplate.exchange(BASE_URL + "/transfers/" + transfer.getTransferId(), HttpMethod.PUT, entity, Transfer.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Sends a transfer object to the API to be added to database
	 * @param transfer
	 * @return
	 */
	public Transfer createTransfer(Transfer transfer) {
		HttpEntity<Transfer> entity = makeTransferEntity(transfer);
		Transfer newTransfer = null;
		try {
		newTransfer = restTemplate.exchange(BASE_URL + "/transfers", HttpMethod.POST, entity, Transfer.class).getBody();
		} catch (Exception exception) {
			System.out.println("Exception caused by creating transfer");
		}
		return newTransfer;
	}
	
	/** 
	 * Returns the WHOLE USER associated with a specified user id, used this to pull username from user. Look at call hierarchy if you're confused!
	 * Required to access user information from transfer object (transfers only have user Id) 
	 * @param id
	 * @return
	 */
	public User getUsernameById(int id) {
		User user = null;
		HttpEntity<User> entity = makeAuthEntity();
		try {
		user = restTemplate.exchange(BASE_URL + "/users/" + id, HttpMethod.GET, entity, User.class).getBody();
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
		return user;
	}
	
	/**
	 * Sends the account to be updated to the API, returns true if it worked
	 * @param account
	 * @return
	 */
	public boolean updateAccount(Account account) {
		HttpEntity<Account> entity = makeAccountEntity(account);
		HttpStatus statusCode = restTemplate.exchange(BASE_URL + "/accounts/" + account.getAccountId(), HttpMethod.PUT, entity, Account.class).getStatusCode();
		int didWork = statusCode.compareTo(HttpStatus.NO_CONTENT);
		if (didWork == 0) {
			return true;
		} else {
			return false;
		}
	}

	// Helper Methods

	private HttpEntity<Account> makeAccountEntity(Account account) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(AUTH_TOKEN);
		HttpEntity<Account> entity = new HttpEntity<>(account, headers);
		return entity;
	}

	private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(AUTH_TOKEN);
		HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
		return entity;
	}

	private HttpEntity makeAuthEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(AUTH_TOKEN);
		HttpEntity entity = new HttpEntity<>(headers);
		return entity;
	}
}
