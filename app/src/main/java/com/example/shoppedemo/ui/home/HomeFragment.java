package com.example.shoppedemo.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.shoppedemo.MainActivity;
import com.example.shoppedemo.MatHang;
import com.example.shoppedemo.MatHangAdapter;
import com.example.shoppedemo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class HomeFragment extends Fragment implements ValueEventListener {
    GridView grDanhSach;
    MatHangAdapter matHangAdapter;
    ArrayList<MatHang>dsHangBayBanChinh=new ArrayList<MatHang>();
    ArrayList<MatHang>dsMuaSeclect=new ArrayList<MatHang>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String email;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        grDanhSach=(GridView)root.findViewById(R.id.grDanhSach);
        matHangAdapter=new MatHangAdapter(getActivity(),R.layout.item1);
        grDanhSach.setAdapter(matHangAdapter);
        dsHangBayBanChinh.add(new MatHang("Mặt hàng 1",100000,R.drawable.uploadh1,""));
        dsHangBayBanChinh.add(new MatHang("Mặt hàng 2",100000,R.drawable.uploadh2,""));
        dsHangBayBanChinh.add(new MatHang("Mặt hàng 3",100000,R.drawable.uploadh3,""));
       dsHangBayBanChinh.add(new MatHang("Mặt hàng 4",100000,R.drawable.uploadh4,""));
        dsHangBayBanChinh.add(new MatHang("Mặt hàng 5",100000,R.drawable.uploadh5,""));
        dsHangBayBanChinh.add(new MatHang("Mặt hàng 6",100000,R.drawable.uploadh6,""));
        dsHangBayBanChinh.add(new MatHang("Mặt hàng 7",100000,R.drawable.uploadh8,""));
        dsHangBayBanChinh.add(new MatHang("Mặt hàng 1",100000,R.drawable.uploadh9,""));
        dsHangBayBanChinh.add(new MatHang("Mặt hàng 1",100000,R.drawable.uploadh10,""));
        dsHangBayBanChinh.add(new MatHang("Mặt hàng 1",100000,R.drawable.uploadh11,""));
        dsHangBayBanChinh.add(new MatHang("Mặt hàng 1",100000,R.drawable.uploadh12,""));
        dsHangBayBanChinh.add(new MatHang("Mặt hàng 1",100000,R.drawable.uploadh13,""));
        matHangAdapter.addAll(dsHangBayBanChinh);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("Shoppe");
        databaseReference.addValueEventListener(this);
        addEvents();
        return root;
    }

    private void addEvents() {
        grDanhSach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MatHang matHang=matHangAdapter.getItem(position);
                moMua(matHang);
            }
        });
    }

    private void moMua(final MatHang matHang) {
        getActivity().setContentView(R.layout.activity_mu);
        TextView txtTenMua=(TextView)getActivity().findViewById(R.id.txTenMua);
        TextView txtGiaMua=(TextView)getActivity().findViewById(R.id.txtGiaMua);
        ImageView imgHinhAnhMua=(ImageView) getActivity().findViewById(R.id.imgHinhAnhMua);
        Button btnTroVe=(Button) getActivity().findViewById(R.id.btnTroVe);
        Button btnMua=(Button) getActivity().findViewById(R.id.btnMua);

        txtTenMua.setText(matHang.getTen());
        txtGiaMua.setText(matHang.getGia()+"VNĐ");
        imgHinhAnhMua.setImageResource(matHang.getHinhAnh());
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        btnMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matHang.setEmail(email);
                dsMuaSeclect.add(matHang);
                databaseReference.setValue(dsMuaSeclect);
            }
        });
}

    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();

        inflater.inflate(R.menu.mnu_search, menu);
        MenuItem item = menu.findItem(R.id.mnu_search);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<MatHang>ds1=new ArrayList<MatHang>();
                for(MatHang matHang:dsHangBayBanChinh){
                    if(matHang.getTen().contains(query)){
                        ds1.add(matHang);
                    }
                }
                matHangAdapter.clear();
                matHangAdapter.addAll(ds1);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Here is where we are going to implement the filter logic
                return true;
            }

        });
    }

    public void onCancelled(DatabaseError databaseError) {
    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().registerSticky(this);
    }
    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    public void onEventMainThread(String event) {

        email=event;
    }
    public void onDataChange(DataSnapshot dataSnapshot) {
        ArrayList<MatHang>ds1=new ArrayList<MatHang>();
        Iterable<DataSnapshot>dataSnapshots=dataSnapshot.getChildren();
        for(DataSnapshot dataSnapshot1:dataSnapshots){
            MatHang matHang=dataSnapshot1.getValue(MatHang.class);
            ds1.add(matHang);
        }
        dsMuaSeclect.addAll(ds1);

    }
}