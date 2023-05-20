package br.com.application.swapsegundo;

import java.util.ArrayList;
import java.util.List;

public class Moedas {

    private String code;
    private String nome;
    private int imagem;

    public Moedas(String code, String nome, int imagem) {
        this.nome = nome;
        this.imagem = imagem;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }
}
