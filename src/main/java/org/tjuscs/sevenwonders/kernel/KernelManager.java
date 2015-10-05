/**
 * This is "7 wonders in Java" which is an Java implementation of the famous 
 * same-name board game. 
 * 
 * Copyright (C) 2011 7Wonders Team in Tianjin University.
 * 
 * Original author and project advisor:	Lonnie Heinke
 * Current project manager: 			Miao Yukai
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later versions.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	You can contact us by e-mail: tjumyk@gmail.com
 *
 * Attention: All of the source files are written with the UTF-8 encoding 
 * system. So you may not see the correct Chinese documentation if you are using 
 * other encoding systems!
 */

package org.tjuscs.sevenwonders.kernel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.tjuscs.sevenwonders.Manager;
import org.tjuscs.sevenwonders.gui.GUIManager;
import org.tjuscs.sevenwonders.gui.MainBackGround;
import org.tjuscs.sevenwonders.kernel.RecManager.AgeInfo;
import org.tjuscs.sevenwonders.kernel.RecManager.GameInfo;
import org.tjuscs.sevenwonders.kernel.RecManager.TurnInfo;
import org.tjuscs.sevenwonders.net.NetPlayer;

/**
 * The Class GameManager.
 * 
 */
public class KernelManager {
	private GUIManager gui;

	private GameInfo gameInfo;
	private int age;
	private boolean replayMode;

	public int getAge() {
		return age;
	}

	public void setGUIManger(GUIManager g) {
		gui = g;
	}

	/**
	 * The end-of-turn delayed action list.<br>
	 * 回合结束时的延时动作列表
	 */
	ArrayList<DelayedAction> EOTurnDelayedActionList;

	/**
	 * The end-Of-turn remove list. <br>
	 * 回合结束时的延时动作清除列表
	 */
	ArrayList<DelayedAction> EOTurnRemoveList;

	/**
	 * The end-of-age delayed action list. <br>
	 * 时代结束时的延时动作列表
	 */
	List<DelayedAction> EOAgeDelayedActionList;

	/**
	 * The end-of-game delayed action list. <br>
	 * 游戏结束时的延时动作列表
	 */
	List<DelayedAction> EOGameDelayedActionList;

	/**
	 * The reference to an object of GameManager class. <br>
	 * GameManager类的实例的引用
	 */
	private static KernelManager gm;

	/**
	 * The card manager. <br>
	 * 卡牌的管理类。
	 */
	static CardManager cardManager;

	/**
	 * The players. <br>
	 * 存储所有玩家的数组。
	 */
	GamePlayer[] players;

	/**
	 * The boards.<br>
	 * 存储所有奇迹板的数组。
	 */
	Board[] boards;

	public Board[] getBoards() {
		return boards;
	}

	/**
	 * The hands.<br>
	 * 存储手牌堆的数组。
	 */
	Hand[] hands;

	public Hand[] getHands() {
		return hands;
	}

	/**
	 * The number of players. <br>
	 * 玩家数量
	 */
	int numPlayers;

	/**
	 * The testing output. <br>
	 * 存储测试输出的数组
	 */
	public StringBuilder[] out;

	/**
	 * The Constant LEFT & RIGHT. <br>
	 * 两个常量LEFT和RIGHT
	 */
	public static final int LEFT = 0, RIGHT = 1;

	/**
	 * The Game Record Manager.<br>
	 * 游戏记录管理器。
	 */
	private static RecManager recorder;

	/**
	 * Build a GameManager object and get the reference to it.<br>
	 * 新建GameManager类的对象并获得其引用。
	 * 
	 * @return the reference to the GameManager object<br>
	 *         GameManager对象的引用
	 */
	public static KernelManager getManager() {
		if (gm == null)
			gm = new KernelManager();
		return gm;
	}

	/**
	 * Instantiates a new game manager， it makes four lists of DelayedAction.<br>
	 * GameManager构造函数,建立了四个延时动作列表。<br>
	 * <br>
	 * Added: Initialize RecManager(Game Record Manager).<br>
	 * 新增：初始化RecManager(游戏记录管理器)。
	 * 
	 * @see DelayedAction
	 */
	private KernelManager() {
		EOTurnDelayedActionList = new ArrayList<DelayedAction>();
		EOAgeDelayedActionList = new ArrayList<DelayedAction>();
		EOGameDelayedActionList = new ArrayList<DelayedAction>();
		EOTurnRemoveList = new ArrayList<DelayedAction>();
		recorder = new RecManager();
	}

