package br.com.drogariamenk.msdmcolaborador.infra.externos;

import br.com.drogariamenk.msdmcolaborador.aplicacao.exception.ViaCepForaDoArException;
import br.com.drogariamenk.msdmcolaborador.dominio.Endereco;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ApiExterna {

    public static Endereco consumindoViaCep (String numeroCep) throws ViaCepForaDoArException {
        try {
            URL url = new URL("https://viacep.com.br/ws/"+numeroCep+"/json/");
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));

            String cep = "";
            StringBuilder jsonCep = new StringBuilder();
            while ((cep = br.readLine()) != null) {
                jsonCep.append(cep);
            }

            Endereco endereco = new Gson().fromJson(
                    jsonCep.toString(), Endereco.class);

            return endereco;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Via cep falho ao conectar.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Via cep fora");
            throw new ViaCepForaDoArException("Via cep está fora do ar.");

        }
    }
}
