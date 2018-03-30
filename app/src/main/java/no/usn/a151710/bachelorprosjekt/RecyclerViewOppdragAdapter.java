package no.usn.a151710.bachelorprosjekt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by GeorgPersen on 21.03.2018.
 */

public class RecyclerViewOppdragAdapter extends RecyclerView.Adapter<RecyclerViewOppdragAdapter> {

    Context context;
    List<Oppdrag> oppdrag;

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
    public void onBindViewHolder(ViewHolder holder, int position) {

        Oppdrag oppdrag1 = oppdrag.get(position);

        holder.NameTextView.setText(oppdrag1.getName());
        holder.AddressTextView.setText(oppdrag1.getAddress());
        holder.MailTextView.setText(oppdrag1.getMail());
        holder.DescriptionTextView.setText(oppdrag1.getDescription());
        holder.StartDateTextView.setText(oppdrag1.getStartDate());
        holder.ExpDateTextView.setText(oppdrag1.getStartDate());


    }

    @Override
    public int getItemCount() { return oppdrag.size(); }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView NameTextView;
        public TextView AddressTextView;
        public TextView MailTextView;
        public TextView DescriptionTextView;
        public TextView StartDateTextView;
        public TextView ExpDateTextView;

        public ViewHolder(View itemView) {

            super(itemView);

            NameTextView = itemView.findViewById(R.id.oppdragText2);
            AddressTextView = itemView.findViewById(R.id.oppdragText4);
            MailTextView = itemView.findViewById(R.id.oppdragText6);
            DescriptionTextView = itemView.findViewById(R.id.oppdragText8);
            StartDateTextView = itemView.findViewById(R.id.oppdragText10);
            ExpDateTextView = itemView.findViewById(R.id.oppdragText12);


        }

        @Override
        public void onClick(View view) {


        }
    }
}
