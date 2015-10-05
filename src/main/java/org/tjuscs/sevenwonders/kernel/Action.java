package org.tjuscs.sevenwonders.kernel;

import java.io.Serializable;

/**
 * The Interface Action.<br>
 * Action接口
 */
interface Action extends Serializable {

    /**
     * Activate the action on the specific board.<br>
     * 在指定的奇迹（板）上激活该Action。
     *
     * @param brd the target board。目标奇迹（板）
     */
    public void activate(Board brd);

    /**
     * To string.
     *
     * @return the string
     */
    public String toString();
}
