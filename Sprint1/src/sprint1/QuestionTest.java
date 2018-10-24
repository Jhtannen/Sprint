package sprint1;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;

class QuestionTest {
	LocalDateTime date = LocalDateTime.now();
	@Test
	void testQuestion() {
		
		Question q1 = new Question("Q1 title", "What time is it", date);
		
		assertFalse(q1 == null);
	}

	@Test
	void testGetTitle() {
		
		Question q1 = new Question("Q1 title", "What time is it", date);
		
		assertEquals("Q1 title", q1.getTitle());
	}

	@Test
	void testSetTitle() {
		
		Question q1 = new Question("Q1 title", "What time is it", date);
		q1.setTitle("New Title");
		assertEquals("New Title", q1.getTitle());
	}

	@Test
	void testAddAnswer() {
		
		Member matt = new Member("Matthew", "Ritter", "Matt", "mritter2283@gmail.com", date);
		Group group1 = new Group("Group One", "The best Group ONE", date);
		matt.joinGroup(group1, date);
		Question q1 = new Question("Q1 Title", "Whats the weather like", date);
		Answer a1 = new Answer(q1, "Weather looks greate", date);
		matt.addQuestion(group1, q1, date);
		matt.addAnswer(group1, q1, a1, date);
		
		assertTrue(q1.getAnswers().contains(a1));
	}

	@Test
	void testGetAnswers() {
		
		Member matt = new Member("Matthew", "Ritter", "Matt", "mritter2283@gmail.com", date);
		Group group1 = new Group("Group One", "The best Group ONE", date);
		matt.joinGroup(group1, date);
		Question q1 = new Question("Q1 Title", "Whats the weather like", date);
		Answer a1 = new Answer(q1, "Weather looks greate", date);
		matt.addQuestion(group1, q1, date);
		matt.addAnswer(group1, q1, a1, date);
		ArrayList<Answer> answers = new ArrayList<Answer>();
		answers.add(a1);
		assertTrue(answers.equals(q1.getAnswers()));
		
	}

	@Test
	void testToString() {
		
		Member matt = new Member("Matthew", "Ritter", "Matt", "mritter2283@gmail.com", date);
		Group group1 = new Group("Group One", "The best Group ONE", date);
		matt.joinGroup(group1, date);
		Question q1 = new Question("Q1 Title", "Whats the weather like", date);
		Answer a1 = new Answer(q1, "Weather looks greate", date);
		matt.addQuestion(group1, q1, date);
		matt.addAnswer(group1, q1, a1, date);
		
		String result = "Question Title: Q1 Title\n" + 
				"Body: Whats the weather like";
		assertEquals(result, q1.toString());
	}

}
