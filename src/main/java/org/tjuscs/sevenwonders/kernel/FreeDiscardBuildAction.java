package org.tjuscs.sevenwonders.kernel;

/**
 * The Class FreeDiscardBuildAction.
 */
public class FreeDiscardBuildAction implements Action, DelayedAction {

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
		GameManager.getManager().addEOTDelayedAction(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tjuscs.sevenwonders.core.DelayedAction#doDelayedAction()
	 */
	public void doDelayedAction() {
		Card[] discards =GameManager.getManager().getCardManager().getDiscardedCards();
		CommandOption[] options = new CommandOption[discards.length];
		int i = 0;
		for (Card crd : discards) {
			options[i] = new CommandOption(crd, 0, null, null, null, true, true);
			i++;
		}

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("TESTING ");
		System.out.println("TESTING FREEDISCARDBUILDACTION:");
		System.out.println("TESTING on: " + board.getName());
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		board.getPlayerChoice(options);

		GameManager.getManager().removeEOTDelayedAction(this); // remove because this is a one time
											// action
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "free build from the discard pile at the end of this turn";
	}

}
