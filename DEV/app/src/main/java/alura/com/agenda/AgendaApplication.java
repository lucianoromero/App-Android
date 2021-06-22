package alura.com.agenda;

import android.app.Application;

import alura.com.agenda.dao.AlunoDAO;
import alura.com.agenda.model.Aluno;

public class AgendaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        criaAlunosDeTeste();
    }

    private void criaAlunosDeTeste() {
        AlunoDAO dao = new AlunoDAO();
        dao.salva(new Aluno("Luciano", "123456798", "luciano@gmail.com"));
        dao.salva(new Aluno("Caio", "123456798", "caio@gmail.com"));
    }
}
