package br.ita.fg.funcional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import br.ita.fg.util.AuxiliaTest;

class TestesFuncionaisForumGamification {
	private WebDriver driver;

	@BeforeEach
	void setUp() throws Exception {
		AuxiliaTest.popularBanco("inicio.xml");
		
		//Setting system properties of ChromeDriver 
		System.setProperty("webdriver.chrome.driver", "D:\\softDev\\libraries\\WebDriver\\bin\\chromedriver.exe");	
		//necessário para testar no Google Chrome - permite todas as origens
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		
		driver = new ChromeDriver(options);
	}

	@AfterEach
	void tearDown() throws Exception {
		driver.quit();
	}
		
	@Test
	void cadastrarUsuario() {
		driver.get("http://localhost:8080/fg");
		//driver.manage().window().maximize();
		driver.findElement(By.name("btLogin")).click();
		driver.findElement(By.linkText("CRIE SUA CONTA AGORA")).click();		
		driver.findElement(By.name("login")).sendKeys("rodrigo.pires");
		driver.findElement(By.name("email")).sendKeys("rodrigo.pires@bol.com.br");
		driver.findElement(By.name("nome")).sendKeys("Rodrigo Pires da Silva");
		driver.findElement(By.name("senha")).sendKeys("rps123");
		driver.findElement(By.name("confirmarSenha")).sendKeys("rps123");
		driver.findElement(By.name("btForm")).click();
		assertEquals("Usuário Cadastrado com Sucesso", driver.findElement(By.id("tituloPagina")).getText());
	}
	
	@Test
	void cadastrarUsuarioComErroDePreenchimento() {
		driver.get("http://localhost:8080/fg");
		driver.findElement(By.name("btLogin")).click();
		driver.findElement(By.linkText("CRIE SUA CONTA AGORA")).click();
		
		//usuário envia formulário em branco (faltou preencher login, e-mail, nome e senha)
		driver.findElement(By.name("btForm")).click();
		assertEquals("Campo de Preenchimento Obrigatório!", driver.findElement(By.id("errorMsgLogin")).getText());
		assertEquals("Campo de Preenchimento Obrigatório!", driver.findElement(By.id("errorMsgEmail")).getText());
		assertEquals("Campo de Preenchimento Obrigatório!", driver.findElement(By.id("errorMsgNome")).getText());
		assertEquals("Campo de Preenchimento Obrigatório!", driver.findElement(By.id("errorMsgSenha")).getText());
		
		//usuario preenche login e envia (faltou preencher e-mail, nome e Senha)
		driver.findElement(By.name("login")).sendKeys("alelima");
		driver.findElement(By.name("btForm")).click();
		assertEquals("", driver.findElement(By.id("errorMsgLogin")).getText());
		assertEquals("Campo de Preenchimento Obrigatório!", driver.findElement(By.id("errorMsgEmail")).getText());
		assertEquals("Campo de Preenchimento Obrigatório!", driver.findElement(By.id("errorMsgNome")).getText());
		assertEquals("Campo de Preenchimento Obrigatório!", driver.findElement(By.id("errorMsgSenha")).getText());
		
		//usuario preenche e-mail e envia (faltou preencher nome e senha)
		driver.findElement(By.name("email")).sendKeys("aloliveira@bol.com.br");
		driver.findElement(By.name("btForm")).click();
		assertEquals("", driver.findElement(By.id("errorMsgLogin")).getText());
		assertEquals("", driver.findElement(By.id("errorMsgEmail")).getText());
		assertEquals("Campo de Preenchimento Obrigatório!", driver.findElement(By.id("errorMsgNome")).getText());
		assertEquals("Campo de Preenchimento Obrigatório!", driver.findElement(By.id("errorMsgSenha")).getText());
		
		//usuario prenche nome e envia (faltou preencher senha)
		driver.findElement(By.name("nome")).sendKeys("Alessandra Lima de Oliveira");
		driver.findElement(By.name("btForm")).click();
		assertEquals("", driver.findElement(By.id("errorMsgLogin")).getText());
		assertEquals("", driver.findElement(By.id("errorMsgEmail")).getText());
		assertEquals("", driver.findElement(By.id("errorMsgNome")).getText());
		assertEquals("Campo de Preenchimento Obrigatório!", driver.findElement(By.id("errorMsgSenha")).getText());
		
		//usuario preenche senha e envia (faltou confirmar senha)
		driver.findElement(By.name("senha")).sendKeys("1234");
		driver.findElement(By.name("btForm")).click();
		assertEquals("", driver.findElement(By.id("errorMsgLogin")).getText());
		assertEquals("", driver.findElement(By.id("errorMsgEmail")).getText());
		assertEquals("", driver.findElement(By.id("errorMsgNome")).getText());
		assertEquals("Senha e Confirmar Senha precisam ser iguais!", driver.findElement(By.id("errorMsgSenha")).getText());
		assertEquals("Senha e Confirmar Senha precisam ser iguais!", driver.findElement(By.id("errorMsgConfirmarSenha")).getText());
		
		//usuario preenche senha e confirmar senha com valores diferentes 
		driver.findElement(By.name("senha")).sendKeys("alo1234");
		driver.findElement(By.name("confirmarSenha")).sendKeys("Alo1234");
		driver.findElement(By.name("btForm")).click();
		assertEquals("Senha e Confirmar Senha precisam ser iguais!", driver.findElement(By.id("errorMsgSenha")).getText());
		assertEquals("Senha e Confirmar Senha precisam ser iguais!", driver.findElement(By.id("errorMsgConfirmarSenha")).getText());
		
		//usuario preenche senha e confirmar senha com valores iguais (mas login escolhido já está cadastrado no sistema)
		driver.findElement(By.name("senha")).sendKeys("alo1234");
		driver.findElement(By.name("confirmarSenha")).sendKeys("alo1234");
		driver.findElement(By.name("btForm")).click();
		assertEquals("Seu login já está cadastrado em nosso sistema!", driver.findElement(By.id("errorMsgLogin")).getText());
		
		//usuario troca login, preenche senha, confirmar senha, e envia
		driver.findElement(By.name("login")).clear();
		driver.findElement(By.name("login")).sendKeys("aloliveira");
		driver.findElement(By.name("senha")).sendKeys("alo1234");
		driver.findElement(By.name("confirmarSenha")).sendKeys("alo1234");
		driver.findElement(By.name("btForm")).click();
		assertEquals("Usuário Cadastrado com Sucesso", driver.findElement(By.id("tituloPagina")).getText());
	}
	
