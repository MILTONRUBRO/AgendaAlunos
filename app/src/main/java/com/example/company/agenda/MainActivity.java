package com.example.company.agenda;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Browser;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.company.agenda.adapter.AlunosAdapter;
import com.example.company.agenda.converter.AlunoConverter;
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

        //monta a lista com o adapter
        //adapter = new ArrayAdapter<Aluno>(context, android.R.layout.simple_list_item_1, alunos);

        AlunosAdapter alunosAdapter = new AlunosAdapter(this, alunos);

        lv_alunos.setAdapter(alunosAdapter);
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

                //Toast.makeText(context, "Aluno " + aluno.getNome() + "foi clicado", Toast.LENGTH_SHORT).show();

                //objeto utilizado para mudancas entre activites
                Intent intent = new Intent(MainActivity.this, FormularioAluno.class);

                //adicionando mais informacoes a intent  para ser recuperado na tela chamada
                intent.putExtra("aluno", aluno);

                startActivity(intent);


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_alunos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_enviar_notas:
               new EnviaAlunosTask(MainActivity.this).execute();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {

        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno) lv_alunos.getItemAtPosition(info.position);

        //Adiciona ligar ao menu
        MenuItem itemLigar = menu.add("Ligar");

        itemLigar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 123);

                }else{
                    Intent intentLigar = new Intent(Intent.ACTION_CALL);
                    intentLigar.setData(Uri.parse("tel:" + aluno.getTelefone()));
                    startActivity(intentLigar);
                }

                return false;
            }
        });


        //adicionando enviar sms para o menu
        MenuItem itemSMS = menu.add("Enviar SMS");

        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:" + aluno.getTelefone()));
        itemSMS.setIntent(intentSMS);

        //adicionando visualizar no mapa
        MenuItem itemMapa = menu.add("Visualizar no Mapa");

        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?q=" + aluno.getEndereco()));
        itemMapa.setIntent(intentMapa);

        //menu para adicionar  visitar site do aluno
        MenuItem itemSite = menu.add("Visitar Site");

        //Intent Explicita
        //Intent intentSite = new Intent (context, Browser.class);

        //Intent implicita
        Intent intentSite = new Intent(Intent.ACTION_VIEW);


        String site = aluno.getSite();
        if (site.startsWith("http://")) {
            site = "http://" + site;
        }


        intentSite.setData(Uri.parse(site));

        itemSite.setIntent(intentSite);

        //criando um menu na  mao
        MenuItem deletar = menu.add("Deletar");

        //listener que aguarda evento do click de um elemento no item do menu
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

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
