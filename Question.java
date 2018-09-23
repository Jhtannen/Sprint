import java.util.ArrayList;
import java.util.Date;
import java.util.List;

     class Question extends Post {

		private String title;
		private Answer answer;
		private List <Answer> answer = new ArrayList<>();
		
        
        public Question(String title, String text, Date dateCreated) {
        	super(text, dateCreated);
        	this.title = title;
        	this.dateCreated = dateCreated;
        }

       public String getTitle() {
            return title;
        }

        
        void setTitle(String title) {
           
            this.title = title;
        }


        
        public void addAnswer(Answer answer) {
              
        	answer.add(answer);
        
        }
        

       
        public List<Answer> getAnswer() {
			return answers;
        }

        
        public String toString() {
        String result = "Question Title: " + title + "\n" + "Body:"
        return result; 
        }

    }

    
   