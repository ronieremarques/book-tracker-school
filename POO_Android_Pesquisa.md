# 📱 POO no Android - Pesquisa e Análise

## 🏛️ 1. Padrões de Arquitetura

### MVC (Model-View-Controller)
**Descrição:** Padrão tradicional que separa a aplicação em três camadas principais.

**Componentes:**
- **Model:** Representa os dados e a lógica de negócio
- **View:** Interface do usuário
- **Controller:** Controla o fluxo entre Model e View

**Exemplo no Android:**
```java
// Model
public class Livro {
    private String titulo;
    private String autor;
    // getters e setters
}

// View (Activity)
public class MainActivity extends AppCompatActivity {
    private TextView txtTitulo;
    private Button btnEmprestar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Configuração da interface
    }
}

// Controller
public class BibliotecaController {
    private BibliotecaManager biblioteca;
    
    public boolean realizarEmprestimo(Usuario usuario, Livro livro) {
        // Lógica de negócio
        return biblioteca.realizarEmprestimo(usuario, livro);
    }
}
```

**Quando usar:** Projetos simples, protótipos rápidos, equipes pequenas.

### MVP (Model-View-Presenter)
**Descrição:** Evolução do MVC com foco na testabilidade e separação de responsabilidades.

**Componentes:**
- **Model:** Dados e lógica de negócio
- **View:** Interface passiva (Activity/Fragment)
- **Presenter:** Lógica de apresentação e comunicação

**Exemplo no Android:**
```java
// Contract (Interface)
public interface BibliotecaContract {
    interface View {
        void mostrarLivros(List<Livro> livros);
        void mostrarErro(String mensagem);
    }
    
    interface Presenter {
        void carregarLivros();
        void emprestarLivro(Livro livro, Usuario usuario);
    }
}

// Presenter
public class BibliotecaPresenter implements BibliotecaContract.Presenter {
    private BibliotecaContract.View view;
    private BibliotecaManager biblioteca;
    
    public BibliotecaPresenter(BibliotecaContract.View view) {
        this.view = view;
        this.biblioteca = BibliotecaManager.getInstancia();
    }
    
    @Override
    public void carregarLivros() {
        List<Livro> livros = biblioteca.getLivros();
        view.mostrarLivros(livros);
    }
}

// Activity (View)
public class MainActivity extends AppCompatActivity implements BibliotecaContract.View {
    private BibliotecaPresenter presenter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new BibliotecaPresenter(this);
    }
    
    @Override
    public void mostrarLivros(List<Livro> livros) {
        // Atualizar RecyclerView
    }
}
```

**Quando usar:** Projetos médios, necessidade de testes unitários, equipes com experiência.

### MVVM (Model-View-ViewModel)
**Descrição:** Padrão moderno com DataBinding e LiveData, recomendado pelo Google.

**Componentes:**
- **Model:** Dados e lógica de negócio
- **View:** Interface do usuário
- **ViewModel:** Estado da UI e comunicação com Model

**Exemplo no Android:**
```java
// ViewModel
public class BibliotecaViewModel extends ViewModel {
    private MutableLiveData<List<Livro>> livros = new MutableLiveData<>();
    private BibliotecaManager biblioteca = BibliotecaManager.getInstancia();
    
    public LiveData<List<Livro>> getLivros() {
        return livros;
    }
    
    public void carregarLivros() {
        List<Livro> lista = biblioteca.getLivros();
        livros.setValue(lista);
    }
    
    public void emprestarLivro(Livro livro, Usuario usuario) {
        boolean sucesso = biblioteca.realizarEmprestimo(usuario, livro);
        if (sucesso) {
            carregarLivros(); // Atualiza a lista
        }
    }
}

// Activity
public class MainActivity extends AppCompatActivity {
    private BibliotecaViewModel viewModel;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        viewModel = new ViewModelProvider(this).get(BibliotecaViewModel.class);
        
        // Observar mudanças nos dados
        viewModel.getLivros().observe(this, livros -> {
            // Atualizar RecyclerView
        });
    }
}
```

**Quando usar:** Projetos complexos, uso de DataBinding, arquitetura moderna.

## 🎯 2. Boas Práticas de POO

### Princípios SOLID no Android

#### S - Single Responsibility Principle
```java
// ❌ Ruim - Múltiplas responsabilidades
public class MainActivity extends AppCompatActivity {
    public void salvarLivro() { /* lógica de persistência */ }
    public void validarDados() { /* lógica de validação */ }
    public void enviarEmail() { /* lógica de email */ }
}

// ✅ Bom - Responsabilidade única
public class LivroRepository {
    public void salvarLivro(Livro livro) { /* apenas persistência */ }
}

public class LivroValidator {
    public boolean validarLivro(Livro livro) { /* apenas validação */ }
}

public class EmailService {
    public void enviarEmail(String destinatario, String assunto) { /* apenas email */ }
}
```

