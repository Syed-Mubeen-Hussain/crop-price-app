package com.example.cropprice.Fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cropprice.Adapters.HomeNewCropAdapter;
import com.example.cropprice.Modals.HomeCategoryModel;
import com.example.cropprice.Modals.HomeCropModel;
import com.example.cropprice.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchFragment extends Fragment {

    Toolbar toolbar;
    EditText etSearch;
    RecyclerView searchCropRcView;
    ImageButton btnSearch;
    LinearLayout progressLinearLayout, searchNoCropFoundLinearLayout;
    ArrayList<HomeCropModel> newCropList = new ArrayList<>();
    String searchCropUrl = "http://crop-price.infinityfreeapp.com/seller.php?searchAuctions=true";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_search, container, false);

        etSearch = view.findViewById(R.id.singleCropEnterBid);
        btnSearch = view.findViewById(R.id.singleCropSubmitBid);
        searchCropRcView = view.findViewById(R.id.searchCropRcView);
        progressLinearLayout = view.findViewById(R.id.progressLinearLayout);
        searchNoCropFoundLinearLayout = view.findViewById(R.id.searchNoCropFoundLinearLayout);

        // set the toolbar
        toolbar = getActivity().findViewById(R.id.toolbar);
        TextView titleToolbar = toolbar.findViewById(R.id.titleToolbar);
        titleToolbar.setText("Search");

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newCropList.clear();
                searchCropRcView.setVisibility(View.GONE);
                progressLinearLayout.setVisibility(View.VISIBLE);
                hideKeyboard(view);
                String search = etSearch.getText().toString();

                // new crop work
                HomeNewCropAdapter newCropAdapter = new HomeNewCropAdapter(newCropList, getActivity().getApplicationContext());
                searchCropRcView.setAdapter(newCropAdapter);
                GridLayoutManager newCropLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 2);
                searchCropRcView.setLayoutManager(newCropLayoutManager);
                searchCropRcView.setNestedScrollingEnabled(false);
                StringRequest newCropRequest = new StringRequest(Request.Method.POST, searchCropUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            if (success.equals("1")) {
                                if(jsonArray.length() > 0){
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        String name = object.getString("name");
                                        String image = object.getString("image");
                                        String price = object.getString("price");
                                        String qty = object.getString("qty");
                                        String description = object.getString("description");
                                        String ending_time = object.getString("ending_time");
                                        String bid_count = object.getString("bid_count");

                                        HomeCropModel crop = new HomeCropModel(image, name, price, description, bid_count, qty, ending_time);
                                        newCropList.add(crop);
                                        newCropAdapter.notifyDataSetChanged();
                                    }
                                    progressLinearLayout.setVisibility(View.GONE);
                                    searchNoCropFoundLinearLayout.setVisibility(View.GONE);
                                    searchCropRcView.setVisibility(View.VISIBLE);
                                }else{
                                    searchCropRcView.setVisibility(View.GONE);
                                    progressLinearLayout.setVisibility(View.GONE);
                                    searchNoCropFoundLinearLayout.setVisibility(View.VISIBLE);
                                }
                            }

                        } catch (Exception e) {
                            Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> param = new HashMap<>();
                        param.put("searchAuctions", "true");
                        param.put("search", search);

                        return param;
                    }
                };

                RequestQueue newCropRequestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                newCropRequestQueue.add(newCropRequest);


            }
        });

        return view;
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}