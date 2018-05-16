package com.example.company.agenda;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.widget.EditText;
import android.widget.ImageView;
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
    private Aluno aluno;
    private ImageView img_foto;

    public FormularioHelper(FormularioAluno activity) {

        //realiza todos os findviews direto no construtor
        et_nome = activity.findViewById(R.id.formulario_et_nome);
        et_endereco = activity.findViewById(R.id.formulario_et_endereco);
        et_telefone = activity.findViewById(R.id.formulario_et_telefone);
        et_site = activity.findViewById(R.id.formulario_et_site_pessoal);
        rb_nota = activity.findViewById(R.id.formulario_rb);
        img_foto = activity.findViewById(R.id.formulario_img_foto);
        aluno = new Aluno();

    }

    public Aluno pegaAluno() {

        //seta os valores capturados do formulario
        aluno.setNome(et_nome.getText().toString().trim());
        aluno.setEndereco(et_endereco.getText().toString().trim());
        aluno.setTelefone(et_telefone.getText().toString().trim());
        aluno.setSite(et_site.getText().toString().trim());
        aluno.setNota(Double.valueOf(rb_nota.getProgress()));
        aluno.setCaminhoFoto((String) img_foto.getTag());

        return aluno;

    }

    public void preencherFormulario(Aluno aluno) {
        et_nome.setText(aluno.getNome());
        et_endereco.setText(aluno.getEndereco());
        et_telefone.setText(aluno.getTelefone());
        et_site.setText(aluno.getSite());
        rb_nota.setProgress(aluno.getNota().intValue());
        carregaImagem(aluno.getCaminhoFoto());

        this.aluno = aluno;

    }

    public void carregaImagem(String caminhoFoto) {

        if (caminhoFoto != null) {
            //converte a foto num bitmap
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);

            //reduz e trata a imagem recebida
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            //passa a imagem para imageview
            img_foto.setImageBitmap(bitmapReduzido);

            //seta a imagem para ocupar toda a imageview
            img_foto.setScaleType(ImageView.ScaleType.FIT_XY);
            img_foto.setTag(caminhoFoto);

        }
    }
}
