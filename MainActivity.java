import models.Livro;
import models.Usuario;
import models.Emprestimo;
import managers.BibliotecaManager;

import java.time.LocalDate;
import java.util.List;

/**
 * Classe principal que demonstra o uso do sistema BookTracker
 * Simula a integração com interface Android
 */
public class MainActivity {
    
    public static void main(String[] args) {
        System.out.println("=== SISTEMA BOOKTRACKER - DEMONSTRAÇÃO ===\n");
        
        // Obtém a instância única do gerenciador
        BibliotecaManager biblioteca = BibliotecaManager.getInstancia();
        
        // Demonstração do sistema
        demonstrarSistema(biblioteca);
    }
    
    /**
     * Demonstra todas as funcionalidades do sistema
     * @param biblioteca Instância do gerenciador da biblioteca
     */
    private static void demonstrarSistema(BibliotecaManager biblioteca) {
        System.out.println("1. CADASTRANDO LIVROS...");
        cadastrarLivros(biblioteca);
        
        System.out.println("\n2. CADASTRANDO USUÁRIOS...");
        cadastrarUsuarios(biblioteca);
        
        System.out.println("\n3. REALIZANDO EMPRÉSTIMOS...");
        realizarEmprestimos(biblioteca);
        
        System.out.println("\n4. CONSULTANDO DADOS...");
        consultarDados(biblioteca);
        
        System.out.println("\n5. REALIZANDO DEVOLUÇÕES...");
        realizarDevolucoes(biblioteca);
        
        System.out.println("\n6. GERANDO RELATÓRIOS...");
        gerarRelatorios(biblioteca);
        
        System.out.println("\n7. TESTANDO FUNCIONALIDADES ESPECIAIS...");
        testarFuncionalidadesEspeciais(biblioteca);
    }
    
    /**
     * Cadastra livros de exemplo no sistema
     * @param biblioteca Gerenciador da biblioteca
     */
    private static void cadastrarLivros(BibliotecaManager biblioteca) {
        // Criando livros com construtores sobrecarregados
        Livro livro1 = new Livro("O Senhor dos Anéis", "J.R.R. Tolkien", "978-8533613379", "Fantasia", 1954, "Martins Fontes");
        Livro livro2 = new Livro("1984", "George Orwell", "978-8535909555", "Ficção Científica", 1949, "Companhia das Letras");
        Livro livro3 = new Livro("Dom Casmurro", "Machado de Assis", "978-8535909555", "Romance", 1899, "Nova Fronteira");
        Livro livro4 = new Livro("O Pequeno Príncipe", "Antoine de Saint-Exupéry", "978-8546501234", "Infantil", 1943, "Geração Editorial");
        Livro livro5 = new Livro("Clean Code", "Robert C. Martin", "978-8576082675", "Tecnologia", 2008, "Alta Books");
        
        // Adicionando livros ao sistema
        biblioteca.adicionarLivro(livro1);
        biblioteca.adicionarLivro(livro2);
        biblioteca.adicionarLivro(livro3);
        biblioteca.adicionarLivro(livro4);
        biblioteca.adicionarLivro(livro5);
        
        System.out.println("✓ 5 livros cadastrados com sucesso!");
    }
    
    /**
     * Cadastra usuários de exemplo no sistema
     * @param biblioteca Gerenciador da biblioteca
     */
    private static void cadastrarUsuarios(BibliotecaManager biblioteca) {
        // Criando usuários com construtores sobrecarregados
        Usuario usuario1 = new Usuario("João Silva", "001", "joao@email.com", "(11) 99999-1111");
        Usuario usuario2 = new Usuario("Maria Santos", "002", "maria@email.com", "(11) 99999-2222");
        Usuario usuario3 = new Usuario("Pedro Oliveira", "003", "pedro@email.com", "(11) 99999-3333", 5); // Limite personalizado
        
        // Adicionando usuários ao sistema
        biblioteca.adicionarUsuario(usuario1);
        biblioteca.adicionarUsuario(usuario2);
        biblioteca.adicionarUsuario(usuario3);
        
        System.out.println("✓ 3 usuários cadastrados com sucesso!");
    }
    
