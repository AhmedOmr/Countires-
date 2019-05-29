package com.mecodroid.myfirebaseauthgoogle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.mecodroid.myfirebaseauthgoogle.API.CountryApi;
import com.mecodroid.myfirebaseauthgoogle.API.RetrofitClient;
import com.mecodroid.myfirebaseauthgoogle.Model.Country;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Home extends AppCompatActivity {
    FirebaseAuth mAuth;
    GoogleSignInOptions inOptions;
    GoogleSignInClient client;
    int Rec_SignIn = 0;
    String token = "801485653415-aht3r2hioib7546scs4bql19359q1bh5.apps.googleusercontent.com";
    RecyclerView rv;
    CountryadapterList adapterList;
    ArrayList<Country> countryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        inOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(token)
                .requestEmail().build();
        client = GoogleSignIn.getClient(this, inOptions);
        setupRecycler();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sign_ou, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.sign:
                mAuth.signOut();
                client.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(Home.this, Begin.class));
                        finish();
                    }
                });
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setupRecycler() {
        rv = findViewById(R.id.recyclerCountry);
        LinearLayoutManager lin = new LinearLayoutManager(this);
        rv.setLayoutManager(lin);
        Retrofit instance = RetrofitClient.getinstance();
        CountryApi countryApi = instance.create(CountryApi.class);
        Call<List<Country>> listcountry = countryApi.getListcountry();
        listcountry.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                List<Country> body = response.body();
                countryList = new ArrayList<>();
                countryList.addAll(body);
                adapterList = new CountryadapterList(getApplicationContext(), countryList);
                rv.setAdapter(adapterList);
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {

            }
        });


    }
}
