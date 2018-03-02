package no.usn.a151710.bachelorprosjekt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

/**
 * Created by GeorgPersen on 02.03.2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    List<Ansatt> ansatt;

    public RecyclerViewAdapter(List<Ansatt> ansatt, Context context) {

        super();

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

        holder.NummerTextView.setText(String.valueOf(ansatt1.getUsername()));
        holder.TilgangsTextView.setText(String.valueOf(ansatt1.getAccessLevel()));
        holder.StillingsTextView.setText(ansatt1.getPosition());

    }

    @Override
    public int getItemCount() {
        return ansatt.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView NummerTextView;
        public TextView TilgangsTextView;
        public TextView StillingsTextView;

        public ViewHolder(View itemView) {

            super(itemView);

            NummerTextView = itemView.findViewById(R.id.textView2);
            TilgangsTextView = itemView.findViewById(R.id.textView4);
            StillingsTextView = itemView.findViewById(R.id.textView6);
        }

        @Override
        public void onClick(View v) {
            // TODO tilegn onClick til hvert element i RecyclerView
        }

    }

}
