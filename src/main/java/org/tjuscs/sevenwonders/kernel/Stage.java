package org.tjuscs.sevenwonders.kernel;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Set;

/**
 * The Class Stage.
 */
public class Stage implements Cloneable, Buildable, Serializable {

    /**
     * The cost.
     */
    EnumMap<Resource, Integer> cost;

    /**
     * The goods.
     */
    EnumMap<Resource, Integer> goods;

    /**
     * The card.
     */
    Card card;

    /**
     * The stage num.
     */
    int stageNum;

    /**
     * The name.
     */
    String name;

    /**
     * The action.
     */
    Action action;

    /**
     * Instantiates a new stage.
     */
    public Stage() {
        cost = new EnumMap<Resource, Integer>(Resource.class);
        goods = new EnumMap<Resource, Integer>(Resource.class);
        action = null;
        name = "No Name";
    }

    /**
     * Instantiates a new stage.
     *
     * @param nm     the nm
     * @param stgNum the stg num
     */
    public Stage(String nm, int stgNum) {
        cost = new EnumMap<Resource, Integer>(Resource.class);
        goods = new EnumMap<Resource, Integer>(Resource.class);
        action = null;
        stageNum = stgNum;
        name = nm;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#clone()
     */
    public Object clone() {
        Stage stage = null;
        try {
            stage = (Stage) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return stage;
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
        str.append(" Cost: ");
        for (Resource r : costSet) {
            str.append(cost.get(r) + " " + r.toString() + "  ");
        }
        str.append("Provides: ");
        for (Resource r : goodsSet) {
            str.append(goods.get(r) + " " + r.toString() + "  ");
        }
        // str.append("");
        return str.toString();
    }

    /**
     * Sets the name.
     *
     * @param nm the new name
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
     * @param r the r
     * @param i the i
     */
    public void addCost(Resource r, int i) {
        cost.put(r, i);
    }

    /**
     * Adds the goods.
     *
     * @param r the r
     * @param i the i
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
     * Gets the stage number.
     *
     * @return the stage number
     */
    public int getStageNumber() {
        return stageNum;
    }

    /**
     * Gets the card.
     *
     * @return the card
     */
    public Card getCard() {
        return card;
    }

    /**
     * Sets the card.
     *
     * @param card the new card
     */
    public void setCard(Card card) {
        this.card = card;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.tjuscs.sevenwonders.core.Buildable#goodsCnt(org.tjuscs.sevenwonders
     * .core.Resource)
     */
    public int goodsCnt(Resource r) {
        //assert goods != null;//For Debug!!!
        Integer i = goods.get(r);
        return i == null ? 0 : i;
    }

    /**
     * Gets the action.
     *
     * @return the action
     */
    public Action getAction() {
        return action;
    }

    /**
     * Sets the action.
     *
     * @param action the new action
     */
    public void setAction(Action action) {
        this.action = action;
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

} // end of Stage class
