package sprint1;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.Date;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;

public class Member {	
	private LocalDateTime dateCreated;
	private String firstName;
	private String lastName;
	private String screenName; // emailAddress & screenName must be unique
	private String emailAddress;
	private List<Membership> memberships = new ArrayList<>();
	
	// Constructor method	
	public Member(String firstName, String lastName, String screenName, String emailAddress, LocalDateTime dateCreated) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.screenName = screenName;
		this.emailAddress = emailAddress;
		this.dateCreated = dateCreated;
	}

		//returns member's email address
	public String getEmailAddress() {
		return this.emailAddress;
	}

	//returns the date member was created
	public LocalDateTime getDateCreated() {
		return this.dateCreated;
	}

	//returns the member's first name
	public String getFirstName() {
		return this.firstName;
	}

	//returns the member's last name
	public String getLastName() {
		return this.lastName;
	}
	
	//returns screen name
	public String getScreenName() {
		return this.screenName;
	}
		
	//returns list of memberships this member is a part of
	List<Membership> getMemberships() {
		return memberships;
	}
	// end of getter methods
	
    //Joins this member to group and records the dateJoined
	public void joinGroup(Group group, LocalDateTime date) {
		Membership membership = new Membership(date, this, group);
	    this.memberships.add(membership);
	    group.addMembership(membership);
	}
	
	//Returns the number of groups this member is a member of
	public int getNumGroups() {
		return this.memberships.size();
	}

	//Returns the Group that corresponds to the groupID
	public Group getGroup(String groupID) {
		Group temp = null;
		for (Membership membership: this.memberships) {
			if ( membership.getGroup().getTitle().equals(groupID) ) {
				temp = membership.getGroup();
				return temp;
			}
		}
		return temp;
	}
	
	//Returns a list of Groups the member is a member of
	public List<Group> getGroups() {
		ArrayList<Group> groupList = new ArrayList<Group>();
		for (Membership membership: this.memberships) {
			groupList.add(membership.getGroup());  
		}      
		Collections.sort(groupList, new Comparator<Group>()  {
			@Override 
			public int compare(Group o1, Group o2) {
				return o1.getTitle().compareTo(o2.getTitle() );
			}
		});
		return groupList;
	}	
		
	//Adds the question to the group by this member and records
	//the date the question was asked
	public void addQuestion(Group groupName, Question question, LocalDateTime date) {
			for (Membership membership: memberships) {
			if (membership.getGroup().equals(groupName)) {
				question.setMembership(membership);
				//membership.getQuestions().add(question);
				membership.addQuestion(question);
			}
		}	
	}
	
	//Returns the date this member joined this group
	public LocalDateTime getDateJoined(Group groupName) {
		LocalDateTime temp =null;
		for (Membership membership: memberships) {
			if (membership.getGroup().equals(groupName)) {
				temp=membership.getDateJoined();
			}
		}
		
		return temp;
	}

	//Adds this member's answer to this question which is in this group and 
	//records the date answered
	public void addAnswer(Group groupName, Question question, Answer answer, LocalDateTime date) {
		question.addAnswer(answer);
		for (Membership membership: memberships) {
			if (membership.getGroup().equals(groupName)) {
				answer.setMembership(membership);
				membership.getAnswers().add(answer);
			}
		}			
	}
	
	//Returns all questions asked by this member in this group
	public List<Question> getQuestions(Group group) {
		ArrayList<Question> questionsList = new ArrayList<Question>();
		for (Membership membership: memberships) {
			if (membership.getGroup().equals(group) ) {
				questionsList.addAll( membership.getQuestions());
			}
		}
		Collections.reverse(questionsList);
		return (questionsList);
	}

	//Returns all answers asked by this member in this group
	public List<Answer> getAnswers(Group groupName) {
		ArrayList<Answer> answersList = new ArrayList<Answer>();
		for (Membership membership: memberships) {
			if (membership.getGroup().equals(groupName)) {
				answersList.addAll( membership.getAnswers());
			}
		}
	    Collections.reverse(answersList);
	    return answersList;
	}
	
	//Returns a list of the n Groups that the member is most active in, sorted on title.
	//Measure most active by the total questions posted and answers provided, in total. 
	//If there are not n groups, then return the groups that exist.
	List<Group> getGroups(int n) {
		Map<Group, Integer> map = new TreeMap<>();
		ArrayList<Group> GroupList = new ArrayList<>();
		for (Membership membership: memberships) {
			 map.put(membership.getGroup(), membership.getGroup().getQuestions().size() + membership.getGroup().getAnswers().size() );
		}
		if (map.size() <= n) {
			for (Map.Entry entry : map.entrySet() ) {
				GroupList.add((Group)entry.getKey() );
			}			
			return GroupList; 	
		}
		int counter = 0;
		for (Map.Entry entry : map.entrySet() ) {
			counter++;
			if (counter <= map.size() - n) {
				GroupList.add((Group)entry.getKey() );
			}
		}
		return GroupList;
	}
	
	// Returns the n most recent questions asked by this member in
	//this group sorted on the order they were asked, most recent first.
	List<Question> getQuestions(Group group, int n) {
		ArrayList<Question> questionsList = new ArrayList<Question>();
		for (Membership membership: memberships) {
			if (membership.getGroup().equals(group) ) {
				questionsList.addAll( membership.getQuestions());
			}
		}
		for (int i = 0; i < questionsList.size() - n; i++) {
			questionsList.remove(i);
		}
		Collections.reverse(questionsList);
		return questionsList;
	}

	//Returns the n most recent answers asked by this 
	//member in this group sorted on the order they were provided, most recent first.
	List<Question> getAnswers(Group group, int n) {
		ArrayList<Question> answersList = new ArrayList<Question>();
		for (Membership membership: memberships) {
			if (membership.getGroup().equals(group) ) {
				answersList.addAll( membership.getQuestions());
			}
		}
		for (int i = 0; i < answersList.size() - n; i++) {
			answersList.remove(i);
		}
		Collections.reverse(answersList);
		return answersList;
	}

	//Provides useful information about this member, neatly formatted
	@Override
	public String toString() {
		String data  = "\n------Member Information------" + 
			   "\nFirst Name: " +firstName +
			   "\nLast Name: " + lastName + 
			   "\nScreen Name: " + screenName + 
			   "\nEmail address: " + emailAddress + 
			   "\nDate Joined: " + dateCreated +
			   "\n------Questions by this member:------\n";
				for (Membership membership: memberships) {
					 for (Question question: membership.getQuestions()) {
						data += question + "\n"; 
					 }
				}
				data += "\n------Answers by this member------\n";
				for (Membership membership: memberships) {
					for (Answer answer: membership.getAnswers()) {
						data += answer + "\n"; 
					}
				}
				data += "\n------Groups this member is a member of------\n";
				for (Membership membership: memberships) { 
						data += membership.getGroup() + "\n"; 
				}				
        return data;       		
	}

}//end of Member.java class