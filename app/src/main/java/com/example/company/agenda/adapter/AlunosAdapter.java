package com.example.company.agenda.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.company.agenda.MainActivity;
import com.example.company.agenda.R;
import com.example.company.agenda.modelo.Aluno;

import java.util.List;

/**
 * Created by milton on 16/05/2018.
 */

public class AlunosAdapter extends BaseAdapter {

    private final List<Aluno> alunos;
    private final Context context;

    public AlunosAdapter(Context context, List<Aluno> alunos) {
        this.context = context;
        this.alunos = alunos;
    }

    /**
     * retorna a quantidade de items na sua lista
     *
     * @return
     */
    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int position) {
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //pega um objeto aluno da lista
        Aluno aluno = alunos.get(position);

        //TextView textView = new TextView(context);
        //textView.setText(aluno.toString());


        LayoutInflater inflater = LayoutInflater.from(context);

        View view = convertView;

         if(view == null) {
             view = inflater.inflate(R.layout.list_item, parent, false);
         }

        //setando a view com a tela lista de itens
        TextView campoNome = view.findViewById(R.id.item_tv_nome);
        campoNome.setText(aluno.getNome());

        TextView campoTelefone = view.findViewById(R.id.item_tv_telefone);
        campoTelefone.setText(aluno.getTelefone());

        ImageView campoFoto = view.findViewById(R.id.item_foto);
        String caminhoFoto = aluno.getCaminhoFoto();

        if(caminhoFoto != null){
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 100,100, true);
            campoFoto.setImageBitmap(bitmapReduzido);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        return view;
    }
}
