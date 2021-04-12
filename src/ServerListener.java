/* EE422C Assignment #4 submission by
 * Eralp Orkun
 * eao789
 * Lab Section: Unique #17110, (5-6:30pm Thursday)
 */

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * ServerListener class which handles creating socket and connecting with client
 *
 * @author Eralp Orkun
 */

public class ServerListener implements Runnable
{
   MessageOutput messageOutput;

   public ServerListener(MessageOutput messageOutput)
   {
      this.messageOutput = messageOutput;
   }

   @Override
   public void run()
   {
      Scanner messageScanner;
      Socket s = null;
      try
      {
         ServerSocket ss = messageOutput.getServerSocket(); //get server socket
         System.out.println("Looking for Client");
         s = ss.accept();//establishes connection
         messageOutput.setMoreThread(true);
         DataInputStream dis = new DataInputStream(s.getInputStream());
         System.out.println("Server connected to a client");
         while (s.isConnected() && !Thread.interrupted())
         { //loop as long as possible until interrupted or not connected
            messageScanner = new Scanner(dis.readUTF()); //waits here
            String username = messageScanner.next();
            username = "[" + username + "]";
            String message;
            String recipient = messageScanner.next();
            recipient = "[" + recipient + "]";
            if (messageScanner.hasNext())
            { //make sure will not cause error
               message = messageScanner.nextLine();
            }
            else
            {
               message = "";
            }
            messageOutput.setMessage(message); //update object
            messageOutput.setRecipient(recipient);
            messageOutput.setUsername(username);
            messageOutput.setSocket(s);
            messageOutput.setUpdated(true);
         }
         System.out.println("No active users, ending server thread");
         s.close(); //close socket
         messageOutput.setNumSockets(messageOutput.getNumSockets() - 1);
      }
      catch (Exception e)
      {
         System.out.println("No active users, ending server thread");
         try
         {
            s.close(); //close socket
         }
         catch (Exception myException)
         {
         }
         finally
         {
            messageOutput.setNumSockets(messageOutput.getNumSockets() - 1);
         }
      }
   }
}
