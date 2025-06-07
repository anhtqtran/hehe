package com.tranthanhqueanh.myapplication.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.tranthanhqueanh.myapplication.R;
import com.tranthanhqueanh.myapplication.models.Product;

import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Product> {
    public ProductAdapter(Context context, ArrayList<Product> products) {
        super(context, 0, products);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_product, parent, false);
        }
        Product product = getItem(position);

        ImageView ivProductImage = convertView.findViewById(R.id.ivProductImage);
        TextView tvProductName = convertView.findViewById(R.id.tvProductName);
        TextView tvUnitPrice = convertView.findViewById(R.id.tvUnitPrice);

        tvProductName.setText(product.getProductName());
        tvUnitPrice.setText(String.format("%,.0f VNĐ", product.getUnitPrice()));
        Glide.with(getContext()).load(product.getImageLink()).into(ivProductImage);

        return convertView;
    }
}