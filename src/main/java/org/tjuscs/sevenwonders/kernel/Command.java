package org.tjuscs.sevenwonders.kernel;

/**
 * The Enum Command.
 */
public enum Command {

    /**
     * Build Card command.
     */
    BUILD_CARD("Build Card"),

    /**
     * Build Stage command.
     */
    BUILD_STAGE("Build Stage"),

    /**
     * Sell Card command.
     */
    SELL_CARD("Sell Card"),

    /**
     * No Action command.
     */
    NONE("No Action:ERROR");

    /**
     * Instantiates a new command.
     *
     * @param s the description of the command
     */
    Command(String s) {
        str = s;
    }

    /**
     * The description of the command.
     */
    private String str;

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Enum#toString()
     */
    public String toString() {
        return str;
    }

}
