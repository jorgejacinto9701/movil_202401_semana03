package com.example.semana02.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.semana02.R;
import com.example.semana02.entity.Product;

import java.util.List;

public class UserAdapter extends ArrayAdapter<Product>  {

    private Context context;
    private  List<Product> objects;
    public UserAdapter(@NonNull Context context, int resource, @NonNull List<Product> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.product_item, parent, false);

        Product obj = objects.get(position);

        TextView txtName = row.findViewById(R.id.idItemProductTitle);
        txtName.setText(obj.getTitle());

        ImageView imgFoto = row.findViewById(R.id.idItemProductImage);
        Glide.with(context).load(obj.getImage()).into(imgFoto);

        TextView txtWebsite= row.findViewById(R.id.idItemProductPrice);
        txtWebsite.setText("Price : $" +  obj.getPrice());

        return row;
    }
}
