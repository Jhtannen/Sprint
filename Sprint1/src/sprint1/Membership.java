package sprint1;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

class Membership implements Serializable{
	private LocalDateTime dateJoined;
	private int points =0;
	private Member member;
	private Group group;
	private List<Question> questions = new ArrayList<>();
	private List<Answer> answers = new ArrayList<>();

	void addQuestion(Question question) {
		this.questions.add(question);	
	}

	void addAnswer(Answer answer) {
		this.answers.add(answer);
	}

	//constructor method
	public Membership(LocalDateTime dateJoined, Member member, Group group) {
		this.dateJoined = dateJoined;
		this.member = member;
		this.group = group;
		//this.points = points; 
	}

	//returns the date the member joined the group
	LocalDateTime getDateJoined() {
		return this.dateJoined;
	}	

	//returns the member in this membership
	Member getMember() {
		return member;
	}

	//returns the group in this membership
	Group getGroup() {
		return group;
	}

	//returns all questions in this membership
	List<Question> getQuestions() {
		return questions;
	}

	//returns all answers in this membership
	List<Answer> getAnswers() {
		return answers;
	}

	//adds points 
	public void addPoints(int points) {
		this.points += points;
	}

	//Returns the number of points the member has earned in this group
	public int getPoints() {
		return points;
	}

	//returns information about member and group and the date the member joined
	public String toString() {
		return "\n" + this.member.getFirstName() + " " + this.member.getLastName() +"joined " + 
				this.group.getTitle() + "on " + this.dateJoined + ".";
	}
}