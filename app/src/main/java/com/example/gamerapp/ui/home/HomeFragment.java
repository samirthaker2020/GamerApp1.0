package com.example.gamerapp.ui.home;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gamerapp.Adapter.GameCategoryAdapter;
import com.example.gamerapp.Controller.MainPage;
import com.example.gamerapp.Modal.GameCategory;
import com.example.gamerapp.Others.Constants;
import com.example.gamerapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private ListView lstgamecategory;  //lstsample
    // Creating List of Subject class.
    List<GameCategory> CustomGameCategory; //CustomSampleNamesList;
    ProgressBar Homeprogressloader;
    // Server Http URL
    String HTTP_URL = Constants.URL_GAMECATEGORY;

    // String to hold complete JSON response object.
    String FinalJSonObject ;
    private HomeViewModel mViewModel;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.home_fragment, container, false);
        ((MainPage) getActivity()).getSupportActionBar().setTitle("Game Categories");
        lstgamecategory = (ListView) root.findViewById(R.id.lstcategory);
        initcategorydata();
        Homeprogressloader=(ProgressBar) root.findViewById(R.id.Homeprogressbar);


       lstgamecategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //  GameList s= CustomGameList.get(position);
                //    System.out.println(CustomGameList.get(position).getGamename());

                Context context = getActivity();
                int text =  CustomGameCategory.get(position).getGcategoryId();
                int duration = Toast.LENGTH_SHORT;
                Fragment fr = new HomeFragment1();
                FragmentManager fm = getFragmentManager();
                Bundle args = new Bundle();
                args.putInt("pid", text);
                fr.setArguments(args);
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment, fr).addToBackStack("HomeFragment");
                fragmentTransaction.commit();
                //Toast toast = Toast.makeText(context, String.valueOf(text), duration);
               // toast.show();

            }
        });
        return root;
    }
    private void initcategorydata()
    {

        // Creating StringRequest and set the JSON server URL in here.
        StringRequest stringRequest = new StringRequest(HTTP_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // After done Loading store JSON response in FinalJSonObject string variable.
                        FinalJSonObject = response ;

                        // Calling method to parse JSON object.
                        new HomeFragment.ParseJSonDataClass(getActivity()).execute();

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
            Homeprogressloader.setVisibility(View.VISIBLE);
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
                       GameCategory samples;

                        // Defining CustomSubjectNamesList AS Array List.
                        CustomGameCategory = new ArrayList<GameCategory>();

                        for (int i = 0; i < jsonArray.length(); i++) {

                            samples = new GameCategory();

                            jsonObject = jsonArray.getJSONObject(i);

                            //Storing ID into subject list.
                            samples.GcategotyName= jsonObject.getString("pname");
                            samples.GcategoryId=jsonObject.getInt("pid");
                            samples.GcategoryImage=jsonObject.getString("pimage");

                            //Storing Subject name in subject list.
                            //  samples.batchname = jsonObject.getString("batchname");

                            // Adding subject list object into CustomSubjectNamesList.
                            CustomGameCategory.add(samples);
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
            GameCategoryAdapter adapter = new GameCategoryAdapter( context, (ArrayList<GameCategory>) CustomGameCategory);
            // lstgamelist.setDivider(new ColorDrawable(0x99F10529));   //0xAARRGGBB
            lstgamecategory.setDividerHeight(4);
            // Setting up all data into ListView.
            lstgamecategory.setAdapter(adapter);

            // Hiding progress bar after all JSON loading done.
             Homeprogressloader.setVisibility(View.GONE);

        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel
    }

}