/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Erycles Junio
 */
public class Recebedor implements Runnable {
    
    public final static String FILE_RECEIVED = "C:\\Users\\user\\Desktop\\arquivos_to_receive\\code_received7.txt";
    public final static int FILE_SIZE = 6022386;
    private InputStream servidor;

    public Recebedor(InputStream servidor) {
        this.servidor = servidor;
    }

    @Override
    public void run() {
        
        int bytesRead;
        int current = 0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        
        try {
          // recebe arquivo em bytes
          byte [] bytearray  = new byte [FILE_SIZE];
          InputStream input = this.servidor;
          fos = new FileOutputStream(FILE_RECEIVED);
          
          /*memórias são otimizados para trabalhar com blocos de dados.
          Para isso, existem classes como BufferedInputStream e BufferedOutputStream que utilizam o 
          padrão de projetos decorator para "decorar" um stream e automaticamente gerenciar a leitura
          e escrita em blocos através de um buffer interno.*/
          
          bos = new BufferedOutputStream(fos);
          bytesRead = input.read(bytearray,0,bytearray.length);//Lendo os bytes
          current = bytesRead; //Quantidade de bytes lidos

          do {
             bytesRead = input.read(bytearray, current, (bytearray.length-current));
             if(bytesRead >= 0) current += bytesRead;
          } while(bytesRead > -1);

          bos.write(bytearray, 0 , current);
          bos.flush();
          System.out.println("Arquivo " + FILE_RECEIVED
              + " foi baixado (" + current + " bytes lidos)");
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }        
        finally {
            try {
                if (fos != null) fos.close();
                if (bos != null) bos.close();
                //if (this.servidor != null) this.servidor.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        // recebe msgs do servidor e imprime na tela
        /*Scanner s = new Scanner(this.servidor);
        while (s.hasNextLine()) {
            System.out.println(s.nextLine());
        }*/
    }
}