package qbank.sistemabancario.dto.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import qbank.sistemabancario.context.conta.Conta;
import qbank.sistemabancario.strategy.transacao.Transacao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public final class Database {
    private static final String ACCOUNT_FILE_PATH = "src/main/java/qbank/sistemabancario/account/account.json";
    private static volatile Database instance;

    //AQUI INSTANCIAR OS TIPOS DE ARQUIVOS QUE SERAO TRABALHADOS NO .JSON - Conta,Transacao (pessoa)
    private List<Conta> contas;
    private List<Transacao> transacoes;
    private Integer contadorTransacoes;

    private Database() {
        loadData();
    }

    public static Database getInstance() {
        if (instance == null) {
            synchronized (Database.class) {
                if (instance == null) {
                    instance = new Database();
                }
            }
        }
        return instance;
    }

    private void loadData() {
        System.out.println("carregando Dados");
        ObjectMapper mapper = new ObjectMapper();
        File file = Paths.get(ACCOUNT_FILE_PATH).toFile();

        try {
            if (file.exists()) {
                DatabaseContainer container = mapper.readValue(file, DatabaseContainer.class);
                this.contas = container.getContas();
                this.transacoes = container.getTransacoes();
                this.contadorTransacoes = container.getContadorTransacoes();
            } else {
                this.contas = new ArrayList<>();
                this.transacoes = new ArrayList<>();
                this.contadorTransacoes = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
            this.contas = new ArrayList<>();
            this.transacoes = new ArrayList<>();
            this.contadorTransacoes = 0;
        }
    }

    public void saveData() {
        ObjectMapper mapper = new ObjectMapper();
        File file = Paths.get(ACCOUNT_FILE_PATH).toFile();
        try {
            DatabaseContainer container = new DatabaseContainer(contas, transacoes, contadorTransacoes);
            mapper.writeValue(file, container);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Conta> getContas() {
        return contas;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    public Integer getContadorTransacoes() {
        return contadorTransacoes;
    }

    public void setContas(List<Conta> contas) {
        this.contas = contas;
    }

    public void updateConta(Conta contaAtualizada) {
        for (int i = 0; i < contas.size(); i++) {
            if (contas.get(i).getNumero().equals(contaAtualizada.getNumero())) {
                contas.set(i, contaAtualizada);
                saveData();
                return;
            }
        }
        System.out.println("Conta não encontrada.");
    }

    public void addContador(){
        this.contadorTransacoes++;
    }
}
