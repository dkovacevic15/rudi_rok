package com.company;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Random;

public class Server {
    public static int PORT = 12345;

    public static void main(String[] args) {
        try (Selector selektor = Selector.open(); ServerSocketChannel sc = ServerSocketChannel.open()) {
            if (!selektor.isOpen() || !sc.isOpen()) {
                System.err.println("nije lepo otvoreno");
                System.exit(1);
            }
            sc.bind(new InetSocketAddress(PORT));
            sc.configureBlocking(false);
            sc.register(selektor, SelectionKey.OP_ACCEPT);

            while (true) {
                selektor.select();
                Iterator<SelectionKey> keys = selektor.selectedKeys().iterator();
                while (keys.hasNext()) {
                    SelectionKey kljuc = keys.next();
                    keys.remove();
                    if (kljuc.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) kljuc.channel();
                        SocketChannel client = server.accept();
                        System.out.println("Accepted connection from: " + client.getLocalAddress().toString());
                        client.configureBlocking(false);
                        client.register(selektor, SelectionKey.OP_WRITE);
                    } else if (kljuc.isWritable()) {
                        SocketChannel client = (SocketChannel) kljuc.channel();
                        Random r = new Random();
                        Integer br = r.nextInt();
                        String s = br.toString() + "\n";
                        ByteBuffer buffer = ByteBuffer.wrap(s.getBytes());
                        client.write(buffer);


                    }

                }


            }

        } catch (IOException ioe) {

        }
    }


    static class ConnectionData {
        private long lastMessageMilis;
        private int messagesSent;


    }
}
