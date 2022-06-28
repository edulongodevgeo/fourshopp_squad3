package br.com.fourshopp.Util;

import br.com.fourshopp.entities.*;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import org.apache.juli.DateFormatCache;

public class UtilMenu {

	private static double valorTotalCompra;

	private static Scanner scanner;

	// SOU CLIENTE
	public static Cliente menuCadastroCliente(Scanner scanner) {
		
		scanner.nextLine();
		System.out.println("Insira seu nome: ");
		String nome = scanner.nextLine();

		System.out.println("Insira seu email: ");
		String email = scanner.nextLine();

		System.out.println("Insira seu celular: ");
		String celular = scanner.nextLine();

		System.out.println("Insira uma senha: ");
		String password = scanner.nextLine();
		while (password.length() < 8) {
			if (password.length() < 8) {
				System.out.println("A senha deve conter 8 caracteres ou mais. Tente novamente! ");
				System.out.println("Insira uma senha: ");
				password = scanner.nextLine();
			} else {
				scanner.close();
			}
		}

		System.out.println("Insira seu cpf: ");
		String cpf = scanner.nextLine();
		while (cpf.length() != 11) {
			if (cpf.length() < 11) {
				System.out.println("O CPF esta Incorreto. Tente novamente! ");
			} else {
				System.out.println("O CPF esta Incorreto. Tente novamente");
			}
			System.out.println("O CPF deve ter tamanho de 11 caracteres");
			System.out.println("Insira o CPF:");
			cpf = scanner.nextLine();
		}

		System.out.println("Insira sua rua: ");
		String rua = scanner.nextLine();

		System.out.println("Insira seu cidade: ");
		String cidade = scanner.nextLine();

		System.out.println("Insira seu bairro: ");
		String bairro = scanner.nextLine();

		System.out.println("Insira seu numero: ");
		int numero = scanner.nextInt();
		scanner.nextLine();
		System.out.println("Insira sua data de nascimento (dd/MM/yyyy): ");
		String dataNascimento = scanner.nextLine();

		Endereco endereco = new Endereco(rua, cidade, bairro, numero);
		Cliente cliente = new Cliente(nome, email, celular, password, cpf, endereco, new Date());

		return cliente;

	}

	public static int menuSetor(Scanner scanner) {
		System.out.println("Digite a opção desejada: " + "\n1- MERCEARIA \n2- BAZAR \n3- ELETRÔNICOS");
		int opcao = scanner.nextInt();
		return opcao;
	}

	public static void gerarCupomFiscal(Cliente cliente) throws IOException {
		Double totalMercearia = 0.0;
		List<Produto> produtos = cliente.getProdutoList();
		for (Produto item: produtos) {
			if (item.getSetor() == Setor.MERCEARIA.getCd());{
				totalMercearia += item.getPreco()*item.getQuantidade();
			}
		}
		for (Produto item: produtos) {
			if (item.getSetor() == Setor.MERCEARIA.getCd());{
				if(totalMercearia >= 500.0){
					item.setPreco(item.getPreco()*0.90);
				}
			}
		}
		Document document = new Document(PageSize.A4);
		File file = new File("CupomFiscal_" + new Random().nextInt() + ".pdf");
		String absolutePath = file.getAbsolutePath();
		PdfWriter.getInstance(document, new FileOutputStream(absolutePath));
		document.open();

		Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fontTitle.setSize(18);

		Image image1 = Image.getInstance(
				"C:\\Users\\pbrito\\Documents\\GitHub\\fourshopp_squad3\\FourShopp-desafioTestsDocumentacao\\src\\main\\java\\br\\com\\fourshopp\\service\\fourshopp.png");
		image1.scaleAbsolute(140f, 140f);
		image1.setAlignment(Element.ALIGN_CENTER);

		Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
		fontParagraph.setSize(12);

		Font total = FontFactory.getFont(FontFactory.HELVETICA);
		total.setSize(12);
		total.setColor(Color.blue);

		Font header = FontFactory.getFont(FontFactory.HELVETICA);
		header.setSize(12);
		header.setFamily("bold");

		document.add(image1);

		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		ListItem item1 = new ListItem();
		produtos.forEach(produto -> {

			System.out.println(produto.toString());
			Chunk nome = new Chunk("\n" + produto.getNome() + " (" + produto.getQuantidade() + ") \nPreço unidade : R$"
					+ df.format(produto.getPreco() / produto.getQuantidade()));
			Phrase frase = new Phrase();
			frase.add(nome);

			Paragraph x = new Paragraph(frase);

			String preco = "............................................................................................................................R$ "
					+ df.format(produto.getPreco());
			Paragraph y = new Paragraph(preco);
			y.setAlignment(Paragraph.ALIGN_RIGHT);
			item1.add(x);
			item1.add(y);

			valorTotalCompra = valorTotalCompra + produto.getPreco();
		});

		Paragraph paragraph = new Paragraph("\n\nTOTAL: R$" + df.format(valorTotalCompra), total);
		paragraph.setAlignment(Paragraph.ALIGN_RIGHT);

		document.add(item1);
		document.add(paragraph);

		document.close();
	}

