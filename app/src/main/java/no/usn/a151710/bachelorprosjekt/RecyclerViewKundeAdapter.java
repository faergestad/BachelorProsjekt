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

public class RecyclerViewKundeAdapter extends RecyclerView.Adapter<RecyclerViewKundeAdapter.ViewHolder> {

    Context context;
    List<Kunde> kunde;

    public RecyclerViewKundeAdapter(List<Kunde> kunde, Context context) {

        super();

        this.kunde = kunde;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recylerview2_items, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Kunde kunde1 = kunde.get(position);

        holder.CustomerTextView.setText(kunde1.getCustomerName());
        holder.MailTextView.setText(kunde1.getMail());


    }

    @Override
    public int getItemCount() { return kunde.size(); }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView CustomerTextView;
        public TextView MailTextView;

        public ViewHolder(View itemView) {

            super(itemView);

            CustomerTextView = itemView.findViewById(R.id.kundeText2);
            MailTextView = itemView.findViewById(R.id.kundeText4);

        }

        @Override
        public void onClick(View view) {

        }
    }
}
