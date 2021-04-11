import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain
{
   public static void main(String[] args)
   {
      try
      {
         Socket s = new Socket("localhost", 6666);
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

         while(true){

            if(myScanner.hasNext()){
               command = myScanner.next();
               message = myScanner.nextLine();
               if(command.equals("/quit")){
                  break;
               }
               if(command.equals("/message")){
                  if(message.equals("")){
                     System.out.println("[Error]: Enter a recipient and a message");
                  }
                  else{
                     dout.writeUTF(username + " " + message);
                     dout.flush();
                  }
               }
               else{
                  System.out.println("[Error]: Unknown Command");
               }
            }
         }


         dout.close();
         s.close();

      }
      catch (Exception e)
      {
         System.out.println(e);
      }
   }
}
