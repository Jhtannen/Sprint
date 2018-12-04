package sprint1;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.Node;
import javafx.scene.Parent;

public class Main extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("site.fxml"));
		primaryStage.setTitle("Site Manager");
		primaryStage.setScene(new Scene(root, 900, 800));//Changed 500 to 800
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
