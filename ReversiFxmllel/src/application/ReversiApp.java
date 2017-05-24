package application;
	
import java.io.IOException;

import application.view.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class ReversiApp extends Application {
	private static BorderPane rootPane;
	private Stage primaryStage;
	
	private void loadRootPane() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ReversiApp.class.getResource("view/RootPane.fxml"));
			rootPane = (BorderPane) loader.load();
						
			Scene scene = new Scene(rootPane);
			primaryStage.setScene(scene);
			primaryStage.show();
		
			ReversiViewController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static BorderPane getRoot() {
	    return rootPane;
	  }
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("::Reversi by Vass Csaba::");

		loadRootPane();

	}

	public static void main(String[] args) {
		launch(args);
	}

	
}
