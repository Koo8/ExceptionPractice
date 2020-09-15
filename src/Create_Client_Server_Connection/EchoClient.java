package Create_Client_Server_Connection;

import java.io.*;
import java.net.*;

public class EchoClient {
    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.err.println(
                    "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
                 // the order of these resources are important because The Java runtime
        // closes these resources in reverse order that they were created.
        // (This is good because streams connected to a socket should be closed before the socket itself is closed.)
        try (   // getting a connection to the echo server by creating a socket
                Socket echoSocket = new Socket(hostName, portNumber);   //highlight should be the first resource to be created
                //To send data through the socket to the server, the EchoClient example needs to write to the PrintWriter.
                PrintWriter out =  // convert characters into bytes
                        new PrintWriter(echoSocket.getOutputStream(), true);
                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(echoSocket.getInputStream()));
                BufferedReader stdIn =
                        new BufferedReader(
                                new InputStreamReader(System.in))
        ) {
            String userInput;
            while ((userInput = stdIn.readLine()) != null) { // read user input from system
                out.println(userInput);   // write to server through socket - forward the user input to the server
                System.out.println("echo: " + in.readLine()); //  The server echoes the input data back through the socket to the client. The client program reads and displays the data
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }
}