	public static Funcionario cadastrarFuncionario(Scanner scanner) throws ParseException {
		scanner.nextLine();
		System.out.println("Insira seu nome: ");
		String nome = scanner.nextLine();

		System.out.println("Insira seu email: ");
		String email = scanner.nextLine();

		System.out.println("Insira seu celular: ");
		String celular = scanner.nextLine();

		System.out.println("Insira uma senha: ");
		String password = scanner.nextLine();
		while (password.length() < 8) {
			if (password.length() < 8) {
				System.out.println("A senha deve conter 8 caracteres ou mais. Tente novamente! ");
				System.out.println("Insira uma senha: ");
				password = scanner.nextLine();
			} else {
				scanner.close();
			}
		}

		System.out.println("Insira seu cpf: ");
		String cpf = scanner.nextLine();
		while (cpf.length() != 11) {
			if (cpf.length() < 11) {
				System.out.println("O CPF esta Incorreto. Tente novamente! ");
			} else {
				System.out.println("O CPF esta Incorreto. Tente novamente");
			}
			System.out.println("O CPF deve ter tamanho de 11 caracteres");
			System.out.println("Insira o CPF:");
			cpf = scanner.nextLine();
		}

		System.out.println("Insira sua rua: ");
		String rua = scanner.nextLine();

		System.out.println("Insira seu cidade: ");
		String cidade = scanner.nextLine();

		System.out.println("Insira seu bairro: ");
		String bairro = scanner.nextLine();

		System.out.println("Insira seu numero: ");
		int numero = scanner.nextInt();
		scanner.nextLine();
		System.out.println("Data de contratação: ");
		String hireDate = scanner.nextLine();

		System.out.println("Insira o salário CLT bruto: ");
		double salario = scanner.nextDouble();
		scanner.nextLine();
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		Date data = formato.parse(hireDate);

		Endereco endereco = new Endereco(rua, cidade, bairro, numero);
		return new Funcionario(nome, email, celular, password, cpf, endereco, data, Cargo.CHEFE_SECAO, Setor.MERCEARIA,
				salario, new ArrayList<>(), new ArrayList<>());

	}

