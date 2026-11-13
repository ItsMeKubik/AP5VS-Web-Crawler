package utb.fai;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/*
    Tento program byl vytvořen na cvičení 9.11.
*/

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Client {
    public static void main(String[] args) {

        /* 
        String host = "www.google.com";
        int port = 80;

        try (Socket socket = new Socket(host,port)) {
            OutputStream out = socket.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.write("GET / HTTP/1.1\r\n".getBytes());
            out.write(("Host: " + host + "\r\n").getBytes());
            out.write("Connection: close\r\n".getBytes());
            out.write("\r\n".getBytes());
            out.flush();

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        String url = "https://www.utb.cz/";

        HttpClient client = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .build();

        try {
            HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200){
                
                if (response.body() == null){
                    return;
                }

                Document doc = Jsoup.parse(response.body());

                System.out.println(doc.title());
                Elements links = doc.select("a");
                for(Element e : links){
                    if(e.hasAttr("href")){
                        System.err.println(e.attr("href"));
                    }
                    
                }
                
            } else {
                System.out.println("Doslo k chybe v komunikaci. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }




    }
}
