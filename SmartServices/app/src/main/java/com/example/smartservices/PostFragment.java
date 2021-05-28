package com.example.smartservices;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.tonywills.loadingbutton.HorizontalLoadingButton;
import com.google.android.material.textfield.TextInputEditText;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostFragment extends Fragment {
    private HorizontalLoadingButton horizontalLoadingButton;
    private TextInputEditText lost,place,name,section,phno;
    public PostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostFragment newInstance(String param1, String param2) {
        PostFragment fragment = new PostFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        horizontalLoadingButton = (HorizontalLoadingButton) view.findViewById(R.id.horizontal_loading_button);
        lost=view.findViewById(R.id.lostitem);
        place=view.findViewById(R.id.place);
        name=view.findViewById(R.id.pname);
        section=view.findViewById(R.id.ydetails);
        phno=view.findViewById(R.id.Phno);
        String email=getActivity().getIntent().getStringExtra("email");
        horizontalLoadingButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                horizontalLoadingButton.setLoading(true);
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        if(TextUtils.isEmpty(name.getText().toString()))
                        {
                            name.setError("Name cannot be empty");
                            horizontalLoadingButton.setLoading(false);
                            return;
                        }
                        if(TextUtils.isEmpty(section.getText().toString()))
                        {
                            section.setError("section cannot be blank");
                            horizontalLoadingButton.setLoading(false);
                            return;
                        }
                        if(TextUtils.isEmpty(phno.getText().toString()))
                        {
                            phno.setError("Phone number should be given to contact you");
                            horizontalLoadingButton.setLoading(false);
                            return;
                        }
                        if(name.getText().toString().length()<4)
                        {
                            name.setError("Name should contain atleast 4 letters");
                            Toast.makeText(getActivity(), "Name should contain minimum of 4 letters", Toast.LENGTH_SHORT).show();
                            horizontalLoadingButton.setLoading(false);
                            return;
                        }
                        if(phno.getText().toString().length()!=10)
                        {
                            phno.setError("Phone number should contain 10 digits");
                            Toast.makeText(getActivity(), "Phone number should contain 10 digits", Toast.LENGTH_SHORT).show();
                            horizontalLoadingButton.setLoading(false);
                            return;
                        }
                        if(TextUtils.isEmpty(lost.getText().toString()))
                        {
                            lost.setError("Enter lost or found item name");
                            horizontalLoadingButton.setLoading(false);
                            return;
                        }
                        Intent intent=new Intent(getActivity(),ImageActivity.class);
                        intent.putExtra("lostitem",lost.getText().toString());
                        intent.putExtra("place",place.getText().toString());
                        intent.putExtra("email",getActivity().getIntent().getStringExtra("email"));
                        intent.putExtra("phoneno",phno.getText().toString());
                        intent.putExtra("name",name.getText().toString());
                        intent.putExtra("class",section.getText().toString());
                        intent.putExtra("email",email);
                        startActivity(intent);
                        horizontalLoadingButton.setLoading(false);
                    }
                }, 2000);
            }
        });
        return view;
    }
}