package com.example.gamerapp.Controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.gamerapp.ForgotPassword;
import com.example.gamerapp.Others.Constants;
import com.example.gamerapp.Others.DialogMessage;
import com.example.gamerapp.Others.VolleySingleton;
import com.example.gamerapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RecoverPassword extends AppCompatActivity {
    String  PrecoveryUser;
    EditText pass0,pass1;
    TextView userTitle;
    Button btnupdatepassword,btnrecover_password;
    final String PrecoveryURL = Constants.URL_PASSWORDRECOVERY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);
        userTitle=(TextView) findViewById(R.id.usertTitle);
        getSupportActionBar().hide();
        //enable full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
            userTitle.setText("Hello, "+Constants.CURRENT_USER);
            pass0=(EditText) findViewById(R.id.recPassword0);
            pass1=(EditText) findViewById(R.id.recPassword1);
            btnupdatepassword=(Button) findViewById(R.id.btnrecovery);
btnrecover_password=(Button) findViewById(R.id.btncancel_recoverpassword);
            btnupdatepassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
validate();
                }
            });
btnrecover_password.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        finish();
        Intent intent = new Intent(RecoverPassword.this, MainActivity.class);
        startActivity(intent);
    }
});
        }

    public void validate()
    {
        pass0= (EditText) findViewById(R.id.recPassword0);
        pass1=(EditText) findViewById(R.id.recPassword1);
        if (TextUtils.isEmpty(pass0.getText().toString()))
        {
            pass0.setError("Please enter your all fields");
            pass0.requestFocus();

            btnupdatepassword.setEnabled(true);
            return;
        }
        if (TextUtils.isEmpty(pass1.getText().toString()))
        {
            pass1.setError("Please enter your all fields");
            pass1.requestFocus();

            btnupdatepassword.setEnabled(true);
            return;
        }
        if(pass0.getText().toString().equals(pass1.getText().toString()))
        {
            updatepassword();
        }else
        {
          //  singlemsg("Invalid","Password does not match! try Again",1);
            singlemsg("Invalid","Password does not match! Try Again",RecoverPassword.this,false);
            System.out.println(pass0.getText().toString());
            System.out.println(pass1.getText().toString());
        }
    }
    public void singlemsg(String title, String msg, Context c, boolean type)
    {
        DialogMessage.singlemsg(title,msg,c,type);
    }

    public void updatepassword()
    {

        //Call our volley library
        StringRequest stringRequest = new StringRequest(Request.Method.POST,PrecoveryURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //  Toast.makeText(getApplicationContext(),response.toString(), Toast.LENGTH_SHORT).show();

                            JSONObject obj = new JSONObject(response);
                            if (obj.getBoolean("error")) {
                                //  Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                              //  singlemsg("Invalid",obj.getString("message"),1);
                                singlemsg("Invalid",obj.getString("message"),RecoverPassword.this,false);
                                pass0.setText("");
                                pass1.setText("");
                            } else {

                              //  singlemsg("Sucess","Password Updated Sucessfully",0);
                                singlemsg("Invalid","Password updated Sucessfully",RecoverPassword.this,true);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        // yourMethod();
                                        finish();
                                        Intent intent = new Intent(RecoverPassword.this, MainActivity.class);
                                        //    intent.putExtra("userlogin", Username);
                                        startActivity(intent);
                                    }
                                }, 2000);


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
                params.put("userid", String.valueOf(Constants.CUURENT_USERID));
                params.put("password", pass0.getText().toString());


                return params;
            }
        };
        VolleySingleton.getInstance(RecoverPassword.this).addToRequestQueue(stringRequest);
    }
    }
