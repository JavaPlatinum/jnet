package com.jbrown.jnet.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;

import com.jbrown.jnet.utils.KeysI;
import com.jbrown.jnet.utils.StringUtils;

public class JNetTester {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String serverAddress = "192.168.8.130";
        Socket s = new Socket(serverAddress, 22);

        PrintStream writer = new PrintStream(s.getOutputStream(), true);

        BufferedReader reader = new BufferedReader(
            new InputStreamReader( s.getInputStream(), "UTF-8"));


        //-----(1)------
        runCommand(reader, writer, String.format("%s\r", "ping"));
        //-------------

        //-----(2)------
        runCommand(reader, writer, String.format("%s\r", "who"));
        //-------------


        runCommand(reader, writer, String.format("%s\r", "quit"));

        System.out.println("**Jnet Test done!! **");

        writer.close();
        reader.close();
        s.close();

        System.exit(0);
    }

    static void runCommand(BufferedReader reader, PrintStream writer,
        String cmd) throws IOException, ClassNotFoundException{
      cmd = String.format("%s\r", "who");
      writer.printf(cmd);
      writer.flush();

      String answer = read(reader);
      System.out.printf("Command = %s \n Result = %s", cmd, answer);
    }

    private static String read(BufferedReader reader) throws IOException {
      StringBuilder builder = new StringBuilder();
      String aux = "";

      while ((aux = reader.readLine()) != null) {
          String resp = aux.trim();
          if(resp.equalsIgnoreCase("END")){
            break;
          }

          if(StringUtils.isEmpty(resp) || resp.equalsIgnoreCase(KeysI.PROMPT_K1)){
            continue;
          }

          builder.append(aux);
      }

      return builder.toString();
    }
}