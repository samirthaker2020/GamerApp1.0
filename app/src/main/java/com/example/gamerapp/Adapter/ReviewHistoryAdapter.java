package com.example.gamerapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.gamerapp.Modal.ReviewHistory;
import com.example.gamerapp.R;

import java.util.ArrayList;

public class ReviewHistoryAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ReviewHistory> ReviewArrayList;
    public ReviewHistoryAdapter(Context context, ArrayList<ReviewHistory> reviewArrayList) {
        this.context = context;
        ReviewArrayList = reviewArrayList;
    }



    @Override
    public int getCount() {
        return ReviewArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return ReviewArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ReviewHistoryViewItem viewItem = null;

        if(convertView == null)
        {
            viewItem = new ReviewHistoryViewItem();
            LayoutInflater layoutInfiater = (LayoutInflater)this.context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInfiater.inflate(R.layout.customreviewhistory, null);
            viewItem.rh_comment=(TextView) convertView.findViewById(R.id.rh_comment);
            viewItem.rh_gamename=(TextView) convertView.findViewById(R.id.rh_gamename);
            viewItem.lblrating=(TextView) convertView.findViewById(R.id.lblrating);
            viewItem.rh_date=(TextView) convertView.findViewById(R.id.rh_date);
            viewItem.rBar = (RatingBar) convertView.findViewById(R.id.rh_ratingBar1);
            convertView.setTag(viewItem);


        }
        else
        {
            viewItem= (ReviewHistoryViewItem) convertView.getTag();
        }
        viewItem.rh_comment.setText("Review::"+" "+ReviewArrayList.get(position).getComment());
        viewItem.rh_date.setText("On::"+" "+ReviewArrayList.get(position).getReviewdate());
        viewItem.rh_gamename.setText("GameName::"+" "+ReviewArrayList.get(position).getGamename());
        viewItem.rBar.setRating((float) ReviewArrayList.get(position).getRating());
        viewItem.lblrating.setText("Rating: "+ReviewArrayList.get(position).getRating()+"/"+"5.0");
        return convertView;
    }
}

class ReviewHistoryViewItem
{
     TextView rh_comment,rh_date,rh_gamename,lblrating;
   RatingBar rBar;

}