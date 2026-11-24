package br.com.fatec.atividade.model;

public class Exposicao implements Identifiable {
    private Long id;
    private String nome;
    private String local;
    private int quantidadeMascara;

    public Exposicao() {}

    public Exposicao(String nome, String local, int quantidadeMascara) {
        this.nome = nome;
        this.local = local;
        this.quantidadeMascara = quantidadeMascara;
    }

    @Override public Long getId() { return id; }
    @Override public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }
    public int getQuantidadeMascara() { return quantidadeMascara; }
    public void setQuantidadeMascara(int quantidadeMascara) { this.quantidadeMascara = quantidadeMascara; }

public void abrirExposicao() { System.out.println("A exposição " + nome + " foi aberta no local " + local + "."); }
        public void informarAcervo() { System.out.println("A exposição possui " + quantidadeMascara + " máscaras em exibição."); }
        public void encerrarExposicao() { System.out.println("A exposição " + nome + " foi encerrada."); }
}