#### O - Open/Closed Principle
```java
// ✅ Extensível sem modificação
public interface FormaPagamento {
    double calcularMulta(int diasAtraso);
}

public class MultaPadrao implements FormaPagamento {
    @Override
    public double calcularMulta(int diasAtraso) {
        return diasAtraso * 0.50;
    }
}

public class MultaEspecial implements FormaPagamento {
    @Override
    public double calcularMulta(int diasAtraso) {
        return diasAtraso * 1.00;
    }
}
```

#### L - Liskov Substitution Principle
```java
// ✅ Classes derivadas podem substituir a base
public abstract class Usuario {
    public abstract boolean podeEmprestar();
}

public class UsuarioPadrao extends Usuario {
    @Override
    public boolean podeEmprestar() {
        return livrosEmprestados.size() < 3;
    }
}

public class UsuarioVIP extends Usuario {
    @Override
    public boolean podeEmprestar() {
        return livrosEmprestados.size() < 10;
    }
}
```

#### I - Interface Segregation Principle
```java
// ✅ Interfaces pequenas e específicas
public interface Emprestavel {
    boolean estaDisponivel();
    void emprestar();
    void devolver();
}

public interface Pesquisavel {
    List<Livro> buscarPorTitulo(String titulo);
    List<Livro> buscarPorAutor(String autor);
}
```

#### D - Dependency Inversion Principle
```java
// ✅ Depender de abstrações, não implementações
public interface BibliotecaRepository {
    void salvarLivro(Livro livro);
    List<Livro> buscarLivros();
}

public class BibliotecaManager {
    private BibliotecaRepository repository;
    
    public BibliotecaManager(BibliotecaRepository repository) {
        this.repository = repository;
    }
}
```

### Organização de Pacotes por Funcionalidade
```
com.biblioteca/
├── data/
│   ├── repository/
│   ├── local/
│   └── remote/
├── domain/
│   ├── model/
│   ├── usecase/
│   └── repository/
├── presentation/
│   ├── main/
│   ├── emprestimo/
│   └── relatorio/
└── di/
    └── modules/
```

### Convenções de Nomes
```java
// ✅ PascalCase para classes
public class BibliotecaManager { }
public class LivroRepository { }

// ✅ camelCase para variáveis e métodos
private String nomeUsuario;
public void realizarEmprestimo() { }

// ✅ UPPER_CASE para constantes
private static final int LIMITE_EMPRESTIMOS = 3;
private static final String STATUS_ATIVO = "ATIVO";
```

## 🧹 3. Gerenciamento de Memória

### Criação e Destruição de Objetos
```java
// ✅ Boas práticas
public class MainActivity extends AppCompatActivity {
    private BibliotecaManager biblioteca;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Objetos criados quando necessário
        biblioteca = BibliotecaManager.getInstancia();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Limpeza de recursos
        biblioteca = null;
    }
}
```

### Garbage Collection Automático
```java
// ✅ Evitar referências desnecessárias
public class BibliotecaManager {
    // ❌ Ruim - referência estática forte
    private static List<Livro> livros = new ArrayList<>();
    
    // ✅ Bom - referência fraca quando apropriado
    private WeakReference<Context> contextRef;
}
```

### Memory Leaks Comuns
```java
// ❌ Memory Leak - Context mal referenciado
public class SingletonManager {
    private static Context context; // Pode causar memory leak
    
    public static void init(Context context) {
        SingletonManager.context = context; // Context da Activity
    }
}

// ✅ Solução - Usar Application Context
public class SingletonManager {
    private static Context context;
    
    public static void init(Context context) {
        SingletonManager.context = context.getApplicationContext();
    }
}

// ✅ Solução - WeakReference
public class SingletonManager {
    private WeakReference<Context> contextRef;
    
    public void setContext(Context context) {
        this.contextRef = new WeakReference<>(context);
    }
}
```

### Boas Práticas de Memória
```java
// ✅ Usar WeakReference para listeners
public class MainActivity extends AppCompatActivity {
    private WeakReference<OnLivroClickListener> listenerRef;
    
    public void setOnLivroClickListener(OnLivroClickListener listener) {
        this.listenerRef = new WeakReference<>(listener);
    }
}

// ✅ Evitar static desnecessário
public class Utils {
    // ❌ Ruim - static desnecessário
    public static String formatarTitulo(String titulo) {
        return titulo.toUpperCase();
    }
    
    // ✅ Bom - método de instância quando apropriado
    public String formatarTitulo(String titulo) {
        return titulo.toUpperCase();
    }
}

// ✅ Liberar recursos
public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Adapter adapter;
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (recyclerView != null) {
            recyclerView.setAdapter(null);
        }
        adapter = null;
    }
}
```

