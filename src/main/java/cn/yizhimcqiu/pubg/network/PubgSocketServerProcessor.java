package cn.yizhimcqiu.pubg.network;

import cn.yizhimcqiu.pubg.PubgMod;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class PubgSocketServerProcessor {
    public static final String HOST = "cn-hk-bgp-4.of-7af93c01.shop";
    public static final int PORT = 61084;
    public static String isUUIDPurchased(final String uuid) {
        try {
            try (Socket socket = new Socket(HOST, PORT);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
            ) {
                PubgMod.LOGGER.info("Connected to the VerifyServer");
                out.println("2ee2844a-94d5-4ea3-8851-d0386ae0d0f0");
                out.println(uuid);

                return in.readLine();
            }
        } catch (IOException e) {
            PubgMod.LOGGER.error("cn.yizhimcqiu.pubg.network.PubgSocketServerProcessor.java:24: VerifyServer is not turned on, returned \"1\"");
            return "1";
        }
    }
}
