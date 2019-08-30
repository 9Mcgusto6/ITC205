import java.util.ArrayList;
import java.util.List;

public class BorrowBookControl {
	
	private BorrowBookUI ui; // was UI
	

	private Library library; // was LIBRARY AS 28.8 class name changed from library to Library by J
	private member member; // was M //delete whitespace, CONTROL_STATE
	private enum ControlState {INITIALISED, READY, RESTRICTED, SCANNING, IDENTIFIED, FINALISING, COMPLETED, CANCELLED};
	private ControlState currentState; // was State
	
	private List<book> pending; // was PENDING
	private List<loan> complete; // was COMPLETED
	private book book; // was BOOK
	
	
	public BorrowBookControl() {
		this.library = library.instance();
		currentState = ControlState.INITIALISED;
	}
	

	public void setUI(BorrowBookUI ui) {
		if (!currentState.equals(ControlState.INITIALISED)) 
			throw new RuntimeException("BorrowBookControl: cannot call setUI except in INITIALISED state");
			
		this.ui = ui;
		ui.setState(BorrowBookUI.UIState.READY);
		currentState = ControlState.READY;		
	}

		
	public void swiped(int memberID) { //Was "Swiped" & "memberId"
		if (!currentState.equals(ControlState.READY)) 
			throw new RuntimeException("BorrowBookControl: cannot call cardSwiped except in READY state");
			
		member = library.currentMember(memberID); //was member, 28.8 MEMBER changed to currentMember by J
		if (member == null) {
			ui.display("Invalid memberId");
			return;
		}
		if (library.memberCanBorrow(member)) { //memberCanBorrow by J
			pending = new ArrayList<>();
			ui.setState(BorrowBookUI.UIState.SCANNING);
			currentState = ControlState.SCANNING; }

		else 
		{
			ui.display("Member cannot borrow at this time");
			ui.setState(BorrowBookUI.UIState.RESTRICTED); }}
	
	
	public void scanned(int bookId) { //bookId
		book = null;
		if (!currentState.equals(ControlState.SCANNING)) {
			throw new RuntimeException("BorrowBookControl: cannot call bookScanned except in SCANNING state");
		}	

		book = library.currentBook(bookId); //currentBook by J
		if (book == null) {
			ui.display("Invalid bookId");

		}
		if (!book.available()) { //no change
			ui.display("Book cannot be borrowed");
			return;
		}
		pending.add(book);
		for (book book : pending) { //was b
			ui.display(book.toString());
		}

		if (library.loansRemainingForMember(member) - pending.size() == 0) {// Loans_Remaining_For_Member is loansRemainingForMember J
			ui.display("Loan limit reached");
			complete();

		}
	}
	
	
	public void complete() { //Complete
		if (pending.size() == 0) {
			cancel();
		}
		else {
			ui.display("\nFinal Borrowing List");
			for (book bookLoan : pending) {
				ui.display(bookLoan.toString());
			}
			complete = new ArrayList<loan>();
			ui.setState(BorrowBookUI.UIState.FINALISING);
			currentState = ControlState.FINALISING;
		}
	}


	public void commitLoans() {
		if (!currentState.equals(ControlState.FINALISING)) {
			throw new RuntimeException("BorrowBookControl: cannot call commitLoans except in FINALISING state");
		}	

		for (book book : pending) { //B
			loan loan = library.issueLoan(book, member); //issueLoan J
			complete.add(loan);			

		}
		ui.display("Completed Loan Slip");
		for (loan loan : complete) { //LOAN
			ui.display(loan.toString());
		}
		ui.setState(BorrowBookUI.UIState.COMPLETED);
		currentState = ControlState.COMPLETED;
	}

	
	public void cancel() {
		ui.setState(BorrowBookUI.UIState.CANCELLED);
		currentState = ControlState.CANCELLED;
	}
	
	
}
