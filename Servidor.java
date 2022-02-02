/*
 * Servidor.java
 *
 * Created on 17 de Maio de 2006, 11:27
 *
 * Servidor ECHO: fica em aguardo de solicitação de algum cliente. Quando recebe
 * simplesmente devolve a mensagem. Funcionamento: tiro unico
 */

import java.net.*;
import java.io.*;

public class Servidor {
    public static void main(String args[]) {
        DatagramSocket s = null;
        try {
            s = new DatagramSocket(6787); // cria um socket UDP
            byte[] buffer = new byte[1000];
            System.out.println("\n\n*** Servidor aguardando request");
            // cria datagrama para recepcionar solicitação do cliente
            DatagramPacket req = new DatagramPacket(buffer, buffer.length);
            s.receive(req);
            System.out.println("*** Request recebido de: " + req.getAddress()+":"+req.getPort());
            
            // envia resposta
            
            System.out.println("Questões recebidas" + new String(req.getData(), 0, req.getLength()));
            
            String questoes = new String(req.getData(), 0, req.getLength());
     
            String[] cortada = questoes.split(";");            
            String numero_questoes = cortada[0];
            String numero_alternativas = cortada[1];
            String questionario = cortada[2];
            
            String resposta = "1;5;VVVFF"; 
            String[] cortada2 = resposta.split(";");
            String numero_questoes1 = cortada2[0];
            String numero_alternativas1 = cortada2[1];
            String respostas = cortada2[2];
            
            char[] q = questionario.toCharArray();
            char[] g = respostas.toCharArray();
           
            int certo = 0;
            int errado = 0;
            boolean r = false;
           
            if (numero_questoes.equals(numero_questoes1)){
            	r = numero_alternativas.equals(numero_alternativas1);
            }if (r == true) {
                	for (int c = 0; c<g.length; c++) {
                		if (q[c] == g[c]) {
                			certo++;
                		}else 
       	            	 errado++;
	              }
            }
           

            String msg = new String(numero_questoes + ";" + certo + ";"+ errado);
            System.out.println(msg);
            
            byte []b = msg.getBytes();
            req.setData(b);
            
            DatagramPacket resp = new DatagramPacket(req.getData(), req.getLength(),
                    req.getAddress(), req.getPort());
            s.send(resp);
        } catch (SocketException e) {
            System.out.println("Erro de socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Erro envio/recepcao pacote: " + e.getMessage());
        } finally {
            if (s != null) s.close();
        }
    }
}
