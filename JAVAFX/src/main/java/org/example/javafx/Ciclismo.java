package org.example.javafx;

// Os métodos retornam String (em vez de System.out.println) para a tela poder exibir o texto.

class Ciclista {
    private String nome;
    private String nivel;
    private double velocidade;

    public Ciclista(String nome, String nivel, double velocidade) {
        this.nome = nome;
        this.nivel = nivel;
        this.velocidade = velocidade;
    }

    public String pedalar() {
        return nome + " está pedalando pela trilha.";
    }

    public String frear() {
        return nome + " está freando a bicicleta.";
    }

    public String acelerar() {
        return String.format("%s está acelerando para %.1f km/h.", nome, velocidade);
    }
}

class Bicicleta {
    private String marca;
    private int aro;
    private String tipoFreio;

    public Bicicleta(String marca, int aro, String tipoFreio) {
        this.marca = marca;
        this.aro = aro;
        this.tipoFreio = tipoFreio;
    }

    public String pedalar() {
        return "A bicicleta " + marca + " está sendo utilizada.";
    }

    public String calibrarPneu() {
        return "Os pneus da bicicleta foram calibrados.";
    }

    public String frear() {
        return "A bicicleta está utilizando freio " + tipoFreio + ".";
    }
}

class Trilha {
    private String nome;
    private double distancia;
    private String dificuldade;

    public Trilha(String nome, double distancia, String dificuldade) {
        this.nome = nome;
        this.distancia = distancia;
        this.dificuldade = dificuldade;
    }

    public String iniciar() {
        return "A trilha " + nome + " foi iniciada.";
    }

    public String mapear() {
        return String.format("A trilha de %.1f km foi mapeada.", distancia);
    }

    public String finalizar() {
        return "A trilha foi finalizada. Dificuldade: " + dificuldade + ".";
    }
}
