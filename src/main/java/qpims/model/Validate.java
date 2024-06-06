package qpims.model;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Validate {
	private static Validate instance;
	private Validate() {
	}
	public static Validate getInstance() {
		if (instance == null) {
			instance = new Validate();
		}
		return instance;
	}
	
	//validate search input
	public boolean validateSearchInput(String searchInput) {
		if(searchInput.isEmpty()) {
			MessageBox.getInstance().showWarning("Search field is empty.");
			return false;
		}
		//make sure search input is only letters and numbers
		if(!searchInput.matches("^[a-zA-Z0-9 ]*$")) {
			MessageBox.getInstance().showWarning("Search input must only contain alphabet characters and numbers.");
			return false;
		}
		return true;
	}
	
	//validate customer inputs
	public boolean validateCustomer(String firstName, String lastName, String email, String phone) {
		if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty()) {
			MessageBox.getInstance().showWarning("All fields are required.");
			return false;
		}
		//check if email is in the correct format
		if(!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
			MessageBox.getInstance().showWarning("Invalid email address. Email must be in the format example@example.com.");
			return false;
		}
		//check if phone is only numbers +61 123 456 789 or +61123456789 or 1234567890 or +911 123 456 789
		if(!phone.matches("^\\+?[0-9]{1,3} ?[0-9]{3} ?[0-9]{3} ?[0-9]{3}$")) {
			MessageBox.getInstance().showWarning("Phone number must be in the format +61 123 456 789 or +61123456789 or 1234567890 or +911 123 456 789.");
			return false;
		}
		
		//check if first name and last name are only letters
		if(!firstName.matches("^[a-zA-Z]*$") || !lastName.matches("^[a-zA-Z]*$")) {
			MessageBox.getInstance().showWarning("First name and last name must only contain alphabet characters.");
			return false;
		}
		
		return true;
	}
	//validate property inputs
	public boolean validateProperty(String address, String description, String year, String agentName, String propertyType, String associatedCustomer) {
		if (address.isEmpty() || description.isEmpty() || year.isEmpty() || agentName.isEmpty() || propertyType.isEmpty() || associatedCustomer.isEmpty()) {
			MessageBox.getInstance().showWarning("All fields are required.");
			return false;
		}
		//check if year is only numbers
		if(!year.matches("^[0-9]{4}$")) {
			MessageBox.getInstance().showWarning("Year must contain numbers and be 4-digit.");
			return false;
		}
		// Check if agent name is only letters and contains a space
		if(!agentName.matches("^[a-zA-Z ]*$")) {
			MessageBox.getInstance().showWarning("Agent name must only contain alphabet characters and a space.");
			return false;
		}

		//check if address is only letters and numbers and spaces and commas and /
		if(!address.matches("^[a-zA-Z0-9 ,/]*$")) {
			MessageBox.getInstance().showWarning("Address must only contain alphabet characters, numbers, spaces, commas,and /.");
			return false;
		}
		
		//check if description is only letters and numbers and spaces and commas and .
		if(!description.matches("^[a-zA-Z0-9 ,.]*$")) {
			MessageBox.getInstance().showWarning("Description must only contain alphabet characters, numbers, spaces, commas and periods.");
			return false;
		}
		
		return true;
	}
	
	//validate Booking inputs
	public boolean validateBooking(String description, String bookingDate, String completionDate, String charge, String staffName, String jobType, String propertyId) {
		if (description.isEmpty() || bookingDate.isEmpty() || completionDate.isEmpty() || charge.isEmpty() || staffName.isEmpty() || jobType.isEmpty() || propertyId.isEmpty()) {
			MessageBox.getInstance().showWarning("All fields are required.");
			return false;
		}
		//check if charge is only numbers or decimal
		if(!charge.matches("^[0-9]*\\.?[0-9]*$")) {
			MessageBox.getInstance().showWarning("Charge must only contain numbers or decimal.");
			return false;
		}
		//check if staff name is only letters
		if(!staffName.matches("^[a-zA-Z ]*$")) {
			MessageBox.getInstance().showWarning("Staff name must only contain alphabet characters.");
			return false;
		}
		//check if description is only letters and numbers and spaces and commas and .
		if(!description.matches("^[a-zA-Z0-9 ,.]*$")) {
			MessageBox.getInstance().showWarning("Description must only contain alphabet characters, numbers, spaces, commas and periods.");
			return false;
		}
		//check if booking date is in the correct formats: yyyy-MM-dd, yyyy/MM/dd, yyyy.MM.dd, yyyy MM dd or dd-MM-yyyy, dd/MM/yyyy, dd.MM.yyyy, dd MM yyyy
		if(!bookingDate.matches("^(\\d{4}[- /.](0[1-9]|1[0-2])[- /.](0[1-9]|[12][0-9]|3[01]))|(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[0-2])[- /.]\\d{4}$")) {
			MessageBox.getInstance().showWarning("Invalid booking date. Date must be in the format yyyy-MM-dd, dd-MM-yyyy.");
			return false;
		}
		//check if completion date is in the correct formats: yyyy-MM-dd, yyyy/MM/dd, yyyy.MM.dd, yyyy MM dd or dd-MM-yyyy, dd/MM/yyyy, dd.MM.yyyy, dd MM yyyy
		if(!completionDate.matches("^(\\d{4}[- /.](0[1-9]|1[0-2])[- /.](0[1-9]|[12][0-9]|3[01]))|(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[0-2])[- /.]\\d{4}$")) {
			MessageBox.getInstance().showWarning("Invalid completion date. Date must be in the format yyyy-MM-dd, dd-MM-yyyy.");
			return false;
		}
		//check if booking date is before completion date
		if (LocalDate.parse(bookingDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")).isAfter(LocalDate.parse(completionDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")))) {
			MessageBox.getInstance().showWarning("Completion date must be after booking date.");
			return false;
		}
		
		return true;
	}
}
