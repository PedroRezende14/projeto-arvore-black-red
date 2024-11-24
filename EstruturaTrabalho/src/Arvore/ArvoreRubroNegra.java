package Arvore;

import enums.CategoriaQuarto;
import enums.Cor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ListasEncadeadas.ListaEncadeadaCliente;
import ListasEncadeadas.ListaEncadeadaQuarto;

public class ArvoreRubroNegra {
    
    
    private class NodoReserva {
        String cpfCliente;
        int numeroQuarto;
        CategoriaQuarto categoriaQuarto;
        LocalDate dataCheckIn;
        LocalDate dataCheckOut;
        Cor cor;
        NodoReserva esquerdo, direito, pai;

        public NodoReserva(String cpfCliente, int numeroQuarto, CategoriaQuarto categoriaQuarto, LocalDate dataCheckIn, LocalDate dataCheckOut) {
            this.cpfCliente = cpfCliente;
            this.numeroQuarto = numeroQuarto;
            this.categoriaQuarto = categoriaQuarto;
            this.dataCheckIn = dataCheckIn;
            this.dataCheckOut = dataCheckOut;
            this.cor = Cor.VERMELHO;
            this.esquerdo = this.direito = this.pai = null;
        }
    }
    
    private NodoReserva raiz;
    private ListaEncadeadaCliente listaClientes;
    private ListaEncadeadaQuarto listaQuartos;
    private ArvoreRubroNegra historico;
    
   
    
    public ArvoreRubroNegra(ListaEncadeadaCliente listaClientes, ListaEncadeadaQuarto listaQuartos) {
        this.raiz = null;
        this.listaClientes = listaClientes;
        this.listaQuartos = listaQuartos;
    }
    
    private NodoReserva buscarReserva(NodoReserva nodo, String cpfCliente, int numeroQuarto) {
        if (nodo == null) {
            return null;
        }
        if (nodo.cpfCliente.equals(cpfCliente) && nodo.numeroQuarto == numeroQuarto) {
            return nodo;
        }
        NodoReserva esquerda = buscarReserva(nodo.esquerdo, cpfCliente, numeroQuarto);
        return (esquerda != null) ? esquerda : buscarReserva(nodo.direito, cpfCliente, numeroQuarto);
    }
    
    public boolean cancelarReserva(String cpfCliente, int numeroQuarto) {
    	
    	NodoReserva reserva = buscarReserva(raiz, cpfCliente, numeroQuarto);
        if (reserva == null) {
            System.out.println("Reserva não encontrada.");
            return false;
        }
        LocalDate dataCheckIn = reserva.dataCheckIn;
        LocalDate dataCheckOut = reserva.dataCheckOut;
        
        removerNodo(reserva);
        
        if (historico == null) {
            historico = new ArvoreRubroNegra(listaClientes, listaQuartos);
        }
        historico.adicionarReserva(cpfCliente, numeroQuarto, dataCheckIn, dataCheckOut);
        
        System.out.println("Reserva cancelada e adicionada ao histórico.");
        return true;
        
    }	
    
    public void historicoCancelamento() {
    	historico.mostrarReservas();
    }
        
    	
    
    private void removerNodo(NodoReserva nodo) {
        if (nodo == null) {
            System.out.println("O nó a ser removido não existe.");
            return;
        }

        NodoReserva substituto = nodo;
        Cor corOriginal = substituto.cor;

        NodoReserva nodoDeCorrecao;
        if (nodo.esquerdo == null) {
            nodoDeCorrecao = nodo.direito;
            transplantar(nodo, nodo.direito);
        } else if (nodo.direito == null) {
            nodoDeCorrecao = nodo.esquerdo;
            transplantar(nodo, nodo.esquerdo);
        } else {
            substituto = minimo(nodo.direito); 
            corOriginal = substituto.cor;
            nodoDeCorrecao = substituto.direito;

            if (substituto.pai == nodo) {
                if (nodoDeCorrecao != null) {
                    nodoDeCorrecao.pai = substituto;
                }
            } else {
                transplantar(substituto, substituto.direito);
                substituto.direito = nodo.direito;
                substituto.direito.pai = substituto;
            }

            transplantar(nodo, substituto);
            substituto.esquerdo = nodo.esquerdo;
            substituto.esquerdo.pai = substituto;
            substituto.cor = nodo.cor;
        }

        if (corOriginal == Cor.PRETO) {
            corrigirRemocao(nodoDeCorrecao);
        }
    }