	public void setNumOfPlayers(int n) {
		replayMode = false;
		numPlayers = n;
		gameInfo = new GameInfo();
		gameInfo.ages = new AgeInfo[3];
	}

	public void setBoards(String whichB) {
		if (whichB.equals("A+B")) {
			boards = BoardFactory.makeAandBSideBoards(numPlayers);
		} else {
			if (whichB.equals("A")) {
				boards = BoardFactory.makeASideBoards(numPlayers);
			} else {
				boards = BoardFactory.makeBSideBoards(numPlayers);
			}
		}
	}

	public void setAIPlayers() {
		GamePlayer player;
		players = new GamePlayer[numPlayers];
		String[] boardNames = new String[numPlayers];
		int[] boardSides = new int[numPlayers];
		String[] playerNames = new String[numPlayers];
		for (int i = 0; i < boards.length; i++) {
			Board board = boards[i];
			players[i] = player = new RandBot(i);
			player.setBoard(board);
			board.setPlayer(player);
			boardNames[i] = board.getName();
			boardSides[i] = board.getSides();
			playerNames[i] = players[i].getName();
		}

		players[0] = player = new GUIPlayer();
		player.setBoard(boards[0]);
		boards[0].setPlayer(player);
		playerNames[0] = players[0].getName();

		for (int i = 0; i < numPlayers; i++)
			playerNames[i] = players[i].getName();
		gameInfo.playerNames = playerNames;
		gameInfo.boardNames = boardNames;
		gameInfo.boardSides = boardSides;
	}
	
	public GameInfo setNetPlayers(String[] netPlayerNames) {
		GamePlayer player;
		players = new GamePlayer[numPlayers];
		String[] boardNames = new String[numPlayers];
		int[] boardSides = new int[numPlayers];
		String[] playerNames = new String[numPlayers];
		for (int i = 0; i < boards.length; i++) {
			Board board = boards[i];
			if(i >= 1){
				players[i] = player = new NetPlayer(i,netPlayerNames[i-1]);
				player.setBoard(board);
				board.setPlayer(player);
				playerNames[i] = players[i].getName();
			}
			boardNames[i] = board.getName();
			boardSides[i] = board.getSides();
		}

		players[0] = player = new GUIPlayer();
		player.setBoard(boards[0]);
		boards[0].setPlayer(player);
		//playerNames[0] = players[0].getName();

		for (int i = 0; i < numPlayers; i++)
			playerNames[i] = players[i].getName();
		gameInfo.playerNames = playerNames;
		gameInfo.boardNames = boardNames;
		gameInfo.boardSides = boardSides;
		return gameInfo;
	}

	/**
	 * Start the game.<br>
	 * 开始游戏
	 * <p>
	 * Set number of players, make a CardManager, make Players, make Boards, and
	 * set the built-in AI player and two neighbors for every board.(Including
	 * make a StringBuilder array for debug output)<br>
	 * 设置玩家数量，根据人数新建一个CardManager、对应数量的Player和Board，并且确定每个Board两边相邻的Board，
	 * 最后设置每个Board的玩家为内置AI.（还包括建立一个StringBuilder对象数组来管理调试输出）
	 * 
	 * @see #numPlayers
	 * 
	 */
	public void initializeGame() { // 加入GUI后改为无参函数

		cardManager = new CardManager(numPlayers);

		out = new StringBuilder[numPlayers];
		for (int i = 0; i < out.length; i++) {
			out[i] = new StringBuilder();
			boards[i].setIndex(i);
		}

		// recorder.recInit(init);

		boards[0].setLeftNeighbor(boards[numPlayers - 1]);
		boards[0].setRightNeighbor(boards[1]);

		for (int i = 1; i < boards.length - 1; i++) {
			boards[i].setLeftNeighbor(boards[i - 1]);
			boards[i].setRightNeighbor(boards[i + 1]);
		}

		boards[numPlayers - 1].setLeftNeighbor(boards[numPlayers - 2]);
		boards[numPlayers - 1].setRightNeighbor(boards[0]);

		for (Board b : boards) {
			Manager.debug(b);
		}

		// recorder.rec(players);
		// recorder.rec(boards);

	}

	/**
	 * Gets the card manager.<br>
	 * 获取CardManager
	 * 
	 * @return the reference to the CardManager object.<br>
	 *         CardManager对象的引用
	 */
	public static CardManager getCardManager() {
		return cardManager;
	}

