package cn.yizhimcqiu.pubg.items;

import cn.yizhimcqiu.pubg.registries.ModAttributes;
import cn.yizhimcqiu.pubg.util.ItemPrices;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

public class SellableItem extends Item {
    private final boolean isFoil;
    public SellableItem(Properties properties) {
        this(properties, false);
    }

    public SellableItem(Properties properties, boolean isFoil) {
        super(properties);
        this.isFoil = isFoil;
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return this.isFoil;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            ItemStack stack = player.getItemInHand(hand);
            sell(player, stack);
            player.setItemInHand(hand, ItemStack.EMPTY);
            return InteractionResultHolder.success(stack);
        }
        return InteractionResultHolder.fail(player.getItemInHand(hand));
    }

    /**
     * 售卖玩家的物品
     * @param player 要售卖物品的玩家
     * @param stack 要售卖的net.minecraft.world.item.ItemStack
     */
    public void sell(Player player, ItemStack stack) {
        double price = ItemPrices.PRICES.get(stack.getItem()) * stack.getCount();
        player.sendSystemMessage(Component.translatable("text.sell.success", price));
        AttributeMap attr = player.getAttributes();
        AttributeInstance moneys = attr.getInstance(ModAttributes.MONEYS.get());
        if (moneys != null) {
            moneys.setBaseValue(moneys.getBaseValue() + price);
        }
        if (!Minecraft.getInstance().isSingleplayer()) {
            player.getServer().getPlayerList().getPlayers().forEach(serverPlayer -> serverPlayer.sendSystemMessage(Component.translatable("broadcast.player_selled_item", player.getName().getString(), price, moneys.getBaseValue())));
        }
    }
}
