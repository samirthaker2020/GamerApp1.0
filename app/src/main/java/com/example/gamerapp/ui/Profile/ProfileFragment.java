package com.example.gamerapp.ui.Profile;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.text.method.KeyListener;
import android.util.Base64;
import android.util.Log;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import com.example.gamerapp.Others.DialogMessage;
import com.example.gamerapp.Others.ProfileImage;
import com.example.gamerapp.Others.SharedPref;
import com.example.gamerapp.Others.VolleySingleton;
import com.example.gamerapp.R;
import com.example.gamerapp.ui.home.HomeFragment1;
import com.google.android.material.navigation.NavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ProfileFragment extends Fragment {
    final String loginURL = Constants.USER_PROFILE;
    final String userUpdateURL = Constants.URL_UPDATEPROFILE;
    final String userProfileImage = Constants.URL_PROFILEIMAGE;
    Button btnimageupload,btnupload;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;
  ToggleButton btnUpdate;
  ImageView profileimage;
    private static final int REQUEST_LOCATION = 1;
    String path=null;
    String img_str=null;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    View view;
    LayoutInflater layoutInflater;
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
        pContactno.setInputType(InputType.TYPE_CLASS_NUMBER);
        btnimageupload=(Button) root.findViewById(R.id.btnuploadimage);
        profileimage=(ImageView)  root.findViewById(R.id.txtprofile_image);
        btnupload=(Button) root.findViewById(R.id.btnupload);
        KeyListener keyListener = DigitsKeyListener.getInstance("1234567890");
       pContactno.setKeyListener(keyListener);
       pFname.setFilters(new InputFilter[] {
                new InputFilter() {
                    public CharSequence filter(CharSequence src, int start,
                                               int end, Spanned dst, int dstart, int dend) {
                        if(src.toString().matches("[a-zA-Z ]+")){
                            return src;
                        }
                        return "";
                    }
                }});
        pLname.setFilters(new InputFilter[] {
                new InputFilter() {
                    public CharSequence filter(CharSequence src, int start,
                                               int end, Spanned dst, int dstart, int dend) {
                        if(src.toString().matches("[a-zA-Z ]+")){
                            return src;
                        }
                        return "";
                    }
                }});

profileimage.setClipToOutline(true);
        layoutInflater = LayoutInflater.from(getActivity());
         view = inflater.inflate(R.layout.activity_main_page, container, false);


        profileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        fetchuser();
        final DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        pDob.setShowSoftInputOnFocus(false);
        pDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(), dateListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                //following line to restrict future date selection
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
        btnUpdate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {



                if (isChecked) {
                    // The toggle is enabled
                //    StringBuilder result = new StringBuilder();
                 //   result.append("ToggleButton1 : ").append(btnUpdate.getText());

                    //Displaying the message in toast
                //    Toast.makeText(getActivity(), result.toString(),Toast.LENGTH_LONG).show();
               btnenable();
                } else {
                    // The toggle is disabled
                    StringBuilder result = new StringBuilder();
                    result.append("ToggleButton1 : ").append(btnUpdate.getText());

                    //Displaying the message in toast
               //     Toast.makeText(getActivity(), result.toString(),Toast.LENGTH_LONG).show();
                    isvalid();


                }
            }
        });

