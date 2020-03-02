package me.kdv.riverlevel.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import me.kdv.riverlevel.R;
import me.kdv.riverlevel.model.River;

public class RiverAdapter extends RecyclerView.Adapter<RiverAdapter.RiverViewHolder> {

    private Context context;
    private List<River> riverList;

    public RiverAdapter(Context context, List<River> riverList) {
        this.context = context;
        this.riverList = riverList;
    }

    @NonNull
    @Override
    public RiverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.river_item, parent, false);
        return new RiverViewHolder(view, this.context);
    }

    @Override
    public void onBindViewHolder(@NonNull RiverViewHolder holder, int position) {

        River river = riverList.get(position);

        holder.riverNameTextView.setText(river.getName());
        holder.riverStationTextView.setText(river.getStation());

        holder.overflowTextView.setText(river.getOverflow());
        holder.overflowTextView.setBackgroundResource(R.drawable.rounded_corner_area_green);
        holder.overflowTextView.setTextColor(Color.WHITE);

        holder.waterLevelTextView.setText(river.getWaterLevel());
        holder.waterLevelTextView.setBackgroundResource(R.drawable.rounded_corner_area_green);
        holder.waterLevelTextView.setTextColor(Color.WHITE);

        holder.levelChangeTextView.setText(river.getLevelChange());
        holder.waterTemperatureTextView.setText(river.getWaterTemperature());

        int levelChange = Integer.parseInt(river.getLevelChange());
        if(levelChange > 0){
            Glide.with(context)
                    .load(R.drawable.ic_arrow_drop_up_black_24dp)
                    .placeholder(R.drawable.ic_arrow_drop_up_black_24dp)
                    .into(holder.arrowImageView);

            holder.levelChangeTextView.setText(String.valueOf(Math.abs(levelChange)));
            holder.levelChangeTextView.setBackgroundResource(R.drawable.rounded_corner_area_green);
            holder.levelChangeTextView.setTextColor(Color.WHITE);

        } else if (levelChange == 0) {
            holder.arrowImageView.setVisibility(View.INVISIBLE);
            holder.levelChangeTextView.setVisibility(View.INVISIBLE);
        } else {
            Glide.with(context)
                    .load(R.drawable.ic_arrow_drop_down_black_24dp)
                    .placeholder(R.drawable.ic_arrow_drop_down_black_24dp)
                    .into(holder.arrowImageView);
            holder.levelChangeTextView.setText(String.valueOf(Math.abs(levelChange)));
            holder.levelChangeTextView.setBackgroundResource(R.drawable.rounded_corner_area_green);
            holder.levelChangeTextView.setTextColor(Color.WHITE);
        }

        try{
            Double temp = Double.parseDouble(river.getWaterTemperature());
            holder.waterTemperatureTextView.setText(river.getWaterTemperature());
        } catch (NumberFormatException e){
            holder.waterTemperatureTextView.setText(holder.context.getString(R.string.water_temperature_no_data));
        }
        finally {
            holder.waterTemperatureTextView.setBackgroundResource(R.drawable.rounded_corner_area_green);
            holder.waterTemperatureTextView.setTextColor(Color.WHITE);
        }

    }

    @Override
    public int getItemCount() {
        return riverList.size();
    }

    public class RiverViewHolder extends RecyclerView.ViewHolder {

        private Context context;

        private TextView riverNameTextView;
        private TextView riverStationTextView;
        private TextView overflowTextView;
        private TextView waterLevelTextView;
        private TextView levelChangeTextView;
        private TextView waterTemperatureTextView;

        private ImageView arrowImageView;

        public RiverViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            this.context = context;

            riverNameTextView = itemView.findViewById(R.id.riverNameTextView);
            riverStationTextView = itemView.findViewById(R.id.riverStationTextView);
            overflowTextView = itemView.findViewById(R.id.overflowTextView);
            waterLevelTextView = itemView.findViewById(R.id.waterLevelTextView);
            levelChangeTextView = itemView.findViewById(R.id.levelChangeTextView);
            waterTemperatureTextView = itemView.findViewById(R.id.waterTemperatureTextView);

            arrowImageView = itemView.findViewById(R.id.arrowImageView);
        }
    }
}
