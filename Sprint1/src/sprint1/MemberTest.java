package sprint1;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;



class MemberTest {
	
	LocalDateTime date = LocalDateTime.now();
	Group g = new Group("Group one", "The best Group", date);
	Member matt = new Member("Matthew", "Ritter", "Matt", "mritter2283@gmail.com", date);
	@Test
	void testMember() {
		assertTrue(matt.getFirstName() != null && matt.getEmailAddress() != null);

	}

	@Test
	void testGetEmailAddress() {
		assertEquals("mritter2283@gmail.com", matt.getEmailAddress());
	}

	@Test
	void testGetDateCreated() {
		assertEquals(date, date);
	}

	@Test
	void testGetFirstName() {
		assertEquals("Matthew",matt.getFirstName());
	}

	@Test
	void testGetLastName() {
		assertEquals("Ritter",matt.getLastName());
	}

	@Test
	void testGetScreenName() {
		assertEquals("Matt",matt.getScreenName());
	}

	@Test
	void testGetMemberships() {
		
		matt.joinGroup(g, date);
		
		for(Membership m : matt.getMemberships()) {
			assertTrue(m.getGroup().getTitle().equals(g.getTitle()));
			
		}
	}

	@Test
	void testJoinGroup() {
		matt.joinGroup(g, date);
		
		for(Membership m : matt.getMemberships()) {
			assertTrue(m.getGroup().getTitle().equals(g.getTitle()));
		}
	}

	@Test
	void testGetNumGroups() {
		matt.joinGroup(g, date);
		assertEquals(1, matt.getNumGroups());
	}

	@Test
	void testGetGroup() {
		matt.joinGroup(g, date);
		String groupName = "Group one";
		assertTrue((groupName).equals(matt.getGroup(g.getTitle()).getTitle()));
	}

	@Test
	void testGetGroups() {
		matt.joinGroup(g, date);
		for(Group group : matt.getGroups()) {
			assertEquals(group, g);
		}
	}

	@Test
	void testAddQuestion() {
		matt.joinGroup(g, date);
		Question q = new Question("Question one", "This is a question?", date);
		matt.addQuestion(g, q, date);
		assertTrue(matt.getQuestions(g).contains(q));
	}

	@Test
	void testGetDateJoined() {
		matt.joinGroup(g, date);
		assertEquals(date, matt.getDateJoined(g));
	}

	@Test
	void testAddAnswer() {
		matt.joinGroup(g, date);
		Question q = new Question("Question one", "This is a question?", date);
		matt.addQuestion(g, q, date);
		Answer a = new Answer(q, "This is the Answer", date);
		matt.addAnswer(g, q, a, date);
		assertTrue(matt.getAnswers(g).contains(a));
	}

	@Test
	void testGetQuestions() {
		matt.joinGroup(g, date);
		Question q = new Question("Question one", "This is a question?", date);
		matt.addQuestion(g, q, date);
		ArrayList<Question> qs = new ArrayList<Question>();
		qs.add(q);
		assertEquals(qs, matt.getQuestions(g));
		
	}

	@Test
	void testGetAnswers() {
		matt.joinGroup(g, date);
		Question q = new Question("Question one", "This is a question?", date);
		matt.addQuestion(g, q, date);
		Answer a = new Answer(q, "This is the Answer", date);
		matt.addAnswer(g, q, a, date);
		ArrayList<Answer> as = new ArrayList<Answer>();
		as.add(a);
		assertEquals(as, matt.getAnswers(g));
	}

	@Test
	void testToString() {

		String expected = "Member InformationFirst Name: MatthewLast Name: RitterScreen Name: MattEmail address: mritter2283@gmail.comDate Joined: " + date;
		System.out.println(expected);
		assertEquals(expected, matt.toString().replaceAll("\n", ""));
		
	}
	

}
