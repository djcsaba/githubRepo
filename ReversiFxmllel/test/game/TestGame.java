package game;

import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import application.model.Game;
import application.model.Point;

public class TestGame {

	static Game game = null;

	@BeforeClass
	public static void setUpBeforeClass() {

		List<Point> white = new ArrayList<Point>();
		List<Point> black = new ArrayList<Point>();
		white.add(new Point(3, 3));
		white.add(new Point(4, 4));
		white.add(new Point(4, 2));
		white.add(new Point(4, 0));
		white.add(new Point(5, 3));
		white.add(new Point(2, 6));
		white.add(new Point(5, 0));
		white.add(new Point(4, 6));
		white.add(new Point(0, 4));
		white.add(new Point(6, 5));

		black.add(new Point(3, 4));
		black.add(new Point(4, 1));
		black.add(new Point(4, 3));
		black.add(new Point(2, 3));
		black.add(new Point(5, 4));
		black.add(new Point(5, 2));
		black.add(new Point(3, 5));
		black.add(new Point(1, 7));
		black.add(new Point(1, 5));
		black.add(new Point(3, 7));

		game = new Game(0, white, black);

	};

	@Before
	public void InitTest() {
		game.clearSwapPoints();
	}

	@Test
	public void testBalrolJobbraSzabaly() {
		game.setCurrentPlayer(0);
		Point p = game.balrolJobbraSzabaly(new Point(4, 4), "ures");
		assertEquals(6, p.getX());
		assertEquals(4, p.getY());
		game.setCurrentPlayer(1);
		p = game.balrolJobbraSzabaly(new Point(4, 4), "ures");
		assertEquals(null, p);
	}

	@Test
	public void testBalrolJobbraSzabalyWhite() {
		game.setCurrentPlayer(0);
		Point p = new Point(3, 3);
		game.balrolJobbraSzabaly(p, "feher");
		assertEquals(1, game.getswapPoints().size());
		assertEquals(4, game.getswapPoints().get(0).getX());
		assertEquals(3, game.getswapPoints().get(0).getY());
	}

	@Test
	public void testBalrolJobbraSzabalyBlack() {
		game.setCurrentPlayer(1);
		Point p = new Point(2, 3);
		game.balrolJobbraSzabaly(p, "fekete");
		assertEquals(1, game.getswapPoints().size());
		assertEquals(3, game.getswapPoints().get(0).getX());
		assertEquals(3, game.getswapPoints().get(0).getY());
	}

	@Test
	public void testBalrolJobbraSzabalySzelsosegesEsetek() {
		game.clearPossibleMoves();
		for (int i = 0; i < 8; i++) {
			Point p = new Point(7, i);

			Point result = game.balrolJobbraSzabaly(p, "ures");
			assertEquals(0, game.getswapPoints().size());
			assertEquals(0, game.getPossibleMoves().size());
			assertEquals(null, result);

			result = game.balrolJobbraSzabaly(p, "feher");
			assertEquals(0, game.getswapPoints().size());
			assertEquals(0, game.getPossibleMoves().size());
			assertEquals(null, result);

			result = game.balrolJobbraSzabaly(p, "fekete");
			assertEquals(0, game.getswapPoints().size());
			assertEquals(0, game.getPossibleMoves().size());
			assertEquals(null, result);
		}
	}

	@Test
	public void testJobbrolBalraSzabaly() {
		game.setCurrentPlayer(1);
		Point p = game.jobbrolBalraSzabaly(new Point(5, 2), "ures");
		assertEquals(3, p.getX());
		assertEquals(2, p.getY());
	}

	@Test
	public void testJobbrolBalraSzabalyWhite() {
		game.setCurrentPlayer(0);
		Point p = new Point(5, 3);
		game.jobbrolBalraSzabaly(p, "feher");
		assertEquals(1, game.getswapPoints().size());
		assertEquals(4, game.getswapPoints().get(0).getX());
		assertEquals(3, game.getswapPoints().get(0).getY());
	}

