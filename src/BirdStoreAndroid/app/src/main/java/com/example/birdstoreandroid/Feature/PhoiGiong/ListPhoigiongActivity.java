package com.example.birdstoreandroid.Feature.PhoiGiong;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdstoreandroid.API.ApiClient;
import com.example.birdstoreandroid.IService.UserService;
import com.example.birdstoreandroid.Model.GetAllPhoigiongResponse;
import com.example.birdstoreandroid.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListPhoigiongActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PhoiGiongAdapter adapter;
    private List<GetAllPhoigiongResponse.PhoiGiongData> phoiGiongList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_phoigiong);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.rvPhoigiong);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        phoiGiongList = new ArrayList<>();
        adapter = new PhoiGiongAdapter(phoiGiongList);
        recyclerView.setAdapter(adapter);

        fetchPhoiGiongData();
    }
    private void fetchPhoiGiongData() {
        String accessToken = getAccessToken();
        UserService userService = ApiClient.getUserService();
        Call<GetAllPhoigiongResponse> call = userService.getAllPhoigiong("Bearer " + accessToken);
        call.enqueue(new Callback<GetAllPhoigiongResponse>() {
            @Override
            public void onResponse(Call<GetAllPhoigiongResponse> call, Response<GetAllPhoigiongResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    phoiGiongList.clear();
                    phoiGiongList.addAll(response.body().getData());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<GetAllPhoigiongResponse> call, Throwable t) {
                // Handle error
            }
        });
    }
    private String getAccessToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("access_token", "");
    }
}