	/**
	 * Adds the end-of-turn delayed action.<br>
	 * 添加回合结束时的延时动作
	 * 
	 * @param da
	 *            the delayed action. 延时动作
	 */
	public void addEOTDelayedAction(DelayedAction da) {
		EOTurnDelayedActionList.add(da);
	}

	/**
	 * Adds the end-of-age delayed action.<br>
	 * 添加时代结束时的延时动作
	 * 
	 * @param da
	 *            the delayed action. 延时动作
	 */
	public void addEOADelayedAction(DelayedAction da) {
		EOAgeDelayedActionList.add(da);
	}

	/**
	 * Adds the end-of-game delayed action.<br>
	 * 添加游戏结束时的延时动作
	 * 
	 * @param da
	 *            the delayed action. 延时动作
	 */
	public void addEOGDelayedAction(DelayedAction da) {
		EOGameDelayedActionList.add(da);
	}

	/**
	 * Removes the end-of-turn delayed action.<br>
	 * 删除回合结束时的延时动作
	 * 
	 * @param da
	 *            the delayed action. 延时动作
	 */
	public void removeEOTDelayedAction(DelayedAction da) {
		EOTurnRemoveList.add(da);
	}

	/**
	 * Removes the end-of-age delayed action.<br>
	 * 删除时代结束时的延时动作
	 * 
	 * @param da
	 *            the delayed action.延时动作
	 */
	public void removeEOADelayedAction(DelayedAction da) {
		EOAgeDelayedActionList.remove(da);
	}

	/**
	 * Removes the end-of-game delayed action.<br>
	 * 删除游戏结束时的延时动作
	 * 
	 * @param da
	 *            the delayed action.延时动作
	 */
	public void removeEOGDelayedAction(DelayedAction da) {
		EOGameDelayedActionList.remove(da);
	}

	/**
	 * Start an age.<br>
	 * 开始一个时代
	 * <p>
	 * Get the hand decks for this age<br>
	 * 获取此时代的手牌。
	 * 
	 * @param ageNum
	 *            the age number.时代序号(1/2/3)
	 */
	public void startAge(int ageNum) {
		age = ageNum;
		if (!replayMode)
			hands = cardManager.setupHands(ageNum);
		else
			hands = cardManager.loadHands(ageNum,gameInfo.ages[ageNum-1].cardNames);
		// debug output
		Manager.debug("Start Age " + ageNum + "\n");
		for (StringBuilder sb : out) {
			sb.append("\nStart Age " + ageNum + "\n");
			// sb.append(b)
		}
		// for(Hand hand: hands){
		// System.out.println(hand);
		// }
		for (Board b : boards) {
			b.setusefreebuild(true);
		}

		String[][] cardNames = new String[numPlayers][7];
		for (int i = 0; i < numPlayers; i++)
			for (int j = 0; j < 7; j++)
				cardNames[i][j] = hands[i].get(j).getName();
		if (!replayMode) {
			AgeInfo ageInfo = new AgeInfo();
			ageInfo.cardNames = cardNames;
			ageInfo.ageNum = ageNum;
			ageInfo.turns = new TurnInfo[7][];
			for (int i = 0; i < 7; i++)
				ageInfo.turns[i] = new TurnInfo[numPlayers];
			gameInfo.ages[ageNum - 1] = ageInfo;
		}
		// recorder.recAge(ageInfo);
	}

	/**
	 * Start a turn.<br>
	 * 开始一个回合
	 * <p>
	 * Pass the hand decks(clockwise/counter-clockwise) to the corresponding
	 * boards, and ask every board to take its turn one by one.<br>
	 * (按顺时针或逆时针)将手牌传给相应的奇迹，并逐个地请求奇迹来完成该回合。
	 * 
	 * @deprecated Don't use it!<br>
	 *             别用这个哦，亲！╮(╯_╰)╭
	 * @param trnNum
	 *            the turn number. 回合序号
	 * @throws InterruptedException
	 */
	public void startTurn(int trnNum) throws InterruptedException {
		int ind = -1;
		System.out.println("\nTurn " + trnNum);

		for (Board board : boards) {
			out[ind + 1].append("\nTurn " + trnNum);
			out[ind + 1].append("\n" + board);
			System.out.println("\n\n" + board);

			// Thread t = new Thread(board.new TakeTurnTask(hands[(ind + trnNum)
			// % numPlayers], trnNum)); // Q:
			// What
			// if
			// Age 2
			// ?
			// t.start();
			// t.sleep(200000);
			ind++;
			// out[ind].append("\n" + board );
		}
	}

