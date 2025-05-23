# 🛍️ Produtos API

API RESTful para gerenciamento de produtos, desenvolvida com Spring Boot. Suporta operações CRUD, busca por EAN e integração com a API pública do OpenFoodFacts.

---

## ⚙️ Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot 3.2.4**
- **Spring Web**
- **Spring Data JPA**
- **Springdoc OpenAPI (Swagger UI)**
- **WebClient (WebFlux)** para chamadas HTTP externas
- **Jackson** para serialização JSON
- **H2 / PostgreSQL / MySQL** (adaptável)
- **Maven**

---

## 🚀 Como Rodar o Projeto

### Pré-requisitos

- Java 17 ou 21 instalado
- Maven instalado

### Passos

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/produtos-api.git
cd produtos-api

# Compile e rode
mvn clean spring-boot:run
