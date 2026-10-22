package org.example.javafx;

// Os métodos retornam String (em vez de System.out.println) para a tela poder exibir o texto.

class Mascara {
    private String origem;
    private String material;
    private String estilo;

    public Mascara(String origem, String material, String estilo) {
        this.origem = origem;
        this.material = material;
        this.estilo = estilo;
    }

    public String exibirDetalhes() {
        return "Máscara de origem " + origem +
                ", feita de " + material +
                ", estilo " + estilo + ".";
    }

    public String restaurar() {
        return "A máscara de " + origem + " está sendo restaurada.";
    }

    public String catalogar() {
        return "Máscara catalogada no acervo.";
    }
}

class Colecionador {
    private String nome;
    private int quantidadePecas;
    private String especialidade;

    public Colecionador(String nome, int quantidadePecas, String especialidade) {
        this.nome = nome;
        this.quantidadePecas = quantidadePecas;
        this.especialidade = especialidade;
    }

    public String adicionarMascara() {
        quantidadePecas++;
        return nome + " adicionou uma nova máscara à coleção.";
    }

    public String pesquisarOrigem() {
        return nome + " está pesquisando peças de " + especialidade + ".";
    }

    public String apresentarColecao() {
        return nome + " possui " + quantidadePecas + " peças em sua coleção.";
    }
}

class Exposicao {
    private String nome;
    private String local;
    private int quantidadeMascara;

    public Exposicao(String nome, String local, int quantidadeMascara) {
        this.nome = nome;
        this.local = local;
        this.quantidadeMascara = quantidadeMascara;
    }

    public String abrirExposicao() {
        return "A exposição \"" + nome + "\" foi aberta no local " + local + ".";
    }

    public String informarAcervo() {
        return "A exposição possui " + quantidadeMascara + " máscaras em exibição.";
    }

    public String encerrarExposicao() {
        return "A exposição \"" + nome + "\" foi encerrada.";
    }
}
