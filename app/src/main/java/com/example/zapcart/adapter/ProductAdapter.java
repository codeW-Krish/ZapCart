package com.example.zapcart.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.zapcart.R;
import com.example.zapcart.fragments.HomeFragment;
import com.example.zapcart.models.Product;
import com.example.zapcart.models.ProductResponse;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.viewHolder>{

    private List<Product> productList = new ArrayList<>();
    private Context context;
    private OnProductClickListener listener;


    public interface OnProductClickListener {
        void onProductClick(Product product);
    }
//
//    public ProductAdapter(Context context, List<Product> productList,OnProductClickListener onProductClickListener){
//        this.context = context;
//        this.productList = productList;
//        this.listener = onProductClickListener;
//    }

    public ProductAdapter(Context context,OnProductClickListener onProductClickListener){
        this.context = context;
        this.listener = onProductClickListener;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_design,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Product product = productList.get(position);
        // Reset the layout parameters if necessary
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT; // Or set a fixed height if necessary
        holder.itemView.setLayoutParams(layoutParams);


        Glide.with(context)
                .load(product.thumbnail)  // URL or resource
                .into(holder.product_img);

        // Debug log to ensure the data is passed properly
        Log.d("ProductAdapter", "Binding product: " + product.title);
        holder.product_title.setText(product.title);
        holder.product_price.setText(String.format("$%.2f", product.price));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onProductClick(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(productList != null){
            return productList.size();
        }
        Log.d("ProductAdapter","No Items");
        return 0;
    }

//    public void setProductList(List<Product> newProductList){
//        if(newProductList == null || newProductList.isEmpty()){
//            Log.e("ProductAdapter", "Received empty or null product list");
//            return;
//        }
//
//        int oldSize = productList.size();
//        productList.addAll(oldSize,newProductList);
//        notifyItemRangeInserted(oldSize, newProductList.size());
//
//        Log.d("ProductAdapter", "Updated product list with " + newProductList.size() + " items");
//    }

    public void setProductList(List<Product> newProductList) {
        if (newProductList == null || newProductList.isEmpty()) {
            Log.e("ProductAdapter", "Received empty or null product list");
            return;
        }
        productList.clear();
        productList.addAll(newProductList);

        notifyDataSetChanged();

        Log.d("ProductAdapter", "Updated product list with " + newProductList.size() + " items");
    }


    static class viewHolder extends RecyclerView.ViewHolder{
        ImageView product_img;
        TextView product_title,product_price;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            product_img = itemView.findViewById(R.id.product_img);
            product_title = itemView.findViewById(R.id.product_title);
            product_price = itemView.findViewById(R.id.product_price);
        }
    }
}
