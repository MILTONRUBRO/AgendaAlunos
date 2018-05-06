package com.example.company.agenda;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.company.agenda.dao.AlunoDAO;
import com.example.company.agenda.modelo.Aluno;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lv_alunos;
    private ArrayAdapter adapter;
    private Context context;
    private Button btn_add_aluno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_inicial);


        initVars();
        initActions();


    }

    @Override
    protected void onResume() {
        super.onResume();

        carregaLista();
    }

    private void initVars() {
        context = getBaseContext();
        lv_alunos = findViewById(R.id.lv_lista_alunos);

        btn_add_aluno = findViewById(R.id.lista_btn_adiciona_aluno);

    }

    private void carregaLista() {
        AlunoDAO dao = new AlunoDAO(context);

        List<Aluno> alunos = dao.buscaAlunos();

        dao.close();

        //lista de alunos feita na mao
        //String[] alunos = {"Ragnar", "Rollo", "Floki", "Lagertha", "Bjorn"};


        adapter = new ArrayAdapter<Aluno>(context, android.R.layout.simple_list_item_1, alunos);

        lv_alunos.setAdapter(adapter);
    }


    private void initActions() {

        btn_add_aluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FormularioAluno.class);

                startActivity(intent);


            }
        });

    }


}
