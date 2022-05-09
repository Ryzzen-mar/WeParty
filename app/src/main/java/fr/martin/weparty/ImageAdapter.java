package fr.martin.weparty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private final Context mContext;
    private ArrayList<String> mImageList;
    private List<Upload> mUploads;

    public ImageAdapter(List<Upload> uploads, ArrayList<String> imageList, Context context) {
        mUploads = uploads;
        mContext = context;
        mImageList = imageList;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item,parent,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        // loading the images from the position
        Upload uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
        holder.textViewDate.setText(uploadCurrent.getDate());
        holder.textViewLocation.setText(uploadCurrent.getLocation());
        Glide.with(holder.itemView.getContext()).load(mUploads.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewDate;
        public TextView textViewLocation;
        ImageView imageView;
        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.item);
            textViewName=itemView.findViewById(R.id.text_view_name);
            textViewDate=itemView.findViewById(R.id.text_view_date);
            textViewLocation=itemView.findViewById(R.id.text_view_location);
        }
    }
}
