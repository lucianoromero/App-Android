package alura.com.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
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
    private ArrayAdapter<Aluno> adapter;

    //TODO Evento onCreate ele acontece quando a activety é criada
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Atribuindo nosso arquivo de layout a nossa activity
        setContentView(R.layout.activity_lista_alunos);
        //Atribuindo titulo
        setTitle(TITULO_APPBAR);
        configuraFabNovoAluno();
        configuraLista();
        dao.salva(new Aluno("Luciano", "123456798", "luciano@gmail.com"));
        dao.salva(new Aluno("Caio", "123456798", "caio@gmail.com"));

    }

    //TODO Evento da criação do nosso menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_lista_alunos_menu, menu);
    }

    //TODO Menu de Contexto
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.activity_lista_alunos_menu_remover) {
            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            Aluno alunoescolhido = adapter.getItem(menuInfo.position);
            remove(alunoescolhido);
        }
        return super.onContextItemSelected(item);
    }

    //TODO Metodo Responsavel pela criação do nosso botão FAB
    private void configuraFabNovoAluno() {
        FloatingActionButton botaoNovoAluno = findViewById(R.id.activity_lista_alunos_fab_novo_aluno);
        botaoNovoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreFormularioModoInsereAluno();
            }
        });
    }

    //TODO Metodo Responsavel pela abertura da classe formularioAlunoActivity
    private void abreFormularioModoInsereAluno() {
        startActivity(new Intent(this, FormularioAlunoActivity.class));
    }

    //TODO Evento do Ciclo de vida da Activity quando saimos e voltamos nela o evento tem a função de atualizar os dados
    @Override
    protected void onResume() {
        super.onResume();
        atualizaAlunos();


    }

    //TODO  Metodo  Responsavel  em atualizar os dados do nosso adapter
    private void atualizaAlunos() {
        adapter.clear();
        adapter.addAll(dao.todos());
    }

    //TODO Metodo Responsavel pela configuração inicial da nossa Activity
    private void configuraLista() {
        ListView listaDeAlunos = findViewById(R.id.activity_lista_alunos_listview);
        configuraAdapter(listaDeAlunos);
        configuraListenerDeCliquePorItem(listaDeAlunos);
        registerForContextMenu(listaDeAlunos);
    }


    //TODO Metodo Responsavel pelo Clique longo de remoção
//    private void configuraListenerDeCliqueLongoPorItem(ListView listaDeAlunos) {
//        listaDeAlunos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
//                Aluno alunoEscolhido = (Aluno) adapterView.getItemAtPosition(position);
//                remove(alunoEscolhido);
//                return true;
//            }
//        });
//    }

    //TODO Metodo Responsavel pela remoção
    private void remove(Aluno aluno) {
        dao.remove(aluno);
        adapter.remove(aluno);
    }

    //TODO Metodo Responsavel pelo clique nos alunos na lista em pegar o aluno e abrir para outra Activity
    private void configuraListenerDeCliquePorItem(ListView listaDeAlunos) {
        //Nova acao para edicao dos campos
        listaDeAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Aluno alunoEscolhido = (Aluno) adapterView.getItemAtPosition(position);
                abreFormularioModoEditaAluno(alunoEscolhido);
            }
        });
    }

    //TODO Metodo Responsavel em abrir o outra Activity e passar os dados do aluno
    private void abreFormularioModoEditaAluno(Aluno aluno) {
        Intent vaiParaFormularioActivity = new Intent(ListaAlunosActivity.this, FormularioAlunoActivity.class);
        //Transferindo dados entre Activity
        vaiParaFormularioActivity.putExtra(CHAVE_ALUNO, aluno);
        startActivity(vaiParaFormularioActivity);
    }

    //TODO Metodo responsavel por inserir a lista de alunos no activity_lista_alunos_listview
    private void configuraAdapter(ListView listaDeAlunos) {
        adapter = new ArrayAdapter<>(
                this,
                R.layout.item_aluno);
        listaDeAlunos.setAdapter(adapter);
    }
}
