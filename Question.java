import java.util.ArrayList;
import java.util.Date;
import java.util.List;

    public class Question {

		private String title;

		private String question;

		private Object dateCreated;

		private Object Answer;
        
        public Question(String title, String text, Date dateCreated) {
        	this.title = title;
        	this.question = question;
        	this.dateCreated = dateCreated;
        }

       public String getTitle() {
            return title;
        }

        
        void setTitle(String title) {
           
            this.title = title;
        }


        
        public void addAnswer(Answer Answer) {
        this.Answer = toString();
        List<Answer> Answers = new List<>();
        
        	Answers.add((Answer) this.Answer);
        
        }
        

       
        public String getAnswer() {
			return List<Answer>
        }

        
        public String toString() {
			return null;
        }

    }

    
   