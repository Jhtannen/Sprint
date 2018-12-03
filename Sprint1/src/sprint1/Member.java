package sprint1;

import java.io.Serializable;
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

public class Member implements Serializable{
	private LocalDateTime dateCreated;
	private String firstName;
	private String lastName;
	private String screenName;
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

	//returns email address
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
	protected List<Membership> getMemberships() {
		return memberships;
	}

	//Joins this member to group and records the dateJoined
	public void joinGroup(Group group, LocalDateTime date) {
		if(!group.getMembers().contains(this)) {
			Membership membership = new Membership(date, this, group);
			this.memberships.add(membership);
			group.addMembership(membership);
		}
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
				membership.addQuestion(question);
			}
		}	
	}

	//Returns the date this member joined this group
	public LocalDateTime getDateJoined(Group groupName) {
		LocalDateTime temp =null;
		for (Membership membership: memberships) {
			if (membership.getGroup().equals(groupName)) {
				return membership.getDateJoined();
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
		Map<Integer, Group> map = new TreeMap<>();
		ArrayList<Group> groups = new ArrayList<>();
		for (Membership membership: memberships) {
			map.put(membership.getGroup().getQuestions().size() + membership.getGroup().getAnswers().size(), membership.getGroup() );
		}
		if (map.size() < n) {
			for (int i=0; i < map.size(); i++ ) {
				groups.add( map.get(i) );
			}			
			return groups; 	
		}
		int count = n;
		for (int i = map.size() -1; count > 0; count--, i--) {
			groups.add(map.get(i));		
		}

		Collections.sort(groups, new Comparator<Group>()  {
			@Override 
			public int compare(Group o1, Group o2) {
				return o1.getTitle().compareTo(o2.getTitle() );
			}
		});
		return groups;
	} 

	//Returns the n most recent questions asked by 
	//this member in this group sorted on the order they were asked, most recent first.
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
	List<Answer> getAnswers(Group group, int n) {//!
		ArrayList<Answer> answersList = new ArrayList<Answer>();//!
		for (Membership membership: memberships) {
			if (membership.getGroup().equals(group) ) {
				answersList.addAll( membership.getAnswers());
			}
		}
		for (int i = 0; i < answersList.size() - n; i++) {
			answersList.remove(i);
		}
		Collections.reverse(answersList);
		return answersList;
	}

	//Likes the post passed in as an argument and gives 5 points to the poster
	private void likePost(Post post) {
		if (this.getGroups().contains(post.getGroup()) && !alreadyLikedPost(post) ) {//check if member is in the group in which "post" was posted
			post.addLike(new Like(this));
			post.getMembership().addPoints(5);
		}
	}	

	//Checks if this member already likes the post
	private boolean alreadyLikedPost(Post post) {
		for (Like like: post.likes) {
			if (like.getUpVoter() == this) {
				return true;
			}
		}
		return false;
	}	

	//Adds a comment to a post and gives 10 points to the poster
	private void commentOnPost(Post post, Comment comment) {
		if (this.getGroups().contains(post.getGroup() ) ) {//check if member is in the group in which "post" was posted
			post.addComment(comment);
			post.getMembership().addPoints(10);
		}
	}

	//Assigns new text to previously posted comment
	private void editComment(Comment comment, String newText) {
		comment.editText(newText);
	}

	//Deletes comment permanently
	private void deleteComment(Comment comment) {
		comment.getPost().removeComment(comment);
	}

	//Adds 40 points to the member who wrote the best answer
	private void chooseBestAnswer(Answer answer) {
		answer.getMembership().addPoints(40);
	}

	//Displays the total number of points this member has earned
	private String displayPoints() {
		int points = 0;
		for (Membership membership : memberships) {
			points += membership.getPoints();
		}
		return  points + " points.";
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
				"\nPoints: " + displayPoints();
		data += "\n------Groups this member is a member of------\n";
		int size = this.memberships.size();
		while(size > 0) {
			data += memberships.get(memberships.size()-1).getGroup().getTitle();
			size--;
		}
		return data;       		
	}

	private int getNumberOfTotalPosts() {
		int count = 0;
		for(Membership membership: this.memberships) {
			count += membership.getAnswers().size() + membership.getQuestions().size();
		}
		return count;
	}

	private int getNumberOfTotalQuestions() {
		int count = 0;
		for(Membership membership: this.memberships) {
			count += membership.getQuestions().size();
		}
		return count;
	}

	private int getNumberOfTotalAnswers() {
		int count = 0;
		for(Membership membership: this.memberships) {
			count += membership.getAnswers().size();
		}
		return count;
	}
}