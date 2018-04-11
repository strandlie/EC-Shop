package tdt4140.gr1864.app.core;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import tdt4140.gr1864.app.core.interfaces.Model;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductAmount implements Model{

	private Integer amount;
	private Product product;
	
	public ProductAmount(Integer amount, Product product) {
		this.amount = amount;
		this.product = product;
	}
	
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	@JsonIgnore
	public int getID() {
		return product.getID();
	}
}
