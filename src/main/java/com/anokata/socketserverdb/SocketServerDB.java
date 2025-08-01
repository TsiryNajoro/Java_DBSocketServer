package com.anokata.socketserverdb;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServerDB {
    public static void main(String[] args) {
        try {
            // Crée un serveur qui écoute sur le port 12345
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Serveur démarré, en attente de connexions...");

            // Accepte les connexions des clients dans une boucle infinie
            while (true) {
                // Accepter la connexion d'un client
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connecté : " + clientSocket.getInetAddress());

                // Créer un thread pour traiter chaque client
                // Le ClientHandler s'occupe de l'ajout, modification, ou suppression des étudiants
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
