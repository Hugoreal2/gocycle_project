package isel.sisinf.model;

public class CreateClientDTO {
    public String nome;
    public String morada;
    public String email;
    public String telefone;
    public String ccPassaporte;
    public String nacionalidade;
    public Boolean ativo = true;

    public CreateClientDTO(String nome, String morada, String email, String telefone, String ccPassaporte, String nacionalidade) {
        this.nome = nome;
        this.morada = morada;
        this.email = email;
        this.telefone = telefone;
        this.ccPassaporte = ccPassaporte;
        this.nacionalidade = nacionalidade;
    }
}
