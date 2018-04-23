package no.usn.a151710.bachelorprosjekt;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by GeorgPersen on 21.03.2018.
 */

public class RecyclerViewOppdragAdapter extends RecyclerView.Adapter<RecyclerViewOppdragAdapter.ViewHolder> {

    Context context;
    List<Oppdrag> oppdrag;
    public SharedPreferences prefWorkPlace;
    int arbeidsplass;

    public RecyclerViewOppdragAdapter(List<Oppdrag> oppdrag, Context context) {

        super();

        this.oppdrag = oppdrag;
        this.context = context;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview3_items, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;


    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Oppdrag oppdrag1 = oppdrag.get(position);

        holder.NameTextView.setText(oppdrag1.getName());
        holder.AddressTextView.setText(oppdrag1.getAddress());
        holder.MailTextView.setText(oppdrag1.getMail());
        holder.DescriptionTextView.setText(oppdrag1.getDescription());
        holder.StartDateTextView.setText(oppdrag1.getStartDate());
        holder.ExpDateTextView.setText(oppdrag1.getStartDate());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = oppdrag1.getName();
                int id = oppdrag1.getpID();
                //Toast.makeText(context, "Du registrerer timer for : " + name, Toast.LENGTH_SHORT).show();

                prefWorkPlace = context.getSharedPreferences("Arbeidsplass", 0); // 0 - for private mode
                SharedPreferences.Editor editor = prefWorkPlace.edit();

                editor.putString("Firma", name);
                editor.putInt("pID", id);
                editor.apply();

                TextView arbeidsplass = SeOppdrag.arbeidsplass;
                arbeidsplass.setText(oppdrag1.getName());

            }
        });


    }

    @Override
    public int getItemCount() { return oppdrag.size(); }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView NameTextView;
        public TextView AddressTextView;
        public TextView MailTextView;
        public TextView DescriptionTextView;
        public TextView StartDateTextView;
        public TextView ExpDateTextView;
        LinearLayout linearLayout;

        public ViewHolder(View itemView) {

            super(itemView);
            NameTextView = itemView.findViewById(R.id.oppdragText2);
            AddressTextView = itemView.findViewById(R.id.oppdragText4);
            MailTextView = itemView.findViewById(R.id.oppdragText6);
            DescriptionTextView = itemView.findViewById(R.id.oppdragText8);
            StartDateTextView = itemView.findViewById(R.id.oppdragText10);
            ExpDateTextView = itemView.findViewById(R.id.oppdragText12);
            linearLayout = itemView.findViewById(R.id.cardview3);

        }

    }
}