    // Método auxiliar para substituir um nó por outro
    private void transplantar(NodoReserva u, NodoReserva v) {
        if (u.pai == null) {
            raiz = v;
        } else if (u == u.pai.esquerdo) {
            u.pai.esquerdo = v;
        } else {
            u.pai.direito = v;
        }
        if (v != null) {
            v.pai = u.pai;
        }
    }

    // Encontra o nó mínimo a partir de um dado nó
    private NodoReserva minimo(NodoReserva nodo) {
        while (nodo.esquerdo != null) {
            nodo = nodo.esquerdo;
        }
        return nodo;
    }

    private void corrigirRemocao(NodoReserva nodo) {
        while (nodo != raiz && (nodo == null || nodo.cor == Cor.PRETO)) {
            if (nodo == nodo.pai.esquerdo) {
                NodoReserva irmao = nodo.pai.direito;
                if (irmao.cor == Cor.VERMELHO) {
                    irmao.cor = Cor.PRETO;
                    nodo.pai.cor = Cor.VERMELHO;
                    rotacaoEsquerda(nodo.pai);
                    irmao = nodo.pai.direito;
                }

                if ((irmao.esquerdo == null || irmao.esquerdo.cor == Cor.PRETO) &&
                    (irmao.direito == null || irmao.direito.cor == Cor.PRETO)) {
                    irmao.cor = Cor.VERMELHO;
                    nodo = nodo.pai;
                } else {
                    if (irmao.direito == null || irmao.direito.cor == Cor.PRETO) {
                        if (irmao.esquerdo != null) {
                            irmao.esquerdo.cor = Cor.PRETO;
                        }
                        irmao.cor = Cor.VERMELHO;
                        rotacaoDireita(irmao);
                        irmao = nodo.pai.direito;
                    }

                    irmao.cor = nodo.pai.cor;
                    nodo.pai.cor = Cor.PRETO;
                    if (irmao.direito != null) {
                        irmao.direito.cor = Cor.PRETO;
                    }
                    rotacaoEsquerda(nodo.pai);
                    nodo = raiz;
                }
            } else {
                NodoReserva irmao = nodo.pai.esquerdo;
                if (irmao.cor == Cor.VERMELHO) {
                    irmao.cor = Cor.PRETO;
                    nodo.pai.cor = Cor.VERMELHO;
                    rotacaoDireita(nodo.pai);
                    irmao = nodo.pai.esquerdo;
                }

                if ((irmao.direito == null || irmao.direito.cor == Cor.PRETO) &&
                    (irmao.esquerdo == null || irmao.esquerdo.cor == Cor.PRETO)) {
                    irmao.cor = Cor.VERMELHO;
                    nodo = nodo.pai;
                } else {
                    if (irmao.esquerdo == null || irmao.esquerdo.cor == Cor.PRETO) {
                        if (irmao.direito != null) {
                            irmao.direito.cor = Cor.PRETO;
                        }
                        irmao.cor = Cor.VERMELHO;
                        rotacaoEsquerda(irmao);
                        irmao = nodo.pai.esquerdo;
                    }

                    irmao.cor = nodo.pai.cor;
                    nodo.pai.cor = Cor.PRETO;
                    if (irmao.esquerdo != null) {
                        irmao.esquerdo.cor = Cor.PRETO;
                    }
                    rotacaoDireita(nodo.pai);
                    nodo = raiz;
                }
            }
        }
        if (nodo != null) {
            nodo.cor = Cor.PRETO;
        }
    }
 