	@Test
	void login() {
		driver.get("http://localhost:8080/fg");
		//driver.manage().window().maximize();
		driver.findElement(By.name("btLogin")).click();
		driver.findElement(By.name("login")).sendKeys("alelima");
		driver.findElement(By.name("senha")).sendKeys("ab1234");
		driver.findElement(By.name("btForm")).click();
		assertEquals("Alessandra Lima - Nível Usuário", driver.findElement(By.id("display")).getText());
	}
	
	@Test
	void loginComErroDePreenchimento() {
		driver.get("http://localhost:8080/fg");
		//driver.manage().window().maximize();
		driver.findElement(By.name("btLogin")).click();
		//tenta login com senha incorreta
		driver.findElement(By.name("login")).sendKeys("alelima"); //login correto
		driver.findElement(By.name("senha")).sendKeys("123"); //senha incorreta
		driver.findElement(By.name("btForm")).click();
		assertEquals("Login ou senha incorretos!", driver.findElement(By.id("errorMsg")).getText());
		//tenta login com login incorreto
		driver.findElement(By.name("login")).sendKeys("ale"); //login incorreto
		driver.findElement(By.name("senha")).sendKeys("ab1234"); //senha correta
		driver.findElement(By.name("btForm")).click();
		assertEquals("Login ou senha incorretos!", driver.findElement(By.id("errorMsg")).getText());
		//tenta login com login e senha corretos		
		driver.findElement(By.name("login")).sendKeys("alelima"); //login correto
		driver.findElement(By.name("senha")).sendKeys("ab1234"); //senha correta
		driver.findElement(By.name("btForm")).click();
		assertEquals("Alessandra Lima - Nível Usuário", driver.findElement(By.id("display")).getText());
	}
	
	@Test
	void loginDeUsuarioBloqueadoEhRedirecionadoParaPaginaErro() {
		driver.get("http://localhost:8080/fg");
		//driver.manage().window().maximize();
		driver.findElement(By.name("btLogin")).click();
		driver.findElement(By.name("login")).sendKeys("braraujo");
		driver.findElement(By.name("senha")).sendKeys("sat3456");
		driver.findElement(By.name("btForm")).click();
		assertEquals("Erro", driver.getTitle());
		assertTrue(driver.findElement(By.id("errorMsg")).getText().contains("Usuário Bloqueado!"));
	}
	
	@Test
	void editarUsuarioEmail() {
		//etapa 1: efetuar login
		driver.get("http://localhost:8080/fg");
		//driver.manage().window().maximize();
		driver.findElement(By.name("btLogin")).click();
		driver.findElement(By.name("login")).sendKeys("alelima");
		driver.findElement(By.name("senha")).sendKeys("ab1234");
		driver.findElement(By.name("btForm")).click();
		assertEquals("Alessandra Lima - Nível Usuário", driver.findElement(By.id("display")).getText());		
		//etapa 2: modificar email
		driver.findElement(By.id("display")).click();
		assertEquals("alelima@gmail.com", driver.findElement(By.id("contaEmail")).getText()); //email atual	
		driver.findElement(By.linkText("editar e-mail")).click();
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys("alessandralima@bol.com.br");
		driver.findElement(By.name("btForm")).click();
		assertEquals("alessandralima@bol.com.br", driver.findElement(By.id("contaEmail")).getText()); //novo email	
	}
	
	@Test
	void editarUsuarioEmailComErroDePreenchimento() {
		//etapa 1: efetuar login
		driver.get("http://localhost:8080/fg");
		//driver.manage().window().maximize();
		driver.findElement(By.name("btLogin")).click();
		driver.findElement(By.name("login")).sendKeys("alelima");
		driver.findElement(By.name("senha")).sendKeys("ab1234");
		driver.findElement(By.name("btForm")).click();
		assertEquals("Alessandra Lima - Nível Usuário", driver.findElement(By.id("display")).getText());		
		//etapa 2: modificar email com erro de preenchimento
		driver.findElement(By.id("display")).click();
		assertEquals("alelima@gmail.com", driver.findElement(By.id("contaEmail")).getText()); //email atual	
		driver.findElement(By.linkText("editar e-mail")).click();
		driver.findElement(By.name("email")).clear();
		//envia formulário com a caixa de texto do e-mail em branco
		driver.findElement(By.name("btForm")).click();
		assertEquals("Campo de Preenchimento Obrigatório!", driver.findElement(By.id("errorMsgEmail")).getText());
		//insere novo e-mail válido e reenvia
		driver.findElement(By.name("email")).sendKeys("alima@bol.com.br.com");
		driver.findElement(By.name("btForm")).click();
		assertEquals("alima@bol.com.br.com", driver.findElement(By.id("contaEmail")).getText()); //novo e-mail)
	}
	
	@Test
	void editarUsuarioNome() {
		//etapa 1: efetuar login
		driver.get("http://localhost:8080/fg");
		//driver.manage().window().maximize();
		driver.findElement(By.name("btLogin")).click();
		driver.findElement(By.name("login")).sendKeys("flaviosp");
		driver.findElement(By.name("senha")).sendKeys("5678");
		driver.findElement(By.name("btForm")).click();
		assertEquals("Flavio Sampaio - Nível Usuário", driver.findElement(By.id("display")).getText());
		//etapa 2: modificar nome
		driver.findElement(By.id("display")).click();
		assertEquals("Flavio Sampaio", driver.findElement(By.id("contaNome")).getText()); //nome atual	
		driver.findElement(By.linkText("editar nome")).click();
		driver.findElement(By.name("nome")).clear();
		driver.findElement(By.name("nome")).sendKeys("Flavio Sampaio Júnior");
		driver.findElement(By.name("btForm")).click();
		assertEquals("Flavio Sampaio Júnior", driver.findElement(By.id("contaNome")).getText()); //novo nome		
	}
	
