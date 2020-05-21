package com.example.shoppedemo.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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

public class DashboardFragment extends Fragment implements ValueEventListener {
    ListView lvDanhsachCuaToi;
    MatHangAdapter matHangAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<MatHang>dsChinh=new ArrayList<MatHang>();
    String email;
    Button btnTroVe;
    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        lvDanhsachCuaToi=(ListView)root.findViewById(R.id.lvDanhSachCuaToi);
        matHangAdapter=new MatHangAdapter(getActivity(),R.layout.item1);
        lvDanhsachCuaToi.setAdapter(matHangAdapter);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("Shoppe");
        databaseReference.addValueEventListener(this);
        return root;
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
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        ArrayList<MatHang> ds1=new ArrayList<MatHang>();
        Iterable<DataSnapshot>dataSnapshots=dataSnapshot.getChildren();
        for(DataSnapshot dataSnapshot1:dataSnapshots){
            MatHang matHang=dataSnapshot1.getValue(MatHang.class);
            if(matHang.getEmail().equals(email)) {
                ds1.add(matHang);
            }
    }
        dsChinh.addAll(ds1);
        matHangAdapter.addAll(dsChinh);

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}