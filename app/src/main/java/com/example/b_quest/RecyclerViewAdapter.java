package com.example.b_quest;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

//CODE BY JUAN MARTIN

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> huntNames = new ArrayList<>();
    private ArrayList<String> heroNames = new ArrayList<>();
    private ArrayList<String> role = new ArrayList<>();
    private ArrayList<String> huntID = new ArrayList<>();

    private Context context;

    public RecyclerViewAdapter(ArrayList<String> huntID, ArrayList<String> huntNames, ArrayList<String> heroNames, ArrayList<String> role, Context context) {
        this.huntID = huntID;
        this.huntNames = huntNames;
        this.heroNames = heroNames;
        this.context = context;
        this.role = role;
    }


    @Override
    public int getItemViewType(int position) {
        if (role.get(position).equals("Chief")) {
            return R.layout.event_items;
        } else if (role.get(position).equals("Hero")) {
            return R.layout.event_item_hero;
        } else if (role.get(position).equals("Inv")) {
            return R.layout.event_item_inv;
        } else if (role.get(position).equals("Lord")) {
            return R.layout.event_item_lord;
        }
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final RecyclerView.ViewHolder holder;
        View view;

        switch (viewType) {
            case R.layout.event_items:
                view = LayoutInflater.from(context).inflate(R.layout.event_items, parent, false);
                holder = new ViewHolder(view);
                break;
            case R.layout.event_item_hero:
                view = LayoutInflater.from(context).inflate(R.layout.event_item_hero, parent, false);
                holder = new HeroViewHolder(view);
                break;
            case R.layout.event_item_inv:
                view = LayoutInflater.from(context).inflate(R.layout.event_item_inv, parent, false);
                holder = new InvitationViewHolder(view);
                break;
            case R.layout.event_item_lord:
                view = LayoutInflater.from(context).inflate(R.layout.event_item_lord, parent, false);
                holder = new LordViewHolder(view);
                break;

            default:
                view = LayoutInflater.from(context).inflate(R.layout.event_items, parent, false);
                holder = new ViewHolder(view);
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).hunt.setText(huntNames.get(position));
            ((ViewHolder) holder).hero.setText(heroNames.get(position));
            ((ViewHolder) holder).role.setText(role.get(position));
            final String thID = huntID.get(position);
            ((ViewHolder) holder).parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, huntNames.get(position), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, ChiefDisplayActivity.class);
                    intent.putExtra("thID", thID);
                    //((MyTreasureHuntActivity)context).finish();
                    context.startActivity(intent);
                }
            });

        } else if (holder instanceof HeroViewHolder) {
            ((HeroViewHolder) holder).hunt.setText(huntNames.get(position));
            ((HeroViewHolder) holder).hero.setText(heroNames.get(position));
            ((HeroViewHolder) holder).role.setText(role.get(position));
            final String thID = huntID.get(position);
            ((HeroViewHolder) holder).parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Hero.class);
                    intent.putExtra("thID", thID);
                    //((MyTreasureHuntActivity)context).finish();
                    context.startActivity(intent);

                }
            });
        } else if (holder instanceof InvitationViewHolder) {
            ((InvitationViewHolder) holder).hunt.setText(huntNames.get(position));
            ((InvitationViewHolder) holder).hero.setText(heroNames.get(position));
            ((InvitationViewHolder) holder).role.setText(role.get(position));
            final String thID = huntID.get(position);
            ((InvitationViewHolder) holder).parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PreviewActivity.class);
                    intent.putExtra("thID", thID);
                    //((MyTreasureHuntActivity) context).finish();
                    context.startActivity(intent);

                }
            });

        } else if (holder instanceof LordViewHolder) {
            ((LordViewHolder) holder).hunt.setText(huntNames.get(position));
            ((LordViewHolder) holder).hero.setText(heroNames.get(position));
            ((LordViewHolder) holder).role.setText(role.get(position));
            final String thID = huntID.get(position);
            ((LordViewHolder) holder).parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, LordScreenActivity.class);
                    intent.putExtra("thID", thID);
                    //((MyTreasureHuntActivity)context).finish();
                    context.startActivity(intent);

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return huntNames.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView hunt;
        TextView hero;
        TextView role;
        CardView parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            hunt = itemView.findViewById(R.id.display_hunt_name);
            hero = itemView.findViewById(R.id.display_hero_name);
            role = itemView.findViewById(R.id.role);
            parentLayout = itemView.findViewById(R.id.parent_layout);

        }
    }

    public class HeroViewHolder extends RecyclerView.ViewHolder {

        TextView hunt;
        TextView hero;
        TextView role;
        CardView parentLayout;

        public HeroViewHolder(@NonNull View itemView) {
            super(itemView);

            hunt = itemView.findViewById(R.id.display_hunt_name);
            hero = itemView.findViewById(R.id.display_hero_name);
            role = itemView.findViewById(R.id.role);
            parentLayout = itemView.findViewById(R.id.hero_parent_layout);

        }
    }

    public class LordViewHolder extends RecyclerView.ViewHolder {

        TextView hunt;
        TextView hero;
        TextView role;
        CardView parentLayout;

        public LordViewHolder(@NonNull View itemView) {
            super(itemView);

            hunt = itemView.findViewById(R.id.display_hunt_name);
            hero = itemView.findViewById(R.id.display_hero_name);
            role = itemView.findViewById(R.id.role);
            parentLayout = itemView.findViewById(R.id.lord_parent_layout);

        }
    }

    public class InvitationViewHolder extends RecyclerView.ViewHolder {

        TextView hunt;
        TextView hero;
        TextView role;
        CardView parentLayout;

        public InvitationViewHolder(@NonNull View itemView) {
            super(itemView);

            hunt = itemView.findViewById(R.id.display_hunt_name);
            hero = itemView.findViewById(R.id.display_hero_name);
            role = itemView.findViewById(R.id.role);
            parentLayout = itemView.findViewById(R.id.inv_parent_layout);

        }
    }
}
