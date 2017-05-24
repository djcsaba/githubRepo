package application.model;

import java.util.ArrayList;
import java.util.List;


public class Game {

	private int SIZE = 8;

	List<Point> whitePoints = new ArrayList<Point>();
	List<Point> blackPoints = new ArrayList<Point>();
	List<Point> possibleMoves = new ArrayList<Point>();
	List<Point> swapPoints = new ArrayList<Point>();
	List<Point> kozteLevok = new ArrayList<Point>();
	private int currentPlayer = 0;

	public Game(int currentPlayer, List<Point> white, List<Point> black) {
		super();
		clearPossibleMoves();
		clearSwapPoints();
		this.currentPlayer = currentPlayer;
		this.whitePoints = white;
		this.blackPoints = black;
		setPossibleMoves();
	}

	public void setPossibleMoves() {

		possibleMoves = new ArrayList<Point>();
		if (this.currentPlayer == 0) {
			for (Point point : whitePoints) {
				szabalyok(point);
			}
		} else {
			for (Point point : blackPoints) {
				szabalyok(point);
			}
		}
	}

	public List<Point> getPossibleMoves() {
		return this.possibleMoves;
	}

	private void szabalyok(Point point) {
		Point szabaly1 = balrolJobbraSzabaly(point, "ures");
		if (szabaly1 != null && !possibleMoves.contains(szabaly1)) {
			possibleMoves.add(szabaly1);
		}
		Point szabaly2 = jobbrolBalraSzabaly(point, "ures");
		if (szabaly2 != null && !possibleMoves.contains(szabaly2)) {
			possibleMoves.add(szabaly2);
		}
		Point szabaly3 = fenntrolLefeleSzabaly(point, "ures");
		if (szabaly3 != null && !possibleMoves.contains(szabaly3)) {
			possibleMoves.add(szabaly3);
		}
		Point szabaly4 = lenntrolFelfeleSzabaly(point, "ures");
		if (szabaly4 != null && !possibleMoves.contains(szabaly4)) {
			possibleMoves.add(szabaly4);
		}
		Point szabaly5 = atloBalrolJobbraSzabaly(point, "ures");
		if (szabaly5 != null && !possibleMoves.contains(szabaly5)) {
			possibleMoves.add(szabaly5);
		}
		Point szabaly6 = atloJobbrolBalraSzabaly(point, "ures");
		if (szabaly6 != null && !possibleMoves.contains(szabaly6)) {
			possibleMoves.add(szabaly6);
		}
		Point szabaly7 = inverzAtloBalrolJobbraSzabaly(point, "ures");
		if (szabaly7 != null && !possibleMoves.contains(szabaly7)) {
			possibleMoves.add(szabaly7);
		}
		Point szabaly8 = inverzAtloJobbrolBalraSzabaly(point, "ures");
		if (szabaly8 != null && !possibleMoves.contains(szabaly8)) {
			possibleMoves.add(szabaly8);
		}
	}

	private int megfeleloPontKeresese(String mitKeresunk, Point point, int i, Point temp) {

		boolean feherbeVan = whitePoints.contains(temp);
		boolean feketebeVan = blackPoints.contains(temp);
		if (mitKeresunk == "ures") {
			if (!feherbeVan && !feketebeVan) {
				return i;
			}
		} else if (mitKeresunk == "feher") {
			if (feherbeVan) {
				return i;
			}
		} else {
			if (feketebeVan) {
				return i;
			}
		}
		kozteLevok.add(temp);

		return -1;

	}

	public Point balrolJobbraSzabaly(Point point, String mitKeresunk) {

		if (point.getX() == SIZE - 1 || point.getX() == SIZE - 2) {
			return null;
		}
		int possible = -1;
		clearKoztelevok();
		for (int i = point.getX() + 1; i <= SIZE - 1; i++) {
			Point temp = new Point(i, point.getY());
			int aktualisErtek = megfeleloPontKeresese(mitKeresunk, point, i, temp);
			if (aktualisErtek >= 0) {
				possible = aktualisErtek;
				break;
			}
		}
		if (possible < 0) {
			return null;
		}
		boolean ellenfelSzinei = (currentPlayer == 1 && whitePoints.containsAll(kozteLevok))
				|| (currentPlayer == 0 && blackPoints.containsAll(kozteLevok));

		if (ellenfelSzinei && kozteLevok.size() > 0) {
			swapPoints.addAll(kozteLevok);
			return new Point(possible, point.getY());
		}
		return null;
	}

	public Point jobbrolBalraSzabaly(Point point, String mitKeresunk) {
		if (point.getX() == 0 || point.getX() == 1) {
			return null;
		}
		int possible = -1;
		clearKoztelevok();
		for (int i = point.getX() - 1; i >= 0; i--) {
			Point temp = new Point(i, point.getY());
			int aktualisErtek = megfeleloPontKeresese(mitKeresunk, point, i, temp);
			if (aktualisErtek >= 0) {
				possible = aktualisErtek;
				break;
			}
		}
		if (possible < 0) {
			return null;
		}
		boolean ellenfelSzinei = (currentPlayer == 1 && whitePoints.containsAll(kozteLevok))
				|| (currentPlayer == 0 && blackPoints.containsAll(kozteLevok));

		if (ellenfelSzinei && kozteLevok.size() > 0) {
			swapPoints.addAll(kozteLevok);
			return new Point(possible, point.getY());
		}
		return null;
	}

