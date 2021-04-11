import java.io.DataOutputStream;
import java.net.Socket;

public class ServerOutputThread implements Runnable
{
   private MessageOutput messageOutput;

   public ServerOutputThread(MessageOutput messageOutput)
   {
      this.messageOutput = messageOutput;
   }

   public MessageOutput getMessageOutput()
   {
      return messageOutput;
   }

   @Override
   public void run()
   {
      try{
         if(!messageOutput.getMessage().equals("")){
            System.out.println(messageOutput.getRecipient() + " " + messageOutput.getUsername() + messageOutput.getMessage());
            Socket s = messageOutput.getSocket();
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            dos.writeUTF(messageOutput.getRecipient() + " " + messageOutput.getUsername() + messageOutput.getMessage());
            dos.flush();
         }
      }
      catch (Exception e){

      }

   }
}
