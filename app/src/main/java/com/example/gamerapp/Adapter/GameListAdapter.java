package com.example.gamerapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewItem viewItem = null;

        if(convertView == null)
        {
            viewItem = new ViewItem();
            LayoutInflater layoutInfiater = (LayoutInflater)this.context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInfiater.inflate(R.layout.custom_gamelist, null);
            viewItem.gamename= (EditText) convertView.findViewById(R.id.editText_gamename);
            viewItem.gamepic=(ImageView) convertView.findViewById(R.id.imageView_gamepic);
            convertView.setTag(viewItem);
        }
        else
        {
            viewItem = (ViewItem) convertView.getTag();
        }

        viewItem.gamename.setText(" "+GameArrayList.get(position).getGamename());
        Picasso
                .get()
                .load(Constants.URL_IMAGES+GameArrayList.get(position).getGameimage())
                .placeholder(R.mipmap.ic_launcher) // can also be a drawable
                .noFade()
                .resize(500,500)
                .error(R.drawable.ic_alert_foreground)
                .into(viewItem.gamepic);
        return convertView;
    }
}
class ViewItem
{
    EditText gamename;
    ImageView gamepic;

}
