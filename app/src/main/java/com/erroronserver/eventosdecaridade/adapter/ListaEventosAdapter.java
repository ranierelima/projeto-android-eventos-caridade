package com.erroronserver.eventosdecaridade.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.erroronserver.eventosdecaridade.EventoActivity;
import com.erroronserver.eventosdecaridade.R;
import com.erroronserver.eventosdecaridade.model.Evento;
import com.erroronserver.eventosdecaridade.util.Constantes;

import java.util.List;

/**
 * Created by Raniere de Lima - contato@erroronserver.com on 02/05/2017.
 */

public class ListaEventosAdapter extends BaseAdapter {
    private List<Evento> eventos;
    private Activity activity;

    public ListaEventosAdapter(List<Evento> eventos, Activity activity){
        this.eventos = eventos;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return eventos.size();
    }

    @Override
    public Object getItem(int position) {
        return eventos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return eventos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        final Evento evento = eventos.get(position);

        final LayoutInflater inflater = LayoutInflater.from(activity.getBaseContext());

        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.content_evento, parent, false);
        }

        TextView dtEvento = (TextView) view.findViewById(R.id.dt_evento);

        dtEvento.setText(evento.getDataEvento().toString());

        dtEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity.getBaseContext(), EventoActivity.class);
                intent.putExtra(Constantes.INTENT_EVENTO, evento);
                activity.startActivity(intent);
            }
        });

        return view;
    }
}
