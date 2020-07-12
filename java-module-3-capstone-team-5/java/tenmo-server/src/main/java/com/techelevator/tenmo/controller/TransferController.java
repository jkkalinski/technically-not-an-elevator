package com.techelevator.tenmo.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.model.Transfer;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

	private TransferDAO transferDAO;
	
	public TransferController(TransferDAO transferDAO) {
		this.transferDAO = transferDAO;
	}
	
	@RequestMapping(path = "/transfers", method = RequestMethod.GET)
	public List<Transfer> getAllTransfers(){
		return transferDAO.listAllTransfers();
	}
	
	@RequestMapping(path = "/users/transfers", method = RequestMethod.GET)
	public List<Transfer> getAllTransfersByUserId(@RequestParam (required = true, value = "userid")Long id, @RequestParam (required = false, value = "status", defaultValue = "all") String status){
		if(status.equals("pending")) {
			return transferDAO.getAllPendingTransfersByUserId(id);
		}
		return transferDAO.getAllTransfersByUserId(id);
	}
	
	
	@RequestMapping(path = "/transfers/{id}", method = RequestMethod.GET)
	public Transfer getTransferById(@PathVariable ("id") Long id) {
		return transferDAO.getTransferById(id);
	}
	
	@RequestMapping(path = "/transfers/{id}", method = RequestMethod.PUT)
	public void updateTransferById(@PathVariable ("id") Long id, @RequestBody Transfer transfer) {
		 transferDAO.updateTransfer(id, transfer);
	}
	
	
	@RequestMapping(path = "/transfers", method = RequestMethod.POST)
	public Transfer createTransfer(@RequestBody Transfer transfer) {
		return transferDAO.create(transfer);
	}
	
	
}
