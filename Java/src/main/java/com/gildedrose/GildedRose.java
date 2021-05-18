package com.gildedrose;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

class GildedRose {
    static final String AGED_BRIE = "Aged Brie";
    static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    static final String CONJURED = "Conjured";

    private static final List<String> SPECIAL_ITEMS = List.of(AGED_BRIE, BACKSTAGE_PASSES, SULFURAS);

    private static final Map<Predicate<Item>, Consumer<Item>> QUALITY_RULES = initQualityRules();

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }
    /**
     * Introduced a map of additive rules, which have a rule as a Predicate to test against, and a Consumer as an action.
     * This arrangement flattens the code, and eases further maintenance and addition of new goods with their custom rules
     * As a side-effect, some conditions may be tested more than once, thus some performance overhead is possible
     */
    private static Map<Predicate<Item>, Consumer<Item>> initQualityRules() {
        var qualityRules = new HashMap<Predicate<Item>, Consumer<Item>>();
        qualityRules.put(item -> (!SPECIAL_ITEMS.contains(item.name) && item.quality > 0), item -> item.quality--);
        qualityRules.put(item -> (!SPECIAL_ITEMS.contains(item.name) && item.quality > 0 && item.sellIn <= 0), item -> item.quality--);

        qualityRules.put(item -> (AGED_BRIE.equals(item.name) && item.quality < 50), item -> item.quality++);

        qualityRules.put(item -> (BACKSTAGE_PASSES.equals(item.name) && item.quality < 50 && item.sellIn > 0),  item -> item.quality++);
        qualityRules.put(item -> (BACKSTAGE_PASSES.equals(item.name) && item.quality < 50 && item.sellIn < 11 && item.sellIn > 0),  item -> item.quality++);
        qualityRules.put(item -> (BACKSTAGE_PASSES.equals(item.name) && item.quality < 50 && item.sellIn < 6 && item.sellIn > 0),  item -> item.quality++);
        qualityRules.put(item -> (BACKSTAGE_PASSES.equals(item.name) && item.sellIn <= 0),  item -> item.quality = 0);

        qualityRules.put(item -> (CONJURED.equals(item.name) && item.quality > 0), item -> item.quality--);
        qualityRules.put(item -> (CONJURED.equals(item.name) && item.quality > 0 && item.sellIn <= 0), item -> item.quality--);
        return qualityRules;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            processItem(items[i]);
        }
    }

    private void processItem(Item item) {
        QUALITY_RULES.forEach((predicate, consumer) -> {
            if (predicate.test(item)) {
                consumer.accept(item);
            }
        });
        // This test could also be implemented by a Predicate test, but since it is the only exceptional case, I decided, it is overkill at this point
        if (!SULFURAS.equals(item.name)) {
            item.sellIn--;
        }
    }
}