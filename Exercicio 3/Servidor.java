import java.io.*;
import java.net.*;
 
public class Servidor {
 
    static final int PORTA = 48471; 
    static int totalClientes = 0;
 
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORTA);
        System.out.println("Servidor rodando na porta " + PORTA);
        System.out.println("Aguardando conexões...\n");
 
        while (true) {
            Socket socket = serverSocket.accept();
            totalClientes++;
            System.out.println("Novo cliente conectado! Total: " + totalClientes);
 
            Thread thread = new Thread(new ClienteHandler(socket, totalClientes));
            thread.start();
        }
    }
}
 
class ClienteHandler implements Runnable {
 
    private Socket socket;
    private int idCliente;
 
    public ClienteHandler(Socket socket, int idCliente) {
        this.socket = socket;
        this.idCliente = idCliente;
    }
 
    @Override
    public void run() {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter saida = new PrintWriter(socket.getOutputStream(), true);
 
            saida.println("Bem-vindo ao servidor! Você é o cliente #" + idCliente);
            saida.println("Digite QUIT para encerrar.\n");
 
            String mensagem;
 
            while (true) {
                mensagem = entrada.readLine();
 
                if (mensagem == null) {
                    System.out.println("Cliente #" + idCliente + " desconectou.");
                    break;
                }
 
                System.out.println("[Cliente #" + idCliente + "]: " + mensagem);
 
                if (mensagem.equalsIgnoreCase("QUIT")) {
                    saida.println("Até mais! Encerrando sua conexão.");
                    System.out.println("Cliente #" + idCliente + " encerrou o chat.");
                    break;
                }
 
                saida.println("[Servidor]: " + mensagem.toUpperCase());
            }
 
            socket.close();
 
        } catch (IOException e) {
            System.out.println("Erro com cliente #" + idCliente + ": " + e.getMessage());
        }
    }
}