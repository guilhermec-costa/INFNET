package com.gildedrose.updater;

import com.gildedrose.Item;

/**
 * Atualizador para o item "Sulfuras, Hand of Ragnaros".
 * Este item lendário não tem sua qualidade alterada.
 */
public class SulfurasUpdater implements ItemUpdater {
    
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";

    @Override
    public void update(Item item) {
    }

    @Override
    public boolean canUpdate(Item item) {
        return SULFURAS.equals(item.name);
    }
}
