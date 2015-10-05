package org.tjuscs.sevenwonders.net;

import org.tjuscs.sevenwonders.kernel.CommandOption;
import org.tjuscs.sevenwonders.kernel.GamePlayer;
import org.tjuscs.sevenwonders.kernel.SimpleResList;

public class NetPlayer extends GamePlayer {

    public NetPlayer(int i, String playerName) {
        this.index = i;
        this.name = playerName;
    }

    @Override
    public CommandOption makeAChoice(CommandOption[] options) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void makeABuyDecision(SimpleResList needs, SimpleResList leftGoods,
                                 SimpleResList rightGoods) {
        // TODO Auto-generated method stub

    }

}