## 💾 4. Persistência de Objetos

### Serialização vs Parcelable
```java
// ✅ Parcelable (Recomendado para Android)
public class Livro implements Parcelable {
    private String titulo;
    private String autor;
    
    protected Livro(Parcel in) {
        titulo = in.readString();
        autor = in.readString();
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(titulo);
        dest.writeString(autor);
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    public static final Creator<Livro> CREATOR = new Creator<Livro>() {
        @Override
        public Livro createFromParcel(Parcel in) {
            return new Livro(in);
        }
        
        @Override
        public Livro[] newArray(int size) {
            return new Livro[size];
        }
    };
}

// ❌ Serialização (Mais lento)
public class Livro implements Serializable {
    private String titulo;
    private String autor;
    // ...
}
```

### SharedPreferences
```java
// ✅ Salvar preferências simples
public class PreferenciasManager {
    private static final String PREF_NAME = "biblioteca_prefs";
    private static final String KEY_ULTIMO_USUARIO = "ultimo_usuario";
    
    public void salvarUltimoUsuario(String usuarioId) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_ULTIMO_USUARIO, usuarioId).apply();
    }
    
    public String obterUltimoUsuario() {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_ULTIMO_USUARIO, "");
    }
}
```

### Banco de Dados (Room)
```java
// ✅ Entity para Room
@Entity(tableName = "livros")
public class LivroEntity {
    @PrimaryKey
    public String isbn;
    
    @ColumnInfo(name = "titulo")
    public String titulo;
    
    @ColumnInfo(name = "autor")
    public String autor;
    
    @ColumnInfo(name = "disponivel")
    public boolean disponivel;
}

// ✅ DAO (Data Access Object)
@Dao
public interface LivroDao {
    @Query("SELECT * FROM livros")
    List<LivroEntity> getAll();
    
    @Query("SELECT * FROM livros WHERE titulo LIKE :titulo")
    List<LivroEntity> buscarPorTitulo(String titulo);
    
    @Insert
    void insert(LivroEntity livro);
    
    @Update
    void update(LivroEntity livro);
    
    @Delete
    void delete(LivroEntity livro);
}

// ✅ Repository
public class LivroRepository {
    private LivroDao livroDao;
    
    public LivroRepository(LivroDao livroDao) {
        this.livroDao = livroDao;
    }
    
    public List<LivroEntity> getAllLivros() {
        return livroDao.getAll();
    }
    
    public void salvarLivro(LivroEntity livro) {
        livroDao.insert(livro);
    }
}
```

### Preparar Objetos para Persistência
```java
// ✅ Mapper para converter entre Model e Entity
public class LivroMapper {
    public static LivroEntity toEntity(Livro livro) {
        LivroEntity entity = new LivroEntity();
        entity.isbn = livro.getIsbn();
        entity.titulo = livro.getTitulo();
        entity.autor = livro.getAutor();
        entity.disponivel = livro.isDisponivel();
        return entity;
    }
    
    public static Livro toModel(LivroEntity entity) {
        return new Livro(
            entity.titulo,
            entity.autor,
            entity.isbn,
            "Gênero", // Valor padrão
            entity.disponivel
        );
    }
}
```

## 📊 Comparação dos Padrões

| Aspecto | MVC | MVP | MVVM |
|---------|-----|-----|------|
| **Complexidade** | Baixa | Média | Alta |
| **Testabilidade** | Baixa | Alta | Alta |
| **DataBinding** | Não | Não | Sim |
| **Manutenibilidade** | Média | Alta | Alta |
| **Curva de Aprendizado** | Baixa | Média | Alta |

## 🎯 Recomendações

### Para Projetos Pequenos
- **Padrão:** MVC
- **Justificativa:** Simplicidade e rapidez de desenvolvimento

### Para Projetos Médios
- **Padrão:** MVP
- **Justificativa:** Boa testabilidade e separação de responsabilidades

### Para Projetos Grandes
- **Padrão:** MVVM
- **Justificativa:** Integração com DataBinding e arquitetura moderna

## 📝 Conclusão

A aplicação correta dos conceitos de POO no Android é fundamental para o desenvolvimento de aplicações robustas, testáveis e manuteníveis. A escolha do padrão arquitetural deve considerar a complexidade do projeto, a experiência da equipe e os requisitos de testabilidade.

O gerenciamento adequado de memória e a persistência eficiente de dados são aspectos críticos que impactam diretamente a performance e a experiência do usuário.

---

**Pesquisa realizada para demonstrar conhecimento em POO aplicado ao desenvolvimento Android** 