package managers;

import models.Livro;
import models.Usuario;
import models.Emprestimo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe gerenciadora que controla todas as operações do sistema de biblioteca
 * Implementa padrão Singleton para garantir uma única instância
 */
public class BibliotecaManager {
    // Atributos privados para encapsulamento
    private List<Livro> livros;
    private List<Usuario> usuarios;
    private List<Emprestimo> emprestimos;
    
    // Instância única (Singleton)
    private static BibliotecaManager instancia;
    
    /**
     * Construtor privado para implementar Singleton
     */
    private BibliotecaManager() {
        this.livros = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.emprestimos = new ArrayList<>();
    }
    
    /**
     * Obtém a instância única do BibliotecaManager
     * @return Instância única do gerenciador
     */
    public static BibliotecaManager getInstancia() {
        if (instancia == null) {
            instancia = new BibliotecaManager();
        }
        return instancia;
    }
    
    // Métodos de gerenciamento de livros
    
    /**
     * Adiciona um novo livro ao sistema
     * @param livro Livro a ser adicionado
     * @return true se foi adicionado com sucesso, false caso contrário
     */
    public boolean adicionarLivro(Livro livro) {
        if (livro != null && !livros.contains(livro)) {
            livros.add(livro);
            return true;
        }
        return false;
    }
    
    /**
     * Remove um livro do sistema
     * @param livro Livro a ser removido
     * @return true se foi removido com sucesso, false caso contrário
     */
    public boolean removerLivro(Livro livro) {
        if (livro != null && livros.contains(livro)) {
            // Verifica se o livro não está emprestado
            if (!livro.isDisponivel()) {
                return false; // Não pode remover livro emprestado
            }
            livros.remove(livro);
            return true;
        }
        return false;
    }
    
