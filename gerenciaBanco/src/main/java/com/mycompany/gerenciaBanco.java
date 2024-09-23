package com.mycompany;


import java.util.InputMismatchException;
import java.text.NumberFormat;
import java.util.Locale;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

class ContaBancaria {
    public String nome;
    public String sobrenome;
    public String cpf;
    public double saldo;

    public ContaBancaria(String nome, String sobrenome, String cpf) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.saldo = 0.0;
    }

    public double consultaSaldo() {
        return saldo;
    }

    public void depositar(double valor) {
        saldo += valor;
        NumberFormat formatoBrasileiro = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        System.out.println("Depósito de " + formatoBrasileiro.format(valor) + " realizado com sucesso.");
    }

    public void sacar(double valor) {
        if (saldo >= valor) {
            saldo -= valor;
            NumberFormat formatoBrasileiro = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
            System.out.println("Saque de " + formatoBrasileiro.format(valor) + " realizado com sucesso!");
        } else {
            System.out.println("Saldo insuficiente para realizar o saque!");
        }
    }

    public void exibirMenu() {
        Scanner scanner = new Scanner(System.in);
        NumberFormat formatoBrasileiro = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        int opcao = 0;

        do {
            System.out.println("\n\n------MENU------");
            System.out.println("\n1. CONSULTAR MEU SALDO");
            System.out.println("\n2. REALIZAR UM DEPÓSITO");
            System.out.println("\n3. REALIZAR UM SAQUE");
            System.out.println("\n4. ENCERRAR NAVEGAÇÃO");
            System.out.println("\n\nESCOLHA UMA OPÇÃO:");

            try {
                opcao = scanner.nextInt();
                switch (opcao) {
                    case 1:
                        System.out.println("\nSaldo: " + formatoBrasileiro.format(consultaSaldo()));
                        break;
                    case 2:
                        System.out.println("\nInforme o valor que deseja depositar (use vírgula): ");
                        scanner.nextLine(); // Limpa o buffer
                        String entradaDeposito = scanner.nextLine();
                        if (entradaDeposito.contains(".")) {
                            System.out.println("Valor inválido! Utilize apenas vírgula.");
                            break;
                        }
                        double valorDeposito = Double.parseDouble(entradaDeposito.replace(",", "."));
                        depositar(valorDeposito);
                        break;
                    case 3:
                        System.out.println("\nInforme quanto deseja sacar (use vírgula):");
                        scanner.nextLine(); // Limpa o buffer
                        String entradaSaque = scanner.nextLine();
                        if (entradaSaque.contains(".")) {
                            System.out.println("Valor inválido! Utilize apenas vírgula.");
                            break;
                        }
                        double valorSaque = Double.parseDouble(entradaSaque.replace(",", "."));
                        sacar(valorSaque);
                        break;
                    case 4:
                        System.out.println("\nEncerrando a operação...");
                        break;
                    default:
                        System.out.println("\nOpção inválida!");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nOpção inválida! Digite um número válido.");
                scanner.next(); // Limpa o buffer da entrada de dados
            } catch (NumberFormatException e) {
                System.out.println("\nValor inválido! Entre com um valor no formato correto.");
            }

        } while (opcao != 4);

        scanner.close();
    }
}

public class gerenciaBanco {

    public static void main(String[] args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            System.err.println("Erro ao configurar UTF-8: " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("Sejam bem-vindos ao Welber Bank!");

        System.out.println("Correntista, informe seu nome, por gentileza: ");
        String nome = scanner.nextLine();

        System.out.println("Agora informe seu sobrenome, por gentileza: ");
        String sobrenome = scanner.nextLine();

        String cpf;
        do {
            System.out.println("Por fim, informe seu CPF (11 dígitos), por gentileza: ");
            cpf = scanner.nextLine();

            if (cpf.length() != 11) {
                System.out.println("CPF inválido! O CPF deve conter exatamente 11 dígitos.");
            }
        } while (cpf.length() != 11);

        ContaBancaria conta = new ContaBancaria(nome, sobrenome, cpf);

        conta.exibirMenu();

        System.out.println("Obrigado por confiar em nossa Instituição, " + nome + " " + sobrenome + "!");
        scanner.close();
    }
}
