package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um usuário no sistema de controle de empréstimos
 * Implementa encapsulamento e gerencia a lista de livros emprestados
 */
public class Usuario {
    // Atributos privados para encapsulamento
    private String nome;
    private String id;
    private String email;
    private String telefone;
    private int limiteEmprestimos;
    private List<Livro> livrosEmprestados;
    private boolean ativo;
    
    // Constante para limite padrão de empréstimos
    private static final int LIMITE_PADRAO = 3;
    
    /**
     * Construtor padrão
     */
    public Usuario() {
        this.livrosEmprestados = new ArrayList<>();
        this.limiteEmprestimos = LIMITE_PADRAO;
        this.ativo = true;
    }
    
    /**
     * Construtor com sobrecarga para inicialização completa
     * @param nome Nome do usuário
     * @param id ID único do usuário
     * @param email Email do usuário
     * @param telefone Telefone do usuário
     */
    public Usuario(String nome, String id, String email, String telefone) {
        this.nome = nome;
        this.id = id;
        this.email = email;
        this.telefone = telefone;
        this.livrosEmprestados = new ArrayList<>();
        this.limiteEmprestimos = LIMITE_PADRAO;
        this.ativo = true;
    }
    
    /**
     * Construtor com limite personalizado de empréstimos
     * @param nome Nome do usuário
     * @param id ID único do usuário
     * @param email Email do usuário
     * @param telefone Telefone do usuário
     * @param limiteEmprestimos Limite de empréstimos permitidos
     */
    public Usuario(String nome, String id, String email, String telefone, int limiteEmprestimos) {
        this.nome = nome;
        this.id = id;
        this.email = email;
        this.telefone = telefone;
        this.limiteEmprestimos = limiteEmprestimos;
        this.livrosEmprestados = new ArrayList<>();
        this.ativo = true;
    }
    
    // Getters e Setters para acesso controlado aos atributos
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getTelefone() {
        return telefone;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public int getLimiteEmprestimos() {
        return limiteEmprestimos;
    }
    
    public void setLimiteEmprestimos(int limiteEmprestimos) {
        this.limiteEmprestimos = limiteEmprestimos;
    }
    
    public List<Livro> getLivrosEmprestados() {
        return new ArrayList<>(livrosEmprestados); // Retorna cópia para evitar modificação externa
    }
    
    public boolean isAtivo() {
        return ativo;
    }
    
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    /**
     * Verifica se o usuário pode emprestar mais livros
     * Considera o limite de empréstimos e se o usuário está ativo
     * @return true se pode emprestar, false caso contrário
     */
    public boolean podeEmprestar() {
        return ativo && livrosEmprestados.size() < limiteEmprestimos;
    }
    
    /**
     * Adiciona um livro à lista de livros emprestados
     * @param livro Livro a ser adicionado
     * @return true se foi adicionado com sucesso, false caso contrário
     */
    public boolean adicionarLivro(Livro livro) {
        if (podeEmprestar() && livro != null && !livrosEmprestados.contains(livro)) {
            livrosEmprestados.add(livro);
            return true;
        }
        return false;
    }
    
    /**
     * Remove um livro da lista de livros emprestados
     * @param livro Livro a ser removido
     * @return true se foi removido com sucesso, false caso contrário
     */
    public boolean removerLivro(Livro livro) {
        if (livro != null && livrosEmprestados.contains(livro)) {
            livrosEmprestados.remove(livro);
            return true;
        }
        return false;
    }
    
    /**
     * Obtém a quantidade de livros emprestados
     * @return Número de livros emprestados
     */
    public int getQuantidadeLivrosEmprestados() {
        return livrosEmprestados.size();
    }
    
    /**
     * Verifica se o usuário tem um livro específico emprestado
     * @param livro Livro a ser verificado
     * @return true se o usuário tem o livro emprestado, false caso contrário
     */
    public boolean temLivroEmprestado(Livro livro) {
        return livrosEmprestados.contains(livro);
    }
    
    /**
     * Obtém o número de empréstimos disponíveis
     * @return Número de empréstimos que ainda pode fazer
     */
    public int getEmprestimosDisponiveis() {
        return limiteEmprestimos - livrosEmprestados.size();
    }
    
    /**
     * Retorna uma representação em string do usuário
     * @return String formatada com informações do usuário
     */
    @Override
    public String toString() {
        String status = ativo ? "Ativo" : "Inativo";
        return String.format("Nome: %s | ID: %s | Email: %s | Telefone: %s | Status: %s | Livros Emprestados: %d/%d",
                nome, id, email, telefone, status, livrosEmprestados.size(), limiteEmprestimos);
    }
    
    /**
     * Compara dois usuários pelo ID
     * @param obj Objeto a ser comparado
     * @return true se os usuários têm o mesmo ID
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Usuario usuario = (Usuario) obj;
        return id != null ? id.equals(usuario.id) : usuario.id == null;
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
} 