# Spring com Redis

1️⃣ Baixar a imagem do PostgreSQL 17
docker pull postgres:17
2️⃣ Criar e executar o container
docker run -d --name db_postgres -e POSTGRES_PASSWORD=senha123 -p 5432:5432 postgres:17

O que cada parte faz
-d → roda em background
--name db_postgres → nome do container
-e POSTGRES_PASSWORD → senha do usuário postgres
-p 5432:5432 → expõe a porta do banco
postgres:17 → imagem usada

3️⃣ Verificar se o container está rodando
docker ps
4️⃣ Conectar ao banco dentro do container
docker exec -it db_postgres psql -U postgres
5️⃣ Conectar de fora (ex: aplicação)

Configuração típica:

host: localhost
port: 5432
database: postgres
user: postgres
password: senha123
6️⃣ Criar container com volume (dados persistentes)

Isso evita perder os dados quando o container for removido.

docker run -d --name db_postgres -e POSTGRES_PASSWORD=senha123 -p 5432:5432 -v pgdata:/var/lib/postgresql/data postgres:17
7️⃣ Parar e remover container
docker stop db_postgres
docker rm db_postgres

💡 Dica profissional: se você for usar em projetos, o ideal é usar Docker Compose.

Exemplo docker-compose.yml:
```bash
version: "3.9"

services:
postgres:
image: postgres:17
container_name: db_postgres
restart: always
environment:
POSTGRES_PASSWORD: senha123
ports:
- "5432:5432"
volumes:
- pgdata:/var/lib/postgresql/data

volumes:
pgdata:
```
Rodar:

```bash
docker compose up -d
```

### Principais comandos
Ver ajuda
\?
Listar bancos
\l
Conectar em um banco
\c nome_do_banco
Listar tabelas
\dt
Ver estrutura de uma tabela
\d nome_tabela
Listar usuários
\du
Ver schemas
\dn
Limpar tela
\! clear
Sair do psql
\q
2️⃣ Comandos SQL principais
Criar banco
CREATE DATABASE empresa;
Conectar no banco
\c empresa
Criar tabela
CREATE TABLE usuarios (
id SERIAL PRIMARY KEY,
nome VARCHAR(100),
email VARCHAR(100)
);
Inserir dados
INSERT INTO usuarios (nome, email)
VALUES ('João', 'joao@email.com');
Consultar dados
SELECT * FROM usuarios;
Atualizar dados
UPDATE usuarios
SET nome = 'João Silva'
WHERE id = 1;
Deletar dados
DELETE FROM usuarios
WHERE id = 1;
3️⃣ Comandos úteis no psql
Mostrar banco atual
SELECT current_database();
Mostrar usuário atual
SELECT current_user;
Ver versão do PostgreSQL
SELECT version();
4️⃣ Criar usuário
CREATE USER dev WITH PASSWORD '123456';

Dar permissão:
ALTER USER dev WITH SUPERUSER;
5️⃣ Dar acesso a banco
GRANT ALL PRIVILEGES ON DATABASE empresa TO dev;


## Cache
### 🧩 1. O que é Cache (conceito real)

Cache é basicamente:
> Guardar o resultado de uma operação para não precisar recalcular depois.

Exemplo:
```
buscarProduto(1) → vai no banco
buscarProduto(1) → pega do cache (mais rápido)
```

### 🧩 2. Como isso funciona internamente

O Spring faz algo conceitualmente parecido com isso:

```bash
Map<String, Object> cache = new ConcurrentHashMap<>();

if (cache.containsKey("produto_1")) {
    return cache.get("produto_1");
} else {
    Object resultado = buscarDoBanco();
    cache.put("produto_1", resultado);
    return resultado;
}
```

👉 Ou seja:

- chave = identificação do método/parâmetro
- valor = resultado da execução

### 🧩 3. Por que usar ConcurrentHashMap?

Aqui está o ponto mais importante da explicação do vídeo.

❌ HashMap
- não é thread-safe
- pode dar erro com múltiplas threads
✔️ ConcurrentHashMap
- é thread-safe
- permite múltiplas threads acessando ao mesmo tempo
- evita problemas como:
- dados inconsistentes
- travamentos
- corrupção de memória

### 🧩 4. O contexto do Spring (multithread)

Quando você usa Spring Web (com Tomcat):

👉 Cada requisição HTTP roda em uma thread diferente

Exemplo:

- usuário A → thread 1
- usuário B → thread 2
- usuário C → thread 3

Se várias threads acessam o cache ao mesmo tempo:
```bash
cache.put(...)
cache.get(...)
```

👉 precisa ser seguro → por isso ConcurrentHashMap

### 🧩 5. O que o Spring Cache realmente faz

Quando você usa:
```bash
@Cacheable("produtos")
public Produto buscar(Long id) {
return repository.findById(id);
}
```
O Spring faz algo como:

```bash
if (cache.containsKey(id)) {
return cache.get(id);
}

Produto produto = buscarDoBanco();
cache.put(id, produto);

return produto;
```

👉 Tudo automático — você não escreve isso.

⚠️ Correção importante da explicação do vídeo

O vídeo diz:

> "Spring Cache usa HashMap"

👉 ❌ Isso não é totalmente correto.

O Spring pode usar vários provedores de cache:

- ConcurrentHashMap (simples, padrão)
- Caffeine (mais avançado)
- Redis (distribuído)
- EhCache

Ou seja:

👉 ConcurrentHashMap é só a implementação mais simples (local, em memória).

🧪 Melhor versão do seu código (mais clara)


```bash
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Main {

    static ConcurrentHashMap<String, List<String>> cache = new ConcurrentHashMap<>();

    static {
        cache.put("estados", Arrays.asList("SP", "MG", "RJ"));
        cache.put("mg", Arrays.asList("Juiz de Fora", "Belo Horizonte", "Contagem"));
    }

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        System.out.print("Digite a chave: ");
        String chave = teclado.next();

        System.out.println(cache.get(chave));
    }
}
```

### 🎯 Resumo final
- ConcurrentHashMap → usado porque é seguro para múltiplas threads
- Spring Cache → funciona como um mapa chave → valor
- Cada requisição → pode rodar em uma thread diferente
- Cache evita acessar banco várias vezes
- Spring faz isso automaticamente com @Cacheable🎯 Resumo final
