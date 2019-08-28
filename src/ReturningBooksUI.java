import java.util.Scanner;


public class ReturningBooksUI {

	public static enum UiState {INITIALISED, READY, INSPECTING, COMPLETED}; //Was "UI_STATE" & deleted white space

	private ReturningBooksControl returnControl; //Was "CoNtRoL"
	private Scanner input; 
	private UiState state; //Was "StATe"

	
	public ReturningBooksUI(ReturningBooksControl control) {
		this.returnControl = control;
		input = new Scanner(System.in);
		state = UiState.INITIALISED;
		control.setUI(this);
	}


	public void runProgram() {		//Was "RuN"
		output("Return Book Use Case UI\n");
		
		while (true) {
			
			switch (state) {
			
			case INITIALISED:
				break;
				
			case READY:
				String bookString = input("Scan Book (<enter> completes): "); //Was "Book_STR"
				if (bookString.length() == 0) {
					returnControl.scanningComplete();
				}
				else {
					try {
						int bookId = Integer.valueOf(bookString).intValue(); //Was "Book_Id"
						returnControl.bookScanned(bookId);
					}
					catch (NumberFormatException e) {
						output("Invalid bookId");
					}					
				}
				break;				
				
			case INSPECTING:
				String ans = input("Is book damaged? (Y/N): ");
				boolean isDamaged = false; //Was "Is_Damaged"
				if (ans.toUpperCase().equals("Y")) {					
					isDamaged = true;
				}
				returnControl.dischargeLoan(isDamaged);
			
			case COMPLETED:
				output("Return processing complete");
				return;
			
			default:
				output("Unhandled state");
				throw new RuntimeException("ReturnBookUI : unhandled state :" + state);			
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
	
	public void setState(UiState state) { //Was "Set_State"
		this.state = state;
	}

	
}
