package cn.yizhimcqiu.pubg;

import cn.yizhimcqiu.pubg.registries.*;
import com.mojang.logging.LogUtils;
import cpw.mods.jarhandling.impl.Jar;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(PubgMod.MODID)
public class PubgMod {
    public static final String MODID = "pubg";
    public static final Logger LOGGER = LogUtils.getLogger();
    public PubgMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::commonSetup);

        ModItems.ITEMS.register(bus);
        ModBlocks.BLOCKS.register(bus);
        ModEntityTypes.ENTITY_TYPES.register(bus);
        ModTabs.TABS.register(bus);
        ModAttributes.ATTRIBUTES.register(bus);

        MinecraftForge.EVENT_BUS.register(this);
        bus.addListener(this::addCreative);
        bus.addListener(this::registerRenderers);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }
    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }
    private void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // event.registerEntityRenderer(ModEntityTypes.CAT_LADY.get(), CatLadyRenderer::new);
    }
}
