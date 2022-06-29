package br.com.fourshopp;

import br.com.fourshopp.Util.UtilMenu;
import br.com.fourshopp.entities.*;
import br.com.fourshopp.repository.ChefeRepository;
import br.com.fourshopp.repository.FuncionarioRepository;
import br.com.fourshopp.repository.ProdutoRepository;
import br.com.fourshopp.service.*;
import ch.qos.logback.classic.pattern.SyslogStartConverter;
import ch.qos.logback.classic.pattern.Util;
import br.com.fourshopp.entities.Administrador;

import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import static br.com.fourshopp.Util.UtilMenu.*;

@SpringBootApplication
public class FourShoppApplication implements CommandLineRunner {

	Scanner scanner = new Scanner(System.in);

	@Autowired
	private AdministradorService administradorService;
	@Autowired
	private ClienteService clienteService;

	@Autowired
	private OperadorService operadorService;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ChefeRepository chefeRepository;

	@Autowired
	private FuncionarioService funcionarioService;

	private Cliente cliente;

	public static void main(String[] args) {
		SpringApplication.run(FourShoppApplication.class, args);
	}

	@Override
	public void run(String[] args) throws Exception {

		System.out.println("====== BEM-VINDO AO FOURSHOPP ======");
		System.out.println("1- Sou cliente " + "\n2- Área do ADM " + "\n3- Seja um Cliente " + "\n4- Login funcionário "
				+ "\n5- Encerrar ");
		int opcao = scanner.nextInt();
		menuInicial(opcao);
	}

