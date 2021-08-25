package br.edu.ifpb.gugawag.so.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente2 {

    public static void main(String[] args) throws IOException {
        System.out.println("== Cliente ==");

		System.out.println("Escolha uma opção\n" +
                "readdir: devolve o conteúdo de um diretório (lista de nomes)\n" +
                "rename: renomeia um arquivo\n" +
                "create: cria um arquivo \n" +
                "remove: remove um arquivo\n");
                
        // configurando o socket
        Socket socket = new Socket("127.0.0.1", 7011);
        // pegando uma referência do canal de saída do socket. Ao escrever nesse canal, está se enviando dados para o
        // servidor
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        // pegando uma referência do canal de entrada do socket. Ao ler deste canal, está se recebendo os dados
        // enviados pelo servidor
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        // laço infinito do cliente
        while (true) {
            Scanner teclado = new Scanner(System.in);
            // escrevendo para o servidor
            dos.writeUTF(teclado.nextLine());

            // lendo o que o servidor enviou
            String mensagem = dis.readUTF();
            System.out.println("Servidor falou: " + mensagem);
        }
    }
}
