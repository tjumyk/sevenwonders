package org.tjuscs.sevenwonders.kernel;

import java.io.Serializable;

/**
 * The Enum Resource.
 */
public enum Resource implements Serializable {

    /**
     * The BRICK.
     */
    BRICK("brick", "B", 1, null),

    /**
     * The ORE.
     */
    ORE("ore", "O", 2, null),

    /**
     * The STONE.
     */
    STONE("stone", "S", 3, null),

    /**
     * The WOOD.
     */
    WOOD("wood", "W", 4, null),

    /**
     * The CLOTH.
     */
    CLOTH("cloth", "C", 5, null),

    /**
     * The GLASS.
     */
    GLASS("glass", "G", 6, null),

    /**
     * The PAPYRUS.
     */
    PAPYRUS("papyrus", "P", 7, null),

    /**
     * The COG.
     */
    COG("cog", "Cog", 8, null),

    /**
     * The COMPASS.
     */
    COMPASS("compass", "Cmp", 9, null),

    /**
     * The TABLET.
     */
    TABLET("tablet", "Tab", 10, null),

    /**
     * The SHEILD.
     */
    SHEILD("sheild", "MP", 0, null),

    /**
     * The COIN.
     */
    COIN("coins", "$", 0, null),

    /**
     * The VP.
     */
    VP("victory points", "VP", 0, null),

    /**
     * The FREE.
     */
    FREE("free", "", 0, null),
    //
    // // either/or resources
    /**
     * The WOO d_ brick.
     */
    WOOD_BRICK("wood/brick", "w/b", -1, new Resource[]{BRICK, WOOD}),

    /**
     * The STON e_ brick.
     */
    STONE_BRICK("stone/brick", "s/b", -1, new Resource[]{BRICK, STONE}),

    /**
     * The BRIC k_ ore.
     */
    BRICK_ORE("brick/ore", "b/o", -1, new Resource[]{BRICK, ORE}),

    /**
     * The STON e_ wood.
     */
    STONE_WOOD("stone/wood", "s/w", -1, new Resource[]{STONE, WOOD}),

    /**
     * The WOO d_ ore.
     */
    WOOD_ORE("wood/ore", "w/o", -1, new Resource[]{ORE, WOOD}),

    /**
     * The OR e_ stone.
     */
    ORE_STONE("ore/stone", "o/s", -1, new Resource[]{STONE, ORE}),
    //
    // either/or non-tradable
    /**
     * The PAPYRU s_ glas s_ cloth.
     */
    PAPYRUS_GLASS_CLOTH("papyrus/glass/cloth", "p/g/c", -1, new Resource[]{
            PAPYRUS, GLASS, CLOTH}),

    /**
     * The BRIC k_ ston e_ or e_ wood.
     */
    BRICK_STONE_ORE_WOOD("brick/stone/ore/wood", "b/s/o/w", -1, new Resource[]{
            BRICK, WOOD, ORE, STONE});

    /**
     * Instantiates a new resource.
     *
     * @param s     the s
     * @param c     the c
     * @param ind   the ind
     * @param types the types
     */
    Resource(String s, String c, int ind, Resource[] types) {
        str = s;
        abr = c;
        difTypes = types;
        index = ind;
    }

    /**
     * The str.
     */
    private String str;

    /**
     * The index.
     */
    private int index;

    /**
     * The dif types.
     */
    private Resource[] difTypes;

    /**
     * The abr.
     */
    private String abr;

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Enum#toString()
     */
    public String toString() {
        return str;
    }

    /**
     * Abbrev.
     *
     * @return the string
     */
    public String abbrev() {
        return abr;
    }

    /**
     * Gets the optional res.
     *
     * @return the optional res
     */
    public Resource[] getOptionalRes() {
        return difTypes;
    }

    /**
     * Index.
     *
     * @return the int
     */
    public int index() {
        return index;
    }
}
