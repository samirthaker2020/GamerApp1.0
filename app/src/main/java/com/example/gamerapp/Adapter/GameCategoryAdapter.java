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
import android.widget.TextView;

import com.example.gamerapp.Modal.GameCategory;
import com.example.gamerapp.Modal.GameList;
import com.example.gamerapp.Others.Constants;
import com.example.gamerapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GameCategoryAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<GameCategory> GameCategory;

    public GameCategoryAdapter(Context context, ArrayList<com.example.gamerapp.Modal.GameCategory> gameCategory) {
        this.context = context;
        GameCategory = gameCategory;
    }

    @Override
    public int getCount() {
        return GameCategory.size();
    }

    @Override
    public Object getItem(int position) {
        return GameCategory.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GameCategoryViewItem viewItem = null;

        if(convertView == null)
        {
            viewItem = new GameCategoryViewItem();
            LayoutInflater layoutInfiater = (LayoutInflater)this.context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInfiater.inflate(R.layout.customgamecategory, null);
            viewItem.Gcategorypic=(ImageView) convertView.findViewById(R.id.Gcategoryimage);
            viewItem.Gcategorytitle=(TextView) convertView.findViewById(R.id.Gcategoryname);
            convertView.setTag(viewItem);


        }
        else
        {
            viewItem = (GameCategoryViewItem) convertView.getTag();
        }
        viewItem.Gcategorytitle.setText(" "+GameCategory.get(position).getGcategotyName());
        Picasso
                .get()
                .load(Constants.URL_IMAGES+GameCategory.get(position).getGcategoryImage())
                .placeholder(R.mipmap.ic_launcher) // can also be a drawable
                .noFade()
                .resize(500,500)
                .error(R.drawable.ic_alert_foreground)
                .into(viewItem.Gcategorypic);
        return convertView;
    }
}
class GameCategoryViewItem
{

    ImageView Gcategorypic;
    TextView Gcategorytitle;

}