    /**
     * Busca livros por título
     * @param titulo Título ou parte do título a ser buscado
     * @return Lista de livros que correspondem à busca
     */
    public List<Livro> buscarLivroPorTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        return livros.stream()
                .filter(livro -> livro.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    /**
     * Busca livros por autor
     * @param autor Nome do autor ou parte do nome
     * @return Lista de livros que correspondem à busca
     */
    public List<Livro> buscarLivroPorAutor(String autor) {
        if (autor == null || autor.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        return livros.stream()
                .filter(livro -> livro.getAutor().toLowerCase().contains(autor.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    /**
     * Obtém todos os livros disponíveis
     * @return Lista de livros disponíveis
     */
    public List<Livro> getLivrosDisponiveis() {
        return livros.stream()
                .filter(Livro::estaDisponivel)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtém todos os livros emprestados
     * @return Lista de livros emprestados
     */
    public List<Livro> getLivrosEmprestados() {
        return livros.stream()
                .filter(livro -> !livro.estaDisponivel())
                .collect(Collectors.toList());
    }
    
    // Métodos de gerenciamento de usuários
    
    /**
     * Adiciona um novo usuário ao sistema
     * @param usuario Usuário a ser adicionado
     * @return true se foi adicionado com sucesso, false caso contrário
     */
    public boolean adicionarUsuario(Usuario usuario) {
        if (usuario != null && !usuarios.contains(usuario)) {
            usuarios.add(usuario);
            return true;
        }
        return false;
    }
    
    /**
     * Remove um usuário do sistema
     * @param usuario Usuário a ser removido
     * @return true se foi removido com sucesso, false caso contrário
     */
    public boolean removerUsuario(Usuario usuario) {
        if (usuario != null && usuarios.contains(usuario)) {
            // Verifica se o usuário não tem livros emprestados
            if (usuario.getQuantidadeLivrosEmprestados() > 0) {
                return false; // Não pode remover usuário com livros emprestados
            }
            usuarios.remove(usuario);
            return true;
        }
        return false;
    }
    
    /**
     * Busca usuário por nome
     * @param nome Nome ou parte do nome a ser buscado
     * @return Lista de usuários que correspondem à busca
     */
    public List<Usuario> buscarUsuarioPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        return usuarios.stream()
                .filter(usuario -> usuario.getNome().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    /**
     * Busca usuário por ID
     * @param id ID do usuário
     * @return Usuário encontrado ou null se não encontrado
     */
    public Usuario buscarUsuarioPorId(String id) {
        return usuarios.stream()
                .filter(usuario -> usuario.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    // Métodos de gerenciamento de empréstimos
    
    /**
     * Realiza um empréstimo de livro
     * @param usuario Usuário que fará o empréstimo
     * @param livro Livro a ser emprestado
     * @return true se o empréstimo foi realizado com sucesso, false caso contrário
     */
    public boolean realizarEmprestimo(Usuario usuario, Livro livro) {
        if (usuario == null || livro == null) {
            return false;
        }
        
        // Verifica se o usuário pode emprestar
        if (!usuario.podeEmprestar()) {
            return false;
        }
        
        // Verifica se o livro está disponível
        if (!livro.estaDisponivel()) {
            return false;
        }
        
        // Verifica se o usuário já tem este livro emprestado
        if (usuario.temLivroEmprestado(livro)) {
            return false;
        }
        
        // Realiza o empréstimo
        if (livro.emprestar() && usuario.adicionarLivro(livro)) {
            Emprestimo emprestimo = new Emprestimo(usuario, livro, LocalDate.now());
            emprestimos.add(emprestimo);
            return true;
        }
        
        return false;
    }
    
    /**
     * Realiza a devolução de um livro
     * @param usuario Usuário que fará a devolução
     * @param livro Livro a ser devolvido
     * @return true se a devolução foi realizada com sucesso, false caso contrário
     */
    public boolean realizarDevolucao(Usuario usuario, Livro livro) {
        if (usuario == null || livro == null) {
            return false;
        }
        
        // Verifica se o usuário tem o livro emprestado
        if (!usuario.temLivroEmprestado(livro)) {
            return false;
        }
        
        // Realiza a devolução
        if (livro.devolver() && usuario.removerLivro(livro)) {
            // Atualiza o empréstimo correspondente
            Emprestimo emprestimo = buscarEmprestimoAtivo(usuario, livro);
            if (emprestimo != null) {
                emprestimo.realizarDevolucao(LocalDate.now());
            }
            return true;
        }
        
        return false;
    }
    
    /**
     * Busca um empréstimo ativo para usuário e livro específicos
     * @param usuario Usuário do empréstimo
     * @param livro Livro do empréstimo
     * @return Empréstimo ativo ou null se não encontrado
     */
    private Emprestimo buscarEmprestimoAtivo(Usuario usuario, Livro livro) {
        return emprestimos.stream()
                .filter(emprestimo -> emprestimo.getUsuario().equals(usuario) && 
                                     emprestimo.getLivro().equals(livro) && 
                                     emprestimo.estaAtivo())
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Obtém todos os empréstimos ativos
     * @return Lista de empréstimos ativos
     */
    public List<Emprestimo> getEmprestimosAtivos() {
        return emprestimos.stream()
                .filter(Emprestimo::estaAtivo)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtém todos os empréstimos em atraso
     * @return Lista de empréstimos em atraso
     */
    public List<Emprestimo> getEmprestimosAtrasados() {
        return emprestimos.stream()
                .filter(emprestimo -> emprestimo.verificarAtraso())
                .collect(Collectors.toList());
    }
    
    // Métodos de relatórios
    
    /**
     * Gera relatório de livros emprestados
     * @return String com o relatório
     */
    public String gerarRelatorioLivrosEmprestados() {
        List<Livro> livrosEmprestados = getLivrosEmprestados();
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("=== RELATÓRIO DE LIVROS EMPRESTADOS ===\n");
        relatorio.append("Total de livros emprestados: ").append(livrosEmprestados.size()).append("\n\n");
        
        for (Livro livro : livrosEmprestados) {
            relatorio.append(livro.toString()).append("\n");
        }
        
        return relatorio.toString();
    }
    
    /**
     * Gera relatório de usuários com multas
     * @return String com o relatório
     */
    public String gerarRelatorioUsuariosComMultas() {
        List<Emprestimo> emprestimosComMulta = emprestimos.stream()
                .filter(emprestimo -> emprestimo.getMulta() > 0)
                .collect(Collectors.toList());
        
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("=== RELATÓRIO DE USUÁRIOS COM MULTAS ===\n");
        relatorio.append("Total de empréstimos com multa: ").append(emprestimosComMulta.size()).append("\n\n");
        
        for (Emprestimo emprestimo : emprestimosComMulta) {
            relatorio.append("Usuário: ").append(emprestimo.getUsuario().getNome())
                    .append(" | Livro: ").append(emprestimo.getLivro().getTitulo())
                    .append(" | Multa: R$ ").append(String.format("%.2f", emprestimo.getMulta()))
                    .append(" | Dias de atraso: ").append(emprestimo.getDiasAtraso())
                    .append("\n");
        }
        
        return relatorio.toString();
    }
    
    /**
     * Gera relatório geral da biblioteca
     * @return String com o relatório completo
     */
    public String gerarRelatorioGeral() {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("=== RELATÓRIO GERAL DA BIBLIOTECA ===\n\n");
        
        // Estatísticas gerais
        relatorio.append("ESTATÍSTICAS GERAIS:\n");
        relatorio.append("Total de livros: ").append(livros.size()).append("\n");
        relatorio.append("Livros disponíveis: ").append(getLivrosDisponiveis().size()).append("\n");
        relatorio.append("Livros emprestados: ").append(getLivrosEmprestados().size()).append("\n");
        relatorio.append("Total de usuários: ").append(usuarios.size()).append("\n");
        relatorio.append("Usuários ativos: ").append(usuarios.stream().filter(Usuario::isAtivo).count()).append("\n");
        relatorio.append("Total de empréstimos: ").append(emprestimos.size()).append("\n");
        relatorio.append("Empréstimos ativos: ").append(getEmprestimosAtivos().size()).append("\n");
        relatorio.append("Empréstimos em atraso: ").append(getEmprestimosAtrasados().size()).append("\n\n");
        
        // Livros mais populares
        relatorio.append("LIVROS MAIS POPULARES:\n");
        // Implementação simplificada - em um sistema real seria mais complexo
        for (Livro livro : livros) {
            long emprestimosDoLivro = emprestimos.stream()
                    .filter(emprestimo -> emprestimo.getLivro().equals(livro))
                    .count();
            if (emprestimosDoLivro > 0) {
                relatorio.append(livro.getTitulo()).append(": ").append(emprestimosDoLivro).append(" empréstimos\n");
            }
        }
        
        return relatorio.toString();
    }
    
    // Getters para acesso às listas (retornam cópias para evitar modificação externa)
    
    public List<Livro> getLivros() {
        return new ArrayList<>(livros);
    }
    
    public List<Usuario> getUsuarios() {
        return new ArrayList<>(usuarios);
    }
    
    public List<Emprestimo> getEmprestimos() {
        return new ArrayList<>(emprestimos);
    }
} 