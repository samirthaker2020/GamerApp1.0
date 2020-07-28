package com.example.gamerapp.ui.home;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
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
import com.example.gamerapp.Adapter.ReviewHistoryAdapter;
import com.example.gamerapp.Controller.MainPage;
import com.example.gamerapp.Modal.GameList;
import com.example.gamerapp.Modal.ReviewHistory;
import com.example.gamerapp.Others.Constants;
import com.example.gamerapp.R;
import com.example.gamerapp.ui.ReviewHistory.ReviewHistoryFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment1 extends Fragment {
    private ListView lstgamelist;  //lstsample
    int pid;
    SearchView gamelistsearch1;
    // Creating List of Subject class.
    List<GameList> CustomGameList; //CustomSampleNamesList;
    List<GameList> CustomGameList1; //CustomSampleNamesList1 for search bar;
    // Server Http URL
    String HTTP_URL = Constants.URL_RH_GAME_SEARCH;
ProgressBar progressloader;
    // String to hold complete JSON response object.
    String FinalJSonObject;
    private HomeViewModel1 homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel1.class);
        View root = inflater.inflate(R.layout.fragment_home1, container, false);
        ((MainPage) getActivity()).getSupportActionBar().setTitle("Game List");
        Bundle args = getArguments();
        if (args != null) {
            pid = args.getInt("pid");
            Constants.CUURENT_PID = pid;
        }
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        lstgamelist = (ListView) root.findViewById(R.id.lstgame);
        gamelistsearch1 = (SearchView) root.findViewById(R.id.gamelist_searchbar1);
        progressloader=(ProgressBar) root.findViewById(R.id.gamelist_loader);

        initsampledata();
        lstgamelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //  GameList s= CustomGameList.get(position);
                //    System.out.println(CustomGameList.get(position).getGamename());

                Context context = getActivity();
                CharSequence text = CustomGameList.get(position).getGamename();
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            }
        });

        gamelistsearch1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                initsampledata();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                gamesearch(newText);
                return true;
            }
        });
        return root;
    }

    private void initsampledata() {

        // Creating StringRequest and set the JSON server URL in here.
        StringRequest stringRequest = new StringRequest(HTTP_URL + "?pid=" + pid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // After done Loading store JSON response in FinalJSonObject string variable.
                        FinalJSonObject = response;

                        // Calling method to parse JSON object.
                        new ParseJSonDataClass(getActivity()).execute();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        // Showing error message if something goes wrong.
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();

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
            progressloader.setVisibility(View.VISIBLE);
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
                            samples.gamename = jsonObject.getString("gamename");
                            samples.gameid = jsonObject.getInt("gameid");
                            samples.gameimage = jsonObject.getString("gamepic");
                            samples.gametrailer = jsonObject.getString("gametrailer");
                            samples.lstgame_rating = (float) jsonObject.getDouble("gamelistrating");
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
        protected void onPostExecute(Void result) {
            // Hiding progress bar after all JSON loading done.
            progressloader.setVisibility(View.GONE);
            // After all done loading set complete CustomSubjectNamesList with application context to ListView adapter.
            GameListAdapter adapter = new GameListAdapter(context, (ArrayList<GameList>) CustomGameList);
            // lstgamelist.setDivider(new ColorDrawable(0x99F10529));   //0xAARRGGBB
            lstgamelist.setDividerHeight(4);
            // Setting up all data into ListView.
            lstgamelist.setAdapter(adapter);


        }
    }

    private void gamesearch(String data) {

        // Creating StringRequest and set the JSON server URL in here.
        StringRequest stringRequest = new StringRequest(HTTP_URL + "?pid=" + Constants.CUURENT_PID + "&gsearch=" + data,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // After done Loading store JSON response in FinalJSonObject string variable.
                        FinalJSonObject = response;

                        // Calling method to parse JSON object.
                        new ParseJSonDataClass1(getActivity()).execute();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        // Showing error message if something goes wrong.
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        // Creating String Request Object.
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        // Passing String request into RequestQueue.
        requestQueue.add(stringRequest);

    }

    // Creating method to parse JSON object.
    private class ParseJSonDataClass1 extends AsyncTask<Void, Void, Void> {

        public Context context;


        public ParseJSonDataClass1(Context context) {

            this.context = context;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressloader.setVisibility(View.VISIBLE);
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
                        CustomGameList1 = new ArrayList<GameList>();

                        for (int i = 0; i < jsonArray.length(); i++) {

                            samples = new GameList();

                            jsonObject = jsonArray.getJSONObject(i);

                            //Storing ID into subject list.
                            samples.gamename = jsonObject.getString("gamename");
                            samples.gameid = jsonObject.getInt("gameid");
                            samples.gameimage = jsonObject.getString("gamepic");
                            samples.gametrailer = jsonObject.getString("gametrailer");
                            samples.lstgame_rating = (float) jsonObject.getDouble("gamelistrating");
                            //Storing Subject name in subject list.
                            //  samples.batchname = jsonObject.getString("batchname");

                            // Adding subject list object into CustomSubjectNamesList.
                            CustomGameList1.add(samples);
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
        protected void onPostExecute(Void result) {
            // Hiding progress bar after all JSON loading done.
            progressloader.setVisibility(View.GONE);
            // After all done loading set complete CustomSubjectNamesList with application context to ListView adapter.
            GameListAdapter adapter = new GameListAdapter(context, (ArrayList<GameList>) CustomGameList1);
            // lstgamelist.setDivider(new ColorDrawable(0x99F10529));   //0xAARRGGBB
            lstgamelist.setDividerHeight(4);
            // Setting up all data into ListView.
            lstgamelist.setAdapter(adapter);



        }
    }

}