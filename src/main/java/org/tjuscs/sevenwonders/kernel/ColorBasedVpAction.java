package org.tjuscs.sevenwonders.kernel;

/**
 * The Class ColorBasedVpAction.
 */
public class ColorBasedVpAction implements Action, DelayedAction {

    /**
     * The color.
     */
    CardColor color;

    /**
     * The vps.
     */
    int vps;

    /**
     * The board.
     */
    Board board;

    /**
     * The right count.
     */
    int leftCount, rightCount;

    /**
     * Instantiates a new color based vp action.
     *
     * @param clr the clr
     * @param inc the inc
     */
    public ColorBasedVpAction(CardColor clr, int inc) {
        color = clr;
        vps = inc;
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
        GameManager.getManager().addEOTDelayedAction(this);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.tjuscs.sevenwonders.core.DelayedAction#doDelayedAction()
     */
    public void doDelayedAction() {
        int newLeftCount = board.getLeftNeighbor().getColorCount(color);
        int newRightCount = board.getRightNeighbor().getColorCount(color);

        int newStructures = 0;
        if (newLeftCount > leftCount) {
            newStructures = newLeftCount - leftCount;
            leftCount = newLeftCount;
        }
        if (newRightCount > rightCount) {
            newStructures += newRightCount - rightCount;
            rightCount = newRightCount;
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
        return "vps based on neighbors " + color.toString() + " cards";
    }
}
