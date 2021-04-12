import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

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
         ServerSocket ss = messageOutput.getServerSocket();
         System.out.println("Looking for Client");
         s = ss.accept();//establishes connection
         messageOutput.setMoreThread(true);

         DataInputStream dis = new DataInputStream(s.getInputStream());
         System.out.println("Server connected to a client");
         while(s.isConnected() && !Thread.interrupted()){
            messageScanner = new Scanner((String) dis.readUTF()); //wait here
            String username = messageScanner.next();
            username = "[" + username + "]";
            String message;
            String recipient = messageScanner.next();
            recipient = "[" + recipient + "]";
            if(messageScanner.hasNext()){
               message = messageScanner.nextLine();
            }
            else{
               message = "";
            }

            messageOutput.setMessage(message);
            messageOutput.setRecipient(recipient);
            messageOutput.setUsername(username);
            messageOutput.setSocket(s);
            messageOutput.setUpdated(true);
         }

         System.out.println("No active users, ending server thread");
         s.close();
         messageOutput.setNumSockets(messageOutput.getNumSockets()-1);

      }
      catch (Exception e)
      {
         System.out.println("No active users, ending server thread");
         try
         {
            s.close();
         }
         catch (Exception myException)
         {
         }
         finally
         {
            messageOutput.setNumSockets(messageOutput.getNumSockets()-1);
         }
      }
   }
}
