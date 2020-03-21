package com.ahmednafe3.photoweather.moduls.gallery.view;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmednafe3.photoweather.R;
import com.ahmednafe3.photoweather.base.status.Status;
import com.ahmednafe3.photoweather.base.view.BaseFragment;
import com.ahmednafe3.photoweather.databinding.FragmentPhotoListBinding;
import com.ahmednafe3.photoweather.moduls.gallery.adapter.PhotoAdapter;
import com.ahmednafe3.photoweather.moduls.gallery.adapter.PhotoAdapterCallback;
import com.ahmednafe3.photoweather.moduls.gallery.data.model.PhotoModel;
import com.ahmednafe3.photoweather.utils.ImageOverlayHelper;
import com.ahmednafe3.photoweather.utils.MarginDecoration;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoListFragment extends BaseFragment implements PhotoAdapterCallback {
    RecyclerView imageRecycler;
    FragmentPhotoListBinding binding;
    List<PhotoModel> photoModelArrayList;
    PhotoAdapter adapter;
    PhotoViewModel viewModel;

    public PhotoListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_photo_list, container, false);
        binding = DataBindingUtil.bind(v);
        imageRecycler = binding.rvPhotos;
        return v;
    }


    @Override
    protected void setUp(View view) {
        viewModel = new ViewModelProvider(this).get(PhotoViewModel.class);
        viewModel.getAllPhotos(ImageOverlayHelper.getInstance().getFolderPath()).observe(this, response -> {
            // update UI
            if (response.status.equals(Status.LOADING)) {
                showLoading();
            } else if (response.status.equals(Status.SUCCESS)) {
                photoModelArrayList = response.data;
                showPhoto(response.data);
            } else if (response.status.equals(Status.ERROR)) {
                showMessage(response.error);
            }
        });
    }

    private void showPhoto(List<PhotoModel> photoModels) {
        adapter = new PhotoAdapter(photoModels, this::onPhotoClick);
        imageRecycler.addItemDecoration(new MarginDecoration(getActivity()));
        imageRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        imageRecycler.setAdapter(adapter);
    }

    @Override
    public void onPhotoClick(String photoPath) {
        Bundle bundle = new Bundle();
        bundle.putString("photoPath", photoPath);
        Navigation.findNavController(getView()).navigate(R.id.photoDetailFragment, bundle);
    }
}
