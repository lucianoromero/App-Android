package alura.com.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import alura.com.agenda.R;
import alura.com.agenda.model.Aluno;
import alura.com.agenda.ui.ListaAlunosView;

import static alura.com.agenda.ui.activity.ConstantesActivities.CHAVE_ALUNO;

public class ListaAlunosActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Lista de Alunos";
    private final ListaAlunosView listaAlunosView = new ListaAlunosView(this);


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

    }

    //TODO Evento da criação do nosso menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_lista_alunos_menu, menu);
    }

    //TODO Menu de Contexto
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.activity_lista_alunos_menu_remover) {
            listaAlunosView.confirmaRemocao(item);
        }
        return super.onContextItemSelected(item);
    }


    //TODO Metodo Responsavel pela criação do nosso botão FAB
    private void configuraFabNovoAluno() {
        FloatingActionButton botaoNovoAluno = findViewById(R.id.activity_lista_alunos_fab_novo_aluno);
        botaoNovoAluno.setOnClickListener(v -> abreFormularioModoInsereAluno());
    }

    //TODO Metodo Responsavel pela abertura da classe formularioAlunoActivity
    private void abreFormularioModoInsereAluno() {
        startActivity(new Intent(this, FormularioAlunoActivity.class));
    }

    //TODO Evento do Ciclo de vida da Activity quando saimos e voltamos nela o evento tem a função de atualizar os dados
    @Override
    protected void onResume() {
        super.onResume();
        listaAlunosView.atualizaAlunos();
    }


    //TODO Metodo Responsavel pela configuração inicial da nossa Activity
    private void configuraLista() {
        ListView listaDeAlunos = findViewById(R.id.activity_lista_alunos_listview);
        listaAlunosView.configuraAdapter(listaDeAlunos);
        configuraListenerDeCliquePorItem(listaDeAlunos);
        registerForContextMenu(listaDeAlunos);
    }


    //TODO Metodo Responsavel pelo clique nos alunos na lista em pegar o aluno e abrir para outra Activity
    private void configuraListenerDeCliquePorItem(ListView listaDeAlunos) {
        //Nova acao para edicao dos campos
        listaDeAlunos.setOnItemClickListener((adapterView, view, position, id) -> {
            Aluno alunoEscolhido = (Aluno) adapterView.getItemAtPosition(position);
            abreFormularioModoEditaAluno(alunoEscolhido);
        });
    }

    //TODO Metodo Responsavel em abrir o outra Activity e passar os dados do aluno
    private void abreFormularioModoEditaAluno(Aluno aluno) {
        Intent vaiParaFormularioActivity = new Intent(ListaAlunosActivity.this, FormularioAlunoActivity.class);
        //Transferindo dados entre Activity
        vaiParaFormularioActivity.putExtra(CHAVE_ALUNO, aluno);
        startActivity(vaiParaFormularioActivity);
    }


}
