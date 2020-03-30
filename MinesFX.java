package mines;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javafx.stage.Stage;

public class MinesFX extends Application {
	private Stage mainPrimaryStage;

	@Override
	public void start(Stage primaryStage) {
		mainPrimaryStage = primaryStage;
		Scene scene = new Scene(createRoot());
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	private HBox createRoot() {
		HBox hbox;
		MyController controller;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("MinesFXML.fxml"));
			hbox = loader.load();
			controller = loader.getController();

			GridPane gridpane = new GridPane();

			//sending Hbox and PrimaryStage to controller.
			controller.setHBox(hbox);
			controller.setPrimaryStage(mainPrimaryStage);

			//Adding the GridPane to the right side
			hbox.getChildren().add(gridpane);

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return hbox;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
