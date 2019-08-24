import java.io.Serializable;


@SuppressWarnings("serial")
public class book implements Serializable {
	
	private String title; // was TITLE
	private String author; // was AUTHOR
	private String callNo; // was CALLNO
	private int id; // was ID
	
	private enum STATE {AVAILABLE, ON_LOAN, DAMAGED, RESERVED}; //delete whitespace
	private STATE State;
	
	
	public book(String author, String title, String callNo, int id) {
		this.author = author;
		this.title = title;
		this.callNo = callNo;
		this.id = id;
		this.State = STATE.AVAILABLE;
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

	public String TITLE() { //TITLE
		return title;
	}


	
	public boolean AVAILABLE() { // AVAILABLE
		return State == STATE.AVAILABLE;
	}

	
	public boolean On_loan() { //On_loan
		return State == STATE.ON_LOAN;
	}

	
	public boolean IS_Damaged() { //Was "IsDamaged"
		return State == STATE.DAMAGED;
	}

	
	public void Borrow() {
		if (State.equals(STATE.AVAILABLE)) {
			State = STATE.ON_LOAN;
		}
		else {
			throw new RuntimeException(String.format("Book: cannot borrow while book is in state: %s", State));
		}
		
	}


	public void Return(boolean damaged) { //was DAMAGED
		if (State.equals(STATE.ON_LOAN)) {
			if (damaged) {
				State = STATE.DAMAGED;
			}
			else {
				State = STATE.AVAILABLE;
			}
		}
		else {
			throw new RuntimeException(String.format("Book: cannot Return while book is in state: %s", State));
		}		
	}

	
	public void Repair() { //was Repair
		if (State.equals(STATE.DAMAGED)) {
			State = STATE.AVAILABLE;
		}
		else {
			throw new RuntimeException(String.format("Book: cannot repair while book is in state: %s", State));
		}
	}


}
