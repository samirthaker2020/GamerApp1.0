package com.example.gamerapp.ui.ReviewHistory;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
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
import com.example.gamerapp.Others.DeleteReview;
import com.example.gamerapp.Others.DialogMessage;
import com.example.gamerapp.Others.VolleySingleton;
import com.example.gamerapp.R;
import com.example.gamerapp.Tabs.ReviewRead;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.gamerapp.Others.DialogMessage.singlemsg;

public class ReviewHistoryFragment extends Fragment {


    private ListView lstreviewhistory;  //lstsample
    // Creating List of Subject class.
    List<ReviewHistory> ListCustomReviewHistory; //CustomSampleNamesList;
    List<ReviewHistory> ListCustomReviewHistory1; //CustomSampleNamesList;
private SearchView rv_sv1;
    // Server Http URL
    String HTTP_URL = Constants.URL_REVIEW_HISTORY;
    String HTTP_SEARCH_URL = Constants.URL_RH_SEARCH;
    String delete_URL = Constants.URL_DELETEREVIEW;
    // String to hold complete JSON response object.
    String FinalJSonObject ;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_reviewhistory, container, false);
        lstreviewhistory = (ListView) root.findViewById(R.id.lst_reviewhistory);
        rv_sv1 = (SearchView)   root.findViewById(R.id.rh_searchbar1);
initsampledata();

lstreviewhistory.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        ReviewHistory rv= ListCustomReviewHistory.get(position);
      //  Toast.makeText(getActivity(),String.valueOf(rv.reviewid), Toast.LENGTH_LONG).show();
      //  System.out.println(ListCustomReviewHistory.get(position).getReviewid());
       DeleteDialogbox(rv.reviewid);
        return false;
    }
});

    rv_sv1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            if(TextUtils.isEmpty(newText))
            {

                 initsampledata();
         //        Toast.makeText(getActivity(),newText,Toast.LENGTH_SHORT).show();
            }
            else {
          //      Toast.makeText(getActivity(),newText,Toast.LENGTH_SHORT).show();

                searchdata(newText);

            }
            return true;
        }
    });
        return root;
    }

public void DeleteDialogbox(final int rid)

{
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setMessage("Are you sure you want to delete?")
            .setCancelable(false)
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                     deletereview(rid);
                }
            })
            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
    AlertDialog alert = builder.create();
    alert.show();
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
    private void searchdata(String data)
    {

        // Creating StringRequest and set the JSON server URL in here.
        StringRequest stringRequest = new StringRequest(HTTP_SEARCH_URL+"?uid="+Constants.CUURENT_USERID+"&rsearch="+data,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // After done Loading store JSON response in FinalJSonObject string variable.
                        FinalJSonObject = response ;

                        // Calling method to parse JSON object.
                        new ReviewHistoryFragment.ReviewHistoryParseJSonDataClass1 (getActivity()).execute();

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
                                double rating=jsonObject.getDouble("gamerating");
                                int reviewid=jsonObject.getInt("reviewid");

                                samples.comment=comment;
                              samples.gamename=gamename;
                                samples.reviewdate=reviewdate;
                                samples.rating=rating;
                                samples.reviewid=reviewid;
                            }else {
                                samples.comment="No data";
                                samples.reviewby= " No data";
                                samples.reviewdate=" No data";
                                samples.rating=0;
                                samples.reviewid=0;
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
    // Creating method to parse JSON object.
    private class ReviewHistoryParseJSonDataClass1 extends AsyncTask<Void, Void, Void> {

        public Context context;



        public  ReviewHistoryParseJSonDataClass1(Context context) {

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
                        ListCustomReviewHistory1 = new ArrayList<ReviewHistory>();

                        for (int i = 0; i < jsonArray.length(); i++) {

                            samples = new ReviewHistory();

                            jsonObject = jsonArray.getJSONObject(i);

                            //Storing ID into subject list.
                            if(jsonObject!=null)
                            {
                                String comment=jsonObject.getString("comments");
                                String reviewdate=jsonObject.getString("revdatetime");
                                String gamename=jsonObject.getString("gamename");
                                double rating=jsonObject.getDouble("gamerating");
                                int reviewid=jsonObject.getInt("reviewid");

                                samples.comment=comment;
                                samples.gamename=gamename;
                                samples.reviewdate=reviewdate;
                                samples.rating=rating;
                                samples.reviewid=reviewid;
                                System.out.println(reviewid);
                            }else {
                                samples.comment="No data";
                                samples.reviewby= " No data";
                                samples.reviewdate=" No data";
                                samples.rating=0;
                                samples.reviewid=0;
                            }




                            // Adding subject list object into CustomSubjectNamesList.
                            ListCustomReviewHistory1.add(samples);
                            for(ReviewHistory student:ListCustomReviewHistory1) {
                                System.out.println("searchbar:"+student);  // Will invoke overrided `toString()` method
                            }
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
                    ListCustomReviewHistory.clear();
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
            ReviewHistoryAdapter adapter = new ReviewHistoryAdapter( context, (ArrayList<ReviewHistory>) ListCustomReviewHistory1);

            // Setting up all data into ListView.
            lstreviewhistory.setAdapter(adapter);
             adapter.notifyDataSetChanged();
            // Hiding progress bar after all JSON loading done.
            // progressBar.setVisibility(View.GONE);

        }
    }
    public void deletereview(final int rid) {

        //Call our volley library
        StringRequest stringRequest = new StringRequest(Request.Method.POST,delete_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
//                            Toast.makeText(getApplicationContext(),response.toString(), Toast.LENGTH_SHORT).show();

                            JSONObject obj = new JSONObject(response);
                            if (obj.getBoolean("error")) {

                                DialogMessage.singlemsg("Invalid",obj.getString("message"),getActivity(),false);

                            } else {

                               DialogMessage.singlemsg("SUCESS",obj.getString("message"),getActivity(),true);
                               initsampledata();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),"Connection Error"+error, Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("rid", String.valueOf(rid));

                return params;
            }
        };
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }


}
