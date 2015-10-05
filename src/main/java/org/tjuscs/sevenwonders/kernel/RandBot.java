package org.tjuscs.sevenwonders.kernel;

import java.util.Random;

import org.tjuscs.sevenwonders.gui.MainBackGround;

/**
 * The Class RandBot.
 */
public class RandBot extends GamePlayer {

	///** The board. */
	//private Board board;
	//private int index;

	// RandBot(Board brd){
	// board = brd;
	// }

//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * org.tjuscs.sevenwonders.core.Player#setBoard(org.tjuscs.sevenwonders.
//	 * core.Board)
//	 */
//	public void setBoard(Board b) {
//		board = b;
//	}
	
	public RandBot(int index){
		this.index = index;
		this.name = "RandBot_"+index;
	}

	public RandBot() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tjuscs.sevenwonders.core.Player#makeChoice(org.tjuscs.sevenwonders
	 * .core.CommandOption[])
	 */
	@Override
	public CommandOption makeAChoice(CommandOption[] options) {
		int buildCnt = 0;
		for (CommandOption opt : options) {
			if (opt.isBuildable())
				buildCnt++;
		}
		if (board.canBuildNextStage()) {
			System.out.println("RandBot::Stage is buildable"); // TODO need to
																// add code to
																// make building
																// the stage an
																// option
		}

		System.out.println("MakeChoice:has " + buildCnt + " buildable cards");
		Random rand = new Random();
		if (buildCnt != 0) {
			int num = rand.nextInt(buildCnt); // TODO buildCnt can be 0, then we
												// need to build Stage or sell
												// card

			buildCnt = 0;
			for (CommandOption opt : options) {
				if (opt.isBuildable()) {
					if (buildCnt == num) {
						System.out.println("MakeChoice:chose " + num
								+ " which is " + opt.card);
						opt.setCommand(Command.BUILD_CARD);
						//MainBackGround.implementPlayerCommandOption(index, opt);
						return opt;
					}
					buildCnt++;
				}
			}
		} else { // no cards are buildable and so randomly chooses to sell a
					// card TODO should also see if Stage is buildable
			int num = rand.nextInt(options.length);
			options[num].setCommand(Command.SELL_CARD);
			System.out.println("MakeChoice:noBuildable so sell "
					+ options[num].card);
			//MainBackGround.implementPlayerCommandOption(index, options[num]);
			return options[num];
		}

		// error in selecting and so sell first card
		options[0].setCommand(Command.SELL_CARD);
		//MainBackGround.implementPlayerCommandOption(index,options[0]);
		return options[0];
	}

	// need to subtract the needed goods from the side of goods that you don't
	// want to buy
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tjuscs.sevenwonders.core.Player#makeBuyDecision(org.tjuscs.sevenwonders
	 * .core.SimpleResList, org.tjuscs.sevenwonders.core.SimpleResList,
	 * org.tjuscs.sevenwonders.core.SimpleResList)
	 */
	public void makeABuyDecision(SimpleResList needs, SimpleResList leftGoods,
			SimpleResList rightGoods) {

		SimpleResList surplus = SimpleResList.subtract(
				SimpleResList.add(leftGoods, rightGoods), needs);
		Random rand = new Random();
		SimpleResList temp;
		System.out.println("Surplus of: " + surplus);
		for (int i = 1; i <= 8; i++) {
			for (int num = surplus.numAt(i); num > 0; num--) {
				// if(surplus.numAt(num) == 0)
				// continue;
				System.out.print("Choose to buy one less from ");
				if (rand.nextInt(2) == 0) {
					System.out.print("left neighbor");
					temp = new SimpleResList(SimpleResList.resourceAt(i));
					System.out.println(" this amt " + temp);
					leftGoods.subtract(temp);
				} else {
					System.out.print("right neighbor");
					temp = new SimpleResList(SimpleResList.resourceAt(i));
					System.out.println(" this amt " + temp);
					rightGoods.subtract(temp);
				}
			}
		}

	}

}
