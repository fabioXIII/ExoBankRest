package it.exolab.exobank.service;

import javax.naming.NamingException;

import it.exolab.exobank.controller.AccountStatusControllerLocal;
import it.exolab.exobank.model.AccountStatus;

public class AccountStatusService {
	
//private EJBService<AccountStatusControllerLocal> ejb = new EJBService<AccountStatusControllerLocal>();

private EJBFactory<AccountStatusControllerLocal> ejbFactory;
private AccountStatusControllerLocal accountStatusController;

public AccountStatusService() {
    try {
        ejbFactory = new EJBFactory<AccountStatusControllerLocal>(AccountStatusControllerLocal.class);
        accountStatusController  = ejbFactory.getEJB();
    } catch (NamingException e) {
        e.printStackTrace();
    }
}
	
	public void updateAccountStatus(AccountStatus a , Integer bankAccountID) throws NamingException {
		accountStatusController.updateAccountStatus(a, bankAccountID);
		 
	}


}
