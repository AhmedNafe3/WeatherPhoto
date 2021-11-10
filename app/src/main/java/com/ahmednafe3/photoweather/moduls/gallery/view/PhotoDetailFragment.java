package com.ahmednafe3.photoweather.moduls.gallery.view;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ahmednafe3.photoweather.R;
import com.ahmednafe3.photoweather.base.view.BaseFragment;
import com.ahmednafe3.photoweather.databinding.FragmentPhotoDetailBinding;
import com.ahmednafe3.photoweather.utils.CommonUtil;

public class PhotoDetailFragment extends BaseFragment {
    FragmentPhotoDetailBinding binding;
    String imgPath;

    public PhotoDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_photo_detail, container, false);
        binding = DataBindingUtil.bind(v);
        showBigPhoto();
        return v;
    }

    private void showBigPhoto() {
        imgPath = getArguments().getString("photoPath");
        if (imgPath != null) {
            Glide.with(this)
                    .load(imgPath)
                    .apply(new RequestOptions().centerCrop())
                    .into(binding.photoBigView);
        } else {
            showMessage("Sorry error occurred");
        }
    }

    @Override
    protected void setUp(View view) {
        binding.shareBut.setOnClickListener(v1 -> {
            if (imgPath != null) {
                CommonUtil.share(imgPath, getActivity());
            } else {
                showMessage("Sorry error occurred");
            }
        });

        binding.backBut.setOnClickListener(v2 -> {
            Navigation.findNavController(v2).popBackStack();
        });
    }
}
