 import java.util.Scanner;


public class FixBookUI {

	public static enum UI_STATE { INITIALISED, READY, FIXING, COMPLETED };
//CoNtRoL update to Control. StAtE is State
	private FixBookControl Control;
	private Scanner input;
	private UI_STATE State;

	
	public FixBookUI(FixBookControl control) {
		this.Control = control;
		input = new Scanner(System.in);
		State = UI_STATE.INITIALISED;
		control.Set_Ui(this);
	}


	public void Set_State(UI_STATE state) {
		this.State = state;
	}

//updated RuN to Run	
	public void Run() {
		output("Fix Book Use Case UI\n");
		
		while (true) {
			
			switch (State) {
//Book_STR changed to bookString			
			case READY:
				String bookString = input("Scan Book (<enter> completes): ");
				if (bookString.length() == 0) {
					Control.Scanning_complete();
				}
				else {
					try {
		//Book_ID changed to bookID
						int bookID = Integer.valueOf(bookString).intValue();
						Control.Book_scanned(bookID);
					}
					catch (NumberFormatException e) {
						output("Invalid bookId");
					}
				}
				break;	
//updated AnS to Answer . FiX updated to Fix.FIX_Book changed to fixBook.. fixBook caused issues need more work				
			case FIXING:
				String Answer = input("Fix Book? (Y/N) : ");
				boolean Fix = false;
				if (Answer.toUpperCase().equals("Y")) {
					Fix = true;
				}
				Control.FIX_Book(Fix);
				break;
								
			case COMPLETED:
				output("Fixing process complete");
				return;
			
			default:
				output("Unhandled state");
				throw new RuntimeException("FixBookUI : unhandled state :" + State);			
			
			}		
		}
		
	}

	
	private String input(String prompt) {
		System.out.print(prompt);
		return input.nextLine();
	}	
		
		
	private void output(Object object) {
		System.out.println(object);
	}
	

	public void display(Object object) {
		output(object);
	}
	
	
}