	public Point fenntrolLefeleSzabaly(Point point, String mitKeresunk) {
		if (point.getY() == SIZE - 1 || point.getY() == SIZE - 2) {
			return null;
		}
		int possible = -1;
		clearKoztelevok();

		for (int i = point.getY() + 1; i <= SIZE - 1; i++) {
			Point temp = new Point(point.getX(), i);
			int aktualisErtek = megfeleloPontKeresese(mitKeresunk, point, i, temp);
			if (aktualisErtek >= 0) {
				possible = aktualisErtek;
				break;
			}
		}
		if (possible < 0) {
			return null;
		}
		boolean ellenfelSzinei = (currentPlayer == 1 && whitePoints.containsAll(kozteLevok))
				|| (currentPlayer == 0 && blackPoints.containsAll(kozteLevok));

		if (ellenfelSzinei && kozteLevok.size() > 0) {
			swapPoints.addAll(kozteLevok);
			return new Point(point.getX(), possible);
		}
		return null;
	}

	public Point lenntrolFelfeleSzabaly(Point point, String mitKeresunk) {
		if (point.getY() == 0 || point.getY() == 1) {
			return null;
		}
		int possible = -1;
		clearKoztelevok();
		for (int i = point.getY() - 1; i >= 0; i--) {
			Point temp = new Point(point.getX(), i);
			int aktualisErtek = megfeleloPontKeresese(mitKeresunk, point, i, temp);
			if (aktualisErtek >= 0) {
				possible = aktualisErtek;
				break;
			}
		}
		if (possible < 0) {
			return null;
		}
		boolean ellenfelSzinei = (currentPlayer == 1 && whitePoints.containsAll(kozteLevok))
				|| (currentPlayer == 0 && blackPoints.containsAll(kozteLevok));

		if (ellenfelSzinei && kozteLevok.size() > 0) {
			swapPoints.addAll(kozteLevok);
			return new Point(point.getX(), possible);
		}
		return null;
	}

	public Point atloBalrolJobbraSzabaly(Point point, String mitKeresunk) {

		int atloIndex = point.getX() + point.getY();

		if (point.getX() == SIZE - 1 || point.getX() == SIZE - 2 || point.getY() == 0 || point.getY() == 1) {
			return null;
		}
		int possible = -1;
		clearKoztelevok();
		for (int i = point.getX() + 1; (i <= SIZE - 1 && atloIndex >= i); i++) {
			Point temp = new Point(i, atloIndex - i);
			int aktualisErtek = megfeleloPontKeresese(mitKeresunk, point, i, temp);
			if (aktualisErtek >= 0) {
				possible = aktualisErtek;
				break;
			}
		}
		if (possible < 0) {
			return null;
		}
		boolean ellenfelSzinei = (currentPlayer == 1 && whitePoints.containsAll(kozteLevok))
				|| (currentPlayer == 0 && blackPoints.containsAll(kozteLevok));

		if (ellenfelSzinei && kozteLevok.size() > 0) {
			swapPoints.addAll(kozteLevok);
			return new Point(possible, atloIndex - possible);
		}
		return null;
	}

	public Point atloJobbrolBalraSzabaly(Point point, String mitKeresunk) {

		int atloIndex = point.getX() + point.getY();

		if (point.getX() == 0 || point.getX() == 1 || point.getY() == SIZE - 1 || point.getY() == SIZE - 2) {
			return null;
		}
		int possible = -1;
		clearKoztelevok();
		for (int i = point.getX() - 1; (i >= 0 && atloIndex - i <= SIZE - 1); i--) {
			Point temp = new Point(i, atloIndex - i);
			int aktualisErtek = megfeleloPontKeresese(mitKeresunk, point, i, temp);
			if (aktualisErtek >= 0) {
				possible = aktualisErtek;
				break;
			}
		}
		if (possible < 0) {
			return null;
		}
		boolean ellenfelSzinei = (currentPlayer == 1 && whitePoints.containsAll(kozteLevok))
				|| (currentPlayer == 0 && blackPoints.containsAll(kozteLevok));

		if (ellenfelSzinei && kozteLevok.size() > 0) {
			swapPoints.addAll(kozteLevok);
			return new Point(possible, atloIndex - possible);
		}
		return null;
	}

