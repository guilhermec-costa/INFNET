package com.gildedrose.updater;

import com.gildedrose.Item;

/**
 * Atualizador para o item "Backstage Pass".
 * A qualidade aumenta conforme a data de venda se aproxima:
 * - Normal: +1
 * - 10 dias ou menos: +2
 * - 5 dias ou menos: +3
 * - Após o concerto: 0
 */
public class BackstagePassUpdater implements ItemUpdater {
    
    private static final String BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert";
    private static final int MAX_QUALITY = 50;
    private static final int TEN_DAYS = 10;
    private static final int FIVE_DAYS = 5;

    @Override
    public void update(Item item) {
        if (item.quality < MAX_QUALITY) {
            item.quality = item.quality + 1;
        }
        
        if (item.sellIn <= TEN_DAYS && item.quality < MAX_QUALITY) {
            item.quality = item.quality + 1;
        }
        
        if (item.sellIn <= FIVE_DAYS && item.quality < MAX_QUALITY) {
            item.quality = item.quality + 1;
        }
        
        item.sellIn = item.sellIn - 1;
        
        if (item.sellIn < 0) {
            item.quality = 0;
        }
    }

    @Override
    public boolean canUpdate(Item item) {
        return BACKSTAGE_PASS.equals(item.name);
    }
}
