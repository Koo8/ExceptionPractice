import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread{

    Socket socket;
    public ServerThread(Socket socket) {
        super("ServerThread");
        this.socket = socket;
    }

    @Override
    public void run() {
          try(
                  PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                  BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                  ){
              GameProtocal gameProtocal = new GameProtocal();
              String serverAnswer = gameProtocal.playWithClient(1);
              writer.println(serverAnswer);
              String clientInput;
              while ((clientInput = reader.readLine()) != null) {
                  serverAnswer = gameProtocal.playWithClient(Integer.parseInt(clientInput));
                  writer.println(serverAnswer);
                  if (serverAnswer.contains("OK")) {
                      break; // break the loop
                  }
              }

          } catch (IOException e) {
              System.out.println("PrintWriter getoutputstream has IOException");
          }
    }
}
