package sprint1;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class SiteManagerTest {
	static int index = 0;
	int random;
	static SiteManager sm = new SiteManager();
	static LocalDateTime dateCreated = LocalDateTime.now();
	
	@BeforeAll
	static void startText(){
		while(index < 100) {
			sm.addMember("Member-" + index, "lastname", ""+index, index + "@gmail.com", dateCreated);
			if(index % 10 == 0) {
				sm.addGroup("Group-" + index/10, index/10+" is great", dateCreated);
			}
			index++;
		}
		
	}
	
	@Test
	void testSiteManager() {
		assertTrue(sm != null);
		
	}

	@Test
	void testAddMember() {
		sm.addMember("Matthew", "Ritter", "Matt", "mritter2283@gmail.com", dateCreated);
		Member matt = new Member("Matthew", "Ritter", "Matt", "mritter2283@gmail.com", dateCreated);
		assertTrue(sm.getMember("mritter2283@gmail.com").getFirstName().equals(matt.getFirstName()));
		
	}

	@Test
	void testGetMember() {
		assertTrue(sm.getMember("1@gmail.com").getFirstName().equals("Member-1"));
	}

	@Test
	void testGetMembers() {
		assertTrue(sm.getMembers().size() == 101);
		assertTrue(sm.getMembers().get(0).getFirstName().equals("Member-0"));
	}

	@Test
	void testGetMembersString() {
		sm.getMember("1@gmail.com").joinGroup(sm.getGroup("Group-9"), dateCreated);
		sm.getMember("1@gmail.com").joinGroup(sm.getGroup("Group-2"), dateCreated);
		
		assertTrue(sm.getMembers("Group-2").contains(sm.getMember("1@gmail.com")));
		assertTrue(sm.getMembers("-2").contains(sm.getMember("1@gmail.com")));
		assertTrue(sm.getMembers("-5").size() == 25);
		
		sm.getMember("1@gmail.com").joinGroup(sm.getGroup("Group-5"), dateCreated);
		assertTrue(sm.getMembers("-5").size() == 26);
	}

	@Test
	void testAddGroup() {
		sm.addGroup("Group-10", "10 is great", dateCreated);
		assertEquals("Group-10",sm.getGroup("Group-10").getTitle());
		assertTrue(sm.getGroups().size() == 11);
		
		//Group-10 will be placed at index 2 because of the sorting algorithm based on strings
		assertEquals(2,sm.getGroups().indexOf(sm.getGroup("Group-10")));
		
	}

	@Test
	void testGetGroup() {
		String groupTitle = sm.getGroup("Group-0").getTitle();
		assertEquals("Group-0", groupTitle);
	}

	@Test
	static void testGetGroups() {
		assertEquals(10, sm.getGroups().size());
	}

	@Test
	void testGetGroupsString() {
		List<Group> expecteds = new ArrayList<>();
		
		expecteds.add(sm.getGroup("Group-0"));
		expecteds.add(sm.getGroup("Group-10"));
		
		assertListEquals(expecteds, sm.getGroups("0"));
	}

	private static boolean assertListEquals(List<Group> expecteds, List<Group> groups) {
		int size = groups.size() - 1;
		while(size > 0) {
			if(expecteds.get(size).getTitle().equals(groups.get(size).getTitle())) {
				size--;
				return true;
			}
			
		}return false;
		
	}

	@Test
	void testGetPopularGroups() {
		for(Member m : sm.getMembers("-5")) {
			m.joinGroup(sm.getGroup("Group-5"), dateCreated);
		}
		for(Member m : sm.getMembers("0")) {
			m.joinGroup(sm.getGroup("Group-0"), dateCreated);
		}
		
		List<Group> expecteds = new ArrayList<>();
		
		expecteds.add(sm.getGroup("Group-5"));
		expecteds.add(sm.getGroup("Group-0"));
		
		//assertListEquals(expecteds, sm.getPopularGroups(2));
		
	}

	@Test
	void testGetActiveGroups() {
		Question q = new Question("Title", "Text", dateCreated);
		Answer a = new Answer(q,"Text",dateCreated);
		for(Member m : sm.getMembers("-5")) {
			m.joinGroup(sm.getGroup("Group-5"), dateCreated);
			m.addAnswer(sm.getGroup("Group-5"), q, a, dateCreated);
		}
		for(Member m : sm.getMembers("1")) {
			m.joinGroup(sm.getGroup("Group-1"), dateCreated);
			m.addAnswer(sm.getGroup("Group-1"), q, a, dateCreated);
		}
		
		List<Group> expecteds = new ArrayList<>();
		
		expecteds.add(sm.getGroup("Group-1"));
		expecteds.add(sm.getGroup("Group-5"));
		
		//assertListEquals(expecteds,sm.getActiveGroups(2));
	}

	@Test
	void testGetActiveMembers() {
		fail("Not yet implemented");
	}

}
