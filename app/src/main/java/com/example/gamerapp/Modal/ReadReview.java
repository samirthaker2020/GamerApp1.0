package com.example.gamerapp.Modal;

import java.io.Serializable;

public class ReadReview implements Serializable {

   public int reviewid,gameid,uid;
   public float readreview_displayratings;
   public String comment,reviewdate,reviewby,readreview_userimage,readreview_lblrating;

    public int getReviewid() {
        return reviewid;
    }

    public String getReadreview_userimage() {
        return readreview_userimage;
    }

    public float getReadreview_displayratings() {
        return readreview_displayratings;
    }

    @Override
    public String toString() {
        return "ReadReview{" +
                "reviewid=" + reviewid +
                ", gameid=" + gameid +
                ", uid=" + uid +
                ", readreview_displayratings=" + readreview_displayratings +
                ", comment='" + comment + '\'' +
                ", reviewdate='" + reviewdate + '\'' +
                ", reviewby='" + reviewby + '\'' +
                ", readreview_userimage='" + readreview_userimage + '\'' +
                ", readreview_lblrating='" + readreview_lblrating + '\'' +
                '}';
    }

    public void setReadreview_displayratings(float readreview_displayratings) {
        this.readreview_displayratings = readreview_displayratings;
    }

    public String getReadreview_lblrating() {
        return readreview_lblrating;
    }

    public void setReadreview_lblrating(String readreview_lblrating) {
        this.readreview_lblrating = readreview_lblrating;
    }

    public void setReadreview_userimage(String readreview_userimage) {
        this.readreview_userimage = readreview_userimage;
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
