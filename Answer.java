import java.util.ArrayList;
import java.util.Date;
import java.util.List;


    public class Answer {
    	
    	private String title;
		private Object question;
		private Date dateCreated;
		public Answer (String title, String text, Date dateCreated) { 
    		this.title = title;
    		this.question = question;
    		this.dateCreated = dateCreated;
    	}
        public Question getQuestion() {
			return (Question) question;
        }
        public String toString() {
			return null;
        
       }

    }