	@Test
	void editarUsuarioNomeComErroDePrenchimento() {
		//etapa 1: efetuar login
		driver.get("http://localhost:8080/fg");
		//driver.manage().window().maximize();
		driver.findElement(By.name("btLogin")).click();
		driver.findElement(By.name("login")).sendKeys("flaviosp");
		driver.findElement(By.name("senha")).sendKeys("5678");
		driver.findElement(By.name("btForm")).click();
		assertEquals("Flavio Sampaio - Nível Usuário", driver.findElement(By.id("display")).getText());
		//etapa 2: modificar nome com erro de preenchimeto
		driver.findElement(By.id("display")).click();
		assertEquals("Flavio Sampaio", driver.findElement(By.id("contaNome")).getText()); //nome atual	
		driver.findElement(By.linkText("editar nome")).click();
		driver.findElement(By.name("nome")).clear();
		//envia formulário com a caixa de texto nome vazia
		driver.findElement(By.name("btForm")).click();
		assertEquals("Campo de Preenchimento Obrigatório!", driver.findElement(By.id("errorMsgNome")).getText());
		//reenvia formulário com nome válido
		driver.findElement(By.name("nome")).sendKeys("Flavio Sampaio Júnior");
		driver.findElement(By.name("btForm")).click();
		assertEquals("Flavio Sampaio Júnior", driver.findElement(By.id("contaNome")).getText()); //novo nome
	}
	
	@Test
	void editarSenha() {
		//prepara teste
		String login = "alelima";
		String senhaAtual = "ab1234";
		String novaSenha = "ab98";
		//acessa a página login
		driver.get("http://localhost:8080/fg");
		//driver.manage().window().maximize();
		driver.findElement(By.name("btLogin")).click();
		assertEquals("Login", driver.getTitle());
		//acessa a página Confirmar Identidade		
		driver.findElement(By.linkText("Redefinir senha")).click();		
		assertEquals("Confirmar Identidade", driver.getTitle());
		//envia formulário com login e senhaAtual
		driver.findElement(By.name("login")).sendKeys(login);
		driver.findElement(By.name("senhaAtual")).sendKeys(senhaAtual);
		driver.findElement(By.name("btForm")).click();
		assertEquals("Redefinir Senha", driver.getTitle());
		//envia formulário com novaSenha		
		driver.findElement(By.name("novaSenha")).sendKeys(novaSenha);
		driver.findElement(By.name("confirmarNovaSenha")).sendKeys(novaSenha);
		driver.findElement(By.name("btForm")).click();
		assertEquals("Senha Atualizada com Sucesso!", driver.findElement(By.id("tituloPagina")).getText());
		//efetua login com a nova senha para testar
		driver.findElement(By.className("botao")).click();
		driver.findElement(By.name("login")).sendKeys(login);
		driver.findElement(By.name("senha")).sendKeys(novaSenha);
		driver.findElement(By.name("btForm")).click();
		assertEquals("Alessandra Lima - Nível Usuário", driver.findElement(By.id("display")).getText());
	}
	
	@Test
	void editarSenhaComErroDePreenchimento() {
		//prepara teste
		String login = "alelima";
		String senhaAtual = "ab1234";
		String novaSenha = "ab98";
		//acessa a página login
		driver.get("http://localhost:8080/fg");
		//driver.manage().window().maximize();
		driver.findElement(By.name("btLogin")).click();
		assertEquals("Login", driver.getTitle());
		//acessa a página Confirmar Identidade
		driver.findElement(By.linkText("Redefinir senha")).click();		
		assertEquals("Confirmar Identidade", driver.getTitle());		
		//confirma identidade com erro de preenchimento
			//envia formulário vazio
			driver.findElement(By.name("btForm")).click();
			assertEquals("Há erros de preenchimento no seu formulário!", driver.findElement(By.id("errorMsg")).getText());
			assertEquals("Campo de Preenchimento Obrigatório!", driver.findElement(By.id("errorMsgLogin")).getText());
			assertEquals("Campo de Preenchimento Obrigatório!", driver.findElement(By.id("errorMsgSenhaAtual")).getText());
			//envia formulário somente com login
			driver.findElement(By.name("login")).sendKeys(login);
			assertEquals("Há erros de preenchimento no seu formulário!", driver.findElement(By.id("errorMsg")).getText());
			assertEquals("Campo de Preenchimento Obrigatório!", driver.findElement(By.id("errorMsgSenhaAtual")).getText());
			//envia formulário com a senha errada
			driver.findElement(By.name("senhaAtual")).sendKeys("senha errada");
			driver.findElement(By.name("btForm")).click();
			assertEquals("Login ou senha incorretos!", driver.findElement(By.id("errorMsg")).getText());
			//envia formulário com a senha correta
			driver.findElement(By.name("senhaAtual")).sendKeys(senhaAtual);
			driver.findElement(By.name("btForm")).click();
		assertEquals("Redefinir Senha", driver.getTitle());
		//troca senha com erro de preenchimento
			//envia o formulário vazio
			driver.findElement(By.name("btForm")).click();
			assertEquals("Há erros de preenchimento no seu formulário!", driver.findElement(By.id("errorMsg")).getText());			
			assertEquals("Campo de Preenchimento Obrigatório!", driver.findElement(By.id("errorMsgNovaSenha")).getText());
			//envia o formulário com Confirmar Nova Senha vazio
			driver.findElement(By.name("novaSenha")).sendKeys(novaSenha);
			driver.findElement(By.name("btForm")).click();
			assertEquals("Há erros de preenchimento no seu formulário!", driver.findElement(By.id("errorMsg")).getText());
			assertEquals("Nova Senha e Confirmar Nova Senha precisam ser iguais!", driver.findElement(By.id("errorMsgNovaSenha")).getText());
			assertEquals("Nova Senha e Confirmar Nova Senha precisam ser iguais!", driver.findElement(By.id("errorMsgConfirmarNovaSenha")).getText());
			//envia o formulário com novaSenha e confirmarNovaSenha diferentes
			driver.findElement(By.name("novaSenha")).sendKeys(novaSenha);
			driver.findElement(By.name("confirmarNovaSenha")).sendKeys("senha errada");
			driver.findElement(By.name("btForm")).click();
			assertEquals("Há erros de preenchimento no seu formulário!", driver.findElement(By.id("errorMsg")).getText());
			assertEquals("Nova Senha e Confirmar Nova Senha precisam ser iguais!", driver.findElement(By.id("errorMsgNovaSenha")).getText());
			assertEquals("Nova Senha e Confirmar Nova Senha precisam ser iguais!", driver.findElement(By.id("errorMsgConfirmarNovaSenha")).getText());
			//envia o formulário com as senhas corretas
			driver.findElement(By.name("novaSenha")).sendKeys(novaSenha);
			driver.findElement(By.name("confirmarNovaSenha")).sendKeys(novaSenha);
			driver.findElement(By.name("btForm")).click();
		//efetua login com a nova senha para testar
		driver.findElement(By.className("botao")).click();
		driver.findElement(By.name("login")).sendKeys(login);
		driver.findElement(By.name("senha")).sendKeys(novaSenha);
		driver.findElement(By.name("btForm")).click();
		assertEquals("Alessandra Lima - Nível Usuário", driver.findElement(By.id("display")).getText());			
	}
	
