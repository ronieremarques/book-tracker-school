package models;

import java.util.Date;

/**
 * Classe que representa um livro no sistema de controle de empréstimos
 * Implementa encapsulamento com atributos privados e métodos públicos
 */
public class Livro {
    // Atributos privados para encapsulamento
    private String titulo;
    private String autor;
    private boolean disponivel;
    private String isbn;
    private String genero;
    private int anoPublicacao;
    private String editora;
    
    /**
     * Construtor padrão
     */
    public Livro() {
        this.disponivel = true; // Livro inicia como disponível
    }
    
    /**
     * Construtor com sobrecarga para inicialização completa
     * @param titulo Título do livro
     * @param autor Autor do livro
     * @param isbn ISBN do livro
     * @param genero Gênero literário
     * @param anoPublicacao Ano de publicação
     * @param editora Editora do livro
     */
    public Livro(String titulo, String autor, String isbn, String genero, int anoPublicacao, String editora) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.genero = genero;
        this.anoPublicacao = anoPublicacao;
        this.editora = editora;
        this.disponivel = true; // Livro inicia como disponível
    }
    
    // Getters e Setters para acesso controlado aos atributos
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getAutor() {
        return autor;
    }
    
    public void setAutor(String autor) {
        this.autor = autor;
    }
    
    public boolean isDisponivel() {
        return disponivel;
    }
    
    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public String getGenero() {
        return genero;
    }
    
    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    public int getAnoPublicacao() {
        return anoPublicacao;
    }
    
    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }
    
    public String getEditora() {
        return editora;
    }
    
    public void setEditora(String editora) {
        this.editora = editora;
    }
    
    /**
     * Método para emprestar o livro
     * Verifica se o livro está disponível antes de emprestar
     * @return true se o empréstimo foi realizado com sucesso, false caso contrário
     */
    public boolean emprestar() {
        if (this.disponivel) {
            this.disponivel = false;
            return true;
        }
        return false;
    }
    
    /**
     * Método para devolver o livro
     * Marca o livro como disponível novamente
     * @return true se a devolução foi realizada com sucesso
     */
    public boolean devolver() {
        if (!this.disponivel) {
            this.disponivel = true;
            return true;
        }
        return false;
    }
    
    /**
     * Verifica se o livro está disponível para empréstimo
     * @return true se o livro está disponível, false caso contrário
     */
    public boolean estaDisponivel() {
        return this.disponivel;
    }
    
    /**
     * Retorna uma representação em string do livro
     * @return String formatada com informações do livro
     */
    @Override
    public String toString() {
        String status = disponivel ? "Disponível" : "Emprestado";
        return String.format("Título: %s | Autor: %s | ISBN: %s | Gênero: %s | Ano: %d | Editora: %s | Status: %s",
                titulo, autor, isbn, genero, anoPublicacao, editora, status);
    }
    
    /**
     * Compara dois livros pelo ISBN
     * @param obj Objeto a ser comparado
     * @return true se os livros têm o mesmo ISBN
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Livro livro = (Livro) obj;
        return isbn != null ? isbn.equals(livro.isbn) : livro.isbn == null;
    }
    
    @Override
    public int hashCode() {
        return isbn != null ? isbn.hashCode() : 0;
    }
} 