    /**
     * Realiza empréstimos de exemplo
     * @param biblioteca Gerenciador da biblioteca
     */
    private static void realizarEmprestimos(BibliotecaManager biblioteca) {
        List<Usuario> usuarios = biblioteca.getUsuarios();
        List<Livro> livros = biblioteca.getLivros();
        
        if (usuarios.size() >= 2 && livros.size() >= 3) {
            Usuario joao = usuarios.get(0);
            Usuario maria = usuarios.get(1);
            
            Livro senhorDosAneis = livros.get(0);
            Livro livro1984 = livros.get(1);
            Livro domCasmurro = livros.get(2);
            
            // Realizando empréstimos
            boolean emprestimo1 = biblioteca.realizarEmprestimo(joao, senhorDosAneis);
            boolean emprestimo2 = biblioteca.realizarEmprestimo(maria, livro1984);
            boolean emprestimo3 = biblioteca.realizarEmprestimo(joao, domCasmurro);
            
            System.out.println("✓ Empréstimo 1 (João - O Senhor dos Anéis): " + (emprestimo1 ? "SUCESSO" : "FALHA"));
            System.out.println("✓ Empréstimo 2 (Maria - 1984): " + (emprestimo2 ? "SUCESSO" : "FALHA"));
            System.out.println("✓ Empréstimo 3 (João - Dom Casmurro): " + (emprestimo3 ? "SUCESSO" : "FALHA"));
        }
    }
    
    /**
     * Consulta dados do sistema
     * @param biblioteca Gerenciador da biblioteca
     */
    private static void consultarDados(BibliotecaManager biblioteca) {
        System.out.println("--- CONSULTAS ---");
        
        // Busca por título
        List<Livro> livrosEncontrados = biblioteca.buscarLivroPorTitulo("Senhor");
        System.out.println("Livros encontrados com 'Senhor': " + livrosEncontrados.size());
        
        // Busca por autor
        List<Livro> livrosOrwell = biblioteca.buscarLivroPorAutor("Orwell");
        System.out.println("Livros de Orwell: " + livrosOrwell.size());
        
        // Busca por usuário
        List<Usuario> usuariosJoao = biblioteca.buscarUsuarioPorNome("João");
        System.out.println("Usuários com 'João': " + usuariosJoao.size());
        
        // Livros disponíveis
        List<Livro> livrosDisponiveis = biblioteca.getLivrosDisponiveis();
        System.out.println("Livros disponíveis: " + livrosDisponiveis.size());
        
        // Empréstimos ativos
        List<Emprestimo> emprestimosAtivos = biblioteca.getEmprestimosAtivos();
        System.out.println("Empréstimos ativos: " + emprestimosAtivos.size());
    }
    
    /**
     * Realiza devoluções de exemplo
     * @param biblioteca Gerenciador da biblioteca
     */
    private static void realizarDevolucoes(BibliotecaManager biblioteca) {
        List<Usuario> usuarios = biblioteca.getUsuarios();
        List<Livro> livros = biblioteca.getLivros();
        
        if (usuarios.size() >= 1 && livros.size() >= 1) {
            Usuario joao = usuarios.get(0);
            Livro senhorDosAneis = livros.get(0);
            
            // Simulando devolução com atraso
            boolean devolucao = biblioteca.realizarDevolucao(joao, senhorDosAneis);
            System.out.println("✓ Devolução (João - O Senhor dos Anéis): " + (devolucao ? "SUCESSO" : "FALHA"));
        }
    }
    
