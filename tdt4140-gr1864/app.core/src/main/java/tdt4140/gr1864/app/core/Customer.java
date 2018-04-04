package tdt4140.gr1864.app.core;

import java.util.List;
import java.util.Objects;
import java.util.Observable;

import tdt4140.gr1864.app.core.databasecontrollers.CustomerDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.ShoppingTripDatabaseController;
import tdt4140.gr1864.app.core.interfaces.UserInterface;

public class Customer extends Observable implements UserInterface {

	private int customerId;

	private String firstName;
	private String lastName;
	/* has a default value for Customers without demographic data */
	private String address = null;
	private int zip = 0;

	// Extra data, is optional
	private String gender = null; //Have to consider more than just male and female
	private int age = 0;
	private int numberOfPersonsInHousehold = 1; //default with 1 person in household

	private List<ShoppingTrip> shoppingTrips;
	private int recommendedProductID = -1;
	private boolean hasUpdated;
	
	/**
	 * @param customerId		id provided by database
	 * @param firstName			name of customer
	 * @param lastName			name of customer
	 * @param shoppingTrips 	trips of customer
	 */
	public Customer(int customerId, String firstName, String lastName, 
			List<ShoppingTrip> shoppingTrips) {
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.shoppingTrips = shoppingTrips;
		this.hasUpdated = false;
	}
	
	/**
	 * Constructor used by CustomerDatabaseController when there is an address
	 * @param customerId		id provided by database
	 * @param firstName			name of customer
	 * @param lastName			name of customer
	 */
	public Customer(String firstName, String lastName, int customerId,
			 String address, int zip) {
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.zip = zip;
	}


	/**
	 * Constructor used when user have added additional information. Checks for each field being not null
	 * Thsi is for not having exponentially many constructors
	 * @param customerId
	 * @param firstName
	 * @param lastName
	 * @param address
	 * @param zip
	 * @param gender
	 * @param age
	 * @param numberOfPersonsInHousehold
	 * @param shoppingTrips
	 */
	public Customer(String firstName, String lastName, int customerId, String address, int zip, String gender,
					int age, int numberOfPersonsInHousehold) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.customerId = customerId;

