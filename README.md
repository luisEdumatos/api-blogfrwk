# Blog API

API Rest Java para Blog (Posts, Comments, Photos) utilizando-se:
 
Spring Boot, PostgreSQL, Spring Security, JWT, JPA, Hibernate, Lombok, Mapstruct, Testes MockMvc, Swagger, Jacoco. 

## Requisitos 

**Seu ambiente deve estar configurado e/ou ter instalado:**

- Java 11 (Configurar nas variaveis de ambiente)
- Maven 3.8.1 (Configurar nas variaveis de ambiente)
- PostgresSQL (Configurações basicas para criar bases de dados)
- Postman (Instalação)

## Como executar a API?

**1. Clone a aplicação**

```bash
git clone https://github.com/luisEdumatos/api-blogfrwk.git
```

**2. Crie duas bases de dados no seu banco PostgreSQL**

- Base de desenvolvimento blogfrwk configurada em `\src\main\resources\application.properties`
- Base de testes blogfrwk-tests configurada em `\src\test\resources\test.properties`

**3. Altere o username e password de acordo com o usuário configurado no seu BD**

+ Arquivos de configuração:
 
Dev: `\src\main\resources\application.properties` - Tests: `\src\test\resources\test.properties`

+ Alterar ambos em `spring.datasource.username` e `spring.datasource.password` 

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
Obs: As imagens são armazenadas em um Bucket AWS S3. Entre em contato para obter as credenciais, ou crie suas proprias e altere no arquivo properties. 

- Ler (GET): Qualquer usuário autenticado
- Deletar (DELETE): Apenas o dono do Post ao qual a Photo pertence.

## Funcionamento (Como testar)

Após ter executado o Maven e iniciado a aplicação, é possível utilizar o Swagger e o Postman para acessar os EndPoints. 
Segue abaixo instruções de como utilizar o Postman: 

- Crie usuário:
 
![image](https://user-images.githubusercontent.com/32941370/147891784-a25e353f-f663-468f-a89c-4f46d5ebf632.png)

- Faça Login copie e o Token disponivel no atributo "accessToken":

![image](https://user-images.githubusercontent.com/32941370/147891794-8c3ae690-e6ea-43ea-9ca9-d273cd1f3359.png)

- Utilize o Token para acessar os demais EndPoints:

![image](https://user-images.githubusercontent.com/32941370/147891835-f7359df2-ec66-4881-b6a5-7992d3cf0446.png)

Obs: Para trocar de usuário, basta gerar novamente o Token via Login e alterar o campo Value do header com o novo Token. 

## Documentação da API

O framework Swagger está configurado na aplicação para fornecer a documentação dos EndPoints da API. 
Para acessa-lo, após iniciar a aplicação a documentação estará disponível em <http://localhost:8080/swagger-ui.html#/>

## Testes

O plugin JaCoCo está configurado na aplicação para analise dos testes implementados. Segundo a analise do plugin, a API está com cobertura de 34% de testes. 
Para acessar os resultados completos abra o arquivo jacoco.html disponível na pasta raiz deste projeto. 

## Considerações Finais

**Melhorias futuras**

- Criar uma imagem em container Docker. 
