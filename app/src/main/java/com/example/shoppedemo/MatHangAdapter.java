package com.example.shoppedemo;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MatHangAdapter extends ArrayAdapter<MatHang> {
    Activity context;
    int resource;
    public MatHangAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context=context;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View customView=this.context.getLayoutInflater().inflate(this.resource,null);

        ImageView imgHinhAnh=(ImageView)customView.findViewById(R.id.imgHinhAnhItem);
        TextView txtGia=(TextView)customView.findViewById(R.id.txtGiaItem);
        TextView txtTen=(TextView)customView.findViewById(R.id.txtTenItem);

        MatHang matHang=getItem(position);
        imgHinhAnh.setImageResource(matHang.getHinhAnh());
        txtGia.setText(matHang.getGia()+"VND");
        txtTen.setText(matHang.getTen());
        return customView;
    }
}
