package br.com.fatec.atividade.model;

public class Trilha implements Identifiable {
    private Long id;
    private String nome;
    private double distancia;
    private String dificuldade;

    public Trilha() {}

    public Trilha(String nome, double distancia, String dificuldade) {
        this.nome = nome;
        this.distancia = distancia;
        this.dificuldade = dificuldade;
    }

    @Override public Long getId() { return id; }
    @Override public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public double getDistancia() { return distancia; }
    public void setDistancia(double distancia) { this.distancia = distancia; }
    public String getDificuldade() { return dificuldade; }
    public void setDificuldade(String dificuldade) { this.dificuldade = dificuldade; }

public void iniciar() { System.out.println("A trilha " + nome + " foi iniciada."); }
        public void mapear() { System.out.println("A trilha de " + distancia + " km foi mapeada."); }
        public void finalizar() { System.out.println("A trilha foi finalizada. Dificuldade: " + dificuldade + "."); }
}
