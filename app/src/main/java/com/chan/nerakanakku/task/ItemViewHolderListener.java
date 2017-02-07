package com.chan.nerakanakku.task;

/**
 * Created by chandra-1765 on 7/21/16.
 */


public interface ItemViewHolderListener {

    /**
     * Called when the first registers an item as being moved or swiped.
     * Implementations should update the item view to indicate it's active state.
     */
    void onItemSelected();


    /**
     * Called when the has completed the move or swipe, and the active item
     * state should be cleared.
     */
    void onItemClear();
}
