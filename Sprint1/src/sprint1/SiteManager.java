package sprint1;

import java.time.LocalDateTime;
import java.util.List;

class SiteManager {

   private List<Member> memberList = new ArrayList<>();
   private List<Group> groupList = new ArrayList<>();
   
   //Constructor, no responsibilities
   public SiteManager() {
   }
   
   //Adds a new Member provided they don’t already exist returning whether successful or not
   public boolean addMember(String firstName, String lastName, String screenName, String emailAddress, LocalDateTime dateCreated) {
      if (memberList.size() == 0) {
      
         Member newMember = new Member(firstName, lastName, screenName, emailAddress, dateCreated);
         memberList.add(newMember);
      }
      
      else if (memberList.size > 0) {
         for (int i = 0; i < memberList.size(), i++) {
            if (firstName != memberList.get(i).getFirstname() && 
                lastName != memberList.get(i).getLastName() &&
                screenName != memberList.get(i).getScreenName() &&
                emailAddress != memberList.get(i).getEmailAddress()) {
                
                Member newMember = new Member(firstName, lastName, screenName, emailAddress, dateCreated);
                memberList.add(newMember);
            }
            
            else {
               continue
            }
         }  
   }
   
   // Returns the Member corresponding to this emailAddress if they exist
   public Member getMember(String emailAddress) {
   }
   
   //Returns a list of all Members, sorted by last name, then first name
   public List<Member> getMembers() {
   }
   
   //Returns a list of all Members where text (partially) matches any of firstName, lastName, screenName, emailAddress across all Members, , sorted by last name, then first name
   public List<Member> getMembers(String text) {
   }
   
   //Adds a new Group provided it doesn’t already exist returning whether successful or not
   public boolean addGroup(String title, String description, LocalDateTime dateCreated) {
   }
   
   //Returns the Group corresponding to this title if it exists
   public Group getGroup(String title) {
   }
   
   //Returns a list of all Groups, sorted by title
   public List<Group> getGroups() {
   }
   
   //Returns a list of all Groups where text (partially) matches any of title or description across all Groups, sorted by title
   public List<Group> getGroups(String text) {
   }
   
   //Returns a list of the n Groups that have the most members, sorted descending on the number of members
   public List<Group> getPopularGroups(int n) {
   }
   
   //Returns a list of the n Groups that have the most questions and answers combined, sorted descending on the total number of questions and asnwers
   public List<Group> getActiveGroups(int n) {
   }
   
   //Returns a list of the n Members that have the most questions and answers across all the groups they are a member of, sorted descending on the total questions and answers
   public List<Member> getActiveMembers(int n) {
   }
   
   // Static method to save the entire system. Hint: a binary file is the easiest way to do this
   public static save(SiteManager sm, File file) {
   }
   
   //Static method to read the system from file and return a SiteManager object
   public static SiteManager read(File file) {
   }
   
}
   
   