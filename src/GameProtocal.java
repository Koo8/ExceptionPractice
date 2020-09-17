import java.awt.desktop.SystemSleepEvent;

public class GameProtocal {
    static final int WAIT = 0;
    static final int START = 1;
    static final int END = 2;
    static int STATE = WAIT;
    String serverReply;


    public String playWithClient(int clientInput) {
       if(STATE == WAIT ) {
          serverReply = "Let's start the game. 100 if you want to stop ";
          STATE = START;
       }else if (STATE == START) {
           if (clientInput != 100 ) {
              serverReply =  String.valueOf(clientInput *2) + " Give me next number...";

           } else{
               serverReply = "Do you want to play again?  please type \"0\" for yes and for \"1\" for no";
               STATE = END;
           }
       }else if (STATE == END) {
           if (clientInput == 0 ) {
               serverReply = "Good, let's start again. Give a a number...";
               STATE = START;
           } else {
               serverReply = "OK. See you again.";
           }
       }
       return serverReply;

    }
}
