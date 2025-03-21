/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.anokata.socketserverdb;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
/**
 *
 * @author Na
 */
public class NetworkManager {
        public static boolean isNetworkAvailable(String host, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 2000); // Timeout de 2 secondes
            return true; // Connexion réussie
        } catch (IOException e) {
            return false; // Pas de connexion
        }
    }
}
