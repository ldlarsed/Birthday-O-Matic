package com.example.luke.tjener;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by luke on 11/3/15.
 */
public class ServerKlient implements Runnable {

    ServerSocket ss = null;
    Socket s = null;
    PrintWriter out = null;
    BufferedReader in = null;
    int PORT = 12345;

    public ServerKlient(Socket inn) {
        this.s = inn;
    }


    @Override
    public void run() {
        try {

            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(s.getOutputStream(), true);

            String inn = in.readLine();
            String[] tallene = inn.split(":");

            int tall1 = Integer.parseInt(tallene[0]);
            int tall2 = Integer.parseInt(tallene[1]);
            int sum = tall1 + tall2;
            out.println(Integer.toString(sum));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                out.close();
                in.close();
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.d("SERVER", "Tjener ble vekket av "+s);
        }
    }
}
