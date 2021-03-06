package AIsocket;

import java.net.*;
import java.io.*;
import java.util.Arrays;
import java.util.Collections;

public class TCPServer {

    public static String getNextMove() {

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(5005);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 5005.");
            System.exit(1);
        }

        Socket clientSocket = null;
        System.out.println("Waiting for Stockfish.....");

        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        System.out.println("Connection successful");
        System.out.println("Waiting for input.....");
        String inputLine;
        String outPut = "";

        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),
                    true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            while ((inputLine = in.readLine()) != null) {
                System.out.println("Server: " + inputLine);
                out.println(inputLine);
                outPut = inputLine;

            }

            out.close();
            in.close();
            clientSocket.close();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return convertCoordinates(outPut);

    }

    private static String convertCoordinates(String move) {

        String str = "[" + getInt(move.charAt(0));

        for (int i = 1; i < move.length(); i++) {
            str += ", " + getInt(move.charAt(i));
        }

        str += "]";
        return str;
    }

    private static int getInt(char c) {
        if (Character.isLetter(c)) {
            return c - 'a';
        } else {
            return 7 - (c - '1');
        }
    }

    public static void main(String[] args) {
        getNextMove();


    }

}
