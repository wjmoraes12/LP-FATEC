package br.com.fatec.atividade.model;

public class Manequim implements Identifiable {
    private Long id;
    private String material;
    private String cor;
    private String roupa;

    public Manequim() {}

    public Manequim(String material, String cor, String roupa) {
        this.material = material;
        this.cor = cor;
        this.roupa = roupa;
    }

    @Override public Long getId() { return id; }
    @Override public void setId(Long id) { this.id = id; }

    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }
    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }
    public String getRoupa() { return roupa; }
    public void setRoupa(String roupa) { this.roupa = roupa; }

public void vestirRoupa() { System.out.println("O manequim está usando " + roupa + "."); }
        public void exibir() { System.out.println("Manequim de cor " + cor + " em exposição."); }
        public void trocarRoupa() { System.out.println("A roupa do manequim foi trocada."); }
}
