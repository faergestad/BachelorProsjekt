package no.usn.a151710.bachelorprosjekt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * Created by GeorgPersen on 02.03.2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    List<Ansatt> ansatt;
    RequestOptions options;

    public RecyclerViewAdapter(Context context, List<Ansatt> ansatt) {

        super();
        options = new RequestOptions()
                .circleCrop()
                .placeholder(R.drawable.ansatt)
                .error(R.drawable.ansatt);
        this.ansatt = ansatt;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {

        Ansatt ansatt1 = ansatt.get(position);

        holder.NummerTextView.setText(ansatt1.getfName() + " " + ansatt1.getlName());
        holder.StillingsTextView.setText(ansatt1.getPosition());

        // load image from the internet using Glide
        Glide.with(context).load(ansatt.get(position).getImage()).apply(options).into(holder.ansattImage);

    }

    @Override
    public int getItemCount() {
        return ansatt.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView ansattImage;
        public TextView NummerTextView;
        public TextView StillingsTextView;

        public ViewHolder(View itemView) {

            super(itemView);

            ansattImage = itemView.findViewById(R.id.ansattimg);
            NummerTextView = itemView.findViewById(R.id.textView2);
            StillingsTextView = itemView.findViewById(R.id.textView6);
        }

        @Override
        public void onClick(View v) {
            // TODO tilegn onClick til hvert element i RecyclerView
        }

    }

}
