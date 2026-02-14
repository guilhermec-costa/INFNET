package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testes automatizados para o sistema Gilded Rose.
 * Validam os comportamentos esperados após a refatoração.
 */
class GildedRoseTest {

    @Test
    void agedBrieIncreasesQualityUntilMax50() {
        Item[] items = new Item[] { new Item("Aged Brie", 2, 0) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        assertEquals(1, app.getItems()[0].quality);
        
        app.updateQuality();
        assertEquals(2, app.getItems()[0].quality);
    }

    @Test
    void agedBrieDoesNotExceedMaxQuality() {
        Item[] items = new Item[] { new Item("Aged Brie", 2, 49) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        assertEquals(50, app.getItems()[0].quality);
        
        app.updateQuality();
        assertEquals(50, app.getItems()[0].quality);
    }

    @Test
    void sulfurasDoesNotChange() {
        Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", 0, 80) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        assertEquals(80, app.getItems()[0].quality);
        assertEquals(0, app.getItems()[0].sellIn);
        
        app.updateQuality();
        assertEquals(80, app.getItems()[0].quality);
    }

    @Test
    void backstagePassZeroQualityAfterSellIn() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        assertEquals(0, app.getItems()[0].quality);
    }

    @Test
    void backstagePassIncreasesQuality() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        assertEquals(21, app.getItems()[0].quality);
        
        app.updateQuality();
        assertEquals(22, app.getItems()[0].quality);
    }

    @Test
    void backstagePassIncreasesBy2When10DaysOrLess() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        assertEquals(22, app.getItems()[0].quality);
    }

    @Test
    void backstagePassIncreasesBy3When5DaysOrLess() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        assertEquals(23, app.getItems()[0].quality);
    }

    @Test
    void conjuredItemLosesQualityTwiceAsFast() {
        Item[] items = new Item[] { new Item("Conjured", 10, 10) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        assertEquals(8, app.getItems()[0].quality);
    }

    @Test
    void conjuredItemLosesDoubleQualityAfterExpired() {
        Item[] items = new Item[] { new Item("Conjured", 0, 10) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        assertEquals(6, app.getItems()[0].quality);
    }

    @Test
    void defaultItemLosesQualityNormally() {
        Item[] items = new Item[] { new Item("Elixir of the Mongoose", 10, 10) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        assertEquals(9, app.getItems()[0].quality);
        
        app.updateQuality();
        assertEquals(8, app.getItems()[0].quality);
    }

    @Test
    void defaultItemLosesDoubleQualityAfterExpired() {
        Item[] items = new Item[] { new Item("Elixir of the Mongoose", 0, 10) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        assertEquals(8, app.getItems()[0].quality);
    }

    @Test
    void qualityCannotBeNegative() {
        Item[] items = new Item[] { new Item("Elixir of the Mongoose", 0, 0) };
        GildedRose app = new GildedRose(items);
        
        app.updateQuality();
        assertEquals(0, app.getItems()[0].quality);
    }

}
