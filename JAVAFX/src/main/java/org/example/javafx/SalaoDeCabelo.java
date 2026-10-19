package org.example.javafx;

// Os métodos retornam String (em vez de System.out.println) para a tela poder exibir o texto.

class Cliente {
    private String nome;
    private String telefone;
    private String tipoCabelo;

    public Cliente(String nome, String telefone, String tipoCabelo) {
        this.nome = nome;
        this.telefone = telefone;
        this.tipoCabelo = tipoCabelo;
    }

    public String agendarCorte() {
        return nome + " agendou um corte de cabelo.";
    }

    public String escolherEstilo() {
        return nome + " escolheu um estilo para cabelo " + tipoCabelo + ".";
    }

    public String atualizarTelefone() {
        return "Telefone de " + nome + " atualizado para " + telefone + ".";
    }
}

class Corte {
    private String estilo;
    private String comprimento;
    private double preco;

    public Corte(String estilo, String comprimento, double preco) {
        this.estilo = estilo;
        this.comprimento = comprimento;
        this.preco = preco;
    }

    public String realizarCorte() {
        return "Realizando corte no estilo " + estilo + ".";
    }

    public String alterarEstilo(String novoEstilo) {
        this.estilo = novoEstilo;
        return "Estilo alterado para " + novoEstilo + ".";
    }

    public String exibirPreco() {
        return String.format("Preço do corte: R$ %.2f", preco);
    }
}

class Cabeleireiro {
    private String nome;
    private String especialidade;
    private int anosExperiencia;

    public Cabeleireiro(String nome, String especialidade, int anosExperiencia) {
        this.nome = nome;
        this.especialidade = especialidade;
        this.anosExperiencia = anosExperiencia;
    }

    public String atenderCliente() {
        return nome + " está atendendo um cliente.";
    }

    public String demonstrarEspecialidade() {
        return nome + " é especialista em " + especialidade + ".";
    }

    public String informarExperiencia() {
        return nome + " possui " + anosExperiencia + " anos de experiência.";
    }
}
