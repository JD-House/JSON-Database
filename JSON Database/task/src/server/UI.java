package server;

import client.Request;

import java.util.*;

public abstract class UI {
    //Commands

    private static final String GET = "get";
    private static final String SET = "set";
    private static final String DELETE = "delete";
    private static final String EXIT = "exit";

    //Data
    private static String KEY;
    private static String VALUE;
    private static String COMMAND;

    public static Response startWorking(Request request) {
        Response response = new Response();

        inputParser(request);
        switch (COMMAND) {
            case GET -> {
                response.setType("get");
                if (Database.getFromDatabase(KEY).equals("ERROR")) {
                    response.setResult("ERROR");
                    response.setReason("No such key");
                } else {
                    response.setValue(Database.getFromDatabase(KEY));
                    response.setResult("OK");
                }
            }
            case SET -> {
                response.setType("set");
                response.setResult(Database.addToDatabase(KEY, VALUE));
            }
            case DELETE -> {
                response.setType("delete");
                if (Database.getFromDatabase(KEY).equals("ERROR")) {
                    response.setResult("ERROR");
                    response.setReason("No such key");
                } else {
                    response.setResult(Database.deleteFromDatabase(KEY));
                    response.setResult("OK");
                }
            }
            case EXIT -> {
                response.setType("exit");
                response.setResult("OK");
            }
        }

        KEY = null;
        VALUE = null;
        COMMAND = null;

        return response;
    }

    private static void inputParser(Request request) {

        COMMAND = request.getType();
        KEY = request.getKey();
        VALUE = request.getValue();
    }




}
