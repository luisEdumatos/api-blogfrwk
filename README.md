# Blog API

API Rest Java para Blog (Posts, Comments, Photos) utilizando-se:
 
Spring Boot, PostgreSQL, Spring Security, JWT, JPA, Hibernate, Lombok, Mapstruct, Testes MockMvc, Swagger, Jacoco. 

## Requisitos 

**1. Seu ambiente deve estar configurado e/ou ter instalado: **

- Java 11 
- Maven 3.8.1 
- PostgresSQL
- Postman

## Como executar a API?

**1. Clone a aplicação**

```bash
git clone https://github.com/luisEdumatos/api-blogfrwk.git
```

**2. Crie duas bases de dados no seu banco PostgreSQL**

- Base de desenvolvimento blogfrwk configurada em `\src\main\resources\application.properties`
- Base de testes blogfrwk-tests configurada em `\src\test\resources\test.properties`

**3. Altere o username e password de acordo com o usuário configurado no seu BD**

+ Arquivos de configuração: Dev: `\src\main\resources\application.properties` - Tests: `\src\test\resources\test.properties`
+ Alterar `spring.datasource.username` e `spring.datasource.password` 

**4. Acesse a pasta do projeto \api-blogfrwk\ e execute o Maven**

```bash
mvn spring-boot:run
```
A aplicação será iniciada em <http://localhost:8080>

## Sobre o sistema 

A API permite o cadastro de Posts, Comments, e Photos. 

Posts: 
- Cadastrar (POST): Qualquer usuário autenticado 
- Ler (GET): Qualquer usuário autenticado
- Atualizar (PUT): Apenas o dono do Post
- Deletar (DELETE): Apenas o dono do Post

Comments:
- Cadastrar (POST): Qualquer usuário autenticado 
- Ler (GET): Qualquer usuário autenticado
- Atualizar (PUT): Apenas o dono do Comment
- Deletar (DELETE): Apenas o dono do Comment

Photos: 
- Cadastrar (POST): Apenas o dono do Post ao qual a Photo pertencerá.
- Ler (GET): Qualquer usuário autenticado
- Deletar (DELETE): Apenas o dono do Post ao qual a Photo pertence.

## Funcionamento (Como testar)

Após ter executado o Maven e iniciado a aplicação, é possível utilizar o Swagger e o Postman para acessar os EndPoints. 
Segue abaixo instruções de como utilizar o Postman: 



## Documentação da API

O framework Swagger está configurado na aplicação para fornecer a documentação dos EndPoints da API. 
Para acessa-lo, após iniciar a aplicação a documentação estará disponível em <http://localhost:8080/swagger-ui.html#/>

## Testes

O plugin JaCoCo está configurado na aplicação para analise dos testes implementados. Segundo a analise do plugin, a API está com cobertura de 40% de testes. 
Para acessar os resultados completos abra o arquivo jacoco.html disponível na pasta raiz deste projeto. 