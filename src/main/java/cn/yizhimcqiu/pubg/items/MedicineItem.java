package cn.yizhimcqiu.pubg.items;

import cn.yizhimcqiu.pubg.util.MathUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MedicineItem extends Item {
    private final int useDuration;
    private final int rHealth;
    private final int x;
    public MedicineItem(Properties properties, int useDuration, int rHealth) {
        this(properties, useDuration, rHealth, 100);
    }
    public MedicineItem(Properties properties, int useDuration, int rHealth, int x) {
        super(properties);
        this.useDuration = useDuration;
        this.rHealth = rHealth;
        this.x = x;
    }

    public int getrHealth() {
        return rHealth;
    }

    public int getX() {
        return x;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return this.useDuration;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        return super.use(p_41432_, p_41433_, p_41434_);
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int i) {
        if (MathUtil.getHealthPercent(entity) > x && entity instanceof Player player) {
            player.getCooldowns().addCooldown(this, 1);
            player.sendSystemMessage(Component.translatable("medicine.cant_eat", x+"%"));
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level p_41410_, LivingEntity entity) {
        if (MathUtil.getHealthPercent(entity) + this.rHealth > entity.getMaxHealth()) {
            entity.setHealth(entity.getMaxHealth());
        } else {
            entity.setHealth((float) (this.rHealth / 100.0 * entity.getMaxHealth()));
        }
        stack.shrink(1);
        return stack;
    }
}
