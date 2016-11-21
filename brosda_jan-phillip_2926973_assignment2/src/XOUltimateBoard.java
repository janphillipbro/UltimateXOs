
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
				renders[i][j] = new XOBoard(this); // render the XO Boards
				getChildren().add(renders[i][j]);
			}
	}

	// we have to override resizing behaviour to make our view appear properly
	@Override
	public void resize(double width, double height) {
		// call the superclass method first
		super.resize(width, height);
		// figure out the width and height of a cell
		cell_width = width / 3.0;
		cell_height = height / 3.0;

		// we need to reset the sizes and positions of all XOPieces that were
		// placed
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
				boardWinners[i][j] = EMPTY;
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
		// save current player, cause it will be switched in placePiece
		int possibleWinner = gameLogic.getCurrent_player();
		// translate height and width values and pass to
		// place piece in the correct XOBoard in the right place
		if (boardWinners[indexx][indexy] == EMPTY) {
			// gameLogic.tryPlacePiece(renders[indexx][indexy],x,y,indexx,indexy);
			if (gameLogic.checkBoard(indexx, indexy)) {
				renders[indexx][indexy].placePiece(x, y, indexx, indexy);
			}
			if (gameLogic.detectOverallWinner(indexx, indexy, possibleWinner)) {
				resetGame();
			}
		}
		if (GameLogic.getInstance().detectOverallWinner(boardWinners, indexx, indexy, possibleWinner)) {
			resetGame();
		}
	}

	// private fields of the class

	private int[][] boardWinners; // array that stores the winner of the game
	private XOBoard[][] renders; // array that holds all the render pieces
	private double cell_width, cell_height; // width and height of a cell
	private GameLogic gameLogic = GameLogic.getInstance();
	// constants for the class
	private final int EMPTY = 0;


	public int[][] getBoardWinners() {
		return boardWinners;
	}

	public void updateBoardWinners(int x, int y) {
		this.boardWinners[x][y] = gameLogic.getCurrent_player();
	}
}