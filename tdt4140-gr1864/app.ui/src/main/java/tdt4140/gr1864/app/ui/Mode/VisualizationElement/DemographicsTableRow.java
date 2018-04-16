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
	private StringProperty gender;
	private StringProperty age;
	private StringProperty numOfPersonInHouse;
	
	private int size;

	public DemographicsTableRow(String customerId, String firstName, String lastName, String address, String zip, String gender, String age, String numOfPersonInHouse) {
		setCustomerId(customerId);
		setFirstName(firstName);
		setLastName(lastName);
		setAddress(address);
		setZip(zip);
		setGender(gender);
		setAge(age);
		setNumOfPersonInHouse(numOfPersonInHouse);
		this.size = 8;
	}
	
	public DemographicsTableRow(int customerId, String firstName, String lastName, String address, int zip, String gender, int age, int numOfPersonInHouse) {
		setCustomerId(customerId);
		setFirstName(firstName);
		setLastName(lastName);
		setAddress(address);
		setZip(zip);
		setGender(gender);
		setAge(age);
		setNumOfPersonInHouse(numOfPersonInHouse);
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
	
	public StringProperty genderProperty() {
		if (this.gender == null) {
			this.gender = new SimpleStringProperty(this, "gender");
		}
		return this.gender;
	}
	
	public void setGender(String gender) {
		this.genderProperty().setValue(gender);
	}
	
	public String getGender() {
		return this.genderProperty().getValue();
	}
	
	public StringProperty ageProperty() {
		if (this.age == null) {
			this.age = new SimpleStringProperty(this, "age");
		}
		return this.age;
	}
	
	public void setAge(int age) {
		setAge(Integer.toString(age));
	}
	
	public void setAge(String age) {
		this.ageProperty().setValue(age);
	}
	
	public String getAge() {
		return this.ageProperty().getValue();
	}
	
	public StringProperty numOfPersonInHouseProperty() {
		if (this.numOfPersonInHouse == null) {
			this.numOfPersonInHouse = new SimpleStringProperty(this, "numOfPersonInHouse");
		}
		return this.numOfPersonInHouse;
	}
	
	public void setNumOfPersonInHouse(int numOfPersonInHouse) {
		setNumOfPersonInHouse(Integer.toString(numOfPersonInHouse));
	}
	
	public void setNumOfPersonInHouse(String numOfPersonInHouse) {
		this.numOfPersonInHouseProperty().setValue(numOfPersonInHouse);
	}
	
	public String getNumOfPersonInHouse() {
		return this.numOfPersonInHouseProperty().getValue();
	}
}
