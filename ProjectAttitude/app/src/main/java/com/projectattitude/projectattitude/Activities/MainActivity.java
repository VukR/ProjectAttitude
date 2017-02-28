package com.projectattitude.projectattitude.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.projectattitude.projectattitude.Adapters.MoodMainAdapter;
import com.projectattitude.projectattitude.Objects.Mood;
import com.projectattitude.projectattitude.Objects.MoodList;
import com.projectattitude.projectattitude.R;

public class MainActivity extends AppCompatActivity {

    //creating the view objects
    protected MoodList moodList;
    private MoodMainAdapter moodAdapter;
    private ListView moodListView;
    private DatabaseReference mDatabase;
    private FloatingActionButton addMoodButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize views
        moodList = new MoodList();
        moodListView = (ListView) findViewById(R.id.moodListView);
        addMoodButton = (FloatingActionButton) findViewById(R.id.addMoodButton);
        moodAdapter = new MoodMainAdapter(this, moodList.getMoodList());

        moodListView.setAdapter(moodAdapter);
        //references location of DB we want to write to
        mDatabase = FirebaseDatabase.getInstance().getReference();


        //on click listener for adding moods
        addMoodButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createMood();
            }
        });

        //Litens for change to DB
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override

            /*When a mood is added to the DB, it will then add it to the moodAdapter, updating
            the local moodList and listview in mainactivity Simultaneously
             */
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Mood testMood = dataSnapshot.getValue(Mood.class);
                Log.d("new mood to add", testMood.getEmotionState());
                moodAdapter.add(testMood);
                Log.d("moodList updated", moodList.getMoodList().toString());

                //checking to see if moods properly updated from DB
                for (int i = 0; i < moodList.getMoodList().size(); i++) {
                    Log.d("emotionState of mood: ", moodList.getMoodList().get(i).getEmotionState());
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



    //TODO Build these functions
    /**
     * This method will take the user to the Create Mood view
     */
    private void createMood(){
        Intent createMoodIntent = new Intent(MainActivity.this, CreateMoodActivity.class);
        startActivityForResult(createMoodIntent, 0);
    }

    /**
     * This method takes a mood the user made and brings them to the edit mood view
     */
    private void editMood(){

    }

    /**
     * This deletes a selected mood.
     * @param mood the mood the user wants to get rid of0
     */
    private void deleteMood(Mood mood){

    }

    /**
     * This is the method that handles finding moods with a given keyword
     * Will probably return a mood list object in time, or set the current one.
     */
    private void searchMood(){

    }

    /**
     * Handles sorting the list, may need several functions for each type of sort.
     * @param moods the moodlist that is to be sorted
     */
    private void sortMood(MoodList moods){

    }

    /**
     * When the user clicks the map button this takes them to the map view
     */
    private void goToMap(){
    }

    /**
     * When the user clicks the profile button it will take them to the profile view
     * Later may take a profile as an argument to go to someone elses profile.
     */
    private void viewProfile(){
    }

    /**
     * Logs the current profile out of the application and returns the user to the log in view.
     */
    private void logOut(){

    }

    //accept returned information from activities
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Mood returnedMood;

        //CreateMoodActivity results and updating to DB as an array
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                returnedMood = (Mood) data.getSerializableExtra("addMoodIntent");
                mDatabase.push().setValue(returnedMood);
            }
        }
    }



}
