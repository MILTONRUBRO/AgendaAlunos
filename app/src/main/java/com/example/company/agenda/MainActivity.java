package com.example.company.agenda;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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

        //cria um menu de conteto para cada item da lista
        registerForContextMenu(lv_alunos);

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

        //evento chamado ao clicar em um elemento da lista
        lv_alunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Aluno aluno = (Aluno) lv_alunos.getItemAtPosition(position);

                Toast.makeText(context, "Aluno " + aluno.getNome() + "foi clicado", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
       MenuItem deletar = menu.add("Deletar");

       deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
           @Override
           public boolean onMenuItemClick(MenuItem menuItem) {

               AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

               Aluno aluno = (Aluno) lv_alunos.getItemAtPosition(info.position);

               AlunoDAO dao = new AlunoDAO(context);

               dao.deleta(aluno);

               dao.close();

                carregaLista();
               //Toast.makeText(context, "Deletar o aluno" + aluno.getNome(), Toast.LENGTH_SHORT).show();

               return false;
           }
       });


    }
}
