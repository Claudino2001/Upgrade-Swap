package br.com.application.swapsegundo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_item_spinner extends ArrayAdapter<Moedas> {

    private final Context context;
    private final ArrayList<Moedas> moedas;

    public Adapter_item_spinner(Context context, ArrayList<Moedas> moedas) {
        super(context, 0, moedas);
        this.context = context;
        this.moedas = moedas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_spinner, parent, false);
        }

        TextView txtNomeMoeda = convertView.findViewById(R.id.txtNomeMoeda);
        ImageView imgMoeda = convertView.findViewById(R.id.imgMoeda);
        imgMoeda.setImageResource(moedas.get(position).getImagem());
        txtNomeMoeda.setText(moedas.get(position).getNome());

        return convertView;
    }
}
