package cn.yizhimcqiu.pubg.verify;

import net.minecraft.CrashReport;
import net.minecraft.client.Minecraft;

import java.util.Random;

public class Crasher {
    public static void crash() {
        _crash();
    }
    private static void _crash() {
        boolean _crashKey = (new Random().nextInt(0, 114514) < 1);
        if (!_crashKey) {
            Minecraft.crash(new CrashReport("[PUBG] You haven't purchased the mod yet", new class_114514_()));
        } else {
            System.out.println("[PUBG] You're in luck! 1/114514!");
        }
    }
    private static class class_114514_ extends Exception {
        public class_114514_() {
            super("request(java.lang.List) not contains net.minecraft.client.Minecraft.getInstance().getUser().getUuid()");
        }
    }
}
