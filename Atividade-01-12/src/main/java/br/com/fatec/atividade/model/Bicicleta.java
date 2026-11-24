package br.com.fatec.atividade.model;

public class Bicicleta implements Identifiable {
    private Long id;
    private String marca;
    private int aro;
    private String tipoFreio;

    public Bicicleta() {}

    public Bicicleta(String marca, int aro, String tipoFreio) {
        this.marca = marca;
        this.aro = aro;
        this.tipoFreio = tipoFreio;
    }

    @Override public Long getId() { return id; }
    @Override public void setId(Long id) { this.id = id; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public int getAro() { return aro; }
    public void setAro(int aro) { this.aro = aro; }
    public String getTipoFreio() { return tipoFreio; }
    public void setTipoFreio(String tipoFreio) { this.tipoFreio = tipoFreio; }

public void pedalar() { System.out.println("A bicicleta " + marca + " está sendo utilizada."); }
        public void calibrarPneu() { System.out.println("Os pneus da bicicleta foram calibrados."); }
        public void frear() { System.out.println("A bicicleta está utilizando freio " + tipoFreio + "."); }
}
