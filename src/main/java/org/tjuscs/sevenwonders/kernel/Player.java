package org.tjuscs.sevenwonders.kernel;

import java.io.Serializable;

/**
 * The Interface Player.
 */
public interface Player extends Serializable{

	/**
	 * Sets the board.
	 * 
	 * @param b
	 *            the new board
	 */
	public void setBoard(Board b);

	/**
	 * The player makes its choice based on the options given.<br>
	 * 玩家根据给出的选项作出选择。
	 * 
	 * @param options
	 *            the options available
	 * @return the command option it chose<br>
	 *         玩家选择的命令选项。
	 */
	public CommandOption makeChoice(CommandOption[] options);

	/**
	 * Make buy decision.
	 * 
	 * @param needs
	 *            the needs
	 * @param leftGoods
	 *            the left goods
	 * @param rightGoods
	 *            the right goods
	 */
	public void makeBuyDecision(SimpleResList needs, SimpleResList leftGoods,
								SimpleResList rightGoods);
}
