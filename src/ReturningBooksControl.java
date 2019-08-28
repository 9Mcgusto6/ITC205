public class ReturningBooksControl {

	private ReturningBooksUI ui; //Was "Ui"
	private enum ControlState {INITIALISED, READY, INSPECTING}; //Was "CONTROL_STATE" & deleted whitespace
	private ControlState currentState; //Was "sTaTe"
	
	private Library currentLibrary; //Was "lIbRaRy"
	private loan currentLoan; //Was "CurrENT_loan"
	

	public ReturningBooksControl() {
		this.currentLibrary = currentLibrary.INSTANCE();
		currentState = ControlState.INITIALISED;
	}
	
	
	public void setUI(ReturningBooksUI ui) { //Was "Set_UI" 
		if (!currentState.equals(ControlState.INITIALISED)) {
			throw new RuntimeException("ReturnBookControl: cannot call setUI except in INITIALISED state");
		}	
		this.ui = ui;
		ui.setState(ReturningBooksUI.UiState.READY);
		currentState = ControlState.READY;		
	}


	public void bookScanned(int bookId) { //Was "Book_scanned" & "Book_ID"
		if (!currentState.equals(ControlState.READY)) {
			throw new RuntimeException("ReturnBookControl: cannot call bookScanned except in READY state");
		}	
		book currentBook = currentLibrary.currentBook(bookId); //Was "CUR_book"
		
		if (currentBook == null) {
			ui.display("Invalid Book Id");
			return;
		}
		if (!currentBook.On_loan()) {
			ui.display("Book has not been borrowed");
			return;
		}		
		currentLoan = currentLibrary.loanByBookId(bookId);	
		double overdueFine = 0.0; //Was "Over_Due_Fine"
		if (currentLoan.OVer_Due()) {
			overdueFine = currentLibrary.calculateOverdueFine(currentLoan);
		}
		ui.display("Inspecting");
		ui.display(currentBook.toString());
		ui.display(currentLoan.toString());
		
		if (currentLoan.OVer_Due()) {
			ui.display(String.format("\nOverdue fine : $%.2f", overdueFine));
		}
		ui.setState(ReturningBooksUI.UiState.INSPECTING);
		currentState = ControlState.INSPECTING;		
	}


	public void scanningComplete() { //Was "Scanning_Complete"
		if (!currentState.equals(ControlState.READY)) {
			throw new RuntimeException("ReturnBookControl: cannot call scanningComplete except in READY state");
		}	
		ui.setState(ReturningBooksUI.UiState.COMPLETED);		
	}


	public void dischargeLoan(boolean isDamaged) { //Was "Discharge_loan"
		if (!currentState.equals(ControlState.INSPECTING)) {
			throw new RuntimeException("ReturnBookControl: cannot call dischargeLoan except in INSPECTING state");
		}	
		currentLibrary.dischargeLoan(currentLoan, isDamaged);
		currentLoan = null;
		ui.setState(ReturningBooksUI.UiState.READY);
		currentState = ControlState.READY;				
	}


}
