package it.exolab.exobank.restconf;

import it.exolab.exobank.model.Transactions;
import it.exolab.exobank.rest.*;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


import org.jboss.resteasy.plugins.interceptors.CorsFilter;




@ApplicationPath("/rest")
public class ControllerRestApplication extends Application {
//	
private Set<Object> singletons; 

	
	public ControllerRestApplication() {
		 super();
		 CorsFilter corsFilter = new CorsFilter();
		 corsFilter.getAllowedOrigins().add("*");
		 corsFilter.setAllowedMethods("OPTIONS, GET, POST, DELETE, PUT, PATCH");
		 singletons = new HashSet<Object>();
		 singletons.add(corsFilter);
		}
	
		@Override
		 public Set<Object> getSingletons() {
		 return singletons;
				 }
	
	@Override
	public Set<Class<?>> getClasses(){
		Set<Class<?>> set = new HashSet<>();
		set.add(UserRest.class);
		set.add(BankAccountRest.class);
		set.add(TransactionsRest.class);
		
		return set;
	}


}
