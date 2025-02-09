package com.example.zapcart.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zapcart.R;
import com.example.zapcart.adapter.CartAdapter;
import com.example.zapcart.authentication.SessionManager;
import com.example.zapcart.database.CartEntity;
import com.example.zapcart.viewmodels.CartViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
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

    CartAdapter cartAdapter;
    CartViewModel cartViewModel;
    SessionManager sessionManager;
    TextView no_items;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        no_items = view.findViewById(R.id.no_items);

        sessionManager = new SessionManager(requireContext());
        if(sessionManager.isLoggedIn()){
            RecyclerView cartRecyclerView = view.findViewById(R.id.cartRecyclerView);
            cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            cartAdapter = new CartAdapter(getContext());
            cartRecyclerView.setAdapter(cartAdapter);


            cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
            cartViewModel.getCartItemsLiveData().observe(getViewLifecycleOwner(), new Observer<List<CartEntity>>() {
                @Override
                public void onChanged(List<CartEntity> cartItems) {
                    cartAdapter.setCartItems(cartItems);
                }
            });

            cartViewModel.fetchCartItems();
        }else{
            no_items.setVisibility(View.VISIBLE);
        }

        return view;
    }
}