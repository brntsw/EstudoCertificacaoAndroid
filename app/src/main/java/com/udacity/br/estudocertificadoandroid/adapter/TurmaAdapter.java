package com.udacity.br.estudocertificadoandroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.br.estudocertificadoandroid.R;
import com.udacity.br.estudocertificadoandroid.model.Turma;

import java.util.List;

/**
 * Created by BPardini on 10/02/2017.
 */

public class TurmaAdapter extends RecyclerView.Adapter<TurmaAdapter.ViewHolder> {

    private List<Turma> turmas;

    public TurmaAdapter(List<Turma> turmas){
        this.turmas = turmas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.turma_item, parent, false);
        return new TurmaAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Turma turma = getTurma(position);
        if(turma != null){
            holder.apply(turma);
        }
    }

    @Override
    public int getItemCount() {
        return turmas.size();
    }

    public Turma getTurma(int position){
        return turmas.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvNome, tvNomeEscola;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNome = (TextView) itemView.findViewById(R.id.tv_nome);
            tvNomeEscola = (TextView) itemView.findViewById(R.id.tv_nome_escola);
        }

        void apply(Turma turma){
            tvNome.setText(turma.getNome());
            tvNomeEscola.setText(turma.getNomeEscola());
        }
    }

}
