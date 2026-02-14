package com.gildedrose.updater;

import com.gildedrose.Item;

/**
 * Atualizador padrão para itens regulares.
 * A qualidade diminui em 1 por dia, e em 2 após a data de venda.
 */
public class DefaultUpdater implements ItemUpdater {
    
    private static final int MIN_QUALITY = 0;
    private static final int DOUBLE_DECAY_THRESHOLD = 0;

    @Override
    public void update(Item item) {
        if (item.quality > MIN_QUALITY) {
            item.quality = item.quality - 1;
        }
        
        item.sellIn = item.sellIn - 1;
        
        if (item.sellIn < DOUBLE_DECAY_THRESHOLD) {
            if (item.quality > MIN_QUALITY) {
                item.quality = item.quality - 1;
            }
        }
    }

    @Override
    public boolean canUpdate(Item item) {
        return true;
    }
}
