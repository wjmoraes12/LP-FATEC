package br.com.fatec.atividade.model;

public class Ciclista implements Identifiable {
    private Long id;
    private String nome;
    private String nivel;
    private double velocidade;

    public Ciclista() {}

    public Ciclista(String nome, String nivel, double velocidade) {
        this.nome = nome;
        this.nivel = nivel;
        this.velocidade = velocidade;
    }

    @Override public Long getId() { return id; }
    @Override public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }
    public double getVelocidade() { return velocidade; }
    public void setVelocidade(double velocidade) { this.velocidade = velocidade; }

public void pedalar() { System.out.println(nome + " está pedalando pela trilha."); }
        public void frear() { System.out.println(nome + " está freando a bicicleta."); }
        public void acelerar() { System.out.println(nome + " está acelerando para " + velocidade + " km/h."); }
}
