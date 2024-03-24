# TableTrust API

Este projeto é uma API desenvolvida com Java e Spring Boot, designada para Reserva e Avaliação de Restaurantes.

## Funcionalidades Principais

### 1 - Registro de Restaurantes
Os donos de restaurantes podem cadastrar seus estabelecimentos informando o nome, localização, tipo de cozinha, horários de funcionamento e capacidade.

### 2 - Busca de Restaurantes
Os clientes podem buscar restaurantes por nome, localização ou tipo de cozinha.

### 3 – Reserva de Mesas
Quando o cliente achar o restaurante, ele pode fazer uma reserva para um dia e hora específica, desde que a capacidade do restaurante não tenha atingido o máximo para aquela data e hora.

### 4 - Avaliação e Comentários
Após a visita, o cliente pode avaliar o restaurante e deixar comentários sobre sua experiência.

### 5 – Gerenciamento de Reservas
Os restaurantes podem gerencias as reservas, visualizando e atualizando o status das mesas.

## Tecnologias Utilizadas

Este projeto foi desenvolvido com as seguintes tecnologias e bibliotecas:

- **Java 17**: Versão do Java utilizada no projeto.
- **Spring Boot**: Framework principal para a criação de aplicações Spring.
- **Spring Boot DevTools**: Para desenvolvimento rápido com reinício automático.
- **Spring Boot Starter Security**: Para autenticação e segurança da aplicação.
- **Spring Boot Starter Web**: Para construção de aplicações web, usando o Spring MVC.
- **Spring Boot Starter Data Jpa**: Para auxílio na persistência no Banco de Dados.
- **Spring Boot Validation**: Para validação de campos.
- **Springdoc OpenAPI**: Para documentação da API REST com Swagger.
- **Lombok**: Para reduzir o código boilerplate, como getters e setters.
- **PostgreSQL**: banco de dados relacional.


## Como Executar

## Documentação

A documentação detalhada da API está acessível na URL:
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)


## Segurança
