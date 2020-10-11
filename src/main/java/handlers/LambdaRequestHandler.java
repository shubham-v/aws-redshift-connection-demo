package handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.redshift.Person;
import dao.redshift.RedshiftClusterClient;

import java.util.Map;

public class LambdaRequestHandler implements RequestHandler<Map<String, String>, String> {

    Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public String handleRequest(Map<String, String> event, Context context) {

        LambdaLogger logger = context.getLogger();
        String response = new String("200 OK");
        // log execution details
        logger.log("ENVIRONMENT VARIABLES: " + GSON.toJson(System.getenv()));
        logger.log("CONTEXT: " + GSON.toJson(context));
        // process event
        logger.log("EVENT: " + GSON.toJson(event));
        logger.log("EVENT TYPE: " + event.getClass().toString());

        Person person = GSON.fromJson(GSON.toJson(event), Person.class);

        RedshiftClusterClient.testConnection();

        return response;
    }

}