package com.example.company.agenda;

import android.widget.EditText;
import android.widget.RatingBar;

import com.example.company.agenda.modelo.Aluno;

/**
 * Created by milton on 02/05/2018.
 */

public class FormularioHelper {

    private EditText et_nome;
    private EditText et_endereco;
    private EditText et_telefone;
    private EditText et_site;
    private RatingBar rb_nota;

    public FormularioHelper(FormularioAluno activity) {

        //realiza todos os findviews direto no construtor
        et_nome = activity.findViewById(R.id.formulario_et_nome);
        et_endereco = activity.findViewById(R.id.formulario_et_endereco);
        et_telefone = activity.findViewById(R.id.formulario_et_telefone);
        et_site = activity.findViewById(R.id.formulario_et_site_pessoal);
        rb_nota = activity.findViewById(R.id.formulario_rb);

    }

    public Aluno pegaAluno() {

        //cria o objeto aluno
        Aluno aluno = new Aluno();

        //seta os valores capturados do formulario
        aluno.setNome(et_nome.getText().toString().trim());
        aluno.setEndereco(et_endereco.getText().toString().trim());
        aluno.setTelefone(et_telefone.getText().toString().trim());
        aluno.setSite(et_site.getText().toString().trim());
        aluno.setNota(Double.valueOf(rb_nota.getProgress()));


        return aluno;


    }
}
