package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Transfer;

public interface TransferDAO {

	List<Transfer> listAllTransfers();
	
	Transfer getTransferById(Long id);
	
	List<Transfer> getAllTransfersByUserId(Long userId);
	
	Transfer create(Transfer transfer);

	List<Transfer> getAllPendingTransfersByUserId(Long userId);

	void updateTransfer(Long id, Transfer transfer);
}
