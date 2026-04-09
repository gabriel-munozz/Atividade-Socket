import java.io.*;
import java.net.*;
 
public class Cliente {
 
    static final String IP_SERVIDOR = "127.0.0.1"; 
    static final int PORTA = 48471;    
 
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(IP_SERVIDOR, PORTA);
        System.out.println("Conectado ao servidor " + IP_SERVIDOR + ":" + PORTA + "\n");
 
        BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter    saida   = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
 
        Thread leitor = new Thread(() -> {
            try {
                String resposta;
                while ((resposta = entrada.readLine()) != null) {
                    System.out.println(resposta);
                }
            } catch (IOException e) {
                System.out.println("Conexão encerrada.");
            }
        });
        leitor.start();
 
        String mensagem;
        while (true) {
            mensagem = teclado.readLine();
            saida.println(mensagem);
            if (mensagem.equalsIgnoreCase("QUIT")) {
                break;
            }
        }
 
        socket.close();
        System.out.println("Você saiu do chat.");
    }
}