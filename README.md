# Fórum Gamification <br> Gamification Forum

## Escolha seu Idioma <br> Choose Your Language 

- [Português (Brasil)](#versão-em-português)
- [English (United States)](#english-version)

<br>

<a name="versão-em-português"></a>
## <img src="https://github.com/togtec/togtec/blob/main/img/pt-br.png" alt="PT-BR" /> Versão em Português

### Fórum Gamification - Resumo
O **Fórum Gamification** é um projeto de **criação original** desenvolvido como trabalho final de conclusão do curso **Desenvolvimento Ágil com Java Avançado**. A aplicação consiste em um **Sistema Web de Gerenciamento de Fóruns**.

Os usuários podem cadastrar **tópicos** e **comentários**, enquanto os administradores, **tópicos**, **comentários** e novos **fóruns**.

Ao cadastrar um tópico ou comentário, o usuário ganha pontos, podendo assim, competir com os demais em um estimulante sistema de ranking.

A aplicação é organizada em camadas, seguindo o modelo **MVC**, com uma camada intermediária entre os **Servlets** e o **Banco de Dados**. O projeto utiliza um Servidor de Aplicação **JEE Apache TomCat**, que recebe as requisições e as retransmitem aos Servlets. Os Servlets, por sua vez, acessam a base de dados — através da camada intermediária **DAO** — e retransmitem as informações às páginas **JSP**.

O Fórum Gamification é uma joia do teste de software que contempla Testes Unitários **JUnit**, Testes de Integração de Banco de Dados **DBUnit** e Testes de Validação do Comportamento Completo da Aplicação **E2E** com **Selenium WebDriver**. <br>

Para maiores informações, visite a página oficial do projeto: <br>
<https://togtec.com.br/projetos/forum-gamification/resumo.php>

### Tecnologias
  * Java SE (11)
  * Maven
  * Servidor de Aplicação JEE Apache TomCat (9.0) 
  * Servlet
  * JSP
  * JDBC
  * PostgreSQL
  * JUnit 5
  * DBUnit
  * Selenium WebDriver
  
### IDE  
  * Eclipse

### Captura de tela
<p align="center">
  <img src="doc/img/img-004-home-Tatiana-Alcantara.png" alt="Home usuário Tatiana Alcantara">
</p>

Ver galeria completa de imagens em: <br>
<https://togtec.com.br/projetos/forum-gamification/imagens.php>

### Funcionalidades
1. Visitante acessa o sistema
    - Navega na lista de fóruns
    - Navega na lista de tópicos
    - Navega na lista de comentários
2. Visitante cria uma conta (define login, e-mail, nome e senha)    
3. Visitante efetua login como **usuário**:
    - Cadastra tópicos (ganha 10 pontos por tópico cadastrado)
    - Cadastra comentários (ganha 3 pontos por comentário cadastrado)
4. Visitante efetua login como **administrador**:
    - Cadastra fóruns
    - Cadastra tópicos (ganha 10 pontos por tópico cadastrado)
    - Cadastra comentários (ganha 3 pontos por comentário cadastrado)
5. Usuários e Administradores acessam o ranking para comparar sua posição em relação aos demais
6. Usuários e Administradores editam nome e e-mail
7. Usuários e Administradores redefinem senha
8. Usuários e Administradores efetuam logout

### Executando o código localmente
1. Instalar o Servidor de Aplicação JEE **Apache TomCat**
2. Instalar a IDE Eclipse (escolher a oção: **Eclipse IDE for Enterprise Java and Web Developers**)
3. **Integrar** a IDE Eclipse ao Servidor de Aplicação Apache TomCat
4. Fazer o **download** do projeto no repositório git
5. **Importar** o projeto no Eclipse
6. **Atualizar** as dependências Mavem do Projeto
7. **Adicionar** o projeto ao Servidor de Aplicação TomCat
9. Baixar manualmente a dependência **chromedriver.exe** (WebDriver para o navegador Google Chrome — Será utilizada para rodar o teste **E2E** com **Selenium Web Driver**)
    - **obs1:** Escolher a versão do chromedriver compatível com a versão do navagador Google Chrome instalada em sua máquina
    - **obs2:** Armazenar o arquivo chromedriver.exe na pasta: D:\softDev\libraries\WebDriver\bin
10. Instalar o Banco de Dados **PostgreSQL**
11. Instalar o **pgAdmin** (plataforma de administração e gerenciamento para o banco de dados PostgreSQL)
12. Dentro do pgAdmin executar:
    - O script para a criação da **ROLE** ita
    - O script para a criação do **DATABASE** forum_gamification_db
    - O script para a criação das **TABLEs**
    - O script para a criação das **SEQUENCEs**
    - **obs1:** Os scripts se encontram no arquivo **banco_de_dados.sql**, dentro da pasta **sql**, na raiz do repositório
    - **obs2:** Os scripts devem ser executados **um por vez**, na sequência em que aparecem no arquivo   
13. Executar o projeto (escolher a opção **Run on Server**)
14. Abrir uma janela do navegador e digitar a url: **localhost:8080/fg/**

**Muito difícil?**<br>
Nesse caso assista ao vídeo de divulgação do projeto e conheça tudo sem instalar absolutamente nada: <br>
<https://togtec.com.br/projetos/forum-gamification/videos.php>

<br>

<a name="english-version"></a>
## <img src="https://github.com/togtec/togtec/blob/main/img/en-us.png" alt="EN-US" /> English Version

###  Gamification Forum - Overview
The **Gamification Forum** is an **original project** developed as a final assignment for the **Agile Development with Advanced Java** course. This application is a **Forum Management System**.

Users can create **topics** and **comments**, while administrators can manage **topics**, **comments**, and add new **forums**.

By creating a topic or comment, users earn points, allowing them to compete with others in an exciting ranking system.

The application is organized in layers, following the **MVC** model, with an intermediary layer between **Servlets** and the **Database**. The project utilizes an **Apache TomCat JEE Application Server**, which receives requests and forwards them to Servlets. The Servlets then access the database — via the **DAO** intermediary layer — and return information to the **JSP** pages.

The Gamification Forum is a gem of software testing, featuring: Unit Tests with **JUnit**, Database Integration Tests with **DBUnit**, and End-to-End (E2E) Behavior Validation Tests with **Selenium WebDriver** <br>

For more information, visit the official project page: <br>
<https://en.togtec.com.br/projects/gamification-forum/summary.php>

### Technologies
  * Java SE (11)
  * Maven
  * Apache TomCat JEE Application Server (9.0)
  * Servlet
  * JSP
  * JDBC
  * PostgreSQL
  * JUnit 5
  * DBUnit
  * Selenium WebDriver

### IDE  
  * Eclipse

### Screenshot
<p align="center">
  <img src="doc/img/img-004-home-Tatiana-Alcantara.png" alt="Home user Tatiana Alcantara">
</p>

See the full image gallery at: <br>
<https://en.togtec.com.br/projects/gamification-forum/images.php>

### Features
1. Visitor accesses the system:
    - Browses through forums
    - Browses through topics
    - Browses through comments
2. Visitor creates an account (specifies login, email, name, and password)
3. Visitor logs in as a **user**:
    - Creates topics (earns 10 points per topic)
    - Creates comments (earns 3 points per comment)
4. Visitor logs in as an **administrator**:
    - Creates forums
    - Creates topics (earns 10 points per topic)
    - Creates comments (earns 3 points per comment)
5. Users and Administrators view the ranking to compare their position with others
6. Users and Administrators edit their name and email
7. Users and Administrators reset their password
8. Users and Administrators log out

### Running the Code Locally
1. Install the **Apache TomCat** JEE Application Server
2. Install the Eclipse IDE (choose **Eclipse IDE for Enterprise Java and Web Developers**)
3. **Integrate** Eclipse with the Apache TomCat Application Server
4. **Download** the project from the git repository
5. **Import** the project into Eclipse
6. **Update** the Project’s Maven dependencies
7. **Add** the project to the Apache TomCat Application Server
8. Manually download the **chromedriver.exe** dependency (WebDriver for Google Chrome — Required for **E2E** tests with **Selenium WebDriver**)
    - **Note 1:** Choose the chromedriver version compatible with your Google Chrome browser version
    - **Note 2:** Place the chromedriver.exe file in the directory: D:\softDev\libraries\WebDriver\bin
9. Install **PostgreSQL** Database
10. Install **pgAdmin** (administration and management tool for PostgreSQL)
11. In pgAdmin, run:
    - The script to create the **ROLE** ita
    - The script to create the **DATABASE** forum_gamification_db
    - The script to create the **TABLEs**
    - The script to create the **SEQUENCEs**
    - **Note 1:** The scripts are located in the **banco_de_dados.sql** file in the **sql** directory at the root of the repository
    - **Note 2:** Scripts should be executed **one by one**, in the order they appear in the file   
12. Run the project (choose **Run on Server** option)
13. Open a browser window and enter the url: **localhost:8080/fg/**

**Too difficult?**<br>
In this case, watch the project introduction video to learn everything without needing to install anything: <br>
<https://en.togtec.com.br/projects/gamification-forum/videos.php>
