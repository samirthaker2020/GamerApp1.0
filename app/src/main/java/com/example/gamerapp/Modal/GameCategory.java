package com.example.gamerapp.Modal;

import java.io.Serializable;

public class GameCategory implements Serializable {
  public   String GcategotyName;
   public int GcategoryId;
   public String GcategoryImage;
    public String getGcategotyName() {
        return GcategotyName;
    }

    public void setGcategotyName(String gcategotyName) {
        GcategotyName = gcategotyName;
    }

    @Override
    public String toString() {
        return "GameCategory{" +
                "GcategotyName='" + GcategotyName + '\'' +
                ", GcategoryId=" + GcategoryId +
                ", GcategoryImage='" + GcategoryImage + '\'' +
                '}';
    }

    public int getGcategoryId() {
        return GcategoryId;
    }

    public void setGcategoryId(int gcategoryId) {
        GcategoryId = gcategoryId;
    }

    public String getGcategoryImage() {
        return GcategoryImage;
    }

    public void setGcategoryImage(String gcategoryImage) {
        GcategoryImage = gcategoryImage;
    }


}
