package sprint1;

import java.io.File;
import java.io.FileOutputStream;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

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
		Label headingL = new Label("Title: " + post.getText() + "\nAuthor: " + post.getAuthor().getFirstName() + post.getAuthor().getLastName());
		Label commentL = new Label("Leave a comment");
		commentL.setStyle("-fx-font-weight: bold;");
		commentL.setFont(new Font("Arial", 30));
		TextArea commentTA = new TextArea();
		commentTA.setWrapText(true);
		Button btnSubmit = new Button("Submit");
		btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				Comment comment = new Comment(commentTA.getText(), member, post);
				post.addComment(comment);
				save();
			}
		});
		vb.getChildren().addAll(commentL,headingL, commentTA,btnSubmit);
		vb.setSpacing(5);
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