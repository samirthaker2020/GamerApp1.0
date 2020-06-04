package com.example.gamerapp.Modal;

public class GameList {

   public String gamename,gameimage;
    public  int gameid;



    @Override
    public String toString() {
        return "GameList{" +
                "gamename='" + gamename + '\'' +
                ", gameimage='" + gameimage + '\'' +
                ", gameid=" + gameid +
                '}';
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
