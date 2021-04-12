/* EE422C Assignment #4 submission by
 * Eralp Orkun
 * eao789
 * Lab Section: Unique #17110, (5-6:30pm Thursday)
 */

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * ClientListener class which listens for message directed at Client
 *
 * @author Eralp Orkun
 */
public class ClientListener implements Runnable
{
   private final Socket socket;
   private boolean loop;
   String clientUsername;

   public ClientListener(Socket s, String username)
   {
      socket = s;
      clientUsername = "[" + username + "]";
      loop = true;
   }

   @Override
   public void run()
   {

      while (loop)
      {
         try
         {
            DataInputStream din = new DataInputStream(socket.getInputStream());
            Scanner threadScanner = new Scanner(din.readUTF()); //read server input
            String recipient = threadScanner.next();
            if (recipient.equals(clientUsername))
            { //if intended recipient
               System.out.println(threadScanner.nextLine()); //print message
            }
         }
         catch (IOException e)
         {
            loop = false; //end thread
         }
      }
   }
}