	@Test
	void botaoNovoForumIndisponivelParaUsuarioComum() {
		//efetua login com usuário comum
		driver.get("http://localhost:8080/fg");
		//driver.manage().window().maximize();
		driver.findElement(By.name("btLogin")).click();
		driver.findElement(By.name("login")).sendKeys("alelima");
		driver.findElement(By.name("senha")).sendKeys("ab1234");
		driver.findElement(By.name("btForm")).click();
		assertEquals("Alessandra Lima - Nível Usuário", driver.findElement(By.id("display")).getText());
		assertThrows(org.openqa.selenium.NoSuchElementException.class, () -> driver.findElement(By.linkText("Novo fórum")));
	}
	
	@Test
	void cadastrarForum() {
		//efetua login como Administrador
		driver.get("http://localhost:8080/fg");
		//driver.manage().window().maximize();
		driver.findElement(By.name("btLogin")).click();
		driver.findElement(By.name("login")).sendKeys("sabcoral");
		driver.findElement(By.name("senha")).sendKeys("vg3516");
		driver.findElement(By.name("btForm")).click();
		assertEquals("Sabrina Coral - Nível Administrador", driver.findElement(By.id("display")).getText());
		//cadastra novo fórum
		driver.findElement(By.linkText("Novo fórum")).click();
		driver.findElement(By.name("forumNome")).sendKeys("Doom 3");
		driver.findElement(By.name("forumDescricao")).sendKeys("Fórum destinado a discutir temas relacionados ao Jogo Doom3. Doom 3 é um jogo de computador do tipo tiro em primeira pessoa produzido pela id Software e distribuído pela Activision.");
		driver.findElement(By.name("btForm")).click();
		assertEquals("Doom 3", driver.findElement(By.linkText("Doom 3")).getText());
	}
	
	@Test
	void cadastrarForumComErroDePreenchimento() {
		//efetua login como Administrador
		driver.get("http://localhost:8080/fg");
		//driver.manage().window().maximize();
		driver.findElement(By.name("btLogin")).click();
		driver.findElement(By.name("login")).sendKeys("sabcoral");
		driver.findElement(By.name("senha")).sendKeys("vg3516");
		driver.findElement(By.name("btForm")).click();
		assertEquals("Sabrina Coral - Nível Administrador", driver.findElement(By.id("display")).getText());		
		//acessa página Cadastrar Fórum
		driver.findElement(By.linkText("Novo fórum")).click();
		//envia o formulário em branco
		driver.findElement(By.name("btForm")).click();
		assertEquals("Há erros de preenchimento no seu formulário!", driver.findElement(By.id("errorMsg")).getText());
		assertEquals("Campo de Preenchimento Obrigatório!", driver.findElement(By.id("errorMsgForumNome")).getText());
		assertEquals("Campo de Preenchimento Obrigatório!", driver.findElement(By.id("errorMsgForumDescricao")).getText());
		//envia o formulário sem a descrição
		driver.findElement(By.name("forumNome")).sendKeys("Doom 3");
		driver.findElement(By.name("btForm")).click();
		assertEquals("Há erros de preenchimento no seu formulário!", driver.findElement(By.id("errorMsg")).getText());
		assertEquals("Campo de Preenchimento Obrigatório!", driver.findElement(By.id("errorMsgForumDescricao")).getText());
		//envia o formulário completo
		driver.findElement(By.name("forumDescricao")).sendKeys("Fórum destinado a discutir temas relacionados ao Jogo Doom3. Doom 3 é um jogo de computador do tipo tiro em primeira pessoa produzido pela id Software e distribuído pela Activision.");
		driver.findElement(By.name("btForm")).click();
		assertEquals("Doom 3", driver.findElement(By.linkText("Doom 3")).getText());
	}
	
	@Test
	void editarForum() {
		//efetua login como Administrador
		driver.get("http://localhost:8080/fg");
		//driver.manage().window().maximize();
		driver.findElement(By.name("btLogin")).click();
		driver.findElement(By.name("login")).sendKeys("sabcoral");
		driver.findElement(By.name("senha")).sendKeys("vg3516");
		driver.findElement(By.name("btForm")).click();
		assertEquals("Sabrina Coral - Nível Administrador", driver.findElement(By.id("display")).getText());		
		//recupera lista de botões editar
		List<WebElement> botoesEditar = driver.findElements(By.name("btEditar"));		
		//edita o fórum Dune 2000
		botoesEditar.get(1).click();
		assertEquals("Dune 2000", driver.findElement(By.name("forumNome")).getAttribute("value"));
		driver.findElement(By.name("forumNome")).clear();
		driver.findElement(By.name("forumNome")).sendKeys("Editado Dune 2000");
		driver.findElement(By.name("forumDescricao")).clear();
		driver.findElement(By.name("forumDescricao")).sendKeys("Editada descrição Dune 2000");
		driver.findElement(By.name("btForm")).click();
		assertEquals("Editado Dune 2000", driver.findElement(By.partialLinkText("Dune 2000")).getText());
		List<WebElement> descricoes = driver.findElements(By.id("ultimaLinha"));
		assertEquals("Editada descrição Dune 2000", descricoes.get(1).getText());
	}
	
