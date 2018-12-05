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
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class Controller {
	private SiteManager sm;
	private PersistanceManager pm;
	private File file = new File("SiteManager_File");
	private List<Member> members;
	private List<Group> groups;
	private Question clickedQuestion = new Question(null,null,null);
	private ListView<String> membersEmailList = new ListView<String>();
	protected VBox groupInfoVB = new VBox();
	protected VBox questionFormVB = new VBox();
	Label headingLabel = new Label("");
	

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
				System.out.println(e + "Error Couldn't Load SiteManager");
			}
		} else {
			sm = new SiteManager();
			members = new ArrayList<Member>();
			groups = new ArrayList<Group>();
		}
		pm = new PersistanceManager();
		String[] siteOptions = {"Members", "Groups"};
		options.getItems().setAll(siteOptions);
		options.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		optionInstructions.setEditable(false);
		optionInstructions.setPrefRowCount(1);
		Label welcome = new Label("Welcome");
		headingLabel.setStyle("-fx-font-weight: bold;");
		headingLabel.setFont(new Font("Arial", 30));
		mainFrame.setCenter(welcome);
		
		
	}

	@FXML
	public void handleClickListView() {
		String option = options.getSelectionModel().getSelectedItem();
		optionInstructions.setStyle("-fx-text-fill: black;");
		optionInstructions.setText("You've Choosen: " + option);
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
		} else {
			mainFunction.getChildren().clear();
		}
	}

	private GridPane createAddMemberScene() {
		mainFunction.getChildren().clear();
		membersEmailList.getItems().clear();
		headingLabel.setText("Add New Member");
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
						optionInstructions.setStyle("-fx-text-fill: red;");
						optionInstructions.setText("  ERROR - Member with this email already exists");
					} else {
						membersEmailList.getItems().clear();
						for(Member m : sm.getMembers()) {
							membersEmailList.getItems().add(m.getEmailAddress());
						}
						optionInstructions.setStyle("-fx-text-fill: green;");
						optionInstructions.setText("  SUCCESS - Member was added");
					}
					save();

				} else {
					optionInstructions.setStyle("-fx-text-fill: red;");
					optionInstructions.setText("  ERROR - all fields are required");
				}	
			}
		});
		
		
		
		
		mainFunction.setPadding(new Insets(20,20,20,20));
		mainFunction.add(headingLabel, 0, 0);
		mainFunction.add(emailL, 0, 1);
		mainFunction.add(emailTF, 0, 2);
		mainFunction.add(firstNameL, 0, 3);
		mainFunction.add(firstNameTF, 0, 4);
		mainFunction.add(lastNameL, 0, 5);
		mainFunction.add(lastNameTF, 0, 6);
		mainFunction.add(screenNameL, 0, 7);
		mainFunction.add(screenNameTF, 0, 8);
		mainFunction.add(btnSave, 0, 9);
		return mainFunction;
	}
	
	private GridPane createAddGroupScene() {
		
		mainFunction.getChildren().clear();
		
		headingLabel.setText("Add New Group");
		Label titleL = new Label("Title");
		TextField titleTF = new TextField();
		Label descriptionL = new Label("Description");
		TextArea descriptionTA = new TextArea();
		descriptionTA.setWrapText(true);
		Button btnSave = new Button();
		btnSave.setText("Save");
		btnSave.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(!titleTF.getText().isEmpty() && !descriptionTA.getText().isEmpty()) {
					LocalDateTime dateCreated = LocalDateTime.now();
					if(!sm.addGroup(titleTF.getText(), descriptionTA.getText(), dateCreated)) {
						optionInstructions.setStyle("-fx-text-fill: red;");
						optionInstructions.setText("  ERROR - Group already exists");
					} else {
						optionInstructions.setStyle("-fx-text-fill: green;");
						optionInstructions.setText("  SUCCESS - Group added");
						save();
					}
				} else {
					optionInstructions.setStyle("-fx-text-fill: red;");
					optionInstructions.setText("  ERROR - all fields are required");
				}	
			}
		});
		mainFunction.add(headingLabel, 0, 0);
		mainFunction.add(titleL, 0, 1);
		mainFunction.add(titleTF, 0, 2);
		mainFunction.add(descriptionL, 0, 3);
		mainFunction.add(descriptionTA, 0, 4);
		mainFunction.add(btnSave, 0, 5);
		return mainFunction;
	}
	
	private void createMembersScene(String member) {
		mainFunction.getChildren().clear();
		VBox vb = new VBox();
		HBox searchMembers = new HBox();
		Button searchB = new Button("Search Members");
		TextField searchTF = new TextField();
		searchMembers.getChildren().addAll(searchB, searchTF);
		vb.getChildren().addAll(searchMembers,membersEmailList);
		vb.setVgrow(membersEmailList, Priority.ALWAYS);
		BorderPane bp = new BorderPane();
		bp.setLeft(vb);
		bp.setCenter(createAddMemberScene());
		searchB.setOnAction(new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			if(!searchTF.getText().isEmpty()) {
				membersEmailList.getItems().clear();
				for(Member m: sm.getMembers(searchTF.getText())) {
					membersEmailList.getItems().add(m.getEmailAddress());
				}
			} else {
				membersEmailList.getItems().clear();
				for(Member m: sm.getMembers()) {
					membersEmailList.getItems().add(m.getEmailAddress());
				}
			}
			
		}
	});
		
		for(Member m : members) {
			membersEmailList.getItems().add(m.getEmailAddress()); 
		}

		membersEmailList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		if(member != null) {
			membersEmailList.getSelectionModel().select(member);
		}

		membersEmailList.setOnMouseClicked(membersEmailListHandler( bp));
		mainFrame.setCenter(bp);
	}

	private EventHandler<MouseEvent> membersEmailListHandler(BorderPane bp){
		return (new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {

					groupInfoVB.getChildren().clear();
					Label exp = new Label("EXP: ");
					ComboBox<String> groupCB = new ComboBox<String>();
					ListView<String> memberGroupList = new ListView<String>();
					List<String> thisMembersGroups = new ArrayList<String>();
					VBox memberInfoVB = new VBox();
					memberInfoVB.setSpacing(5);
					memberInfoVB.setPadding(new Insets(10,10,10,10));
					groupInfoVB.setSpacing(5);
					Label memberNameL = new Label();
					memberNameL.setStyle("-fx-font-weight: bold;");
					memberNameL.setFont(new Font("Arial", 20));
					
					Label groupL = new Label("Groups");
					groupL.setStyle("-fx-font-weight: bold;");
					groupL.setFont(new Font("Arial", 20));
					
					
					Label memberDateCreatedL = new Label();
					ScrollPane sp = new ScrollPane();
					Label groupNameL = new Label();
					groupNameL.setStyle("-fx-font-weight: bold;");
					groupNameL.setFont(new Font("Arial", 30));
					
					groupCB.setPromptText("Join Group");
					String member = membersEmailList.getSelectionModel().getSelectedItem();
					int count = 0;
					for(Membership membership : sm.getMember(member).getMemberships()) {
						count += membership.getPoints();
					}
					exp.setText("EXP: " + count);
					
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
							//System.out.println(newValue);
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
							createGroupPane(groupTitle, member, membersEmailList, false, 0);
						}
					});
					
					optionInstructions.setStyle("-fx-text-fill: black;");
					optionInstructions.setText("You've Choosen to: " + member);
					String name = sm.getMember(member).getFirstName() + " " + sm.getMember(member).getLastName();
					memberNameL.setText(name);
					memberDateCreatedL.setText("Added: " +  sm.getMember(member).getDateCreated().toString());
					exp.setFont(new Font("Arial", 15));
					
					memberInfoVB.getChildren().addAll(memberNameL, exp, memberDateCreatedL, groupL, memberGroupList, groupCB, groupInfoVB);
					sp.setContent(memberInfoVB);
					bp.setCenter(sp);
				}catch(Exception e) {
					System.out.println(e);
				}}
		});

	}

	private void createGroupScene() {
		groupInfoVB.getChildren().clear();
		ListView<String> groupTitles = new ListView<String>();
		VBox groupsListVBox = new VBox();
		HBox filterButtons = new HBox();
		Button getActiveGroupsB = new Button("Active Groups");
		Button getPopularGroupsB = new Button("Popular Groups");
		
		Button getMatchingGroupSB = new Button("Search Groups");
		TextField searchGroupsTF = new TextField();
		HBox searchBox = new HBox();
		searchBox.getChildren().addAll(getMatchingGroupSB, searchGroupsTF);
		filterButtons.getChildren().addAll(getActiveGroupsB, getPopularGroupsB);
		groupsListVBox.getChildren().addAll(searchBox, groupTitles, filterButtons);
		groupTitles.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		BorderPane bp = new BorderPane();
		groupsListVBox.setVgrow(groupTitles, Priority.ALWAYS);
		bp.setLeft(groupsListVBox);
		bp.setCenter(createAddGroupScene());
		
		getMatchingGroupSB.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(!searchGroupsTF.getText().isEmpty()) {
					groupTitles.getItems().clear();
					for(Group g: sm.getGroups(searchGroupsTF.getText())) {
						groupTitles.getItems().add(g.getTitle());
					}
				} else {
					groupTitles.getItems().clear();
					for(Group g: sm.getGroups()) {
						groupTitles.getItems().add(g.getTitle());
					}
				}
			}
			
		});
		
		
		getPopularGroupsB.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				groupTitles.getItems().clear();
				for (Group g: sm.getPopularGroups(sm.getGroups().size()) ) {
					groupTitles.getItems().add(g.getTitle());
				}


			}
		});

		getActiveGroupsB.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				groupTitles.getItems().clear();
				for (Group g: sm.getActiveGroups(sm.getGroups().size()) ) {
					groupTitles.getItems().add(g.getTitle());
				}
			}
		});

		for(Group group : groups) {
			groupTitles.getItems().add(group.getTitle());
		}
		
		groupTitles.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				String groupTitle = groupTitles.getSelectionModel().getSelectedItem();
				optionInstructions.setStyle("-fx-text-fill: black;");
				optionInstructions.setText("You've Chosen: " + groupTitle);
				bp.getChildren().clear();
				bp.setLeft(groupsListVBox);
				bp.setCenter(groupInfoVB);
				
				createGroupPane(groupTitle, null, null, false, 0);////!
			}
		});
		
			
		mainFrame.setCenter(bp);
	}

	private void createGroupPane(String groupTitle, String member, ListView<String> membersEmailList, boolean filter, int filterNumber) {
		groupInfoVB.getChildren().clear();
		groupInfoVB.setPadding(new Insets(10,10,10,10));
		groupInfoVB.setSpacing(5);
		String memberEmail = member;
		Label groupL = new Label(groupTitle);
		Label questionsL = new Label();
		Label commentL = new Label("Comments");
		Label answersL = new Label("Answers (" + sm.getGroup(groupTitle).getAnswers().size() + ")" );
		questionsL.setText("Questions (" + sm.getGroup(groupTitle).getQuestions().size() + ")" );
		ListView<String> questions = new ListView<String>();
		ListView<String> answers = new ListView<String>();
		questions.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		answers.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		ArrayList<Question> questionsList = new ArrayList<Question>();
		ArrayList<Answer> answersList = new ArrayList<Answer>();
		ListView<String> comments = new ListView<String>();
		Label memberL = new Label();
		memberL.setText("Members (" + sm.getGroup(groupTitle).getNumOfMembers() + ")" );
		ListView<String> members = new ListView<String>();
		Button btnAdd = new Button("Add Question");
		Button btnFilterQuestions = new Button("Recent Questions");
		Button btnFilterAnswers = new Button("Recent Answers");
		if (!filter) {
			for(Question question : sm.getGroup(groupTitle).getQuestions()) {
				if(!questions.getItems().contains(question.getTitle())) {
					questions.getItems().add(question.getTitle()); 
					questionsList.add(question);
				}
			}
		} else {
			questions.getItems().clear();		
			for(Question question : sm.getMember(member).getQuestions(sm.getGroup(groupTitle) ,filterNumber)) {
				if(!questions.getItems().contains(question.getTitle())) {
					questions.getItems().add(question.getTitle()); 
					questionsList.add(question);
				}
			}	
		}

		for (Member m: sm.getGroup(groupTitle).getMembers()) {
			if(!members.getItems().contains(m.getScreenName())) {
				members.getItems().add(m.getScreenName());
			}
		}

		if(member != null) {
			questions.setMaxHeight(100.0);
			questions.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					groupInfoVB.getChildren().clear();
					Question question = new Question(null,null,null);
					for(Question q : sm.getGroup(groupTitle).getQuestions()) {
						if(questions.getSelectionModel().getSelectedItem().equals(q.getTitle())) {
							question = q;
						}
					}
					QuestionControlPane qp = new QuestionControlPane(sm.getMember(memberEmail), sm.getGroup(groupTitle), question, pm, sm);
					groupInfoVB.getChildren().add(qp.createControlPane());
				}
			});

		} else {
			questions.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					if(!questions.getItems().isEmpty()) {
						
					
					ListView<String> answers = new ListView<String>();
					String questionClicked = questions.getSelectionModel().getSelectedItem();
					for(Comment comment : clickedQuestion.comments) {
						comments.getItems().add(comment.getText());
					}
					for (Question q: questionsList) {
						if (q.getTitle() == questionClicked) {
							clickedQuestion = q;
							for (Answer a: q.getAnswers()) {
								answers.getItems().add(a.getText());
								answersList.add(a);
							}
						}
					}		
					Label questionsL = new Label("Questions");
					Label answersL = new Label("Answers");
					groupInfoVB.getChildren().clear();
					groupInfoVB.getChildren().addAll(questionsL, questions, answersL, answers,commentL, comments, btnFilterAnswers);
					}
				}
			});
		}

		btnAdd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {		
				createAddQuestionPane(sm.getMember(member), sm.getGroup(groupTitle));
				BorderPane bp = new BorderPane();
				bp.setCenter(questionFormVB);
				bp.setLeft(membersEmailList);
				membersEmailList.setOnMouseClicked(membersEmailListHandler(bp));
				mainFrame.setCenter(bp);
			}
		});

		btnFilterQuestions.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {		
				Label filterNumber = new Label("Number of Questions to filter: ");
				TextField filterNumberTF = new TextField();
				Button btnSubmit = new Button("Submit");
				HBox filterQuestionsHBox = new HBox();
				filterQuestionsHBox.getChildren().addAll(filterNumber, filterNumberTF);
				VBox filterQuestionsSubmitVB = new VBox();
				filterQuestionsSubmitVB.getChildren().clear();
				filterQuestionsSubmitVB.getChildren().addAll(filterNumber, filterNumberTF, btnSubmit);
				groupInfoVB.getChildren().addAll(filterQuestionsSubmitVB);
				btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {	
						if(!filterNumberTF.getText().isEmpty() && filterNumberTF != null) {
							int filterNumber = Integer.parseInt(filterNumberTF.getText());
							if (filterNumber > questionsList.size() ) {
								Label alert = new Label("Input must be an integer <= questionsList.size()!!");
								groupInfoVB.getChildren().add(alert);
							} else {
								createGroupPane(groupTitle, member,  membersEmailList, true, filterNumber );
							}
						}
						
					}
				});

			}
		});

		btnFilterAnswers.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {		
				ListView<String> filteredAnswers = new ListView<String>();
				Label filterNumber = new Label("Number of Answers to filter: ");
				TextField filterNumberTF = new TextField();
				Button btnSubmitAnswerFilter = new Button("Submit");
				HBox filterAnswersHBox = new HBox();
				filterAnswersHBox.getChildren().addAll(filterNumber, filterNumberTF);
				groupInfoVB.getChildren().addAll(filterAnswersHBox, btnSubmitAnswerFilter);
				btnSubmitAnswerFilter.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						try {
							if(!filterNumberTF.getText().isEmpty() && filterNumberTF != null) {
								int filterNumber = Integer.parseInt(filterNumberTF.getText());
								if (filterNumber > answersList.size() || answersList.size() < 1 ) {
									Label alert = new Label("Input must be an integer <= answersList.size()!!");
									groupInfoVB.getChildren().add(alert);
								} else {
									
									//System.out.println();
									for (int i = 1; i <= filterNumber; i++)  {
										filteredAnswers.getItems().add(clickedQuestion.getAnswers().get(clickedQuestion.getAnswers().size() - i).getText());
									}
									answers.setMaxHeight(100);
									groupInfoVB.getChildren().clear();
									groupInfoVB.getChildren().addAll(questionsL, questions,btnFilterQuestions, answersL, filteredAnswers, btnFilterAnswers);
							}
						}
						
					}catch(Exception e) {
						optionInstructions.setStyle("-fx-text-fill: red;");
						optionInstructions.setText("ERROR - " + "Put a number of recent answers you would like to");
						
					}
					}
				});
			}
		});

		if (member == null) {
			groupInfoVB.getChildren().addAll(groupL,new Label(sm.getGroup(groupTitle).getDescription()), questionsL, questions, memberL, members);
		} else if (member != null) {
			groupInfoVB.getChildren().addAll(groupL, questionsL, questions);
		}
		if(member != null) {
			HBox buttonBox = new HBox();
			buttonBox.getChildren().addAll(btnAdd, btnFilterQuestions);
			groupInfoVB.getChildren().addAll(buttonBox); 
		}
	}

	private void createAddQuestionPane(Member member, Group group) {
		Label addQuestionL = new Label("Add Question");
		addQuestionL.setStyle("-fx-font-weight: bold;");
		addQuestionL.setFont(new Font("Arial", 30));
		Label questionTitleL = new Label("Title");
		TextField questionTitleTF = new TextField();
		Label questionDescriptionL = new Label("Description");
		TextArea questionDescriptionTA = new TextArea();
		questionDescriptionTA.setWrapText(true);
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
						//System.out.println("Question Added");
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
		BorderPane bp = new BorderPane();
		VBox vb = new VBox();
		vb.getChildren().addAll(addQuestionL,questionTitleL,questionTitleTF, questionDescriptionL, questionDescriptionTA, btnSubmitQuestion);
		bp.setLeft(vb);
		questionFormVB = new VBox(bp);
		vb.setSpacing(5);
		vb.setPadding(new Insets(10,10,10,10));
		
		
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