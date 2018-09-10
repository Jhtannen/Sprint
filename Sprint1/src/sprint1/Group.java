package sprint1;
import java.util.Date;

public class Group {
	Date dateCreated;
	String title;
	String description;
	
	public Group(String title, String description, Date date) {
		this.title = title;
		this.description = description;
		this.dateCreated = date;
	}
	//Returns this groups dateCreated
	public Date getDateCreated() {
		return dateCreated;
	}
	//Returns this groups title
	public String getTitle() {
		return title;
	}
	//Returns this groups description
	public String getDescription() {
		return description;
	}
	//Returns the number of members of this group
	public int getNumberOfMembers() {
		return -1;
	}
	//Returns the Member of this group that corresponds to the emailAddress
	public Member getMember(String emailAddress) {
		
	}
	//Return a list fo Members of this group
	public List<Member> getMembers(){
		
	}
	//Returns all questions that have been asked in this group in the order
	//that they were asked
	public List<Question> getQuestions(){
		
	}
	//Returns all answers to all questions that have been asked in this group in the order 
	//that they were answered
	public List<Answers> getAnswers(){
		
	}
	//Provides useful information about this group, neatly formatted
	public String toString() {
		
	}
	
}
