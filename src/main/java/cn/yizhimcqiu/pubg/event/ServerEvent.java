package cn.yizhimcqiu.pubg.event;

import cn.yizhimcqiu.pubg.PubgMod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.DEDICATED_SERVER, modid = PubgMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ServerEvent {
}
