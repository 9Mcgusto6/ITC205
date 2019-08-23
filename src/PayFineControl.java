public class PayFineControl {
	
	private PayFineUI Ui;
	private enum CONTROL_STATE { INITIALISED, READY, PAYING, COMPLETED, CANCELLED };
	private CONTROL_STATE State;
	
	private library Library; //renamed LiBrAry to Library
	private member Member; //renamed MeMbEr to Member


	public PayFineControl() {
		this.Library = Library.INSTANCE();
		State = CONTROL_STATE.INITIALISED; //updated StAtE to State
	}
	
	
	public void Set_UI(PayFineUI ui) {
		if (!State.equals(CONTROL_STATE.INITIALISED)) {
			throw new RuntimeException("PayFineControl: cannot call setUI except in INITIALISED state");
		}	
		this.Ui = ui;
		ui.Set_State(PayFineUI.UI_STATE.READY);
		State = CONTROL_STATE.READY;		
	}


	public void Card_Swiped(int memberId) {
		if (!State.equals(CONTROL_STATE.READY)) {
			throw new RuntimeException("PayFineControl: cannot call cardSwiped except in READY state");
		}	
		Member = Library.MEMBER(memberId);
		
		if (Member == null) {
			Ui.Display("Invalid Member Id"); //renamed DiSplAY to Display
			return;
		}
		Ui.Display(Member.toString());
		Ui.Set_State(PayFineUI.UI_STATE.PAYING);
		State = CONTROL_STATE.PAYING;
	}
	
//Updated CaNcEl to Cancel	
	public void Cancel() {
		Ui.Set_State(PayFineUI.UI_STATE.CANCELLED);
		State = CONTROL_STATE.CANCELLED;
	}


	public double PaY_FiNe(double Amount) {
		if (!State.equals(CONTROL_STATE.PAYING)) {
			throw new RuntimeException("PayFineControl: cannot call payFine except in PAYING state");
		}	
		double Change = Member.Pay_Fine(Amount); //renamed ChAnGe to Change. AmOuNt to Amount
		if (Change > 0) {
			Ui.Display(String.format("Change: $%.2f", Change));
		}
		Ui.Display(Member.toString());
		Ui.Set_State(PayFineUI.UI_STATE.COMPLETED);
		State = CONTROL_STATE.COMPLETED;
		return Change;
	}
	


}
