package com.example.cropprice.Fragments;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cropprice.DateInputMask;
import com.example.cropprice.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SellerAddFragment extends Fragment {

    Toolbar toolbar;
    ImageView imgSellerAddNewCrop;
    TextView tvSellerAddNewCrop;
    TextInputLayout sellerAddCategory, sellerAddCropName, sellerAddCropPrice, sellerAddCropDescription, sellerAddCropEndingTime;
    Button sellerAddCropSubmitButton;
    String dateRegex = "^(\\d{2}/){2}\\d{4}$";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_seller_add, container, false);

        // set the toolbar
        toolbar = getActivity().findViewById(R.id.toolbar);
        TextView titleToolbar = toolbar.findViewById(R.id.titleToolbar);
        titleToolbar.setText("Add New Crop");

        imgSellerAddNewCrop = view.findViewById(R.id.imgSellerAddNewCrop);
        tvSellerAddNewCrop = view.findViewById(R.id.tvSellerAddNewCrop);
        sellerAddCategory = view.findViewById(R.id.sellerAddCategory);
        sellerAddCropName = view.findViewById(R.id.sellerAddCropName);
        sellerAddCropPrice = view.findViewById(R.id.sellerAddCropPrice);
        sellerAddCropDescription = view.findViewById(R.id.sellerAddCropDescription);
        sellerAddCropEndingTime = view.findViewById(R.id.sellerAddCropEndingTime);
        sellerAddCropSubmitButton = view.findViewById(R.id.sellerAddCropSubmitButton);

        String getImageSource = getActivity().getIntent().getStringExtra("loadActivity");
        if (getImageSource != null) {
            imgSellerAddNewCrop.setImageURI(Uri.parse(getImageSource.toString()));
        }

        imgSellerAddNewCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(getContext())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                getActivity().startActivityForResult(Intent.createChooser(intent, "Image Browse"), 101);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        tvSellerAddNewCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(getContext())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                getActivity().startActivityForResult(Intent.createChooser(intent, "Image Browse"), 101);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        // this is the format of date
        new DateInputMask(sellerAddCropEndingTime.getEditText());

        sellerAddCropSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sellerAddCategory.setError("");
                sellerAddCropName.setError("");
                sellerAddCropPrice.setError("");
                sellerAddCropDescription.setError("");
                sellerAddCropEndingTime.setError("");

                String category = sellerAddCategory.getEditText().getText().toString();
                String name = sellerAddCropName.getEditText().getText().toString();
                String price = sellerAddCropPrice.getEditText().getText().toString();
                String description = sellerAddCropDescription.getEditText().getText().toString();
                String endingTime = sellerAddCropEndingTime.getEditText().getText().toString();
                String loadActivity = getActivity().getIntent().getStringExtra("loadActivity");
                if (category.trim().equals("")) {
                    sellerAddCategory.setError("Category is required");
                } else if (name.trim().equals("")) {
                    sellerAddCropName.setError("Name is required");
                } else if (price.trim().equals("")) {
                    sellerAddCropPrice.setError("Price is required");
                } else if (description.trim().equals("")) {
                    sellerAddCropDescription.setError("Description is required");
                } else if (endingTime.trim().equals("")) {
                    sellerAddCropEndingTime.setError("Ending Time is required");
                } else if (endingTime.trim().equals("") || !endingTime.matches(dateRegex)) {
                    sellerAddCropEndingTime.setError("Ending Time format is incorrect");
                } else if (loadActivity == null) {
                    Toast.makeText(getContext(), "Please upload image", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Form has been submitted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    public void errorMessage() {
        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...").setContentText("Something went wrong!").show();
    }
}