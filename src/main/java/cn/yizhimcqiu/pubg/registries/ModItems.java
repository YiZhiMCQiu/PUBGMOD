package cn.yizhimcqiu.pubg.registries;

import cn.yizhimcqiu.pubg.PubgMod;
import cn.yizhimcqiu.pubg.items.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PubgMod.MODID);
    public static final RegistryObject<Item> TEST_GUN = ITEMS.register("test_gun", () -> new GunItem(new Item.Properties().stacksTo(1), GunItem.GunType.AUTOMATIC_RIFLES));
    public static final RegistryObject<Item> TEST_SNIPER_GUN = ITEMS.register("test_sniper_gun", () -> new GunItem(new Item.Properties().stacksTo(1), GunItem.GunType.SNIPER_GUN));
    public static final RegistryObject<Item> GOLD_BRICK = ITEMS.register("gold_brick", () -> new SellableItem(new Item.Properties(), true));
    public static final RegistryObject<Item> BULLET = ITEMS.register("bullet", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TACTICAL_SHIELD = ITEMS.register("tactical_shield", () -> new BlockItem(ModBlocks.TACTICAL_SHIELD.get(), new Item.Properties()));
    // public static final RegistryObject<Item> BUSHES = ITEMS.register("bushes", () -> new BlockItem(ModBlocks.BUSHES.get(), new Item.Properties()));
}