# Sistema de Gerenciamento de Clientes

Olá! Esse é o meu projeto de um sistema de gerenciamento de clientes. Foram implementados todos os serviços requeridos e alguns diferenciais: envio de e-mail assíncrono, build pelo Docker e alguns serviços extras para melhor interação com a API.

## Executando o Docker

### Importante!!
Se você encontrar problemas ao iniciar a imagem do Java, isso pode ser devido ao MySQL não ter sido inicializado 100% e o Java ter tentado se conectar com ele antes disso. Para resolver isso, siga estes passos:

**Inicie o MySQL**: Caso o docker-compose pare de rodar o MySql, Execute o comando docker-compose up mysql e aguarde até que o MySQL esteja completamente iniciado.

**Inicie a imagem do Java**: Após garantir que o MySQL está rodando corretamente, execute o comando para iniciar a imagem do Java. Isso garantirá que a aplicação Java possa se conectar ao MySQL sem problemas.

Ao seguir esses passos, a imagem do Java deve funcionar corretamente.

Vamos lá!!

1. **Clone o repositório**:
   ```bash
   git clone <URL_DO_REPOSITORIO>
   cd cliente-gerenciamento

2. **Compile o projeto utilizando Maven**:
   mvn clean package (para criar o .jar)

3. **Construa a imagem Docker**:
   docker-compose build

4. **Inicie os containers**:
   docker-compose up -d

## Efetuando o Login e utilizando os serviços

1. Após rodar o docker, você terá que utilizar o serviço **"/popularBD"** (apenas para criar um usuario admin, pois o banco de dados se iniciará vazio e o serviço de criar o usuário é autenticado, conforme solicitado).

2. Após ter o "admin" criado, deverá realizar o login no serviço **"/login"**, com os dados no body(na collection também temos essa informação):

   {"cpf" : "85575443745", "password" : "admin123"}

4. Feito o login e retornado 201, será gerado e retornado um **token**, agora conseguirá utilizar todos os outros serviços! Basta ir na aba "Authorization", usar a opção **"Bearer Token"** e colar o token obtido.

## Contato

Obrigado! Qualquer dúvida, estou à disposição para conversar.
