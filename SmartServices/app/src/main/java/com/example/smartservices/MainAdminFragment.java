package com.example.smartservices;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.dev.materialspinner.MaterialSpinner;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainAdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainAdminFragment extends Fragment {
    TextInputEditText et;
    String str;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public MainAdminFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainAdminFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainAdminFragment newInstance(String param1, String param2) {
        MainAdminFragment fragment = new MainAdminFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_admin, container, false);
        et=view.findViewById(R.id.editText);
        String[] Admins= new String[] {"Canteen Admin", "Events admin", "Cleanliness Admin"};


        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        getContext(),
                        R.layout.dropdown_menu_popup_item,
                        Admins);

        MaterialSpinner editTextFilledExposedDropdown = view.findViewById(R.id.filled_exposed_dropdown);
        editTextFilledExposedDropdown.setAdapter(adapter);
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String s1 = et.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if(TextUtils.isEmpty(s1))
                {
                    et.setError("email cannot be empty");
                    return;
                }
                if(!(s1.matches(emailPattern)))
                {
                    et.setError("Invalid email");
                    return;
                }
                final String s2 = editTextFilledExposedDropdown.getSpinner().getSelectedItem().toString();
                if(TextUtils.isEmpty(s2))
                {
                    editTextFilledExposedDropdown.setError("Select an admin from the list");
                    return;
                }
                db.collection("users").document("roles").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()) {
                            final Map<String, Object> u = new HashMap<>();
                            if (s2.contains("Canteen Admin")) {
                                u.put("canteen_admin", s1);
                                String str = documentSnapshot.get("clean_admin").toString();
                                String str2 = documentSnapshot.get("events_admin").toString();
                                String str3=documentSnapshot.get("Main_admin").toString();
                                u.put("clean_admin", str);
                                u.put("events_admin", str2);
                                u.put("Main_admin",str3);
                            } else if (s2.contains("Cleanliness Admin")) {
                                u.put("clean_admin", s1);
                                String str = documentSnapshot.get("canteen_admin").toString();
                                String str2 = documentSnapshot.get("events_admin").toString();
                                String str3=documentSnapshot.get("Main_admin").toString();
                                u.put("canteen_admin", str);
                                u.put("events_admin", str2);
                                u.put("Main_admin",str3);
                            }
                            else {
                                u.put("events_admin", s1);
                                String str = documentSnapshot.get("canteen_admin").toString();
                                String str2 = documentSnapshot.get("clean_admin").toString();
                                String str3=documentSnapshot.get("Main_admin").toString();
                                u.put("canteen_admin", str);
                                u.put("clean_admin", str2);
                                u.put("Main_admin",str3);
                            }
                            db.collection("users").document("roles").set(u);
                            Toast.makeText(getActivity(), "Added Successfully!!! Restart the app to see changes..", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
        return view;
    }
}
