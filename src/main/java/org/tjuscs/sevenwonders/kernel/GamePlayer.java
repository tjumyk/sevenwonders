package org.tjuscs.sevenwonders.kernel;

import org.tjuscs.sevenwonders.Manager;
import org.tjuscs.sevenwonders.gui.MainBackGround;

public abstract class GamePlayer implements Player {
    protected Board board;
    protected int index;
    protected String name;

    public final CommandOption makeChoice(CommandOption[] options) {
        CommandOption opt = makeAChoice(options);
        MainBackGround.implementPlayerCommandOption(index, opt);
        return opt;
    }

    public final void makeBuyDecision(SimpleResList needs, SimpleResList leftGoods, SimpleResList rightGoods) {
        //Inform the GUI!!!
        makeABuyDecision(needs, leftGoods, rightGoods);
    }

    public abstract CommandOption makeAChoice(CommandOption[] options);

    public abstract void makeABuyDecision(SimpleResList needs, SimpleResList leftGoods, SimpleResList rightGoods);


    public Board getBoard() {
        return board;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return Manager.getKernel().getAge();
    }

}
