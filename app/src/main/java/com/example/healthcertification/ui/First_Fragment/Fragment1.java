package com.example.healthcertification.ui.First_Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.healthcertification.R;

public class Fragment1 extends Fragment {

    private Fragment1_ViewModel fragment1_viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        fragment1_viewModel =
                ViewModelProviders.of(this).get(Fragment1_ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_1, container, false);
        final TextView textView = root.findViewById(R.id.fragment1_11);
        fragment1_viewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