    public boolean adicionarReserva(String cpfCliente, int numeroQuarto, LocalDate dataCheckIn, LocalDate dataCheckOut) {
        if (listaClientes.buscar(cpfCliente) == null) {
            System.out.println("Cliente não encontrado.");
            return false;
        }
        
        CategoriaQuarto categoriaQuarto = listaQuartos.buscarQuarto(numeroQuarto);
        if (categoriaQuarto == null) {
            System.out.println("Quarto não encontrado.");
            return false;
        }
        
        if (verificarConflitoReserva(numeroQuarto, dataCheckIn, dataCheckOut, raiz)) {
            System.out.println("Conflito de reserva: O quarto já está reservado para o período solicitado.");
            return false;
        }
        
        
        NodoReserva novaReserva = new NodoReserva(cpfCliente, numeroQuarto, categoriaQuarto, dataCheckIn, dataCheckOut);
        raiz = inserirNodo(raiz, novaReserva);
        corrigirInsercao(novaReserva);
        System.out.println("Reserva adicionada com sucesso.");
        return true;
    }
    
    private NodoReserva inserirNodo(NodoReserva atual, NodoReserva novoNodo) {
        if (atual == null) {
            return novoNodo;
        }
        if (novoNodo.dataCheckIn.isBefore(atual.dataCheckIn)) {
            atual.esquerdo = inserirNodo(atual.esquerdo, novoNodo);
            atual.esquerdo.pai = atual;
        } else {
            atual.direito = inserirNodo(atual.direito, novoNodo);
            atual.direito.pai = atual;
        }
        return atual;
    }
    
    private void corrigirInsercao(NodoReserva nodo) {
        NodoReserva pai, avo;

        while (nodo != raiz && nodo.pai.cor == Cor.VERMELHO) {
            pai = nodo.pai;
            avo = pai.pai;

            if (pai == avo.esquerdo) {
                NodoReserva tio = avo.direito;
                if (tio != null && tio.cor == Cor.VERMELHO) {
                    pai.cor = Cor.PRETO;
                    tio.cor = Cor.PRETO;
                    avo.cor = Cor.VERMELHO;
                    nodo = avo;
                } else {
                    if (nodo == pai.direito) {
                        nodo = pai;
                        rotacaoEsquerda(nodo);
                    }
                    pai.cor = Cor.PRETO;
                    avo.cor = Cor.VERMELHO;
                    rotacaoDireita(avo);
                }
            } else {
                NodoReserva tio = avo.esquerdo;
                if (tio != null && tio.cor == Cor.VERMELHO) {
                    pai.cor = Cor.PRETO;
                    tio.cor = Cor.PRETO;
                    avo.cor = Cor.VERMELHO;
                    nodo = avo;
                } else {
                    if (nodo == pai.esquerdo) {
                        nodo = pai;
                        rotacaoDireita(nodo);
                    }
                    pai.cor = Cor.PRETO;
                    avo.cor = Cor.VERMELHO;
                    rotacaoEsquerda(avo);
                }
            }
        }
        raiz.cor = Cor.PRETO;
    }
    
    private void rotacaoEsquerda(NodoReserva nodo) {
        NodoReserva novoNodo = nodo.direito;
        nodo.direito = novoNodo.esquerdo;

        if (novoNodo.esquerdo != null) {
            novoNodo.esquerdo.pai = nodo;
        }
        novoNodo.pai = nodo.pai;

        if (nodo.pai == null) {
            raiz = novoNodo;
        } else if (nodo == nodo.pai.esquerdo) {
            nodo.pai.esquerdo = novoNodo;
        } else {
            nodo.pai.direito = novoNodo;
        }
        novoNodo.esquerdo = nodo;
        nodo.pai = novoNodo;
    }

    private void rotacaoDireita(NodoReserva nodo) {
        NodoReserva novoNodo = nodo.esquerdo;
        nodo.esquerdo = novoNodo.direito;

        if (novoNodo.direito != null) {
            novoNodo.direito.pai = nodo;
        }
        novoNodo.pai = nodo.pai;

        if (nodo.pai == null) {
            raiz = novoNodo;
        } else if (nodo == nodo.pai.direito) {
            nodo.pai.direito = novoNodo;
        } else {
            nodo.pai.esquerdo = novoNodo;
        }
        novoNodo.direito = nodo;
        nodo.pai = novoNodo;
    }
    
    public void mostrarReservas() {
        if (raiz == null) {
            System.out.println("Nenhuma reserva registrada.");
        } else {
            mostrarArvore(raiz, "", true);
        }
    }

