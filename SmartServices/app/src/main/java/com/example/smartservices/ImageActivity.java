package com.example.smartservices;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ImageActivity extends AppCompatActivity {
    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private TextInputEditText mEditTextFileName;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;
    long mLastClickTime = 0;
    private SlidrInterface slidr;
    String lost,place,name,section,phno;
    String email;
    private StorageReference mStorageRef;
   FirebaseFirestore db=FirebaseFirestore.getInstance();
    private StorageTask mUploadTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        slidr= Slidr.attach(this);
        setContentView(R.layout.activity_image);
        mButtonChooseImage = findViewById(R.id.button_choose_image);
       lost=getIntent().getStringExtra("lostitem");
        place=getIntent().getStringExtra("place");
        phno=getIntent().getStringExtra("phoneno");
        name=getIntent().getStringExtra("name");
        section=getIntent().getStringExtra("class");
        mButtonUpload = findViewById(R.id.button_upload);
        mEditTextFileName = findViewById(R.id.edit_text_file_name);
        mImageView = findViewById(R.id.image_view);
        mProgressBar = findViewById(R.id.progress_bar);
        email=getIntent().getStringExtra("email");
        String[] a=email.split("@");
        AlertDialog alertDialog = new AlertDialog.Builder(ImageActivity.this).create();
        alertDialog.setTitle("Hi"+" "+a[0]);
        alertDialog.setMessage("Do you have an Image of your lost item?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HashMap<String,String> hm=new HashMap<>();
                        hm.put("imageurl","https://firebasestorage.googleapis.com/v0/b/services-200a6.appspot.com/o/uploads%2Fnoi.jpg?alt=media&token=96431d52-480c-4ec6-b805-0c4c1c682e7c");
                        hm.put("lostitem",lost);
                        hm.put("place",place);
                        hm.put("phoneno",phno);
                        hm.put("name",name);
                        hm.put("section",section);
                        hm.put("email",email);
                        db.collection("lost").document(lost).set(hm);
                        Toast.makeText(ImageActivity.this, "Uploaded Successfully !! Go back to see the changes", Toast.LENGTH_SHORT).show();
                    }
                });
        alertDialog.show();
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(ImageActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ImageActivity.this, "Upload in progress.. Please wait", Toast.LENGTH_SHORT).show();
                    uploadFile();
                }
            }
        });


    }
    private void openFileChooser() {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(mImageView);
        }
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (mImageUri != null) {
            final StorageReference FileRef = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));
           
            mUploadTask = FileRef.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);
                            FileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Upload upload = new Upload(mEditTextFileName.getText().toString().trim(),
                                            uri.toString());
                                 //   Toast.makeText(ImageActivity.this, "This"+uri, Toast.LENGTH_SHORT).show();
                                    HashMap<String,String> hm=new HashMap<>();
                                    hm.put("imageurl",upload.getImageUrl());
                                    hm.put("lostitem",lost);
                                    hm.put("place",place);
                                    hm.put("phoneno",phno);
                                    hm.put("name",name);
                                    hm.put("section",section);
                                    hm.put("email",email);
                                    db.collection("lost").document(lost).set(hm);
                                }
                            });

                            Toast.makeText(ImageActivity.this, "Uploaded Successfully", Toast.LENGTH_LONG).show();


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ImageActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });

        } else {
            Toast.makeText(this, "No File Selected", Toast.LENGTH_SHORT).show();
        }
    }
    private void openImagesActivity() {

    }
}
