package org.tjuscs.sevenwonders.kernel;

/**
 * The Class ThreeColorBasedVpAction.
 */
public class ThreeColorBasedVpAction implements Action, DelayedAction {

	/** The vps. */
	int vps = 1;

	/** The board. */
	Board board;

	/** The current count. */
	int currentCount;

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
		currentCount = board.getColorCount(CardColor.BROWN)
				+ board.getColorCount(CardColor.GREY)
				+ board.getColorCount(CardColor.PURPLE);
		board.addToVPs(currentCount);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tjuscs.sevenwonders.core.DelayedAction#doDelayedAction()
	 */
	public void doDelayedAction() {
		int newCount = board.getColorCount(CardColor.BROWN)
				+ board.getColorCount(CardColor.GREY)
				+ board.getColorCount(CardColor.PURPLE);

		int newStructures = 0;
		if (newCount > currentCount) {
			newStructures = newCount - currentCount;
			currentCount = newCount;
		}
		board.GuildVps += newStructures * vps;
		board.addToVPs(newStructures * vps);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "vps for brown, grey, and purple building built";
	}
}
