public class FixBookControl {
//CONTROL_STATE updated to ControlState	
	private FixBookUI UI;
	private enum ControlState { INITIALISED, READY, FIXING };
	private ControlState State;
//	updated StAtE to State
	private library LIB;
	private book CurrentBook;
//Cur_book updated to CurrentBook. library updated to Library. book updated to Book

	public FixBookControl() {
		this.LIB = LIB.INSTANCE();
		State = ControlState.INITIALISED;
	}
	
	
	public void Set_Ui(FixBookUI ui) {
		if (!State.equals(ControlState.INITIALISED)) {
			throw new RuntimeException("FixBookControl: cannot call setUI except in INITIALISED state");
		}	
		this.UI = ui;
		ui.Set_State(FixBookUI.UI_STATE.READY);
		State = ControlState.READY;		
	}


	public void Book_scanned(int bookId) {
		if (!State.equals(ControlState.READY)) {
			throw new RuntimeException("FixBookControl: cannot call bookScanned except in READY state");
		}	
		CurrentBook = LIB.Book(bookId);
		
		if (CurrentBook == null) {
			UI.display("Invalid bookId");
			return;
		}
		if (!CurrentBook.IS_Damaged()) {
			UI.display("Book has not been damaged");
			return;
		}
		UI.display(CurrentBook.toString());
		UI.Set_State(FixBookUI.UI_STATE.FIXING);
		State = ControlState.FIXING;		
	}


	public void FIX_Book(boolean MUST_fix) {
		if (!State.equals(ControlState.FIXING)) {
			throw new RuntimeException("FixBookControl: cannot call fixBook except in FIXING state");
		}	
		if (MUST_fix) {
			LIB.Repair_BOOK(CurrentBook);
		}
		CurrentBook = null;
		UI.Set_State(FixBookUI.UI_STATE.READY);
		State = ControlState.READY;		
	}

	//updated SCannING_COMplete to Scanning_Complete
	public void Scanning_complete() {
		if (!State.equals(ControlState.READY)) {
			throw new RuntimeException("FixBookControl: cannot call scanningComplete except in READY state");
		}	
		UI.Set_State(FixBookUI.UI_STATE.COMPLETED);		
	}






}
