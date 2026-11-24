package br.com.fatec.atividade.model;

public class Roupa implements Identifiable {
    private Long id;
    private String tipo;
    private String cor;
    private double preco;

    public Roupa() {}

    public Roupa(String tipo, String cor, double preco) {
        this.tipo = tipo;
        this.cor = cor;
        this.preco = preco;
    }

    @Override public Long getId() { return id; }
    @Override public void setId(Long id) { this.id = id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }
    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

public void vestir() { System.out.println("A roupa " + tipo + " de cor " + cor + " foi vestida."); }
        public void vender() { System.out.println("A roupa foi vendida por R$ " + preco + "."); }
        public void exibirPreco() { System.out.println("Preço da roupa: R$ " + preco); }
}
