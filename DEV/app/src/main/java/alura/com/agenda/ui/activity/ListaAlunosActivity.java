package alura.com.agenda.ui.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import alura.com.agenda.R;
import alura.com.agenda.dao.AlunoDAO;

public class ListaAlunosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Atribuindo nosso arquivo de layout a nossa activity
        setContentView(R.layout.activity_lista_alunos);

        //Pegando a lista de Alunos do DAO
        AlunoDAO dao = new AlunoDAO();

        //Atribuindo titulo
        setTitle("Lista de Alunos");


        //Pegando os campos criados no nosso layout e atribuindo valores
        ListView listaDeAlunos = findViewById(R.id.activity_lista_alunos_listview);

        listaDeAlunos.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                dao.todos()));
    }
}
