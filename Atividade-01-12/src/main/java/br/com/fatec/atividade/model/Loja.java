package br.com.fatec.atividade.model;

public class Loja implements Identifiable {
    private Long id;
    private String nome;
    private String endereco;
    private int quantidadeFuncionarios;

    public Loja() {}

    public Loja(String nome, String endereco, int quantidadeFuncionarios) {
        this.nome = nome;
        this.endereco = endereco;
        this.quantidadeFuncionarios = quantidadeFuncionarios;
    }

    @Override public Long getId() { return id; }
    @Override public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public int getQuantidadeFuncionarios() { return quantidadeFuncionarios; }
    public void setQuantidadeFuncionarios(int quantidadeFuncionarios) { this.quantidadeFuncionarios = quantidadeFuncionarios; }

public void abrir() { System.out.println("A loja " + nome + " foi aberta."); }
        public void fechar() { System.out.println("A loja " + nome + " foi fechada."); }
        public void exibirInformacoes() {
            System.out.println("Loja: " + nome);
            System.out.println("Endereço: " + endereco);
            System.out.println("Funcionários: " + quantidadeFuncionarios);
        }
}