btnupload.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (profileimage.getDrawable() == null) {
            singlemsg("Invalid","Please select image first",getActivity(),false);

        } else {
        updateProfileImage();
    }
    }
});
        btnimageupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPictureDialog();

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
    private void showPictureDialog(){
        android.app.AlertDialog.Builder pictureDialog = new android.app.AlertDialog.Builder(getActivity());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:

                                choosePhotoFromGallary();
                                btnupload.setEnabled(true);
                                break;
                            case 1:
                                takePhotoFromCamera();
                                btnupload.setEnabled(true);
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }
    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void takePhotoFromCamera() {
        if (getActivity().checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    MY_CAMERA_REQUEST_CODE);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,22);
    }
    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.createScaledBitmap(myBitmap,(int)(myBitmap.getWidth()*0.8), (int)(myBitmap.getHeight()*0.8), true);

        myBitmap.compress(Bitmap.CompressFormat.JPEG, 30, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(getActivity(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void  requestMultiplePermissions(){
        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getActivity(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                    }


                });

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 110) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                takePhotoFromCamera();
            }
        }}
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
                     path = saveImage(bitmap);
                    Toast.makeText(getActivity(), "Image Saved!"+path, Toast.LENGTH_SHORT).show();
                   profileimage.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == 22) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            profileimage.setImageBitmap(thumbnail);
            path=saveImage(thumbnail);
            Toast.makeText(getActivity(), "Image Saved!"+path, Toast.LENGTH_SHORT).show();
        }
    }











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
        pContactno.setInputType(InputType.TYPE_CLASS_NUMBER);
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

        btnUpdate.setChecked(false);
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
                                singlemsg("Invalid",obj.getString("message"),getActivity(),false);

                            } else {

                                //getting user name
                                String fname = obj.getString("fname");
                                String lname = obj.getString("lname");
                                String email = obj.getString("email");
                                String contactno = obj.getString("contactno");
                                String dob = obj.getString("dob");
                                String profilepic = obj.getString("profilepic");

                                int Userid=obj.getInt("uid");

                                pFname.setText(fname);
                                pLname.setText(lname);
                                pEmail.setText(email);
                                pContactno.setText(contactno);
                                pDob.setText(dob);
                                profileimage.setImageBitmap(ProfileImage.StringToBitMap(profilepic));


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


    public void singlemsg(String title, String msg, Context c, boolean type)
    {
        DialogMessage.singlemsg(title,msg,c,type);
    }

    private void updateUser() {
        //Call our volley library
        StringRequest stringRequest = new StringRequest(Request.Method.POST,userUpdateURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
//                            Toast.makeText(getApplicationContext(),response.toString(), Toast.LENGTH_SHORT).show();

                            JSONObject obj = new JSONObject(response);
                            if (obj.getBoolean("error")) {
                                // Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                                singlemsg("Invalid",obj.getString("message"),getActivity(),false);
                                //  email_input.setText("");
                                //    password_input.setText("");
                            } else {

                                //getting user name
                                //  String Username = obj.getString("username");
                                //    Toast.makeText(getApplicationContext(),Username, Toast.LENGTH_SHORT).show();
                                singlemsg("SUCESS",obj.getString("message"),getActivity(),true);
                                Constants.CURRENT_USER=pFname.getText().toString()+" "+pLname.getText().toString();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        // yourMethod();
                                        Intent intent = new Intent(getActivity(), MainPage.class);

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
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", String.valueOf(Constants.CUURENT_USERID));
                params.put("fname", pFname.getText().toString());
                params.put("lname", pLname.getText().toString());
                params.put("email", pEmail.getText().toString());

                params.put("contactno", pContactno.getText().toString());
                params.put("dob", pDob.getText().toString());
                return params;
            }
        };
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    private void updateProfileImage() {

        if (profileimage.getDrawable() == null) {
            img_str = "error";

        } else {

            try {
               Bitmap bitmap = ((BitmapDrawable) profileimage.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap = Bitmap.createScaledBitmap(bitmap,(int)(bitmap.getWidth()*0.8), (int)(bitmap.getHeight()*0.8), true);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 40, stream);
                byte[] byte_arr = stream.toByteArray();
              img_str = Base64.encodeToString(byte_arr, Base64.DEFAULT);
              reloadfrg();
            }catch(Exception e){
                img_str = "errorinconverting";
            }

        }

        //Call our volley library
        StringRequest stringRequest = new StringRequest(Request.Method.POST,userProfileImage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
//                            Toast.makeText(getApplicationContext(),response.toString(), Toast.LENGTH_SHORT).show();

                            JSONObject obj = new JSONObject(response);
                            if (obj.getBoolean("error")) {
                                // Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                                singlemsg("Invalid",obj.getString("message"),getActivity(),false);
                                //  email_input.setText("");
                                //    password_input.setText("");
                            } else {

                                //getting user name
                                //  String Username = obj.getString("username");
                                //    Toast.makeText(getApplicationContext(),Username, Toast.LENGTH_SHORT).show();
                                singlemsg("SUCESS",obj.getString("message"),getActivity(),true);
                                Constants.PROFILE_PIC=img_str;
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        // yourMethod();
                                        Intent intent = new Intent(getActivity(), MainPage.class);

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
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", String.valueOf(Constants.CUURENT_USERID));
                params.put("image", img_str);

                return params;
            }
        };
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    public void reloadfrg()
    {


        // Reload current fragment
        Fragment fr = new ProfileFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fr).addToBackStack("HomeFragment");
        fragmentTransaction.commit();
    }

    public void isvalid()
    {
        if (pContactno.getText().toString().trim().length() < 10) {


            pContactno.setError("Please enter your valid 10 digit number");
            pContactno.requestFocus();
            btnUpdate.setChecked(true);
            btnUpdate.setTextOn("SAVE");
            btnenable();
        }
        //checking if email is empty
       else if (TextUtils.isEmpty(pFname.getText().toString())) {
            pFname.setError("Please enter your FirstName");
            pFname.requestFocus();

            btnUpdate.setChecked(true);
            btnUpdate.setTextOn("SAVE");
            btnenable();
        }else if (TextUtils.isEmpty(pLname.getText().toString())) {
            pLname.setError("Please enter your LastName");
            pLname.requestFocus();

            btnUpdate.setChecked(true);
            btnUpdate.setTextOn("SAVE");
            btnenable();
        } else if (TextUtils.isEmpty(pEmail.getText().toString())) {
        pEmail.setError("Please enter your Email ID");
        pEmail.requestFocus();

        btnUpdate.setChecked(true);
        btnUpdate.setTextOn("SAVE");
        btnenable();
    } else if (TextUtils.isEmpty(pContactno.getText().toString())) {
        pContactno.setError("Please enter your Phone Numbar");
        pContactno.requestFocus();

        btnUpdate.setChecked(true);
        btnUpdate.setTextOn("SAVE");
        btnenable();
    }else if (TextUtils.isEmpty(pDob.getText().toString())) {
        pDob.setError("Please enter your Date Of Birth");
        pDob.requestFocus();

        btnUpdate.setChecked(true);
        btnUpdate.setTextOn("SAVE");
        btnenable();
    }else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(pEmail.getText().toString()).matches()) {
            pEmail.setError("Please enter your valid Email ID");
            pEmail.requestFocus();

            btnUpdate.setChecked(true);
            btnUpdate.setTextOn("SAVE");
            btnenable();
    }
        else
        {
            updateUser();
            btndisable();
            reloadfrg();
        }
    }
}
