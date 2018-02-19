package tdt4140.gr1864.app.core;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

	//XML based configuration
	private static SessionFactory sessionFactory;
	
	//Annotation based configuration
	private static SessionFactory sessionAnnotationFactory;
	
	//Property based configuration
	private static SessionFactory sessionJavaConfigFactory;

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
	        	Configuration configuration = new Configuration();
	        	configuration.configure("/main/resources/tdt4140/gr1864/app/core/hibernate.cfg.xml");
	        	System.out.println("Hibernate Configuration loaded");
	        	
	        	ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
	        	System.out.println("Hibernate serviceRegistry created");
	        	
	        	SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	        	
            return sessionFactory;
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static SessionFactory buildSessionAnnotationFactory() {
	    	try {
	            // Create the SessionFactory from hibernate.cfg.xml
	        	Configuration configuration = new Configuration();
	        	configuration.configure("/main/resources/tdt4140/gr1864/app/core/hibernate.cfg.xml");
	        	System.out.println("Hibernate Annotation Configuration loaded");
	        	
	        	ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
	        	System.out.println("Hibernate Annotation serviceRegistry created");
	        	
	        	SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	        	
            return sessionFactory;
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
	}

    private static SessionFactory buildSessionJavaConfigFactory() {
	    	try {
	    	Configuration configuration = new Configuration();
			
			//Create Properties, can be read from property files too
			Properties props = new Properties();
			props.put("hibernate.connection.driver_class", "com.sqlite.jdbc.Driver");
			props.put("hibernate.connection.url", "jdbc:sqlite:database.db");
			props.put("hibernate.connection.username", "");
			props.put("hibernate.connection.password", "");
			props.put("hibernate.current_session_context_class", "thread");
			
			configuration.setProperties(props);
			
			//we can set mapping file or class with annotation
			//addClass(Employee1.class) will look for resource
			// com/journaldev/hibernate/model/Employee1.hbm.xml (not good)
			configuration.addAnnotatedClass(User.class);
			
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		    	System.out.println("Hibernate Java Config serviceRegistry created");
		    	
		    	SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		    	
	        return sessionFactory;
	    	}
        catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
	}
    
	public static SessionFactory getSessionFactory() {
		if(sessionFactory == null) sessionFactory = buildSessionFactory();
        return sessionFactory;
    }
	
	public static SessionFactory getSessionAnnotationFactory() {
		if(sessionAnnotationFactory == null) sessionAnnotationFactory = buildSessionAnnotationFactory();
        return sessionAnnotationFactory;
    }
	
	public static SessionFactory getSessionJavaConfigFactory() {
		if(sessionJavaConfigFactory == null) sessionJavaConfigFactory = buildSessionJavaConfigFactory();
        return sessionJavaConfigFactory;
    }
	
}
