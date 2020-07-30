package com.example.firebaseuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.firebaseuser.Adapter.DoctorRecyclerAdapter;
import com.example.firebaseuser.Adapter.NewsRecyclerAdapter;
import com.example.firebaseuser.Adapter.ProductRecyclerAdapter;
import com.example.firebaseuser.Model.DoctorModel;
import com.example.firebaseuser.Model.MedicineModel;
import com.example.firebaseuser.Model.NewsModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference dbDoctor,dbNews,dbMedicine;
    //doctor
    ArrayList<DoctorModel> listDoctor;
    DoctorRecyclerAdapter adapterDoctors;
    RecyclerView recyclerDoctor;
    LinearLayoutManager linearLayoutManagerDoctor;
    //news
    ArrayList<NewsModel> listNews;
    NewsRecyclerAdapter newsRecyclerAdapter;
    RecyclerView recyclerNews;
    LinearLayoutManager linearLayoutManagerNews;
    //Medicine
    ArrayList<MedicineModel> listMedicine;
    ProductRecyclerAdapter productRecyclerAdapter;
    RecyclerView recyclerMedicine;
    LinearLayoutManager linearLayoutManagerMedicine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        database = FirebaseDatabase.getInstance();

        //TODO: Recyclerview of Medicine
        dbMedicine = database.getReference("Medicine");
        recyclerMedicine = (RecyclerView) findViewById(R.id.recyclerViewMedicine);
        linearLayoutManagerMedicine = new LinearLayoutManager(this);
        linearLayoutManagerMedicine.setOrientation(LinearLayoutManager.VERTICAL);

        listMedicine = new ArrayList<>();
        recyclerMedicine.setLayoutManager(linearLayoutManagerMedicine);

        productRecyclerAdapter = new ProductRecyclerAdapter(listMedicine);
        recyclerMedicine.setAdapter(productRecyclerAdapter);
        dbMedicine.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    String name = ds.child("Name").getValue().toString();
                    String price = ds.child("Price").getValue().toString();
                    String image = ds.child("ImgUrl").getValue().toString();
                    listMedicine.add(new MedicineModel(name,price,image));
                }
                productRecyclerAdapter = new ProductRecyclerAdapter(listMedicine);
                recyclerMedicine.setAdapter(productRecyclerAdapter);
                productRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //TODO: Recyclerview of News
        dbNews = database.getReference("News");
        recyclerNews = (RecyclerView) findViewById(R.id.recycleViewNews);
        linearLayoutManagerNews = new LinearLayoutManager(this);
        linearLayoutManagerNews.setOrientation(LinearLayoutManager.HORIZONTAL);

        listNews = new ArrayList<>();
        recyclerNews.setLayoutManager(linearLayoutManagerNews);

        newsRecyclerAdapter = new NewsRecyclerAdapter(listNews);
        recyclerNews.setAdapter(newsRecyclerAdapter);
        dbNews.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    String tile = ds.child("Title").getValue().toString();
                    String content = ds.child("Content").getValue().toString();
                    String author = ds.child("Author").getValue().toString();
                    String b_date = ds.child("B_Date").getValue().toString();
                    listNews.add(new NewsModel(tile,content,b_date,author));
                }
                newsRecyclerAdapter = new NewsRecyclerAdapter(listNews);
                recyclerNews.setAdapter(newsRecyclerAdapter);
                newsRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //TODO: Recyclerview of Doctor
        dbDoctor = database.getReference("MEMBER");
        recyclerDoctor = (RecyclerView) findViewById(R.id.recycleViewDoctors);
        linearLayoutManagerDoctor = new LinearLayoutManager(this);
        linearLayoutManagerDoctor.setOrientation(LinearLayoutManager.HORIZONTAL);

        listDoctor = new ArrayList<>();
        recyclerDoctor.setLayoutManager(linearLayoutManagerDoctor);

        adapterDoctors = new DoctorRecyclerAdapter(listDoctor);
        recyclerDoctor.setAdapter(adapterDoctors);

        dbDoctor.child("Doctor").addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    String name = ds.child("User_name").getValue().toString();
                    String specialist = ds.child("Specialist").getValue().toString();
                    String imgURL = ds.child("imgURL").getValue().toString();
                    listDoctor.add(new DoctorModel("",name,"",imgURL,"",specialist,"",""));
                }
                adapterDoctors = new DoctorRecyclerAdapter(listDoctor);
                recyclerDoctor.setAdapter(adapterDoctors);
                adapterDoctors.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        BottomNavigationView navigation = findViewById(R.id.navigationHome);
        navigation.setSelectedItemId(R.id.itemHome);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.itemHome:
                    finish();
                    return true;
                case R.id.itemNews:
                    intent = new Intent(getApplicationContext(),NewsActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.itemMess:
                    intent = new Intent(getApplicationContext(),ContactActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.itemCart:
                    intent = new Intent(getApplicationContext(),CartActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.itemAccount:
                    intent = new Intent(getApplicationContext(),ProfileActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().signOut();
    }
}