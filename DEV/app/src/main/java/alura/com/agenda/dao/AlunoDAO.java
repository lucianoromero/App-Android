package alura.com.agenda.dao;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import alura.com.agenda.model.Aluno;

public class AlunoDAO {

    private final static List<Aluno> alunos = new ArrayList<>();
    private static int contatorDeIds = 1;


    /**
     * @param aluno
     * Metodo Responsavel por salvar o Aluno
     */
    public void salva(Aluno aluno) {
        aluno.setId(contatorDeIds);
        alunos.add(aluno);
        atualizaIds();
    }

    private void atualizaIds() {
        contatorDeIds++;
    }

    /**
     * @param aluno
     * Metodo Responsavel por Editar um aluno
     */
    public void edita(Aluno aluno) {
        Aluno alunoEncontrado = buscaAlunoPeloID(aluno);

        if (alunoEncontrado != null) {
            int posicaoDoAluno = alunos.indexOf(alunoEncontrado);
            alunos.set(posicaoDoAluno, aluno);
        }
    }


    @Nullable
    private Aluno buscaAlunoPeloID(Aluno aluno) {
        for (Aluno a :
                alunos) {
            if (a.getId() == aluno.getId()) {
                return a;
            }
        }
        return null;
    }

    /**
     * @return Lista de Alunos
     * Metodo Responsavel por retorno a lista com todos alunos
     */
    public List<Aluno> todos() {
        return new ArrayList<>(alunos);
    }

    /**
     * @param aluno
     * Metodo Responsavel por remover um aluno
     */
    public void remove(Aluno aluno) {
        Aluno alunoDevolvido = buscaAlunoPeloID(aluno);
        if (alunoDevolvido != null) {
            alunos.remove(alunoDevolvido);
        }
    }
}
