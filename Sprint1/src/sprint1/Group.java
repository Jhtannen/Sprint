package sprint1;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Group {
	Date dateCreated;
	String title;
	String description;
	private List<Answer> answers;
	private List<Membership> memberships;
	private List<Question> questions;
	
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
		
		for(Membership m : memberships) {
			if(m.getMember().name.equals(emailAddress)) {
				return m.getMember();
			}
		} return new Member("Dummy", "NoEmail");
	}
	//Return a list for Members of this group
	public List<Member> getMembers(){
		List<Member> members = new ArrayList<Member>();
		for(Membership m : memberships) {
			members.add(m.getMember());
		}
		return members;
	}
	//Returns all questions that have been asked in this group in the order
	//that they were asked
	public List<Question> getQuestions(){
		return questions;
	}
	//Returns all answers to all questions that have been asked in this group in the order 
	//that they were answered
	public List<Answer> getAnswers(){
		return answers;
	}
	//Provides useful information about this group, neatly formatted
	public String toString() {
		return "Group: "+ this.title;
	}
	
}
