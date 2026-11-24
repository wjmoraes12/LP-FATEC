package br.com.fatec.atividade.model;

public class Corte implements Identifiable {
    private Long id;
    private String estilo;
    private String comprimento;
    private double preco;

    public Corte() {}

    public Corte(String estilo, String comprimento, double preco) {
        this.estilo = estilo;
        this.comprimento = comprimento;
        this.preco = preco;
    }

    @Override public Long getId() { return id; }
    @Override public void setId(Long id) { this.id = id; }

    public String getEstilo() { return estilo; }
    public void setEstilo(String estilo) { this.estilo = estilo; }
    public String getComprimento() { return comprimento; }
    public void setComprimento(String comprimento) { this.comprimento = comprimento; }
    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

public void realizarCorte() { System.out.println("Realizando corte no estilo " + estilo + "."); }
        public void alterarEstilo(String novoEstilo) { this.estilo = novoEstilo; System.out.println("Estilo alterado para " + novoEstilo + "."); }
        public void exibirPreco() { System.out.println("Preço do corte: R$ " + preco); }
}
