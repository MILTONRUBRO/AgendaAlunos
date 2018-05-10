package com.example.company.agenda;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.company.agenda.dao.AlunoDAO;
import com.example.company.agenda.modelo.Aluno;

import java.util.zip.Inflater;

/**
 * Created by milton on 29/04/2018.
 */

public class FormularioAluno extends AppCompatActivity {

    private FormularioHelper helper;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_formulario);

        initVars();
        initAction();
    }

    private void initVars() {
        context = getBaseContext();
        helper = new FormularioHelper(this);

    }

    private void initAction() {

        //recebendo valores da intent da MainActivity
        Intent intent = getIntent();

        //captura as informacoes extras que foram passadas com serializable
        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");

        //caso o aluno seja diferente de nulo chama se o helper para preencher os valores
        if (aluno != null) {
            helper.preencherFormulario(aluno);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        //infla o menu para adicionar o icone de ok
        inflater.inflate(R.menu.formulario_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            //verifica qual item o usuario apertou
            case R.id.menu_formulario_ok:

                Aluno aluno = helper.pegaAluno();

                //criando o objeto dao para manipular acoes do banco
                AlunoDAO dao = new AlunoDAO(context);

                if (aluno.getId() != null) {
                    dao.altera(aluno);
                } else {
                    dao.insere(aluno);
                }


                dao.close();

                Toast.makeText(context, "Aluno  " + aluno.getNome() + " adicionado com sucesso", Toast.LENGTH_SHORT).show();

                finish();
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