	@Test
	public void testJobbrolBalraSzabalyBlack() {
		game.setCurrentPlayer(1);
		Point p = new Point(5, 4);
		game.jobbrolBalraSzabaly(p, "fekete");
		assertEquals(1, game.getswapPoints().size());
		assertEquals(4, game.getswapPoints().get(0).getX());
		assertEquals(4, game.getswapPoints().get(0).getY());
	}

	@Test
	public void testJobbrolBalraSzabalySzelsosegesEsetek() {
		game.clearPossibleMoves();
		for (int i = 0; i < 8; i++) {
			Point p = new Point(0, i);

			Point result = game.jobbrolBalraSzabaly(p, "ures");
			assertEquals(0, game.getswapPoints().size());
			assertEquals(0, game.getPossibleMoves().size());
			assertEquals(null, result);

			result = game.jobbrolBalraSzabaly(p, "feher");
			assertEquals(0, game.getswapPoints().size());
			assertEquals(0, game.getPossibleMoves().size());
			assertEquals(null, result);

			result = game.jobbrolBalraSzabaly(p, "fekete");
			assertEquals(0, game.getswapPoints().size());
			assertEquals(0, game.getPossibleMoves().size());
			assertEquals(null, result);
		}
	}

	@Test
	public void testfentrolLefeleSzabaly() {
		Point p = game.fenntrolLefeleSzabaly(new Point(3, 3), "ures");
		assertEquals(3, p.getX());
		assertEquals(6, p.getY());
	}

	@Test
	public void testfentrolLefeleSzabalyWhite() {
		game.setCurrentPlayer(0);
		Point p = new Point(4, 2);
		game.fenntrolLefeleSzabaly(p, "feher");
		assertEquals(1, game.getswapPoints().size());
		assertEquals(4, game.getswapPoints().get(0).getX());
		assertEquals(3, game.getswapPoints().get(0).getY());
	}

	@Test
	public void testfentrolLefeleSzabalyBlack() {
		game.setCurrentPlayer(1);
		Point p = new Point(4, 1);
		game.fenntrolLefeleSzabaly(p, "fekete");
		assertEquals(1, game.getswapPoints().size());
		assertEquals(4, game.getswapPoints().get(0).getX());
		assertEquals(2, game.getswapPoints().get(0).getY());
	}

	@Test
	public void testfentrolLefeleSzabalySzelsosegesEsetek() {
		game.clearPossibleMoves();
		for (int i = 0; i < 8; i++) {
			Point p = new Point(i, 7);

			Point result = game.fenntrolLefeleSzabaly(p, "ures");
			assertEquals(0, game.getswapPoints().size());
			assertEquals(0, game.getPossibleMoves().size());
			assertEquals(null, result);

			result = game.fenntrolLefeleSzabaly(p, "feher");
			assertEquals(0, game.getswapPoints().size());
			assertEquals(0, game.getPossibleMoves().size());
			assertEquals(null, result);

			result = game.fenntrolLefeleSzabaly(p, "fekete");
			assertEquals(0, game.getswapPoints().size());
			assertEquals(0, game.getPossibleMoves().size());
			assertEquals(null, result);
		}
	}

	@Test
	public void testlentrolFelfeleSzabaly() {
		game.setCurrentPlayer(0);
		Point p = game.lenntrolFelfeleSzabaly(new Point(5, 3), "ures");
		assertEquals(5, p.getX());
		assertEquals(1, p.getY());
	}

	@Test
	public void testlentrolFelfeleSzabalyWhite() {
		game.setCurrentPlayer(0);
		Point p = new Point(4, 4);
		game.lenntrolFelfeleSzabaly(p, "feher");
		assertEquals(1, game.getswapPoints().size());
		assertEquals(4, game.getswapPoints().get(0).getX());
		assertEquals(3, game.getswapPoints().get(0).getY());
	}

	@Test
	public void testlentrolFelfeleSzabalyBlack() {
		game.setCurrentPlayer(1);
		Point p = new Point(4, 3);
		game.lenntrolFelfeleSzabaly(p, "fekete");
		assertEquals(1, game.getswapPoints().size());
		assertEquals(4, game.getswapPoints().get(0).getX());
		assertEquals(2, game.getswapPoints().get(0).getY());
	}

