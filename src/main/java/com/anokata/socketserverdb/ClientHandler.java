package com.anokata.socketserverdb;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;
import com.anokata.socketserverdb.models.Etudiant;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;

    // Constructeur pour initialiser le Socket du client
    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (
            // Crée un ObjectInputStream pour lire les objets envoyés par le client
            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
            // Crée un ObjectOutputStream pour envoyer des objets vers le client
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream())
        ) {
            // Lire l'objet envoyé par le client
            Object obj = inputStream.readObject();

            // Traitement du message reçu (ex : ajout d'un étudiant à la base de données)
            if (obj instanceof Etudiant) {
                Etudiant etudiant = (Etudiant) obj;
                
                // Appel de DatabaseHandler pour ajouter l'étudiant à la base de données
                DatabaseHandler dbHandler = new DatabaseHandler();
                dbHandler.ajouterEtudiant(etudiant.getNumero(), etudiant.getNom(), etudiant.getAdresse(), etudiant.getBourse());

                // Envoi d'un message de confirmation au client
                outputStream.writeObject("Enregistrement ajouté");
            }
        } catch (IOException | ClassNotFoundException e) {
            // Affiche les erreurs s'il y en a
            
        } finally {
            try {
                // Fermer le socket une fois le traitement terminé
                if (clientSocket != null && !clientSocket.isClosed()) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                // Si l'on ne peut pas fermer le socket, affiche l'erreur
                
            }
        }
    }
}
