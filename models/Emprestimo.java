package models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Classe que representa um empréstimo no sistema de controle de biblioteca
 * Implementa encapsulamento e gerencia datas e multas
 */
public class Emprestimo {
    // Atributos privados para encapsulamento
    private Usuario usuario;
    private Livro livro;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private LocalDate dataDevolucaoPrevista;
    private String status; // "ATIVO", "DEVOLVIDO", "ATRASADO"
    private double multa;
    private String observacoes;
    
    // Constantes para status e configurações
    private static final String STATUS_ATIVO = "ATIVO";
    private static final String STATUS_DEVOLVIDO = "DEVOLVIDO";
    private static final String STATUS_ATRASADO = "ATRASADO";
    private static final int PRAZO_PADRAO_DIAS = 15;
    private static final double VALOR_MULTA_POR_DIA = 0.50;
    
    /**
     * Construtor padrão
     */
    public Emprestimo() {
        this.status = STATUS_ATIVO;
        this.multa = 0.0;
    }
    
    /**
     * Construtor com sobrecarga para inicialização completa
     * @param usuario Usuário que fez o empréstimo
     * @param livro Livro emprestado
     * @param dataEmprestimo Data do empréstimo
     */
    public Emprestimo(Usuario usuario, Livro livro, LocalDate dataEmprestimo) {
        this.usuario = usuario;
        this.livro = livro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoPrevista = dataEmprestimo.plusDays(PRAZO_PADRAO_DIAS);
        this.status = STATUS_ATIVO;
        this.multa = 0.0;
    }
    
    /**
     * Construtor com prazo personalizado
     * @param usuario Usuário que fez o empréstimo
     * @param livro Livro emprestado
     * @param dataEmprestimo Data do empréstimo
     * @param prazoDias Prazo em dias para devolução
     */
    public Emprestimo(Usuario usuario, Livro livro, LocalDate dataEmprestimo, int prazoDias) {
        this.usuario = usuario;
        this.livro = livro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoPrevista = dataEmprestimo.plusDays(prazoDias);
        this.status = STATUS_ATIVO;
        this.multa = 0.0;
    }
    
    // Getters e Setters para acesso controlado aos atributos
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public Livro getLivro() {
        return livro;
    }
    
    public void setLivro(Livro livro) {
        this.livro = livro;
    }
    
    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }
    
    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }
    
    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }
    
    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }
    
    public LocalDate getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }
    
    public void setDataDevolucaoPrevista(LocalDate dataDevolucaoPrevista) {
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public double getMulta() {
        return multa;
    }
    
    public void setMulta(double multa) {
        this.multa = multa;
    }
    
    public String getObservacoes() {
        return observacoes;
    }
    
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
    
    /**
     * Calcula a multa baseada na data de devolução
     * @return Valor da multa calculada
     */
    public double calcularMulta() {
        if (status.equals(STATUS_DEVOLVIDO) && dataDevolucao != null) {
            if (dataDevolucao.isAfter(dataDevolucaoPrevista)) {
                long diasAtraso = ChronoUnit.DAYS.between(dataDevolucaoPrevista, dataDevolucao);
                this.multa = diasAtraso * VALOR_MULTA_POR_DIA;
                return this.multa;
            }
        } else if (status.equals(STATUS_ATIVO) || status.equals(STATUS_ATRASADO)) {
            LocalDate hoje = LocalDate.now();
            if (hoje.isAfter(dataDevolucaoPrevista)) {
                long diasAtraso = ChronoUnit.DAYS.between(dataDevolucaoPrevista, hoje);
                this.multa = diasAtraso * VALOR_MULTA_POR_DIA;
                return this.multa;
            }
        }
        return 0.0;
    }
    
    /**
     * Verifica se o empréstimo está em atraso
     * @return true se está em atraso, false caso contrário
     */
    public boolean verificarAtraso() {
        if (status.equals(STATUS_ATIVO)) {
            LocalDate hoje = LocalDate.now();
            if (hoje.isAfter(dataDevolucaoPrevista)) {
                this.status = STATUS_ATRASADO;
                return true;
            }
        }
        return status.equals(STATUS_ATRASADO);
    }
    
    /**
     * Realiza a devolução do livro
     * @param dataDevolucao Data da devolução
     * @return true se a devolução foi realizada com sucesso
     */
    public boolean realizarDevolucao(LocalDate dataDevolucao) {
        if (status.equals(STATUS_ATIVO) || status.equals(STATUS_ATRASADO)) {
            this.dataDevolucao = dataDevolucao;
            this.status = STATUS_DEVOLVIDO;
            calcularMulta(); // Calcula multa se houver atraso
            return true;
        }
        return false;
    }
    
    /**
     * Obtém o número de dias de atraso
     * @return Número de dias em atraso, 0 se não há atraso
     */
    public long getDiasAtraso() {
        if (status.equals(STATUS_DEVOLVIDO) && dataDevolucao != null) {
            if (dataDevolucao.isAfter(dataDevolucaoPrevista)) {
                return ChronoUnit.DAYS.between(dataDevolucaoPrevista, dataDevolucao);
            }
        } else if (status.equals(STATUS_ATIVO) || status.equals(STATUS_ATRASADO)) {
            LocalDate hoje = LocalDate.now();
            if (hoje.isAfter(dataDevolucaoPrevista)) {
                return ChronoUnit.DAYS.between(dataDevolucaoPrevista, hoje);
            }
        }
        return 0;
    }
    
    /**
     * Obtém o número de dias restantes para devolução
     * @return Número de dias restantes, negativo se está em atraso
     */
    public long getDiasRestantes() {
        if (status.equals(STATUS_ATIVO)) {
            LocalDate hoje = LocalDate.now();
            return ChronoUnit.DAYS.between(hoje, dataDevolucaoPrevista);
        }
        return 0;
    }
    
    /**
     * Verifica se o empréstimo está ativo
     * @return true se está ativo, false caso contrário
     */
    public boolean estaAtivo() {
        return status.equals(STATUS_ATIVO);
    }
    
    /**
     * Verifica se o empréstimo foi devolvido
     * @return true se foi devolvido, false caso contrário
     */
    public boolean foiDevolvido() {
        return status.equals(STATUS_DEVOLVIDO);
    }
    
    /**
     * Retorna uma representação em string do empréstimo
     * @return String formatada com informações do empréstimo
     */
    @Override
    public String toString() {
        String infoDevolucao = dataDevolucao != null ? 
            String.format(" | Devolvido em: %s", dataDevolucao) : "";
        
        return String.format("Usuário: %s | Livro: %s | Emprestado em: %s | Devolução prevista: %s | Status: %s | Multa: R$ %.2f%s",
                usuario.getNome(), livro.getTitulo(), dataEmprestimo, dataDevolucaoPrevista, status, multa, infoDevolucao);
    }
    
    /**
     * Compara dois empréstimos pelo usuário e livro
     * @param obj Objeto a ser comparado
     * @return true se os empréstimos são do mesmo usuário e livro
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Emprestimo emprestimo = (Emprestimo) obj;
        return usuario.equals(emprestimo.usuario) && livro.equals(emprestimo.livro);
    }
    
    @Override
    public int hashCode() {
        return usuario.hashCode() * 31 + livro.hashCode();
    }
} 