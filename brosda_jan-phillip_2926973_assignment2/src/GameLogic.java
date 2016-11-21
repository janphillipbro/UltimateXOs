import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

// implemented GameLogic as a Singleton
public class GameLogic {

	private int[][] winners;
	private int current_player;
	private Boolean[][] activeBoards = new Boolean[3][3]; // active state of the
	// XOBoards
	private static GameLogic singleton = new GameLogic();

	/*
	 * A private Constructor prevents any other class from instantiating.
	 */

	private GameLogic() {
		winners = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				winners[i][j] = EMPTY;
				activeBoards[i][j] = ACTIVE;
			}
		}
	}

	public boolean detectWinner(int[][] board, int pieceX, int pieceY, int boardX, int boardY) {
		if (examineBoard(board, pieceX, pieceY, current_player)) {
			displayWinner(current_player, "Board");
			this.winners[boardX][boardY] = current_player;
			return true;
		}
		return false;
	}

	public boolean detectOverallWinner(int boardX, int boardY, int possibleWinner) {
		if (examineBoard(this.winners, boardX, boardY, possibleWinner)) {
			displayWinner(possibleWinner, "Game");
			return true;
		} else
			return false;
	}

	/* Static 'instance' method */
	public static GameLogic getInstance() {
		return singleton;
	}

	public boolean checkBoard(int boardX, int boardY) {
		return (activeBoards[boardX][boardY]);
	}

	public void setActiveStates(int boardX, int boardY) {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				activeBoards[i][j] = INACTIVE;
			}

		if (winners[boardX][boardY] == EMPTY) {
			activeBoards[boardX][boardY] = ACTIVE;
		} else {
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++) {
					if (winners[i][j] != EMPTY)
						activeBoards[i][j] = INACTIVE;
					else
						activeBoards[i][j] = ACTIVE;
				}
		}
	}

	// detects winner of a tile and updates
	public boolean detectBoardWinner(int[][] board, int x, int y, int currentPlayer) {
		if (examineBoard(board, x, y, currentPlayer)) {
			displayWinner(currentPlayer, "Tile");
			return true;
		} else
			return false;
	}

	public boolean detectOverallWinner(int[][] board, int x, int y, int currentPlayer) {
		if (examineBoard(board, x, y, currentPlayer)) {
			displayWinner(currentPlayer, "Game");
			return true;
		} else
			return false;
	}

	// source:
	// http://stackoverflow.com/questions/1056316/algorithm-for-determining-tic-tac-toe-game-over
	public boolean examineBoard(int[][] board, int x, int y, int currentPlayer) {

		// check col
		for (int i = 0; i < 3; i++) {
			if (board[x][i] != currentPlayer)
				break;
			if (i == 2) {
				return true;
			}
		}

		// check row
		for (int i = 0; i < 3; i++) {
			if (board[i][y] != currentPlayer)
				break;
			if (i == 2) {
				return true;
			}
		}

		// check diag
		if (x == y) {
			// we're on a diagonal
			for (int i = 0; i < 3; i++) {
				if (board[i][i] != currentPlayer)
					break;
				if (i == 2) {
					return true;
				}
			}
		}

		// check anti diag (thanks rampion)
		if (x + y == 2) {
			for (int i = 0; i < 3; i++) {
				if (board[i][2 - i] != currentPlayer)
					break;
				if (i == 2) {
					return true;
				}
			}
		}
		return false;
	}

	public void displayWinner(int winner, String winType) {
		String playername = "NO NAME";
		if (winner == XPIECE)
			playername = "X";
		if (winner == OPIECE)
			playername = "O";
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(winType + " Winner");
		alert.setHeaderText("You won the " + winType);
		alert.setContentText("Hello Player " + playername + "!\n " + "You won the " + winType);
		alert.showAndWait();
	}

	public int getCurrent_player() {
		return current_player;
	}

	public void setCurrent_player(int current_player) {
		this.current_player = current_player;
	}

	// constants for the class
	private final int EMPTY = 0;
	private final int XPIECE = 1;
	private final int OPIECE = 2;
	private final Boolean ACTIVE = true;
	private final Boolean INACTIVE = false;

	public void tryPlacePiece(XOBoard xoBoard, double x, double y, int indexx, int indexy) {
		xoBoard.placePiece(x, y, indexx, indexy);

	}

	public void reset() {

		winners = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				winners[i][j] = EMPTY;
				activeBoards[i][j] = ACTIVE;
			}
		}

	}

	public int[][] getWinners() {
		return winners;
	}
}
