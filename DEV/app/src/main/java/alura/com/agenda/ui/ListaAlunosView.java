package alura.com.agenda.ui;

import android.content.Context;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;

import alura.com.agenda.dao.AlunoDAO;
import alura.com.agenda.model.Aluno;
import alura.com.agenda.ui.adapter.ListaAlunosAdapter;

public class ListaAlunosView {

    private final ListaAlunosAdapter adapter;
    private final AlunoDAO dao;
    private final Context context;

    public ListaAlunosView(Context context) {
        this.context = context;
        this.adapter = new ListaAlunosAdapter(this.context);
        this.dao = new AlunoDAO();
    }

    //TODO Metodo Responsavel por aplicar a caixa de dialog e remover o aluno
    public void confirmaRemocao(final MenuItem item) {
        new AlertDialog
                .Builder(context)
                .setTitle("Removendo aluno")
                .setMessage("Tem certeza que quer remover o aluno?")
                .setPositiveButton("Sim", (dialog, which) -> {
                    AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                    Aluno alunoescolhido = adapter.getItem(menuInfo.position);
                    remove(alunoescolhido);
                })
                .setNegativeButton("Não", null)
                .show();
    }

    //TODO  Metodo  Responsavel  em atualizar os dados do nosso adapter
    public void atualizaAlunos() {
        adapter.atualiza(dao.todos());
    }

    //TODO Metodo Responsavel pela remoção
    public void remove(Aluno aluno) {
        dao.remove(aluno);
        adapter.remove(aluno);
    }

    //TODO Metodo responsavel por inserir a lista de alunos no activity_lista_alunos_listview
    public void configuraAdapter(ListView listaDeAlunos) {
        listaDeAlunos.setAdapter(adapter);
    }
}
