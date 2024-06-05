package cn.yizhimcqiu.pubg.mixin.client.screen;

import cn.yizhimcqiu.pubg.PubgMod;
import cn.yizhimcqiu.pubg.screens.IJoinMultiplayerScreen;
import cn.yizhimcqiu.pubg.screens.ITitleScreen;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.BufferUploader;
import net.minecraft.SharedConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Shadow
    private Thread gameThread;
    @Shadow
    public ClientLevel level;
    @Shadow
    public LocalPlayer player;
    @Shadow
    public Screen screen;
    @Final
    @Shadow
    public MouseHandler mouseHandler;
    @Final
    @Shadow
    private Window window;
    @Shadow
    public boolean noRender;
    @Final
    @Shadow
    private SoundManager soundManager;
    @Shadow
    public abstract void updateTitle();

    @Inject(method = "setScreen", at = @At("HEAD"), cancellable = true)
    public void setScreen(Screen screen, CallbackInfo ci) {
        if (screen instanceof TitleScreen) {
            screen = new ITitleScreen();
        } else if (screen instanceof JoinMultiplayerScreen) {
            screen = new IJoinMultiplayerScreen(new ITitleScreen());
        }
        if (SharedConstants.IS_RUNNING_IN_IDE && Thread.currentThread() != this.gameThread) {
            PubgMod.LOGGER.error("setScreen called from non-game thread");
        }

        if (screen == null && this.level == null) {
            screen = new ITitleScreen();
        } else if (screen == null && this.player.isDeadOrDying()) {
            if (this.player.shouldShowDeathScreen()) {
                screen = new DeathScreen((Component)null, this.level.getLevelData().isHardcore());
            } else {
                this.player.respawn();
            }
        }

        net.minecraftforge.client.ForgeHooksClient.clearGuiLayers(Minecraft.getInstance());
        Screen old = this.screen;
        if (screen != null) {
            var event = new net.minecraftforge.client.event.ScreenEvent.Opening(old, screen);
            if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return;
            screen = event.getNewScreen();
        }

        if (old != null && screen != old) {
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.ScreenEvent.Closing(old));
            old.removed();
        }

        this.screen = screen;
        if (this.screen != null) {
            this.screen.added();
        }

        BufferUploader.reset();
        if (screen != null) {
            this.mouseHandler.releaseMouse();
            KeyMapping.releaseAll();
            screen.init(Minecraft.getInstance(), this.window.getGuiScaledWidth(), this.window.getGuiScaledHeight());
            this.noRender = false;
        } else {
            this.soundManager.resume();
            this.mouseHandler.grabMouse();
        }

        this.updateTitle();
        ci.cancel();
    }
}
