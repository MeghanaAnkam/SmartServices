package com.example.smartservices;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.github.tonywills.loadingbutton.LoadingButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddItems#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddItems extends Fragment {
    TextInputEditText e1,e2;
    int max=0;
    long mLastClickTime = 0;
    private LoadingButton add;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public AddItems() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddItems.
     */
    // TODO: Rename and change types and number of parameters
    public static AddItems newInstance(String param1, String param2) {
        AddItems fragment = new AddItems();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_add_items, container, false);
        e1=view.findViewById(R.id.itemname);
        e2=view.findViewById(R.id.price);
        add=view.findViewById(R.id.add_item);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add.setLoading(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        HashMap<String,String> hm=new HashMap<>();
                        hm.put("item",e1.getText().toString());
                        hm.put("price",e2.getText().toString());
                        db.collection("canteen").document(e1.getText().toString()).set(hm);
                        Toast.makeText(getContext(), "Added", Toast.LENGTH_SHORT).show();
                        add.setLoading(false);
                    }
                },1000);
            }
        });
        return view;
    }
}
