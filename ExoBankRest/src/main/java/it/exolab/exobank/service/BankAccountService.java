package it.exolab.exobank.service;

import java.util.List;

import javax.naming.NamingException;
import javax.ws.rs.core.Response;

import it.exolab.exobank.controller.*;
import it.exolab.exobank.model.BankAccount;
import it.exolab.exobank.model.User;

public class BankAccountService {
//	private EJBService<BankAccountControllerLocal> ejb = new EJBService<BankAccountControllerLocal>();
	
	  private EJBFactory<BankAccountControllerLocal> ejbFactory;
	    private BankAccountControllerLocal bankAccountController;
	    
	    public BankAccountService() {
	        try {
	            ejbFactory = new EJBFactory<BankAccountControllerLocal>(BankAccountControllerLocal.class);
	            bankAccountController = ejbFactory.getEJB();
	        } catch (NamingException e) {
	            e.printStackTrace();
	        }
	    }
	

	public void insertBankAccount(BankAccount b) {

		bankAccountController.insertBankAccount(b);

	}

	public BankAccount findBankAccountByUserId(Integer id)  {
		BankAccount b = bankAccountController.findBankAccountByUserId(id);
		return b;
	}

	public List<BankAccount> findAllBankAccount()  {
		List<BankAccount> listaUser =bankAccountController.findAllBankAccount();
		return listaUser;
	}

	public void updateBankAccountStatus(BankAccount b){
		bankAccountController.updateBankAccountStatus(b);
	}

	public void updateBankAccountBalance(Integer bankAccountID, double newBalance){
		bankAccountController.updateBankAccountBalance(bankAccountID, newBalance);
	}

	public BankAccount findBankAccountById(Integer id){
		BankAccount b =bankAccountController.findBankAccountById(id);
		return b;

	}
}
