package com.example.gamerapp.Tabs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gamerapp.Adapter.GameListAdapter;
import com.example.gamerapp.Controller.LoginActivity;
import com.example.gamerapp.Controller.MainPage;
import com.example.gamerapp.Modal.GameList;
import com.example.gamerapp.Others.Constants;
import com.example.gamerapp.Others.SharedPref;
import com.example.gamerapp.Others.VolleySingleton;
import com.example.gamerapp.R;
import com.example.gamerapp.ui.home.HomeFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameOverview#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameOverview extends Fragment {

    ImageView Ogameimg;
    TextView Ogamename,Ogametype,Ogameplatform;
    // Server Http URL
    String HTTP_URL = Constants.URL_GAMEDETAILS;

    // String to hold complete JSON response object.
    String FinalJSonObject ;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GameOverview() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GameOverview.
     */
    // TODO: Rename and change types and number of parameters
    public static GameOverview newInstance(String param1, String param2) {
        GameOverview fragment = new GameOverview();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_game_overview, container, false);
        Ogamename=(TextView) root.findViewById(R.id.Overview_gamename);
        Ogametype=(TextView) root.findViewById(R.id.Overview_gametype);
        Ogameplatform=(TextView) root.findViewById(R.id.Overview_gameplatform);
        Ogameimg=(ImageView) root.findViewById(R.id.Overview_image) ;
      //  Toast.makeText(getActivity(),String.valueOf(Constants.CUURENT_GAMEID),Toast.LENGTH_SHORT).show();
        fetchgamedata();
        return root;

    }

    private void fetchgamedata() {





        //Call our volley library
        StringRequest stringRequest = new StringRequest(Request.Method.POST,HTTP_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
//                            Toast.makeText(getApplicationContext(),response.toString(), Toast.LENGTH_SHORT).show();

                            JSONObject obj = new JSONObject(response);
                            if (obj.getBoolean("error")) {
                                //  Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                singlemsg("Invalid",obj.getString("message"));

                            } else {

                                //getting user name
                                Ogamename.setText("Game Name : "+ obj.getString("gamename"));
                                Ogametype.setText( "Game Type : "+obj.getString("gametype"));
                                Ogameplatform.setText( "Game Platform : "+obj.getString("gameplatform"));
                                Picasso
                                        .get()
                                        .load(Constants.URL_IMAGES+obj.getString("gameimage"))
                                        .placeholder(R.mipmap.ic_launcher) // can also be a drawable
                                        .noFade()
                                        .resize(500,500)
                                        .error(R.drawable.ic_alert_foreground)
                                        .into(Ogameimg);
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),"Connection Error"+error, Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("gameid", String.valueOf(Constants.CUURENT_GAMEID));


                return params;
            }
        };
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }


    public void singlemsg(String title,String msg)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage(msg);
        builder1.setCancelable(true);
        builder1.setIcon(R.drawable.ic_alert_foreground);
        builder1.setTitle(title);
        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

      /*  builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });*/

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }



}