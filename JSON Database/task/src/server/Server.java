package server;

import client.Request;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 7070;

    public Server() {
    }

    @Override
    public void run() {
        try (ServerSocket server = new ServerSocket(PORT,
                50,
                InetAddress.getByName(ADDRESS))) {
            System.out.println("Server started!");

            while (true){
                Socket socket = server.accept();
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                String receivedMessage = input.readUTF();

                Gson receivedRequest = new Gson();
                Request request = receivedRequest.fromJson(receivedMessage, Request.class);


                Response response = UI.startWorking(request);
                JsonObject json = new JsonObject();
                switch (response.getType()) {
                    case "get" -> {
                        if (response.getResult().equals("ERROR")) {
                            json.addProperty("response", response.getResult());
                            json.addProperty("reason", response.getReason());
                        } else {
                            json.addProperty("response", response.getResult());
                            json.addProperty("value", response.getValue());
                        }
                    }
                    case "set" -> {
                        json.addProperty("response", response.getResult());
                    }
                    case "delete" -> {
                        if (response.getResult().equals("ERROR")) {
                            json.addProperty("response", response.getResult());
                            json.addProperty("reason", response.getReason());
                        } else {
                            json.addProperty("response", response.getResult());
                        }
                    }
                    case "exit" -> json.addProperty("response", response.getResult());
                }

                if (response.getType().equals("exit")) {
                    output.writeUTF(json.toString());
                    return;
                }

                output.writeUTF(json.toString());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
