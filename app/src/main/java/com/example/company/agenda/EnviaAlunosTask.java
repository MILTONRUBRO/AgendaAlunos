package com.example.company.agenda;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.company.agenda.converter.AlunoConverter;
import com.example.company.agenda.dao.AlunoDAO;
import com.example.company.agenda.modelo.Aluno;

import java.util.List;

/**
 * Created by milton on 17/05/2018.
 */

public class EnviaAlunosTask extends AsyncTask<Object, Object, String> {

    private Context context;
    private ProgressDialog dialog;

    public EnviaAlunosTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        //mostra progresso da requisicao
        dialog = ProgressDialog.show(context, "Aguarde ", "Enviando Aunos ...", true, true);
    }

    @Override
    protected String doInBackground(Object[] objects) {

        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.buscaAlunos();

        dao.close();

        AlunoConverter conversor = new AlunoConverter();
        String json = conversor.converterParaJSON(alunos);


        WebClient client = new WebClient();

        //pega resposta do servidor
        String resposta = client.post(json);

        return resposta;
    }

    /**
     * Executa apos o doInBackground
     *
     * @param
     */
    @Override
    protected void onPostExecute(String resposta) {
        dialog.dismiss();
        Toast.makeText(context, resposta, Toast.LENGTH_LONG).show();
    }
}
