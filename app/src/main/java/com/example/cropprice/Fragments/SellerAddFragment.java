package com.example.cropprice.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cropprice.DateInputMask;
import com.example.cropprice.Modals.HomeCategoryModel;
import com.example.cropprice.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SellerAddFragment extends Fragment {


    Spinner sellerAddCategory;
    Toolbar toolbar;
    ImageView imgSellerAddNewCrop;
    TextView tvSellerAddNewCrop;
    TextInputLayout sellerAddCropName, sellerAddCropPrice, sellerAddCropDescription;
    Button sellerAddCropSubmitButton;
    ArrayList<String> categories = new ArrayList<>();
    String categoryUrl = "http://crop-price.infinityfreeapp.com/seller.php?categories=true";

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
        sellerAddCropSubmitButton = view.findViewById(R.id.sellerAddCropSubmitButton);

        // drop down categories
        sellerAddCategory.setSele
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,categories);
        sellerAddCategory.setAdapter(adapter);


        StringRequest request = new StringRequest(Request.Method.POST, categoryUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if (success.equals("1")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            String id = object.getString("id");
                            String name = object.getString("name");
                            categories.add(name+","+id);
                            adapter.notifyDataSetChanged();
                            Log.d("Categories",name);
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error is : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put("categories", "true");

                return param;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);


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

        sellerAddCropSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sellerAddCropName.setError("");
                sellerAddCropPrice.setError("");
                sellerAddCropDescription.setError("");

                String category = sellerAddCategory.getSelectedItem().toString();
                String name = sellerAddCropName.getEditText().getText().toString();
                String price = sellerAddCropPrice.getEditText().getText().toString();
                String description = sellerAddCropDescription.getEditText().getText().toString();
                String loadActivity = getActivity().getIntent().getStringExtra("loadActivity");
                if (category.trim().equals("")) {

                }
                else if (name.trim().equals("")) {
                    sellerAddCropName.setError("Name is required");
                } else if (price.trim().equals("")) {
                    sellerAddCropPrice.setError("Price is required");
                } else if (description.trim().equals("")) {
                    sellerAddCropDescription.setError("Description is required");
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