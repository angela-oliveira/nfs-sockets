package br.edu.ifpb.gugawag.so.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Servidor2 {


	public static void main(String[] args) throws IOException {
        System.out.println("== Servidor ==");

        // Configurando o socket
        ServerSocket serverSocket = new ServerSocket(7011);
        Socket socket = serverSocket.accept();

        // pegando uma referência do canal de saída do socket. Ao escrever nesse canal, está se enviando dados para o
        // servidor
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        // pegando uma referência do canal de entrada do socket. Ao ler deste canal, está se recebendo os dados
        // enviados pelo servidor
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        // laço infinito do servidor
        while (true) {
            System.out.println("Cliente: " + socket.getInetAddress());

            String mensagem = dis.readUTF();
            
            System.out.println(mensagem);
            switch(mensagem) {
		    	case "readdir": {
		    		System.out.println("Lista de Nomes");

		            File diretorio = new File("/home/angela/pdist");
		            List<String> files = Arrays.asList(diretorio.listFiles())
		                    .stream()
		                    .map(file -> file.getName())
		                    .collect(Collectors.toList());
		            dos.writeUTF(files.toString());
		            break;
		    	}
		        case "rename": {
		            File rename = new File ("/home/angela/pdist/teste.txt");
		            File fileNew = new File("/home/angela/pdist/teste2.txt");
		          
		            rename.renameTo(fileNew);
                    dos.writeUTF("Arquivo renomeado");
		            break;
		        }
		        case "create": {
		            File diretorio = new File("/home/angela/pdist".concat("/create.txt"));
		            diretorio.createNewFile();
					dos.writeUTF("Arquivo Criado");
		            break;
		        }
		        case "remove": {
		            Path remove = Paths.get("/home/angela/pdist/teste2.txt");
		            if (Files.exists(remove)) {
			            Files.delete(remove);
						dos.writeUTF("Arquivo Deletado");
		            }
		            break;
		        }
            }

            dos.writeUTF("Li sua mensagem: " + mensagem);
        }
        /*
         * Observe o while acima. Perceba que primeiro se lê a mensagem vinda do cliente (linha 29, depois se escreve
         * (linha 32) no canal de saída do socket. Isso ocorre da forma inversa do que ocorre no while do Cliente2,
         * pois, de outra forma, daria deadlock (se ambos quiserem ler da entrada ao mesmo tempo, por exemplo,
         * ninguém evoluiria, já que todos estariam aguardando.
         */
    }
}
