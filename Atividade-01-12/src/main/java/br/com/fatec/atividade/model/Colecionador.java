package br.com.fatec.atividade.model;

public class Colecionador implements Identifiable {
    private Long id;
    private String nome;
    private int quantidadePecas;
    private String especialidade;

    public Colecionador() {}

    public Colecionador(String nome, int quantidadePecas, String especialidade) {
        this.nome = nome;
        this.quantidadePecas = quantidadePecas;
        this.especialidade = especialidade;
    }

    @Override public Long getId() { return id; }
    @Override public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getQuantidadePecas() { return quantidadePecas; }
    public void setQuantidadePecas(int quantidadePecas) { this.quantidadePecas = quantidadePecas; }
    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }

public void adicionarMascara() {
            quantidadePecas++;
            System.out.println(nome + " adicionou uma nova máscara à coleção.");
        }
        public void pesquisarOrigem() { System.out.println(nome + " está pesquisando peças de " + especialidade + "."); }
        public void apresentarColecao() {
            System.out.println(nome + " possui " + quantidadePecas + " peças em sua coleção.");
        }
}