    /**
     * Gera relatórios do sistema
     * @param biblioteca Gerenciador da biblioteca
     */
    private static void gerarRelatorios(BibliotecaManager biblioteca) {
        System.out.println("--- RELATÓRIOS ---");
        
        // Relatório geral
        String relatorioGeral = biblioteca.gerarRelatorioGeral();
        System.out.println(relatorioGeral);
        
        // Relatório de livros emprestados
        String relatorioEmprestados = biblioteca.gerarRelatorioLivrosEmprestados();
        System.out.println(relatorioEmprestados);
        
        // Relatório de multas
        String relatorioMultas = biblioteca.gerarRelatorioUsuariosComMultas();
        System.out.println(relatorioMultas);
    }
    
    /**
     * Testa funcionalidades especiais do sistema
     * @param biblioteca Gerenciador da biblioteca
     */
    private static void testarFuncionalidadesEspeciais(BibliotecaManager biblioteca) {
        System.out.println("--- TESTES ESPECIAIS ---");
        
        List<Usuario> usuarios = biblioteca.getUsuarios();
        List<Livro> livros = biblioteca.getLivros();
        
        if (usuarios.size() >= 1 && livros.size() >= 1) {
            Usuario usuario = usuarios.get(0);
            Livro livro = livros.get(0);
            
            // Teste de limite de empréstimos
            System.out.println("Limite de empréstimos do " + usuario.getNome() + ": " + usuario.getLimiteEmprestimos());
            System.out.println("Empréstimos disponíveis: " + usuario.getEmprestimosDisponiveis());
            
            // Teste de disponibilidade do livro
            System.out.println("Livro '" + livro.getTitulo() + "' está disponível: " + livro.estaDisponivel());
            
            // Teste de empréstimos em atraso
            List<Emprestimo> emprestimosAtrasados = biblioteca.getEmprestimosAtrasados();
            System.out.println("Empréstimos em atraso: " + emprestimosAtrasados.size());
        }
    }
    
    /**
     * Demonstra o uso de construtores e sobrecarga
     */
    private static void demonstrarConstrutores() {
        System.out.println("--- DEMONSTRAÇÃO DE CONSTRUTORES ---");
        
        // Construtor padrão
        Livro livro1 = new Livro();
        livro1.setTitulo("Livro Teste");
        livro1.setAutor("Autor Teste");
        
        // Construtor com parâmetros
        Livro livro2 = new Livro("Java Programming", "John Doe", "978-1234567890", "Tecnologia", 2023, "Tech Books");
        
        // Construtor de usuário padrão
        Usuario usuario1 = new Usuario();
        usuario1.setNome("Usuário Teste");
        usuario1.setId("999");
        
        // Construtor de usuário com parâmetros
        Usuario usuario2 = new Usuario("Ana Silva", "123", "ana@email.com", "(11) 99999-4444");
        
        // Construtor de usuário com limite personalizado
        Usuario usuario3 = new Usuario("Carlos Santos", "456", "carlos@email.com", "(11) 99999-5555", 10);
        
        System.out.println("✓ Construtores demonstrados com sucesso!");
    }
    
    /**
     * Demonstra o encapsulamento e acesso controlado
     */
    private static void demonstrarEncapsulamento() {
        System.out.println("--- DEMONSTRAÇÃO DE ENCAPSULAMENTO ---");
        
        Livro livro = new Livro("Livro Encapsulado", "Autor Teste", "978-0000000000", "Teste", 2023, "Editora Teste");
        
        // Acesso controlado via getters/setters
        System.out.println("Título original: " + livro.getTitulo());
        livro.setTitulo("Título Modificado");
        System.out.println("Título modificado: " + livro.getTitulo());
        
        // Verificação de disponibilidade
        System.out.println("Disponível antes do empréstimo: " + livro.estaDisponivel());
        livro.emprestar();
        System.out.println("Disponível após empréstimo: " + livro.estaDisponivel());
        livro.devolver();
        System.out.println("Disponível após devolução: " + livro.estaDisponivel());
        
        System.out.println("✓ Encapsulamento demonstrado com sucesso!");
    }
} 