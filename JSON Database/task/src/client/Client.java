package client;
import com.google.gson.JsonObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client implements Runnable{
    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 7070;
    String requestType;
    String indexOfCell;
    String value;

    public Client(Parser parser) {
        this.requestType = parser.getRequestType();
        this.indexOfCell = parser.getIndex();
        this.value = parser.getIsSaving();
    }

    @Override
    public void run() {
        try(Socket socket = new Socket(InetAddress.getByName(ADDRESS), PORT);
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

            System.out.println("Client started!");

            Request request = new Request(requestType, indexOfCell, value);


            JsonObject json = new JsonObject();
            switch (request.getType()) {
                case "get", "delete" -> {
                    json.addProperty("type", requestType);
                    json.addProperty("key", request.getKey());
                }
                case "set" -> {
                    json.addProperty("type", requestType);
                    json.addProperty("key", request.getKey());
                    json.addProperty("value", request.getValue());
                }
                case "exit" -> json.addProperty("type", requestType);
            }

            output.writeUTF(json.toString());


            String receivedMessage = input.readUTF();

            System.out.println("Sent: " + json.toString()+ "\n" +
                    "Received: " + receivedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
