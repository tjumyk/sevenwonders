package org.tjuscs.sevenwonders.kernel;

import javax.swing.JOptionPane;

import org.tjuscs.sevenwonders.Manager;
import org.tjuscs.sevenwonders.kernel.RecManager.GameInfo;
import org.tjuscs.sevenwonders.kernel.RecManager.TurnInfo;

public class ReplayPlayer extends GamePlayer {

	private static GameInfo record;

	public static void init(GameInfo record) {
		ReplayPlayer.record = record;
	}

	public ReplayPlayer(int i) {
		this.index = i;
	}

	@Override
	public CommandOption makeAChoice(CommandOption[] options) {
		if (record != null){
			TurnInfo t = record.ages[this.getAge() - 1].turns[7 - options.length][this.index];
			int i = t.chosenCardIndex;
			//JOptionPane.showMessageDialog(null, i);
			options[i].setCommand(t.chosenCommandType);
			return options[i];
		}else
			return null;
	}

	@Override
	public void makeABuyDecision(SimpleResList needs, SimpleResList leftGoods, SimpleResList rightGoods) {
		// TODO Auto-generated method stub

	}

}
