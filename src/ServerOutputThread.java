/* EE422C Assignment #4 submission by
 * Eralp Orkun
 * eao789
 * Lab Section: Unique #17110, (5-6:30pm Thursday)
 */

import java.io.DataOutputStream;
import java.net.Socket;

/**
 * ServerOutputThread class which handles sending messages when called by Main
 *
 * @author Eralp Orkun
 */

public class ServerOutputThread implements Runnable
{
   private final MessageOutput messageOutput;

   public ServerOutputThread(MessageOutput messageOutput)
   {
      this.messageOutput = messageOutput;
   }

   @Override
   public void run()
   {
      try
      {
         if (!messageOutput.getMessage().equals(""))
         { //send message if message contains a valid message
            Socket s = messageOutput.getSocket();
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            dos.writeUTF(messageOutput.getRecipient() + " "
               + messageOutput.getUsername() + messageOutput.getMessage());
            dos.flush();
         }
      }
      catch (Exception ignored)
      {
      }
   }
}
