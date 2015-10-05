package org.tjuscs.sevenwonders.kernel;

/**
 * The Class DiscountAction.
 */
class DiscountAction implements Action {

	/**
	 * The Enum DiscountType.
	 */
	enum DiscountType {

		/** The LEF t_ raw. */
		LEFT_RAW("left neighbor's raw goods"),

		/** The RIGH t_ raw. */
		RIGHT_RAW("right neighbor's raw goods"),

		/** The BOT h_ raw. */
		BOTH_RAW("both neighbor's raw goods"),

		/** The BOT h_ manuf. */
		BOTH_MANUF("both neighbor's manufactured goods");

		/**
		 * Instantiates a new discount type.
		 * 
		 * @param s
		 *            the s
		 */
		DiscountType(String s) {
			str = s;
		}

		/** The str. */
		private String str;

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Enum#toString()
		 */
		public String toString() {
			return str;
		}

	};

	/** The discount. */
	private DiscountType discount;

	/**
	 * Instantiates a new discount action.
	 * 
	 * @param discnt
	 *            the discnt
	 */
	public DiscountAction(DiscountType discnt) {
		discount = discnt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tjuscs.sevenwonders.core.Action#activate(org.tjuscs.sevenwonders.
	 * core.Board)
	 */
	public void activate(Board brd) {
		switch (discount) {
		case LEFT_RAW:
			brd.leftRawCost = 1;
			break;
		case RIGHT_RAW:
			brd.rightRawCost = 1;
			break;
		case BOTH_RAW:
			brd.leftRawCost = brd.rightRawCost = 1;
			break;
		case BOTH_MANUF:
			brd.manfCost = 1;
			break;
		default:
			System.out.println("Bad discount value in Discount Action");
		}
	} // end of activate method

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "gives discount on " + discount.toString();
	}
}
