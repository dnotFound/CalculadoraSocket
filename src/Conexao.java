
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Conexao {
    
    //Output escreve o dado
    //Input lê o dado
    
    //Strem é um fluxo de dados, é a conexão entre origem e destino
    //onde a mensagem será passada via byte ou char
    
    public static void send(Socket socket, Object object) {
        ObjectOutputStream out;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(object);

        } catch (Exception e) {
            System.out.println("Problema no ObjectOutputStream: " + e.getMessage());
        }
    }

    public static Object receive(Socket socket) {
        ObjectInputStream in;
        Object object = null;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            object = in.readObject();
        } catch (Exception e) {
            System.out.println("Problema no InputStream: " + e.getMessage());
        }
        return object;
    }
}