	@Test
	public void testlentrolFelfeleSzabalySzelsosegesEsetek() {
		game.clearPossibleMoves();
		for (int i = 0; i < 8; i++) {
			Point p = new Point(i, 0);

			Point result = game.lenntrolFelfeleSzabaly(p, "ures");
			assertEquals(0, game.getswapPoints().size());
			assertEquals(0, game.getPossibleMoves().size());
			assertEquals(null, result);

			result = game.lenntrolFelfeleSzabaly(p, "feher");
			assertEquals(0, game.getswapPoints().size());
			assertEquals(0, game.getPossibleMoves().size());
			assertEquals(null, result);

			result = game.lenntrolFelfeleSzabaly(p, "fekete");
			assertEquals(0, game.getswapPoints().size());
			assertEquals(0, game.getPossibleMoves().size());
			assertEquals(null, result);
		}
	}

	@Test
	public void testatloBalrolJobbraSzabaly() {
		game.setCurrentPlayer(1);
		Point p = game.atloBalrolJobbraSzabaly(new Point(3, 5), "ures");
		assertEquals(6, p.getX());
		assertEquals(2, p.getY());
	}

	@Test
	public void testatloBalrolJobbraSzabalyWhite() {
		game.setCurrentPlayer(0);
		Point p = new Point(2, 6);
		game.atloBalrolJobbraSzabaly(p, "feher");
		assertEquals(1, game.getswapPoints().size());
		assertEquals(3, game.getswapPoints().get(0).getX());
		assertEquals(5, game.getswapPoints().get(0).getY());
	}

	@Test
	public void testatloBalrolJobbraSzabalyBlack() {
		game.setCurrentPlayer(1);
		Point p = new Point(1, 7);
		game.atloBalrolJobbraSzabaly(p, "fekete");
		assertEquals(1, game.getswapPoints().size());
		assertEquals(2, game.getswapPoints().get(0).getX());
		assertEquals(6, game.getswapPoints().get(0).getY());
	}

	@Test
	public void testatloBalrolJobbraSzabalySzelsosegesEsetek() {
		game.clearPossibleMoves();
		for (int i = 0; i < 8; i++) {
			Point p = new Point(i, 0);

			Point result = game.atloBalrolJobbraSzabaly(p, "ures");
			assertEquals(0, game.getswapPoints().size());
			assertEquals(0, game.getPossibleMoves().size());
			assertEquals(null, result);

			result = game.atloBalrolJobbraSzabaly(p, "feher");
			assertEquals(0, game.getswapPoints().size());
			assertEquals(0, game.getPossibleMoves().size());
			assertEquals(null, result);

			result = game.atloBalrolJobbraSzabaly(p, "fekete");
			assertEquals(0, game.getswapPoints().size());
			assertEquals(0, game.getPossibleMoves().size());
			assertEquals(null, result);
		}
		for (int i = 0; i < 8; i++) {
			Point p = new Point(7, i);

			Point result = game.atloBalrolJobbraSzabaly(p, "ures");
			assertEquals(0, game.getswapPoints().size());
			assertEquals(0, game.getPossibleMoves().size());
			assertEquals(null, result);

			result = game.atloBalrolJobbraSzabaly(p, "feher");
			assertEquals(0, game.getswapPoints().size());
			assertEquals(0, game.getPossibleMoves().size());
			assertEquals(null, result);

			result = game.atloBalrolJobbraSzabaly(p, "fekete");
			assertEquals(0, game.getswapPoints().size());
			assertEquals(0, game.getPossibleMoves().size());
			assertEquals(null, result);
		}
	}

	@Test
	public void testatloJobbrolBalraSzabaly() {
		Point p = game.atloJobbrolBalraSzabaly(new Point(5, 0), "ures");
		assertEquals(3, p.getX());
		assertEquals(2, p.getY());
	}

