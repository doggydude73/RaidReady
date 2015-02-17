package com.ajsuneson.raidready.JSONParsers;

import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asuneson on 2/16/15.
 */
public class RealmStatus {

    public int numRealms;
    public List<Realm> realmList;

    public RealmStatus(){
        realmList = new ArrayList<Realm>();
    }

    public class Realm{
        public String type;
        public String population;
        public boolean queue;
        public boolean status;  // Whether the server is online or offline; Online: True, Offline: False
        public String name;
        public String slug;
        public String battlegroup;
        public String timezone;
        public List<String> connected_realms;

        public Realm(String type, String population, boolean queue, boolean status, String name, String slug, String battlegroup, String timezone, List<String> connected_realms){
            this.type = type;
            this.population = population;
            this.queue = queue;
            this.status = status;
            this.name = name;
            this.slug = slug;
            this.battlegroup = battlegroup;
            this.timezone = timezone;
            this.connected_realms = connected_realms;
        }

    }

    public RealmStatus readRealmStatusStream(InputStream in) throws IOException{
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        RealmStatus status = new RealmStatus();
        try{
            reader.beginObject();
            reader.nextName();
            status = readRealmArray(reader);
            reader.endObject();
        }
        finally{
            reader.close();
        }

        return status;
    }

    public RealmStatus readRealmArray(JsonReader reader) throws IOException{
        RealmStatus realms = new RealmStatus();

        reader.beginArray();
        while (reader.hasNext()){
            realms.realmList.add(readRealm(reader));
            realms.numRealms ++;
        }
        reader.endArray();

        return realms;
    }

    public Realm readRealm(JsonReader reader) throws IOException{

        String type = "";
        String population = "";
        boolean queue = false;
        boolean status = true;
        String name = "No-Name FAILED";
        String slug = "";
        String battlegroup = "";
        String timezone = "";
        List<String> connected_realms = new ArrayList<String>();

        reader.beginObject(); // Open up a realm object
        while (reader.hasNext()){
            String fieldName = reader.nextName();

            if (fieldName.equals("type")){
                type = reader.nextString();
            }else if (fieldName.equals("population")){
                population = reader.nextString();
            }else if (fieldName.equals("queue")){
                queue = reader.nextBoolean();
            }else if (fieldName.equals("status")){
                status = reader.nextBoolean();
            }else if (fieldName.equals("name")){
                name = reader.nextString();
            }else if (fieldName.equals("slug")){
                slug = reader.nextString();
            }else if (fieldName.equals("battlegroup")){
                battlegroup = reader.nextString();
            }else if (fieldName.equals("timezone")){
                timezone = reader.nextString();
            }else if (fieldName.equals("connected_realms")){
                reader.beginArray();

                while (reader.hasNext()){
                    connected_realms.add(reader.nextString());
                }

                reader.endArray();
            }else{
                reader.skipValue();
            }
            //Log.v("RealmStatusParser", fieldName);
        }
        reader.endObject();

        return new Realm(type, population, queue, status, name, slug, battlegroup, timezone, connected_realms);
    }


}
