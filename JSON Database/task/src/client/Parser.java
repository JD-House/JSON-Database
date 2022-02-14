package client;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Parameter(names = {"-t"}, description = "type of the request", required = true)
    private String requestType;

    @Parameter(names = {"-k"}, description = "index of the cell", required = false)
    private String index;

    @Parameter(names = {"-v"}, description = "to save value in database", variableArity = true, required = false)
    private List<String> isSaving = new ArrayList<>();

    public String getRequestType() {
        return requestType.isEmpty() ? "" : requestType;
    }
    public String getIndex() {
        return index == null ? "" : index;
    }

    public String getIsSaving() {
        StringBuilder result = new StringBuilder("");
        for (String s: isSaving) {
            result.append(s).append(" ");
        }
        return result.toString().trim();
    }
}
