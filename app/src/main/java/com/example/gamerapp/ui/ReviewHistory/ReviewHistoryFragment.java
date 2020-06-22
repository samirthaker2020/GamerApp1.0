package com.example.gamerapp.ui.ReviewHistory;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.gamerapp.Adapter.ReadReviewAdapter;
import com.example.gamerapp.Adapter.ReviewHistoryAdapter;
import com.example.gamerapp.Modal.ReadReview;
import com.example.gamerapp.Modal.ReviewHistory;
import com.example.gamerapp.Others.Constants;
import com.example.gamerapp.R;
import com.example.gamerapp.Tabs.ReviewRead;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReviewHistoryFragment extends Fragment {


    private ListView lstreviewhistory;  //lstsample
    // Creating List of Subject class.
    List<ReviewHistory> ListCustomReviewHistory; //CustomSampleNamesList;

    // Server Http URL
    String HTTP_URL = Constants.URL_REVIEW_HISTORY;

    // String to hold complete JSON response object.
    String FinalJSonObject ;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_reviewhistory, container, false);
        lstreviewhistory = (ListView) root.findViewById(R.id.lst_reviewhistory);
initsampledata();
        return root;
    }


    private void initsampledata()
    {

        // Creating StringRequest and set the JSON server URL in here.
        StringRequest stringRequest = new StringRequest(HTTP_URL+"?uid="+Constants.CUURENT_USERID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // After done Loading store JSON response in FinalJSonObject string variable.
                        FinalJSonObject = response ;

                        // Calling method to parse JSON object.
                        new ReviewHistoryFragment.ReviewHistoryParseJSonDataClass (getActivity()).execute();

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
    private class ReviewHistoryParseJSonDataClass extends AsyncTask<Void, Void, Void> {

        public Context context;



        public  ReviewHistoryParseJSonDataClass(Context context) {

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
                        ReviewHistory samples;

                        // Defining CustomSubjectNamesList AS Array List.
                        ListCustomReviewHistory = new ArrayList<ReviewHistory>();

                        for (int i = 0; i < jsonArray.length(); i++) {

                            samples = new ReviewHistory();

                            jsonObject = jsonArray.getJSONObject(i);

                            //Storing ID into subject list.
                            if(jsonObject!=null)
                            {
                                String comment=jsonObject.getString("comments");
                                String reviewdate=jsonObject.getString("revdatetime");
                                String gamename=jsonObject.getString("gamename");

                                samples.comment=comment;
                              samples.gamename=gamename;
                                samples.reviewdate=reviewdate;
                            }else {
                                samples.comment="No data";
                                samples.reviewby= " No data";
                                samples.reviewdate=" No data";
                            }




                            // Adding subject list object into CustomSubjectNamesList.
                            ListCustomReviewHistory.add(samples);
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block

                        e.printStackTrace();
                    }
                }else
                {
                   ReviewHistory r=new ReviewHistory();
                    r.reviewdate="no";
                    r.reviewby="no";
                    r.comment="no";
                    r.gamename="no";
                    ListCustomReviewHistory.add(r);
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
            ReviewHistoryAdapter adapter = new ReviewHistoryAdapter( context, (ArrayList<ReviewHistory>) ListCustomReviewHistory);

            // Setting up all data into ListView.
            lstreviewhistory.setAdapter(adapter);

            // Hiding progress bar after all JSON loading done.
            // progressBar.setVisibility(View.GONE);

        }
    }



}
