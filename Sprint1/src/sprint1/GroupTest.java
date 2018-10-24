package sprint1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class GroupTest {

	LocalDateTime date = LocalDateTime.now();
	
	@Test
	void testGroup() {
		
		Group group1 = new Group("Group One", "The best Group ONE", date.now());
		
		assertTrue(isGroup(group1));
	}
	
	boolean isGroup(Group g) {
		if(g.getDateCreated() != null && g.getTitle() != null && g.getDescription() != null) {
			return true;
		}
		return false;
	}

	@Test
	void testGetDateCreated() {
		
		Group group1 = new Group("Group One", "The best Group ONE", date);
		assertEquals(date, date);
	}

	@Test
	void testGetTitle() {
		
		Group group1 = new Group("Group One", "The best Group ONE", date);
		assertEquals(group1.getTitle(), "Group One");
	}

	@Test
	void testGetDescription() {
		
		Group group1 = new Group("Group One", "The best Group ONE", date);
		assertEquals("The best Group ONE", group1.getDescription());
	}

	@Test
	void testGetNumberOfMembers() {
		
		Member matt = new Member("Matthew", "Ritter", "Matt", "mritter2283@gmail.com", date);
		Group group1 = new Group("Group One", "The best Group ONE", date);
		matt.joinGroup(group1, date);
		assertEquals(1,group1.getNumberOfMembers());
	}

	@Test
	void testGetMember() {
		
		Member matt = new Member("Matthew", "Ritter", "Matt", "mritter2283@gmail.com", date);
		Group group1 = new Group("Group One", "The best Group ONE", date);
		matt.joinGroup(group1, date);
		assertEquals(matt,group1.getMember("mritter2283@gmail.com"));
	}

	@Test
	void testAddMembership() {
		
		Member matt = new Member("Matthew", "Ritter", "Matt", "mritter2283@gmail.com", date);
		Group group1 = new Group("Group One", "The best Group ONE", date);
		matt.joinGroup(group1, date);
		assertEquals(1, group1.getNumberOfMembers());
	}

	@Test
	void testGetMembers() {
		
		Member matt = new Member("Matthew", "Ritter", "Matt", "mritter2283@gmail.com", date);
		Group group1 = new Group("Group One", "The best Group ONE", date);
		matt.joinGroup(group1, date);
		List<Member> myGroupMembers = new ArrayList<Member>();
		myGroupMembers.add(matt);
		
		assertEquals(myGroupMembers,group1.getMembers());
	}

	@Test
	void testGetQuestions() {
		
		
		Member matt = new Member("Matthew", "Ritter", "Matt", "mritter2283@gmail.com", date);
		Group group1 = new Group("Group One", "The best Group ONE", date);
		matt.joinGroup(group1, date);
		Question q1 = new Question("Q1 Title", "Whats the weather like", date);
		matt.addQuestion(group1, q1, date);
		List<Question> myGroupQuestions = new ArrayList<Question>();
		myGroupQuestions.add(q1);
		System.out.println(myGroupQuestions);
		System.out.println(group1.getQuestions());
		
		assertEquals(myGroupQuestions, group1.getQuestions());
	}

	@Test
	void testGetAnswers() {
		
		Member matt = new Member("Matthew", "Ritter", "Matt", "mritter2283@gmail.com", date);
		Group group1 = new Group("Group One", "The best Group ONE", date);
		matt.joinGroup(group1, date);
		Question q1 = new Question("Q1 Title", "Whats the weather like", date);
		Answer a1 = new Answer(q1, "Weather looks greate", date);
		matt.addQuestion(group1, q1, date);
		List<Question> myGroupQuestions = new ArrayList<Question>();
		List<Answer> myGroupAnswers = new ArrayList<Answer>();
		
		myGroupAnswers.add(a1);
		
		System.out.println(myGroupAnswers);
		System.out.println(group1.getAnswers());
		
		assertEquals(myGroupAnswers, group1.getAnswers());
	}

	@Test
	void testToString() {
		
		Group group1 = new Group("Group One", "The best Group ONE", date);
		assertEquals("Group: Group One",group1.toString());
	}

}
