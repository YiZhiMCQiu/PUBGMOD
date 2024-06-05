package cn.yizhimcqiu.pubg.screens;

import cn.yizhimcqiu.pubg.network.PubgSocketServerProcessor;
import cn.yizhimcqiu.pubg.util.CopyUtil;
import cn.yizhimcqiu.pubg.verify.UUIDVerify;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.RealmsMainScreen;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.*;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.LevelSummary;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class ITitleScreen extends Screen {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String DEMO_LEVEL_ID = "Demo_World";
    public static final Component COPYRIGHT_TEXT = Component.literal("Copyright Mojang AB. Do not distribute!");
    public static final CubeMap CUBE_MAP = new CubeMap(new ResourceLocation("textures/gui/title/background/panorama"));
    private static final ResourceLocation PANORAMA_OVERLAY = new ResourceLocation("textures/gui/title/background/panorama_overlay.png");
    @Nullable
    private SplashRenderer splash;
    private final PanoramaRenderer panorama = new PanoramaRenderer(CUBE_MAP);
    private final boolean fading;
    private long fadeInStart;
    @Nullable
    private WarningLabel warningLabel;
    private final LogoRenderer logoRenderer;
    private boolean clicked = false;
    private net.minecraftforge.client.gui.TitleScreenModUpdateIndicator modUpdateNotification;

    public ITitleScreen() {
        this(false);
    }

    public ITitleScreen(boolean p_96733_) {
        this(p_96733_, (LogoRenderer)null);
    }

    public ITitleScreen(boolean p_265779_, @Nullable LogoRenderer p_265067_) {
        super(Component.translatable("narrator.screen.title"));
        this.fading = p_265779_;
        this.logoRenderer = Objects.requireNonNullElseGet(p_265067_, () -> {
            return new LogoRenderer(false);
        });
    }

    public static CompletableFuture<Void> preloadResources(TextureManager p_96755_, Executor p_96756_) {
        return CompletableFuture.allOf(p_96755_.preload(LogoRenderer.MINECRAFT_LOGO, p_96756_), p_96755_.preload(LogoRenderer.MINECRAFT_EDITION, p_96756_), p_96755_.preload(PANORAMA_OVERLAY, p_96756_), CUBE_MAP.preload(p_96755_, p_96756_));
    }

    public boolean isPauseScreen() {
        return false;
    }

    public boolean shouldCloseOnEsc() {
        return false;
    }

    protected void init() {
        if (this.splash == null) {
            this.splash = this.minecraft.getSplashManager().getSplash();
        }

        int i = this.font.width(COPYRIGHT_TEXT);
        int j = this.width - i - 2;
        int k = 24;
        int l = this.height / 4 + 48;
        Button modButton = this.addRenderableWidget(Button.builder(Component.translatable("fml.menu.mods"), button -> this.minecraft.setScreen(new net.minecraftforge.client.gui.ModListScreen(this)))
                .pos(this.width / 2 - 100, l + 24 * 2).size(98, 20).build());
        this.createNormalMenuOptions(l, 24, UUIDVerify.verify());

        this.addRenderableWidget(new ImageButton(this.width / 2 - 124, l + 72 + 12, 20, 20, 0, 106, 20, Button.WIDGETS_LOCATION, 256, 256, (p_280830_) -> {
            this.minecraft.setScreen(new LanguageSelectScreen(this, this.minecraft.options, this.minecraft.getLanguageManager()));
        }, Component.translatable("narrator.button.language")));
        this.addRenderableWidget(Button.builder(Component.translatable("menu.options"), (p_280838_) -> {
            this.minecraft.setScreen(new OptionsScreen(this, this.minecraft.options));
        }).bounds(this.width / 2 - 100, l + 72 + 12, 98, 20).build());
        this.addRenderableWidget(Button.builder(Component.translatable("menu.quit"), (p_280831_) -> {
            this.minecraft.stop();
        }).bounds(this.width / 2 + 2, l + 72 + 12, 98, 20).build());
        this.addRenderableWidget(new ImageButton(this.width / 2 + 104, l + 72 + 12, 20, 20, 0, 0, 20, Button.ACCESSIBILITY_TEXTURE, 32, 64, (p_280835_) -> {
            this.minecraft.setScreen(new AccessibilityOptionsScreen(this, this.minecraft.options));
        }, Component.translatable("narrator.button.accessibility")));
        this.addRenderableWidget(new PlainTextButton(j, this.height - 10, i, 10, COPYRIGHT_TEXT, (p_280834_) -> {
            this.minecraft.setScreen(new CreditsAndAttributionScreen(this));
        }, this.font));
        if (!UUIDVerify.verify()) {
            this.warningLabel = new WarningLabel(this.font, MultiLineLabel.create(this.font, Component.translatable("title.purchased.deprecation"), 350, 2), this.width / 2, l - 24);
        }

    }

    private void createNormalMenuOptions(int p_96764_, int p_96765_, boolean flag) {
        Tooltip tooltip = getGamePlayDisabledReason(!flag);
        (this.addRenderableWidget(Button.builder(Component.translatable("menu.singleplayer"), (p_280832_) -> {
            this.minecraft.setScreen(new SelectWorldScreen(this));
        }).bounds(this.width / 2 - 100, p_96764_, 200, 20).tooltip(tooltip).build())).active = flag;
        (this.addRenderableWidget(Button.builder(Component.translatable("menu.multiplayer"), (p_280833_) -> {
            Screen screen = new JoinMultiplayerScreen(this);
            this.minecraft.setScreen(screen);
        }).bounds(this.width / 2 - 100, p_96764_ + p_96765_, 200, 20).tooltip(tooltip).build())).active = flag;
        Button buyButton = (this.addRenderableWidget(Button.builder(Component.translatable("menu.buy"), (p_210872_) -> {
            this.clicked = true;
            CopyUtil.copy(Minecraft.getInstance().getUser().getName()+" "+Minecraft.getInstance().getUser().getUuid());
            p_210872_.setTooltip(getBuyDisabledReason(flag));
            p_210872_.active = false;
        }).bounds(this.width / 2 + 2, p_96764_ + p_96765_ * 2, 98, 20).tooltip(getBuyDisabledReason(flag)).build()));
        buyButton.active = !flag;
    }
    private Tooltip getGamePlayDisabledReason(boolean flag) {
        return flag ? Tooltip.create(Component.translatable("menu.game_play.reason")) : Tooltip.create(Component.empty());
    }
    private Tooltip getBuyDisabledReason(boolean flag) {
        return flag ? Tooltip.create(Component.translatable("menu.buy.reason")) : this.clicked ? Tooltip.create(Component.translatable("menu.buy.message")) : Tooltip.create(Component.empty());
    }
    private boolean checkDemoWorldPresence() {
        try {
            boolean flag;
            try (LevelStorageSource.LevelStorageAccess levelstoragesource$levelstorageaccess = this.minecraft.getLevelSource().createAccess("Demo_World")) {
                flag = levelstoragesource$levelstorageaccess.getSummary() != null;
            }

            return flag;
        } catch (IOException ioexception) {
            SystemToast.onWorldAccessFailure(this.minecraft, "Demo_World");
            LOGGER.warn("Failed to read demo world data", ioexception);
            return false;
        }
    }

    private void realmsButtonClicked() {
        this.minecraft.setScreen(new RealmsMainScreen(this));
    }

    public void render(GuiGraphics p_282860_, int p_281753_, int p_283539_, float p_282628_) {
        if (this.fadeInStart == 0L && this.fading) {
            this.fadeInStart = Util.getMillis();
        }

        float f = this.fading ? (float)(Util.getMillis() - this.fadeInStart) / 1000.0F : 1.0F;
        this.panorama.render(p_282628_, Mth.clamp(f, 0.0F, 1.0F));
        RenderSystem.enableBlend();
        p_282860_.setColor(1.0F, 1.0F, 1.0F, this.fading ? (float)Mth.ceil(Mth.clamp(f, 0.0F, 1.0F)) : 1.0F);
        p_282860_.blit(PANORAMA_OVERLAY, 0, 0, this.width, this.height, 0.0F, 0.0F, 16, 128, 16, 128);
        p_282860_.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        float f1 = this.fading ? Mth.clamp(f - 1.0F, 0.0F, 1.0F) : 1.0F;
        this.logoRenderer.renderLogo(p_282860_, this.width, f1);
        int i = Mth.ceil(f1 * 255.0F) << 24;
        if ((i & -67108864) != 0) {
            if (this.warningLabel != null) {
                this.warningLabel.render(p_282860_, i);
            }
            if (this.splash != null) {
                this.splash.render(p_282860_, this.width, this.font, i);
            }

            String s = "Minecraft " + SharedConstants.getCurrentVersion().getName();
            if (this.minecraft.isDemo()) {
                s = s + " Demo";
            } else {
                s = s + ("release".equalsIgnoreCase(this.minecraft.getVersionType()) ? "" : "/" + this.minecraft.getVersionType());
            }

            if (Minecraft.checkModStatus().shouldReportAsModified()) {
                s = s + I18n.get("menu.modded");
            }

            net.minecraftforge.internal.BrandingControl.forEachLine(true, true, (brdline, brd) ->
                    p_282860_.drawString(this.font, brd, 2, this.height - ( 10 + brdline * (this.font.lineHeight + 1)), 16777215 | i)
            );

            net.minecraftforge.internal.BrandingControl.forEachAboveCopyrightLine((brdline, brd) ->
                    p_282860_.drawString(this.font, brd, this.width - font.width(brd), this.height - (10 + (brdline + 1) * ( this.font.lineHeight + 1)), 16777215 | i)
            );


            for(GuiEventListener guieventlistener : this.children()) {
                if (guieventlistener instanceof AbstractWidget) {
                    ((AbstractWidget)guieventlistener).setAlpha(f1);
                }
            }
            super.render(p_282860_, p_281753_, p_283539_, p_282628_);
        }
    }

    private void confirmDemo(boolean p_96778_) {
        if (p_96778_) {
            try (LevelStorageSource.LevelStorageAccess levelstoragesource$levelstorageaccess = this.minecraft.getLevelSource().createAccess("Demo_World")) {
                levelstoragesource$levelstorageaccess.deleteLevel();
            } catch (IOException ioexception) {
                SystemToast.onWorldDeleteFailure(this.minecraft, "Demo_World");
                LOGGER.warn("Failed to delete demo world", (Throwable)ioexception);
            }
        }

        this.minecraft.setScreen(this);
    }

    @OnlyIn(Dist.CLIENT)
    static record WarningLabel(Font font, MultiLineLabel label, int x, int y) {
        public void render(GuiGraphics p_281783_, int p_281383_) {
            this.label.renderBackgroundCentered(p_281783_, this.x, this.y, 9, 2, 2097152 | Math.min(p_281383_, 1426063360));
            this.label.renderCentered(p_281783_, this.x, this.y, 9, 16777215 | p_281383_);
        }
    }
}
