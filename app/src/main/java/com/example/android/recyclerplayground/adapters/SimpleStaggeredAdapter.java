package com.example.android.recyclerplayground.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.android.recyclerplayground.MainActivity;
import com.example.android.recyclerplayground.R;

import java.util.List;

public class SimpleStaggeredAdapter extends SimpleAdapter {

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder itemHolder, int position) {
        if(itemHolder instanceof GridViewItemHolder){
            List<GameItem> data = generateDummyData(10);

             ((GridViewItemHolder) itemHolder).setAdapter(new ArrayAdapter<GameItem>(MainActivity.current, R.layout.view_match_item, data){
                 @Override
                 public View getView(int position, View convertView, ViewGroup parent) {
                     if(convertView == null){
                         convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_match_item, parent, false);
                     }
                     GameItem item = getItem(position);

                     ((TextView) convertView.findViewById(R.id.text_score_home)).setText(item.homeScore);
                     ((TextView) convertView.findViewById(R.id.text_score_away)).setText(item.awayScore);
                     ((TextView) convertView.findViewById(R.id.text_team_home)).setText(item.homeTeam);
                     ((TextView) convertView.findViewById(R.id.text_team_away)).setText(item.awayTeam);

                     return convertView;
                 }
             });

            int height = itemHolder.itemView.getContext().getResources().getDimensionPixelSize(R.dimen.card_staggered_height) / 2;
            itemHolder.itemView.setMinimumHeight(height * ((data.size() - 1) / 4 + 1));

        }
        else {
            super.onBindViewHolder(itemHolder, position);
            itemHolder.itemView.setMinimumHeight(0);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int type = 0;
        if(mItems.get(position) instanceof GameItemList) type = 1;
        return type;
    }

    @Override
    protected void onItemHolderClick(RecyclerView.ViewHolder itemHolder) {
        int pos = itemHolder.getAdapterPosition();
        pos = (pos / 4 + 1)*4;
        mItems.add(pos, new GameItemList());
        notifyItemInserted(pos);
        Log.w("click item..." + pos, "");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup container, int viewType) {
        if(viewType == 1){
            LayoutInflater inflater = LayoutInflater.from(container.getContext());
            View root = inflater.inflate(R.layout.view_gridview_item, container, false);
            return new GridViewItemHolder(root, this);
        }
        return super.onCreateViewHolder(container, viewType);
    }

    public class GridViewItemHolder extends RecyclerView.ViewHolder {

        GridView gridView;
        SimpleAdapter adapter;

        public GridViewItemHolder(View itemView, SimpleAdapter adapter) {
            super(itemView);

            gridView = (GridView)itemView.findViewById(R.id.view_gridview_gridview);
            this.adapter = adapter;
        }

        public void setAdapter(ListAdapter adapter){
            gridView.setAdapter(adapter);
        }
    }

    public class GameItemList extends GameItem{

        public GameItemList() {
            super("0", "0", 0, 0);
        }
    }
}
