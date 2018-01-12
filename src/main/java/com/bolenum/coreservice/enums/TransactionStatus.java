package com.bolenum.coreservice.enums;

public enum TransactionStatus {

	WITHDRAW("withdraw"), DEPOSIT("deposit"), FEE("fee"), TRANSFER("transfer");

	private String txStatus;

	private TransactionStatus(String txType) {
		this.txStatus = txType;
	}

	public String getTransactionStatus() {
		return txStatus;
	}
}
