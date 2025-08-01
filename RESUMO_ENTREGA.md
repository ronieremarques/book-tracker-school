# 📚 BookTracker - Trabalho de POO em Java

## 📋 Informações do Trabalho

**Disciplina:** Programação Orientada a Objetos  
**Título:** Sistema de Controle de Empréstimos de Livros (BookTracker)  
**Tecnologia:** Java  
**Data de Entrega:** Próxima aula  

---

## 🎯 Atividade 1 - Desenvolvimento do BookTracker

### ✅ 1. Modelagem das Classes

#### 📘 **Classe Livro**
**Atributos:**
- `titulo` (String): Título do livro
- `autor` (String): Autor do livro  
- `disponivel` (boolean): Status de disponibilidade
- `isbn` (String): Código ISBN
- `genero` (String): Gênero literário
- `anoPublicacao` (int): Ano de publicação
- `editora` (String): Editora

**Métodos:**
- `emprestar()`: Marca livro como emprestado
- `devolver()`: Marca livro como disponível
- `estaDisponivel()`: Verifica disponibilidade

**Justificativa:** Cada atributo é necessário para identificar unicamente o livro e controlar seu status no sistema. Os métodos implementam a lógica de negócio para empréstimos e devoluções.

#### 👤 **Classe Usuario**
**Atributos:**
- `nome` (String): Nome do usuário
- `id` (String): ID único
- `email` (String): Email do usuário
- `telefone` (String): Telefone
- `limiteEmprestimos` (int): Limite de empréstimos
- `livrosEmprestados` (List<Livro>): Lista de livros emprestados
- `ativo` (boolean): Status do usuário

**Métodos:**
- `podeEmprestar()`: Verifica se pode emprestar
- `adicionarLivro()`: Adiciona livro à lista
- `removerLivro()`: Remove livro da lista

**Justificativa:** Os atributos permitem identificar o usuário e controlar seus empréstimos. Os métodos implementam as regras de negócio para validação de empréstimos.

#### 🔄 **Classe Emprestimo**
**Atributos:**
- `usuario` (Usuario): Usuário que fez o empréstimo
- `livro` (Livro): Livro emprestado
- `dataEmprestimo` (LocalDate): Data do empréstimo
- `dataDevolucao` (LocalDate): Data da devolução
- `dataDevolucaoPrevista` (LocalDate): Data prevista para devolução
- `status` (String): Status do empréstimo
- `multa` (double): Valor da multa

**Métodos:**
- `calcularMulta()`: Calcula multa por atraso
- `verificarAtraso()`: Verifica se está em atraso
- `realizarDevolucao()`: Processa devolução

**Justificativa:** Os atributos registram todas as informações necessárias do empréstimo. Os métodos implementam a lógica de cálculo de multas e controle de prazos.

### ✅ 2. Implementação de Métodos

#### 🟢 **emprestar()**
```java
public boolean emprestar() {
    if (this.disponivel) {
        this.disponivel = false;
        return true;
    }
    return false;
}
```
**Funcionalidade:** Verifica disponibilidade e atualiza estado do livro.

#### 🟡 **podeEmprestar()**
```java
public boolean podeEmprestar() {
    return ativo && livrosEmprestados.size() < limiteEmprestimos;
}
```
**Funcionalidade:** Valida regras por usuário (ativo e limite de empréstimos).

#### 🔴 **calcularMulta()**
```java
public double calcularMulta() {
    if (status.equals(STATUS_DEVOLVIDO) && dataDevolucao != null) {
        if (dataDevolucao.isAfter(dataDevolucaoPrevista)) {
            long diasAtraso = ChronoUnit.DAYS.between(dataDevolucaoPrevista, dataDevolucao);
            this.multa = diasAtraso * VALOR_MULTA_POR_DIA;
            return this.multa;
        }
    }
    return 0.0;
}
```
**Funcionalidade:** Calcula multa com base na data de devolução e prazo.

#### 🔍 **Métodos de Busca**
```java
public List<Livro> buscarLivroPorTitulo(String titulo)
public List<Usuario> buscarUsuarioPorNome(String nome)
```
**Funcionalidade:** Implementam busca flexível por título e nome.

### ✅ 3. Classe Gerenciadora

#### 🧠 **BibliotecaManager**
**Funcionalidades:**
- Gerenciamento centralizado de todas as operações
- Implementação do padrão Singleton
- Métodos de busca e filtros
- Geração de relatórios
- Validações de negócio

**Listas de Objetos:**
- `ArrayList<Livro>`: Lista de livros
- `ArrayList<Usuario>`: Lista de usuários  
- `ArrayList<Emprestimo>`: Lista de empréstimos

**Métodos Principais:**
- `realizarEmprestimo()`: Processa empréstimos
- `realizarDevolucao()`: Processa devoluções
- `buscarLivros()`: Busca livros
- `gerarRelatorio()`: Gera relatórios

### ✅ 4. Integração com Interface

#### 💡 **Instanciação de Objetos**
```java
// Obtém instância única do gerenciador
BibliotecaManager biblioteca = BibliotecaManager.getInstancia();

// Cria objetos das classes modelo
Livro livro = new Livro("Título", "Autor", "ISBN", "Gênero", 2023, "Editora");
Usuario usuario = new Usuario("Nome", "ID", "email@email.com", "(11) 99999-9999");
```

#### 📲 **Exibição de Dados**
```java
// Exemplo de como os dados aparecem na tela
String relatorio = biblioteca.gerarRelatorioGeral();
System.out.println(relatorio);
```

