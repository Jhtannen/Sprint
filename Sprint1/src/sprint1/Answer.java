package sprint1;
import java.util.ArrayList;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


public class Answer extends Post implements Serializable  {
	private Question question;

	public Answer (Question question, String text, LocalDateTime dateCreated) { 
		super(text,dateCreated);
		this.question = question;
	}

	public Question getQuestion() {
		return question;
	}

	private String getLikes() {
		String result = "This post has: " + this.likes.size() + "\n=============\n";
		if(likes.size() > 0) {
			result += "Like by: [";
			for(Like like: this.likes) {
				result += " " + like.getUpVoter() + ",\n";
			}
			result += "]";
		}
		return result;	
	}
	
	public String toString() {
		String result = "Answer: " + text + " for question " + question.getTitle();
		result += "\n" + getLikes();
		return result;
	}

}
    