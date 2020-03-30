package mines;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;

public class MyController {
	private int width = 0, height = 0, mines = 0;
	private HBox hbox;
	private Stage primaryStage;
	private Mines board;
	private ButtonMines[][] boardButton;

	@FXML
	private TextField TextFieldWidth;

	@FXML
	private TextField TextFieldHeight;

	@FXML
	private TextField TextFieldMines;
	
    @FXML
    private Label clock;
	@FXML
	void PressReset(ActionEvent event) {
		boolean flag = true;
		GridPane newGridPane = new GridPane();
		Alert alert1 = new Alert(AlertType.ERROR);
		try {
			// getting the size of the board and the number of the mines
			width = Integer.valueOf(TextFieldWidth.getText());
			height = Integer.valueOf(TextFieldHeight.getText());
			mines = Integer.valueOf(TextFieldMines.getText());
			if (height <= 0 || width <= 0 || mines < 0 || mines > height * width) {
				alert1.setTitle("Wrong Input");
				alert1.setHeaderText("What about Numbers?");
				alert1.setContentText("On the TextFields you have to enter 0 or positive numbers.");
				alert1.show();
				return;
			}
		} catch (Exception e) {
			flag = false;
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Sorry");
			alert.setHeaderText("What about Numbers?");
			alert.setContentText("On the TextFields you have to enter 0 or positive numbers.");
			alert.showAndWait();
		}

		if (flag) {
			board = new Mines(height, width, mines);
			boardButton = new ButtonMines[height][width];

			List<ColumnConstraints> firstCol = new ArrayList<ColumnConstraints>();
			List<RowConstraints> secondRow = new ArrayList<RowConstraints>();

			// Setting the size of the buttons.
			for (int i = 0; i < width; i++)
				firstCol.add(new ColumnConstraints(35));
			for (int i = 0; i < height; i++)
				secondRow.add(new RowConstraints(35));

			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {

					ButtonMines b = new ButtonMines(i, j);
					boardButton[i][j] = b;
					b.setText(board.get(i, j));
					// Button Left Click.
					b.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							boolean res = board.open(((ButtonMines) event.getSource()).getX(),
									((ButtonMines) event.getSource()).getY());
							updateButtons();
							if (!res) {
								// openAll();
								board.setShowAll(true);
								updateButtons();
								Alert alert = new Alert(AlertType.ERROR);
								alert.setTitle("Sorry");
								alert.setHeaderText("Maybe next time?");
								alert.setContentText("You Lost!");
								alert.showAndWait();
							}
							if (board.isDone()) {
								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("You are the Winner!!!");
								alert.setHeaderText(null);
								alert.setContentText("Congratulation, you are amazing ");

								alert.showAndWait();
							}
						}
					});
					b.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent event) {//put flag when right mouse clicked
							if (event.getButton() == MouseButton.SECONDARY) {
								int x = ((ButtonMines) event.getSource()).getX();
								int y = ((ButtonMines) event.getSource()).getY();
								board.toggleFlag(x, y);

							}
						}
					});
					b.setMaxWidth(Double.MAX_VALUE);
					b.setMaxHeight(Double.MAX_VALUE);
					newGridPane.add(b, j, i);
				}
			}
			newGridPane.getColumnConstraints().addAll(firstCol);
			newGridPane.getRowConstraints().addAll(secondRow);

			hbox.getChildren().remove(hbox.getChildren().size() - 1);
			hbox.getChildren().add(newGridPane);

			hbox.autosize();
			primaryStage.sizeToScene();
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setHBox(HBox hbox) {
		this.hbox = hbox;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	private void updateButtons() {
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++)
				boardButton[i][j].setText(board.get(i, j));

	}
}
