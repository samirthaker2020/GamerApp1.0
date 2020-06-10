package com.example.gamerapp.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gamerapp.Adapter.GameListAdapter;
import com.example.gamerapp.Controller.GameDetails;
import com.example.gamerapp.Modal.GameList;
import com.example.gamerapp.Others.Constants;
import com.example.gamerapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private ListView lstgamelist;  //lstsample
    // Creating List of Subject class.
    List<GameList> CustomGameList; //CustomSampleNamesList;

    // Server Http URL
    String HTTP_URL = Constants.URL_GAMELIST;

    // String to hold complete JSON response object.
    String FinalJSonObject ;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        lstgamelist = (ListView) root.findViewById(R.id.lstgame);
        initsampledata();
        lstgamelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              //  GameList s= CustomGameList.get(position);
            //    System.out.println(CustomGameList.get(position).getGamename());
              //  Intent intent = new Intent(getActivity(), GameDetails.class);
              //  intent.putExtra("gamelink",CustomGameList.get(position).getGametrailer() );
              //  startActivity(intent);
                Context context = getActivity();
                CharSequence text =  CustomGameList.get(position).getGamename();
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            }
        });
        return root;
    }

    private void initsampledata()
    {

        // Creating StringRequest and set the JSON server URL in here.
        StringRequest stringRequest = new StringRequest(HTTP_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // After done Loading store JSON response in FinalJSonObject string variable.
                        FinalJSonObject = response ;

                        // Calling method to parse JSON object.
                        new ParseJSonDataClass(getActivity()).execute();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        // Showing error message if something goes wrong.
                        Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });

        // Creating String Request Object.
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        // Passing String request into RequestQueue.
        requestQueue.add(stringRequest);

    }

    // Creating method to parse JSON object.
    private class ParseJSonDataClass extends AsyncTask<Void, Void, Void> {

        public Context context;



        public ParseJSonDataClass(Context context) {

            this.context = context;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            try {

                // Checking whether FinalJSonObject is not equals to null.
                if (FinalJSonObject != null) {

                    // Creating and setting up JSON array as null.
                    JSONArray jsonArray = null;
                    try {

                        // Adding JSON response object into JSON array.
                        jsonArray = new JSONArray(FinalJSonObject);

                        // Creating JSON Object.
                        JSONObject jsonObject;

                        // Creating Subject class object.
                        GameList samples;

                        // Defining CustomSubjectNamesList AS Array List.
                        CustomGameList = new ArrayList<GameList>();

                        for (int i = 0; i < jsonArray.length(); i++) {

                            samples = new GameList();

                            jsonObject = jsonArray.getJSONObject(i);

                            //Storing ID into subject list.
                            samples.gamename= jsonObject.getString("gamename");
                            samples.gameid=jsonObject.getInt("gameid");
                            samples.gameimage=jsonObject.getString("gamepic");
                            samples.gametrailer=jsonObject.getString("gametrailer");
                            //Storing Subject name in subject list.
                          //  samples.batchname = jsonObject.getString("batchname");

                            // Adding subject list object into CustomSubjectNamesList.
                            CustomGameList.add(samples);
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)

        {
            // After all done loading set complete CustomSubjectNamesList with application context to ListView adapter.
            GameListAdapter adapter = new GameListAdapter( context, (ArrayList<GameList>) CustomGameList);
           // lstgamelist.setDivider(new ColorDrawable(0x99F10529));   //0xAARRGGBB
            lstgamelist.setDividerHeight(4);
            // Setting up all data into ListView.
            lstgamelist.setAdapter(adapter);

            // Hiding progress bar after all JSON loading done.
            // progressBar.setVisibility(View.GONE);

        }
    }
}
