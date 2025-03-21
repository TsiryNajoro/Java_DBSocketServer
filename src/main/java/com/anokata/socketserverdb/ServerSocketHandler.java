package com.anokata.socketserverdb;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketHandler {
    private int port;

    public ServerSocketHandler(int port) {
        this.port = port;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Serveur démarré sur le port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();  // Attendre les connexions des clients
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();  // Démarrer un thread pour chaque client
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
