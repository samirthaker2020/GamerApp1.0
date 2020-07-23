package com.example.gamerapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamerapp.Controller.GameDetails;
import com.example.gamerapp.Controller.Game_Trailer;
import com.example.gamerapp.Modal.GameList;
import com.example.gamerapp.Others.Constants;
import com.example.gamerapp.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GameListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<GameList> GameArrayList;

    public GameListAdapter(Context context, ArrayList<GameList> gameArrayList) {
        this.context = context;
        GameArrayList = gameArrayList;
    }

    @Override
    public int getCount() {
        return GameArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return GameArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewItem viewItem = null;

        if(convertView == null)
        {
            viewItem = new ViewItem();
            LayoutInflater layoutInfiater = (LayoutInflater)this.context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInfiater.inflate(R.layout.custom_gamelist, null);
            viewItem.gamename= (EditText) convertView.findViewById(R.id.editText_gamename);
            viewItem.gamepic=(ImageView) convertView.findViewById(R.id.imageView_gamepic);
            viewItem.btn_trailer=(ImageButton) convertView.findViewById(R.id.btn_trailer);
            viewItem.txtlearnmore=(TextView) convertView.findViewById(R.id.txt_learnmore);
            viewItem.lstgame_rating=(RatingBar) convertView.findViewById(R.id.gamelist_ratingBar1);
            convertView.setTag(viewItem);


        }
        else
        {
            viewItem = (ViewItem) convertView.getTag();
        }
        viewItem.lstgame_rating.setRating(GameArrayList.get(position).getLstgame_rating());
        viewItem.gamename.setText(" "+GameArrayList.get(position).getGamename());
        Picasso
                .get()
                .load(Constants.URL_IMAGES+GameArrayList.get(position).getGameimage())
                .placeholder(R.mipmap.ic_launcher) // can also be a drawable
                .noFade()
                .resize(500,500)
                .error(R.drawable.ic_alert_foreground)
                .into(viewItem.gamepic);

        viewItem.btn_trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   int duration = Toast.LENGTH_SHORT;
              //  Toast toast = Toast.makeText(context, GameArrayList.get(position).getGametrailer(), duration);
               // toast.show();
                Intent i=new Intent(context, Game_Trailer.class);
                i.putExtra("gametrailer",GameArrayList.get(position).getGametrailer());
                i.putExtra("gamename",GameArrayList.get(position).getGamename());

                context.startActivity(i);
            }
        });

        viewItem.txtlearnmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, String.valueOf(GameArrayList.get(position).getGameid()), Toast.LENGTH_SHORT).show();
                Intent i=new Intent(context, GameDetails.class);
                i.putExtra("gametrailer",GameArrayList.get(position).getGametrailer());
                i.putExtra("gamename",GameArrayList.get(position).getGamename());
                i.putExtra("gameid",String.valueOf(GameArrayList.get(position).getGameid()));
               context.startActivity(i);
            }
        });
        return convertView;
    }
}
class ViewItem
{
    EditText gamename;
    ImageView gamepic;
    ImageButton btn_trailer;
    TextView txtlearnmore;
    RatingBar lstgame_rating;

}
