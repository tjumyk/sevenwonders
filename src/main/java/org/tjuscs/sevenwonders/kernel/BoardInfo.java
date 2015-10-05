package org.tjuscs.sevenwonders.kernel;

/**
 * enum the information of the board.
 * 枚举奇迹板的信息
 */
public enum BoardInfo {

	/** The RHODE s_ a. */
	RHODES_A(1) {
		public Board buildBoard() {
			Board board = new Board("Rhodes", Resource.ORE, 3);
			Stage stage = new Stage("Rhodes STG1", 1);

			stage.addCost(Resource.WOOD, 2);
			stage.addGoods(Resource.VP, 3);
			board.setStage(stage);

			stage = new Stage("Rhodes STG2", 2);
			// stage.setName("Rhodes STG2");
			stage.addCost(Resource.BRICK, 3);
			stage.addGoods(Resource.SHEILD, 2);
			board.setStage(stage);

			stage = new Stage("Rhodes STG3", 3);
			// stage.setName("Rhodes STG3");
			stage.addCost(Resource.ORE, 4);
			stage.addGoods(Resource.VP, 7);
			board.setStage(stage);

			board.setBSide(false);
			return board;
		}
	}, // end of RHODES_A

	/** The ALEXANDRI a_ a. */
	ALEXANDRIA_A(1) {
		public Board buildBoard() {
			Board board = new Board("Alexandria", Resource.GLASS, 3);
			Stage stage = new Stage("Alexandria STG1", 1);

			stage.addCost(Resource.STONE, 2);
			stage.addGoods(Resource.VP, 3);
			board.setStage(stage);

			stage = new Stage("Alexandria STG2", 2);
			// stage.setName("Alexandria STG2");
			stage.addCost(Resource.ORE, 2);
			stage.addGoods(Resource.BRICK_STONE_ORE_WOOD, 1);
			board.setStage(stage);

			stage = new Stage("Alexandria STG3", 3);
			// stage.setName("Alexandria STG3");
			stage.addCost(Resource.GLASS, 2);
			stage.addGoods(Resource.VP, 7);
			board.setStage(stage);
			board.setBSide(false);
			return board;
		}
	}, // end of ALEXANDRIA_A

	/** The EPHESU s_ a. */
	EPHESUS_A(1) {
		public Board buildBoard() {
			Board board = new Board("Ephesus", Resource.PAPYRUS, 3);
			Stage stage = new Stage("Ephesus STG1", 1);

			stage.addCost(Resource.STONE, 2);
			stage.addGoods(Resource.VP, 3);
			board.setStage(stage);

			stage = new Stage("Ephesus STG2", 2);
			// stage.setName("Ephesus STG2");
			stage.addCost(Resource.ORE, 2);
			stage.addGoods(Resource.COIN, 9);
			board.setStage(stage);

			stage = new Stage("Ephesus STG3", 3);
			// stage.setName("Ephesus STG3");
			stage.addCost(Resource.PAPYRUS, 2);
			stage.addGoods(Resource.VP, 7);
			board.setStage(stage);
			board.setBSide(false);
			return board;
		}
	}, // end of EPHESUS_A

	/** The BABYLO n_ a. */
	BABYLON_A(1) {
		public Board buildBoard() {
			Board board = new Board("Babylon", Resource.BRICK, 3);
			Stage stage = new Stage("Babylon STG1", 1);

			stage.addCost(Resource.BRICK, 2);
			stage.addGoods(Resource.VP, 3);
			board.setStage(stage);

			stage = new Stage("Babylon STG2", 2);
			stage.addCost(Resource.WOOD, 3);
			stage.setAction(new FreeSciSymbolAction());
			board.setStage(stage);

			stage = new Stage("Babylon STG3", 3);
			stage.addCost(Resource.BRICK, 4);
			stage.addGoods(Resource.VP, 7);
			board.setStage(stage);
			board.setBSide(false);
			return board;
		}
	}, // end of BABYLON_A

	/** The OYLMPI a_ a. */
	OYLMPIA_A(1) {
		public Board buildBoard() {
			Board board = new Board("Olympia", Resource.WOOD, 3);
			Stage stage = new Stage("Olympia STG1", 1);

			stage.addCost(Resource.WOOD, 2);
			stage.addGoods(Resource.VP, 3);
			board.setStage(stage);

			stage = new Stage("Olympia STG2", 2);
			stage.addCost(Resource.STONE, 2);
			stage.setAction(new FreeBuildAction());
			board.setStage(stage);

			stage = new Stage("Olympia STG3", 3);
			stage.addCost(Resource.ORE, 4);
			stage.addGoods(Resource.VP, 7);
			board.setStage(stage);
			board.setBSide(false);
			return board;
		}
	}, // end of OYLMPIA_A

