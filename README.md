# backend-user-api

API HTTP para um e-commerce.

## Modelagem da base de dados

O diagrama de entidade-relacionamento da base de dados pode ser encontrado [aqui](diagrama-er.png).

## Como rodar o projeto?

Para baixar o projeto localmente, digitar o seguinte comando no terminal:
```
git clone https://github.com/fpsaraiva/backend-user-api.git
```

O projeto utiliza banco de dados PostgreSQL + PgAdmin em containeres Docker. Para subir a infraestrutura, om o projeto aberto na IDE,
digitar o seguinte comando:
```
docker compose up -d
```

A url para acessar a aplicação, por padrão, é **http://localhost:8081**

## Funcionalidades e endpoints

- Gerenciamento de usuários

POST /auth/register - criação de usuário  
POST /auth/login - login de usuário

- Gerenciamento de produtos

POST /products - criação de produto  
PUT /products/{productId} - atualização de produto  
GET /products - listar produtos

- Gerenciamento de carrinho de compras

POST /shopping-cart - criação de carrinho  
PUT /shopping-cart/{cartId} - atualização de carrinho  
GET /shopping-cart/{cartId} - buscar carrinho específico