package ListasEncadeadas;

import enums.CategoriaQuarto;
import java.util.Scanner;

public class ListaEncadeadaQuarto {
    private NoQuarto head;

    private static class NoQuarto {
        int numeroQuarto;
        CategoriaQuarto categoria;
        NoQuarto proximo;

        public NoQuarto(int numeroQuarto, CategoriaQuarto categoria) {
            this.numeroQuarto = numeroQuarto;
            this.categoria = categoria;
            this.proximo = null;
        }
    }
    
    public ListaEncadeadaQuarto() {
    	setarQuartos(1, CategoriaQuarto.ECONOMICO);
    	setarQuartos(2, CategoriaQuarto.ECONOMICO);
    	setarQuartos(3, CategoriaQuarto.LUXO);
    	setarQuartos(4, CategoriaQuarto.LUXO);
    	setarQuartos(5, CategoriaQuarto.SUPER_LUXO);
    	setarQuartos(6, CategoriaQuarto.SUPER_LUXO);
    }
    
    Scanner scanner = new Scanner(System.in);

    public void adicionarQuarto() {
    	
        System.out.print("Digite o número do quarto: ");
        int numeroQuarto = scanner.nextInt();
        scanner.nextLine(); 
        
        System.out.println("Selecione a categoria do quarto:");
        System.out.println("1. Econômico");
        System.out.println("2. Luxo");
        System.out.println("3. Super Luxo");
        System.out.print("Escolha uma opção: ");
        int categoriaOpcao = scanner.nextInt();
        scanner.nextLine(); 

        CategoriaQuarto categoria;
        switch (categoriaOpcao) {
            case 1:
                categoria = CategoriaQuarto.ECONOMICO;
                break;
            case 2:
                categoria = CategoriaQuarto.LUXO;
                break;
            case 3:
                categoria = CategoriaQuarto.SUPER_LUXO;
                break;
            default:
                System.out.println("Opção inválida! Quarto não adicionado.");
                return; 
        }

        NoQuarto novoNo = new NoQuarto(numeroQuarto, categoria);
        if (head == null) {
            head = novoNo;
        } else {
            NoQuarto atual = head;
            while (atual.proximo != null) {
                atual = atual.proximo;
            }
            atual.proximo = novoNo;
        }
        System.out.println("Quarto adicionado com sucesso!");
    }
    
    
    private void setarQuartos(int numeroQuarto, CategoriaQuarto categoria) {
    	NoQuarto novoNo = new NoQuarto(numeroQuarto, categoria);
        if (head == null) {
            head = novoNo;
        } else {
            NoQuarto atual = head;
            while (atual.proximo != null) {
                atual = atual.proximo;
            }
            atual.proximo = novoNo;
        }
    	
    }

    public void exibirQuartos() {
        NoQuarto atual = head;
        while (atual != null) {
            System.out.println("Número do Quarto: " + atual.numeroQuarto + " | Categoria: " + atual.categoria);
            atual = atual.proximo;
        }
    }

    public CategoriaQuarto buscarQuarto(int numeroQuarto) {
        NoQuarto atual = head;
        while (atual != null) {
            if (atual.numeroQuarto == numeroQuarto) {
                return atual.categoria;
            }
            atual = atual.proximo;
        }
        return null; 
    }

    public boolean removerQuarto(int numeroQuarto) {
        if (head == null) {
            return false;
        }
        if (head.numeroQuarto == numeroQuarto) {
            head = head.proximo;
            return true;
        }
        NoQuarto atual = head;
        while (atual.proximo != null && atual.proximo.numeroQuarto != numeroQuarto) {
            atual = atual.proximo;
        }
        if (atual.proximo == null) {
            return false; 
        }
        atual.proximo = atual.proximo.proximo;
        return true; 
    }


	public int[] obterTodosQuartos() {
		// TODO Auto-generated method stub
		return null;
	}


	public int getQuantidade() {
		// TODO Auto-generated method stub
		return 0;
	}
}
