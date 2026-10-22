package org.example.javafx;

// Os métodos retornam String (em vez de System.out.println) para a tela poder exibir o texto.

class Roupa {
    private String tipo;
    private String cor;
    private double preco;

    public Roupa(String tipo, String cor, double preco) {
        this.tipo = tipo;
        this.cor = cor;
        this.preco = preco;
    }

    public String vestir() {
        return "A roupa " + tipo + " de cor " + cor + " foi vestida.";
    }

    public String vender() {
        return String.format("A roupa foi vendida por R$ %.2f.", preco);
    }

    public String exibirPreco() {
        return String.format("Preço da roupa: R$ %.2f", preco);
    }
}

class Manequim {
    private String material;
    private String cor;
    private String roupa;

    public Manequim(String material, String cor, String roupa) {
        this.material = material;
        this.cor = cor;
        this.roupa = roupa;
    }

    public String vestirRoupa() {
        return "O manequim está usando " + roupa + ".";
    }

    public String exibir() {
        return "Manequim de cor " + cor + " em exposição.";
    }

    public String trocarRoupa() {
        return "A roupa do manequim foi trocada.";
    }
}

class Loja {
    private String nome;
    private String endereco;
    private int quantidadeFuncionarios;

    public Loja(String nome, String endereco, int quantidadeFuncionarios) {
        this.nome = nome;
        this.endereco = endereco;
        this.quantidadeFuncionarios = quantidadeFuncionarios;
    }

    public String abrir() {
        return "A loja " + nome + " foi aberta.";
    }

    public String fechar() {
        return "A loja " + nome + " foi fechada.";
    }

    public String exibirInformacoes() {
        return "Loja: " + nome + "\n"
             + "Endereço: " + endereco + "\n"
             + "Funcionários: " + quantidadeFuncionarios;
    }
}
