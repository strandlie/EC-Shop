package tdt4140.gr1864.app.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="user", 
	   uniqueConstraints={@UniqueConstraint(columnNames={"user_id"})})
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id", nullable=false, unique=true, length=11)
	private int userId;
	
	@Column(name="first_name", length=30, nullable=true)
	private String firstName;
	
	@Column(name="last_name", length=30, nullable=true)
	private String lastName;
	
	public User(int userId, String firstName, String lastName) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + userId;
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
		User other = (User) obj;
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
		if (userId != other.userId)
			return false;
		return true;
	}
}

