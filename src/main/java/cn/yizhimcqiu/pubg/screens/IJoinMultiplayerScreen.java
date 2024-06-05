package cn.yizhimcqiu.pubg.screens;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.network.chat.Component;

public class IJoinMultiplayerScreen extends JoinMultiplayerScreen {
    private static final ServerData PUBG_SERVER_DATA = new ServerData("官方PUBG服务器", "cn-hk-bgp-4.of-7af93c01.shop:43042", false);
    public IJoinMultiplayerScreen(Screen screen) {
        super(screen);
    }

    @Override
    protected void init() {
        this.addRenderableWidget(Button.builder(Component.translatable("selectServer.pubg"), (p_99728_) -> {
            ConnectScreen.startConnecting(this, Minecraft.getInstance(), ServerAddress.parseString(PUBG_SERVER_DATA.ip), PUBG_SERVER_DATA, false);
        }).pos(0, 0).build());
        super.init();
    }
}
