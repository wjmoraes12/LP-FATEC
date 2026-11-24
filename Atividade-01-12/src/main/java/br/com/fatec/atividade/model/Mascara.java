package br.com.fatec.atividade.model;

public class Mascara implements Identifiable {
    private Long id;
    private String origem;
    private String material;
    private String estilo;

    public Mascara() {}

    public Mascara(String origem, String material, String estilo) {
        this.origem = origem;
        this.material = material;
        this.estilo = estilo;
    }

    @Override public Long getId() { return id; }
    @Override public void setId(Long id) { this.id = id; }

    public String getOrigem() { return origem; }
    public void setOrigem(String origem) { this.origem = origem; }
    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }
    public String getEstilo() { return estilo; }
    public void setEstilo(String estilo) { this.estilo = estilo; }

public void exibirDetalhes() {
            System.out.println("Máscara de origem " + origem + ", feita de " + material + ", estilo " + estilo + ".");
        }
        public void restaurar() { System.out.println("A máscara de " + origem + " está sendo restaurada."); }
        public void catalogar() { System.out.println("Máscara catalogada no acervo."); }
}
