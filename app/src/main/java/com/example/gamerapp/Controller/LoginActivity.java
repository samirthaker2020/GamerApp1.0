package com.example.gamerapp.Controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.gamerapp.ForgotPassword;
import com.example.gamerapp.Others.Constants;
import com.example.gamerapp.Others.DialogMessage;
import com.example.gamerapp.R;
import com.example.gamerapp.Others.SharedPref;
import com.example.gamerapp.Others.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
Button btnsigIn,btnsignUp,btnforgotPassword;
EditText emailId_input;
CheckBox remember;
EditText userPassword_input;

    Vibrator v;
    //change this to match your url
    final String loginURL = Constants.URL_LOGIN;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String uEmail = "userEmail";
    public static final String uPassword = "userPassword";
    public static final String ucheck = "false";
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
        remember=(CheckBox) findViewById(R.id.rememberme);
        userPassword_input=  findViewById(R.id.txtpassword);
        btnforgotPassword=(Button) findViewById(R.id.btnforgotpassword);

         sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        //check if user is logged in

        if (sharedpreferences.contains(uEmail)) {
            emailId_input.setText(sharedpreferences.getString(uEmail, ""));
        }
     /*   if (sharedpreferences.contains(uPassword)) {
            userPassword_input.setText(sharedpreferences.getString(uPassword, ""));
        }*/
        if (sharedpreferences.contains(ucheck)) {
           remember.setChecked(true);
        }



        btnforgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPassword.class);
                startActivity(intent);
            }
        });

        btnsigIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(remember.isChecked()==true)
                {

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(uEmail,emailId_input.getText().toString() );
                  //  editor.putString(uPassword, userPassword_input.getText().toString());
                    editor.putString(ucheck,"true");
                    editor.commit();
                    remember.setChecked(true);
                }else
                {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.remove(uEmail);
                //    editor.remove(uPassword);
                    editor.remove(ucheck);
                    editor.clear();
                    editor.commit(); // commit changes

                    remember.setChecked(false);
                }


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
                                singlemsg("Invalid",obj.getString("message"),LoginActivity.this,false);

                                userPassword_input.setText("");
                            } else {

                                //getting user name
                                String Username = obj.getString("username");
                                Constants.PROFILE_PIC=obj.getString("profilepic");
                                int Userid=obj.getInt("uid");
                                Constants.CURRENT_USER=Username;
                                Constants.CUURENT_USERID=Userid;


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
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //This is used to hide/show 'Status Bar' & 'System Bar'. Swip bar to get it as visible.
        View decorView = getWindow().getDecorView();
        if (hasFocus) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    public void singlemsg(String title, String msg, Context c, boolean type)
    {
        DialogMessage.singlemsg(title,msg,c,type);
    }
}
