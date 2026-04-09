import java.io.*;
import java.net.*;
 
public class Servidor {
 
    static final int PORTA = 48471; 
 
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORTA);
        System.out.println("Servidor aguardando conexão na porta " + PORTA + "...");
 
        Socket socket = serverSocket.accept();
        System.out.println("Cliente conectado: " + socket.getInetAddress());
 
        BufferedReader entrada   = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter    saida     = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader teclado   = new BufferedReader(new InputStreamReader(System.in));
 
        String mensagem;
 
        System.out.println("Chat iniciado! Digite QUIT para encerrar.\n");
 
        while (true) {
            mensagem = entrada.readLine();
            if (mensagem == null || mensagem.equalsIgnoreCase("QUIT")) {
                System.out.println("Cliente encerrou o chat.");
                saida.println("QUIT");
                break;
            }
            System.out.println("[Cliente]: " + mensagem);
 
            System.out.print("[Você]: ");
            mensagem = teclado.readLine();
            saida.println(mensagem);
            if (mensagem.equalsIgnoreCase("QUIT")) {
                System.out.println("Você encerrou o chat.");
                break;
            }
        }
 
        socket.close();
        serverSocket.close();
        System.out.println("Conexão encerrada.");
    }
}