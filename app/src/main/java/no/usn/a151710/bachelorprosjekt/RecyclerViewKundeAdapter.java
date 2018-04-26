package no.usn.a151710.bachelorprosjekt;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;

/**
 * Created by GeorgPersen on 02.03.2018.
 */

public class RecyclerViewKundeAdapter extends RecyclerView.Adapter<RecyclerViewKundeAdapter.ViewHolder> {

    Context context;
    List<Kunde> kunde;

    public RecyclerViewKundeAdapter(Context context, List<Kunde> kunde) {

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

        final Kunde kunde1 = kunde.get(position);

        holder.CustomerTextView.setText(kunde1.getCustomerName());
        holder.MailTextView.setText(kunde1.getMail());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, ansatt.getEpost(), Toast.LENGTH_SHORT).show();
                sendMail(kunde1.getMail());
            }
        });

    }

    public void sendMail(String epost) {
        String mottaker = epost;
        String emne = "Vedr jobb - sendt fra BachelorGAKK app";

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, mottaker);
        intent.putExtra(Intent.EXTRA_SUBJECT, emne);

        intent.setType("message/rfc822");
        context.startActivity(Intent.createChooser(intent, "Velg epost-klient"));
    }

    @Override
    public int getItemCount() { return kunde.size(); }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView CustomerTextView;
        public TextView MailTextView;
        LinearLayout linearLayout;

        public ViewHolder(View itemView) {

            super(itemView);

            CustomerTextView = itemView.findViewById(R.id.kundeText2);
            MailTextView = itemView.findViewById(R.id.kundeText4);
            linearLayout = itemView.findViewById(R.id.cardview2);

        }

    }
}
