package cn.yizhimcqiu.pubg.network;

import java.io.*;
import java.net.Socket;

public class PubgSocketServerProcessor {
    public static final String HOST = "cn-hk-bgp-4.of-7af93c01.shop";
    public static final int PORT = 61084;
    public static String isUUIDPurchased(final String uuid) {
        try {
            try (Socket socket = new Socket(HOST, PORT);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
            ) {
                System.out.println("Connected to server");
                out.println("2ee2844a-94d5-4ea3-8851-d0386ae0d0f0");
                out.println(uuid);

                return in.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }
}
