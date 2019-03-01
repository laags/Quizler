package com.highlatencygames.laags.quizler;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.highlatencygames.laags.assignment2.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// The class facilitates reading in questions and answers from a delimited text file, shuffling those questions, and returning pairs and answers
public class Questions {

    private Map<String, String> map = new HashMap<String, String>(); // Hashmap
    private ArrayList<String> terms, defs;
    private Context context;

    public Questions(Context context){
        this.context = context;
        populate();
        terms = new ArrayList<String>(map.values()); //Populate terms from hashmap
        defs = new ArrayList<String>(map.keySet()); //Populate definitions from hashmap
        shuffle(); // Shuffle the questions/answers around

        //Demonstrating removing items from an array list, not implemented because it is not needed
        //removing the item at the first index
        //terms.remove(0);
        //removes all items in the arraylist
        //terms.clear();
    }

    // Read in key-value pairs from file populating the hashmap
    private void populate(){
        String str;
        BufferedReader br = null;
        try {
            InputStream is = context.getResources().openRawResource(R.raw.questions);
            br = new BufferedReader(new InputStreamReader(is));
        }catch(Exception e)
        {
            Log.e("Questions Class", "Error opening file");
        }
        try{
            while((str = br.readLine())!=null)
            {
                String[] parts = str.split(":", 2);
                if (parts.length >= 2)
                {
                    String key = parts[0];
                    String value = parts[1];
                    map.put(key, value);
                } else {
                    System.out.println("ignoring line: " + str);
                }
            }
            br.close();
        }catch(IOException e)
        {
            Log.e("Questions Class", "Unable to read line");
        }
    }

    // Shuffle the order of the terms and definitions
    public void shuffle(){
        Collections.shuffle(terms);
        Collections.shuffle(defs);
    }

    // Returns a question to be asked from a list of definitions
    public String getQuestion(int a){
        return defs.get(a);
    }

    // Returns a list of terms to be displayed
    // Uses hashmap, arraylists and loops to find the matching answer then populate a list with other answers
    public ArrayList<String> getOptions(String definition) {
        ArrayList<String> options = new ArrayList<>(), selectedAlready = new ArrayList<>();
        // First set the real answer
        options.add(map.get(definition));
        // Next loop to find options that aren't correct
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < terms.size(); j++){
                if(!map.get(definition).equals(terms.get(j))&& !selectedAlready.contains(terms.get(j))){
                    selectedAlready.add(terms.get(j));
                    options.add(terms.get(j));
                    break;
                }
            }
        }

        // Shuffle the options so they dont show up the same order every time
        shuffle();
        Collections.shuffle(options);

        // Return a list of options to be used
        return options;
    }

    // Returns the matching answer to a provided definition using hashmap
    public String getAnswer(String definition){
        return map.get(definition);
    }
}
