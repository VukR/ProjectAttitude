package com.projectattitude.projectattitude.Controllers;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.projectattitude.projectattitude.Objects.FollowRequest;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by henry on 3/26/2017.
 */

public class ElasticSearchRequestController {

    private static ElasticSearchRequestController instance = new ElasticSearchRequestController();

    public ElasticSearchRequestController() {
    }

    /**
     * gets instance of the db
     * @return instance
     */
    public static ElasticSearchRequestController getInstance() {
        return instance;
    }

    private static JestDroidClient client;
    //points to server
    private static String DATABASE_URL = "http://cmput301.softwareprocess.es:8080/";
    //type of object stored on DB
    private static final String TYPE = "FollowRequest";
    //index on server
    private static final String INDEX = "cmput301w17t12";

    //add user to ES DB
    public static class AddRequestTask extends AsyncTask<FollowRequest, Void, Boolean> {

        @Override
        protected Boolean doInBackground(FollowRequest...search_parameters){
            verifySettings();

            boolean requestExist = false;
            Index index = new Index.Builder(search_parameters[0]).index(INDEX).type(TYPE).build();

            try {
                DocumentResult result = client.execute(index);

                if (result.isSucceeded()){
                    requestExist = true;
                    search_parameters[0].setID(result.getId());
                    Log.d("Request", "added");
                }
            }
            catch (IOException e) {
                Log.d("Error", "The application failed to build and send the User");
            }

            return requestExist;
        }
    }

    //search for requests in DB, and return list of followrequests or null
    public static class GetRequestsTask extends AsyncTask<String, Void, ArrayList<FollowRequest>> {

        @Override
        protected ArrayList<FollowRequest> doInBackground(String... search_parameters) { //enter requestee's username
            verifySettings();

            ArrayList<FollowRequest> requests = null;

            String query = "{\n" +
                    "    \"id\": \"myTemplateId\"," +
                    "    \"params\": {\n" +
                    "        \"requestee\" : \""+search_parameters[0]+"\"" + //requestee's username
                    "    }\n" +
                    "}";

            Search search = new Search.TemplateBuilder(query)
                    .addIndex(INDEX)
                    .addType(TYPE)
                    .build();

            try {
                SearchResult result = client.execute(search);
                if(result.isSucceeded()){
                    Log.d("Error", "Success getting request");
                    List<FollowRequest> foundRequests = result.getSourceAsObjectList(FollowRequest.class);
                    requests.addAll(foundRequests);
                }
                else{
                    Log.d("Error", "Elasticsearch was not able to get requests.");
                }
            }
            catch (IOException e) {
                Log.i("Error", "The application failed to connect to DB");
            }
            return requests;
        }
    }

    public static class DeleteRequestTask extends AsyncTask<FollowRequest, Void, Boolean>{

        @Override
        protected Boolean doInBackground(FollowRequest... search_parameters){
            verifySettings();

            Delete delete = new Delete.Builder(search_parameters[0].getID()).index(INDEX).type(TYPE).id(search_parameters[0].getID()).build();

            try{
                client.execute(delete);
                Log.d("Error", "Request deleted.");
            }
            catch(IOException e) {
                Log.d("Error", "Elasticsearch failed.");
            }
            return true;
        }
    }

    //copied from lonelytwitter
    private static void verifySettings(){
        if(client == null){
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder(DATABASE_URL);
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}
