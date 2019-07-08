
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    static Conexao c;
    static Socket socket;

    public Cliente() {
        try {
            socket = new Socket("localhost", 9600);
            System.out.println("CONEXÃO EFETUADA COM SUCESSO"); //Confere se foi iniciado
        } catch (Exception e) {
            System.out.println("Nao consegui resolver o host...");
        }
    }

    public static void main(String[] args) {

        new Cliente();     //inicia a conexão do cliente
                
        float op1, op2;
        char oper;
        Scanner in = new Scanner(System.in);
      
        
        System.out.println("*********************************");
        System.out.println("***  CALCULADORA DISTRIBUIDA  ***");      //Confeitaria
        System.out.println("*********************************");

        
        //ENTRADA DE DADOS PARA O USUÁRIO
        System.out.println("Digite o primeiro numero");
        op1 = in.nextFloat();
        System.out.println("Digite o segundo numero");      
        op2 = in.nextFloat();
        System.out.println("Escolha uma operação");
        System.out.println("(+)SOMA (-)SUBTRACAO (x)MULTIPLICACAO (/)DIVISAO");
        oper = in.next().charAt(0);
        //ENTRADA DE DADOS PARA O USUÁRIO
             
        
        Requisicao msgReq = new Requisicao(op1, op2, oper); //obj de requisição passando os dados inseridos
        c.send(socket, msgReq);                             //chama método send passando socket e obj de requisição
                                                            //neste momento, a mensagem será enviada e processada pelo servidor
        Resposta msgRep = (Resposta) c.receive(socket);     //recebe o obj de resposta enviada pelo Servidor
        
        //VERIFICAÇÕES / EXCEÇÕES
        if (msgRep.getStatus() == 0) {                               
            System.out.println("Resultado = " + msgRep.getResult()); //retorna o resultado do obj Resposta
        } else if (msgRep.getStatus() == 1) {
            System.out.println("Operacao nao Implementada");         //se 1, op não realizada (exceção)       
            System.out.println("Cliente inseriu um operador inválido: " + oper);
        } else {
            System.out.println("Divisao por Zero");                  //se 2, divisão por 0, erro no imput do usuário
        }
        //VERIFICAÇÕES / EXCEÇÕES
        
        try {
            socket.close();                          //fecha o socket/requisição do Cliente
            System.out.println("SOCKET ENCERRADO");
        } catch (Exception e) {
            System.out.println("problemas ao fechar socket");
        }
    }
}
