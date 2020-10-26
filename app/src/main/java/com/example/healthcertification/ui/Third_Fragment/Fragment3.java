package com.example.healthcertification.ui.Third_Fragment;

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

public class Fragment3 extends Fragment {

    private Fragment3_ViewModel fragment3_viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        fragment3_viewModel =
                ViewModelProviders.of(this).get(Fragment3_ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_3, container, false);
        final TextView textView = root.findViewById(R.id.fragment3_address);

        fragment3_viewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
