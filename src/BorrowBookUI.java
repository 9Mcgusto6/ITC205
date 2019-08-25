import java.util.Scanner;


public class BorrowBookUI {
	//delete whitespace was UI_STATE
	public static enum UIState {INITIALISED, READY, RESTRICTED, SCANNING, IDENTIFIED, FINALISING, COMPLETED, CANCELLED};

	private BorrowBookControl control; //was CONTROL
	private Scanner input;
	private UIState state; //was StaTe

	
	public BorrowBookUI(BorrowBookControl control) {
		this.control = control;
		input = new Scanner(System.in);
		state = UIState.INITIALISED;
		control.setUI(this);
	}

	
	private String input(String prompt) {
		System.out.print(prompt);
		return input.nextLine();
	}	
		
		
	private void output(Object object) {
		System.out.println(object);
	}
	
			
	public void setState(UIState STATE) { // was Set_State
		this.state = STATE;
	}

	
	public void run() {
		output("Borrow Book Use Case UI\n");
		
		while (true) {
			
			switch (state) {			
			
			case CANCELLED:
				output("Borrowing Cancelled");
				return;

				
			case READY:
				String member = input("Swipe member card (press <enter> to cancel): "); //was MEM_STR
				if (member.length() == 0) {
					control.cancel();
					break;
				}
				try {
					int memberID = Integer.valueOf(member).intValue(); //was Member_ID
					control.swiped(memberID);
				}
				catch (NumberFormatException e) {
					output("Invalid Member Id");
				}
				break;

				
			case RESTRICTED:
				input("Press <any key> to cancel");
				control.cancel();
				break;
			
				
			case SCANNING:
				String bookStr = input("Scan Book (<enter> completes): "); //was Book_Str
				if (bookStr.length() == 0) {
					control.complete();
					break;
				}
				try {
					int bookID = Integer.valueOf(bookStr).intValue();  //was BiD
					control.scanned(bookID);
					
				} catch (NumberFormatException e) {
					output("Invalid Book Id");
				} 
				break;
					
				
			case FINALISING:
				String Ans = input("Commit loans? (Y/N): "); //was Ans
				if (Ans.toUpperCase().equals("N")) {
					control.cancel();
					
				} else {
					control.commitLoans(); //Commit_LOans
					input("Press <any key> to complete ");
				}
				break;
				
				
			case COMPLETED:
				output("Borrowing Completed");
				return;
	
				
			default:
				output("Unhandled state");
				throw new RuntimeException("BorrowBookUI : unhandled state :" + state);			
			}
		}		
	}


	public void display(Object object) { //Was "Display"
		output(object);		
	}


}
