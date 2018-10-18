package sprint1;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;



class MemberTest {
	
	Date date = new Date();
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
		fail("Not yet implemented");
	}

	@Test
	void testGetGroups() {
		fail("Not yet implemented");
	}

	@Test
	void testAddQuestion() {
		fail("Not yet implemented");
	}

	@Test
	void testGetDateJoined() {
		fail("Not yet implemented");
	}

	@Test
	void testAddAnswer() {
		fail("Not yet implemented");
	}

	@Test
	void testGetQuestions() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAnswers() {
		fail("Not yet implemented");
	}

	@Test
	void testToString() {
		fail("Not yet implemented");
	}
	

}
