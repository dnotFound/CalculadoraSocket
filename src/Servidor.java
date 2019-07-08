
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor extends Thread {

    static ServerSocket serversocket;
    static Socket client_socket;
    static Conexao c;
    
    public Servidor() {
        try {
            serversocket = new ServerSocket(9600);
            
            System.out.println("Calculadora distribuida no ar!!!");
            System.out.println("Aguardando cliente fazer requisiçao ...");
        } catch (Exception e) {
            System.out.println("Nao criou o server socket..." + e.getMessage());
        }
    }

    public static void main(String args[]) {
        
        new Servidor(); //inicia a conexão do Servidor
        
        Requisicao msgReq;  //obj Requisição
        Resposta msgRep;    //obj Resposta
        int visits = 0;
        
        while (true) {
            if (connect()) {
                msgReq = (Requisicao) c.receive(client_socket); //obj recebe o retorno do Socket Cliente
                                                                //espera-se que o cliente tenha imputado os dados
                                                                //para realizar os calculos

                char op = msgReq.getOperacao();
                if((op != '+') && (op != '-') && (op != '/') && (op != '*')){
                    System.out.println("Cliente não inseriu uma operação válida!! - " + "Operador: " + op);
                }else{
                System.out.println("Operacao " + op);
                }
                msgRep = new Resposta();    //inicia o obj Resposta

                //CASES PARA REALIZAR OS CÁLCULOS
                switch (op) {
                    case '+':
                        msgRep.setStatus(0);
                        msgRep.setResult(msgReq.getOp1() + msgReq.getOp2());
                        break;
                    case '-':
                        msgRep.setStatus(0);
                        msgRep.setResult(msgReq.getOp1() - msgReq.getOp2());
                        break;
                    case 'x':
                        msgRep.setStatus(0);
                        msgRep.setResult(msgReq.getOp1() * msgReq.getOp2());
                        break;
                    case '/':
                        if (msgReq.getOp2() == 0.0F) {  //divisão por 0
                            msgRep.setStatus(2);         //status para 2 
                        } else {
                            msgRep.setStatus(0);
                            msgRep.setResult(msgReq.getOp1() / msgReq.getOp2());
                        }
                        break;
                    default:                 //caso o cliente não escolha nenhuma operação
                        msgRep.setStatus(1); //status 1, op não realizada
                        break;
                }
                //CASES PARA REALIZAR OS CÁLCULOS
                
                c.send(client_socket, msgRep);  //envia o obj resposta para o cliente
                
            } else {
                try {
                    serversocket.close();
                    break;
                } catch (Exception e) {
                    System.out.println("Nao desconectei...");
                }
            }
        }
    }

    static boolean connect() { //verifica se a conexão foi aceita
        boolean ret;
        try {
            client_socket = serversocket.accept();  
            return true;
        } catch (Exception e) {
            System.out.println("Erro de connect..." + e.getMessage());
            return false;
        }
    }
}
