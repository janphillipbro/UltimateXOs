
// an implementation of the XO board and the game logic
// imports necessary for this class
import javafx.scene.layout.Pane;

// class definition for drawing a game board
class XOUltimateBoard extends Pane {
	// constructor for the class
	public XOUltimateBoard() {
		// initialise the boards
		boardWinners = new int[3][3];
		renders = new XOBoard[3][3];
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				boardWinners[i][j] = EMPTY; // initialize the winners array
				renders[i][j] = new XOBoard(XOUltimateBoard.this); // render the
																	// XO Boards
				getChildren().add(renders[i][j]);
			}
		current_player = XPIECE;
	}

	public int getCurrent_player() {
		return current_player;
	}

	public void setCurrent_player(int current_player) {
		this.current_player = current_player;
	}

	// we have to override resizing behaviour to make our view appear properly
	@Override
	public void resize(double width, double height) {
		// call the superclass method first
		super.resize(width, height);
		// figure out the width and height of a cell
		cell_width = width / 3.0;
		cell_height = height / 3.0;

		// we need to reset the sizes and positions of all XOPieces that were placed
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				renders[i][j].relocate(i * cell_width, j * cell_height);
				renders[i][j].resize(cell_width, cell_height);
			}
		}
	}

	// public method for resetting the game
	public void resetGame() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				boardWinners[i][j] = 0;
				this.getChildren().remove(renders[i][j]);
				renders[i][j] = new XOBoard(XOUltimateBoard.this);
				getChildren().add(renders[i][j]);
			}
		}
	}

	// public method that tries to place a piece
	public void placePiece(final double x, final double y) {
		// translate the x, y coordinates into cell indexes
		int indexx = (int) (x / cell_width);
		int indexy = (int) (y / cell_height);
		// translate height and width values and pass to
		// place piece in the correct XOBoard in the right place
		renders[indexx][indexy].placePiece(x, y, indexx * cell_width, indexy * cell_height);
	}

	// private fields of the class
	private int[][] boardWinners; // array that stores the winner of the game
	private XOBoard[][] renders; // array that holds all the render pieces
	private double cell_width, cell_height; // width and height of a cell
	private int current_player; // who is the current player
	// constants for the class
	private final int EMPTY = 0;
	private final int XPIECE = 1;
	private final int OPIECE = 2;
}