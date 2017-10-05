/**
 * 
 */
package com.bolenum.coreservice.services;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import com.bolenum.coreservice.enums.TransactionType;
import com.bolenum.coreservice.model.Transaction;
import com.bolenum.coreservice.model.User;
import com.bolenum.coreservice.repo.TransactionRepo;
import com.bolenum.coreservice.repo.UserRepository;

/**
 * @author chandan kumar singh
 * @date 02-Oct-2017
 */
@Service
public class TransactionServiceImpl implements TransactionService {

	private Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TransactionRepo transactionRepo;

	private Web3j web3j;

	@PostConstruct
	void init() {
		web3j = Web3j.build(new HttpService("http://127.0.0.1:8000"));
	}

	@Override
	public void saveEthereumIncomingTx() {
		logger.debug("method for transaction event called");
		web3j.transactionObservable().subscribe(Transaction -> {
			logger.debug("transaction to address: {}", Transaction.getTo());
			if (Transaction.getTo() == null || Transaction.getTo().isEmpty()) {
				return;
			}
			User user = userRepository.findByEthWalletaddress(Transaction.getTo());
			if (user != null) {
				logger.debug("new Incoming ethereum transaction for user : {}", user.getEmailId());
				Transaction tx = transactionRepo.findByTxHash(Transaction.getHash());
				if (tx == null) {
					Transaction transaction = new Transaction();
					transaction.setTxHash(Transaction.getHash());
					transaction.setFromAddress(Transaction.getFrom());
					transaction.setToAddress(Transaction.getTo());
					transaction.setGas(convertWeiToEther(Transaction.getGas()));
					transaction.setTxAmount(convertWeiToEther(Transaction.getValue()));
					transaction.setTransactionType(TransactionType.INCOMING);
					Transaction saved = transactionRepo.saveAndFlush(transaction);
					if (saved != null) {
						logger.debug("new incoming transaction saved of user: {}", user.getEmailId());
					}
				} else {
					logger.debug("tx exists: {}", Transaction.getHash());
				}
			}

		}, Throwable::printStackTrace);

	}

	@Override
	public void getBlockNumber() {
		web3j.blockObservable(true).subscribe(block -> {
			logger.debug("block number: {}", block.getBlock().getNumber() + " has just been created");
		}, Throwable::printStackTrace);
	}
	/**
	 * to convert value from wei to either
	 * @description convertWeiToEther
	 * @param 
	 * @return Double
	 * @exception 
	 *
	 */
	private Double convertWeiToEther(BigInteger amount){
		logger.debug("amount in Wei: {}",amount);
		BigDecimal balance = new BigDecimal(amount);
		BigDecimal conversionRate = new BigDecimal(new BigInteger("1000000000000000000"));
		BigDecimal amountInEther = balance.divide(conversionRate);
		logger.debug("amount in eth: {}", amountInEther.doubleValue());
		return amountInEther.doubleValue();
	}
}
