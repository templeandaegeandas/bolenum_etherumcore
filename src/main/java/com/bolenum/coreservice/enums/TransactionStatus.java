package com.bolenum.coreservice.enums;

public enum TransactionStatus {

	WITHDRAW("WITHDRAW"), DEPOSIT("DEPOSIT");

	private String txStatus;

	private TransactionStatus(String txStatus) {
		this.txStatus = txStatus;
	}

	public String getTransactionType() {
		return txStatus;
	}
}
