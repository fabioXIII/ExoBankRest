package it.exolab.exobank.rest;

import java.io.IOException;
import java.util.List;

import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.lowagie.text.DocumentException;
import it.exolab.exobank.utils.Endpoint;
import it.exolab.exobank.model.*;
import it.exolab.exobank.pdf.CreatePdf;
import it.exolab.exobank.service.*;

@Path(Endpoint.TRANSACTIONS_REST)
public class TransactionsRest {

	private TransactionsService service = new TransactionsService();

	@GET
	@Path("/findAllTransactions")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response findAllTransactions() {
		try {
			List<Transactions> transactionsList = service.findAllTransactions();
			return Response.status(200).entity(transactionsList).build();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return Response.status(500).build();
	}

	@PUT
	@Path(Endpoint.UPDATE_TRANSACTION_STATUS)
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response updateTransactionStatus(Transactions t) {
		try {
			service.updateTransactionStatus(t);

			return Response.status(200).build();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return Response.status(500).build();
	}

	@POST
	@Path(Endpoint.INSERT_TRANSACTIONS)
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response insertTransaction(Transactions t) {
		try {
			service.insertTransaction(t);
			System.out.println("Transazione inserita con successo!");

			return Response.status(200).build();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return Response.status(500).build();
	}

	@POST
	@Path(Endpoint.FIND_USER_TRANSACTIONS)
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response findUserTransactions(BankAccount b) {
		try {
			List<Transactions> transactionList = service.findUserTransactions(b.getBankAccountID());
			return Response.status(200).entity(transactionList).build();

		} catch (NamingException e) {
			e.printStackTrace();
		}
		return Response.status(500).build();

	}

	@POST
	@Path("/getTransactionsPdf")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response getPdf(TransactionsWithUser request) {
		List<Transactions> transactionList = request.getTransactionList();
		User user = request.getUser();
		try {
			byte[] transactionsPdf = service.generatePdf(transactionList, user);
			return Response.status(200).entity(transactionsPdf).build();
		} catch (DocumentException | IOException | NamingException e) {

			e.printStackTrace();
		}
		return Response.status(500).build();

	}

	@POST
	@Path(Endpoint.DOWNLOAD_PDF)
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response downloadPdf(BankAccount bankAccount) {
		Integer id = bankAccount.getBankAccountID();
	    try {
	    	List<UserTransactions> userTransactions = service.findUserTransactions2(id);
//	        List<Transactions> transazioni = service.findUserTransactions(user.getUserID());
	        byte[] pdfData = new CreatePdf().createPdfTable(userTransactions);

	        if (pdfData != null) {
	            String fileName = "riepilogoTransazioni.pdf";

	            return Response.ok(pdfData)
	                    .header("Content-Disposition", "attachment; filename=" + fileName)
	                    .build();
	        } else {
	            return Response.status(200).build();
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return Response.status(500).build();
	    }
	}

}
