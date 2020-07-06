package com.example.gamerapp.Controller;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.gamerapp.Others.Constants;
import com.example.gamerapp.R;
import com.example.gamerapp.Others.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    EditText dob;
    Button btnregister;
    final String signupURL = Constants.URL_USER_REGISTER;
    EditText ufname,ulname,uemail,upassword,urepassword,ucontactno,udob;
    final Calendar myCalendar = Calendar.getInstance();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // hide the title bar
        getSupportActionBar().hide();
        //enable full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dob=(EditText) findViewById(R.id.udob);
        ufname=(EditText) findViewById(R.id.ufname);
        ulname=(EditText) findViewById(R.id.ulname);
        uemail=(EditText) findViewById(R.id.uemail);
        upassword=(EditText) findViewById(R.id.txtpassword);
        urepassword=(EditText) findViewById(R.id.urepassword);
        ucontactno=(EditText) findViewById(R.id.ucontactno);
        udob=(EditText) findViewById(R.id.udob);
        btnregister=(Button) findViewById(R.id.btnregister);
        dob.setShowSoftInputOnFocus(false);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SignUp.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
isvalid();
            }
        });


    }
    public void isvalid()
    {

        //checking if email is empty
        if (TextUtils.isEmpty(uemail.getText().toString()) || TextUtils.isEmpty(ufname.getText().toString()) || TextUtils.isEmpty(ulname.getText().toString()) || TextUtils.isEmpty(upassword.getText().toString()) || TextUtils.isEmpty(urepassword.getText().toString()) || TextUtils.isEmpty(ucontactno.getText().toString()) || TextUtils.isEmpty(udob.getText().toString()))
        {
            singlemsg("Invalid" ,"Enter All Feilds First");

            btnregister.setEnabled(true);
            return;
        }
        //validating email
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(uemail.getText().toString()).matches()) {
            singlemsg("Invalid" ,"Enter valid Email Address");
            btnregister.setEnabled(true);
            return;
        }
        if ( upassword.getText().toString().equals (urepassword.getText().toString())) {

        }else {
            singlemsg("Invalid" ,"Password Does not Match");
            btnregister.setEnabled(true);
            return;
        }
        System.out.println(upassword.getText().toString());
        System.out.println(urepassword.getText().toString());
        registerUser();
    }

    private void registerUser() {





        //Call our volley library
        StringRequest stringRequest = new StringRequest(Request.Method.POST,signupURL,
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

                                //getting user name
                                //  String Username = obj.getString("username");
                                //    Toast.makeText(getApplicationContext(),Username, Toast.LENGTH_SHORT).show();
                                singlemsg("SUCESS",obj.getString("message"));
                                //storing the user in shared preferences
                                //     SharedPref.getInstance(getApplicationContext()).storeUserName(Username);
                                //starting the profile activity
                                finish();
                                Intent intent = new Intent(SignUp.this, LoginActivity.class);
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
                params.put("fname", ufname.getText().toString());
                params.put("lname", ulname.getText().toString());
                params.put("email", uemail.getText().toString());
                params.put("password", upassword.getText().toString());
                params.put("repassword", urepassword.getText().toString());
                params.put("contactno", ucontactno.getText().toString());
                params.put("dob", udob.getText().toString());
                return params;
            }
        };
        VolleySingleton.getInstance(SignUp.this).addToRequestQueue(stringRequest);
    }

    public void singlemsg(String title,String msg)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
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


    final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
          dob.setInputType(InputType.TYPE_NULL);
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            updateLabel();
        }

    };

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dob.setText(sdf.format(myCalendar.getTime()));
    }

}