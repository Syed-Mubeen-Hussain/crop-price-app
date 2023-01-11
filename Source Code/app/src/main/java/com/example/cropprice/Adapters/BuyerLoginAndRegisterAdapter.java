package com.example.cropprice.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.cropprice.Fragments.BuyerLoginFragment;
import com.example.cropprice.Fragments.BuyerRegisterFragment;
import com.example.cropprice.Fragments.SellerLoginFragment;
import com.example.cropprice.Fragments.SellerRegisterFragment;

public class BuyerLoginAndRegisterAdapter extends FragmentStateAdapter {

    public BuyerLoginAndRegisterAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:return new BuyerLoginFragment();
            case 1:return new BuyerRegisterFragment();
            default:return new BuyerLoginFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
