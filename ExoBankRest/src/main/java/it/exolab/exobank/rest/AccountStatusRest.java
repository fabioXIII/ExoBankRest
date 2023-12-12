package it.exolab.exobank.rest;

import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import it.exolab.exobank.model.AccountStatus;
import it.exolab.exobank.service.*;

@Path("/AccountStatusRest")
public class AccountStatusRest {
	
	private AccountStatusService service = new AccountStatusService();
	
	@PUT
	@Path("/updateAccountStatus")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	
	public Response updateAccountStatus(AccountStatus a , Integer bankAccountID) {
		try {
			service.updateAccountStatus(a, bankAccountID);
			return Response.status(200).entity(a).build();
			} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(500).build();
	}
	
	
	

}
