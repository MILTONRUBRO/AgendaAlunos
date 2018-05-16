package com.example.company.agenda;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.company.agenda.dao.AlunoDAO;
import com.example.company.agenda.modelo.Aluno;

import java.io.File;
import java.util.zip.Inflater;

/**
 * Created by milton on 29/04/2018.
 */

public class FormularioAluno extends AppCompatActivity {

    public static final int CODIGO_CAMERA = 567;
    private FormularioHelper helper;
    private Context context;
    private Button btn_foto;
    private ImageView img_foto;
    private String caminhoFoto;

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

        btn_foto = findViewById(R.id.formulario_btn_foto);
        img_foto = findViewById(R.id.formulario_img_foto);

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

        //evento chamado caso o botao da foto seja clicado
        btn_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                caminhoFoto =  getExternalFilesDir(null) +"/" + System.currentTimeMillis() + ".jpg";
                File arquivoFoto = new File(caminhoFoto);

                //intent que passa ao Android solicitacao para camera
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));

                //pede para o Android iniciar a activity e espera por um resultado
                startActivityForResult(intentCamera, CODIGO_CAMERA);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK) {

            //verifica qual codigo foi retornado pelo android
            switch (requestCode) {

                case CODIGO_CAMERA:
                    /**
                    //converte a foto num bitmap
                    Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);

                    //reduz e trata a imagem recebida
                    Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
                    //passa a imagem para imageview
                    img_foto.setImageBitmap(bitmapReduzido);

                    //seta a imagem para ocupar toda a imageview
                    img_foto.setScaleType(ImageView.ScaleType.FIT_XY);
                    img_foto.setTag(caminhoFoto);
                    **/
                    helper.carregaImagem(caminhoFoto);

                    break;

                default:
                    break;
            }

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
