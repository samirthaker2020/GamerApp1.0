package com.example.gamerapp.Modal;

import java.io.Serializable;

public class GameList implements Serializable {

   public String gamename,gameimage,gametrailer;
    public  int gameid;
    public float lstgame_rating;

    public float getLstgame_rating() {
        return lstgame_rating;
    }

    @Override
    public String toString() {
        return "GameList{" +
                "gamename='" + gamename + '\'' +
                ", gameimage='" + gameimage + '\'' +
                ", gametrailer='" + gametrailer + '\'' +
                ", gameid=" + gameid +
                ", lstgame_rating=" + lstgame_rating +
                '}';
    }

    public void setLstgame_rating(float lstgame_rating) {
        this.lstgame_rating = lstgame_rating;
    }

    public String getGametrailer() {
        return gametrailer;
    }

    public void setGametrailer(String gametrailer) {
        this.gametrailer = gametrailer;
    }


    public String getGamename() {
        return gamename;
    }

    public void setGamename(String gamename) {
        this.gamename = gamename;
    }

    public String getGameimage() {
        return gameimage;
    }

    public void setGameimage(String gameimage) {
        this.gameimage = gameimage;
    }

    public int getGameid() {
        return gameid;
    }

    public void setGameid(int gameid) {
        this.gameid = gameid;
    }
}
