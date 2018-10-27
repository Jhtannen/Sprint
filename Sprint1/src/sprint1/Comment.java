package sprint1;

public class Comment {

	private String text;
	private Member commentor;
	
	public Comment(String text, Member commentor) {
		this.text = text;
		this.commentor = commentor;
	}

	public String getText() {
		return text;
	}

	public void editText(String text) {
		this.text = text;
	}

}
