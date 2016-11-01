

import javafx.scene.control.Control;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

//class definition for a custom control
class CustomControl extends Control {
	// constructor for the class
	public CustomControl() {
		// set a default skin and generate a game board
		setSkin(new CustomControlSkin(this));
		 xoboard = new XOBoard();
		 getChildren().add(xoboard);

		// add a mouse clicked listener that will try to place a piece
		setOnMouseClicked((MouseEvent event) -> {
			xoboard.placePiece(event.getX(), event.getY());
		});

		setOnKeyPressed((KeyEvent event) -> {
			if (event.getCode() == KeyCode.SPACE) {
				xoboard.resetGame();
			}
		});

	}

	// override the resize method
	@Override
	public void resize(double width, double height) {
		// update size of rect
		super.resize(width, height);
		xoboard.resize(width, height);
	}

	// private fields of the class
	private XOBoard xoboard;
}