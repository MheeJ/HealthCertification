package com.example.healthcertification.ui.Fourth_Fragment;

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
import com.example.healthcertification.ui.First_Fragment.Fragment1_ViewModel;

public class Fragment4 extends Fragment {
    private Fragment4_ViewModel fragment4_viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        fragment4_viewModel =
                ViewModelProviders.of(this).get(Fragment4_ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_4, container, false);
        final TextView textView = root.findViewById(R.id.text_fourth);
        fragment4_viewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
