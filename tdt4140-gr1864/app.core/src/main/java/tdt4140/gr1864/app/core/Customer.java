package tdt4140.gr1864.app.core;

import java.util.List;

import tdt4140.gr1864.app.core.databasecontrollers.ShoppingTripDatabaseController;
import tdt4140.gr1864.app.core.interfaces.UserInterface;

public class Customer implements UserInterface {
	private int customerId;
	private String firstName;
	private String lastName;
	private List<ShoppingTrip> shoppingTrips;
	private int recommendedProductID = -1;
	
	/**
	 * Constructor used by CustomerDatabaseController
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
	 *  Will based on shopping trips stored in the database calculate the recommended product
	 *  based on all customers shopping trips
	 * @return productID	The ID of the product that is recommended to the customer
	 */
	
	public int giveRecommendation() {
		// Controller for handling database requests
		ShoppingTripDatabaseController stdc = new ShoppingTripDatabaseController();
		// List of all shopping trips for all customers
		List<ShoppingTrip> allTrips = stdc.retrieveAllShoppingTrips();
		// List of all shopping trips for this customer
		List<ShoppingTrip> customerTrip = stdc.retrieveAllShoppingTripsForCustomer(this.customerId);
		
		if (customerTrip.size() == 0) {
			//calculateMostPopularProduct
		}
		
		/**
		 * A list of amount of product bought by id.
		 * productsInTotal[0] will give amount of products with id = 0 bought by all customers
		 * Right now there is only 64 possible products
		 */
		int amountOfProducts = 64;
		int[] productsBoughtInTotal = new int[amountOfProducts];
		
		// Updating the productsBoughtInTotal based on all shopping trips
		for (ShoppingTrip st : allTrips) {
			for (Action action : st.getActions()) {
				/* 
				 * Adds to total bought products (by 1) by converting the 1-indexed
				 * productID to be 0-indexed (int[]-index != dbTable-index)
				 */
				productsBoughtInTotal[action.getProduct().getID()-1]++;
			}
		}
		
		/**
		 * An overview for amount of products the customer bought in total since registering
		 * customerProducts[0] gives amount of products with id = 0 that this customer bought in total
		 */
		int[] customerBoughtProductsInTotal= new int[amountOfProducts];
		
		// Average bought products for all customers based on productsInTotal / customerAmount
		double[] averageBoughtProducts = new double[amountOfProducts];
		
		
		/*  
		 * Calculates the biggest difference between the average of bought products for all customers and 
		 * the amount of bought products for this customer
		 * Sets the recommendedProduct to the product with most difference 

		int idForMaxDelta = 0;
		double maxDelta = -1;
		for (int i = 0; i < amountOfProducts; i++) {
			double temp;
			
			if (averageBoughtProducts[i] != 0 && this.getCustomerProductsBought()[i] != 0) {
				temp = averageBoughtProducts[i] - this.getCustomerProductsBought()[i];	
				if (maxDelta < temp) {
					idForMaxDelta = i;
					maxDelta = temp;
				}
			}
		}
		setRecommendation(++idForMaxDelta);
		
		* Calculates average product bought for each customer
		* 
		double[] averageBought = new double[amountOfProducts];
		for (int i = 0; i < amountOfProducts; i++) {
			
			double prodInTot = productsInTotal[i];
			double customerAmount = Customer.customerAmount;
			
			if (customerAmount != 0.0 && prodInTot != 0.0) {
				averageBought[i] = prodInTot / customerAmount;
			} else {
				averageBought[i] = 0.0;
			}
		}
		averageBoughtProducts = averageBought;
		
		*  calculate the most popular product
		int mostAmount = 0;
		int mostAmountIdx = 0;
		
		for (int i = 0; i < amountOfProducts; i++) {
			if (mostAmount < productsInTotal[i]) {
				mostAmount = productsInTotal[i];
				mostAmountIdx = i;
			}
		}
		
		return mostAmountIdx;

		*/
		
		return -1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + customerId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} 
		else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} 
		else if (!lastName.equals(other.lastName))
			return false;
		if (customerId != other.customerId)
			return false;
		return true;
	}
}