	public static Operador menuCadastrarOperador(Scanner scanner) throws ParseException {
		
		scanner.nextLine();
		System.out.println("Insira seu nome: ");
		String nome = scanner.nextLine();

		System.out.println("Insira seu email: ");
		String email = scanner.nextLine();

		System.out.println("Insira seu celular: ");
		String celular = scanner.nextLine();

		System.out.println("Insira uma senha: ");
		String password = scanner.nextLine();
		
		while (password.length() < 8) {
			if (password.length() < 8) {
				System.out.println("A senha deve conter 8 caracteres ou mais. Tente novamente! ");
				System.out.println("Insira uma senha: ");
				password = scanner.nextLine();
			} else {
				scanner.close();
			}
		}
		
		System.out.println("Insira seu cpf: ");
		String cpf = scanner.nextLine();
		while (cpf.length() != 11) {
			if (cpf.length() < 11) {
				System.out.println("O CPF esta Incorreto. Tente novamente! ");
			} else {
				System.out.println("O CPF esta Incorreto. Tente novamente");
			}
			System.out.println("O CPF deve ter tamanho de 11 caracteres");
			System.out.println("Insira o CPF:");
			cpf = scanner.nextLine();
		}

		System.out.println("Insira sua rua: ");
		String rua = scanner.nextLine();

		System.out.println("Insira seu cidade: ");
		String cidade = scanner.nextLine();

		System.out.println("Insira seu bairro: ");
		String bairro = scanner.nextLine();
		

		System.out.println("Insira seu numero: ");
		int numero = scanner.nextInt();
		scanner.nextLine();
		System.out.println("Data de contratação: ");
		String hireDate = scanner.nextLine();

		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		Date data = formato.parse(hireDate);

		System.out.println("Insira o salário CLT bruto: ");
		double salario = scanner.nextDouble();

		return new Operador(nome, email, celular, password, cpf, new Endereco(), data, Cargo.OPERADOR, salario);
	}
	
	//METODO CADASTRAR CHEFE
	public static Chefe menuCadastrarChefe(Scanner scanner) throws ParseException {
		
		scanner.nextLine();
		System.out.println("Insira seu nome: ");
		String nome = scanner.nextLine();

		System.out.println("Insira seu email: ");
		String email = scanner.nextLine();

		System.out.println("Insira seu celular: ");
		String celular = scanner.nextLine();

		System.out.println("Insira uma senha: ");
		String password = scanner.nextLine();
		
		while (password.length() < 8) {
			if (password.length() < 8) {
				System.out.println("A senha deve conter 8 caracteres ou mais. Tente novamente! ");
				System.out.println("Insira uma senha: ");
				password = scanner.nextLine();
			} else {
				scanner.close();
			}
		}
		
		System.out.println("Insira seu cpf: ");
		String cpf = scanner.nextLine();
		while (cpf.length() != 11) {
			if (cpf.length() < 11) {
				System.out.println("O CPF esta Incorreto. Tente novamente! ");
			} else {
				System.out.println("O CPF esta Incorreto. Tente novamente");
			}
			System.out.println("O CPF deve ter tamanho de 11 caracteres");
			System.out.println("Insira o CPF:");
			cpf = scanner.nextLine();
		}

		System.out.println("Insira sua rua: ");
		String rua = scanner.nextLine();

		System.out.println("Insira seu cidade: ");
		String cidade = scanner.nextLine();

		System.out.println("Insira seu bairro: ");
		String bairro = scanner.nextLine();
		

		System.out.println("Insira seu numero: ");
		int numero = scanner.nextInt();
		scanner.nextLine();
		System.out.println("Data de contratação: ");
		String hireDate = scanner.nextLine();

		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		Date data = formato.parse(hireDate);

		System.out.println("Insira o salário CLT bruto: ");
		double salario = scanner.nextDouble();

		Endereco endereco = new Endereco(rua, cidade, bairro, numero);
		
		return new Chefe(nome, email, celular, password, cpf, endereco, data, Cargo.CHEFE_SECAO, Setor.BAZAR, salario);
		
		//Se der certo, voltar uma mensagem de positivo, e retornar para o menu.
	}

	public static Produto menuCadastrarProduto(Scanner scanner) throws ParseException {
		scanner.nextLine();
		System.out.println("Nome do produto: ");
		String nome = scanner.nextLine();

		System.out.println("Setor: ");
		int setor = scanner.nextInt(); // Tem que retornar como SETOR (ENUM)

		System.out.println("Quantidade: ");
		Integer quantidade = scanner.nextInt();

		System.out.println("Preço: R$ ");
		Double preco = scanner.nextDouble();
		scanner.nextLine();
		System.out.println("Data de vencimento: ");
		String dataVencimento = scanner.nextLine();

		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		Date dataVencimentoFormatada = formato.parse(dataVencimento);

		Produto produto = new Produto(nome, quantidade, preco, setor, dataVencimentoFormatada);
		return new Produto(nome, quantidade, preco, setor, dataVencimentoFormatada);
	}
}
