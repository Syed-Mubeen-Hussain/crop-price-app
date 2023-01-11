package com.example.cropprice.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cropprice.BuyerHomeActivity;
import com.example.cropprice.ChooserActivity;
import com.example.cropprice.R;
import com.example.cropprice.SellerHomeActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BuyerLoginFragment extends Fragment {

    Button btnSellerLogin;
    String emailRegex = "^[a-z0-9_]+@[a-z0-9.-]+\\.[a-z]{2,3}$";
    EditText etBuyerLoginEmail, etBuyerLoginPassword;
    String buyerLogin = "https://crop-price-web.000webhostapp.com/seller.php?buyerLogin=true";
    private RequestQueue requestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buyer_login, container, false);

        etBuyerLoginEmail = view.findViewById(R.id.etBuyerLoginEmail);
        etBuyerLoginPassword = view.findViewById(R.id.etBuyerLoginPassword);
        btnSellerLogin = view.findViewById(R.id.btnBuyerLogin);

        btnSellerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etBuyerLoginEmail.getText().toString();
                String password = etBuyerLoginPassword.getText().toString();
                if (email.trim().equals("")) {
                    etBuyerLoginEmail.setError("Email is required");
                } else if (email.trim().equals("") || !email.matches(emailRegex)) {
                    etBuyerLoginEmail.setError("Email format is invalid");
                } else if (password.trim().equals("")) {
                    etBuyerLoginPassword.setError("Password is required");
                } else {
                    SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Loading");
                    pDialog.setCancelable(false);
                    pDialog.show();
                    StringRequest request = new StringRequest(Request.Method.POST, buyerLogin, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("data");

                                if (success.equals("1")) {
                                    pDialog.dismiss();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);


                                        String id = object.getString("id");
                                        String name = object.getString("name");
                                        String contact = object.getString("contact");
                                        String email = object.getString("email");
                                        String password = object.getString("password");
                                        String image = object.getString("image");

                                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("BuyerLoginSharedPreference",MODE_PRIVATE);
                                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                        myEdit.putString("id", id);
                                        myEdit.putString("name", name);
                                        myEdit.putString("contact", contact);
                                        myEdit.putString("email", email);
                                        myEdit.putString("password", password);
                                        myEdit.putString("image", image);
                                        myEdit.putString("buyer", "Buyer");
                                        myEdit.apply();

                                        getActivity().startActivity(new Intent(getContext(), BuyerHomeActivity.class));
                                        getActivity().finish();
                                    }
                                }else{
                                    pDialog.dismiss();
                                    Toast.makeText(getActivity().getApplicationContext(), "Invalid email or password", Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                pDialog.dismiss();
                                Toast.makeText(getActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                            pDialog.dismiss();
                        }
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> param = new HashMap<>();
                            param.put("email", email);
                            param.put("password", password);

                            return param;
                        }
                    };

                    if (requestQueue == null) {
                        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                        requestQueue.add(request);
                    } else {
                        requestQueue.add(request);
                    }

                }
            }
        });

        return view;
    }

}