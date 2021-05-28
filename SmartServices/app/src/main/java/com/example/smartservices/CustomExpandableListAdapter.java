package com.example.smartservices;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private Activity activity;
    private List<String> listTitulo;
    long mLastClickTime = 0;
    private List<String> expandableListDetalles;
    private static final int REQUEST_CALL = 1;
    public CustomExpandableListAdapter(Context context,
                                       List<String> listTitulo,
                                       List<String> expandableListDetalles) {
        this.context = context;
        this.listTitulo = listTitulo;
        this.expandableListDetalles=expandableListDetalles;
    }


    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

//        final Contacto contacto = (Contacto) getChild(groupPosition, childPosition);

        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.list_item, null);

        }

        TextView circleImageView = convertView.findViewById(R.id.circleIMG);
        if(expandableListDetalles.get(0).equalsIgnoreCase(""))
        {
            circleImageView.setText("");
            LinearLayout call = convertView.findViewById(R.id.call);
            LinearLayout whatsapp = convertView.findViewById(R.id.whatsapp);
            LinearLayout message = convertView.findViewById(R.id.message);
            LinearLayout layoutInfo = convertView.findViewById(R.id.lInfo);
            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "No data available", Toast.LENGTH_SHORT).show();
                }
            });
            whatsapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "No data available", Toast.LENGTH_SHORT).show();
                }
            });
            message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "No data available", Toast.LENGTH_SHORT).show();
                }
            });
            layoutInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "No data available", Toast.LENGTH_SHORT).show();
                }
            });
        }
        //Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), contacto.getImg());
        //circleImageView.setImageBitmap(bitmap);
        //Picasso.get().load(contacto.getImg()).into(circleImageView);
        else {
            String[] call1 = listTitulo.get(groupPosition).split("\n");
            //  Picasso.get().load(expandableListDetalles.get(groupPosition)).into(circleImageView);
            circleImageView.setText("Name : " + call1[0] + "\n" + "Section : " + call1[2] + "\n" + "Lost item : " + call1[4] + "\n" + "Place : " + call1[3]);
            LinearLayout call = convertView.findViewById(R.id.call);
            LinearLayout whatsapp = convertView.findViewById(R.id.whatsapp);
            LinearLayout message = convertView.findViewById(R.id.message);
            LinearLayout layoutInfo = convertView.findViewById(R.id.lInfo);

            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_click));
                    if (ContextCompat.checkSelfPermission(context,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                    } else {
                        String p = "tel:" + call1[1];
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(p));
                        context.startActivity(intent);
                        Animatoo.animateZoom(context);
                    }
                }

            });

            message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_click));
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.putExtra("address", call1[1]);
                    intent.setType("vnd.android-dir/mms-sms");
                    context.startActivity(intent);
                    Animatoo.animateZoom(context);
                }
            });

            whatsapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_click));
                    String p = "+91" + call1[1];
                    try {
                        Intent sendMsg = new Intent(Intent.ACTION_VIEW);
                        String url = "https://api.whatsapp.com/send?phone=" + p + "&text=" + URLEncoder.encode("", "UTF-8");
                        sendMsg.setPackage("com.whatsapp");
                        sendMsg.setData(Uri.parse(url));
                        if (sendMsg.resolveActivity(context.getPackageManager()) != null) {
                            context.startActivity(sendMsg);
                            Animatoo.animateZoom(context);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            final View cv = convertView;
            layoutInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_click));
                    List<String> index = new ArrayList<>();
                    Intent intent = new Intent(context, PopUp.class);
                    Pair[] pairs = new Pair[3];
                    pairs[0] = new Pair<View, String>(cv.findViewById(R.id.dblostimg2), "images");
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, pairs[0]);
                    intent.putExtra("Image", expandableListDetalles.get(groupPosition));
                    context.startActivity(intent, options.toBundle());

                }
            });


            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
            convertView.startAnimation(animation);

        }
        return convertView;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        String nombre = (String) getGroup(groupPosition);
        //Contacto contacto = (Contacto) getChild(groupPosition,0);

        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.list_group, null);

        }
        TextView txtNombre = convertView.findViewById(R.id.txtGroupNombre);
        ImageView imageView=convertView.findViewById(R.id.dblostimg);
        if(nombre.equalsIgnoreCase("Empty Records"))
        {
                txtNombre.setText("Empty Records");
                imageView.setImageResource(R.drawable.noi);
        }
        else {
            String[] det = nombre.split("\n");
            txtNombre.setText(det[0] + "\n" + det[2]);
            String firstLetter = String.valueOf(txtNombre.getText().toString().charAt(0));

            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
            // generate random color
            int color = generator.getColor(getGroup(groupPosition));

            //int color = generator.getRandomColor();

            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(firstLetter, color); // radius in px
            imageView.setImageDrawable(drawable);
        }
       // txtNumero.setText(contacto.getNumero());
        return convertView;
    }


    @Override
    public int getGroupCount() {
        return this.listTitulo.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listTitulo.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.expandableListDetalles.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