	/** The HALICARNASSU s_ a. */
	HALICARNASSUS_A(1) {
		public Board buildBoard() {
			Board board = new Board("Halicarnassus", Resource.CLOTH, 3);
			Stage stage = new Stage("Halicarnassus STG1", 1);

			stage.addCost(Resource.BRICK, 2);
			stage.addGoods(Resource.VP, 3);
			board.setStage(stage);

			stage = new Stage("Halicarnassus STG2", 2);
			stage.addCost(Resource.ORE, 3);
			stage.setAction(new FreeDiscardBuildAction());
			board.setStage(stage);

			stage = new Stage("Halicarnassus STG3", 3);
			stage.addCost(Resource.CLOTH, 2);
			stage.addGoods(Resource.VP, 7);
			board.setStage(stage);
			board.setBSide(false);
			return board;
		}
	}, // end of HALICARNASSUS_A

	/** The GIZ a_ a. */
	GIZA_A(1) {
		public Board buildBoard() {
			Board board = new Board("Giza", Resource.STONE, 3);
			Stage stage = new Stage("Giza STG1", 1);

			stage.addCost(Resource.STONE, 2);
			stage.addGoods(Resource.VP, 3);
			board.setStage(stage);

			stage = new Stage("Giza STG2", 2);
			stage.addCost(Resource.WOOD, 3);
			stage.addGoods(Resource.VP, 5);
			board.setStage(stage);

			stage = new Stage("Giza STG3", 3);
			stage.addCost(Resource.STONE, 4);
			stage.addGoods(Resource.VP, 7);
			board.setStage(stage);
			board.setBSide(false);
			return board;
		}
	}, // end of GIZA_A

	/** The RHODE s_ b. */
	RHODES_B(2) {
		public Board buildBoard() {
			Board board = new Board("Rhodes", Resource.ORE, 2);
			Stage stage = new Stage("Rhodes STG1b", 1);

			stage.addCost(Resource.STONE, 3);
			stage.addGoods(Resource.VP, 3);
			stage.addGoods(Resource.COIN, 3);
			stage.addGoods(Resource.SHEILD, 1);
			board.setStage(stage);

			stage = new Stage("Rhodes STG2", 2);
			stage.addCost(Resource.ORE, 4);
			stage.addGoods(Resource.VP, 4);
			stage.addGoods(Resource.COIN, 4);
			stage.addGoods(Resource.SHEILD, 1);
			board.setStage(stage);
			board.setBSide(true);
			return board;
		}
	}, // end of RHODES_B

	/** The ALEXANDRI a_ b. */
	ALEXANDRIA_B(2) {
		public Board buildBoard() {
			Board board = new Board("Alexandria", Resource.GLASS, 3);
			Stage stage = new Stage("Alexandria STG1b", 1);

			stage.addCost(Resource.BRICK, 2);
			stage.addGoods(Resource.BRICK_STONE_ORE_WOOD, 1);
			board.setStage(stage);

			stage = new Stage("Alexandria STG3", 2);
			stage.addCost(Resource.WOOD, 2);
			stage.addGoods(Resource.PAPYRUS_GLASS_CLOTH, 1);
			board.setStage(stage);

			stage = new Stage("Alexandria STG3", 3);
			stage.addCost(Resource.STONE, 4);
			stage.addGoods(Resource.VP, 7);
			board.setStage(stage);
			board.setBSide(true);
			return board;
		}
	}, // end of ALEXANDRIA_B

	/** The EPHESU s_ b. */
	EPHESUS_B(2) {
		public Board buildBoard() {
			Board board = new Board("Ephesus", Resource.PAPYRUS, 3);
			Stage stage = new Stage("Ephesus STG1b", 1);

			stage.addCost(Resource.STONE, 2);
			stage.addGoods(Resource.VP, 2);
			stage.addGoods(Resource.COIN, 4);
			board.setStage(stage);

			stage = new Stage("Ephesus STG2", 2);
			stage.addCost(Resource.WOOD, 2);
			stage.addGoods(Resource.VP, 3);
			stage.addGoods(Resource.COIN, 4);
			board.setStage(stage);

			stage = new Stage("Ephesus STG2", 3);
			stage.addCost(Resource.PAPYRUS, 1);
			stage.addCost(Resource.CLOTH, 1);
			stage.addCost(Resource.GLASS, 1);
			stage.addGoods(Resource.VP, 5);
			stage.addGoods(Resource.COIN, 4);
			board.setStage(stage);
			board.setBSide(true);
			return board;
		}
	}, // end of EPHESUS_B

