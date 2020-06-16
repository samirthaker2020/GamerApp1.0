package com.example.gamerapp.ui.Logout;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gamerapp.Controller.MainPage;
import com.example.gamerapp.Others.SharedPref;
import com.example.gamerapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NavLogout#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavLogout extends Fragment {
TextView title;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NavLogout() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NavLogout.
     */
    // TODO: Rename and change types and number of parameters
    public static NavLogout newInstance(String param1, String param2) {
        NavLogout fragment = new NavLogout();
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
        View root = inflater.inflate(R.layout.fragment_nav_logout, container, false);

        title=root.findViewById(R.id.txtlogout_title);
        ((MainPage) getActivity()).getSupportActionBar().setTitle("Logout");
        SharedPref.getInstance(getActivity()).logout();

        getActivity().moveTaskToBack(true);
        getActivity().finish();
        return root;
    }


    public void showAlertDialogButtonClicked(View view) {

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("AlertDialog");
        builder.setMessage("Would you like to Logout from the App?");

        // add the buttons
        builder.setPositiveButton("Yes!logout", null);
        builder.setNegativeButton("Cancel", null);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}