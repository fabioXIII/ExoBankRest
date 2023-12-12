package it.exolab.exobank.rest;

import java.util.List;
import java.util.UUID;

import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import it.exolab.exobank.model.*;
import it.exolab.exobank.service.BankAccountService;
import it.exolab.exobank.utils.Endpoint;

@Path(Endpoint.BANK_ACCOUNT_REST)
public class BankAccountRest {

	
	private BankAccountService bankService = new BankAccountService();

		
	@POST
	@Path(Endpoint.INSERT_BANK_ACCOUNT)
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response insertBankAccount(User user) {
		// creo un id random univoco
		System.out.println(user.getUserID());
		UUID id = UUID.randomUUID();
		String idRandom = id.toString();

		// setto lo stato dell account
		AccountStatus a = new AccountStatus();
		a.setId(1);
		// creo l account
		BankAccount b = new BankAccount();
		b.setAccountNumber(idRandom);
		b.setAccountStatusID(a);
		b.setBalance(0);
		b.setUserID(user);
		try {
			bankService.insertBankAccount(b);
			return Response.status(200).entity(b).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(500).build();

	}
	@POST
	@Path(Endpoint.FIND_BANKACCOUNT_BY_USERID)
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response findBankAccountByUserId(User u) {
		
		try {
			BankAccount b =bankService.findBankAccountByUserId(u.getUserID());
			return Response.status(200).entity(b).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return Response.status(500).build();
	}
	
	@GET
	@Path(Endpoint.FIND_ALL_BANKACCOUNT)
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response  findAllBankAccount(){
		
		try {
			List<BankAccount> listaUser = bankService.findAllBankAccount();
			return Response.status(200).entity(listaUser).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(500).build();
}
	@PUT
	@Path(Endpoint.UPDATE_BANKACCOUNT_STATUS)
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response updateBankAccountStatus(BankAccount b) {
		    try {
				bankService.updateBankAccountStatus(b);
				return Response.status(200).build();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return Response.status(500).build();
	}

}
