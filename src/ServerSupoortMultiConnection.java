import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSupoortMultiConnection {
    public static void main(String[] args) {
      if (args.length != 1) {
          System.out.println("java ServerSupportMultiConnection <portNumber>");
          System.exit(1);
      }

      int portNumber = Integer.parseInt(args[0]);
      try(
              //create serverSocket
              ServerSocket serverSocket = new ServerSocket(portNumber);

              ) {
          // constantly listen to new connection, if there is one start a new thread
          while(true) {
              new ServerThread(serverSocket.accept()).run();
          }
      } catch (IOException e) {
          System.out.println("serverSupportMiltiConnection has I/O Exception");
      }
    }
}
