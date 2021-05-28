package com.example.smartservices;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainFragment extends Fragment {
    public static String s1="title";
    public static String s2="desc";
    public static String s3="link";
    int max=0;
    TextInputEditText et;
    EditText et1,et2;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main, container, false);
        et=view.findViewById(R.id.editText);
        et1=view.findViewById(R.id.editText2);
        et2=view.findViewById(R.id.editText3);
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = et.getText().toString().trim();
                String desc=et1.getText().toString().trim();
                String link = et2.getText().toString().trim();

                if (title.isEmpty() ) {
                    Toast.makeText(getContext(), "Empty Fields!.....", Toast.LENGTH_SHORT).show();
                } else {
                    final Map<String, Object> u = new HashMap<>();
                    title = title.substring(0, 1).toUpperCase() + title.substring(1);
                    u.put(s1,title);
                    u.put(s2, desc);
                    u.put(s3,link);

                    db.collection("events").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful())
                            {
                                List<String> list=new ArrayList();
                                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                    list.add(documentSnapshot.getId());

                                }
                                try {
                                    max = Integer.parseInt(Collections.max(list));
                                    db.collection("events").document(String.valueOf(max + 1)).set(u).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getContext(),"Succcess",Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }catch (Exception e)
                                {
                                    db.collection("events").document("1").set(u).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getContext(),"Success",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                            else
                            {

                            }
                        }
                    });

                }
            }
        });
      //  ViewPager pager = (ViewPager) view.findViewById(R.id.pager);
     //   pager.setAdapter(new PagerAdapter());
        return view;
    }


}
