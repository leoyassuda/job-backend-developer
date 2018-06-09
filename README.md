# Intelipost: Teste prático para Backend Developer

[![Build Status](http://circleci-badges-max.herokuapp.com/img/leoyassuda/job-backend-developer?token=eb51499611e865a0b6eae80986c57f7861e058d9)](https://circleci.com/gh/leoyassuda/job-backend-developer)

Aqui está a resolução do teste para Backend Developer.

## Instruções

realizar o clone do repositório
```bash
git clone https://github.com/leoyassuda/job-backend-developer.git
``` 

### Pré-requisitos

> - Java 1.8
> - Maven 3


### Compilação e geração do projeto

Acessar a pasta em que foi feito o clone e executar o seguinte comando:

```bash
mvn clean install -DskipTests
```

## Executando os testes

```bash
mvn test
```

### Executando a aplicação

Por padrão a porta que será executada é a **8989**

Mas pode ser alterada no arquivo de propriedades **application.properties**

Com o parâmetro: `server.port=NUMERO_DA_PORTA`

```bash
mvn spring-boot:run
```

### API


> / = Tipo POST - Retorna apenas uma mensagem 'OK'.
> - **Method:** `GET` 
> 
> /api/auth/signin - Efetua o login de um usuário e é gerado um token em sua resposta.
> - **Method:** `POST`
> - Parâmetros obrigatórios
>  - `usernameOrEmail=[string]`
>  - `password=[string]`
>
> /api/auth/signup - Cria um novo usuário 
> - **Method:** `POST`
> - Parâmetros obrigatórios
>  - `name=[string]`
>  - `username=[string]`
>  - `email=[string]`
>  - `password=[string]`
> 
> /api/auth/signup - Cria um novo usuário 
> - **Method:** `POST`
> - Parâmetros obrigatórios
>  - `name=[string]`
>  - `username=[string]`
>  - `email=[string]`
>  - `password=[string]`
> 
> /api/user/checkUsernameAvailability - Verifica se um 'username' pode ser utilizado
> - **Method:** `GET`
> - Parâmetros obrigatórios
>  - `username=[string]`
>
> /api/user/checkEmailAvailability - Verifica se um 'e-mail' pode ser utilizado
> - **Method:** `GET`
> - Parâmetros obrigatórios
>  - `email=[string]`
>
> /api/users/{username} - Retorna um usuário com cadastro na aplicação
> - **Method:** `GET`
> - Parâmetros obrigatórios
>  - `username=[string]`
>
> /api/user/me - Retorna o usuário corrente da sessão
> - **Method:** `GET`
> 
> /actuator/health - Verifica o status da aplicação
> - **Method:** `GET`
>
> /actuator/info - Retorna informações da aplicação
> - **Method:** `GET`
>
> /actuator/shutdown - Desliga a aplicação
> - **Method:** `POST`
>

## Construído com

* [Spring-boot](https://projects.spring.io/spring-boot/) - Framework
* [Maven](https://maven.apache.org/) - Dependency management and automation of compilation and tests 
* [H2-Database](http://www.h2database.com/html/main.html) - Database in memory

## Autor

* **Leo Yassuda** - *Initial work* - [Job-Backend-Developer](https://github.com/leoyassuda/job-backend-developer) - site [www.leoyas.com](www.leoyas.com)


## Como foi desenvolvido

A partir do fork como proposto, realizei algumas pesquisas para o obter mais ideias para tal resolução.

Foi gasto menos de 20 horas.

A estrutura foi separado em camadas para facilitar o entendimento, codificação e manutenção.

Uma api foi desenvolvida para atender o problema proposto por método assíncrono e configurável pelo arquivo de propriedade.