	@Test
	void editarForumComErroDePreenchimento() {
		//efetua login como Administrador
		driver.get("http://localhost:8080/fg");
		//driver.manage().window().maximize();
		driver.findElement(By.name("btLogin")).click();
		driver.findElement(By.name("login")).sendKeys("sabcoral");
		driver.findElement(By.name("senha")).sendKeys("vg3516");
		driver.findElement(By.name("btForm")).click();
		assertEquals("Sabrina Coral - Nível Administrador", driver.findElement(By.id("display")).getText());
		//recupera lista de botões editar
		List<WebElement> botoesEditar = driver.findElements(By.name("btEditar"));		
		//edita o fórum Jogo das Palavras Embaralhadas com erros de preenchimento
		botoesEditar.get(0).click();
		assertEquals("Jogo Das Palavras Embaralhadas", driver.findElement(By.name("forumNome")).getAttribute("value"));		
		//envia formulário vazio
		driver.findElement(By.name("forumNome")).clear();
		driver.findElement(By.name("forumDescricao")).clear();
		driver.findElement(By.name("btForm")).click();
		assertEquals("Há erros de preenchimento no seu formulário!", driver.findElement(By.id("errorMsg")).getText());
		assertEquals("Campo de Preenchimento Obrigatório!", driver.findElement(By.id("errorMsgForumNome")).getText());
		assertEquals("Campo de Preenchimento Obrigatório!", driver.findElement(By.id("errorMsgForumDescricao")).getText());
		//reenvia formulário faltando descrição
		driver.findElement(By.name("forumNome")).sendKeys("Editado Jogo Das Palavras Embaralhadas");
		driver.findElement(By.name("btForm")).click();
		assertEquals("Há erros de preenchimento no seu formulário!", driver.findElement(By.id("errorMsg")).getText());
		assertEquals("Campo de Preenchimento Obrigatório!", driver.findElement(By.id("errorMsgForumDescricao")).getText());
		//renvia o formulário completo
		driver.findElement(By.name("forumDescricao")).sendKeys("Editada descrição Jogo Das Palavras Embaralhadas");
		driver.findElement(By.name("btForm")).click();
		assertEquals("Editado Jogo Das Palavras Embaralhadas", driver.findElement(By.partialLinkText("Palavras Embaralhadas")).getText());
		List<WebElement> descricoes = driver.findElements(By.id("ultimaLinha"));
		assertEquals("Editada descrição Jogo Das Palavras Embaralhadas", descricoes.get(0).getText());		
	}
	
	@Test
	void botaoNovoTopicoRedirecionaUsuarioDeslogadoParaPaginaLogin() {
		//acessa a página index.jsp		
		driver.get("http://localhost:8080/fg");
		//driver.manage().window().maximize();
		//seleciona o fórum Dune 2000
		driver.findElement(By.linkText("Dune 2000")).click();
		//clica no botão novo tópico
		driver.findElement(By.linkText("Novo tópico")).click();
		assertEquals("Login", driver.getTitle());
		assertEquals("Você precisa estar logado para acessar este recurso!", driver.findElement(By.id("errorMsg")).getText());
	}
	
	@Test
	void cadastrarTopico() {
		//efetua login
		driver.get("http://localhost:8080/fg");
		//driver.manage().window().maximize();
		driver.findElement(By.name("btLogin")).click();
		driver.findElement(By.name("login")).sendKeys("flaviosp");
		driver.findElement(By.name("senha")).sendKeys("5678");
		driver.findElement(By.name("btForm")).click();
		assertEquals("Flavio Sampaio - Nível Usuário", driver.findElement(By.id("display")).getText());
		//verifica placar do usuário antes de inserir novo tópico
		driver.findElement(By.id("display")).click();
		int placarInicial = Integer.parseInt(driver.findElement(By.id("contaPontos")).getText());
		assertEquals(1, placarInicial);
		//seleciona fórum Dune 2000
		driver.findElement(By.name("btHome")).click();
		driver.findElement(By.linkText("Dune 2000")).click();
		//cria um novo tópico
		driver.findElement(By.linkText("Novo tópico")).click();
		assertEquals("Dune 2000", driver.findElement(By.name("forumNome")).getAttribute("value"));
		driver.findElement(By.name("topicoNome")).sendKeys("Fase 9A e Fase 9B");
		driver.findElement(By.name("topicoConteudo")).sendKeys("Gostaria de saber se 9A e 9B são duas fases diferentes com inimigos diferentes, ou se é uma fase só com dois pontos iniciais diferentes?");
		driver.findElement(By.name("btForm")).click();
		//verifica se novo tópico foi devidamente inserido
		assertEquals("Fase 9A e Fase 9B", driver.findElement(By.partialLinkText("Fase 9")).getText());
		List<WebElement> conteudos = driver.findElements(By.id("ultimaLinha"));
		assertTrue(conteudos.get(3).getText().contains("Gostaria de saber se 9A e 9B são duas fases diferentes com inimigos diferentes, ou se é uma fase só com dois pontos iniciais diferentes?"));
		//verifica se usuário ganhou 10 pontos por criar novo tópico
		driver.findElement(By.id("display")).click();
		int placarEsperado = placarInicial + 10;
		assertEquals(placarEsperado, Integer.parseInt(driver.findElement(By.id("contaPontos")).getText()));
	}
	
	@Test
	void cadastrarTopicoComErroDePreenchimento() {
		//efetua login
		driver.get("http://localhost:8080/fg");
		//driver.manage().window().maximize();
		driver.findElement(By.name("btLogin")).click();
		driver.findElement(By.name("login")).sendKeys("flaviosp");
		driver.findElement(By.name("senha")).sendKeys("5678");
		driver.findElement(By.name("btForm")).click();
		assertEquals("Flavio Sampaio - Nível Usuário", driver.findElement(By.id("display")).getText());
		//verifica placar do usuário antes de inserir novo tópico
		driver.findElement(By.id("display")).click();
		int placarInicial = Integer.parseInt(driver.findElement(By.id("contaPontos")).getText());
		assertEquals(1, placarInicial);
		//seleciona fórum Dune 2000
		driver.findElement(By.name("btHome")).click();
		driver.findElement(By.linkText("Dune 2000")).click();
		//cria um novo tópico
		driver.findElement(By.linkText("Novo tópico")).click();
		assertEquals("Dune 2000", driver.findElement(By.name("forumNome")).getAttribute("value"));
		//envia o formulário vazio
		driver.findElement(By.name("btForm")).click();
		assertEquals("Há erros de preenchimento no seu formulário!", driver.findElement(By.id("errorMsg")).getText());
		assertEquals("Campo de Preenchimento Obrigatório!", driver.findElement(By.id("errorMsgTopicoNome")).getText());
		assertEquals("Campo de Preenchimento Obrigatório!", driver.findElement(By.id("errorMsgTopicoConteudo")).getText());
		//envia o forumulário faltando o conteúdo
		driver.findElement(By.name("topicoNome")).sendKeys("Fase 9A e Fase 9B");
		driver.findElement(By.name("btForm")).click();
		assertEquals("Há erros de preenchimento no seu formulário!", driver.findElement(By.id("errorMsg")).getText());
		assertEquals("Campo de Preenchimento Obrigatório!", driver.findElement(By.id("errorMsgTopicoConteudo")).getText());
		//envia o forumuário completo
		driver.findElement(By.name("topicoConteudo")).sendKeys("Gostaria de saber se 9A e 9B são duas fases diferentes com inimigos diferentes, ou se é uma fase só com dois pontos iniciais diferentes?");
		driver.findElement(By.name("btForm")).click();
		//verifica se novo tópico foi devidamente inserido
		assertEquals("Fase 9A e Fase 9B", driver.findElement(By.partialLinkText("Fase 9")).getText());
		List<WebElement> conteudos = driver.findElements(By.id("ultimaLinha"));
		assertTrue(conteudos.get(3).getText().contains("Gostaria de saber se 9A e 9B são duas fases diferentes com inimigos diferentes, ou se é uma fase só com dois pontos iniciais diferentes?"));
		//verifica se usuário ganhou 10 pontos por criar novo tópico
		driver.findElement(By.id("display")).click();
		int placarEsperado = placarInicial + 10;
		assertEquals(placarEsperado, Integer.parseInt(driver.findElement(By.id("contaPontos")).getText()));	
	}
	
