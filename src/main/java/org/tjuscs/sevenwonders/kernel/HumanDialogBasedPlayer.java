package org.tjuscs.sevenwonders.kernel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * The Class HumanDialogBasedPlayer.
 */
public class HumanDialogBasedPlayer implements Player {

	/** The board. */
	private Board board;

	/** The choice. */
	private int choice = 0;

	/** The cmd choice. */
	private Command cmdChoice = Command.SELL_CARD;

	/** The commands. */
	private Command[] commands = { Command.BUILD_CARD, Command.SELL_CARD,
			Command.BUILD_STAGE };

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tjuscs.sevenwonders.core.Player#setBoard(org.tjuscs.sevenwonders.
	 * core.Board)
	 */
	public void setBoard(Board b) {
		board = b;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tjuscs.sevenwonders.core.Player#makeChoice(org.tjuscs.sevenwonders
	 * .core.CommandOption[])
	 */
	public CommandOption makeChoice(CommandOption[] options) {

		int numButtons = options.length;
		JRadioButton[] radioButtons = new JRadioButton[numButtons];
		ButtonGroup group = new ButtonGroup();

		JPanel bigPanel = new JPanel();
		JPanel nPanel = new JPanel();
		nPanel.add(new BoardPanel(board, "My Board"));
		JPanel ePanel = new BoardPanel(board.rightNeighbor, "Right board");
		JPanel wPanel = new BoardPanel(board.leftNeighbor, "Left board");
		JPanel sPanel = new JPanel();
		JPanel panel = new JPanel();

		bigPanel.setLayout(new BorderLayout(20, 2));
		bigPanel.add(nPanel, BorderLayout.NORTH);
		bigPanel.add(ePanel, BorderLayout.EAST);
		bigPanel.add(wPanel, BorderLayout.WEST);
		bigPanel.add(sPanel, BorderLayout.SOUTH);
		bigPanel.add(panel, BorderLayout.CENTER);

		nPanel.add(new JLabel("  Can build next Stage: "
				+ board.canBuildNextStage() + "   "
				+ board.stages[board.stagesCompleted].cost));
		sPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		ActionListener cardLster = new CardOptionListener();
		for (int i = 0; i < numButtons; i++) {
			radioButtons[i] = new JRadioButton(options[i].toString());
			radioButtons[i].setActionCommand("" + i);
			group.add(radioButtons[i]);
			radioButtons[i].addActionListener(cardLster);
			panel.add(radioButtons[i]);
		}
		// radioButtons[0].setSelected(true);

		JRadioButton[] cmdRadioButtons = new JRadioButton[3];
		ButtonGroup cmdGroup = new ButtonGroup();
		ActionListener cmdLster = new CommandOptionListener();
		for (int i = 0; i < 3; i++) {
			cmdRadioButtons[i] = new JRadioButton(commands[i].toString());
			cmdRadioButtons[i].setActionCommand("" + i);
			cmdGroup.add(cmdRadioButtons[i]);
			cmdRadioButtons[i].addActionListener(cmdLster);
			sPanel.add(cmdRadioButtons[i]);
		}
		// cmdRadioButtons[0].setSelected(true);

		JOptionPane.showMessageDialog(null, bigPanel, board.getName(),
				JOptionPane.PLAIN_MESSAGE);

		if (board.canBuildNextStage()) 
			options[choice].setCanBuildStage(true);
		options[choice].setCommand(cmdChoice);
		System.out.println("HumanDialogPlayer Chose: "
				+ options[choice].toString());
		return options[choice];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tjuscs.sevenwonders.core.Player#makeBuyDecision(org.tjuscs.sevenwonders
	 * .core.SimpleResList, org.tjuscs.sevenwonders.core.SimpleResList,
	 * org.tjuscs.sevenwonders.core.SimpleResList)
	 */
	//TODO make buy decision
	@Override
	public void makeBuyDecision(SimpleResList needs, SimpleResList leftGoods,
			SimpleResList rightGoods) {
		int needtol = needs.getTotalRes();
		for (int i = 0; i < needtol; i++) {
			
		}
		System.out.println("HumanDialogBasedPlayer::makeBuyDecision");
	}

	/**
	 * The listener interface for receiving cardOption events. The class that is
	 * interested in processing a cardOption event implements this interface,
	 * and the object created with that class is registered with a component
	 * using the component's <code>addCardOptionListener<code> method. When
	 * the cardOption event occurs, that object's appropriate
	 * method is invoked.
	 * 
	 * @see CardOptionEvent
	 */
	class CardOptionListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		public void actionPerformed(ActionEvent e) {
			choice = Integer.parseInt(e.getActionCommand());
			System.out.println("HumanDialogBasedPlayer:: choice changed to "
					+ choice);
		}
	}

	/**
	 * The listener interface for receiving commandOption events. The class that
	 * is interested in processing a commandOption event implements this
	 * interface, and the object created with that class is registered with a
	 * component using the component's
	 * <code>addCommandOptionListener<code> method. When
	 * the commandOption event occurs, that object's appropriate
	 * method is invoked.
	 * 
	 * @see CommandOptionEvent
	 */
	class CommandOptionListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		public void actionPerformed(ActionEvent e) {
			cmdChoice = commands[Integer.parseInt(e.getActionCommand())];
			System.out.println("HumanDialogBasedPlayer:: command changed to "
					+ cmdChoice);
		}
	}

	/**
	 * The Class BoardPanel.
	 */
	class BoardPanel extends JPanel {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = -1739998564556370924L;

		/** The board. */
		Board board;

		/** The labels. */
		JLabel[] labels;

		/**
		 * Instantiates a new board panel.
		 * 
		 * @param nghbr
		 *            the nghbr
		 * @param locStr
		 *            the loc str
		 */
		public BoardPanel(Board nghbr, String locStr) {
			board = nghbr;

			setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

			add(new JLabel(locStr + ":"));
			add(new JLabel(board.getName()));

			labels = new JLabel[21];

			for (int i = 0; i < labels.length; i++) {
				labels[i] = new JLabel();
				add(labels[i]);
			}

			update();
			doLayout();
		}

		/**
		 * Update.
		 */
		public void update() {
			int num;
			if (board.goods.containsKey(Resource.BRICK)
					&& (num = board.goods.get(Resource.BRICK)) > 0)
				labels[0].setText("Brick: " + num);
			if (board.goods.containsKey(Resource.ORE)
					&& (num = board.goods.get(Resource.ORE)) > 0)
				labels[1].setText("Ore: " + num);
			if (board.goods.containsKey(Resource.STONE)
					&& (num = board.goods.get(Resource.STONE)) > 0)
				labels[2].setText("Stone: " + num);
			if (board.goods.containsKey(Resource.WOOD)
					&& (num = board.goods.get(Resource.WOOD)) > 0)
				labels[3].setText("Wood: " + num);
			if (board.goods.containsKey(Resource.CLOTH)
					&& (num = board.goods.get(Resource.CLOTH)) > 0)
				labels[4].setText("Cloth: " + num);
			if (board.goods.containsKey(Resource.GLASS)
					&& (num = board.goods.get(Resource.GLASS)) > 0)
				labels[5].setText("Glass: " + num);
			if (board.goods.containsKey(Resource.PAPYRUS)
					&& (num = board.goods.get(Resource.PAPYRUS)) > 0)
				labels[6].setText("Papyrus: " + num);

			if (board.goods.containsKey(Resource.WOOD_BRICK)
					&& (num = board.goods.get(Resource.WOOD_BRICK)) > 0)
				labels[7].setText("wood/brick: " + num);
			if (board.goods.containsKey(Resource.BRICK_ORE)
					&& (num = board.goods.get(Resource.BRICK_ORE)) > 0)
				labels[8].setText("brick/ore: " + num);
			if (board.goods.containsKey(Resource.STONE_BRICK)
					&& (num = board.goods.get(Resource.STONE_BRICK)) > 0)
				labels[9].setText("stone/brick: " + num);
			if (board.goods.containsKey(Resource.STONE_WOOD)
					&& (num = board.goods.get(Resource.STONE_WOOD)) > 0)
				labels[10].setText("stone/wood: " + num);
			if (board.goods.containsKey(Resource.WOOD_ORE)
					&& (num = board.goods.get(Resource.WOOD_ORE)) > 0)
				labels[11].setText("wood/ore: " + num);
			if (board.goods.containsKey(Resource.ORE_STONE)
					&& (num = board.goods.get(Resource.ORE_STONE)) > 0)
				labels[12].setText("ore/stone: " + num);

			if (board.goods.containsKey(Resource.PAPYRUS_GLASS_CLOTH)
					&& (num = board.goods.get(Resource.PAPYRUS_GLASS_CLOTH)) > 0)
				labels[13].setText("papyrus/glass/cloth: " + num);
			if (board.goods.containsKey(Resource.BRICK_STONE_ORE_WOOD)
					&& (num = board.goods.get(Resource.BRICK_STONE_ORE_WOOD)) > 0)
				labels[14].setText("brick/stone/ore/wood: " + num);

			if (board.goods.containsKey(Resource.COG)
					&& (num = board.goods.get(Resource.COG)) > 0)
				labels[15].setText("SCI:Cog: " + num);
			if (board.goods.containsKey(Resource.COMPASS)
					&& (num = board.goods.get(Resource.COMPASS)) > 0)
				labels[16].setText("SCI:Compass: " + num);
			if (board.goods.containsKey(Resource.TABLET)
					&& (num = board.goods.get(Resource.TABLET)) > 0)
				labels[17].setText("SCI:Tablet: " + num);

			if (board.goods.containsKey(Resource.SHEILD))
				num = board.goods.get(Resource.SHEILD);
			else
				num = 0;
			labels[18].setText("Sheilds: " + num);

			if (board.goods.containsKey(Resource.COIN))
				num = board.goods.get(Resource.COIN);
			else
				num = 0;
			labels[19].setText("Coins: " + num);

			if (board.goods.containsKey(Resource.VP))
				num = board.goods.get(Resource.VP);
			else
				num = 0;
			labels[20].setText("VPs: " + num);

		}
	} // end of BoardPanel class



} // end of HumanDialogBasedPlayer class

