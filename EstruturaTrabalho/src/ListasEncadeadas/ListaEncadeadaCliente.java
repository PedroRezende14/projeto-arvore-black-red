package ListasEncadeadas;

import java.util.Scanner;

public class ListaEncadeadaCliente {
    
    private Node head;
    private Scanner scanner = new Scanner(System.in);

    public class Node {
        String nome;
        String cpf;
        Node next;

        public Node(String nome, String cpf) {
            this.nome = nome;
            this.cpf = cpf;
            this.next = null;
        }
    }

    public void adicionar(String nome, String cpf) {
    	
    	if (buscar(cpf) == null) { 
	        Node novoNo = new Node(nome, cpf);
	        if (head == null) {
	            head = novoNo;
	        } else {
	            Node atual = head;
	            while (atual.next != null) {
	                atual = atual.next;
	            }
	            atual.next = novoNo;
	        }
	        System.out.println("Cliente adicionado com sucesso.");
	    	}
    	}
    
    public void remover(String cpf) {
        if (head == null) {
            System.out.println("Lista vazia.");
            return;
        }

        if (head.cpf.equals(cpf)) {
            head = head.next;
            System.out.println("Cliente removido.");
            return;
        }

        Node atual = head;
        Node anterior = null;
        while (atual != null && !atual.cpf.equals(cpf)) {
            anterior = atual;
            atual = atual.next;
        }

        if (atual == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        anterior.next = atual.next;
        System.out.println("Cliente removido.");
    }

    public Node buscar(String cpf) {
        Node atual = head;
        while (atual != null) {
            if (atual.cpf.equals(cpf)) {
                return atual;
            }
            atual = atual.next;
        }
        return null; 
        }
  
    public void exibir() {
        if (head == null) {
            System.out.println("Lista vazia.");
            return;
        }

        Node atual = head;
        while (atual != null) {
            System.out.println("Nome: " + atual.nome + ", CPF: " + atual.cpf);
            atual = atual.next;
        }
    }
}
