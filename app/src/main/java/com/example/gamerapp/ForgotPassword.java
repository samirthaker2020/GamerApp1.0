package com.example.gamerapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.gamerapp.Controller.LoginActivity;
import com.example.gamerapp.Controller.MainActivity;
import com.example.gamerapp.Controller.MainPage;
import com.example.gamerapp.Controller.RecoverPassword;
import com.example.gamerapp.Others.Constants;
import com.example.gamerapp.Others.DialogMessage;
import com.example.gamerapp.Others.SharedPref;
import com.example.gamerapp.Others.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotPassword extends AppCompatActivity {
    Button btnforgotpassword,btncancel_fp;

    EditText Femailid;
    final String forgotpasswordURL = Constants.URL_FORGOTPASSWORD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().hide();
        //enable full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Femailid=(EditText) findViewById(R.id.EditText_ForgotEmailid);
        btnforgotpassword =(Button) findViewById(R.id.btnforgotpassword);
        btncancel_fp=(Button) findViewById(R.id.btncancel_fp);
        btnforgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateUserData();
            }
        });
btncancel_fp.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        finish();
        Intent intent = new Intent(ForgotPassword.this, MainActivity.class);
        startActivity(intent);

    }
});
    }

    private void validateUserData() {
        if (TextUtils.isEmpty(Femailid.getText().toString()))
        {
            Femailid.setError("Please enter your Email");
            Femailid.requestFocus();

            btnforgotpassword.setEnabled(true);
            return;
        }else {

            loginUser();
        }
    }

    private void loginUser() {
        final String checkemail=Femailid.getText().toString();
     //   Toast.makeText(getApplicationContext(),checkemail, Toast.LENGTH_SHORT).show();
        //Call our volley library
        StringRequest stringRequest = new StringRequest(Request.Method.POST,forgotpasswordURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
//                            Toast.makeText(getApplicationContext(),response.toString(), Toast.LENGTH_SHORT).show();

                            JSONObject obj = new JSONObject(response);
                            if (obj.getBoolean("error")) {
                             //     Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                singlemsg("Invalid",obj.getString("msg"),ForgotPassword.this,false);
                                Femailid.setText("");

                            } else {

                                //getting user name
                                String Username = obj.getString("username");
                                int Userid=obj.getInt("uid");
                                Constants.CURRENT_USER=Username;
                                Constants.CUURENT_USERID=Userid;
                               // Toast.makeText(getApplicationContext(),Username, Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(ForgotPassword.this, RecoverPassword.class);
                                startActivity(intent);
                                finish();

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
                params.put("remailid", checkemail);


                return params;
            }
        };
        VolleySingleton.getInstance(ForgotPassword.this).addToRequestQueue(stringRequest);
    }


    public void singlemsg(String title, String msg, Context c, boolean type)
    {
        DialogMessage.singlemsg(title,msg,c,type);
    }
}