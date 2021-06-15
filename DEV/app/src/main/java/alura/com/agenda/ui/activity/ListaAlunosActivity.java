package alura.com.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import alura.com.agenda.R;
import alura.com.agenda.dao.AlunoDAO;
import alura.com.agenda.model.Aluno;

import static alura.com.agenda.ui.activity.ConstantesActivities.CHAVE_ALUNO;

public class ListaAlunosActivity extends AppCompatActivity {

    private final AlunoDAO dao = new AlunoDAO();

    public static final String TITULO_APPBAR = "Lista de Alunos";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //Evento OnCreate
        super.onCreate(savedInstanceState);
        //Atribuindo nosso arquivo de layout a nossa activity
        setContentView(R.layout.activity_lista_alunos);
        //Atribuindo titulo
        setTitle(TITULO_APPBAR);
        configuraFabNovoAluno();
        dao.salva(new Aluno("Luciano", "123456798", "luciano@gmail.com"));
        dao.salva(new Aluno("Caio", "123456798", "caio@gmail.com"));
    }

    private void configuraFabNovoAluno() {
        //Inicializando o formularioAlunoActivity
        FloatingActionButton botaoNovoAluno = findViewById(R.id.activity_lista_alunos_fab_novo_aluno);
        botaoNovoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreFormularioModoInsereAluno();
            }
        });
    }

    private void abreFormularioModoInsereAluno() {
        startActivity(new Intent(this, FormularioAlunoActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        configuraLista();
    }

    private void configuraLista() {
        //Pegando os campos criados no nosso layout e atribuindo valores
        ListView listaDeAlunos = findViewById(R.id.activity_lista_alunos_listview);
        final List<Aluno> alunos = dao.todos();
        configuraAdapter(listaDeAlunos, alunos);
        configuraListenerDeCliquePorItem(listaDeAlunos);

        //Implementando a ação de pressão no botão clike long
        listaDeAlunos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Aluno alunoEscolhido = (Aluno) adapterView.getItemAtPosition(position);
                dao.remove(alunoEscolhido);
                return true;
            }
        });
    }

    private void configuraListenerDeCliquePorItem(ListView listaDeAlunos ) {
        //Nova acao para edicao dos campos
        listaDeAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Aluno alunoEscolhido = (Aluno) adapterView.getItemAtPosition(position);
                abreFormularioModoEditaAluno(alunoEscolhido);
            }
        });
    }

    private void abreFormularioModoEditaAluno(Aluno aluno) {
        Intent vaiParaFormularioActivity = new Intent(ListaAlunosActivity.this, FormularioAlunoActivity.class);
        //Transferindo dados entre Activity
        vaiParaFormularioActivity.putExtra(CHAVE_ALUNO, aluno);
        startActivity(vaiParaFormularioActivity);
    }

    private void configuraAdapter(ListView listaDeAlunos, List<Aluno> alunos) {
        listaDeAlunos.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                alunos));
    }
}