	public Point inverzAtloBalrolJobbraSzabaly(Point point, String mitKeresunk) {

		int atloIndex = point.getX() - point.getY();

		if (point.getX() == SIZE - 1 || point.getX() == SIZE - 2 || point.getY() == SIZE - 1
				|| point.getY() == SIZE - 2) {
			return null;
		}
		int possible = -1;
		clearKoztelevok();
		for (int i = point.getX() + 1; (i <= SIZE - 1 && i - atloIndex <= SIZE - 1); i++) {
			Point temp = new Point(i, i - atloIndex);
			int aktualisErtek = megfeleloPontKeresese(mitKeresunk, point, i, temp);
			if (aktualisErtek >= 0) {
				possible = aktualisErtek;
				break;
			}
		}
		if (possible < 0) {
			return null;
		}
		boolean ellenfelSzinei = (currentPlayer == 1 && whitePoints.containsAll(kozteLevok))
				|| (currentPlayer == 0 && blackPoints.containsAll(kozteLevok));

		if (ellenfelSzinei && kozteLevok.size() > 0) {
			swapPoints.addAll(kozteLevok);
			return new Point(possible, possible - atloIndex);
		}
		return null;
	}

	public Point inverzAtloJobbrolBalraSzabaly(Point point, String mitKeresunk) {

		int atloIndex = point.getX() - point.getY();

		if (point.getX() == 0 || point.getX() == 1 || point.getY() == 0 || point.getY() == 1) {
			return null;
		}
		int possible = -1;
		clearKoztelevok();
		for (int i = point.getX() - 1; (i >= 0 && i >= atloIndex); i--) {
			Point temp = new Point(i, i - atloIndex);
			int aktualisErtek = megfeleloPontKeresese(mitKeresunk, point, i, temp);
			if (aktualisErtek >= 0) {
				possible = aktualisErtek;
				break;
			}
		}
		if (possible < 0) {
			return null;
		}
		boolean ellenfelSzinei = (currentPlayer == 1 && whitePoints.containsAll(kozteLevok))
				|| (currentPlayer == 0 && blackPoints.containsAll(kozteLevok));

		if (ellenfelSzinei && kozteLevok.size() > 0) {
			swapPoints.addAll(kozteLevok);
			return new Point(possible, possible - atloIndex);
		}
		return null;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public List<Point> getWhite() {
		return whitePoints;
	}

	public void setWhite(List<Point> white) {
		this.whitePoints = white;
	}

	public List<Point> getBlack() {
		return blackPoints;
	}

	public void setBlack(List<Point> black) {
		this.blackPoints = black;
	}

	public void addWhite(Point pont) {
		this.whitePoints.add(pont);
	}

	public void addBlack(Point pont) {
		this.blackPoints.add(pont);
	}

	public void removeWhite(Point pont) {
		if (this.whitePoints.contains(pont)) {
			this.whitePoints.remove(pont);
		}
		;
	}

	public void removeBlack(Point pont) {
		if (this.blackPoints.contains(pont)) {
			this.blackPoints.remove(pont);
		}
		;
	}

	public List<Point> removePoint(List<Point> possibleMoves, Point p) {

		if (possibleMoves.contains(p)) {
			possibleMoves.remove(p);
			return possibleMoves;
		}
		return possibleMoves;
	}

	public boolean isEnded() {
		if (whitePoints.isEmpty() || blackPoints.isEmpty() || possibleMoves.isEmpty()
				|| (whitePoints.size() + blackPoints.size() == SIZE * SIZE)) {
			return true;
		}
		return false;
	}

	public int getWinner() {
		if (whitePoints.size() > blackPoints.size()) {
			return 0;
		}
		if (whitePoints.size() < blackPoints.size()) {
			return 1;
		}
		return -1;
	}

	public void clearPossibleMoves() {
		this.possibleMoves = new ArrayList<Point>();

	}

	public void clearSwapPoints() {
		this.swapPoints = new ArrayList<Point>();
	}

	public void clearKoztelevok() {
		this.kozteLevok = new ArrayList<Point>();
	}

	public List<Point> getswapPoints() {
		return this.swapPoints;

	}

	public void setSwapPoints(Point tempPoint) {

		clearSwapPoints();

		balrolJobbraSzabaly(tempPoint, currentPlayer == 0 ? "feher" : "fekete");
		jobbrolBalraSzabaly(tempPoint, currentPlayer == 0 ? "feher" : "fekete");
		fenntrolLefeleSzabaly(tempPoint, currentPlayer == 0 ? "feher" : "fekete");
		lenntrolFelfeleSzabaly(tempPoint, currentPlayer == 0 ? "feher" : "fekete");
		atloBalrolJobbraSzabaly(tempPoint, currentPlayer == 0 ? "feher" : "fekete");
		atloJobbrolBalraSzabaly(tempPoint, currentPlayer == 0 ? "feher" : "fekete");
		inverzAtloBalrolJobbraSzabaly(tempPoint, currentPlayer == 0 ? "feher" : "fekete");
		inverzAtloJobbrolBalraSzabaly(tempPoint, currentPlayer == 0 ? "feher" : "fekete");

		if (currentPlayer == 0) {
			whitePoints.addAll(swapPoints);
			blackPoints.removeAll(swapPoints);
		} else {
			blackPoints.addAll(swapPoints);
			whitePoints.removeAll(swapPoints);
		}

	}

}
