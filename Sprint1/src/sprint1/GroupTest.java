package sprint1;

import java.util.List;

public class GroupTest {

	public static void main(String[] args) {
		System.out.println("Hello World");
		
	}

}

class Member{
	String email, name;
	List<Membership> memberships;
	Member(String name, String email){
		this.name = name;
		this.email = email;
	}
}
class Membership {
	Member m;
	String name;
	Member getMember() {return m;}
}

class Answer{
	String answer = "This is an answer";
}

class Question{
	String question = "This is a question";
}
