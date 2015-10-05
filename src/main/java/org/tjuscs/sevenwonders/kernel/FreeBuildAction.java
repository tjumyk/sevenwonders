package org.tjuscs.sevenwonders.kernel;

public class FreeBuildAction implements Action {

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
		board.canfreebuild = true;
	}

}
