package org.tjuscs.sevenwonders.kernel;

/**
 * The Class ColorIncAndVpAction.
 */
public class ColorIncAndVpAction implements Action, DelayedAction {

	/** The color. */
	CardColor color;

	/** The incr. */
	int incr;

	/** The board. */
	Board board;

	/** The self count. */
	int selfCount;

	/**
	 * Instantiates a new color inc and vp action.
	 * 
	 * @param clr
	 *            the clr
	 * @param inc
	 *            the inc
	 */
	public ColorIncAndVpAction(CardColor clr, int inc) {
		color = clr;
		incr = inc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tjuscs.sevenwonders.core.Action#activate(org.tjuscs.sevenwonders.
	 * core.Board)
	 */
	public void activate(Board brd) {
		board = brd;
		int tempCount = board.getColorCount(color);
		board.addToCoins(tempCount * incr);
		GameManager.getManager().addEOTDelayedAction(this);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tjuscs.sevenwonders.core.DelayedAction#doDelayedAction()
	 */
	public void doDelayedAction() {
		int newSelfCount = board.getColorCount(color);
		int newStructures = 0;

		if (newSelfCount > selfCount) {
			newStructures += newSelfCount - selfCount;
			selfCount = newSelfCount;
		}
		if (newStructures * incr != 0)
		{
			board.CommerceVps += newStructures * incr;
			board.addToVPs(newStructures * incr);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "coins and vps based on your own and neighbors "
				+ color.toString() + " cards";
	}

}
