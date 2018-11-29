package sprint1;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


public class Controller {
	private SiteManager sm;
	private PersistanceManager pm;
	private File file = new File("SiteManger_File");
	private List<Member> members;
	private List<Group> groups;
	
	@FXML
	private ListView<String> options;
	@FXML
	private TextArea optionInstructions;
	@FXML
	private BorderPane mainFrame;
	@FXML
	private GridPane mainFunction;
	
	
	
	public void initialize() {
		if(file.exists()) {
			try {
				FileInputStream fis = new FileInputStream(file);
				sm = pm.read(fis);
				members = sm.getMembers();
				groups = sm.getGroups();
			} catch(Exception e) {
				System.out.println(e + "Erro Couldnt Load SiteManager");
			}
		} else {
			sm = new SiteManager();
			members = new ArrayList<Member>();
			groups = new ArrayList<Group>();
		}
		
		
		pm = new PersistanceManager();
		String[] siteOptions = {"Add Member", "Add Group", "Members", "Groups"};
		options.getItems().setAll(siteOptions);
		options.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		optionInstructions.setEditable(false);
		optionInstructions.setPrefRowCount(1);
	}
	
	@FXML
	public void handleClickListView() {
		String option = options.getSelectionModel().getSelectedItem();
		optionInstructions.setText("You've Choosen to: " + option);
		
		if(option.equals("Add Member")) {
			mainFrame.setCenter(mainFunction);
			createAddMemberScene();
		} 
		else if(option.equals("Add Group")) {
			mainFrame.setCenter(mainFunction);
			createAddGroupScene();
		} else if(option.equals("Members")) {
			createMembersScene();
		} else if(option.equals("Groups")) {
			createGroupScene();
		}
		else {
			mainFunction.getChildren().clear();
		}
		
		
	}
	
	
	
	private void createAddMemberScene() {
		mainFunction.getChildren().clear();
		Label emailL = new Label("Email");
		TextField emailTF = new TextField();
		Label firstNameL = new Label("First Name");
		TextField firstNameTF = new TextField();
		Label lastNameL = new Label("Last Name");
		TextField lastNameTF = new TextField();
		Label screenNameL = new Label("Screen Name");
		TextField screenNameTF = new TextField();
		Button btnSave = new Button();
		btnSave.setText("Save");
		btnSave.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(!emailTF.getText().isEmpty() && !firstNameTF.getText().isEmpty() && !lastNameTF.getText().isEmpty()
						&& !screenNameTF.getText().isEmpty()) {
					LocalDateTime dateCreated = LocalDateTime.now();
					if(!sm.addMember(firstNameTF.getText(), lastNameTF.getText(), screenNameTF.getText(), emailTF.getText(), dateCreated)) {
						optionInstructions.setText("  ERROR - Member with this email already exists");
					} else {
						optionInstructions.setText("  SUCSESS - Member was added");
					}
					save();
					
				} else {
					optionInstructions.setText("  ERROR - all fields are required");
				}	
			}
		});
		mainFunction.setAlignment(Pos.CENTER);
		mainFunction.setPadding(new Insets(20,20,20,20));
		mainFunction.add(emailL, 0, 0);
		mainFunction.add(emailTF, 1, 0);
		mainFunction.add(firstNameL, 0, 1);
		mainFunction.add(firstNameTF, 1, 1);
		mainFunction.add(lastNameL, 0, 2);
		mainFunction.add(lastNameTF, 1, 2);
		mainFunction.add(screenNameL, 0, 3);
		mainFunction.add(screenNameTF, 1, 3);
		mainFunction.add(btnSave, 2, 4);
	}
	
	private void createAddGroupScene() {
		mainFunction.getChildren().clear();
		Label titleL = new Label("Title");
		TextField titleTF = new TextField();
		Label descriptionL = new Label("Description");
		TextArea descriptionTA = new TextArea();
		Button btnSave = new Button();
		btnSave.setText("Save");
		btnSave.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(!titleTF.getText().isEmpty() && !descriptionTA.getText().isEmpty()) {
					LocalDateTime dateCreated = LocalDateTime.now();
					if(!sm.addGroup(titleTF.getText(), descriptionTA.getText(), dateCreated)) {
						optionInstructions.setText("  ERROR - Group already exists");
					} else {
						optionInstructions.setText("  SUCSESS - Group added");
						save();
					}
				} else {
					optionInstructions.setText("  ERROR - all fields are required");
				}	
			}
		});
		mainFunction.setAlignment(Pos.CENTER);
		mainFunction.setPadding(new Insets(20,20,20,20));
		mainFunction.add(titleL, 0, 0);
		mainFunction.add(titleTF, 1, 0);
		mainFunction.add(descriptionL, 0, 1);
		mainFunction.add(descriptionTA, 1, 1);
		mainFunction.add(btnSave, 2, 4);
	}
	
	private void createMembersScene() {
		ListView<String> membersEmailList = new ListView<String>();
		ListView<String> memberGroupList = new ListView<String>();
		BorderPane bp = new BorderPane();
		VBox memberInfoVB = new VBox();
		Label memberNameL = new Label();
		Label groupL = new Label("Groups");
		Label groupL2 = new Label("Groups");
		Label memberDateCreatedL = new Label();
		ScrollPane sp = new ScrollPane();
		bp.setLeft(membersEmailList);
		
		for(Member member : members) {
			membersEmailList.getItems().add(member.getEmailAddress()); 
		}
		
		
		
		membersEmailList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		membersEmailList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					String member = membersEmailList.getSelectionModel().getSelectedItem();
					Member thisMember = sm.getMember(member);
					for(Group group : thisMember.getGroups()) {
						memberGroupList.getItems().add(group.getDescription());
					}
					memberGroupList.setMaxHeight(100.0);
					
					optionInstructions.setText("You've Choosen to: " + member);
					String name = sm.getMember(member).getFirstName() + " " + sm.getMember(member).getLastName();
					memberNameL.setText(name);
					memberDateCreatedL.setText("Added: " +  thisMember.getDateCreated().toString());
					memberInfoVB.getChildren().addAll(memberNameL, memberDateCreatedL, groupL, memberGroupList);
					
					sp.setContent(memberInfoVB);
					
					bp.setCenter(sp);
					
					System.out.println("Click member: " + name);
					System.out.println("Email: " + thisMember.getEmailAddress());
					System.out.println("Number of Groups: " + thisMember.getGroups().size());
					System.out.println("Part of these Groups: " + thisMember.getGroups().toString());
					System.out.println("Date Created: " + thisMember.getDateCreated());
					
					
				} catch(Exception e) {
					System.out.println("Click at member");
				}
			}
		});
		
		mainFrame.setCenter(bp);
	}
	
	private void createGroupScene() {
		ListView<String> groupTitles = new ListView<String>();
		BorderPane bp = new BorderPane();
		bp.setLeft(groupTitles);
		for(Group group : groups) {
			groupTitles.getItems().add(group.getTitle());
		}
		
		mainFrame.setCenter(bp);
	}
	
	private void save() {
		try {
			FileOutputStream fos = new FileOutputStream(file);
			pm.save(sm, fos);
		} catch(Exception e) {
			System.out.println("ERROR: "+ e);
		}
	}
	
	private void handelEmailListClick() {
		
	}
}
