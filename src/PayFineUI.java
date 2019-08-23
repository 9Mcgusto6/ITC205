import java.util.Scanner;


public class PayFineUI {


	public static enum UI_STATE { INITIALISED, READY, PAYING, COMPLETED, CANCELLED };

	private PayFineControl Control; //update CoNtRoL to Control
	private Scanner input;
	private UI_STATE State;

	
	public PayFineUI(PayFineControl control) {
		this.Control = control;
		input = new Scanner(System.in);
		State = UI_STATE.INITIALISED; //renamed StAtE to State
		control.Set_UI(this);
	}
	
	
	public void Set_State(UI_STATE state) {
		this.State = state;
	}

//renamed RuN to Run. However this means updating Main
	public void Run() {
		output("Pay Fine Use Case UI\n");
		
		while (true) {
			
			switch (State) {
	//Updated CaNcEl to Cancel		
			case READY:
				String Mem_Str = input("Swipe member card (press <enter> to cancel): ");
				if (Mem_Str.length() == 0) {
					Control.Cancel();
					break;
				}
				try {
					int Member_ID = Integer.valueOf(Mem_Str).intValue();
					Control.Card_Swiped(Member_ID);
				}
				catch (NumberFormatException e) {
					output("Invalid memberId");
				}
				break;
				
			case PAYING:
				double Amount = 0; //renamed AmouNT to Amount
				String Amt_Str = input("Enter amount (<Enter> cancels) : ");
				if (Amt_Str.length() == 0) {
					Control.Cancel(); //renamed CaNcEl to Cancel
					break;
				}
				try {
					Amount = Double.valueOf(Amt_Str).doubleValue();
				}
				catch (NumberFormatException e) {}
				if (Amount <= 0) {
					output("Amount must be positive");
					break;
				}
				Control.PaY_FiNe(Amount);
				break;
								
			case CANCELLED:
				output("Pay Fine process cancelled");
				return;
			
			case COMPLETED:
				output("Pay Fine process complete");
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
			
//updated DiSplAY to Display
	public void Display(Object object) {
		output(object);
	}


}
