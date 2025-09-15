package com.infnet.TP5_CLI.util;

import de.vandermeer.asciitable.AsciiTable;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.util.Scanner;

public class ConsoleUtil {
    
    private static final Scanner scanner = new Scanner(System.in);
    
    static {
        AnsiConsole.systemInstall();
    }
    
    public static void printSuccess(String message) {
        System.out.println(Ansi.ansi().fg(Ansi.Color.GREEN).a("✓ " + message).reset());
    }
    
    public static void printError(String message) {
        System.out.println(Ansi.ansi().fg(Ansi.Color.RED).a("✗ " + message).reset());
    }
    
    public static void printWarning(String message) {
        System.out.println(Ansi.ansi().fg(Ansi.Color.YELLOW).a("⚠ " + message).reset());
    }
    
    public static void printInfo(String message) {
        System.out.println(Ansi.ansi().fg(Ansi.Color.CYAN).a("ℹ " + message).reset());
    }
    
    public static void printTitle(String title) {
        System.out.println();
        System.out.println(Ansi.ansi().fg(Ansi.Color.BLUE).bold().a("=== " + title + " ===").reset());
    }
    
    public static String readLine(String prompt) {
        System.out.print(Ansi.ansi().fg(Ansi.Color.WHITE).a(prompt + ": ").reset());
        return scanner.nextLine().trim();
    }
    
    public static String readPassword(String prompt) {
        System.out.print(Ansi.ansi().fg(Ansi.Color.WHITE).a(prompt + ": ").reset());
        if (System.console() != null) {
            return new String(System.console().readPassword());
        } else {
            return scanner.nextLine();
        }
    }
    
    public static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(Ansi.ansi().fg(Ansi.Color.WHITE).a(prompt + ": ").reset());
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                printError("Por favor, digite um número válido.");
            }
        }
    }
    
    public static long readLong(String prompt) {
        while (true) {
            try {
                System.out.print(Ansi.ansi().fg(Ansi.Color.WHITE).a(prompt + ": ").reset());
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                printError("Por favor, digite um número válido.");
            }
        }
    }
    
    public static void printTable(String[] headers, String[][] data) {
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow((Object[]) headers);
        table.addRule();
        
        for (String[] row : data) {
            table.addRow((Object[]) row);
            table.addRule();
        }
        
        System.out.println(table.render());
    }
    
    public static void clearScreen() {
        System.out.print("\033[2J\033[H");
    }
    
    public static void waitForEnter() {
        System.out.println("\nPressione ENTER para continuar...");
        scanner.nextLine();
    }
}