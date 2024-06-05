package cn.yizhimcqiu.pubg.verify;

import cn.yizhimcqiu.pubg.network.PubgSocketServerProcessor;
import net.minecraft.client.Minecraft;
import net.minecraft.util.HttpUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface UUIDVerify {
    boolean[] V = {false, false};
    List<String> ALLOWED_UUIDS = List.of(
            "380df991-f603-344c-a090-369bad2a924a",
            "038d4a77-200d-48c7-8217-20de0a4d313a");
    static boolean verify() {
        if (!V[0]) {
            V[0] = true;
            V[1] = PubgSocketServerProcessor.isUUIDPurchased(Minecraft.getInstance().getUser().getUuid()).equals("1");
        }
        return V[1];
    }
}
