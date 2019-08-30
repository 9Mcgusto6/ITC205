import java.io.Serializable;


@SuppressWarnings("serial")
public class book implements Serializable {
	
	private String title; // was TITLE
	private String author; // was AUTHOR
	private String callNo; // was CALLNO
	private int id; // was ID
	
	private enum State {AVAILABLE, ON_LOAN, DAMAGED, RESERVED}; //delete whitespace, STATE
	private State state; //State changed to state as per review
	
	
	public book(String author, String title, String callNo, int id) {
		this.author = author;
		this.title = title;
		this.callNo = callNo;
		this.id = id;
		this.state = state.AVAILABLE;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Book: ").append(id).append("\n")
		  .append("  Title:  ").append(title).append("\n")
		  .append("  Author: ").append(author).append("\n")
		  .append("  CallNo: ").append(callNo).append("\n")
		  .append("  State:  ").append(state);
		
		return sb.toString();
	}

	public Integer ID() { //ID
		return id;
	}

	public String title() { //TITLE
		return title;
	}


	
	public boolean available() { // AVAILABLE
		return state == state.AVAILABLE;
	}

	
	public boolean onLoan() { //On_loan
		return state == state.ON_LOAN;
	}

	
	public boolean isDamaged() { //Was "IS_Damaged"
		return state == state.DAMAGED;
	}

	
	public void borrow() { //Borrow
		if (state.equals(state.AVAILABLE)) {
			state = state.ON_LOAN;
		}
		else {
			throw new RuntimeException(String.format("Book: cannot borrow while book is in state: %s", state));
		}
		
	}


	public void returnBook(boolean damaged) { //was DAMAGED, Return
		if (state.equals(state.ON_LOAN)) {
			if (damaged) {
				state = state.DAMAGED;
			}
			else {
				state = state.AVAILABLE;
			}
		}
		else {
			throw new RuntimeException(String.format("Book: cannot Return while book is in state: %s", state));
		}		
	}

	
	public void repair() { //was Repair
		if (state.equals(state.DAMAGED)) {
			state = state.AVAILABLE;
		}
		else {
			throw new RuntimeException(String.format("Book: cannot repair while book is in state: %s", state));
		}
	}


}
