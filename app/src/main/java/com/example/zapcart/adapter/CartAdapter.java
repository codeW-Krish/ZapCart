package com.example.zapcart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.zapcart.R;
import com.example.zapcart.database.CartEntity;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartEntity> cartItems = new ArrayList<>();
    private Context context;

    public CartAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_design, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartEntity cartItem = cartItems.get(position);
        holder.titleTextView.setText(cartItem.getTitle());
        holder.priceTextView.setText(String.valueOf(cartItem.getPrice()));
        holder.quantityTextView.setText(String.valueOf(cartItem.getQuantity()));
        Glide.with(context).load(cartItem.getThumbnail()).into(holder.productImageView);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public void setCartItems(List<CartEntity> newCartItems) {
        this.cartItems = newCartItems;
        notifyDataSetChanged();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView priceTextView;
        private TextView quantityTextView;
        private ImageView productImageView;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.cart_product_title);
            priceTextView = itemView.findViewById(R.id.cart_product_price);
            quantityTextView = itemView.findViewById(R.id.cart_product_quantity);
            productImageView = itemView.findViewById(R.id.cart_product_img);
        }

    }
}
