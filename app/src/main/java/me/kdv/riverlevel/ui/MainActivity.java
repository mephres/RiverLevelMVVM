package me.kdv.riverlevel.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.kdv.riverlevel.adapter.RiverAdapter;
import me.kdv.riverlevel.databinding.ActivityMainBinding;
import me.kdv.riverlevel.model.River;
import me.kdv.riverlevel.model.RiverResponse;
import me.kdv.riverlevel.viewmodel.MainActivityViewModel;

import static androidx.lifecycle.ViewModelProviders.of;


public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel viewModel;
    private ActivityMainBinding binding;
    private MainActivityClickHandlers clickHandlers;

    private RiverAdapter riverAdapter;
    List<River> riverList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        clickHandlers = new MainActivityClickHandlers();
        binding.setClickHandlers(clickHandlers);

        setContentView(binding.getRoot());

        riverList = new ArrayList<>();

        viewModel = of(this).get(MainActivityViewModel.class);
        viewModel.init();
        viewModel.getRiverLevel().observe(this, new Observer<RiverResponse>() {
            @Override
            public void onChanged(RiverResponse riverResponse) {
                List<River> list = riverResponse.getRiverList();
                riverList.addAll(list);
                riverAdapter.notifyDataSetChanged();
            }
        });

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        if (riverAdapter == null) {
            riverAdapter = new RiverAdapter(MainActivity.this, riverList);

            binding.riverRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            binding.riverRecyclerView.setAdapter(riverAdapter);
            binding.riverRecyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.riverRecyclerView.setNestedScrollingEnabled(true);
        } else {
            riverAdapter.notifyDataSetChanged();
        }
    }

    public class MainActivityClickHandlers{

        public void onProviderClicked(View view){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.meteorb.ru/"));
            startActivity(browserIntent);
        }
    }
}
