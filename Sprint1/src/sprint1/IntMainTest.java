package sprint1;
import java.util.List;
import java.util.Scanner;
import java.util.Date;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class IntMainTest {
	
	 static LocalDateTime date = LocalDateTime.now();
     static PersistanceManager pm = new PersistanceManager();
     static SiteManager sm = new SiteManager();
     static List<String> member_firstnames = new ArrayList<>();
     static List<String> member_lastnames = new ArrayList<>();
     static String[] email_end = {"@gmail.com", "@yahoo.com", "@hotmail.com", "@icloud.com"};
     static String[] random_names = {"Maupassant", "Legacy","Bombarder","Disunion","Procurer","Accentuable","Tenser","Renormalizing","Histochemistry","Unevenness"};
     static String lorem_ipsum = "is a long established fact that a reader will be distracted by the "
     		+ "readable content of a page when looking at its layout. The point of using Lorem Ipsum is "
     		+ "that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', "
     		+ "making it look like readable English.";
    public static void main(String[] args) {
    	try {
    		File name_file = new File("member_names.txt");
    		Scanner scan = new Scanner(name_file);
    		while(scan.hasNextLine()) {
    			String name = scan.nextLine();
    			member_firstnames.add(name.split(" ")[0]);
    			member_lastnames.add(name.split(" ")[1]);
    		}
    	}catch(Exception e) {
    		log(e.toString());
    	}
    	
        setUpData();
        
    }
    
    
    static void setUpData() {
    	log("Setting up data...");
    	int size = member_lastnames.size();
    	int numberOfGroups = 10;
    	
    	
    	while(size > 0) {
    		sm.addMember(member_firstnames.get(size - 1), member_lastnames.get(size - 1), member_firstnames.get(size - 1),  member_lastnames.get(size - 1) + email_end[size % email_end.length], date);
    		size--;
    	}
    	
    	while(numberOfGroups > 0) {
    		sm.addGroup(random_names[numberOfGroups - 1], lorem_ipsum, date);
    		numberOfGroups--;
    	}
    	
    	for(Member m: sm.getMembers()) {
    		int random = (int) (Math.random() * 100) + 1;
    		sm.getMember(m.getEmailAddress()).joinGroup(sm.getGroups().get(random % random_names.length), date);
    	}
    	for(Group g : sm.getGroups()) {
    		log("Group: " + g.getTitle() + " has " + g.getNumOfMembers() + " Members");
    	}
    	
    	
    }
    
    static void log(String msg) {
    	System.out.println(msg);
    }
    static void log(int num) {
    	System.out.println(num);
    }
   
   
    
    
  
    
   
    
    static void log(boolean msg) {
    	System.out.println(msg);
    }

}