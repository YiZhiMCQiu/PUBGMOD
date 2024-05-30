package cn.yizhimcqiu.pubg.registries;

import cn.yizhimcqiu.pubg.PubgMod;
import cn.yizhimcqiu.pubg.blocks.TacticalShieldBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, PubgMod.MODID);
    public static final RegistryObject<Block> TACTICAL_SHIELD = BLOCKS.register("tactical_shield", () -> new TacticalShieldBlock(BlockBehaviour.Properties.copy(Blocks.IRON_DOOR).noOcclusion()));
//     public static final RegistryObject<Block> BUSHES = BLOCKS.register("bushes", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isSuffocating((a, b, c) -> false).isViewBlocking((a, b, c) -> false).ignitedByLava().pushReaction(PushReaction.DESTROY).isRedstoneConductor((a, b, c) -> false)));
}
