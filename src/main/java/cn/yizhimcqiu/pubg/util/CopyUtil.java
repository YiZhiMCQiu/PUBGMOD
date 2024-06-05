package cn.yizhimcqiu.pubg.util;

import net.minecraft.client.Minecraft;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class CopyUtil {
    public static void copy(String s) {
        Minecraft.getInstance().keyboardHandler.setClipboard(s);
    }
}
