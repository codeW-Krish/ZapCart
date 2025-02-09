package com.example.zapcart.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.zapcart.R;
import com.example.zapcart.authentication.LogInActivity;
import com.example.zapcart.authentication.SessionManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

    TextView tv_username,tv_email;
    Button btn_logout,btn_login;
    SessionManager sessionManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        tv_username = view.findViewById(R.id.tv_username);
        tv_email = view.findViewById(R.id.tv_email);
        btn_logout = view.findViewById(R.id.btn_logout);
        btn_login = view.findViewById(R.id.btn_login);

        sessionManager = new SessionManager(requireContext());

        if(sessionManager.isLoggedIn()){
            tv_username.setText(sessionManager.getUserName());
            tv_email.setText(sessionManager.getUserEmail());
            btn_logout.setVisibility(View.VISIBLE);
            btn_login.setVisibility(View.GONE);
        }else{
            tv_username.setText("No Account");
            tv_email.setText("Please Log In");
            btn_logout.setVisibility(View.GONE);
            btn_login.setVisibility(View.VISIBLE);
        }


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logoutUser();
                Intent intent = new Intent(getContext(), LogInActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LogInActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });

        return view;
    }
}