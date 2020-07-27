package com.example.gamerapp;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.gamerapp.Modal.ReviewHistory;
import com.example.gamerapp.Others.Constants;
import com.example.gamerapp.Others.DeleteReview;
import com.example.gamerapp.Others.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.gamerapp.Others.DialogMessage.singlemsg;

public class DeleteFragment extends Fragment {

    private BlankViewModel mViewModel;
    // Server Http URL
    String delete_URL = Constants.URL_DELETEREVIEW;
    public static DeleteFragment newInstance() {
        return new DeleteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.blank_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BlankViewModel.class);
        // TODO: Use the ViewModel
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
                                // Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                                singlemsg("Invalid",obj.getString("message"), getActivity(),false);
                                //  email_input.setText("");
                                //    password_input.setText("");
                            } else {

                                //getting user name
                                //  String Username = obj.getString("username");
                                //    Toast.makeText(getApplicationContext(),Username, Toast.LENGTH_SHORT).show();
                                singlemsg("SUCESS",obj.getString("message"),getActivity(),true);
                                //storing the user in shared preferences
                                //     SharedPref.getInstance(getApplicationContext()).storeUserName(Username);
                                //starting the profile activity
                               getActivity().finish();
                                Intent intent = new Intent(getActivity(), ReviewHistory.class);
                                // intent.putExtra("userlogin", Username);
                                startActivity(intent);
                                //startActivity(new Intent(getApplicationContext(), MainMenu.class));

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