package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static int PORT = 12345;

    public static void main(String[] args) {
        // write your code here
        String hostname = "localhost";
        BufferedReader buf = null;
        try (Socket soket = new Socket(hostname, PORT)) {
            System.out.println("Socket otvoren");
            buf = new BufferedReader(new InputStreamReader(soket.getInputStream()));
            while (true) {
                System.out.println("Cekam da procitam");
                String procitano = buf.readLine();
                System.out.println(procitano);
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (buf != null) {
                try {
                    buf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
