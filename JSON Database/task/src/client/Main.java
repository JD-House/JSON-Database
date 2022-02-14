package client;

import com.beust.jcommander.JCommander;

public class Main {


    public static void main(String[] args) {
        Parser parser = new Parser();
        JCommander.newBuilder()
                .addObject(parser)
                .build()
                .parse(args);
        Client client = new Client(parser);
        client.run();
    }
}
