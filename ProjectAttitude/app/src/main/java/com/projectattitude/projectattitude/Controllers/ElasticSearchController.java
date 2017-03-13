/*
 * MIT License
 *
 * Copyright (c) 2017 CMPUT301W17T12
 * Authors rsauveho vuk bfleyshe henrywei cs3
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.projectattitude.projectattitude.Controllers;

import android.os.AsyncTask;
import android.util.Log;

import com.projectattitude.projectattitude.Objects.Mood;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by Vuk on 3/6/2017.
 */

/**
 * THIS SHOULD BE DEPRECATED FOR NOW AND NEVER USED OR REFERENCED TO, MOODS ARE NOW STORED IN AN ARRAY
 * INSIDE THE USER OBJECT
 */

public class ElasticSearchController {

    private static JestDroidClient client;

    //points to server
    private static String DATABASE_URL = "http://cmput301.softwareprocess.es:8080/";

    //index on server
    private static final String INDEX = "cmput301w17t12";

    //type of object stored on DB
    private static final String TYPE = "mood";

    private static final String REQUEST = "request";



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

    /**
     * Adds user to db
     */
    public static class AddMoodsTask extends AsyncTask<Mood, Void, Void>{

        @Override

        protected Void doInBackground(Mood...moods){
            verifySettings();

            for(Mood mood : moods) {
                //Log.d("Mood here", mood.getEmotionState());
                Index index = new Index.Builder(mood).index(INDEX).type(TYPE).build();
                try {
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        mood.setId(result.getId());
                        Log.d("mood with id added", mood.getEmotionState());
                    } else {
                        Log.d("Error", "Elasticsearch was not able to add the mood");
                    }

                } catch (IOException e) {
                    Log.d("Error", "The application failed to build and send the tweets");
                }
            }
            return null;
        }
    }

    /**
     * Gets user by id (username)
     */
    public static class GetMoodsTask extends AsyncTask<String, Void, ArrayList<Mood>> {
        @Override
        protected ArrayList<Mood> doInBackground(String... search_parameters){
            verifySettings();

            ArrayList<Mood> moods = new ArrayList<Mood>();

            String query;
            query = search_parameters[0];

            Search search = new Search.Builder(query)
                    .addIndex(INDEX)
                    .addType(TYPE)
                    .build();

            try{
                SearchResult result = client.execute(search);
                if(result.isSucceeded()){
                    List<Mood> foundMoods = result.getSourceAsObjectList(Mood.class);
                    moods.addAll(foundMoods);
                    Log.d("moods return from DB", moods.toString());
                }
                else{
                    Log.d("Error", "The search query failed to find any moods");
                }
            }
            catch(Exception e){
                Log.d("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            return moods;
        }
    }
}
