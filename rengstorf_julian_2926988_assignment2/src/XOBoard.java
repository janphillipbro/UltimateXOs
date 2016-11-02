

// an implementation of the XO board and the game logic
// imports necessary for this class
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;

// class definition for drawing a game board
class XOBoard extends Pane {
	// constructor for the class
	public XOBoard(XOUltimateBoard ref) {
		// initialise the boards
		board = new int[3][3];
		renders = new XOPiece[3][3];
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				board[i][j] = EMPTY;
				renders[i][j] = null;
			}
		ref.setCurrent_player(XPIECE);
		
		// initialise the rectangle and lines
		back = new Rectangle();
		back.setFill(Color.BLACK);
		h1 = new Line(); h2 = new Line();
		v1 = new Line(); v2 = new Line();
		h1.setStroke(Color.WHITE); h2.setStroke(Color.WHITE);
		v1.setStroke(Color.WHITE); v2.setStroke(Color.WHITE);

		// the horizontal lines only need the endx value modified the rest of
		// the values can be zero
		h1.setStartX(0);
		h1.setStartY(0);
		h1.setEndY(0);
		h2.setStartX(0);
		h2.setStartY(0);
		h2.setEndY(0);
		// the vertical lines only need the endy value modified the rest of the
		// values can be zero
		v1.setStartX(0);
		v1.setStartY(0);
		v1.setEndX(0);
		v2.setStartX(0);
		v2.setStartY(0);
		v2.setEndX(0);

		// setup the translation of one cell height and two cell heights
		ch_one = new Translate(0, 0);
		ch_two = new Translate(0, 0);
		h1.getTransforms().add(ch_one);
		h2.getTransforms().add(ch_two);
		// setup the translation of one cell width and two cell widths
		cw_one = new Translate(0, 0);
		cw_two = new Translate(0, 0);
		v1.getTransforms().add(cw_one);
		v2.getTransforms().add(cw_two);

		// add the rectangles and lines to this group
		getChildren().addAll(back, h1, h2, v1, v2);
	}

	// we have to override resizing behaviour to make our view appear properly
	@Override
	public void resize(double width, double height) {
		// call the superclass method first
		super.resize(width, height);
		// figure out the width and height of a cell
		cell_width = width / 9.0;
		cell_height = height / 9.0;

		// resize the rectangle to take the full size of the widget
		back.setWidth(width);
		back.setHeight(height);
		// set a new y on the horizontal lines and translate them into place
		ch_one.setY(cell_height);
		ch_two.setY(2 * cell_height);
		h1.setEndX(width);
		h2.setEndX(width);
		// set a new x on the vertical lines and translate them into place
		cw_one.setX(cell_width);
		cw_two.setX(2 * cell_width);
		v1.setEndY(height);
		v2.setEndY(height);
		// we need to reset the sizes and positions of all XOPieces that were placed
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] != 0) {
					renders[i][j].relocate(i * cell_width, j * cell_height);
					renders[i][j].resize(cell_width, cell_height);
				}
			}
		}
	}

	// public method for resetting the game
	public void resetGame() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = 0;
				getChildren().remove(renders[i][j]);
				renders[i][j] = null;
			}
		}
	}

	// public method that tries to place a piece
	public void placePiece(final double x, final double y) {
		// translate the x, y coordinates into cell indexes
		int indexx = (int) (x / cell_width);
		int indexy = (int) (y / cell_height);
		// if the position is empty then place a piece and swap the players
		if (board[indexx][indexy] == EMPTY && ref.getCurrent_player() == XPIECE) {
			board[indexx][indexy] = XPIECE;
			renders[indexx][indexy] = new XOPiece(XPIECE);
			renders[indexx][indexy].resize(cell_width, cell_height);
			renders[indexx][indexy].relocate(indexx * cell_width, indexy * cell_height);
			getChildren().add(renders[indexx][indexy]);
			ref.setCurrent_player(OPIECE);
		} else if (board[indexx][indexy] == EMPTY && ref.getCurrent_player() == OPIECE) {
			board[indexx][indexy] = OPIECE;
			renders[indexx][indexy] = new XOPiece(OPIECE);
			renders[indexx][indexy].resize(cell_width, cell_height);
			renders[indexx][indexy].relocate(indexx * cell_width, indexy * cell_height);
			getChildren().add(renders[indexx][indexy]);
			ref.setCurrent_player(XPIECE);
		}
	}

	// private fields of the class
	private int[][] board; // array that holds all pieces
	private XOPiece[][] renders; // array that holds all the render pieces
	private Rectangle back; // background of the board
	private Line h1, h2, v1, v2; // horizontal and vertical grid lines
	private double cell_width, cell_height; // width and height of a cell
	// translation of {one, two} cell {width, height}
	private Translate ch_one, ch_two, cw_one, cw_two;
	// constants for the class
	private final int EMPTY = 0;
	private final int XPIECE = 1;
	private final int OPIECE = 2;
	
	private XOUltimateBoard ref;
}