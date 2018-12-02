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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
	protected VBox groupInfoVB = new VBox();
	protected VBox questionFormVB = new VBox();
	
	
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
		
	if(option == null){
		optionInstructions.setText("");
	}else if(option.equals("Add Member")) {
			mainFrame.setCenter(mainFunction);
			createAddMemberScene();
		} 
		else if(option.equals("Add Group")) {
			mainFrame.setCenter(mainFunction);
			createAddGroupScene();
		} else if(option.equals("Members")) {
			createMembersScene(null);
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
	
	private void createMembersScene(String member) {
		mainFunction.getChildren().clear();
		ListView<String> membersEmailList = new ListView<String>();
		BorderPane bp = new BorderPane();
		bp.setLeft(membersEmailList);

		for(Member m : members) {
			membersEmailList.getItems().add(m.getEmailAddress()); 
		}
		
		membersEmailList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		if(member != null) {
			membersEmailList.getSelectionModel().select(member);
		}
		membersEmailList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					
					groupInfoVB.getChildren().clear();
					ComboBox<String> groupCB = new ComboBox<String>();
					ListView<String> memberGroupList = new ListView<String>();
					List<String> thisMembersGroups = new ArrayList<String>();
					VBox memberInfoVB = new VBox();
					HBox labelAndCombo = new HBox();
					Label memberNameL = new Label();
					Label groupL = new Label("Groups");
					Label memberDateCreatedL = new Label();
					ScrollPane sp = new ScrollPane();
					
					Label groupNameL = new Label();
					groupCB.setPromptText("Join Group");
					String member = membersEmailList.getSelectionModel().getSelectedItem();

					for(Group group : sm.getMember(member).getGroups()) {
						if(!memberGroupList.getItems().contains(group.getTitle())) {
							memberGroupList.getItems().add(group.getTitle());
							thisMembersGroups.add(group.getTitle());
						}
						
					}
					
					for(Group group : sm.getGroups()) {
						if(!thisMembersGroups.contains(group.getTitle())) {
							groupCB.getItems().add(group.getTitle());
						}
					}
					
					groupCB.valueProperty().addListener(new ChangeListener<String>() {
						@Override
						public void changed(ObservableValue<? extends String> observable, String oldValue,
								String newValue) {
							LocalDateTime date = LocalDateTime.now();
							System.out.println(newValue);
							sm.getMember(membersEmailList.getSelectionModel().getSelectedItem()).joinGroup(sm.getGroup(newValue), date);
							for(Group group : sm.getMember(member).getGroups()) {
								if(!memberGroupList.getItems().contains(group.getTitle())) {
									memberGroupList.getItems().add(group.getTitle());
									thisMembersGroups.add(group.getTitle());
								}
								
							}
							
							save();
						}
						
					});
					
					memberGroupList.setMaxHeight(100.0);
					memberGroupList.setOnMouseClicked(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent event) {
							groupInfoVB.getChildren().clear();
							String groupTitle = memberGroupList.getSelectionModel().getSelectedItem();
							createGroupPane(groupTitle, member, membersEmailList);
						}
					});
					
					
					
					
					optionInstructions.setText("You've Choosen to: " + member);
					String name = sm.getMember(member).getFirstName() + " " + sm.getMember(member).getLastName();
					memberNameL.setText(name);
					memberDateCreatedL.setText("Added: " +  sm.getMember(member).getDateCreated().toString());
					labelAndCombo.getChildren().addAll(memberGroupList, groupCB);
					memberInfoVB.getChildren().addAll(memberNameL, memberDateCreatedL, groupL, labelAndCombo, groupInfoVB);
					sp.setContent(memberInfoVB);
					bp.setCenter(sp);
			}catch(Exception e) {
				System.out.println(e);
			}}
		});
		
		mainFrame.setCenter(bp);
	}
	
	private void createGroupScene() {
		groupInfoVB.getChildren().clear();
		ListView<String> groupTitles = new ListView<String>();
		groupTitles.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		BorderPane bp = new BorderPane();
		bp.setLeft(groupTitles);
		for(Group group : groups) {
			groupTitles.getItems().add(group.getTitle());
		}
		groupTitles.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				String groupTitle = groupTitles.getSelectionModel().getSelectedItem();
				createGroupPane(groupTitle, null, null);
			}
		});
		
		bp.setCenter(groupInfoVB);
		
		
		mainFrame.setCenter(bp);
	}
	
	private void createGroupPane(String groupTitle, String member, ListView<String> membersEmailList) {
		groupInfoVB.getChildren().clear();
		Label groupL = new Label(groupTitle);
		Label questionL = new Label("Questions");
		ListView<String> questions = new ListView<String>();
		Button btnAdd = new Button("Add Question");
		
		btnAdd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Creating Add Question Form for: "+ member + " : " + groupTitle);
				createAddQuestionPane(sm.getMember(member), sm.getGroup(groupTitle));
				BorderPane bp = new BorderPane();
				bp.setCenter(questionFormVB);
				bp.setLeft(membersEmailList);
				membersEmailList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
				membersEmailList.setOnMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						createMembersScene(membersEmailList.getSelectionModel().getSelectedItem());
					}
				});
				mainFrame.setCenter(bp);
				
			}
		});
		
		groupInfoVB.getChildren().addAll(groupL, questionL, questions);
		if(member != null) {
			groupInfoVB.getChildren().add(btnAdd);
		}
		
		
	}
	
	private void createAddQuestionPane(Member member, Group group) {
		Label questionTitleL = new Label("Title");
		TextField questionTitleTF = new TextField();
		Label questionDescriptionL = new Label("Description");
		TextArea questionDescriptionTA = new TextArea();
		Button btnSubmitQuestion = new Button("Submit");
		btnSubmitQuestion.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(!questionDescriptionTA.getText().isEmpty() && !questionTitleTF.getText().isEmpty()) {
					try {
						LocalDateTime dateCreated = LocalDateTime.now();
						Question question = new Question(questionTitleTF.getText(), questionDescriptionTA.getText(), dateCreated);
						sm.getMember(member.getEmailAddress()).addQuestion(group, question, dateCreated);
						save();
						createMembersScene(member.getEmailAddress());
						System.out.println("Question Added");
					} catch(Exception e) {
						String error = "ERROR - " + e;
						System.out.println(error);
					}
				}else {
					String error = "ERROR - All fields required"; 
					System.out.println(error);
				} 
			}
			
		});
		
		HBox titleHBox = new HBox();
		titleHBox.getChildren().addAll(questionTitleL, questionTitleTF);
		
		questionFormVB = new VBox(titleHBox, questionDescriptionL, questionDescriptionTA, btnSubmitQuestion);
		
		
	}
	
	private void save() {
		try {
			FileOutputStream fos = new FileOutputStream(file);
			pm.save(sm, fos);
		} catch(Exception e) {
			System.out.println("ERROR: "+ e);
		}
	}
	
	
		
	
}