	/**
	 * Do end of turn.<br>
	 * 回合结束时的结算
	 * <p>
	 * Add the income of every board into their treasury<br>
	 * 各方将该回合的收入加入各自的金库中
	 * 
	 * @param trnNum
	 *            the turn number.回合序号
	 */
	public void doEndOfTurn(int trnNum) {
		for (Board board : boards) {
			board.addTurnSales();
		}

		for (DelayedAction da : EOTurnDelayedActionList) {
			da.doDelayedAction();
		}

		for (DelayedAction da : EOTurnRemoveList) {
			EOTurnDelayedActionList.remove(da);
		}
	}

	/**
	 * Do end of age.<br>
	 * 时代结束时的结算
	 * 
	 * @param ageNum
	 *            the age number。时代序号
	 */
	public void doEndOfAge(int ageNum) {
		int[] victVP = { 0, 1, 3, 5 };

		for (DelayedAction da : EOAgeDelayedActionList) {
			da.doDelayedAction();
		}

		for (Board board : boards) {
			int ourSheild = (board.goods.containsKey(Resource.SHEILD) ? board.goods.get(Resource.SHEILD) : 0);
			int lftNghborSheild = (board.getLeftNeighbor().goods.containsKey(Resource.SHEILD) ? board.getLeftNeighbor().goods
					.get(Resource.SHEILD) : 0);
			int rghtNghborSheild = (board.getRightNeighbor().goods.containsKey(Resource.SHEILD) ? board
					.getRightNeighbor().goods.get(Resource.SHEILD) : 0);
			if (ourSheild > lftNghborSheild)
				board.militaryVPS[LEFT][ageNum] = victVP[ageNum];
			else if (ourSheild < lftNghborSheild) {
				board.militaryVPS[LEFT][ageNum] = -1;
			}
			if (ourSheild > rghtNghborSheild)
				board.militaryVPS[RIGHT][ageNum] = victVP[ageNum];
			else if (ourSheild < rghtNghborSheild) {
				board.militaryVPS[RIGHT][ageNum] = -1;
			}
			board.addToVPs(board.militaryVPS[LEFT][ageNum]);
			board.addToVPs(board.militaryVPS[RIGHT][ageNum]);
		}

	} // end of doEndOfAge method

	/**
	 * Do end of game.<br>
	 * 游戏结束时的结算
	 */
	public void doEndOfGame() {
		for (Board board : boards) {
			int sciScore = board.scoreVPs();
			board.addToVPs(sciScore);
			int coinScore = board.cscoreVPs();
			board.addToCoins(coinScore);
		}
		for (DelayedAction da : EOGameDelayedActionList) {
			da.doDelayedAction();
		}
		// TODO do Victory Calculations

	} // end of doEndOfGame method

	public void saveRec(boolean isSaved) {
		if (replayMode || !isSaved)
			return;
		recorder.rec(gameInfo);
	}
	
	public String getTempRec(){
		if(gameInfo != null)
			return gameInfo.toString();
		else
			return null;
	}

	public void recordTurnInfo(TurnInfo info) {
		// recorder.recTurn(turn-1, playerIndex, info);
		// JOptionPane.showMessageDialog(null, init);
		// JOptionPane.showMessageDialog(null, info);
		if (!replayMode)
			gameInfo.ages[MainBackGround.age - 1].turns[MainBackGround.turn - 1][info.playerIndex] = info;
	}

	public void loadRecord(File file) {
		try {
			// Manager.info("ok");
			this.replayMode = true;
			gameInfo = recorder.read(file);
			numPlayers = gameInfo.boardNames.length;
			boards = BoardFactory.makeBoards(gameInfo.boardNames, gameInfo.boardSides);
			ReplayPlayer.init(gameInfo);
			players = new ReplayPlayer[numPlayers];
			for (int i = 0; i < boards.length; i++) {
				players[i] = new ReplayPlayer(i);
				players[i].setBoard(boards[i]);
				boards[i].setPlayer(players[i]);
			}
			initializeGame();

//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		}catch(Throwable e){
			Manager.error(e);
		}
	}
	
	public boolean isReplayMode(){
		return replayMode;
	}
}
