import java.util.ArrayList;
import java.util.Date;
import java.util.List;


class Answer extends Post {
	private Question question;
	private Date dateCreated;
	
		public Answer (Question question, String text, Date dateCreated) { 
			super(text,dateCreated);
			this.question = question;
			this.dateCreated = dateCreated;
    	}
        public Question getQuestion() {
			return question;
        }
        public String toString() {
        	String result = "Answer: " + text + " for question " + question.getTitle();
        	return result
       }

    }
