package tdt4140.gr1864.app.ui.Mode.VisualizationElement;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import tdt4140.gr1864.app.ui.globalUIModel.VisualizationViewController;

public class DemographicsTableRow implements TableRow {
	
	private StringProperty customerId;
	private StringProperty firstName;
	private StringProperty lastName;
	private StringProperty address;
	private StringProperty zip;
	
	private int size;

	public DemographicsTableRow(String customerId, String firstName, String lastName, String address, String zip) {
	
		this.size = 5;
	}



	@Override
	public int getSize() {
		return this.size;
	}
	
	public StringProperty customerIdProperty() {
		if (this.customerId == null) {
			this.customerId = new SimpleStringProperty(this, "customerId");
		}
		return this.customerId;
	}
	
	public void setCustomerId(String customerId) {
		this.customerIdProperty().setValue(customerId);
	}
	
	public void setCustomerId(int customerId) {
		setCustomerId(Integer.toString(customerId));
	}
	
	public String getCustomerId() {
		return customerIdProperty().getValue();
	}
	
	
	public StringProperty firstNameProperty() {
		if (this.firstName == null) {
			this.firstName = new SimpleStringProperty(this, "firstName");
		}
		return this.firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstNameProperty().setValue(firstName);
	}
	
	public String getFirstName() {
		return this.firstNameProperty().getValue();
	}
	
	
	public StringProperty lastNameProperty() {
		if (this.lastName == null) {
			this.lastName = new SimpleStringProperty(this, "lastName");
		}
		return this.lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastNameProperty().setValue(lastName);
	}
	
	public String getLastName() {
		return this.lastNameProperty().getValue();
	}
	
	
	public StringProperty addressProperty() {
		if (this.address == null) {
			this.address = new SimpleStringProperty(this, "address");
		}
		return this.address;
	}
	
	public void setAddress(String address) {
		this.addressProperty().setValue(address);
	}
	
	public String getAddress() {
		return this.addressProperty().getValue();
	}
	
	
	public StringProperty zipProperty() {
		if (this.zip == null) {
			this.zip = new SimpleStringProperty(this, "zip");
		}
		return this.zip;
	}
	
	public void setZip(String zip) {
		this.zipProperty().setValue(zip);
	}
	
	public void setZip(int zip) {
		setZip(Integer.toString(zip));
	}
	
	public String getZip() {
		return this.zipProperty().getValue();
	}
}
