package org.tjuscs.sevenwonders.kernel;

/**
 * The Class NeighborDefeatVpAction.
 */
public class NeighborDefeatVpAction implements Action, DelayedAction {

    /**
     * The board.
     */
    Board board;

    /**
     * The right count.
     */
    int leftCount, rightCount;

    /*
     * (non-Javadoc)
     *
     * @see
     * org.tjuscs.sevenwonders.core.Action#activate(org.tjuscs.sevenwonders.
     * core.Board)
     */
    public void activate(Board brd) {
        board = brd;
        GameManager.getManager().addEOADelayedAction(this);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.tjuscs.sevenwonders.core.DelayedAction#doDelayedAction()
     */
    public void doDelayedAction() {
        int newLeftCount = board.getLeftNeighbor().getNumberOfDefeats();
        int newRightCount = board.getRightNeighbor().getNumberOfDefeats();

        int newDefeats = 0;
        if (newLeftCount > leftCount) {
            newDefeats = newLeftCount - leftCount;
            leftCount = newLeftCount;
        }
        if (newRightCount > rightCount) {
            newDefeats += newRightCount - rightCount;
            rightCount = newRightCount;
        }
        board.GuildVps += newDefeats;
        board.addToVPs(newDefeats);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "vps for the amount of defeats suffered by neighbors";
    }
} // end of NeighborDefeatVpAction class
