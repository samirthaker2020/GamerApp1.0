package com.example.gamerapp.Tabs;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gamerapp.Adapter.ReadReviewAdapter;
import com.example.gamerapp.Modal.ReadReview;
import com.example.gamerapp.Others.Constants;
import com.example.gamerapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReviewRead#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReviewRead extends Fragment {
    private ListView lstreviewlist;  //lstsample
    // Creating List of Subject class.
    List<ReadReview> CustomReadReview; //CustomSampleNamesList;

    // Server Http URL
    String HTTP_URL = Constants.URL_READREVIEW;

    // String to hold complete JSON response object.
    String FinalJSonObject ;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReviewRead() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReviewRead.
     */
    // TODO: Rename and change types and number of parameters
    public static ReviewRead newInstance(String param1, String param2) {
        ReviewRead fragment = new ReviewRead();
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
        View root = inflater.inflate(R.layout.fragment_read_review, container, false);
        lstreviewlist = (ListView) root.findViewById(R.id.lst_read_review);

initsampledata();

        return  root;
    }









    private void initsampledata()
    {

        // Creating StringRequest and set the JSON server URL in here.
        StringRequest stringRequest = new StringRequest(HTTP_URL+"?gameid="+Constants.CUURENT_GAMEID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // After done Loading store JSON response in FinalJSonObject string variable.
                        FinalJSonObject = response ;

                        // Calling method to parse JSON object.
                        new ReviewRead.ParseJSonDataClass(getActivity()).execute();

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
                        ReadReview samples;

                        // Defining CustomSubjectNamesList AS Array List.
                        CustomReadReview = new ArrayList<ReadReview>();

                        for (int i = 0; i < jsonArray.length(); i++) {

                            samples = new ReadReview();

                            jsonObject = jsonArray.getJSONObject(i);

                            //Storing ID into subject list.
                            if(jsonObject!=null)
                            {
                                String comment=jsonObject.getString("comments");
                                String reviewdate=jsonObject.getString("revdatetime");
                                String fname=jsonObject.getString("fname");
                                String lname=jsonObject.getString("lname");
                                String readreview_userimg=jsonObject.getString("profilepic");
                                double rating=jsonObject.getDouble("gamerating");
                                samples.comment=comment;
                                samples.reviewby=fname+"."+lname;
                                samples.reviewdate=reviewdate;
                                samples.readreview_userimage=readreview_userimg;
                                samples.readreview_displayratings= (float) rating;
                                samples.readreview_lblrating= String.valueOf(rating);
                            }else {
                                samples.comment="No data";
                                samples.reviewby= " No data";
                                samples.reviewdate=" No data";
                                samples.readreview_userimage="";
                                samples.readreview_displayratings=0;
                                samples.readreview_lblrating="No data" ;
                            }




                            // Adding subject list object into CustomSubjectNamesList.
                            CustomReadReview.add(samples);
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block

                        e.printStackTrace();
                    }
                }else
                {
                    ReadReview r=new ReadReview();
                    r.reviewdate="no";
                    r.reviewby="no";
                    r.comment="no";
                    CustomReadReview.add(r);
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
            ReadReviewAdapter adapter = new ReadReviewAdapter( context, (ArrayList<ReadReview>) CustomReadReview);

            // Setting up all data into ListView.
            lstreviewlist.setAdapter(adapter);

            // Hiding progress bar after all JSON loading done.
            // progressBar.setVisibility(View.GONE);

        }
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