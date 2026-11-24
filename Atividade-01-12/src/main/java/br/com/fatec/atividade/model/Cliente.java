package br.com.fatec.atividade.model;

public class Cliente implements Identifiable {
    private Long id;
    private String nome;
    private String telefone;
    private String tipoCabelo;

    public Cliente() {}

    public Cliente(String nome, String telefone, String tipoCabelo) {
        this.nome = nome;
        this.telefone = telefone;
        this.tipoCabelo = tipoCabelo;
    }

    @Override public Long getId() { return id; }
    @Override public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getTipoCabelo() { return tipoCabelo; }
    public void setTipoCabelo(String tipoCabelo) { this.tipoCabelo = tipoCabelo; }

public void agendarCorte() { System.out.println(nome + " agendou um corte de cabelo."); }
        public void escolherEstilo() { System.out.println(nome + " escolheu um estilo para cabelo " + tipoCabelo + "."); }
        public void atualizarTelefone() { System.out.println("Telefone de " + nome + " atualizado para " + telefone + "."); }
}
