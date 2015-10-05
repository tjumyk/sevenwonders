package org.tjuscs.sevenwonders.kernel;

/**
 * The Class FreeSciSymbolAction.
 */
public class FreeSciSymbolAction implements Action {

	/** The board. */
	Board board;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tjuscs.sevenwonders.core.Action#activate(org.tjuscs.sevenwonders.
	 * core.Board)
	 */
	public void activate(Board brd) {
		board = brd;
		board.setFreeSci(board.getFreeSci() + 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "free science symbol at the end of the game ";
	}

}
