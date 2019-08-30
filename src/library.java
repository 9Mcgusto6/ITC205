
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class Library implements Serializable {
	
	private static final String LIBRARY_FILE = "library.obj"; //Was "libraryFile"
	private static final int LOAN_LIMIT = 2; //Was "loanLimit"
	private static final int LOAN_PERIOD = 2; //Was "loanPeriod"
	private static final double FINES_PER_DAY = 1.0; //Was "finesPerDay"
	private static final double MAX_FINES_OWED = 1.0; //Was "maxFinesOwed"
	private static final double DAMAGE_FEE = 2.0; //Was "damageFee"
	
	private static Library self; //Was "SeLf"
	private int bookId; //Was "BOOK_ID"
	private int memberId; //Was "MEMBER_ID"
	private int loanId; //Was "LOAN_ID"
	private Date loanDate; //Was "LOAN_DATE"
	
	private Map<Integer, book> catalogue; //Was "CATALOG"
	private Map<Integer, member> membersMap; //Was "MEMBERS"
	private Map<Integer, loan> loansMap; //Was "LOANS"
	private Map<Integer, loan> currentLoansMap; //Was "CURRENT_LOANS"
	private Map<Integer, book> damagedBooksMap; //Was "DAMAGED_BOOKS"
	

	private Library() {
		catalogue = new HashMap<>();
		membersMap = new HashMap<>();
		loansMap = new HashMap<>();
		currentLoansMap = new HashMap<>();
		damagedBooksMap = new HashMap<>();
		bookId = 1;
		memberId = 1;		
		loanId = 1;		
	}

	
	public static synchronized Library instance() {		
		if (self == null) {
			Path path = Paths.get(LIBRARY_FILE);	//Was "Path PATH"		
			if (Files.exists(path)) {	
				try (ObjectInputStream lif = new ObjectInputStream(new FileInputStream(LIBRARY_FILE));) { //Was "LiF"
			    
					self = (Library) lif.readObject();
					Calendar.instance().setDate(self.loanDate); // instance setDate AS
					lif.close();
				}
				catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			else self = new Library();
		}
		return self;
	}

	
	public static synchronized void save() { //Was "SAVE"
		if (self != null) {
			self.loanDate = Calendar.instance().date();
			try (ObjectOutputStream lof = new ObjectOutputStream(new FileOutputStream(LIBRARY_FILE));) { //Was "LoF"
				lof.writeObject(self);
				lof.flush();
				lof.close();	
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	
	public int currentBookId() { //Was "BookID"
		return bookId;
	}
	
	
	public int currentMemberId() { //Was "MemberID"
		return memberId;
	}
	
	
	private int nextBookId() { //Was "NextBID"
		return bookId++;
	}

	
	private int nextMemberId() { //Was "NextMID"
		return memberId++;
	}

	
	private int nextLoanId() { //Was "NextLID"
		return loanId++;
	}

	
	public List<member> membersArray() {		//Was "MEMBERS"
		return new ArrayList<member>(membersMap.values()); 
	}


	public List<book> booksArray() {		//Was "BOOKS"
		return new ArrayList<book>(catalogue.values()); 
	}


	public List<loan> currentLoans() { //Was "CurrentLoans"
		return new ArrayList<loan>(currentLoansMap.values());
	}


	public member addMember(String lastName, String firstName, String email, int phoneNo) {		//Was "Add_mem"
		member member = new member(lastName, firstName, email, phoneNo, nextMemberId());
		membersMap.put(member.getId(), member);		
		return member;
	}

	
	public book addBook(String a, String t, String c) {		//Was "Add_book"
		book b = new book(a, t, c, nextBookId());
		catalogue.put(b.ID(), b);		
		return b;
	}

	
	public member currentMember(int memberId) { //Was "MEMBER"
		if (membersMap.containsKey(memberId)) 
			return membersMap.get(memberId);
		return null;
	}

	
	public book currentBook(int bookId) { //Was "Book"
		if (catalogue.containsKey(bookId)) 
			return catalogue.get(bookId);		
		return null;
	}

	
	public int loanLimit() { //Was "LOAN_LIMIT"
		return LOAN_LIMIT;
	}

	
	public boolean memberCanBorrow(member member) {		//Was "MEMBER_CAN_BORROW"
		if (member.numberOfCurrentLoans() == LOAN_LIMIT ) 
			return false;
				
		if (member.finesOwed() >= MAX_FINES_OWED) 
			return false;
				
		for (loan loan : member.getLoans()) 
			if (loan.OVer_Due()) 
				return false;
			
		return true;
	}

	
	public int loansRemainingForMember(member member) {	//Was "Loans_Remaining_For_Member"	
		return LOAN_LIMIT - member.numberOfCurrentLoans();
	}

	
	public loan issueLoan(book book, member member) {  //Was "ISSUE_LAON"
		Date dueDate = Calendar.instance().dueDate(LOAN_PERIOD);
		loan loan = new loan(nextLoanId(), book, member, dueDate);
		member.takeOutLoan(loan);
		book.borrow(); //Borrow As
		loansMap.put(loan.ID(), loan);
		currentLoansMap.put(book.ID(), loan);
		return loan;
	}
	
	
	public loan loanByBookId(int bookId) { //Was "LOAN_BY_BOOK_ID"
		if (currentLoansMap.containsKey(bookId)) {
			return currentLoansMap.get(bookId);
		}
		return null;
	}

	
	public double calculateOverdueFine(loan loan) { //Was "CalculateOverDueFine"
		if (loan.OVer_Due()) {
			long daysOverDue = Calendar.instance().getDaysDifference(loan.Get_Due_Date());//getDaysDifference AS
			double fine = daysOverDue * FINES_PER_DAY;
			return fine;
		}
		return 0.0;		
	}


	public void dischargeLoan(loan currentLoan, boolean isDamaged) { //Was "Discharge_loan"
		member member = currentLoan.Member();
		book book  = currentLoan.Book();
		
		double overDueFine = calculateOverdueFine(currentLoan);
		member.addFine(overDueFine);	
		
		member.dischargeLoan(currentLoan);
		book.returnBook(isDamaged); //returnBook
		if (isDamaged) {
			member.addFine(DAMAGE_FEE);
			damagedBooksMap.put(book.ID(), book);
		}
		currentLoan.DiScHaRgE();
		currentLoansMap.remove(book.ID());
	}


	public void checkCurrentLoans() {
		for (loan loan : currentLoansMap.values()) {
			loan.checkOverDue();
		}		
	}


	public void repairBook(book currentBook) { //Was "Repair_BOOK"
		if (damagedBooksMap.containsKey(currentBook.ID())) {
			currentBook.repair();
			damagedBooksMap.remove(currentBook.ID());
		}
		else {
			throw new RuntimeException("Library: repairBook: book is not damaged");
		}
		
	}
	
	
}
