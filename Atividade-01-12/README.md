# Atividade 01-12 — OO, JavaFX e JDBC

A aplicação contém somente as classes dos quatro grupos das imagens 1 a 4: loja de roupas, ciclismo e trilha, máscaras e exposição, e salão de cabeleireiro. A interface está em FXML e pode ser aberta no Scene Builder. Cada classe tem um painel de cadastro com operações de inserir, listar, atualizar e excluir.

## Requisitos

- JDK 21 ou superior
- Maven 3.9 ou superior
- MySQL 8.0 ou superior em `localhost:3306`

## Configuração do banco

Ao iniciar, a aplicação conecta por JDBC, cria o banco configurado se ainda não existir e executa o esquema de 12 tabelas de `sql/schema.sql`.

Por padrão, usa banco `atividade_01_12`, usuário `root` e senha vazia. Se o MySQL local usa outra configuração, defina as variáveis antes de iniciar:

```sh
export DB_HOST=localhost
export DB_PORT=3306
export DB_NAME=atividade_01_12
export DB_USER=root
export DB_PASSWORD='sua-senha'
```

O usuário do MySQL precisa poder criar o banco e as tabelas. Nenhuma senha foi gravada no código.

## Executar

```sh
mvn javafx:run
```

No IntelliJ IDEA, carregue o `pom.xml` como projeto Maven. Depois execute `Plugins > javafx > javafx:run` na janela Maven, ou use `mvn javafx:run` no terminal. Não execute `MainApp.java` como uma aplicação Java comum: esse caminho usa `exec:exec` e não coloca o runtime JavaFX no module path.

A conexão é indicada no cabeçalho. Se o servidor estiver desligado, a tela ainda abre e informa a falha nos painéis.
