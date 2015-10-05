package org.tjuscs.sevenwonders.kernel;

import java.util.Random;

/**
 * A factory for creating Board objects. 制造奇迹板的类
 */
public class BoardFactory {

	/**
	 * Make only have A sides's boards. 创建只有A面的奇迹板
	 * 
	 * @param num
	 *            the number of boards
	 * @return the board[]
	 */
	public static Board[] makeASideBoards(int num) {
		Board[] boardPile = new Board[7];
		boardPile[0] = BoardInfo.ALEXANDRIA_A.buildBoard();
		boardPile[1] = BoardInfo.BABYLON_A.buildBoard();
		boardPile[2] = BoardInfo.EPHESUS_A.buildBoard();
		boardPile[3] = BoardInfo.GIZA_A.buildBoard();
		boardPile[4] = BoardInfo.HALICARNASSUS_A.buildBoard();
		boardPile[5] = BoardInfo.OYLMPIA_A.buildBoard();
		boardPile[6] = BoardInfo.RHODES_A.buildBoard();

		shuffle(boardPile);
		Board[] returnBoards = new Board[num];
		for (int i = 0; i < returnBoards.length; i++) {
			returnBoards[i] = boardPile[i];
		}
		return returnBoards;
	}

	/**
	 * Make only have B side's boards. 创建只有B面的奇迹板
	 * 
	 * @param num
	 *            the number of boards
	 * @return the board[]
	 */
	public static Board[] makeBSideBoards(int num) {
		Board[] boardPile = new Board[7];
		boardPile[0] = BoardInfo.ALEXANDRIA_B.buildBoard();
		boardPile[1] = BoardInfo.BABYLON_B.buildBoard();
		boardPile[2] = BoardInfo.EPHESUS_B.buildBoard();
		boardPile[3] = BoardInfo.GIZA_B.buildBoard();
		boardPile[4] = BoardInfo.HALICARNASSUS_B.buildBoard();
		boardPile[5] = BoardInfo.OYLMPIA_B.buildBoard();
		boardPile[6] = BoardInfo.RHODES_B.buildBoard();

		shuffle(boardPile);
		Board[] returnBoards = new Board[num];
		for (int i = 0; i < returnBoards.length; i++) {
			returnBoards[i] = boardPile[i];
		}
		return returnBoards;
	}

	/**
	 * Make A and B side boards. 创建同时含有A面和B面的奇迹板
	 * 
	 * @param num
	 *            the number of boards
	 * @return the board[]
	 */
	public static Board[] makeAandBSideBoards(int num) {
		Board[] boardPile = new Board[7];
		Random rndGen = new Random(System.currentTimeMillis());

		boardPile[0] = (rndGen.nextInt(100) <= 50 ? BoardInfo.ALEXANDRIA_A.buildBoard() : BoardInfo.ALEXANDRIA_B
				.buildBoard());
		boardPile[1] = (rndGen.nextInt(100) <= 50 ? BoardInfo.BABYLON_A.buildBoard() : BoardInfo.BABYLON_B.buildBoard());
		boardPile[2] = (rndGen.nextInt(100) <= 50 ? BoardInfo.EPHESUS_A.buildBoard() : BoardInfo.EPHESUS_B.buildBoard());
		boardPile[3] = (rndGen.nextInt(100) <= 50 ? BoardInfo.GIZA_A.buildBoard() : BoardInfo.GIZA_B.buildBoard());
		boardPile[4] = (rndGen.nextInt(100) <= 50 ? BoardInfo.HALICARNASSUS_A.buildBoard() : BoardInfo.HALICARNASSUS_B
				.buildBoard());
		boardPile[5] = (rndGen.nextInt(100) <= 50 ? BoardInfo.OYLMPIA_A.buildBoard() : BoardInfo.OYLMPIA_B.buildBoard());
		boardPile[6] = (rndGen.nextInt(100) <= 50 ? BoardInfo.RHODES_A.buildBoard() : BoardInfo.RHODES_B.buildBoard());

		shuffle(boardPile);
		Board[] returnBoards = new Board[num];
		for (int i = 0; i < returnBoards.length; i++) {
			returnBoards[i] = boardPile[i];
		}
		return returnBoards;
	}

	public static Board[] makeBoards(String[] boardNames, int[] boardSides) {
		Board[] boards = new Board[boardNames.length];
		for (int i = 0; i < boardNames.length; i++) {
			if (boardNames[i].equals("Rhodes")) {
				if (boardSides[i] == 0)
					boards[i] = BoardInfo.RHODES_A.buildBoard();
				else
					boards[i] = BoardInfo.RHODES_B.buildBoard();
			}else if (boardNames[i].equals("Alexandria")) {
				if (boardSides[i] == 0)
					boards[i] = BoardInfo.ALEXANDRIA_A.buildBoard();
				else
					boards[i] = BoardInfo.ALEXANDRIA_B.buildBoard();
			}else if (boardNames[i].equals("Ephesus")) {
				if (boardSides[i] == 0)
					boards[i] = BoardInfo.EPHESUS_A.buildBoard();
				else
					boards[i] = BoardInfo.EPHESUS_B.buildBoard();
			}else if (boardNames[i].equals("Babylon")) {
				if (boardSides[i] == 0)
					boards[i] = BoardInfo.BABYLON_A.buildBoard();
				else
					boards[i] = BoardInfo.BABYLON_B.buildBoard();
			}else if (boardNames[i].equals("Olympia")) {
				if (boardSides[i] == 0)
					boards[i] = BoardInfo.OYLMPIA_A.buildBoard();
				else
					boards[i] = BoardInfo.OYLMPIA_B.buildBoard();
			}else if (boardNames[i].equals("Halicarnassus")) {
				if (boardSides[i] == 0)
					boards[i] = BoardInfo.HALICARNASSUS_A.buildBoard();
				else
					boards[i] = BoardInfo.HALICARNASSUS_B.buildBoard();
			}else if (boardNames[i].equals("Giza")) {
				if (boardSides[i] == 0)
					boards[i] = BoardInfo.GIZA_A.buildBoard();
				else
					boards[i] = BoardInfo.GIZA_B.buildBoard();
			}
		}
		return boards;
	}

	/**
	 * Shuffle the card of given boards 为指定的奇迹板洗牌
	 * 
	 * @param brds
	 *            the boards 奇迹板
	 */
	private static void shuffle(Board[] brds) {
		Board temp;
		int randNum;
		Random rndGen = new Random(System.currentTimeMillis());
		for (int sh = 0; sh < 4; sh++)
			for (int i = 0; i < brds.length; i++) {
				temp = brds[i];
				randNum = rndGen.nextInt(brds.length);
				brds[i] = brds[randNum];
				brds[randNum] = temp;
			}
	}
}
