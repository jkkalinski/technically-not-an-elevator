package com.techelevator.tenmo.models;

public class Transfer {
	private Long transferId;
	private int transferTypeId;
	private int transferStatusId;
	private int accountFrom;
	private int accountTo;
	private double amount;

	public Transfer() {
	};
	
	public Transfer(int transferTypeId, int transferStatusId, int accountFrom, int accountTo, double amount) {
		this.transferTypeId = transferTypeId;
		this.transferStatusId = transferStatusId;
		this.accountFrom = accountFrom;
		this.accountTo = accountTo;
		this.amount = amount;
	}
	
	public Transfer(String transferType, String transferStatus, int accountFrom, int accountTo, double amount) {
		this.transferTypeId = setTransferTypeId(transferType);
		this.transferStatusId = setTransferStatusId(transferStatus);
		this.accountFrom = accountFrom;
		this.accountTo = accountTo;
		this.amount = amount;
	}

	public Long getTransferId() {
		return transferId;
	}

	public void setTransferId(Long transferId) {
		this.transferId = transferId;
	}

	public int getTransferTypeId() {
		return transferTypeId;
	}
	
	// Sets TransferTypeId IF int
	public void setTransferTypeId(int transferTypeId) {
		this.transferTypeId = transferTypeId;
	}
	
	// Sets TransferTypeId IF String
	public int setTransferTypeId(String transferType) {
		if (transferType.equals("Request")) {
			this.transferTypeId = 1;
		} else if (transferType.equals("Send")) {
			this.transferTypeId = 2;
		}
		return transferTypeId;
	}

	public int getTransferStatusId() {
		return transferStatusId;
	}

	// Sets TransferStatusId IF int
	public void setTransferStatusId(int transferStatusId) {
		this.transferStatusId = transferStatusId;
	}
	
	// Sets TransferStatusId IF String
	public int setTransferStatusId(String transferStatus) {
		if (transferStatus.equals("Pending")) {
			this.transferStatusId = 1;
		} else if (transferStatus.equals("Approved")) {
			this.transferStatusId = 2;
		} else if (transferStatus.equals("Rejected")) {
			this.transferStatusId = 3;
		}
		return transferStatusId;
	}

	public int getAccountFrom() {
		return accountFrom;
	}

	public void setAccountFrom(int accountFrom) {
		this.accountFrom = accountFrom;
	}

	public int getAccountTo() {
		return accountTo;
	}

	public void setAccountTo(int accountTo) {
		this.accountTo = accountTo;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Transfer [transferId=" + transferId + ", transferTypeId=" + transferTypeId + ", transferStatusId="
				+ transferStatusId + ", accountFrom=" + accountFrom + ", accountTo=" + accountTo + ", amount=" + amount
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accountFrom;
		result = prime * result + accountTo;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((transferId == null) ? 0 : transferId.hashCode());
		result = prime * result + transferStatusId;
		result = prime * result + transferTypeId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transfer other = (Transfer) obj;
		if (accountFrom != other.accountFrom)
			return false;
		if (accountTo != other.accountTo)
			return false;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (transferId == null) {
			if (other.transferId != null)
				return false;
		} else if (!transferId.equals(other.transferId))
			return false;
		if (transferStatusId != other.transferStatusId)
			return false;
		if (transferTypeId != other.transferTypeId)
			return false;
		return true;
	}

}