	@Test
	void editarTopico() {
		//efetua login
		driver.get("http://localhost:8080/fg");
		//driver.manage().window().maximize();
		driver.findElement(By.name("btLogin")).click();
		driver.findElement(By.name("login")).sendKeys("paulo.cintra");
		driver.findElement(By.name("senha")).sendKeys("1234");
		driver.findElement(By.name("btForm")).click();
		assertEquals("Paulo Cintra - Nível Usuário", driver.findElement(By.id("display")).getText());
		//seleciona fórum Jogo Das Palavras Embaralhadas
		driver.findElement(By.linkText("Jogo Das Palavras Embaralhadas")).click();
		//edita tópico Implementações de Embaralhador
		List<WebElement> botoesEditar = driver.findElements(By.name("btEditar"));
		botoesEditar.get(0).click();
		assertEquals("Implementações de Embaralhador", driver.findElement(By.name("topicoNome")).getAttribute("value"));
		driver.findElement(By.name("topicoNome")).clear();
		driver.findElement(By.name("topicoNome")).sendKeys("Editado Implementações de Embaralhador");
		driver.findElement(By.name("topicoConteudo")).clear();
		driver.findElement(By.name("topicoConteudo")).sendKeys("Editado Alguém tem uma boa ideia para implementar a interface embaralhador?");
		driver.findElement(By.name("btForm")).click();
		//verifica se o tópico foi editado
		assertEquals("Editado Implementações de Embaralhador", driver.findElement(By.partialLinkText("Implementações de Embaralhador")).getText());
		List<WebElement> conteudos = driver.findElements(By.id("ultimaLinha"));
		assertTrue(conteudos.get(1).getText().contains("Editado Alguém tem uma boa ideia para implementar a interface embaralhador?"));
	}
	
	@Test
	void editarTopicoComErroDePreenchimento() {
		//efetua login
		driver.get("http://localhost:8080/fg");
		//driver.manage().window().maximize();
		driver.findElement(By.name("btLogin")).click();
		driver.findElement(By.name("login")).sendKeys("paulo.cintra");
		driver.findElement(By.name("senha")).sendKeys("1234");
		driver.findElement(By.name("btForm")).click();
		assertEquals("Paulo Cintra - Nível Usuário", driver.findElement(By.id("display")).getText());
		//seleciona fórum Jogo Das Palavras Embaralhadas
		driver.findElement(By.linkText("Jogo Das Palavras Embaralhadas")).click();
		//edita tópico Implementações de Embaralhador com erros de preenchimento
		List<WebElement> botoesEditar = driver.findElements(By.name("btEditar"));
		botoesEditar.get(0).click();
		assertEquals("Implementações de Embaralhador", driver.findElement(By.name("topicoNome")).getAttribute("value"));
		//envia o formulário vazio
		driver.findElement(By.name("topicoNome")).clear();
		driver.findElement(By.name("topicoConteudo")).clear();
		driver.findElement(By.name("btForm")).click();
		assertEquals("Há erros de preenchimento no seu formulário!", driver.findElement(By.id("errorMsg")).getText());
		assertEquals("Campo de Preenchimento Obrigatório!", driver.findElement(By.id("errorMsgTopicoNome")).getText());
		assertEquals("Campo de Preenchimento Obrigatório!", driver.findElement(By.id("errorMsgTopicoConteudo")).getText());
		//envia o formulário sem o conteúdo
		driver.findElement(By.name("topicoNome")).sendKeys("Editado Implementações de Embaralhador");
		driver.findElement(By.name("btForm")).click();
		assertEquals("Há erros de preenchimento no seu formulário!", driver.findElement(By.id("errorMsg")).getText());
		assertEquals("Campo de Preenchimento Obrigatório!", driver.findElement(By.id("errorMsgTopicoConteudo")).getText());
		//envia o formulário completo
		driver.findElement(By.name("topicoConteudo")).sendKeys("Editado Alguém tem uma boa ideia para implementar a interface embaralhador?");
		driver.findElement(By.name("btForm")).click();
		//verifica se o tópico foi editado
		assertEquals("Editado Implementações de Embaralhador", driver.findElement(By.partialLinkText("Implementações de Embaralhador")).getText());
		List<WebElement> conteudos = driver.findElements(By.id("ultimaLinha"));
		assertTrue(conteudos.get(1).getText().contains("Editado Alguém tem uma boa ideia para implementar a interface embaralhador?"));
	}
	
	@Test
	void botaoAdicionarComentarioRedirecionaUsuarioDeslogadoParaPaginaLogin() {
		//acessa a página index.jsp		
		driver.get("http://localhost:8080/fg");
		//driver.manage().window().maximize();
		//seleciona o fórum Dune 2000
		driver.findElement(By.linkText("Dune 2000")).click();
		//seleciona o tópico Ordem das construções
		driver.findElement(By.linkText("Ordem das construções")).click();		
		//clica no botão Adicionar Comentário
		driver.findElement(By.name("btForm")).click();
		assertEquals("Login", driver.getTitle());
		assertEquals("Você precisa estar logado para acessar este recurso!", driver.findElement(By.id("errorMsg")).getText());
	}
	
