package sprint1;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

abstract class Post implements Serializable{
	protected String text;
	protected LocalDateTime date;
	protected Membership membership;
	private List<Comment> comments = new ArrayList<>();
	private List<Like> likes = new ArrayList<>();

	// constructor to set initial values
	public Post(String txt, LocalDateTime date) {
		text = txt;
		this.date = date;
	}

	// returns value of text
	public String getText() {
		return text;
	}

	// returns local date value
	public LocalDateTime getDate() {
		return date;
	}

	// sets/resets value in text
	public void setText(String text) {
		this.text = text;
	}

	// returns author name
	public Member getAuthor() {
		return membership.getMember();
	}

	// returns group name
	public Group getGroup() {
		return membership.getGroup();
	}

	// sets membership status
	protected void setMembership(Membership membership) {
		this.membership = membership;
	}

	// returns membership status
	protected Membership getMembership() {
		return membership;
	}
	
	// add like to a post
	protected void addLike(Like like) {
		likes.add(like);
	}
	
	// add comment to a post
	protected void addComment(Comment comment) {
		comments.add(comment);
	}

	//Permanently removes comment from post
	public void removeComment(Comment deleteComment) {
		for (Comment comment: comments) {
			if (deleteComment.equals(comment)) {
				comments.remove(comment);
			}
		}
	} 

}