	@Test
	public void testatloJobbrolBalraSzabalyWhite() {
		game.setCurrentPlayer(0);
		Point p = new Point(4, 4);
		game.atloJobbrolBalraSzabaly(p, "feher");
		assertEquals(1, game.getswapPoints().size());
		assertEquals(3, game.getswapPoints().get(0).getX());
		assertEquals(5, game.getswapPoints().get(0).getY());
	}

	@Test
	public void testatloJobbrolBalraSzabalyBlack() {
		game.setCurrentPlayer(1);
		Point p = new Point(3, 5);
		game.atloJobbrolBalraSzabaly(p, "fekete");
		assertEquals(1, game.getswapPoints().size());
		assertEquals(2, game.getswapPoints().get(0).getX());
		assertEquals(6, game.getswapPoints().get(0).getY());
	}

	@Test
	public void testatloJobbrolBalraSzabalySzelsosegesEsetek() {
		game.clearPossibleMoves();
		for (int i = 0; i < 8; i++) {
			Point p = new Point(i, 7);

			Point result = game.atloJobbrolBalraSzabaly(p, "ures");
			assertEquals(0, game.getswapPoints().size());
			assertEquals(0, game.getPossibleMoves().size());
			assertEquals(null, result);

			result = game.atloJobbrolBalraSzabaly(p, "feher");
			assertEquals(0, game.getswapPoints().size());
			assertEquals(0, game.getPossibleMoves().size());
			assertEquals(null, result);

			result = game.atloJobbrolBalraSzabaly(p, "fekete");
			assertEquals(0, game.getswapPoints().size());
			assertEquals(0, game.getPossibleMoves().size());
			assertEquals(null, result);
		}
		for (int i = 0; i < 8; i++) {
			Point p = new Point(0, i);

			Point result = game.atloJobbrolBalraSzabaly(p, "ures");
			assertEquals(0, game.getswapPoints().size());
			assertEquals(0, game.getPossibleMoves().size());
			assertEquals(null, result);

			result = game.atloJobbrolBalraSzabaly(p, "feher");
			assertEquals(0, game.getswapPoints().size());
			assertEquals(0, game.getPossibleMoves().size());
			assertEquals(null, result);

			result = game.atloJobbrolBalraSzabaly(p, "fekete");
			assertEquals(0, game.getswapPoints().size());
			assertEquals(0, game.getPossibleMoves().size());
			assertEquals(null, result);
		}
	}

	@Test
	public void testinverzatloBalrolJobbraSzabaly() {
		game.setCurrentPlayer(1);
		Point p = game.inverzAtloBalrolJobbraSzabaly(new Point(3, 5), "ures");
		assertEquals(5, p.getX());
		assertEquals(7, p.getY());
	}

	@Test
	public void testinverzatloBalrolJobbraSzabalyWhite() {
		game.setCurrentPlayer(0);
		Point p = new Point(0, 4);
		game.inverzAtloBalrolJobbraSzabaly(p, "feher");
		assertEquals(1, game.getswapPoints().size());
		assertEquals(1, game.getswapPoints().get(0).getX());
		assertEquals(5, game.getswapPoints().get(0).getY());
	}

	@Test
	public void testinverzatloBalrolJobbraSzabalyBlack() {
		game.setCurrentPlayer(1);
		Point p = new Point(1, 5);
		game.inverzAtloBalrolJobbraSzabaly(p, "fekete");
		assertEquals(1, game.getswapPoints().size());
		assertEquals(2, game.getswapPoints().get(0).getX());
		assertEquals(6, game.getswapPoints().get(0).getY());
	}

