package cn.yizhimcqiu.pubg.util;

import cn.yizhimcqiu.pubg.registries.ModItems;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;

public class ItemPrices {
    public static final Map<Item, Double> PRICES = new HashMap<>();
    static {
        PRICES.put(ModItems.GOLD_BRICK.get(), 500000d);
    }
}
