package com.gildedrose.updater;

import com.gildedrose.Item;

/**
 * Atualizador para o item "Aged Brie".
 * Este item aumenta sua qualidade em 1 a cada dia.
 */
public class AgedBrieUpdater implements ItemUpdater {
    
    private static final String AGED_BRIE = "Aged Brie";
    private static final int MAX_QUALITY = 50;

    @Override
    public void update(Item item) {
        if (item.quality < MAX_QUALITY) {
            item.quality = item.quality + 1;
        }
        
        item.sellIn = item.sellIn - 1;
        
        if (item.sellIn < 0) {
            if (item.quality < MAX_QUALITY) {
                item.quality = item.quality + 1;
            }
        }
    }

    @Override
    public boolean canUpdate(Item item) {
        return AGED_BRIE.equals(item.name);
    }
}