	public void menuInicial(int opcao) throws CloneNotSupportedException, IOException, ParseException {
		if (opcao == 1) {
			System.out.println("Insira seu cpf: ");
			String cpf = scanner.next();
			System.out.println("Insira sua senha: ");
			String password = scanner.next();
			try {
				this.cliente = clienteService.loadByEmailAndPassword(cpf, password)
						.orElseThrow(() -> new ObjectNotFoundException(1L, "Cliente"));
			} catch (ObjectNotFoundException e) {
				System.err.println("Usuario não encontrado. Faça o cadastro do cliente!");
				menuInicial(3);
			}

			int contador = 1;
			while (contador == 1) {
				System.out.println("Escolha o setor: ");
				int setor = menuSetor(scanner);

				List<Produto> collect = produtoService.listaProdutosPorSetor(setor).stream()
						.filter(x -> x.getSetor() == setor).collect(Collectors.toList());

				collect.forEach(produto -> {
					if (produto.getQuantidade() > 0) {
						System.out.println(produto.getId() + "- " + produto.getNome() + " Preço: " + produto.getPreco()
								+ " Estoque - " + produto.getQuantidade());
					}
				});

				System.out.println("Informe o número do produto desejado: ");
				Long produto = scanner.nextLong();

				System.out.println("Escolha a quantidade");
				int quantidade = scanner.nextInt();
				Produto byId = produtoService.findById(produto);
				if (produtoService.confereEstoque(quantidade, byId) == false) {
					System.err.println("Estoque indisponivel");
					break;
				}

				// Atualiza estoque
				Produto foundById = produtoService.findById(produto);
				if (foundById.getQuantidade() - quantidade >= 0) {
					produtoService.diminuirEstoque(quantidade, foundById);

					Produto clone = foundById.clone();
					System.out.println(clone.toString());
					clone.getCalculaValor(quantidade, clone);
					cliente.getProdutoList().add(clone);
					System.out.println("Deseja outro produto S/N ?");
					String escolha = scanner.next();

					if (!escolha.equalsIgnoreCase("S")) {
						contador = 2;
						gerarCupomFiscal(cliente);
						System.out.println("Gerando nota fiscal...");
						System.err.println("Fechando a aplicação...");
					} else
						System.out.println("Encerrando sistema.");
				}
			}
		}

		if (opcao == 2) {

			Administrador administrador = new Administrador("987654321", "12345678");
			if (administradorService.findAll().size() == 0) {
				administradorService.save(administrador);
			}

			System.out.println(administrador.toString());

			System.out.println("INTRANET FOURSHOPP....");

			System.out.println("Insira as credenciais do usuário administrador: ");

			System.out.println("Insira seu cpf: ");
			String cpf = scanner.next();

			System.out.println("Insira sua password: ");
			String password = scanner.next();

			try {
				Administrador admnistrador = this.administradorService.loadByCpfAndPassword(cpf, password).orElseThrow(() -> new Exception("Usuario não encontrado"));
				if (admnistrador != null) {
					System.out.println("Bem-vindo administrador!");
					System.out.println(administrador.toString());

					System.out.println("1- Cadastrar novo chefe " + "\n2- Demitir funcionário funcionário ");
					int opcaoAdm = scanner.nextInt();

					switch (opcaoAdm) {

						case 1:
							Chefe chefe = UtilMenu.menuCadastrarChefe(scanner);
							this.chefeRepository.save(chefe);
							ChefeService chefeService = new ChefeService();
							System.out.println(chefe.toString());
							menuInicial(2);
							break;

						case 2:

							System.out.println("Digite o id do funcionário que será desligado: ");
							Long idFuncionario = scanner.nextLong();
							operadorService.remove(idFuncionario);
							System.out.println("Parabéns, o funcionário foi desligado com sucesso!");
							menuInicial(2);
							break;
					}
				}
				} catch (Exception e) {

					System.out.println("Erro. Usuário não encontrado.");
				} 

			}

			if (opcao == 3) {
				Cliente cliente = menuCadastroCliente(scanner);
				this.clienteService.create(cliente);
				System.out.println("Bem-vindo, " + cliente.getNome());
				menuInicial(1);
			}

			if (opcao == 4) {
				System.out.println("Área do funcionário");

				System.out.println("1- Chefe  " + "\n2- Operador ");
				int escolhaCargo = scanner.nextInt();

				System.out.println("Insira seu cpf: ");
				String cpf = scanner.next();

				System.out.println("Insira sua password: ");
				String password = scanner.next();

				if (escolhaCargo == 1) {
					try {
						this.funcionarioService.loadByEmailAndPassword(cpf, password);
					} catch (Exception e) {
						System.out.println("Erro. Usuário não encontrado.");
					} finally {
						System.out.println("1 - Cadastrar produto");
						System.out.println("2 - Alterar cadastro de produto");
						System.out.println("3 - Deletar produto");
						System.out.println("4 - Cadastrar operadores");
						int opt = scanner.nextInt();

						if (opt == 1) {
							// Método para Cadastrar Produto
							Produto produto = UtilMenu.menuCadastrarProduto(scanner); // criamos no UtilMenu o método de
																						// cadastrar produto
							produtoService.create(produto);
						}
						if (opt == 2) {
							List<Produto> produtos = produtoService.listAll();
							System.out.println("Informe o ID do produto que deseja atualizar.");
							for (int i = 0; i < produtos.size(); i++) {
								System.out.println("Produto: " + produtos.get(i).toString());
							}
							Long itemId = scanner.nextLong();

							Produto produto = new Produto();

							try {
								produto = UtilMenu.atualizarProduto(scanner);
							} catch (ParseException e) {
								System.err
										.println("ERRO, não foi possivel atualizar o produto com as informações passadas.");
								menuInicial(4);
							}
							Produto atualizado = produtoService.update(produto, itemId);

							System.out.println("O produto " + atualizado + " Foi alterado com sucesso");
							menuInicial(4);
						}
						if (opt == 3) {
							List<Produto> produtos = produtoService.listAll();
							System.out.println("Informe o ID do produto que deseja deletar.");
							for (int i = 0; i < produtos.size(); i++) {
								System.out.println("Produto: " + produtos.get(i).toString());
							}
							Long itemId = scanner.nextLong();

		
							 produtoService.remove(itemId);

							System.out.println("O produto foi deletado com sucesso");
							menuInicial(4);
						}

						if (opt == 4) {
							Operador operador = UtilMenu.menuCadastrarOperador(scanner);
							Operador operadorCriado = operadorService.create(operador);
							System.out.println("Foi cadastrado o " + operadorCriado.toString());
							menuInicial(4);

						}			
						System.err.println("Usuario não encontrado! Devido a sugurança do sistema, estamos fechando o sistema.");
						menuInicial(5);
					}
					}
				}
				if (opcao == 5) {
					System.out.println("Fechando a aplicação...");
					System.exit(0);

				}

			}
		}
