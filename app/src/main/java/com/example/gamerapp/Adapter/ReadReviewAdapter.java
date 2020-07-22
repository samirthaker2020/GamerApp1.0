package com.example.gamerapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.gamerapp.Modal.GameList;
import com.example.gamerapp.Modal.ReadReview;
import com.example.gamerapp.Others.ProfileImage;
import com.example.gamerapp.R;

import java.util.ArrayList;

public class ReadReviewAdapter extends BaseAdapter {
    private Context context;

    public ReadReviewAdapter(Context context, ArrayList<ReadReview> reviewArrayList) {
        this.context = context;
        ReviewArrayList = reviewArrayList;
    }

    private ArrayList<ReadReview> ReviewArrayList;

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
        ReviewViewItem viewItem = null;

        if(convertView == null)
        {
            viewItem = new ReviewViewItem();
            LayoutInflater layoutInfiater = (LayoutInflater)this.context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInfiater.inflate(R.layout.customreadreview, null);
            viewItem.r_comment=(TextView) convertView.findViewById(R.id.review_comment);
            viewItem.r_by=(TextView) convertView.findViewById(R.id.review_by);
            viewItem.r_date=(TextView) convertView.findViewById(R.id.review_date);
            viewItem.r_lblrating=(TextView) convertView.findViewById(R.id.lbl_readreview_rating);
            viewItem.read_ratings=(RatingBar) convertView.findViewById(R.id.readreview_ratingBar1);
            viewItem.r_userimage=(ImageView) convertView.findViewById(R.id.readreview_user_image);
            convertView.setTag(viewItem);


        }
        else
        {
            viewItem= (ReviewViewItem) convertView.getTag();
        }
        viewItem.r_comment.setText("Review::"+" "+ReviewArrayList.get(position).getComment());
        viewItem.r_date.setText("On::"+" "+ReviewArrayList.get(position).getReviewdate());
        viewItem.r_by.setText(ReviewArrayList.get(position).getReviewby());
        viewItem.read_ratings.setRating((float) ReviewArrayList.get(position).getReadreview_displayratings());
        viewItem.r_lblrating.setText("Rating: "+ReviewArrayList.get(position).getReadreview_lblrating()+"/"+"5.0");
       viewItem.r_userimage.setImageBitmap(ProfileImage.StringToBitMap(ReviewArrayList.get(position).getReadreview_userimage()));
        return convertView;
    }
}
class ReviewViewItem
{
     TextView r_comment,r_date,r_by,r_lblrating;
     ImageView r_userimage;
     RatingBar read_ratings;

}