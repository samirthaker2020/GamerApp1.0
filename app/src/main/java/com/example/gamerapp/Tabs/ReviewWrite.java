package com.example.gamerapp.Tabs;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.gamerapp.Controller.GameDetails;
import com.example.gamerapp.Controller.MainPage;
import com.example.gamerapp.Others.Constants;
import com.example.gamerapp.Others.VolleySingleton;
import com.example.gamerapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReviewWrite#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReviewWrite extends Fragment {
EditText edittxt_Wreview;
Button   btnsubmitreview;
float  getrating;
    private RatingBar Wreview_rBar;
    private TextView tViewRating;
    private Button btnrating;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    final String writeURL = Constants.URL_WRITEREVIEW;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReviewWrite() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReviewWrite.
     */
    // TODO: Rename and change types and number of parameters
    public static ReviewWrite newInstance(String param1, String param2) {
        ReviewWrite fragment = new ReviewWrite();
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
        View root = inflater.inflate(R.layout.fragment_review_write, container, false);
        edittxt_Wreview=(EditText) root.findViewById(R.id.edittext_review);
        btnsubmitreview=(Button) root.findViewById(R.id.btn_Wreview);

 btnsubmitreview.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         isvalid();
     }
 });
        Wreview_rBar = (RatingBar) root.findViewById(R.id.wr_ratingBar1 );
        tViewRating = (TextView) root.findViewById(R.id.lbl_write_rating);

       Wreview_rBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
           @Override
           public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
               int noofstars = Wreview_rBar.getNumStars();
                getrating = Wreview_rBar.getRating();
               System.out.println(getrating);
               tViewRating.setText("Rating: "+getrating+"/"+noofstars);
           }
       });
        return root;
    }

    public void isvalid()
    {

        //checking if email is empty
        if (TextUtils.isEmpty(edittxt_Wreview.getText().toString()) || getrating==0)
        {
            edittxt_Wreview.setError("Please enter your review and select stars");
            edittxt_Wreview.requestFocus();

            btnsubmitreview.setEnabled(true);
            return;
        }else {

       writereview();
        }
    }

    private void writereview() {


        //Call our volley library
        StringRequest stringRequest = new StringRequest(Request.Method.POST,writeURL ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
//                            Toast.makeText(getApplicationContext(),response.toString(), Toast.LENGTH_SHORT).show();

                            JSONObject obj = new JSONObject(response);
                            if (obj.getBoolean("error")) {
                                // Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                                singlemsg("Invalid",obj.getString("message"));
                                //  email_input.setText("");
                                //    password_input.setText("");
                            } else {


                                singlemsg("SUCESS",obj.getString("message"));
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        // yourMethod();
                                        getActivity().finish();
                                        Intent intent = new Intent(getActivity(), GameDetails.class);

                                        startActivity(intent);
                                    }
                                }, 2000);


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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("writegameid", Constants.CUURENT_GAMEID);
                params.put("writereview", edittxt_Wreview.getText().toString());
                params.put("reviewdate", currentdatetime());
                params.put("rating",String.valueOf(getrating));
                params.put("uid",String.valueOf(Constants.CUURENT_USERID));


                return params;
            }
        };
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }
@RequiresApi(api = Build.VERSION_CODES.O)
public String currentdatetime()
{
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh.mm aa");
    String formattedDate = dateFormat.format(new Date()).toString();
   return formattedDate;
}
    public void singlemsg(String title,String msg)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage(msg);
        builder1.setCancelable(true);
        builder1.setIcon(R.drawable.ic_sucess_foreground);
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