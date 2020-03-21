package com.ahmednafe3.photoweather.moduls.home;


import android.Manifest;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.ahmednafe3.photoweather.R;
import com.ahmednafe3.photoweather.base.view.BaseFragment;
import com.ahmednafe3.photoweather.databinding.FragmentHomeBinding;

import java.util.List;

import static com.ahmednafe3.photoweather.utils.CommonUtil.showPermissionSettingsDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {
    private boolean isGranted = false;
    private FragmentHomeBinding binding;
    private HomeViewModel model;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        binding = DataBindingUtil.bind(v);
        binding.captureButton.setOnClickListener(v1 -> showCaptureScreen(v1));

        binding.galleryButton.setOnClickListener(v2 -> showPhotosScreen(v2));
        return v;
    }


    private boolean checkPermission() {
        Dexter.withActivity(getActivity()).withPermissions(
                Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.areAllPermissionsGranted()) {
                    isGranted = report.areAllPermissionsGranted();
                }
                if (report.isAnyPermissionPermanentlyDenied()) {
                    showPermissionSettingsDialog(getActivity());
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).onSameThread().check();
        return isGranted;
    }

    private void showCaptureScreen(View v) {
        if (checkPermission()) {
            Navigation.findNavController(v).navigate(R.id.captchPhotoFragment);
        }
    }

    private void showPhotosScreen(View v) {
        if (checkPermission())
            Navigation.findNavController(v).navigate(R.id.photoListFragment);
    }

    @Override
    protected void setUp(View view) {
        model = new ViewModelProvider(getActivity()).get(HomeViewModel.class);
        model.callData();
        model.getCurrentCity().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.cityTv.setText(s);
            }
        });
        model.getCurrentTimp().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.tempTv.setText(String.valueOf(aDouble));
            }
        });
        model.getDescription().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.descTv.setText(s);
            }
        });

    }
}
