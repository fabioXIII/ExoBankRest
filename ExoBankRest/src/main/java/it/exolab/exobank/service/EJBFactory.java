package it.exolab.exobank.service;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class EJBFactory<T> {

    private final static String PREFIX = "java:global/ExoBankEJBEAR/ExoBankEJB/";

    private Class<T> interfaceClass;

    public EJBFactory(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

   

    @SuppressWarnings("unchecked")
	public T getEJB() throws NamingException {
        Properties props = new Properties();
        InitialContext ctx;
        T ret = null;
        props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");

        ctx = new InitialContext(props);
      
        ret = (T) ctx.lookup(PREFIX + interfaceClass.getSimpleName() + "!" + interfaceClass.getName());

        return ret;
    }
}