		// Input validation
		if (address != null)
			this.address = address;
		if (zip != 0)
            this.zip = zip;
		if (gender != null)
            this.gender = gender;
		if (age != 0)
            this.age = age;
		if (numberOfPersonsInHousehold != 0)
            this.numberOfPersonsInHousehold = numberOfPersonsInHousehold;
	}

	/**
	 * @param firstName			name of customer
	 * @param lastName			name of customer
	 * @param customerId		id provided by database
	 */
	public Customer(String firstName, String lastName, int customerId) { 
		this.firstName = firstName;
		this.lastName = lastName;
		this.customerId = customerId;
	}
	
	/**
	 * @param firstName		name of customer
	 * @param lastName		name of customer
	 */
	public Customer(String firstName, String lastName) { 
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public int getUserId() {
		return customerId;
	}

	public void setUserId(int userId) {
		this.customerId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public List<ShoppingTrip> getShoppingTrips() {
		return shoppingTrips;
	}

	public void setShoppingTrips(List<ShoppingTrip> shoppingTrips) {
		this.shoppingTrips = shoppingTrips;
	}
	
	public int getRecommendedProductID() {
		return this.recommendedProductID;
	}
	
	/**
	 *  The recommendation is based on shopping trips stored in the database and 
	 *  gives the recommendation based on to occasions:
	 *  1. The customer has not bought anything
	 *  	- The recommended product will be set to the most popular one amoung all customers
	 *  2. The customer has bought at least one item
	 *      - The recommended product will be set to be the product where
	 *      there is most difference between the average amount of bought products
	 *      for all customers and the total amount of bought products for this customer.
	 *      Also, this difference is only relevant for when the total average is larger than 
	 *      the customers total amount of bought products
	 * @return productID	The ID of the product that is recommended to the customer
	 */
	
	public int giveRecommendation() {
		// Controller for handling database request for customer
		CustomerDatabaseController cdc = new CustomerDatabaseController();
		
		// Controller for handling database requests shopping trip
		ShoppingTripDatabaseController stdc = new ShoppingTripDatabaseController();
		
		// List of all shopping trips for all customers
		List<ShoppingTrip> allTrips = stdc.retrieveAllShoppingTrips();
		
		// List of all shopping trips for this customer	
		List<ShoppingTrip> customerTrips = stdc.retrieveAllShoppingTripsForCustomer(this.customerId);
		
		// Amount of customers (registered)
		int countCustomers = cdc.countCustomers();
		
		/**
		 * A list of amount of product bought by id.
		 * productsInTotal[0] will give amount of products with id = 0 bought by all customers
		 * Right now there is only 64 possible products
		 */
		int amountOfProducts = 64;
		int[] productsBoughtInTotal = new int[amountOfProducts];
		
		// Updating the productsBoughtInTotal based on all shopping trips
		System.out.println("alltrips size in recommendation " + allTrips.size());
		if (allTrips.size() == 0) return -1;
		
		for (ShoppingTrip st : allTrips) {
			for (Action action : st.getActions()) {
				/* 
				 * Adds to total bought products (by 1) by converting the 1-indexed
				 * productID to be 0-indexed (int[]-index != dbTable-index)
				 */
				productsBoughtInTotal[action.getProduct().getID()-1]++;
			}
		}
		
		/*
		 * If this customer haven't bought anything, the recommended product
		 * will be set to the most popular one among all customers' shopping trips
		 */
		if (customerTrips.size() == 0) {
			int mostAmount = 0;
			int productID = 0;
			
			for (int i = 0; i < amountOfProducts; i++) {
				if (mostAmount < productsBoughtInTotal[i]) {
					mostAmount = productsBoughtInTotal[i];
					productID = i;
				}
			}
			
			return productID;
		}
		
		/**
		 * An overview for amount of products the customer bought in total since registering.
		 * customerProducts[0] gives amount of products with productID = 1 (1-indexed in db) 
		 * that this customer bought in total
		 */
		int[] customerBoughtProductsInTotal= new int[amountOfProducts];
		
		/* 
		 * Updating the customerBoughtProductsInTotal based on all 
		 * shopping trips for this customer
		 */
		for (ShoppingTrip st : customerTrips) {
			for (Action action : st.getActions()) {
				/* 
				 * Adds to total bought products (by 1) for customer by converting the 1-indexed
				 * productID to be 0-indexed (int[]-index != dbTable-index)
				 */
				customerBoughtProductsInTotal[action.getProduct().getID()-1]++;
			}
		}
		
		// Average bought products for all customers based on productsBoughtInTotal / customerAmount
		double[] avgAmountOfProdBoughtByAll = new double[amountOfProducts];
		
		// Calculates average product bought for each customer
		for (int i = 0; i < amountOfProducts; i++) {
			
			// Converts from int to double
			double pbit = productsBoughtInTotal[i];
			double cc = countCustomers;
			
			/*
			 * Sets the average in the averageBoughtProducts list based on whether pbit and cc > 0
			 * NOTE: Don't care about zero or negative relations:
			 */
			if (cc > 0.0 && pbit > 0.0) {
				avgAmountOfProdBoughtByAll[i] = pbit / cc;
			} else {
				avgAmountOfProdBoughtByAll[i] = 0.0;
			}
		}
		
		/*
		 * Calculates the average amount of bought products for this customer based on
		 * customerBoughtProductsInTotal / amount of shopping trips this customer had
		 */
		// Average amount of bought products for this customer
		double[] avgAmountOfProdBoughtByCustomer = new double[amountOfProducts];
		
		for (int i = 0; i < amountOfProducts; i++) {
			
			// Converts from int to double
			double cbpit = customerBoughtProductsInTotal[i];
			double countCustomerTrips = customerTrips.size();
			
			/*
			 * Sets the average in the averageBoughtProductsCustomer list based on 
			 * whether cbpit > 0 and countCustomerTrips > 0
			 * NOTE: Don't care about zero or negative relations:
			 */
			if (cbpit > 0.0 && countCustomerTrips > 0.0) {
				avgAmountOfProdBoughtByCustomer[i] = cbpit / countCustomerTrips;
			} else {
				avgAmountOfProdBoughtByCustomer[i] = 0.0;
			}
		}
		
		/*  
		 * Calculates the biggest difference between the average of bought products per customer
		 * and the average of bought products for one customer (based on amount of shopping trips)
		 * Sets the recommendedProduct to the product with most difference between the total average and
		 * this customer's average where the total average > customer's average
		 */
		
		int productID = -1;
		double maxDelta = -1; // max difference
		
		/* A function that iterates and finds the max difference 
		 * between totalAverage and customer's average
		 */
		for (int i = 0; i < amountOfProducts; i++) {
			double temp;
			
			// Checks for nonzero values
			if (avgAmountOfProdBoughtByAll[i] > 0 && avgAmountOfProdBoughtByCustomer[i] > 0) {
				// the difference between 
				temp = avgAmountOfProdBoughtByAll[i] - avgAmountOfProdBoughtByCustomer[i];	
				if (maxDelta < temp) {
					productID = i;
					maxDelta = temp;
				}
			}
		}
		
		/* 
		 * Converts back from 0-indexing to 1-indexing
		 * Adding '++' before the variable increments the productID by 1 before returning.
		 */
		return ++productID;
	}
	
	public int getZip() {
		return zip;
	}

	/**
	 * Sets new zip code and notifies observer
	 * @param zip new zip code
	 */
	public void setZip(int zip) {
        setChanged();
		notifyObservers();
		clearChanged();
		this.zip = zip;
	}
	
	public String getAddress() {
		return address;
	}

	/**
	 * Sets new address and notifies observer
	 * @param address new address
	 */
	public void setAddress(String address) {
		setChanged();
		notifyObservers();
		clearChanged();
		this.address = address;
	}

    public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getNumberOfPersonsInHousehold() {
		return numberOfPersonsInHousehold;
	}

	public void setNumberOfPersonsInHousehold(int numberOfPersonsInHousehold) {
		this.numberOfPersonsInHousehold = numberOfPersonsInHousehold;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Customer customer = (Customer) o;
		return customerId == customer.customerId &&
				zip == customer.zip &&
				age == customer.age &&
				numberOfPersonsInHousehold == customer.numberOfPersonsInHousehold &&
				recommendedProductID == customer.recommendedProductID &&
				hasUpdated == customer.hasUpdated &&
				Objects.equals(firstName, customer.firstName) &&
				Objects.equals(lastName, customer.lastName) &&
				Objects.equals(address, customer.address) &&
				Objects.equals(gender, customer.gender) &&
				Objects.equals(shoppingTrips, customer.shoppingTrips);
	}

	@Override
	public int hashCode() {

		return Objects.hash(customerId, firstName, lastName, address, zip, gender, age, numberOfPersonsInHousehold, shoppingTrips, recommendedProductID, hasUpdated);
	}
}

