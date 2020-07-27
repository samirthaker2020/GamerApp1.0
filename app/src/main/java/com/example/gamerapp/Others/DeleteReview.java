package com.example.gamerapp.Others;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.gamerapp.Controller.LoginActivity;
import com.example.gamerapp.Controller.SignUp;
import com.example.gamerapp.Modal.ReviewHistory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.gamerapp.Others.DialogMessage.singlemsg;

public class DeleteReview extends AppCompatActivity {

    // Server Http URL
    String delete_URL = Constants.URL_DELETEREVIEW;
    @Nullable


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
                                singlemsg("Invalid",obj.getString("message"), DeleteReview.this,false);
                                //  email_input.setText("");
                                //    password_input.setText("");
                            } else {

                                //getting user name
                                //  String Username = obj.getString("username");
                                //    Toast.makeText(getApplicationContext(),Username, Toast.LENGTH_SHORT).show();
                                singlemsg("SUCESS",obj.getString("message"),DeleteReview.this,true);
                                //storing the user in shared preferences
                                //     SharedPref.getInstance(getApplicationContext()).storeUserName(Username);
                                //starting the profile activity
                                finish();
                                Intent intent = new Intent(DeleteReview.this, ReviewHistory.class);
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
                        Toast.makeText(getApplicationContext(),"Connection Error"+error, Toast.LENGTH_SHORT).show();
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
        VolleySingleton.getInstance(DeleteReview.this).addToRequestQueue(stringRequest);
    }
}
