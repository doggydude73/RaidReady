package com.ajsuneson.raidready.JSONParsers;

import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by asuneson on 2/16/15.
 */
public class RealmStatus {

    public RealmStatus (InputStream in){

    }

    public RealmStatus readRealmStatusStream(InputStream in) throws IOException{
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));

        try{

        }
        finally{
            reader.close();
        }

        return null;
    }


}