    private void mostrarArvore(NodoReserva nodo, String prefixo, boolean isFilhoDireito) {
        if (nodo != null) {
            System.out.println(prefixo + (isFilhoDireito ? "└── " : "├── ") + "Reserva [Cliente CPF: " + nodo.cpfCliente 
                               + ", Quarto: " + nodo.numeroQuarto + ", Categoria: " + nodo.categoriaQuarto 
                               + ", Check-in: " + nodo.dataCheckIn + ", Check-out: " + nodo.dataCheckOut + "] (" + nodo.cor + ")");
            mostrarArvore(nodo.direito, prefixo + (isFilhoDireito ? "    " : "│   "), false);
            mostrarArvore(nodo.esquerdo, prefixo + (isFilhoDireito ? "    " : "│   "), true);
        }
    }
    
    
    private boolean verificarConflitoReserva(int numeroQuarto, LocalDate dataCheckIn, LocalDate dataCheckOut, NodoReserva nodo) {
        if (nodo == null) {
            return false;
        }
        
        if (nodo.numeroQuarto == numeroQuarto) {
            boolean conflito = !dataCheckOut.isBefore(nodo.dataCheckIn) && !dataCheckIn.isAfter(nodo.dataCheckOut);
            if (conflito) {
                return true;
            }
        }
        
        return verificarConflitoReserva(numeroQuarto, dataCheckIn, dataCheckOut, nodo.esquerdo) ||
               verificarConflitoReserva(numeroQuarto, dataCheckIn, dataCheckOut, nodo.direito);
    }
    
    
    
    public String consultarReservaPorCliente(String cpfCliente) {
        NodoReserva reserva = buscarReservaPorCPF(raiz, cpfCliente);
        if (reserva == null) {
            System.out.println("Nenhuma reserva encontrada para o cliente com CPF: " + cpfCliente);
            return null;
        }
        String infoReserva = "Reserva encontrada: [Cliente CPF: " + reserva.cpfCliente +
                             ", Quarto: " + reserva.numeroQuarto + ", Categoria: " + reserva.categoriaQuarto +
                             ", Check-in: " + reserva.dataCheckIn + ", Check-out: " + reserva.dataCheckOut + "]";
        System.out.println(infoReserva);
        return infoReserva;
    }
    
    private NodoReserva buscarReservaPorCPF(NodoReserva nodo, String cpfCliente) {
        if (nodo == null) {
            return null;
        }
        if (nodo.cpfCliente.equals(cpfCliente)) {
            return nodo;
        }
        NodoReserva esquerda = buscarReservaPorCPF(nodo.esquerdo, cpfCliente);
        return (esquerda != null) ? esquerda : buscarReservaPorCPF(nodo.direito, cpfCliente);
    }
    
    public List<Integer> consultarQuartosDisponiveis(LocalDate dataCheckIn, LocalDate dataCheckOut) {
        List<Integer> quartosDisponiveis = new ArrayList<>();
        verificarDisponibilidade(raiz, dataCheckIn, dataCheckOut, quartosDisponiveis);
        return quartosDisponiveis;
    }

    private void verificarDisponibilidade(NodoReserva nodo, LocalDate dataCheckIn, LocalDate dataCheckOut, List<Integer> quartosDisponiveis) {
        if (nodo == null) {
            return;
        }

        boolean conflito = !dataCheckOut.isBefore(nodo.dataCheckIn) && !dataCheckIn.isAfter(nodo.dataCheckOut);
        if (!conflito) {
  
            if (!quartosDisponiveis.contains(nodo.numeroQuarto)) {
                quartosDisponiveis.add(nodo.numeroQuarto);
            }
        }

        verificarDisponibilidade(nodo.esquerdo, dataCheckIn, dataCheckOut, quartosDisponiveis);
        verificarDisponibilidade(nodo.direito, dataCheckIn, dataCheckOut, quartosDisponiveis);
    }

	    
	    public List<String> listarReservasPorDataCheckIn() {
	        List<String> reservasOrdenadas = new ArrayList<>();
	        percorrerEmOrdem(raiz, reservasOrdenadas);
	        if (reservasOrdenadas.isEmpty()) {
	            System.out.println("Nenhuma reserva registrada.");
	        } else {
	            System.out.println("Reservas ordenadas por data de check-in:");
	            reservasOrdenadas.forEach(System.out::println);
	        }
	        return reservasOrdenadas;
	    }
	
