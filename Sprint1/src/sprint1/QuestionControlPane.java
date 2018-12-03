package sprint1;

import java.io.File;
import java.io.FileOutputStream;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class QuestionControlPane {
	
	Member member;
	Group group;
	Question question;
	PersistanceManager pm;
	SiteManager sm;
	
	public QuestionControlPane(Member m, Group g, Question q, PersistanceManager pm, SiteManager sm) {
		member = m;
		group = g;
		question = q;
		this.pm = pm;
		this.sm = sm;
	}
	
	public VBox createControlPane() {
		VBox controlPane = new VBox();
		HBox controls = new HBox();
		Label questionL = new Label("Question\nTitle: " + question.getTitle() + "\nAuthor: " + question.getAuthor().getFirstName() + 
				" " +question.getAuthor().getLastName() + "\nDescription: " + question.getText());
		questionL.setWrapText(true);
		Button btnAdd = new Button("Add Answer");
		Button btnComment = new Button("Comment on Question");
		Button btnLike =  new Button("Like Post");
		controls.getChildren().addAll(btnAdd,btnComment,btnLike);
		controlPane.getChildren().addAll(questionL,controls);
		
		
		btnAdd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				controlPane.getChildren().clear();
				AnswerFormPane ap = new AnswerFormPane(member, group, question, pm, sm);
				controlPane.getChildren().add(ap.createAnswerFormPane());
			}
		});
		btnComment.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				controlPane.getChildren().clear();
				CommentFormPane cp = new CommentFormPane(member, question, pm, sm);
				controlPane.getChildren().add(cp.createCommentFormPane());
			}
		});
		btnLike.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Like like = new Like(member);
				question.addLike(like);
				System.out.println(question.likes.get(0).getUpVoter());
				save();
			}
		});
		
		
		
		return controlPane;
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
