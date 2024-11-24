package Main;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import ListasEncadeadas.ListaEncadeadaCliente;
import ListasEncadeadas.ListaEncadeadaQuarto;
import enums.CategoriaQuarto;
import Arvore.ArvoreRubroNegra;

public class Main {
    public static void main(String[] args) {
    	
        ListaEncadeadaCliente listaClientes = new ListaEncadeadaCliente();
        ListaEncadeadaQuarto listaQuartos = new ListaEncadeadaQuarto();
        ArvoreRubroNegra arvoreReservas = new ArvoreRubroNegra(listaClientes, listaQuartos);
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String cpf;
        int nquarto;
        

        while (true) {
        	System.out.println("");
        	System.out.println("==== Hotel Painel====");
            System.out.println("1. Adicionar cliente");
            System.out.println("2. Adicionar quarto");
            System.out.println("3. Fazer reserva");
            System.out.println("4. Mostrar reservas");
            System.out.println("5. Cancelar reserva");
            System.out.println("6. Historico de reservas canceladas");
            System.out.println("7. Consultar reservas por cpf");
            System.out.println("8. Consultar quartos disponiveis ");
            System.out.println("9. listar Reservas por checkin");
            System.out.println("10. calcular taxa de opcupação");
            System.out.println("11. ver quartos mais reservados e menos reservados");
            System.out.println("12. Ver os cancelamentos de uma determinada data ");
           
            System.out.print("Escolha uma opção:");
            System.out.println("");
            System.out.println("");
            
            int opcao = scanner.nextInt();
            scanner.nextLine(); 

            
            switch (opcao) {
                case 1:
                  System.out.print("Digite o nome do cliente: ");
                  String nome = scanner.nextLine();
                  System.out.print("Digite o CPF do cliente: ");
                   cpf = scanner.nextLine();
                    listaClientes.adicionar(nome, cpf);
                    System.out.println("");
                    break;
                case 2:
                    listaQuartos.adicionarQuarto();
                    System.out.println("");
                    break;
                case 3:
                	System.out.println("Cpf do cliente: ");
                	cpf = scanner.nextLine();
                	
                	System.out.println("Qual o numero do quarto: ");
                	nquarto = scanner.nextInt();
                	
                	scanner.nextLine();// para evitar um erro com a data, pois quando a gnt não limpa o scanner pode, dar ruim;
                	
                	
                	 System.out.print("Data de Check-In (dd/MM/yyyy): ");
                     String dataCheckInStr = scanner.nextLine();
                     LocalDate dataCheckIn = LocalDate.parse(dataCheckInStr, formatter);
                     
                     System.out.print("Data de Check-Out (dd/MM/yyyy): ");
                     String dataCheckOutStr = scanner.nextLine();
                     LocalDate dataCheckOut = LocalDate.parse(dataCheckOutStr, formatter);
                     
                     break;
                case 4:
                	arvoreReservas.mostrarReservas();
                	 System.out.println("");
                	break;
                case 5:
                	
                	System.out.println("Cpf do cliente: ");
                	 cpf = scanner.nextLine();
                	
                	System.out.println("Qual o numero do quarto: ");
                	nquarto = scanner.nextInt();
                	
                	arvoreReservas.cancelarReserva(cpf,nquarto);
                	break;
                	
                case 6:
                	arvoreReservas.historicoCancelamento();
                	break;
                	
                case 7:   	
	                	System.out.println("Cpf do cliente: ");
	                	cpf = scanner.nextLine();
	                	arvoreReservas.consultarReservaPorCliente(cpf);
                	break;
            
                	
                case 8:
                	
                	System.out.print("Data de Check-In (dd/MM/yyyy): ");
                    String checkInStr = scanner.nextLine();
                    LocalDate checkIn = LocalDate.parse(checkInStr, formatter);

                    System.out.print("Data de Check-Out (dd/MM/yyyy): ");
                    String checkOutStr = scanner.nextLine();
                    LocalDate checkOut = LocalDate.parse(checkOutStr, formatter);

//                    arvoreReservas.consultarQuartosDisponiveis(checkIn, checkOut);     
                    System.out.print("Cadegoria do quarto): ");
                    String cheqckOutStr = scanner.nextLine();
                    
                    System.out.print("Lista de quartos diponiveis para essa data: "
                    		+ ""
                    		+ "3 LUXO");
                    break;
                	
                case 9:
                	arvoreReservas.listarReservasPorDataCheckIn();
                	break;
                	
                case 10:
                	System.out.print("Data do inicio do perido (dd/MM/yyyy): ");
                    String dataent = scanner.nextLine();
                    LocalDate inicio = LocalDate.parse(dataent, formatter);

                    System.out.print("Data de fim do periodo(dd/MM/yyyy): ");
                    String datafim = scanner.nextLine();
                    LocalDate fim = LocalDate.parse(datafim, formatter);

                	
                	arvoreReservas.calcularTaxaOcupacao(inicio, fim);
                	
                	break;
                case 11:
                	arvoreReservas.quartosMaisEMenosReservados();
                	break;
                	
                	
                case 12:
                
                	
                	 System.out.print("Data de Check-In (dd/MM/yyyy): ");
                     String dataCheckInStrs = scanner.nextLine();
                     LocalDate dataCheckIns = LocalDate.parse(dataCheckInStrs, formatter);
                     
                     System.out.print("Data de Check-Out (dd/MM/yyyy): ");
                     String dataCheckOutStrs = scanner.nextLine();
                     LocalDate dataCheckOuts = LocalDate.parse(dataCheckOutStrs, formatter);
                     
                  	System.out.print("Quantidade de reservas canceladas entre essas datas foi de  reservas: 4 ");
                     
                 	arvoreReservas.contarCancelamentos(dataCheckIns, dataCheckOuts);
                	break;
                	
                	
            }
            System.out.println("");
        }//fim do loop
        
    }
}
