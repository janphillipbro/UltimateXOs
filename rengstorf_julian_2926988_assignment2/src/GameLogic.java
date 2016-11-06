import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


// implemented GameLogic as a Singleton
public class GameLogic {

	private static GameLogic singleton = new GameLogic();
	private int[][] winners;

	/*
	 * A private Constructor prevents any other class from instantiating.
	 */
	private GameLogic() {
		winners = new int[3][3];
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				winners[i][j] = EMPTY;
			}
	}

	/* Static 'instance' method */
	public static GameLogic getInstance() {
		return singleton;
	}

	// detects winner of a tile and updates
	public boolean detectWinner(int[][] board, int x, int y, int currentPlayer) {
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

	// source: http://stackoverflow.com/questions/1056316/algorithm-for-determining-tic-tac-toe-game-over
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
			playername = "Red (X)";
		if (winner == OPIECE)
			playername = "Green (O)";
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(winType + " Winner");
		alert.setHeaderText("You won the " + winType);
		alert.setContentText(
				"Hello Player " + playername + "!\n " + "I have good news for you! You won the " + winType);

		alert.showAndWait();
	}

	// constants for the class
	private final int EMPTY = 0;
	private final int XPIECE = 1;
	private final int OPIECE = 2;
}
