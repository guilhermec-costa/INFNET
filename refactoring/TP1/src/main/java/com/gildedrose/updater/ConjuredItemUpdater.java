package com.gildedrose.updater;

import com.gildedrose.Item;

/**
 * Atualizador para itens "Conjured".
 * Itens Conjured diminuem a qualidade duas vezes mais rápido que os itens normais.
 */
public class ConjuredItemUpdater implements ItemUpdater {
    
    private static final String CONJURED = "Conjured";
    private static final int MIN_QUALITY = 0;
    private static final int DOUBLE_DECAY_RATE = 2;

    @Override
    public void update(Item item) {
        if (item.quality > MIN_QUALITY) {
            item.quality = item.quality - DOUBLE_DECAY_RATE;
            if (item.quality < MIN_QUALITY) {
                item.quality = MIN_QUALITY;
            }
        }
        
        item.sellIn = item.sellIn - 1;
        
        if (item.sellIn < 0) {
            if (item.quality > MIN_QUALITY) {
                item.quality = item.quality - DOUBLE_DECAY_RATE;
                if (item.quality < MIN_QUALITY) {
                    item.quality = MIN_QUALITY;
                }
            }
        }
    }

    @Override
    public boolean canUpdate(Item item) {
        return CONJURED.equals(item.name);
    }
}
