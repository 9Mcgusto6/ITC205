import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class member implements Serializable { //Can't get class name to change without warnings

	private String lastName; //Was "LN"
	private String firstName; //Was "FN"
	private String email; //Was "EM"
	private int phoneNumber; //Was "PN"
	private int id; //Was "ID"
	private double fines; //Was "FINES"
	
	private Map<Integer, loan> lns; //Was "LNS"

	
	public member(String lastName, String firstName, String email, int phoneNo, int id) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
		this.phoneNumber = phoneNo;
		this.id = id;
		
		this.lns = new HashMap<>();
	}

	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Member:  ").append(id).append("\n")
		  .append("  Name:  ").append(lastName).append(", ").append(firstName).append("\n")
		  .append("  Email: ").append(email).append("\n")
		  .append("  Phone: ").append(phoneNumber)
		  .append("\n")
		  .append(String.format("  Fines Owed :  $%.2f", fines))
		  .append("\n");
		
		for (loan loan : lns.values()) { //Was "LoAn"
			sb.append(loan).append("\n");
		}		  
		return sb.toString();
	}

	
	public int getId() { //Was "GeT_ID"
		return id;
	}

	
	public List<loan> getLoans() { //Was "GeT_LoAnS"
		return new ArrayList<loan>(lns.values());
	}

	
	public int numberOfCurrentLoans() { //Was "Number_Of_Current_Loans"
		return lns.size();
	}

	
	public double finesOwed() { //Was "Fines_OwEd"
		return fines;
	}

	
	public void takeOutLoan(loan loan) { //Was "Take_Out_Loan"
		if (!lns.containsKey(loan.ID())) {
			lns.put(loan.ID(), loan);
		}
		else {
			throw new RuntimeException("Duplicate loan added to member");
		}		
	}

	
	public String getLastName() { //Was "Get_LastName"
		return lastName;
	}

	
	public String getFirstName() { //Was "Get_FirstName"
		return firstName;
	}


	public void addFine(double fine) { //Was "Add_Fine"
		fines += fine;
	}
	
	public double payFine(double amount) { //Was "Pay_Fine" & "AmOuNt"
		if (amount < 0) {
			throw new RuntimeException("Member.payFine: amount must be positive");
		}
		double change = 0;
		if (amount > fines) {
			change = amount - fines;
			fines = 0;
		}
		else {
			fines -= amount;
		}
		return change;
	}


	public void dischargeLoan(loan loan) { //Was "dIsChArGeLoAn" & "loan LoAn"
		if (lns.containsKey(loan.ID())) {
			lns.remove(loan.ID());
		}
		else {
			throw new RuntimeException("No such loan held by member");
		}		
	}

}
