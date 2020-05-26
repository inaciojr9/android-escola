package br.com.inaciojr9.retrofit.service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.inaciojr9.retrofit.R;
import br.com.inaciojr9.retrofit.model.Aluno;

public class AlunoAdapter extends ArrayAdapter<Aluno> {
    private final Context context;
    private final List<Aluno> elementos;

    public AlunoAdapter(Context context, List<Aluno> elementos) {
        super(context, R.layout.linha, elementos);
        this.context = context;
        this.elementos = elementos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.linha, parent, false);

        TextView txvNome = (TextView) rowView.findViewById(R.id.txtNome);
        txvNome.setText(elementos.get(position).getNome());

        return rowView;
    }
}