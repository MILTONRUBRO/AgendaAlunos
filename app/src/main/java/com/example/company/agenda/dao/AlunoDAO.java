package com.example.company.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.company.agenda.modelo.Aluno;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by milton on 02/05/2018.
 * Classe com a logica de interacao com o banco de dados
 */

public class AlunoDAO extends SQLiteOpenHelper {

    public AlunoDAO(Context context) {
        super(context, "agenda", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //query para criacao da tabela
        String sql = "CREATE TABLE alunos(id INTEGER PRIMARY KEY, nome TEXT, endereco TEXT, telefone TEXT, site TEXT, nota REAL);";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS alunos ";

        db.execSQL(sql);
        onCreate(db);

    }

    public void insere(Aluno aluno) {

        //
        SQLiteDatabase db = getWritableDatabase();

        //funciona como um mapping usando chave-valor
        ContentValues dados = new ContentValues();

        dados.put("nome", aluno.getNome());
        dados.put("endereco", aluno.getEndereco());
        dados.put("telefone", aluno.getTelefone());
        dados.put("site", aluno.getSite());
        dados.put("nota", aluno.getNota());

        db.insert("alunos", null, dados);

    }

    public List<Aluno> buscaAlunos() {

        String sql = "SELECT * FROM alunos";

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        List<Aluno> alunos = new ArrayList<>();


        while(cursor.moveToNext()){
            Aluno aluno = new Aluno();
            aluno.setId(cursor.getLong(cursor.getColumnIndex("id")));
            aluno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            aluno.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
            aluno.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
            aluno.setSite(cursor.getString(cursor.getColumnIndex("site")));
            aluno.setNota(cursor.getDouble(cursor.getColumnIndex("nota")));

            alunos.add(aluno);

        }
        cursor.close();

        return alunos;

    }

    public void deleta(Aluno aluno) {

        SQLiteDatabase db = getWritableDatabase();

        String[] params = {String.valueOf(aluno.getId())};
        db.delete("alunos", "id = ?", params);


    }

    public void altera(Aluno aluno) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = new ContentValues();

        dados.put("nome", aluno.getNome());
        dados.put("endereco", aluno.getEndereco());
        dados.put("telefone", aluno.getTelefone());
        dados.put("site", aluno.getSite());
        dados.put("nota", aluno.getNota());

        String[] params = {aluno.getId().toString()};


        db.update("alunos", dados, "id = ?", params);



    }
}
