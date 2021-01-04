package com.example.simpletodo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

// Adapter Responsible for displaying data from the model into a row in the recycler view
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {
    public interface OnLongClickListener{
        void onItemLongClicked(int position);
    }
    List<String> items;
    OnLongClickListener longClickListener;

    public ItemsAdapter( List<String> items,OnLongClickListener longClickListener) {
         this.items = items;
         this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup Parent, int i) {
        // Use layout inflator to inflate a view

        View todoView = LayoutInflater.from(Parent.getContext()).inflate(android.R.layout.simple_list_item_1,Parent,false);

        // Wrap it inside a view holder and return it
        return new ViewHolder(todoView);

    }

    //responsible for binding a data to a particular view holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Grab the item at the position
        String item = items.get(position);

        //Bind the item into the specified view holder
        holder.bind(item);
    }

    //Tells the recycle review how many items are in the list
    @Override
    public int getItemCount() {
        return items.size();
    }

    //Container to provide easy access to views that represent each row of the list
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }
        //update the view inside of the view holder with this data
        public void bind(String item) {
            tvItem.setText(item);
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                   //notify the long pressed position to listener
                    longClickListener.onItemLongClicked(getAdapterPosition());

                    return true;
                }
            });
        }
    }
}
