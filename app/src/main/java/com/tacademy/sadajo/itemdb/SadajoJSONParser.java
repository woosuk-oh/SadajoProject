package com.tacademy.sadajo.itemdb;

import android.util.Log;

import com.tacademy.sadajo.itemdb.ItemDB;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by pyoinsoo on 2016-11-21.
 */

public class SadajoJSONParser {
    //가요 및 장르 챠트를 JSON으로 파싱하는 곳
   public static ArrayList<itemDB>  getMelonChartParsing(String responsedJSON){

       ArrayList<SongInfo> songInfos = null;

       try {

           JSONObject melon = new JSONObject(responsedJSON);

           melon = melon.getJSONObject("melon");
           melon = melon.getJSONObject("songs");

           JSONArray songs = melon.getJSONArray("song");

           int songSize = songs.length();
           songInfos = new ArrayList<>();

           for(int i = 0 ; i < songSize ; i++) {
               SongInfo songInfo = new SongInfo();
               JSONObject song = songs.getJSONObject(i);

               songInfo.songName = song.getString("songName");
               songInfo.curentRank = song.getInt("currentRank");

               JSONObject artist = song.getJSONObject("artists");
               songInfo.artistName = artist.getJSONArray("artist").getJSONObject(0).getString("artistName");


               songInfos.add(songInfo);
           }

       } catch (JSONException e) {
           Log.e("jsonParser", e.toString());
       }
       return songInfos;
   }

    //JSON으로 장르목록을 리턴하는 메소드
    public static ArrayList<Genre> getGenreList(String responsedJSON) {
        ArrayList<Genre> genres = null;
        try {
            JSONObject melon = new JSONObject(responsedJSON);
            melon = melon.getJSONObject("melon");
            melon = melon.getJSONObject("genres");

            JSONArray genreArray = melon.getJSONArray("genre");
            int genreSize = genreArray.length();
            if( genreSize > 0){
                genres = new ArrayList<>();
            }
            for(int i = 0 ; i < genreSize ; i++){
                Genre genreInfo = new Genre();
                JSONObject genre = genreArray.getJSONObject(i);
                genreInfo.genreId = genre.getString("genreId");
                genreInfo.genreName = genre.getString("genreName");
                genres.add(genreInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return genres;
    }
}
