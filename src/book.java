import java.io.Serializable;


@SuppressWarnings("serial")
public class book implements Serializable {
	
	private String title; // was TITLE
	private String author; // was AUTHOR
	private String callNo; // was CALLNO
	private int id; // was ID
	
	private enum State {AVAILABLE, ON_LOAN, DAMAGED, RESERVED}; //delete whitespace, STATE
	private State State;
	
	
	public book(String author, String title, String callNo, int id) {
		this.author = author;
		this.title = title;
		this.callNo = callNo;
		this.id = id;
		this.State = State.AVAILABLE;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Book: ").append(id).append("\n")
		  .append("  Title:  ").append(title).append("\n")
		  .append("  Author: ").append(author).append("\n")
		  .append("  CallNo: ").append(callNo).append("\n")
		  .append("  State:  ").append(State);
		
		return sb.toString();
	}

	public Integer ID() { //ID
		return id;
	}

	public String title() { //TITLE
		return title;
	}


	
	public boolean available() { // AVAILABLE
		return State == State.AVAILABLE;
	}

	
	public boolean onLoan() { //On_loan
		return State == State.ON_LOAN;
	}

	
	public boolean isDamaged() { //Was "IS_Damaged"
		return State == State.DAMAGED;
	}

	
	public void borrow() { //Borrow
		if (State.equals(State.AVAILABLE)) {
			State = State.ON_LOAN;
		}
		else {
			throw new RuntimeException(String.format("Book: cannot borrow while book is in state: %s", State));
		}
		
	}


	public void returnBook(boolean damaged) { //was DAMAGED, Return
		if (State.equals(State.ON_LOAN)) {
			if (damaged) {
				State = State.DAMAGED;
			}
			else {
				State = State.AVAILABLE;
			}
		}
		else {
			throw new RuntimeException(String.format("Book: cannot Return while book is in state: %s", State));
		}		
	}

	
	public void repair() { //was Repair
		if (State.equals(State.DAMAGED)) {
			State = State.AVAILABLE;
		}
		else {
			throw new RuntimeException(String.format("Book: cannot repair while book is in state: %s", State));
		}
	}


}
