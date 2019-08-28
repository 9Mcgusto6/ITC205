import java.text.SimpleDateFormat;
import java.util.Scanner;


public class Main {
	
	private static Scanner IN;
	private static Library LIB;
	private static String MENU;
	private static Calendar CAL;
	private static SimpleDateFormat SDF;
	
	
	private static String Get_menu() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("\nLibrary Main Menu\n\n")
		  .append("  M  : add member\n")
		  .append("  LM : list members\n")
		  .append("\n")
		  .append("  B  : add book\n")
		  .append("  LB : list books\n")
		  .append("  FB : fix books\n")
		  .append("\n")
		  .append("  L  : take out a loan\n")
		  .append("  R  : return a loan\n")
		  .append("  LL : list loans\n")
		  .append("\n")
		  .append("  P  : pay fine\n")
		  .append("\n")
		  .append("  T  : increment date\n")
		  .append("  Q  : quit\n")
		  .append("\n")
		  .append("Choice : ");
		  
		return sb.toString();
	}


	public static void main(String[] args) {		
		try {			
			IN = new Scanner(System.in);

			LIB = Library.instance(); //Library J
			CAL = Calendar.instance();

			SDF = new SimpleDateFormat("dd/MM/yyyy");
	
			for (member m : LIB.membersArray()) {
				output(m);
			}
			output(" ");
			for (book b : LIB.booksArray()) {
				output(b);
			}
						
			MENU = Get_menu();
			
			boolean e = false;
			
			while (!e) {
				
				output("\n" + SDF.format(CAL.date()));
				String c = input(MENU);
				
				switch (c.toUpperCase()) {
				
				case "M": 
					ADD_MEMBER();
					break;
					
				case "LM": 
					MEMBERS();
					break;
					
				case "B": 
					ADD_BOOK();
					break;
					
				case "LB": 
					BOOKS();
					break;
					
				case "FB": 
					FIX_BOOKS();
					break;
					
				case "L": 
					BORROW_BOOK();
					break;
					
				case "R": 
					RETURN_BOOK();
					break;
					
				case "LL": 
					CURRENT_LOANS();
					break;
					
				case "P": 
					FINES();
					break;
					
				case "T": 
					INCREMENT_DATE();
					break;
					
				case "Q": 
					e = true;
					break;
					
				default: 
					output("\nInvalid option\n");
					break;
				}
				
				Library.save();
			}			
		} catch (RuntimeException e) {
			output(e);
		}		
		output("\nEnded\n");
	}	

		private static void FINES() {
		new PayFineUI(new PayFineControl()).RuN();		
	}


	private static void CURRENT_LOANS() {
		output("");
		for (loan loan : LIB.currentLoans()) {
			output(loan + "\n");
		}		
	}



	private static void BOOKS() {
		output("");
		for (book book : LIB.booksArray()) {
			output(book + "\n");
		}		
	}



	private static void MEMBERS() {
		output("");
		for (member member : LIB.membersArray()) {
			output(member + "\n");
		}		
	}



	private static void BORROW_BOOK() {
		new BorrowBookUI(new BorrowBookControl()).run();		
	}


	private static void RETURN_BOOK() {
		new ReturningBooksUI(new ReturningBooksControl()).runProgram();		
	}


	private static void FIX_BOOKS() {
		new FixBookUI(new FixBookControl()).RuN();		
	}


	private static void INCREMENT_DATE() {
		try {
			int days = Integer.valueOf(input("Enter number of days: ")).intValue();
			CAL.incrementDate(days);
			LIB.checkCurrentLoans();
			output(SDF.format(CAL.date()));
			
		} catch (NumberFormatException e) {
			 output("\nInvalid number of days\n");
		}
	}


	private static void ADD_BOOK() {
		
		String A = input("Enter author: ");
		String T  = input("Enter title: ");
		String C = input("Enter call number: ");
		book B = LIB.addBook(A, T, C);
		output("\n" + B + "\n");
		
	}

	
	private static void ADD_MEMBER() {
		try {
			String LN = input("Enter last name: ");
			String FN  = input("Enter first name: ");
			String EM = input("Enter email: ");
			int PN = Integer.valueOf(input("Enter phone number: ")).intValue();
			member M = LIB.addMember(LN, FN, EM, PN);
			output("\n" + M + "\n");
			
		} catch (NumberFormatException e) {
			 output("\nInvalid phone number\n");
		}
		
	}


	private static String input(String prompt) {
		System.out.print(prompt);
		return IN.nextLine();
	}
	
	
	
	private static void output(Object object) {
		System.out.println(object);
	}

	
}
