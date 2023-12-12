package it.exolab.exobank.service;

import java.io.IOException;
import java.util.List;

import javax.naming.NamingException;

import com.lowagie.text.DocumentException;

import it.exolab.exobank.controller.TransactionsControllerLocal;
import it.exolab.exobank.model.Transactions;
import it.exolab.exobank.model.User;
import it.exolab.exobank.model.UserTransactions;

public class TransactionsService {
	
//	private EJBService<TransactionsControllerLocal> ejb = new EJBService<TransactionsControllerLocal>();
	
	 private EJBFactory<TransactionsControllerLocal> ejbFactory;
	    private TransactionsControllerLocal transactionsController;
	    
	    public TransactionsService() {
	        try {
	            ejbFactory = new EJBFactory<TransactionsControllerLocal>(TransactionsControllerLocal.class);
	            transactionsController  = ejbFactory.getEJB();
	        } catch (NamingException e) {
	            e.printStackTrace();
	        }
	    }

	public List<Transactions> findAllTransactions() throws NamingException{
		List<Transactions> transactionsList = transactionsController.findAllTransactions();
		return transactionsList;
		
	}
	public void updateTransactionStatus(Transactions s) throws NamingException{

		transactionsController.updateTransactionStatus(s);
	} 
	public void insertTransaction(Transactions t) throws NamingException{
		transactionsController.insertTransaction(t);
		
	}
	public List<Transactions> findUserTransactions(Integer bankId) throws NamingException{
		List<Transactions> transactionList =transactionsController.findUserTransactions(bankId);
		return transactionList;
	}
	
	
	public byte[] generatePdf(List<Transactions> transazioni, User user) throws DocumentException, IOException, NamingException {
		 byte[] transactionsPdf=transactionsController.generatePdf(transazioni, user);
		return transactionsPdf;
		}
	
	public List<UserTransactions> findUserTransactions2(Integer bankId) throws NamingException{
		   List<UserTransactions> userTransactions= transactionsController.findUserTransactions2(bankId);
		   return userTransactions;
	}

}
