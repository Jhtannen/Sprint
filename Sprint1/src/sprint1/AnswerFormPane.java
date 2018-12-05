package sprint1;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class AnswerFormPane {
	Member member;
	Group group;
	Question question;
	PersistanceManager pm;
	SiteManager sm;
	
	AnswerFormPane(Member member, Group group, Question question, PersistanceManager pm, SiteManager sm){
		this.member = member;
		this.group = group;
		this.question = question;
		this.sm = sm;
		this.pm = pm;
	}
	
	public VBox createAnswerFormPane() {
		Label answerL = new Label("Add Answer");
		answerL.setStyle("-fx-font-weight: bold;");
		answerL.setFont(new Font("Arial", 30));
		Label questionDescriptionL = new Label("Title: " + question.getTitle() + "\nDescription: " + question.getText());
		TextArea answerTA = new TextArea();
		answerTA.setWrapText(true);
		answerTA.setMaxWidth(500.0);
		Button btnSubmit = new Button("Submit");
		btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(!answerTA.getText().isEmpty()) {
					LocalDateTime dateCreated = LocalDateTime.now();
					Answer answer = new Answer(question,answerTA.getText(),dateCreated);
							member.addAnswer(group, question, answer, dateCreated);
							save();
				}
			}
			
		});
		VBox vb = new VBox();
		vb.getChildren().addAll(answerL,questionDescriptionL, answerTA, btnSubmit);
		
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