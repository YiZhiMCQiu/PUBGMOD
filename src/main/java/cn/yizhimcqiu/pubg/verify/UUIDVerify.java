package cn.yizhimcqiu.pubg.verify;

import cn.yizhimcqiu.pubg.network.PubgSocketServerProcessor;
import net.minecraft.client.Minecraft;
import net.minecraft.util.HttpUtil;

import java.util.List;
import java.util.UUID;

public interface UUIDVerify {
    List<String> ALLOWED_UUIDS = List.of(
            "380df991-f603-344c-a090-369bad2a924a",
            "038d4a77-200d-48c7-8217-20de0a4d313a");
    static boolean verify() {
        return PubgSocketServerProcessor.isUUIDPurchased(Minecraft.getInstance().getUser().getUuid()).equals("1");
    }
    static void handle() {
        if (!verify()) {
            Crasher.crash();
        }
    }
}
