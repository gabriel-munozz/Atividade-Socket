import java.io.*;
import java.net.*;
 
public class Cliente {
 
    static final String IP_SERVIDOR = "127.0.0.1"; 
    static final int PORTA = 48471;
 
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(IP_SERVIDOR, PORTA);
        System.out.println("Conectado ao servidor " + IP_SERVIDOR + ":" + PORTA);
 
        BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter    saida   = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
 
        String mensagem;
 
        System.out.println("Chat iniciado! Digite QUIT para encerrar.\n");
 
        while (true) {
            
            System.out.print("[Você]: ");
            mensagem = teclado.readLine();
            saida.println(mensagem);
            if (mensagem.equalsIgnoreCase("QUIT")) {
                System.out.println("Você encerrou o chat.");
                break;
            }
 
            mensagem = entrada.readLine();
            if (mensagem == null || mensagem.equalsIgnoreCase("QUIT")) {
                System.out.println("Servidor encerrou o chat.");
                break;
            }
            System.out.println("[Servidor]: " + mensagem);
        }
 
        socket.close();
        System.out.println("Conexão encerrada.");
    }
}
 