package org.tjuscs.sevenwonders.kernel;

import java.util.ArrayList;

/**
 * The Class CopyNeighborsGuildAction.
 */
public class CopyNeighborsGuildAction implements Action, DelayedAction {

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
		GameManager.getManager().addEOGDelayedAction(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tjuscs.sevenwonders.core.DelayedAction#doDelayedAction()
	 */
	public void doDelayedAction() {
		ArrayList<CommandOption> options = new ArrayList<CommandOption>();
		Board neighbor = board.getLeftNeighbor();
		for (Card card : neighbor.structures) {
			if (card.getColor() == CardColor.PURPLE)
				options.add(new CommandOption(card, 0, null, null, null, true,
						true));
		}

		neighbor = board.getRightNeighbor();
		for (Card card : neighbor.structures) {
			if (card.getColor() == CardColor.PURPLE)
				options.add(new CommandOption(card, 0, null, null, null, true,
						true));
		}
		CommandOption[] arr = options.toArray(null);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("TESTING ");
		System.out.println("TESTING CopyNeighborsGuildAction:");
		System.out.println("TESTING ");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		board.getPlayerChoice(arr);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "copies a neighbors guild at the end of the game";
	}

}
