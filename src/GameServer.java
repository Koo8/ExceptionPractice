import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
/**
 *  https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html
 *
 *  This server can only connect with one client, if another client trys to connect with it
 *   the other client will have I/O Exception thrown.
 *   check ServerSupportMultiConnection.java
 */


public class GameServer {
    static GameProtocal gameProtocal = new GameProtocal();

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("java GameServer <portNumber>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);
        try (
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                // use PrintWriter so that println() can be called to write to a file or a socket, since the inputstream is read by each line.
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader readFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String serverAnswer = gameProtocal.playWithClient(1);
            writer.println(serverAnswer);
            String clientInput;
            while ((clientInput = readFromServer.readLine()) != null) {
                serverAnswer = gameProtocal.playWithClient(Integer.parseInt(clientInput));
                writer.println(serverAnswer);
                if (serverAnswer.contains("OK")) {
                    break; // break the loop
                }
            }


        } catch (IOException e) {
            System.out.println("IOException from Server");
        }
    }
}
