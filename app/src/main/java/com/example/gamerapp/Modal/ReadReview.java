package com.example.gamerapp.Modal;

import java.io.Serializable;

public class ReadReview implements Serializable {

   public int reviewid,gameid,uid;
   public String comment,reviewdate,reviewby;

    public int getReviewid() {
        return reviewid;
    }

    @Override
    public String toString() {
        return "ReadReview{" +
                "reviewid=" + reviewid +
                ", gameid=" + gameid +
                ", uid=" + uid +
                ", comment='" + comment + '\'' +
                ", reviewdate='" + reviewdate + '\'' +
                ", reviewby='" + reviewby + '\'' +
                '}';
    }

    public String getReviewby() {
        return reviewby;
    }

    public void setReviewby(String reviewby) {
        this.reviewby = reviewby;
    }

    public void setReviewid(int reviewid) {
        this.reviewid = reviewid;
    }

    public int getGameid() {
        return gameid;
    }

    public void setGameid(int gameid) {
        this.gameid = gameid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getReviewdate() {
        return reviewdate;
    }

    public void setReviewdate(String reviewdate) {
        this.reviewdate = reviewdate;
    }
}
