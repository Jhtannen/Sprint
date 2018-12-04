package sprint1;

import java.io.File;
import java.io.FileOutputStream;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class CommentFormPane {

	String comment;
	Post post;
	Member member;
	PersistanceManager pm;
	SiteManager sm;
	
	public CommentFormPane(Member m, Post p, PersistanceManager pm,SiteManager sm ){
		member = m;
		post = p;
		this.sm =sm;
		this.pm =pm;
		
	}
	
	public VBox createCommentFormPane() {
		VBox vb = new VBox();
		Label headingL = new Label("Post\nTitle: " + post.getText() + "\nAuthor: " + post.getAuthor().getFirstName() + post.getAuthor().getLastName());
		Label commentL = new Label("Leave a comment");
		TextArea commentTA = new TextArea();
		commentTA.setWrapText(true);
		Button btnSubmit = new Button("Submit");
		vb.getChildren().addAll(headingL,commentL, commentTA,btnSubmit);
		
		return vb;
	}
	
	protected void save() {
		try {
			File file = new File("SiteManager_File");
			FileOutputStream fos = new FileOutputStream(file);
			pm.save(sm, fos);
		} catch(Exception e) {
			System.out.println("ERROR: "+ e);
		}
	}
	
}