	@Test
	void adicionarComentario() {
		//efetua login
		driver.get("http://localhost:8080/fg");
		//driver.manage().window().maximize();
		driver.findElement(By.name("btLogin")).click();
		driver.findElement(By.name("login")).sendKeys("flaviosp");
		driver.findElement(By.name("senha")).sendKeys("5678");
		driver.findElement(By.name("btForm")).click();
		assertEquals("Flavio Sampaio - Nível Usuário", driver.findElement(By.id("display")).getText());
		//verifica placar do usuário antes de inserir novo tópico
		driver.findElement(By.id("display")).click();
		int placarInicial = Integer.parseInt(driver.findElement(By.id("contaPontos")).getText());
		assertEquals(1, placarInicial);
		//seleciona fórum Dune 2000
		driver.findElement(By.name("btHome")).click();
		driver.findElement(By.linkText("Dune 2000")).click();
		//seleciona o tópico Ordem das construções
		driver.findElement(By.linkText("Ordem das construções")).click();
		//adiciona um novo comentário
		driver.findElement(By.name("comentario")).sendKeys("Energia, barraca, refinaria, fábrica de tanque pesado, fábrica de tanque leve, reparo, centro de alta tecnologia.");
		driver.findElement(By.name("btForm")).click();
		//verifica se novo comentário foi devidamente inserido
		List<WebElement> comentarios = driver.findElements(By.id("ultimaLinha"));
		assertTrue(comentarios.get(0).getText().contains("Energia, barraca, refinaria, fábrica de tanque pesado, fábrica de tanque leve, reparo, centro de alta tecnologia."));
		//verifica se usuário ganhou 3 pontos por criar novo tópico
		driver.findElement(By.id("display")).click();
		int placarEsperado = placarInicial + 3;
		assertEquals(placarEsperado, Integer.parseInt(driver.findElement(By.id("contaPontos")).getText()));		
	}
	
	@Test
	void adicionarComentarioComErroDePreenchimento() {
		//efetua login
		driver.get("http://localhost:8080/fg");
		//driver.manage().window().maximize();
		driver.findElement(By.name("btLogin")).click();
		driver.findElement(By.name("login")).sendKeys("flaviosp");
		driver.findElement(By.name("senha")).sendKeys("5678");
		driver.findElement(By.name("btForm")).click();
		assertEquals("Flavio Sampaio - Nível Usuário", driver.findElement(By.id("display")).getText());
		//verifica placar do usuário antes de inserir novo tópico
		driver.findElement(By.id("display")).click();
		int placarInicial = Integer.parseInt(driver.findElement(By.id("contaPontos")).getText());
		assertEquals(1, placarInicial);
		//seleciona fórum Dune 2000
		driver.findElement(By.name("btHome")).click();
		driver.findElement(By.linkText("Dune 2000")).click();
		//seleciona o tópico Ordem das construções
		driver.findElement(By.linkText("Ordem das construções")).click();
		//adiciona um novo comentário vazio
		driver.findElement(By.name("btForm")).click();		
		assertEquals("Campo de Preenchimento Obrigatório!", driver.findElement(By.id("errorMsgComentario")).getText());
		//adiciona comentário correto
		driver.findElement(By.name("comentario")).sendKeys("Energia, barraca, refinaria, fábrica de tanque pesado, fábrica de tanque leve, reparo, centro de alta tecnologia.");
		driver.findElement(By.name("btForm")).click();
		//verifica se novo comentário foi devidamente inserido
		List<WebElement> comentarios = driver.findElements(By.id("ultimaLinha"));
		assertTrue(comentarios.get(0).getText().contains("Energia, barraca, refinaria, fábrica de tanque pesado, fábrica de tanque leve, reparo, centro de alta tecnologia."));
		//verifica se usuário ganhou 3 pontos por criar novo tópico
		driver.findElement(By.id("display")).click();
		int placarEsperado = placarInicial + 3;
		assertEquals(placarEsperado, Integer.parseInt(driver.findElement(By.id("contaPontos")).getText()));		
	}
	
	@Test
	void editarComentario() {
		//efetua login
		driver.get("http://localhost:8080/fg");
		//driver.manage().window().maximize();
		driver.findElement(By.name("btLogin")).click();
		driver.findElement(By.name("login")).sendKeys("tatialc");
		driver.findElement(By.name("senha")).sendKeys("1216");
		driver.findElement(By.name("btForm")).click();
		assertEquals("Tatiana Alcantara - Nível Administrador", driver.findElement(By.id("display")).getText());
		//acessa o fórum Dune 2000
		driver.findElement(By.linkText("Dune 2000")).click();
		//acessa o tópico Harkonnen 07 Hard
		driver.findElement(By.linkText("Harkonnen 07 Hard")).click();
		//edita o 2º comentário da Tatiana  na página
		List<WebElement> botoesEditar = driver.findElements(By.name("btEditar"));
		botoesEditar.get(1).click();
		driver.findElement(By.name("comentario")).clear();
		driver.findElement(By.name("comentario")).sendKeys("Editado");
		driver.findElement(By.name("btForm")).click();
		//verifica se o comentário foi editado
		List<WebElement> conteudos = driver.findElements(By.id("ultimaLinha"));
		assertTrue(conteudos.get(3).getText().contains("Editado"));		
	}
	
	@Test
	void editarComentarioComErroDePreenchimento() {
		//efetua login
		driver.get("http://localhost:8080/fg");
		//driver.manage().window().maximize();
		driver.findElement(By.name("btLogin")).click();
		driver.findElement(By.name("login")).sendKeys("tatialc");
		driver.findElement(By.name("senha")).sendKeys("1216");
		driver.findElement(By.name("btForm")).click();
		assertEquals("Tatiana Alcantara - Nível Administrador", driver.findElement(By.id("display")).getText());
		//acessa o fórum Dune 2000
		driver.findElement(By.linkText("Dune 2000")).click();
		//acessa o tópico Harkonnen 07 Hard
		driver.findElement(By.linkText("Harkonnen 07 Hard")).click();
		//edita o 2º comentário da Tatiana  na página com erro de preenchimento
		List<WebElement> botoesEditar = driver.findElements(By.name("btEditar"));
		botoesEditar.get(1).click();
		driver.findElement(By.name("comentario")).clear();
		//envia o comentário vazio
		driver.findElement(By.name("btForm")).click();
		assertEquals("Há erros de preenchimento no seu formulário!", driver.findElement(By.id("errorMsg")).getText());
		assertEquals("Campo de Preenchimento Obrigatório!", driver.findElement(By.id("errorMsgComentario")).getText());
		//envia o formulário completo
		driver.findElement(By.name("comentario")).sendKeys("Editado");
		driver.findElement(By.name("btForm")).click();
		//verifica se o comentário foi editado
		List<WebElement> conteudos = driver.findElements(By.id("ultimaLinha"));
		assertTrue(conteudos.get(3).getText().contains("Editado"));
	}
	
	
	
/********************************************/	
/*** Testes de Acesso à Páginas Restritas ***/
/********************************************/	
	@Test
	void quandoUsuarioDeslogadoAcessaPaginaUsuarioConta_ehRedirecionadoParaPaginaLogin() {
		//quando usuário deslogado acessa a página usuarioConta.jsp, é redirecionado para página login  
		driver.get("http://localhost:8080/fg/usuarioConta.jsp");
		assertEquals("Login", driver.getTitle());
	}
	
