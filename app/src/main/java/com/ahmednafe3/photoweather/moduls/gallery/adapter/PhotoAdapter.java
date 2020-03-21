package com.ahmednafe3.photoweather.moduls.gallery.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ahmednafe3.photoweather.R;
import com.ahmednafe3.photoweather.databinding.PhotoItemLayoutBinding;
import com.ahmednafe3.photoweather.moduls.gallery.data.model.PhotoModel;

import java.util.List;


public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoHolder> {
    private List<PhotoModel> pictureList;
    private PhotoAdapterCallback callback;


    public PhotoAdapter(List<PhotoModel> pictureList, PhotoAdapterCallback mCallback) {
        this.pictureList = pictureList;
        this.callback = mCallback;
    }

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PhotoItemLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.photo_item_layout, parent, false);
        PhotoAdapter.PhotoHolder holder = new PhotoHolder(binding.getRoot());

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {
        PhotoModel photoModel = pictureList.get(position);
        holder.bind(photoModel);
    }

    @Override
    public int getItemCount() {
        return pictureList.size();
    }

    public class PhotoHolder extends RecyclerView.ViewHolder {
        public ImageView picture;


        public PhotoHolder(@NonNull View itemView) {
            super(itemView);
            picture = itemView.findViewById(R.id.image_place);

        }

        public void bind(PhotoModel photoModel) {
            Glide.with(itemView.getContext())
                    .load(photoModel.getPicturePath())
                    .apply(new RequestOptions().centerCrop())
                    .into(picture);

            picture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onPhotoClick(photoModel.getPicturePath());
                }
            });

        }
    }
}
