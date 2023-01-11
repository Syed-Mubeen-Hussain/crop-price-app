package com.example.cropprice.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.cropprice.Fragments.SellerLoginFragment;
import com.example.cropprice.Fragments.SellerRegisterFragment;

public class SellerLoginAndRegisterAdapter extends FragmentStateAdapter {

    public SellerLoginAndRegisterAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:return new SellerLoginFragment();
            case 1:return new SellerRegisterFragment();
            default:return new SellerLoginFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
