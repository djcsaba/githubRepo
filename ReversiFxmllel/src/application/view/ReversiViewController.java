package application.view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import application.ReversiApp;
import application.model.Game;
import application.model.Point;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class ReversiViewController {

	private ReversiApp app;
	int SIZE = 8;
	private Game game;
	int nextPlayer = 0;
	List<Point> whitePointsCoordinates = new ArrayList<Point>();
	List<Point> blackPointsCoordinates = new ArrayList<Point>();

	@FXML
	private Menu menu;
	@FXML
	private MenuItem newMenuItem;
	@FXML
	private MenuItem loadMenuItem;
	@FXML
	private MenuItem saveMenuItem;
	@FXML
	private MenuItem exitMenuItem;
	@FXML
	private GridPane gameGrid;
	@FXML
	private Label player1;
	@FXML
	private Label player2;
	@FXML
	private Label whitePoints;
	@FXML
	private Label blackPoints;

	@FXML
	void newGame(ActionEvent event) {

		try {

			AnchorPane currentPane = FXMLLoader.load(getClass().getResource("NewGame.fxml"));

			BorderPane border = ReversiApp.getRoot();
			border.setCenter(currentPane);
			player1.setText("Player 1");
			player2.setText("Player 2");
			initGrid();
			loadView();
			readFromFile("new");
			setStartView();
			setScores();
			play();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	private void loadGame() {

		player1.setText("Player 1");
		player2.setText("Player 2");
		this.game = null;
		setScores();
		initGrid();
		loadView();
		readFromFile("");
		setStartView();
		setScores();
		play();
	}

	@FXML
	private void saveGame() throws TransformerException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("XML File", "*.xml"));
		Stage temp = this.app.getPrimaryStage();
		File file = fileChooser.showSaveDialog(temp);
		if (file != null) {
			try {
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer;
				transformer = transformerFactory.newTransformer();
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.newDocument();
				Element root = doc.createElement("Reversi");
				doc.appendChild(root);
				Element nextPlayer = doc.createElement("NextPlayer");
				nextPlayer.appendChild(doc.createTextNode(game.getCurrentPlayer() + ""));
				root.appendChild(nextPlayer);
				Element white = doc.createElement("White");
				Element wpoints = doc.createElement("Points");
				for (Point elem : game.getWhite()) {
					Element point = doc.createElement("Point");
					Element x = doc.createElement("X");
					x.appendChild(doc.createTextNode(elem.getX() + ""));
					Element y = doc.createElement("Y");
					y.appendChild(doc.createTextNode(elem.getY() + ""));
					point.appendChild(x);
					point.appendChild(y);
					wpoints.appendChild(point);
				}
				white.appendChild(wpoints);
				root.appendChild(white);

				Element black = doc.createElement("Black");
				Element bpoints = doc.createElement("Points");
				for (Point elem : game.getBlack()) {
					Element point = doc.createElement("Point");
					Element x = doc.createElement("X");
					x.appendChild(doc.createTextNode(elem.getX() + ""));
					Element y = doc.createElement("Y");
					y.appendChild(doc.createTextNode(elem.getY() + ""));
					point.appendChild(x);
					point.appendChild(y);
					bpoints.appendChild(point);
				}
				black.appendChild(bpoints);
				root.appendChild(black);

				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File(file.getPath()));
				transformer.transform(source, result);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@FXML
	private void exitGame() {
		System.exit(0);

	}

	private void readFromFile(String gameType) {

		whitePointsCoordinates = new ArrayList<Point>();
		blackPointsCoordinates = new ArrayList<Point>();

		if (gameType.equals("new")) {
			nextPlayer = 0;
			whitePointsCoordinates.add(new Point(3, 3));
			whitePointsCoordinates.add(new Point(4, 4));
			blackPointsCoordinates.add(new Point(3, 4));
			blackPointsCoordinates.add(new Point(4, 3));
			return;
		}

		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("XML File", "*.xml"));
		Stage temp = this.app.getPrimaryStage();
		File file = fileChooser.showOpenDialog(temp);
		if (file != null) {

			try {
				String filepath = file.getPath();
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.parse(filepath);

				Element reversi = doc.getDocumentElement();
				Element next = (Element) reversi.getElementsByTagName("NextPlayer").item(0);
				Element white = (Element) reversi.getElementsByTagName("White").item(0);
				Element black = (Element) reversi.getElementsByTagName("Black").item(0);

				nextPlayer = Integer.parseInt(next.getTextContent());
				Element pointsw = (Element) white.getElementsByTagName("Points").item(0);
				NodeList listaw = pointsw.getElementsByTagName("Point");
				for (int i = 0; i < listaw.getLength(); i++) {
					int x = Integer
							.parseInt(((Element) listaw.item(i)).getElementsByTagName("X").item(0).getTextContent());
					int y = Integer
							.parseInt(((Element) listaw.item(i)).getElementsByTagName("Y").item(0).getTextContent());
					Point p = new Point(x, y);
					whitePointsCoordinates.add(p);
				}

				Element pointsb = (Element) black.getElementsByTagName("Points").item(0);
				NodeList listab = pointsb.getElementsByTagName("Point");
				for (int i = 0; i < listab.getLength(); i++) {
					int x = Integer
							.parseInt(((Element) listab.item(i)).getElementsByTagName("X").item(0).getTextContent());
					int y = Integer
							.parseInt(((Element) listab.item(i)).getElementsByTagName("Y").item(0).getTextContent());
					Point p = new Point(x, y);
					blackPointsCoordinates.add(p);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			;

		}

	}

	private void initGrid() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setMaxHeight(334);
		grid.setMaxWidth(334);
		grid.setMinHeight(334);
		grid.setMinWidth(334);
		grid.setHgap(2);
		grid.setVgap(2);
		gameGrid = grid;
	}

	private void loadView() {
		AnchorPane currentPane;
		try {
			currentPane = FXMLLoader.load(getClass().getResource("Game.fxml"));
			BorderPane border = ReversiApp.getRoot();
			border.setCenter(currentPane);
			currentPane.getChildren().add(gameGrid);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void setStartView() {

		if (whitePointsCoordinates.size() != 0 && blackPointsCoordinates.size() != 0) {

			game = new Game(nextPlayer, whitePointsCoordinates, blackPointsCoordinates);

			for (int i = 0; i < SIZE; i++) {
				RowConstraints row = new RowConstraints(40);
				gameGrid.getRowConstraints().add(row);
				ColumnConstraints column = new ColumnConstraints(40);
				gameGrid.getColumnConstraints().add(column);
				for (int j = 0; j < SIZE; j++) {
					setToBase(i, j);
				}
			}
			for (int k = 0; k < game.getWhite().size(); k++) {
				int i = game.getWhite().get(k).getX();
				int j = game.getWhite().get(k).getY();
				setToWhite(i, j);
			}

			for (int k = 0; k < game.getBlack().size(); k++) {
				int i = game.getBlack().get(k).getX();
				int j = game.getBlack().get(k).getY();
				setToBlack(i, j);
			}
		}

	}

	private void play() {

		if (game != null) {
			List<Point> possibleMoves = game.getPossibleMoves();

			for (int k = 0; k < possibleMoves.size(); k++) {

				setToPossible(possibleMoves.get(k).getX(), possibleMoves.get(k).getY());
			}
			if (!game.isEnded()) {
			}
		}

	}

	private void setScores() {
		if (game != null) {

			whitePoints.setText(game.getWhite().size() + "");
			blackPoints.setText(game.getBlack().size() + "");
		} else {
			whitePoints.setText("0");
			blackPoints.setText("0");
		}

	}

	private void setToBase(int i, int j) {
		Image feher = new Image("file:Images/base.jpg");
		ImageView pic = new ImageView();
		pic.setFitWidth(40);
		pic.setFitHeight(40);
		pic.setImage(feher);
		gameGrid.add(pic, i, j);
	}

	private void setToBlack(int i, int j) {
		Image fekete = new Image("file:Images/black.jpg");
		ImageView pic = new ImageView();
		pic.setFitWidth(40);
		pic.setFitHeight(40);
		pic.setImage(fekete);
		gameGrid.add(pic, i, j);
	}

	private void setToWhite(int i, int j) {
		Image feher = new Image("file:Images/white.jpg");
		ImageView pic = new ImageView();
		pic.setFitWidth(40);
		pic.setFitHeight(40);
		pic.setImage(feher);
		gameGrid.add(pic, i, j);
	}

	private void setToPossible(int i, int j) {
		Image feher = new Image("file:Images/possible.jpg");
		ImageView pic = new ImageView();
		pic.setFitWidth(40);
		pic.setFitHeight(40);
		pic.setImage(feher);
		Point tempPoint = new Point(i, j);
		List<Point> tempPossible = game.getPossibleMoves();
		pic.setOnMouseClicked(e -> {
			try {
				if (game.getCurrentPlayer() == 0) {
					setToWhite(i, j);
					game.addWhite(tempPoint);
				} else {
					setToBlack(i, j);
					game.addBlack(tempPoint);
				}
				game.removePoint(tempPossible, tempPoint);
				for (int z = 0; z < tempPossible.size(); z++) {
					setToBase(tempPossible.get(z).getX(), tempPossible.get(z).getY());
				}
				game.clearPossibleMoves();
				game.setSwapPoints(tempPoint);
				for (Point t : game.getswapPoints()) {
					if (game.getCurrentPlayer() == 0) {
						setToWhite(t.getX(), t.getY());
					} else {
						setToBlack(t.getX(), t.getY());
					}
				}
				game.setCurrentPlayer(game.getCurrentPlayer() == 0 ? 1 : 0);
				game.setPossibleMoves();

				setScores();
				if (!game.isEnded()) {
					play();
				} else {
					if (game.getWinner() == 0) {
						player1.setText("Player 1 Win");
					}
					if (game.getWinner() == 1) {
						player2.setText("Player 2 Win");
					}
					if (game.getWinner() == -1) {
						player1.setText("Player 1 Draw");
						player2.setText("Player 2 Draw");
					}

				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		});
		gameGrid.add(pic, i, j);
	}

	public void setMainApp(ReversiApp app) {
		this.app = app;
	}

}
