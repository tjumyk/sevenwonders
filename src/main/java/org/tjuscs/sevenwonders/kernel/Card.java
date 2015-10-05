package org.tjuscs.sevenwonders.kernel;

import java.io.Serializable;
import java.util.*;

/**
 * The Class Card.
 */
public class Card implements Cloneable, Buildable ,Serializable{

	/** The cost. */
	EnumMap<Resource, Integer> cost;

	/** The goods. */
	EnumMap<Resource, Integer> goods;

	/** The free list. */
	ArrayList<String> freeList;

	/** The name. */
	String name;

	/** The color. */
	CardColor color;

	CommandOption cmd;
	/** The age. */
	int age;
	
	int coinNeed = 0;

	/** The player number. */
	int playerNumber;

	/** The action. */
	Action action;

	/**
	 * Instantiates a new card.
	 */
	public Card() {
		cmd = new CommandOption();
		cmd.setCard(this);
		cost = new EnumMap<Resource, Integer>(Resource.class);
		goods = new EnumMap<Resource, Integer>(Resource.class);
		freeList = null;
		action = null;
		coinNeed = 0;
		name = "No Name";
	}

	/**
	 * Instantiates a new card.
	 * 
	 * @param nm
	 *            the nm
	 * @param age
	 *            the age
	 * @param plynum
	 *            the plynum
	 * @param clr
	 *            the clr
	 */
	public Card(String nm, int age, int plynum, CardColor clr) {
		cmd = new CommandOption();
		cmd.setCard(this);
		cost = new EnumMap<Resource, Integer>(Resource.class);
		goods = new EnumMap<Resource, Integer>(Resource.class);
		freeList = null;
		action = null;
		name = nm;
		this.age = age;
		//coinNeed = cost.get(Resource.COIN);
		playerNumber = plynum;
		color = clr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public Object clone() {
		Card card = null;
		try {
			card = (Card) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return card;
	}
	
	int getCoin(){
		if(cost.containsKey(Resource.COIN))
			return cost.get(Resource.COIN);
		else 
			return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder str = new StringBuilder(name);
		Set<Resource> costSet = cost.keySet();
		Set<Resource> goodsSet = goods.keySet();
		str.append("(AGE:" + age + " P#:" + playerNumber + " " + color + ")");
		str.append("\t  Cost: ");
		for (Resource r : costSet) {
			str.append(cost.get(r) + " " + r.toString() + "  ");
		}
		str.append("  Provides: ");
		for (Resource r : goodsSet) {
			str.append(goods.get(r) + " " + r.toString() + "  ");
		}
		if (this.hasAction())
			str.append(this.getAction().toString());
		// str.append("\n");
		return str.toString();
	}

	public int getCoinNeed() {
		return coinNeed;
	}

	public void setCoinNeed(int coinNeed) {
		this.coinNeed = coinNeed;
	}

	/**
	 * Adds the to free list.
	 * 
	 * @param str
	 *            the str
	 */
	public void addToFreeList(String str) {
		if (freeList == null)
			freeList = new ArrayList<String>();
		freeList.add(str);
	}

	/**
	 * Gets the freelist iter.
	 * 
	 * @return the freelist iter
	 */
	public Iterator<String> getFreelistIter() {
		if (freeList == null)
			freeList = new ArrayList<String>();
		return freeList.iterator();
	}

	public ArrayList<String> getFreeList() {
		return freeList;
	}

	public void setFreeList(ArrayList<String> freeList) {
		this.freeList = freeList;
	}

	public CommandOption getCmd() {
		return cmd;
	}

	public void setCmd(CommandOption cmd) {
		this.cmd = cmd;
	}

	/**
	 * Sets the name.
	 * 
	 * @param nm
	 *            the new name
	 */
	public void setName(String nm) {
		name = nm;
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Adds the cost.
	 * 
	 * @param r
	 *            the r
	 * @param i
	 *            the i
	 */
	public void addCost(Resource r, int i) {
		cost.put(r, i);
	}

	/**
	 * Adds the goods.
	 * 
	 * @param r
	 *            the r
	 * @param i
	 *            the i
	 */
	public void addGoods(Resource r, int i) {
		goods.put(r, i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tjuscs.sevenwonders.core.Buildable#getGoods()
	 */
	public Set<Resource> getGoods() {
		return goods.keySet();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tjuscs.sevenwonders.core.Buildable#goodsCnt(org.tjuscs.sevenwonders
	 * .core.Resource)
	 */
	public int goodsCnt(Resource r) {
		int cnt = 0;
		if (goods.containsKey(r))
			cnt = goods.get(r);
		return cnt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tjuscs.sevenwonders.core.Buildable#getCosts()
	 */
	public Set<Resource> getCosts() {
		return cost.keySet();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.tjuscs.sevenwonders.core.Buildable#costCnt(org.tjuscs.sevenwonders
	 * .core.Resource)
	 */
	public int costCnt(Resource r) {
		return cost.get(r);
	}

	/**
	 * Gets the age.
	 * 
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * Sets the age.
	 * 
	 * @param age
	 *            the new age
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * Sets the action.
	 * 
	 * @param act
	 *            the new action
	 */
	public void setAction(Action act) {
		action = act;
	}

	/**
	 * Gets the action.
	 * 
	 * @return the action
	 */
	public Action getAction() {
		return action;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tjuscs.sevenwonders.core.Buildable#hasAction()
	 */
	public boolean hasAction() {
		if (action == null)
			return false;
		else
			return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tjuscs.sevenwonders.core.Buildable#hasResources()
	 */
	public boolean hasResources() {
		if (action == null)
			return true;
		else
			return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tjuscs.sevenwonders.core.Buildable#hasOrResources()
	 */
	public boolean hasOrResources() {
		for (Resource r : getGoods()) {
			if (r.index() == -1)
				return true;
		}
		return false;
	}

	/**
	 * Checks if is free to build.
	 * 
	 * @return true, if is free to build
	 */
	public boolean isFreeToBuild() {
		for (Resource r : getCosts()) {
			if (r == Resource.FREE)
				return true;
		}
		return false;
	}

	/**
	 * Gets the color.
	 * 
	 * @return the color
	 */
	public CardColor getColor() {
		return color;
	}

	/**
	 * Gets the player number.
	 * 
	 * @return the player number
	 */
	public int getplayerNumber() {
		return playerNumber;
	}

	/**
	 * Sets the player number.
	 * 
	 * @param playNum
	 *            the new player number
	 */
	public void setplayerNumber(int playNum) {
		this.playerNumber = playNum;
	}

} // end of Card class
