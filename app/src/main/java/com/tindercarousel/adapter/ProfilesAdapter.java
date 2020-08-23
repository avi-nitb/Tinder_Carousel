package com.tindercarousel.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tindercarousel.R;
import com.tindercarousel.pojo.UserData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilesAdapter extends RecyclerView.Adapter<ProfilesAdapter.MyViewHolder> {
    private Context context;
    private List<UserData.Results> listData;

    public ProfilesAdapter(Context context, List<UserData.Results> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.cardviewdetails, parent, false);
        return (new MyViewHolder(itemView));
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final UserData.Results currentData = listData.get(position);


        Picasso.get().load(currentData.getUser().getPicture()).into(holder.image_view_profile_pic);

        holder.textViewLabel.setText("My name is");
        holder.textViewData.setText(currentData.getUser().getName().getFirst() + " " +
                currentData.getUser().getName().getLast());
        holder.imgButton_profile.setElevation(10);

        holder.imgButton_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.textViewLabel.setText("My name is");
                holder.textViewData.setText(currentData.getUser().getName().getFirst() + " " +
                        currentData.getUser().getName().getLast());
                holder.imgButton_profile.setElevation(10);
            }
        });
        holder.imgButton_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.textViewLabel.setText("My address is");
                holder.textViewData.setText(currentData.getUser().getLocation().getStreet());
                holder.imgButton_address.setElevation(20);
                holder.imgButton_address.setOutlineSpotShadowColor(Color.parseColor("#76FF03"));
            }
        });

        holder.imgButton_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.textViewLabel.setText("Birthday");
                holder.textViewData.setText(millsToDate(Long.parseLong(currentData.getUser().getDob())));
                holder.imgButton_info.setElevation(10);
            }
        });

        holder.imgButton_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.textViewLabel.setText("My contact is");
                holder.textViewData.setText(currentData.getUser().getPhone());
                holder.imgButton_contact.setElevation(10);
            }
        });

        holder.imgButton_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.textViewLabel.setText("My username is");
                holder.textViewData.setText(currentData.getUser().getUsername());
                holder.imgButton_lock.setElevation(10);
            }
        });

    }

    @Override
    public int getItemCount() {
        try {
            return listData.size();
        } catch (Exception e) {
            return listData.size()-1;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewLabel, textViewData;
        ImageButton imgButton_profile, imgButton_info, imgButton_address, imgButton_contact, imgButton_lock;
        CircleImageView image_view_profile_pic;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewData = itemView.findViewById(R.id.textViewData);
            textViewLabel = itemView.findViewById(R.id.textViewLabel);
            imgButton_profile = itemView.findViewById(R.id.imgButton_profile);
            imgButton_info = itemView.findViewById(R.id.imgButton_info);
            imgButton_address = itemView.findViewById(R.id.imgButton_address);
            imgButton_contact = itemView.findViewById(R.id.imgButton_contact);
            imgButton_lock = itemView.findViewById(R.id.imgButton_lock);
            image_view_profile_pic = itemView.findViewById(R.id.image_view_profile_pic);

        }
    }

    @SuppressLint("SimpleDateFormat")
    private static String millsToDate(long mills) {

        Date d = new Date(mills);
        String format = "MM/dd/yyyy";
        return new SimpleDateFormat(format).format(d);
    }

}
