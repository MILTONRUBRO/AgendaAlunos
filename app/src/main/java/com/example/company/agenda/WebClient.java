package com.example.company.agenda;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by milton on 17/05/2018.
 */

public class WebClient {

    public String post(String json){


        try {
            //endereco do servidor
            URL url = new URL("https://www.caelum.com.br/mobile");

            //abre a conexao
            HttpURLConnection  connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            //escrever a saida para a conexao
            PrintStream output = new PrintStream(connection.getOutputStream());

            //escreve na requisao o que sera enviado ao servidor
            output.println(json);

            //conecta com o servidor
            connection.connect();

            //ler a resposta do servidor
            Scanner scanner = new Scanner(connection.getInputStream());
            String resposta = scanner.next();

            return resposta;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }
}
