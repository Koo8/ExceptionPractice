import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html
 *
 * When you start the client program, the server should already be running and
 * listening to the port, waiting for a client to request a connection. So,
 * the first thing the client program does is to open a socket that is connected
 * to the server running on the specified host name and port:
 */

public class GameClient {
    public static void main(String[] args) {
        // get hostName and portNumber from command line
        if (args.length != 2) {
            System.out.println("java GameClient <hostName> <portNumber>");
            System.exit(1);
        }
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        // create socket, write to server, read from server and read from user input
        try(
                Socket socket = new Socket(hostName, portNumber);
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader readFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedReader readFromUserInput = new BufferedReader(new InputStreamReader(System.in));
                ) {
            String serverReply, clientInput;
            while((serverReply = readFromServer.readLine()) != null) {
                System.out.println("Server: " + serverReply);
                if (serverReply.contains("OK")) break;

                if ((clientInput = readFromUserInput.readLine()) != null) {
                    System.out.println("Clients: " + clientInput);
                    writer.println(clientInput);
                }

            }

        } catch (UnknownHostException e) {
            System.out.println("HostName is unknown");
        } catch (IOException e) {
            System.out.println("IOException from Client");

        }
    }
}
