package tdt4140.gr1864.app.core;

import java.util.Date;

import org.hibernate.Session;

public class UserConnection {

	public static void main(String[] args) {
		User user = new User(1, "Ben", "Ten");
		
		//Get Session
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		//start transaction
		session.beginTransaction();
		//Save the Model object
		session.save(user);
		//Commit transaction
		session.getTransaction().commit();
		System.out.println("Employee ID="+user.getUserId());
		
		//terminate session factory, otherwise program won't end
		HibernateUtil.getSessionFactory().close();
	}

}
