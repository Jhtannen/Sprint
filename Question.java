import java.util.ArrayList;
import java.util.Date;
import java.util.List;

    public class Question {
     
        private String Title;

        public List<Answer> answer = new ArrayList<Answer> ();
        
        public Question(String title, String text, Date LocalDateTime) {
        }

        String getTitle() {
            return this.Title;
        }

        
        void setTitle(String value) {
           
            this.Title = value;
        }


        
        public void addAnswer(Answer Answer) {
        }

       
        public String getAnswer() {
			return Title;
        }

        
        public String toString() {
			return Title;
        }

    }

    
   