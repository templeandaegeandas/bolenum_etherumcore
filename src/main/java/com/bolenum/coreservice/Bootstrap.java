package com.bolenum.coreservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.bolenum.coreservice.services.TransactionService;

/**
 * @author chandan kumar singh
 * @date 12-Sep-2017
 */
@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private TransactionService transactionService;

	private static final Logger logger = LoggerFactory.getLogger(Bootstrap.class);

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		logger.debug("on application event");
		transactionService.getBlockNumber();
		transactionService.saveEthereumIncomingTx();
		/*
		 * Web3j web3j = Web3j.build(new HttpService()); // defaults to
		 * http://localhost:8545/ Subscription subscription =
		 * web3j.blockObservable(false).subscribe(block -> {
		 * System.out.println("Sweet, block number " +
		 * block.getBlock().getNumber() + " has just been created"); },
		 * Throwable::printStackTrace); try { TimeUnit.MINUTES.sleep(2); } catch
		 * (InterruptedException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } subscription.unsubscribe();
		 * registerIncomingTransaction();
		 */
	}

	/**
	 * @description to get the all the incoming transaction in application, it
	 *              will continue listen for any new transaction in blockchain
	 *              and emit an Object of new transaction @param @exception
	 *
	 */

	/*
	 * private void registerIncomingTransaction() {
	 * 
	 * Web3j web3j = Web3j.build(new HttpService()); // defaults to //
	 * http://localhost:8545/ Subscription subscription =
	 * web3j.blockObservable(false).subscribe(block -> {
	 * System.out.println("Sweet, block number " + block.getBlock().getNumber()
	 * + " has just been created"); }, Throwable::printStackTrace);
	 * 
	 * try { TimeUnit.MINUTES.sleep(2); } catch (InterruptedException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); }
	 * subscription.unsubscribe(); }
	 */

}
