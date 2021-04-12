/* EE422C Assignment #4 submission by
 * Eralp Orkun
 * eao789
 * Lab Section: Unique #17110, (5-6:30pm Thursday)
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * ServerMain class which handles creating threads and sending messages
 *
 * @author Eralp Orkun
 */

public class ServerMain
{
   public static void main(String[] args) throws IOException
   {
      Thread latest = null;
      Map<String, Socket> activeUsers = new HashMap<>();
      ServerSocket ss = null;
      int counter = 0;
      try
      {
         ss = new ServerSocket(6666); //make server port
         MessageOutput messageOutput = new MessageOutput(ss);
         messageOutput.setMoreThread(true);
         messageOutput.setNumSockets(0);
         while (true) //serverMain loop, no waiting in this loop
         {
            if (messageOutput.moreThread())
            { //allocate a new server listener if needed
               messageOutput.setMoreThread(false);
               Runnable r = new ServerListener(messageOutput);
               Thread t = new Thread(r);
               t.start();
               messageOutput.setNumSockets(messageOutput.getNumSockets() + 1);
               counter = 0;
            }
            Thread.sleep(100); //delay for correct communication
            if (messageOutput.isUpdated())
            { //if new message then proceed
               messageOutput.setUpdated(false);
               if (messageOutput.getUsername().equals(messageOutput.getRecipient()))
               { //if first time connection, add to hashmap
                  activeUsers.put(messageOutput.getUsername(), messageOutput.getSocket());
               }

               messageOutput.setSocket(activeUsers.get(messageOutput.getRecipient()));

               Runnable r = new ServerOutputThread(messageOutput); //new thread
               Thread t = new Thread(r);
               t.start();

            }
            if (messageOutput.getNumSockets() == 1)
            { //check for timeout
               counter = counter + 1;
               if (counter > 100)
               {
                  latest.interrupt(); //interrupt if timesout
               }
            }
            if (messageOutput.getNumSockets() == 0)
            { //server timeout after last thread deleted
               System.out.println("Sever Timeout");
               ss.close(); //close server
            }
         }
      }
      catch (Exception ignored)
      {
      }
      finally
      {
         ss.close(); //make sure server is closed
         System.out.println("Sever Timeout");
      }
   }
}