	    private void percorrerEmOrdem(NodoReserva nodo, List<String> reservas) {
	        if (nodo != null) {
	            percorrerEmOrdem(nodo.esquerdo, reservas);
	            reservas.add(formatarReserva(nodo));
	            percorrerEmOrdem(nodo.direito, reservas);
	        }
	    }
	
	    private String formatarReserva(NodoReserva nodo) {
	        return "Reserva [Cliente CPF: " + nodo.cpfCliente +
	               ", Quarto: " + nodo.numeroQuarto +
	               ", Categoria: " + nodo.categoriaQuarto +
	               ", Check-in: " + nodo.dataCheckIn +
	               ", Check-out: " + nodo.dataCheckOut + "]";
	    }
	    
	    
	    public double calcularTaxaOcupacao(LocalDate inicio, LocalDate fim) {
	        int totalReservas = contarReservasPorPeriodo(raiz, inicio, fim);
	        int totalQuartos = listaQuartos.getQuantidade();
	        long diasPeriodo = inicio.until(fim).getDays() + 1; 
	        return (totalReservas / (double) (totalQuartos * diasPeriodo)) * 100;
	    }

	    private int contarReservasPorPeriodo(NodoReserva nodo, LocalDate inicio, LocalDate fim) {
	        if (nodo == null) return 0;

	        boolean dentroPeriodo = !nodo.dataCheckOut.isBefore(inicio) && !nodo.dataCheckIn.isAfter(fim);
	        int contadorAtual = dentroPeriodo ? 1 : 0;

	        return contadorAtual +
	               contarReservasPorPeriodo(nodo.esquerdo, inicio, fim) +
	               contarReservasPorPeriodo(nodo.direito, inicio, fim);
	    }

	  
	    public void quartosMaisEMenosReservados() {
	        Map<Integer, Integer> contagemReservas = contarReservasPorQuarto(raiz, new HashMap<>());
	        
	        int maisReservado = -1, menosReservado = -1;
	        int maxReservas = Integer.MIN_VALUE, minReservas = Integer.MAX_VALUE;

	        for (Map.Entry<Integer, Integer> entry : contagemReservas.entrySet()) {
	            if (entry.getValue() > maxReservas) {
	                maxReservas = entry.getValue();
	                maisReservado = entry.getKey();
	            }
	            if (entry.getValue() < minReservas) {
	                minReservas = entry.getValue();
	                menosReservado = entry.getKey();
	            }
	        }

	        System.out.println("Quarto mais reservado: " + maisReservado + " com " + maxReservas + " reservas.");
	        System.out.println("Quarto menos reservado: " + menosReservado + " com " + minReservas + " reservas.");
	    }

	    private Map<Integer, Integer> contarReservasPorQuarto(NodoReserva nodo, Map<Integer, Integer> contagem) {
	        if (nodo == null) return contagem;

	        contagem.put(nodo.numeroQuarto, contagem.getOrDefault(nodo.numeroQuarto, 0) + 1);

	        contarReservasPorQuarto(nodo.esquerdo, contagem);
	        contarReservasPorQuarto(nodo.direito, contagem);

	        return contagem;
	    }

	    public int contarCancelamentos(LocalDate inicio, LocalDate fim) {
	        return contarCancelamentosNoPeriodo(historico.raiz, inicio, fim);
	    }

	    private int contarCancelamentosNoPeriodo(NodoReserva nodo, LocalDate inicio, LocalDate fim) {
	        if (nodo == null) return 0;

	        boolean dentroPeriodo = !nodo.dataCheckOut.isBefore(inicio) && !nodo.dataCheckIn.isAfter(fim);
	        int contadorAtual = dentroPeriodo ? 1 : 0;

	        return contadorAtual +
	               contarCancelamentosNoPeriodo(nodo.esquerdo, inicio, fim) +
	               contarCancelamentosNoPeriodo(nodo.direito, inicio, fim);
	    }

    
}




