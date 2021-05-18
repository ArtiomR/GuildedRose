package com.gildedrose;

import org.junit.jupiter.api.Test;

import static com.gildedrose.GildedRose.AGED_BRIE;
import static com.gildedrose.GildedRose.BACKSTAGE_PASSES;
import static com.gildedrose.GildedRose.SULFURAS;
import static org.assertj.core.api.Assertions.assertThat;

class GildedRoseTest {
    static final String REGULAR_GOODS = "regular goods";

    @Test
    void whenRegularItemIsAgedByOneDay_ThenQualityDegradesByOne_AndSellInIsDecreasedByOne() {
        var items = new Item[] { new Item(REGULAR_GOODS, 1, 5) };
        var app = new GildedRose(items);
        app.updateQuality();
        var updatedItem = app.items[0];
        assertThat(updatedItem.name).isEqualTo(REGULAR_GOODS);
        assertThat(updatedItem.quality).isEqualTo(4);
        assertThat(updatedItem.sellIn).isEqualTo(0);
    }

    @Test
    void whenRegularItemOfZeroQualityIsAgedByOneDay_ThenQualityRemainsZero() {
        var items = new Item[] { new Item(REGULAR_GOODS, 1, 0) };
        var app = new GildedRose(items);
        app.updateQuality();
        var updatedItem = app.items[0];
        assertThat(updatedItem.name).isEqualTo(REGULAR_GOODS);
        assertThat(updatedItem.quality).isEqualTo(0);
        assertThat(updatedItem.sellIn).isEqualTo(0);
    }

    @Test
    void whenRegularItemSellInDateIsPassed_ThenQualityDecreasesByTwo() {
        var items = new Item[] { new Item(REGULAR_GOODS, 0, 2) };
        var app = new GildedRose(items);
        app.updateQuality();
        var updatedItem = app.items[0];
        assertThat(updatedItem.name).isEqualTo(REGULAR_GOODS);
        assertThat(updatedItem.quality).isEqualTo(0);
        assertThat(updatedItem.sellIn).isEqualTo(-1);
    }

    @Test
    void whenAgedBrieIsAgedByOneDay_ThenQualityIncreasesByOne() {
        var items = new Item[] { new Item(AGED_BRIE, 1, 5) };
        var app = new GildedRose(items);
        app.updateQuality();
        var updatedItem = app.items[0];
        assertThat(updatedItem.name).isEqualTo(AGED_BRIE);
        assertThat(updatedItem.quality).isEqualTo(6);
    }

    @Test
    void whenAgedBrieOfMaximumQualityIsAgedByOneDay_ThenQualityRemainsTheSame() {
        var items = new Item[] { new Item(AGED_BRIE, 1, 50) };
        var app = new GildedRose(items);
        app.updateQuality();
        var updatedItem = app.items[0];
        assertThat(updatedItem.name).isEqualTo(AGED_BRIE);
        assertThat(updatedItem.quality).isEqualTo(50);
    }

    @Test
    void whenSulfurasIsAgedByOneDay_ThenQualityRemainsTheSame() {
        var items = new Item[] { new Item(SULFURAS, 1, 50) };
        var app = new GildedRose(items);
        app.updateQuality();
        var updatedItem = app.items[0];
        assertThat(updatedItem.name).isEqualTo(SULFURAS);
        assertThat(updatedItem.quality).isEqualTo(50);
    }

    @Test
    void whenBackstagePassesIsAgedByOneDayAndMoreThanTenDaysRemains_ThenQualityIncreasesByOne() {
        var items = new Item[] { new Item(BACKSTAGE_PASSES, 11, 1) };
        var app = new GildedRose(items);
        app.updateQuality();
        var updatedItem = app.items[0];
        assertThat(updatedItem.name).isEqualTo(BACKSTAGE_PASSES);
        assertThat(updatedItem.quality).isEqualTo(2);
    }

    @Test
    void whenBackstagePassesIsAgedByOneDayAndTenDaysOrLessRemains_ThenQualityIncreasesByOne() {
        var items = new Item[] { new Item(BACKSTAGE_PASSES, 10, 1) };
        var app = new GildedRose(items);
        app.updateQuality();
        var updatedItem = app.items[0];
        assertThat(updatedItem.name).isEqualTo(BACKSTAGE_PASSES);
        assertThat(updatedItem.quality).isEqualTo(3);
    }

    @Test
    void whenBackstagePassesIsAgedByOneDayAndFiveDaysOrLessRemains_ThenQualityIncreasesByOne() {
        var items = new Item[] { new Item(BACKSTAGE_PASSES, 5, 1) };
        var app = new GildedRose(items);
        app.updateQuality();
        var updatedItem = app.items[0];
        assertThat(updatedItem.name).isEqualTo(BACKSTAGE_PASSES);
        assertThat(updatedItem.quality).isEqualTo(4);
    }

    @Test
    void whenBackstagePassesSellInDatePasses_ThenQualityDropsToZero() {
        var items = new Item[] { new Item(BACKSTAGE_PASSES, 0, 50) };
        var app = new GildedRose(items);
        app.updateQuality();
        var updatedItem = app.items[0];
        assertThat(updatedItem.name).isEqualTo(BACKSTAGE_PASSES);
        assertThat(updatedItem.quality).isEqualTo(0);
    }
}
