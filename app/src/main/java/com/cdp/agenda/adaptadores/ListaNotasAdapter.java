package com.cdp.agenda.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cdp.agenda.R;
import com.cdp.agenda.VerActivity;
import com.cdp.agenda.entidades.Notas;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListaNotasAdapter extends RecyclerView.Adapter<ListaNotasAdapter.ContactoViewHolder> {

    ArrayList<Notas> listaNotas;
    ArrayList<Notas> listaOriginal;

    public ListaNotasAdapter(ArrayList<Notas> listaContactos) {
        this.listaNotas = listaContactos;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaContactos);
    }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_notas, null, false);
        return new ContactoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position) {
        holder.viewTitulo.setText(listaNotas.get(position).getTitulo());
        holder.viewDescripcion.setText(listaNotas.get(position).getDescripcion());
    }

    public void filtrado(final String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            listaNotas.clear();
            listaNotas.addAll(listaOriginal);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Notas> collecion = listaNotas.stream()
                        .filter(i -> i.getTitulo().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                listaNotas.clear();
                listaNotas.addAll(collecion);
            } else {
                for (Notas c : listaOriginal) {
                    if (c.getTitulo().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        listaNotas.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaNotas.size();
    }

    public class ContactoViewHolder extends RecyclerView.ViewHolder {

        TextView viewTitulo, viewDescripcion;

        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);

            viewTitulo = itemView.findViewById(R.id.viewTitulo);
            viewDescripcion = itemView.findViewById(R.id.viewDescripcion);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, VerActivity.class);
                    intent.putExtra("ID", listaNotas.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
