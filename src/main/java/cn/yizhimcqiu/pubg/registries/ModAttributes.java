package cn.yizhimcqiu.pubg.registries;

import cn.yizhimcqiu.pubg.PubgMod;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, PubgMod.MODID);
    public static final RegistryObject<Attribute> MONEYS = ATTRIBUTES.register("moneys", () -> new RangedAttribute("moneys", 0, 0, 2147483647));
}
