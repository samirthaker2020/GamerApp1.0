package com.example.gamerapp.Controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.gamerapp.Others.Constants;
import com.example.gamerapp.R;
import com.example.gamerapp.Others.SharedPref;
import com.example.gamerapp.Others.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
Button btnsigIn,btnsignUp;
EditText emailId_input;
EditText userPassword_input;
    Vibrator v;
    //change this to match your url
    final String loginURL = Constants.URL_LOGIN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // hide the title bar
        getSupportActionBar().hide();
        //enable full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        btnsigIn= (Button) findViewById(R.id.btn_signin);
        btnsignUp=(Button) findViewById(R.id.btnsignup);
        emailId_input=   findViewById(R.id.txtemailid);
        userPassword_input=  findViewById(R.id.txtpassword);
        btnsigIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUserData();
            }
        });
        btnsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUp.class);
                startActivity(intent);
            }
        });
    }
    private void validateUserData() {

        //first getting the values
        final String email = emailId_input.getText().toString();
        final String password = userPassword_input.getText().toString();

        //checking if email is empty
        if (TextUtils.isEmpty(email)) {
            emailId_input.setError("Please enter your email");
            userPassword_input.requestFocus();
            // Vibrate for 100 milliseconds
            v.vibrate(100);
            btnsigIn.setEnabled(true);
            return;
        }
        //checking if password is empty
        if (TextUtils.isEmpty(password)) {
            userPassword_input.setError("Please enter your password");
            userPassword_input.requestFocus();
            //Vibrate for 100 milliseconds
            v.vibrate(100);
            btnsigIn.setEnabled(true);
            return;
        }
        //validating email
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailId_input.setError("Enter a valid email");
            emailId_input.requestFocus();
            //Vibrate for 100 milliseconds
            v.vibrate(100);
            btnsigIn.setEnabled(true);
            return;
        }

        //Login User if everything is fine
        loginUser();


    }
    private void loginUser() {

        //first getting the values
        final String email = emailId_input.getText().toString();
        final String password = userPassword_input.getText().toString();



        //Call our volley library
        StringRequest stringRequest = new StringRequest(Request.Method.POST,loginURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
//                            Toast.makeText(getApplicationContext(),response.toString(), Toast.LENGTH_SHORT).show();

                            JSONObject obj = new JSONObject(response);
                            if (obj.getBoolean("error")) {
                                //  Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                singlemsg("Invalid",obj.getString("message"));
                                emailId_input.setText("");
                                userPassword_input.setText("");
                            } else {

                                //getting user name
                                String Username = obj.getString("username");
                                int Userid=obj.getInt("uid");
                                Constants.CURRENT_USER=Username;
                                Constants.CUURENT_USERID=Userid;
                                Toast.makeText(getApplicationContext(),Username, Toast.LENGTH_SHORT).show();

                                //storing the user in shared preferences
                                SharedPref.getInstance(getApplicationContext()).storeUserName(Username);
                                //starting the profile activity
                                finish();
                                Intent intent = new Intent(LoginActivity.this, MainPage.class);
                                intent.putExtra("userlogin", Username);
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
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };
        VolleySingleton.getInstance(LoginActivity.this).addToRequestQueue(stringRequest);
    }


    public void singlemsg(String title,String msg)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
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