	/** The BABYLO n_ b. */
	BABYLON_B(2) {
		public Board buildBoard() {
			Board board = new Board("Babylon", Resource.BRICK, 3);
			Stage stage = new Stage("Babylon STG1b", 1);

			stage.addCost(Resource.BRICK, 1);
			stage.addCost(Resource.CLOTH, 1);
			stage.addGoods(Resource.VP, 3);
			board.setStage(stage);

			stage = new Stage("Babylon STG2", 2);
			stage.addCost(Resource.WOOD, 2);
			stage.addCost(Resource.GLASS, 1);
			stage.setAction(new PlayLastCardAction());
			board.setStage(stage);

			stage = new Stage("Babylon STG3", 3);
			stage.addCost(Resource.BRICK, 3);
			stage.addCost(Resource.PAPYRUS, 1);
			stage.setAction(new FreeSciSymbolAction());
			board.setStage(stage);
			board.setBSide(true);
			return board;
		}
	}, // end of BABYLON_B

	/** The OYLMPI a_ b. */
	OYLMPIA_B(2) {
		public Board buildBoard() {
			Board board = new Board("Olympia", Resource.WOOD, 3);
			Stage stage = new Stage("Olympia STG1b", 1);

			stage.addCost(Resource.WOOD, 2);
			stage.setAction(new DiscountAction(
					DiscountAction.DiscountType.BOTH_RAW));
			board.setStage(stage);

			stage = new Stage("Olympia STG2", 2);
			stage.addCost(Resource.STONE, 2);
			stage.addGoods(Resource.VP, 5);
			board.setStage(stage);

			stage = new Stage("Olympia STG3", 3);
			stage.addCost(Resource.CLOTH, 1);
			stage.addCost(Resource.ORE, 2);
			stage.setAction(new CopyNeighborsGuildAction());
			board.setStage(stage);
			board.setBSide(true);
			return board;
		}
	}, // end of OYLMPIA_B

	/** The HALICARNASSU s_ b. */
	HALICARNASSUS_B(2) {
		public Board buildBoard() {
			Board board = new Board("Halicarnassus", Resource.CLOTH, 3);
			Stage stage = new Stage("Halicarnassus STG1b", 1);

			stage.addCost(Resource.ORE, 2);
			stage.setAction(new FreeDiscardBuildAction());
			board.setStage(stage);

			stage = new Stage("Halicarnassus STG2", 2);
			stage.addCost(Resource.BRICK, 3);
			stage.setAction(new FreeDiscardBuildAction());
			board.setStage(stage);

			stage = new Stage("Halicarnassus STG3", 3);
			stage.addCost(Resource.CLOTH, 1);
			stage.addCost(Resource.PAPYRUS, 1);
			stage.addCost(Resource.GLASS, 1);
			stage.setAction(new FreeDiscardBuildAction());
			board.setStage(stage);
			board.setBSide(true);
			return board;
		}
	}, // end of HALICARNASSUS_B

	/** The GIZ a_ b. */
	GIZA_B(2) {
		public Board buildBoard() {
			Board board = new Board("Giza", Resource.STONE, 4);
			Stage stage = new Stage("Giza STG1b", 1);

			stage.addCost(Resource.WOOD, 2);
			stage.addGoods(Resource.VP, 3);
			board.setStage(stage);

			stage = new Stage("Giza STG2", 2);
			stage.addCost(Resource.STONE, 3);
			stage.addGoods(Resource.VP, 5);
			board.setStage(stage);

			stage = new Stage("Giza STG3", 3);
			stage.addCost(Resource.BRICK, 3);
			stage.addGoods(Resource.VP, 5);
			board.setStage(stage);

			stage = new Stage("Giza STG4", 4);
			stage.addCost(Resource.PAPYRUS, 1);
			stage.addCost(Resource.STONE, 4);
			stage.addGoods(Resource.VP, 7);
			board.setStage(stage);
			board.setBSide(true);
			return board;
		}
	}; // end of GIZA_B

	/** The side. */
	private int side;

	/**
	 * Instantiates a new board info.
	 * 
	 * @param sd
	 *            the sd
	 */
	private BoardInfo(int sd) {
		side = sd;
	}

	/**
	 * Gets the side.
	 * 
	 * @return the side
	 */
	public int getSide() {
		return side;
	}

	/**
	 * Builds the board.
	 * 
	 * @return the board
	 */
	public abstract Board buildBoard();
}