	@Test
	public void testinverzatloBalrolJobbraSzabalySzelsosegesEsetek() {
		game.clearPossibleMoves();
		for (int i = 0; i < 8; i++) {
			Point p = new Point(i, 7);

			Point result = game.inverzAtloBalrolJobbraSzabaly(p, "ures");
			assertEquals(0, game.getswapPoints().size());
			assertEquals(0, game.getPossibleMoves().size());
			assertEquals(null, result);

			result = game.inverzAtloBalrolJobbraSzabaly(p, "feher");
			assertEquals(0, game.getswapPoints().size());
			assertEquals(0, game.getPossibleMoves().size());
			assertEquals(null, result);

			result = game.inverzAtloBalrolJobbraSzabaly(p, "fekete");
			assertEquals(0, game.getswapPoints().size());
			assertEquals(0, game.getPossibleMoves().size());
			assertEquals(null, result);
		}
		for (int i = 0; i < 8; i++) {
			Point p = new Point(7, i);

			Point result = game.inverzAtloBalrolJobbraSzabaly(p, "ures");
			assertEquals(0, game.getswapPoints().size());
			assertEquals(0, game.getPossibleMoves().size());
			assertEquals(null, result);

			result = game.inverzAtloBalrolJobbraSzabaly(p, "feher");
			assertEquals(0, game.getswapPoints().size());
			assertEquals(0, game.getPossibleMoves().size());
			assertEquals(null, result);

			result = game.inverzAtloBalrolJobbraSzabaly(p, "fekete");
			assertEquals(0, game.getswapPoints().size());
			assertEquals(0, game.getPossibleMoves().size());
			assertEquals(null, result);
		}
	}

	@Test
	public void testinverzatloJobbrolSzabaly() {
		Point p = game.inverzAtloJobbrolBalraSzabaly(new Point(4, 6), "ures");
		assertEquals(2, p.getX());
		assertEquals(4, p.getY());

		p = game.inverzAtloJobbrolBalraSzabaly(new Point(6, 5), "ures");
		assertEquals(3, p.getX());
		assertEquals(2, p.getY());
	}

	@Test
	public void testinverzatloJobbrolSzabalyWhite() {
		game.setCurrentPlayer(0);
		Point p = new Point(2, 6);
		game.inverzAtloJobbrolBalraSzabaly(p, "feher");
		assertEquals(1, game.getswapPoints().size());
		assertEquals(1, game.getswapPoints().get(0).getX());
		assertEquals(5, game.getswapPoints().get(0).getY());
	}

	@Test
	public void testinverzatloJobbrolSzabalyBlack() {
		game.setCurrentPlayer(1);
		Point p = new Point(3, 7);
		game.inverzAtloJobbrolBalraSzabaly(p, "fekete");
		assertEquals(1, game.getswapPoints().size());
		assertEquals(2, game.getswapPoints().get(0).getX());
		assertEquals(6, game.getswapPoints().get(0).getY());
	}

	@Test
	public void testinverzatloJobbrolSzabalySzelsosegesEsetek() {
		game.clearPossibleMoves();
		for (int i = 0; i < 8; i++) {
			Point p = new Point(i, 0);

			Point result = game.inverzAtloJobbrolBalraSzabaly(p, "ures");
			assertEquals(0, game.getswapPoints().size());
			assertEquals(0, game.getPossibleMoves().size());
			assertEquals(null, result);

			result = game.inverzAtloJobbrolBalraSzabaly(p, "feher");
			assertEquals(0, game.getswapPoints().size());
			assertEquals(0, game.getPossibleMoves().size());
			assertEquals(null, result);

			result = game.inverzAtloJobbrolBalraSzabaly(p, "fekete");
			assertEquals(0, game.getswapPoints().size());
			assertEquals(0, game.getPossibleMoves().size());
			assertEquals(null, result);
		}
		for (int i = 0; i < 8; i++) {
			Point p = new Point(0, i);

			Point result = game.inverzAtloJobbrolBalraSzabaly(p, "ures");
			assertEquals(0, game.getswapPoints().size());
			assertEquals(0, game.getPossibleMoves().size());
			assertEquals(null, result);

			result = game.inverzAtloJobbrolBalraSzabaly(p, "feher");
			assertEquals(0, game.getswapPoints().size());
			assertEquals(0, game.getPossibleMoves().size());
			assertEquals(null, result);

			result = game.inverzAtloJobbrolBalraSzabaly(p, "fekete");
			assertEquals(0, game.getswapPoints().size());
			assertEquals(0, game.getPossibleMoves().size());
			assertEquals(null, result);
		}
	}
}
