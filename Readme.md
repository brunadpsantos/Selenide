# Automação do site QaNinja
Projeto de automação de teste web utilizando selenide na linguagem java.
#### Como rodar o projeto
1 - Suba os containers 

Sempre que reiniciar o computador, certifique-se que o Docker esteja online e suba os containers novamente, basta abrir o terminal e adicionar os comandos a seguir:
- Subir o Banco de Dados
```
docker run --name pgdb --network=skynet -e "POSTGRES_PASSWORD=qaninja" -p 5432:5432 -v var/lib/postgresql/data -d postgres
```
- Subir o Administrador do Banco de Dados (PgAdmin)
```
docker run --name pgadmin --network=skynet -p 15432:80 -e "PGADMIN_DEFAULT_EMAIL=root@qaninja.io" -e "PGADMIN_DEFAULT_PASSWORD=qaninja" -d dpage/pgadmin4
```
- Subir a API 
```
docker run --name ninjaplus-api --network=skynet -e "DATABASE=pgdb" -p 3000:3000 -d qaninja/ninjaplus-api
```
- Subir a Aplicação Web
```
docker run --name ninjaplus-web --network=skynet -e "VUE_APP_API=http://ninjaplus-api:3000" -p 5000:5000 -d qaninja/ninjaplus-web
```
2 - Configure o banco de dados

Realize a configuração do banco de dados: 

- Abra o terminal e digite:
```
sudo vim /etc/hosts
```
- Adicione sua senha do computador 
- Clique na tecla i para abilitar a opção de insert
- Vá ate o final da linha e adicione:
```
127.0.0.1       pgdb
127.0.0.1       pgadmin
127.0.0.1       ninjaplus-api
127.0.0.1       ninjaplus-web
```
- Clique em esc > "shift" + ":" + "wq!" + "enter"
- Digitando no terminal o comando abaixo é possível visualizar a configuração realizada 
```
cat /etc/hosts
```
3 - Acesse o banco de dados 
Após subir o container com o banco de dados acesse-o.

- Abra o google chrome e entre no endereço:
```
pgadmin:15432
```
- Digite o usuário e senha:
```
Usuário: root@qaninja.io
Senha: qaninja
```
- No servers clique com o botão direito e selecione para create > service
- Em general coloque o **nome pgdb** e em connection coloque o **host name como pgdb** e o **userName como postgres** e a **senha qaninja**
- Crie o banco de dados clicando em Databases botão direito > create > DataBase, adicione o nome da dataBase como ninjaPlus e salva. 

4 - Acesse a documentação da api 

Após configurar o banco de dados, criar o banco e subir o container da api, será possível acessar a documentação da api que contem a rota de criar usuário, o link de acesso a esta api é:
```
http://ninjaplus-api:3000/apidoc/
```

5 - Cadastre o usuário 

Para realizar o login no site será necessário criar um usuário batendo direto na api, podemos usar o postman como ferramenta para auxiliar neste processo, segue o passo a passo:

- Abra o postman e adicione o curl abaixo
```
curl --location --request POST 'http://ninjaplus-api:3000/user' \
--header 'Content-Type: application/json' \
--data-raw '{
  "full_name": "Papito",
  "email": "papito@ninjaplus.com",
  "password": "pwd123"
}'
```
#### Como gerar relatório com Allure

Este projeto tem integração com a Allure report.
A Allure Framework é uma ferramenta de relatório de teste leve e flexível que não apenas mostra uma representação muito concisa do que foi testado em um formulário de relatório da web, mas permite que todos os participantes do processo de desenvolvimento extraiam o máximo de informações úteis da execução diária de testes. 

1 - Siga o passo a passo para gerar este relatório:

- Rode os testes que contem no projeto
- Abra o terminal e caminhe ate a pasta que contem o projeto (nesta pasta deve conter o arquivo allure.results)
- Digite allure serve e clique em enter
- Abrirá uma página no navegador com o relatório da allure. 

Sempre que rodar os testes este relatório deve ser gerado novamente utilizando o mesmo procedimento acima. 

#### Como configurar o ambiente do 0 em sua máquina

Será necessário instalar o docker em sua máquina e posteriormente subir as imagens e seus containers, para isso siga os comandos contidos na pasta src > main > resources > Docker.md contido neste projeto. 
