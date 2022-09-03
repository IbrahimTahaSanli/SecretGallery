package com.tahasanli.secretgallery;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tahasanli.secretgallery.databinding.MainscreenrecyclegridBinding;

public class MainScreenAdapter extends RecyclerView.Adapter<MainScreenAdapter.MainScreenHolder> {


    @NonNull
    @Override
    public MainScreenHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MainscreenrecyclegridBinding holder = MainscreenrecyclegridBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new MainScreenHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull MainScreenHolder holder, int position) {
        holder.binding.MainScreenGridImage.setImageBitmap(MainScreen.instance.photos[position].photo);
        holder.binding.MainScreenGridText.setText(MainScreen.instance.photos[position].name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), AddScreen.class);
                intent.putExtra(AddScreen.IDKey, holder.getAdapterPosition());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return MainScreen.instance.photos.length;
    }

    public class MainScreenHolder extends RecyclerView.ViewHolder{
        private MainscreenrecyclegridBinding binding;

        public MainScreenHolder(@NonNull MainscreenrecyclegridBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
