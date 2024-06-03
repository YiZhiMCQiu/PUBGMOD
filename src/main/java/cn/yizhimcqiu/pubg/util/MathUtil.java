package cn.yizhimcqiu.pubg.util;

import net.minecraft.world.entity.LivingEntity;

public class MathUtil {
    public static float getHealthPercent(LivingEntity entity) {
        return entity.getHealth() / entity.getMaxHealth() * 100;
    }
}
