package br.com.fatec.atividade.model;

public class Cabeleireiro implements Identifiable {
    private Long id;
    private String nome;
    private String especialidade;
    private int anosExperiencia;

    public Cabeleireiro() {}

    public Cabeleireiro(String nome, String especialidade, int anosExperiencia) {
        this.nome = nome;
        this.especialidade = especialidade;
        this.anosExperiencia = anosExperiencia;
    }

    @Override public Long getId() { return id; }
    @Override public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
    public int getAnosExperiencia() { return anosExperiencia; }
    public void setAnosExperiencia(int anosExperiencia) { this.anosExperiencia = anosExperiencia; }

public void atenderCliente() { System.out.println(nome + " está atendendo um cliente."); }
        public void demonstrarEspecialidade() { System.out.println(nome + " é especialista em " + especialidade + "."); }
        public void informarExperiencia() { System.out.println(nome + " possui " + anosExperiencia + " anos de experiência."); }
}
