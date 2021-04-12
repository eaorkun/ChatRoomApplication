/* EE422C Assignment #4 submission by
 * Eralp Orkun
 * eao789
 * Lab Section: Unique #17110, (5-6:30pm Thursday)
 */

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * ClientMain class which handles Connecting with server, username,
 * and command checking
 *
 * @author Eralp Orkun
 */
public class ClientMain
{
   public static void main(String[] args)
   {
      try
      {
         Socket s = new Socket("localhost", 6666); //Create socket
         DataOutputStream dout = new DataOutputStream(s.getOutputStream());
         Scanner myScanner = new Scanner(System.in);
         System.out.print("Enter your username: ");
         String username = myScanner.nextLine();
         System.out.println("Message users with: /message <username> <message>");
         System.out.println("Quit chat with: /quit");
         System.out.println();
         String command = "";
         String message = "";
         Runnable r = new ClientListener(s, username);
         Thread t = new Thread(r);
         t.start();
         dout.writeUTF(username + " " + username + " " + "Connected to Chatroom");
         dout.flush();
         while (true)
         { //check for input
            if (myScanner.hasNext())
            {
               command = myScanner.next(); //get command
               message = myScanner.nextLine(); //get message
               if (command.equals("/quit"))
               { //end if /quit
                  break;
               }
               if (command.equals("/message"))
               { //send message to server
                  if (message.equals(""))
                  {
                     System.out.println("[Error]: Enter a recipient and a message");
                  }
                  else
                  {
                     dout.writeUTF(username + " " + message);
                     dout.flush(); //write
                  }
               }
               else
               {
                  System.out.println("[Error]: Unknown Command");
               }
            }
         }
         dout.close();
         s.close();
      }
      catch (Exception ignored)
      {
      }
   }
}
