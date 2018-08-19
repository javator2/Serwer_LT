package com.sdacademy.serwer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
public class Main {

    static volatile int i = 0;

    public static void main(String[] args) throws IOException {
        int portNumber = 5555;
        System.out.println("Slucham na porcie: " + portNumber);
        ServerSocket serverSocket = new ServerSocket(portNumber);

        while (true){
            Socket clientSocket = serverSocket.accept();
            new Thread(() -> {
                if (clientSocket.isConnected()) {
                    System.out.println("Nowy klient: " + clientSocket.getRemoteSocketAddress().toString());
                    System.out.println("HOST: " + clientSocket.getInetAddress().getHostName());
                    System.out.println("Numer klienta: " + i);
                    i++;
                }
                PrintWriter out = null;
                try {
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BufferedReader in = null;
                try {
                    in = new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream()));
                    String request, response;
                    while ((request = in.readLine()) != null) {
                        response = processRequest(request);
                        out.println(response);
                        if ("Done".equals(request)) {
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
    public static String processRequest(String request) {
        System.out.println("Server receive message from > " + request);
        return request;
    }
}