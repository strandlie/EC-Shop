package tdt4140.gr1864.app.core;

import java.util.HashMap;
import java.util.Map;

import com.stripe.Stripe;
import com.stripe.model.Token;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

public class StripeShoppingTrip {
	private String card;
	private int expMonth;
	private int expYear;
	private String cvc;
	
	static {
		/**
		 * Test API key for the Stripe API.
		 */
		Stripe.apiKey = "sk_test_UfH4v9dDBkZ1DJvte0keF1Ta";
	}
	
	/**
	 * @param card The data for the card to charge.
	 */
	public StripeShoppingTrip(String card, int expMonth, int expYear, String cvc) {
		this.card = card;
		this.expMonth = expMonth;
		this.expYear = expYear;
		this.cvc = cvc;
	}
	
	/**
	 * @param amount The amount to charge from the card, in millikroner.
	 * @return A Boolean indicating whether or not the amount was charged successfully.
	 */
	public boolean charge(int amount) {
		Map<String, Object> tokenParams = new HashMap<String, Object>();
		Map<String, Object> cardParams = new HashMap<String, Object>();
		cardParams.put("number", card);
		cardParams.put("exp_month", expMonth);
		cardParams.put("exp_year", expYear);
		cardParams.put("cvc", cvc);
		tokenParams.put("card", cardParams);
		
		try {
			Token token = Token.create(tokenParams);
	        Map<String, Object> chargeMap = new HashMap<String, Object>();
	        chargeMap.put("amount", amount);
	        chargeMap.put("currency", "nok");
	        chargeMap.put("source", token.getId());

	        try {
	            Charge.create(chargeMap);
	            return true;
	        } catch (StripeException e) {
	            e.printStackTrace();
	        }
		} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException | APIException e1) {
			e1.printStackTrace();
		}

		return false;
	}
}
