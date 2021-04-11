import java.io.DataInputStream;
import java.io.DataOutputStream;
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
      try
      {
         ServerSocket ss = messageOutput.getServerSocket();
         Socket s = ss.accept();//establishes connection
         messageOutput.setMoreThread(true);

         DataInputStream dis = new DataInputStream(s.getInputStream());
         System.out.println("Server connected to a client");
         while(s.isConnected()){
            System.out.println("Reading input");
            messageScanner = new Scanner((String) dis.readUTF()); //wait here
            String username = messageScanner.next();
            username = "[" + username + "]";
            String message;
            System.out.println("username= " + username);
            String recipient = messageScanner.next();
            recipient = "[" + recipient + "]";
            System.out.println("recipient= " + recipient);
            if(messageScanner.hasNext()){
               message = messageScanner.nextLine();
            }
            else{
               message = "";
            }
            System.out.println("message= " + message);

            messageOutput.setMessage(message);
            messageOutput.setRecipient(recipient);
            messageOutput.setUsername(username);
            messageOutput.setSocket(s);
            messageOutput.setUpdated(true);
         }


         ss.close();

      }
      catch (Exception e)
      {
         System.out.println(e);
         System.out.println("No active users, ending server thread");
      }
   }
}
