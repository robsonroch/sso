
# 🔐 Exemplo de Autenticação com Spring Security + OAuth2 + JWT

Este projeto demonstra uma estrutura básica de autenticação utilizando **Spring Security**, **OAuth2** e **JWT**.

📌 **Objetivo principal:** apresentar as classes e métodos centrais envolvidos no processo de autenticação e autorização com tokens JWT no Spring Boot.

⚠️ Como se trata de um **exemplo didático**, algumas simplificações foram adotadas:

- Os usuários, perfis (roles) e permissões **não são armazenados em banco de dados**
- Os endpoints **não seguem rigorosamente o padrão RESTful**
- As respostas **não incluem HATEOAS** nem links de navegação

---

## ✅ Tecnologias utilizadas

- Java 17
- Spring Boot 3.x
- Spring Security 6.x
- JWT (via `io.jsonwebtoken`)
- OpenAPI/Swagger UI
- Lombok

---

## ▶️ Como executar

1. Clone o projeto:
   ```bash
   git clone https://github.com/seuusuario/seuprojeto.git
   cd seuprojeto
   ```

2. Execute a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ```

3. Acesse o Swagger UI:
   ```
   http://localhost:8080/sso/swagger-ui.html
   ```

---

## 👥 Usuários disponíveis

Os usuários estão definidos em memória (`UserFakeRepository`):

| username | senha     | roles                   |
|----------|-----------|--------------------------|
| admin    | 123456    | ROLE_ADMIN, ROLE_USER    |
| user     | senha     | ROLE_USER                |
| chefe    | 1234      | ROLE_USER                |

---

## 🔐 Autenticação

### POST `/auth/login`

Recebe `username` e `senha`, e retorna um **token JWT** válido.

#### Exemplo de requisição:

```json
{
  "username": "admin",
  "senha": "123456"
}
```

#### Resposta:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

## 🔓 Endpoints públicos

| Verbo | Rota                      | Descrição                          |
|-------|---------------------------|-------------------------------------|
| POST  | `/auth/login`             | Gera o token JWT                   |
| GET   | `/swagger-ui.html`        | Interface de documentação          |
| GET   | `/openapi.yaml`           | Definição OpenAPI                  |

---

## 🔐 Endpoints protegidos

> Necessário incluir o token JWT no header:  
> `Authorization: Bearer <token>`

### GET `/api/hello`

- Teste de autenticação simples.
- Retorna uma saudação se o token for válido.
- Requer: qualquer usuário autenticado.

---

## 🏭 CRUD de recursos

### Produtos

| Verbo | Rota        | Acesso       | Descrição                |
|-------|-------------|--------------|--------------------------|
| GET   | /produtos   | público      | Lista todos os produtos |
| GET   | /produtos/{id} | público  | Consulta produto por ID |
| POST  | /produtos   | `ROLE_ADMIN` | Cadastra novo produto   |

### Segmentos

| Verbo | Rota         | Acesso       | Descrição                   |
|-------|--------------|--------------|-----------------------------|
| GET   | /seguimentos | público      | Lista todos os segmentos   |
| GET   | /seguimentos/{id} | público | Consulta por ID            |
| POST  | /seguimentos | `ROLE_ADMIN` | Cadastra novo segmento     |

---

## 🧾 Fornecedores

| Verbo | Rota                                | Regra de acesso |
|-------|-------------------------------------|-----------------|
| POST  | `/fornecedores`                     | Qualquer usuário autenticado pode criar |
| GET   | `/fornecedores/{id}`                | Apenas usuários com permissão `LER` |
| POST  | `/fornecedores/{id}/delegar`        | Apenas quem **tem a permissão diretamente** (não delegada) pode delegar |

### 🔄 Lógica de permissão aplicada

- Cada fornecedor possui:
  - Um **responsável direto (`userId`)**
  - Uma lista de **delegações** com permissões (`LER`, `LISTAR`, etc.)
- A permissão é validada via o componente `@autorizador.permite(...)` e `@autorizador.podeDelegar(...)`
- Usuários que receberam acesso por delegação **não podem redelegar**

---

## 🧪 Exemplos de testes

### Requisição com token JWT

```bash
curl -H "Authorization: Bearer <seu_token>" http://localhost:8080/sso/fornecedores/f1
```

---

## 📌 Observações finais

- O foco deste projeto é **demonstrar estrutura e lógica de segurança**, não seguir padrões REST completos.
- Este código **não deve ser usado em produção sem adaptações**, especialmente:
  - Persistência em banco de dados
  - Controle de erros e validação
  - Testes de segurança
  - Separação de domínios e DTOs

---

## 📂 Organização recomendada para estudo

- `JwtUtil`: geração e extração de claims do token
- `JwtAuthenticationFilter`: validação e autenticação por token
- `UserFakeRepository`: simula banco de usuários e roles
- `Autorizador`: aplica as regras de acesso por recurso
- Controllers (`FornecedorController`, `ProdutoController`, `SeguimentoController`): aplicam segurança com `@PreAuthorize`

---

## ✉️ Contribuições

Este projeto é didático. Se quiser sugerir melhorias ou tirar dúvidas, fique à vontade para abrir uma issue.

---
