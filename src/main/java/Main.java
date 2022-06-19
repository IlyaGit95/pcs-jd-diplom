import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws Exception {

        try (ServerSocket serverSocket = new ServerSocket(8989)) {
            BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
            String word;
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            while (true) {
                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                ) {
                    word = in.readLine().toLowerCase();
                    out.println(gson.toJson(engine.search(word)));
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}