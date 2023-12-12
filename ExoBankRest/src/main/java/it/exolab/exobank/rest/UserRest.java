package it.exolab.exobank.rest;

import it.exolab.exobank.utils.*;
import java.util.*;
import java.util.List;

import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import it.exolab.exobank.controller.UserControllerLocal;
import it.exolab.exobank.service.*;
import it.exolab.exobank.model.*;
import it.exolab.exobank.email.*;

@Path(Endpoint.USER_REST)
public class UserRest {

	private UserService service = new UserService();
	private SendEmail emailSender = new SendEmail();
//	private RandomOTP otpGenerator = new RandomOTP();
	private JWTUtils jwtUtil = new JWTUtils();
	private OTPGenerator otpGeneratore =OTPGenerator.getInstance();

	private static final Map<String, Integer> userOTPMap = new HashMap<>(); // Mappa per associare l'OTP all'utente

	@GET
	@Path(Endpoint.FIND_ALL_USERS)
	@Produces({ "application/json" })
	@Consumes({ "application/json" })
	public Response findAllUser() {

		try {
			List<User> listaUser = service.findAllUser();

			return Response.status(201).entity(listaUser).build();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(500).build();

	}

	@POST
	@Path(Endpoint.LOGIN)
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response login(User user) {
		System.out.println(user.getEmail());
		System.out.println(user.getPassword());

		try {
			User u = service.findByEmailPassword(user);
			String id = String.valueOf(u.getUserID());

//			String token = jwtUtil.generateToken(id);

//			int otp = otpGenerator.createOTP();
			String secretKey = otpGeneratore.generateSecretKey();
			String otpString = otpGeneratore.generateOTP(secretKey);
			int otp = Integer.parseInt(otpString);

			userOTPMap.put(id, otp);

			String messaggio = "Questo è il tuo OTP:" + otp;

			emailSender.sendEmail(u.getEmail(), "OTP LOGIN", messaggio);
			return Response.status(200).entity(u).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(500).build();

	}

	@POST
	@Path(Endpoint.VERIFY_OTP)
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response verifyOTP(VerifyOtpRequest request) {
		try {
			String frontEndOtp = request.getFrontEndOtp();
			Integer userId = request.getUserId();
			String userIdString = String.valueOf(userId);

			int intOTP = Integer.parseInt(frontEndOtp);
			Integer storedOtp = userOTPMap.get(userIdString);

			if (storedOtp != null && storedOtp == intOTP) {
				// Controlla se è trascorso più di 3 minuti dall'ultima generazione dell'OTP
				long currentTime = System.currentTimeMillis() / 1000; // Tempo corrente in secondi
				long lastOTPGenerationTime = otpGeneratore.getLastOTPGenerationTime(); // Ottieni il tempo dell'ultima
																						// generazione OTP
				if ((currentTime - lastOTPGenerationTime) <= otpGeneratore.getOtpValidityDuration()) {

					System.out.println("OTP corretto");
					userOTPMap.remove(userIdString);

					return Response.status(200).build();
				}

			} else {
				System.out.println("OTP non corretto");
				userOTPMap.remove(userIdString);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(500).build();

	}

	@POST
	@Path(Endpoint.REGISTER)
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response insertUser(User user) {
		System.out.println(user.getFirstName());
		try {
			// creo ruolo da dare all user
			Role role = new Role();

			role.setRoleID(1);
			user.setRoleID(role);
			// inserisco user
			service.insertUser(user);

			return Response.status(200).entity(user).build();

		} catch (Exception e) {

			e.printStackTrace();
		}
		return Response.status(500).build();

	}

	@PUT
	@Path(Endpoint.UPDATE_USER)
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response updateUser(User user) {
		try {
			service.updateUser(user);
			return Response.status(200).build();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(500).build();
	}

	//// ---------------------
	@POST
	@Path("/findBy")
	@Consumes({ "application/json" })
	@Produces({ "application/json" })

	public Response findBy(User user) {

		try {
			User u = service.findByEmailPassword(user);
			return Response.status(200).entity(u).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(500).build();

	}

}