	@Test
	void quandoUsuarioDeslogadoAcessaPaginaUsuarioEditarEmailForm_ehRedirecionadoParaPaginaLogin() {
		//quando usuário deslogado acessa a página usuarioEditarEmailForm.jsp, é redirecionado para página login
		driver.get("http://localhost:8080/fg/usuarioEditarEmailForm.jsp");
		assertEquals("Login", driver.getTitle());
	}
	
	@Test
	void quandoUsuarioDeslogadoAcessaPaginaUsuarioEditarNomeForm_ehRedirecionadoParaPaginaLogin() {
		//quando usuário deslogado acessa a página usuarioEditarNomeForm.jsp, é redirecionado para página login 
		driver.get("http://localhost:8080/fg/usuarioEditarNomeForm.jsp"); 
		assertEquals("Login", driver.getTitle());
	}
	
	@Test
	void quandoUsuarioDeslogadoAcessaPaginaUsuarioRanking_ehRedirecionadoParaPaginaLogin() {
		//quando usuário deslogado acessa página usuarioRanking.jsp, é redirecionado para página login 
		driver.get("http://localhost:8080/fg/usuarioRanking.jsp");
		assertEquals("Login", driver.getTitle());
	}
	
	@Test
	void quandoUsuarioNaoAutenticadoAcessaPaginaUsuarioRedefinirSenhaForm_ehRedirecionadoParaPaginaLogin() {
		//quando usuário não autenticado acessa página usuarioRedefinirSenhaForm.jsp, é redirecionado para página login 
		driver.get("http://localhost:8080/fg/usuarioRedefinirSenhaForm.jsp");
		assertEquals("Login", driver.getTitle());
	}
	
	@Test
	void quandoUsuarioDeslogadoAcessaPaginaForumCadastrarForm_ehRedirecionadoParaPaginaLogin() {
		//quando usuário deslogado acessa a página forumCadastrarForm.jsp, é redirecionado para página login
		driver.get("http://localhost:8080/fg/forumCadastrarForm.jsp");
		assertEquals("Login", driver.getTitle());	
	}
	
	@Test
	void quandoUsuarioLogadoNaoAdministradorAcessaPaginaForumCadastrarForm_ehRedirecionadoParaPaginaErro() {
		//quando usuário logado não Administrador acessa a página forumCadastrarForm.jsp, é redirecionado para página erro
		driver.get("http://localhost:8080/fg");
		//efetua login de usuário não Administrador
		driver.findElement(By.name("btLogin")).click();
		driver.findElement(By.name("login")).sendKeys("alelima");
		driver.findElement(By.name("senha")).sendKeys("ab1234");
		driver.findElement(By.name("btForm")).click();
		assertEquals("Alessandra Lima - Nível Usuário", driver.findElement(By.id("display")).getText());
		//acessa página permitida somente à Administradores
		driver.get("http://localhost:8080/fg/forumCadastrarForm.jsp");
		assertEquals("Erro", driver.getTitle());
		assertEquals("Acesso negado! Requer nível Administrador!", driver.findElement(By.id("errorMsg")).getText());
	}
	
	@Test
	void quandoUsuarioDeslogadoAcessaPaginaForumEditarForm_ehRedirecionadoParaPaginaLogin() {
		//quando usuário deslogado acessa a página forumEditarForm.jsp, é redirecionado para a página login
		driver.get("http://localhost:8080/fg/forumEditarForm.jsp");
		assertEquals("Login", driver.getTitle());
	}
	
	@Test
	void quandoUsuarioLogadoNaoAdministradorAcessaPaginaForumEditarForm_ehRedirecionadoParaPaginaErro() {
		//quando usuário logado não Administrador acessa a página forumEditarForm.jsp é redirecionado para página erro
		driver.get("http://localhost:8080/fg");
		driver.findElement(By.name("btLogin")).click();
		driver.findElement(By.name("login")).sendKeys("alelima");
		driver.findElement(By.name("senha")).sendKeys("ab1234");
		driver.findElement(By.name("btForm")).click();
		assertEquals("Alessandra Lima - Nível Usuário", driver.findElement(By.id("display")).getText());
		driver.get("http://localhost:8080/fg/forumEditarForm.jsp");
		assertEquals("Erro", driver.getTitle());
		assertEquals("Acesso negado! Requer nível Administrador!", driver.findElement(By.id("errorMsg")).getText());
	}
	
	@Test
	void quandoUsuarioDeslogadoAcessaTopicoCadastrarForm_ehRedirecionadoParaPaginaLogin() {
		//quando usuário deslogado acessa topicoCadastrarForm.jsp, é redirecionado para página login
		driver.get("http://localhost:8080/fg/topicoCadastrarForm.jsp");
		assertEquals("Login", driver.getTitle());
	}
	
	@Test
	void quandoUsuarioDeslogadoAcessaPaginaTopicoEditarForm_ehRedirecionadoParaPaginaLogin() {
		//quando usuário deslogado acessa a página topicoEditarForm.jsp, é redirecionado para página login
		driver.get("http://localhost:8080/fg/topicoEditarForm.jsp");
		assertEquals("Login", driver.getTitle());		
	}
	
	@Test
	void quandoUsuarioDeslogadoAcessaPaginaComentarioEditarForm_ehRedirecionadoParaPaginaLogin() {		
		//quando usuário deslogado acessa a página comentarioEditarForm.jsp, é redirecionado para página login
		driver.get("http://localhost:8080/fg/comentarioEditarForm.jsp");
		assertEquals("Login", driver.getTitle());
	}
}
