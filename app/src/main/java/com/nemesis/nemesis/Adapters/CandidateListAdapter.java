package com.nemesis.nemesis.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.nemesis.nemesis.Pojos.MyCandidates;
import com.nemesis.nemesis.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by aditya on 3/27/17.
 */

public class CandidateListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static ArrayList<HashMap<String,String>> list;
    public static Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.name) TextView name;
        @BindView(R.id.rollno) TextView rollno;
        @BindView(R.id.email) TextView email;
        @BindView(R.id.attempts) TextView attempts;
        @BindView(R.id.statusicon) ImageView statusIcon;
        @BindView(R.id.status) TextView status;
        @BindView(R.id.profile) de.hdodenhof.circleimageview.CircleImageView profile;
        @BindView(R.id.cardView) CardView cardView;
        View v;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            v=itemView;
        }

        @OnClick(R.id.profile)
        public void onProfileClick(){
            AlertDialog.Builder terms = new AlertDialog.Builder(v.getContext());
            terms.setTitle("Candidates Photo");
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setPadding(10, 10, 10, 10);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            Space tv1=new Space(context);
            tv1.setMinimumHeight(200);
            linearLayout.addView(tv1);
            ImageView img = new ImageView(context);
            img.setScaleX(3);
            img.setScaleY(3);
            linearLayout.addView(img);
            Space tv2=new Space(context);
            tv2.setMinimumHeight(200);
            linearLayout.addView(tv2);
            Picasso.with(context.getApplicationContext())
                    .load("http://35.154.117.178/" + list.get(getAdapterPosition()).get("profile"))
                    .into(img);
            terms.setView(linearLayout);
            terms.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            terms.create().show();
        }
    }

    public CandidateListAdapter(Context context,ArrayList<HashMap<String,String>> list) {
        this.list=list;
        this.context=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.candidate_item,parent,false);
        RecyclerView.ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder holder0=(ViewHolder) holder;
        holder0.name.setText(list.get(position).get("name"));
        holder0.attempts.setText("Attempts Made : "+list.get(position).get("attempts"));
        holder0.status.setText(list.get(position).get("status"));
        holder0.email.setText(list.get(position).get("email"));
        holder0.rollno.setText("Enrollment no : "+list.get(position).get("rollno"));
        if(list.get(position).get("status").equals("SUCCESS")){
            holder0.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.success_24));
            //holder0.cardView.setCardBackgroundColor(Color.GREEN);
        }
        else if (list.get(position).get("status").equals("FAILURE")){
            holder0.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.fail_24));
            //holder0.cardView.setCardBackgroundColor(Color.RED);
        }
        else{
            holder0.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.unknown_24));
            //holder0.cardView.setCardBackgroundColor(Color.YELLOW);
        }
        Picasso.with(context).load("http://35.154.117.178/"+list.get(position).get("profile")).noFade().into(holder0.profile);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