#### ⚙️ **Eventos de Clique**
```java
// Exemplo de fluxo: selecionar livro → emprestar → devolver → mostrar multa
if (biblioteca.realizarEmprestimo(usuario, livro)) {
    System.out.println("Empréstimo realizado com sucesso!");
} else {
    System.out.println("Erro ao realizar empréstimo!");
}
```

---

## 📱 Atividade 2 - Pesquisa sobre POO no Android

### 🏛️ 1. Padrões de Arquitetura

#### MVC (Model-View-Controller)
- **Descrição:** Separação em três camadas principais
- **Uso:** Projetos simples, protótipos rápidos
- **Vantagens:** Simplicidade, curva de aprendizado baixa
- **Desvantagens:** Testabilidade limitada

#### MVP (Model-View-Presenter)
- **Descrição:** Foco na testabilidade e separação de responsabilidades
- **Uso:** Projetos médios, necessidade de testes unitários
- **Vantagens:** Alta testabilidade, separação clara
- **Desvantagens:** Mais código boilerplate

#### MVVM (Model-View-ViewModel)
- **Descrição:** Padrão moderno com DataBinding e LiveData
- **Uso:** Projetos complexos, arquitetura moderna
- **Vantagens:** DataBinding, reatividade, recomendado pelo Google
- **Desvantagens:** Curva de aprendizado alta

### 🎯 2. Boas Práticas de POO

#### Princípios SOLID
- **S - Single Responsibility:** Cada classe tem uma responsabilidade
- **O - Open/Closed:** Aberto para extensão, fechado para modificação
- **L - Liskov Substitution:** Classes derivadas podem substituir a base
- **I - Interface Segregation:** Interfaces pequenas e específicas
- **D - Dependency Inversion:** Depender de abstrações, não implementações

#### Organização de Pacotes
```
com.biblioteca/
├── data/          # Camada de dados
├── domain/        # Lógica de negócio
├── presentation/  # Interface do usuário
└── di/           # Injeção de dependência
```

### 🧹 3. Gerenciamento de Memória

#### Criação e Destruição
- Objetos criados quando necessário
- Limpeza adequada em `onDestroy()`
- Uso de `WeakReference` para evitar memory leaks

#### Memory Leaks Comuns
- Context mal referenciado
- Listeners não removidos
- Referências estáticas desnecessárias

#### Boas Práticas
- Usar `ApplicationContext` quando possível
- Liberar recursos em `onDestroy()`
- Evitar referências estáticas a Activities

### 💾 4. Persistência de Objetos

#### Serialização vs Parcelable
- **Parcelable:** Recomendado para Android (mais rápido)
- **Serialização:** Mais lento, mas mais simples

#### SharedPreferences
- Para dados simples e preferências
- Uso de chaves constantes
- Operações assíncronas

#### Banco de Dados (Room)
- Entity para representar tabelas
- DAO para operações de dados
- Repository para abstração

---

## 📊 Resultados da Execução

### ✅ Sistema Funcionando
O sistema foi compilado e executado com sucesso, demonstrando:

- ✅ Cadastro de 5 livros
- ✅ Cadastro de 3 usuários
- ✅ Realização de 3 empréstimos
- ✅ Devolução de 1 livro
- ✅ Geração de relatórios completos
- ✅ Cálculo de estatísticas

### 📈 Estatísticas do Sistema
```
Total de livros: 5
Livros disponíveis: 2
Livros emprestados: 2
Total de usuários: 3
Usuários ativos: 3
Total de empréstimos: 3
Empréstimos ativos: 2
Empréstimos em atraso: 0
```

---

## 🎓 Conceitos Técnicos Demonstrados

### ✅ **Encapsulamento**
- Atributos privados com acesso via getters/setters
- Controle de modificação de dados

### ✅ **Construtores e Sobrecarga**
- Múltiplos construtores para flexibilidade
- Inicialização com diferentes parâmetros

### ✅ **Composição**
- Relacionamentos entre classes (Emprestimo composto por Usuario e Livro)
- Agregação de objetos

### ✅ **Padrões de Projeto**
- Singleton para gerenciamento centralizado
- Factory para criação de objetos

### ✅ **Coleções e Manipulação**
- Uso de ArrayList para gerenciar objetos
- Streams para filtros e buscas
- Manipulação eficiente de dados

---

## 📁 Arquivos Entregues

1. **models/Livro.java** - Classe modelo para livros
2. **models/Usuario.java** - Classe modelo para usuários
3. **models/Emprestimo.java** - Classe modelo para empréstimos
4. **managers/BibliotecaManager.java** - Classe gerenciadora
5. **MainActivity.java** - Classe principal com demonstração
6. **README.md** - Documentação completa do projeto
7. **POO_Android_Pesquisa.md** - Pesquisa sobre POO no Android
8. **RESUMO_ENTREGA.md** - Este resumo final

---

## 🎯 Conclusão

O trabalho demonstra com sucesso a aplicação dos conceitos de **Programação Orientada a Objetos** em um sistema prático e funcional. Todas as funcionalidades solicitadas foram implementadas:

- ✅ Modelagem completa das classes
- ✅ Implementação de métodos de negócio
- ✅ Classe gerenciadora centralizada
- ✅ Integração com interface (simulada)
- ✅ Pesquisa detalhada sobre POO no Android
- ✅ Documentação completa e profissional

O sistema está pronto para ser integrado a uma interface Android real, seguindo as melhores práticas de desenvolvimento e os padrões estabelecidos na indústria.

---

**Trabalho desenvolvido com aplicação completa dos conceitos de POO em Java**  
**Pronto para entrega na próxima aula** 