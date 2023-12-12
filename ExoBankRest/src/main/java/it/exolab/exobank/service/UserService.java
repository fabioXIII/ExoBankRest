package it.exolab.exobank.service;

import java.util.List;

import javax.naming.NamingException;
import javax.ws.rs.core.Response;

import it.exolab.exobank.controller.*;
import it.exolab.exobank.model.User;

public class UserService {
	private EJBFactory<UserControllerLocal> ejbFactory;
	private UserControllerLocal userController;

//	private EJBService<UserControllerLocal> ejb = new EJBService<UserControllerLocal>();
	public UserService() {
		try {
			ejbFactory = new EJBFactory<UserControllerLocal>(UserControllerLocal.class);
			userController = ejbFactory.getEJB();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public List<User> findAllUser() {
		List<User> listaUser = null;

		listaUser = userController.findAllUser();
		return listaUser;
	}

	public Response insertUser(User user) {
		try {
			userController.insertUser(user);
			return Response.status(200).entity(user).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(500).build();
	}

	public User findByEmailPassword(User user) {

		User u = userController.findByEmailPassword(user);
		return u;
	}

	public void updateUser(User user) throws NamingException {
		userController.updateUser(user);

	}
//	public void fai() throws NamingException {
//		ejb.getBankAccountEJB();
//		}

}
