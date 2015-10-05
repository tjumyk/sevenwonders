package org.tjuscs.sevenwonders.kernel;

/**
 * The Class PlayLastCardAction.
 */
public class PlayLastCardAction implements Action, DelayedAction {

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
		board.saveSeventhCard = true;
		GameManager.getManager().addEOADelayedAction(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tjuscs.sevenwonders.core.DelayedAction#doDelayedAction()
	 */
	public void doDelayedAction() {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("TESTING ");
		System.out.println("TESTING PlayLastCardAction:");
		System.out.println("TESTING on: " + board.getName());
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

		if (board.saveSeventhCard && board.seventhCard != null) {
			Hand hand = new Hand();
			hand.add(board.seventhCard);
			board.getPlayerChoice(board.buildCommandOptions(hand));
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "play of the seventh card at the end of the age";
	}

}
