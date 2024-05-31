package cn.yizhimcqiu.pubg.registries;

import cn.yizhimcqiu.pubg.PubgMod;
import cn.yizhimcqiu.pubg.items.GunItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PubgMod.MODID);
    public static final RegistryObject<CreativeModeTab> ITEM_TAB = TABS.register("item_tab", () ->
            CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.pubg.items"))
            .icon(() -> new ItemStack(ModItems.GOLD_BRICK.get()))
            .displayItems(ModTabs::buildItem)
            .build());
    public static ItemStack createGunItem(Item item) {
        ItemStack stack = new ItemStack(item);
        CompoundTag tag = new CompoundTag();
        tag.putInt(GunItem.LEVEL_IDENTIFY, 4);
        stack.setTag(tag);
        return stack;
    }
    private static void buildItem(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
        output.accept(ModItems.GOLD_BRICK.get());
        output.accept(ModItems.BULLET.get());
        output.accept(createGunItem(ModItems.TEST_GUN.get()));
        output.accept(createGunItem(ModItems.TEST_SNIPER_GUN.get()));
        // output.accept(ModItems.BUSHES.get());
    }
}
