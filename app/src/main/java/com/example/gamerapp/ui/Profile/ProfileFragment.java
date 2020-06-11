package com.example.gamerapp.ui.Profile;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.gamerapp.Controller.LoginActivity;
import com.example.gamerapp.Controller.MainPage;
import com.example.gamerapp.Controller.SignUp;
import com.example.gamerapp.Others.Constants;
import com.example.gamerapp.Others.SharedPref;
import com.example.gamerapp.Others.VolleySingleton;
import com.example.gamerapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ProfileFragment extends Fragment {
    final String loginURL = Constants.USER_PROFILE;
  ToggleButton btnUpdate;
  EditText edtdob;
    private profileViewModel profileViewModel;
    final Calendar myCalendar = Calendar.getInstance();
    private EditText pFname,pLname,pEmail,pContactno,pDob;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        profileViewModel =
                ViewModelProviders.of(this).get(profileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        final TextView textView = root.findViewById(R.id.txtprofile_username);
        pContactno= root.findViewById(R.id.editText_contactno);
        pDob=root.findViewById(R.id.editText_dob);
        pEmail=root.findViewById(R.id.editText_email);
        pFname=root.findViewById(R.id.editText_fname);
        pLname=root.findViewById(R.id.editText_lname);
        btnUpdate=root.findViewById(R.id.btn_update);

        profileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        fetchuser();
        pDob.setShowSoftInputOnFocus(false);
        pDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        btnUpdate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
               btnenable();
                } else {
                    // The toggle is disabled
                   btndisable();
                }
            }
        });

        return root;
    }

    final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            pDob.setInputType(InputType.TYPE_NULL);
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

        pDob.setText(sdf.format(myCalendar.getTime()));
    }

    private void btnenable()
    {
        pFname.setEnabled(true);
        pFname.setInputType(InputType.TYPE_CLASS_TEXT);
        pFname.setFocusable(true);

        pLname.setEnabled(true);
        pLname.setInputType(InputType.TYPE_CLASS_TEXT);
        pLname.setFocusable(true);

        pContactno.setEnabled(true);
        pContactno.setInputType(InputType.TYPE_CLASS_TEXT);
        pContactno.setFocusable(true);

        pDob.setEnabled(true);
        pDob.setInputType(InputType.TYPE_CLASS_TEXT);
        pDob.setFocusable(true);

        pEmail.setEnabled(true);
        pEmail.setInputType(InputType.TYPE_CLASS_TEXT);
        pEmail.setFocusable(true);
    }
    private void btndisable()
    {
        pFname.setEnabled(false);
        pFname.setInputType(InputType.TYPE_NULL);
        pFname.setFocusable(false);

        pLname.setEnabled(false);
        pLname.setInputType(InputType.TYPE_NULL);
        pLname.setFocusable(false);

        pContactno.setEnabled(false);
        pContactno.setInputType(InputType.TYPE_NULL);
        pContactno.setFocusable(false);

        pDob.setEnabled(false);
        pDob.setInputType(InputType.TYPE_NULL);
        pDob.setFocusable(false);

        pEmail.setEnabled(false);
        pEmail.setInputType(InputType.TYPE_NULL);
        pEmail.setFocusable(false);
    }
    private void fetchuser() {

        //first getting the values
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

                            } else {

                                //getting user name
                                String fname = obj.getString("fname");
                                String lname = obj.getString("lname");
                                String email = obj.getString("email");
                                String contactno = obj.getString("contactno");
                                String dob = obj.getString("dob");
                                int Userid=obj.getInt("uid");

                                pFname.setText(fname);
                                pLname.setText(lname);
                                pEmail.setText(email);
                                pContactno.setText(contactno);
                                pDob.setText(dob);


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
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", String.valueOf(Constants.CUURENT_USERID));


                return params;
            }
        };
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
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
