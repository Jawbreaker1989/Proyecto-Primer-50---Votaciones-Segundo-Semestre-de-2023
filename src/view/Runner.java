package view;

import java.util.InputMismatchException;
import java.util.Scanner;
import logic.Votaciones;

public class Runner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Inicialización de variables
        int numCandidatos = 0;
        int numMunicipios = 0;
        String[] candidatos;
        String[] municipios;
        Votaciones votaciones;

        try {
            // Solicitar el número de candidatos 
            do {
                System.out.print("Ingrese el número de candidatos: ");
                while (!scanner.hasNextInt()) {
                    System.out.println("Error: Ingrese un número válido para el número de candidatos.");
                    scanner.next(); 
                }
                numCandidatos = scanner.nextInt();
                if (numCandidatos <= 0) {
                    System.out.println("Error: El número de candidatos debe ser positivo.");
                }
            } while (numCandidatos <= 0);

            // Solicitar el número de municipios
            do {
                System.out.print("Ingrese el número de municipios: ");
                while (!scanner.hasNextInt()) {
                    System.out.println("Error: Ingrese un número válido para el número de municipios.");
                    scanner.next();
                }
                numMunicipios = scanner.nextInt();
                if (numMunicipios <= 0) {
                    System.out.println("Error: El número de municipios debe ser positivo.");
                }
            } while (numMunicipios <= 0);

            // Inicializar arrays de candidatos y municipios
            candidatos = new String[numCandidatos];
            municipios = new String[numMunicipios];

            // Leer nombres de candidatos y verificar duplicados
            for (int i = 0; i < numCandidatos; i++) {
                boolean nombreDuplicado;
                do {
                    nombreDuplicado = false;
                    System.out.print("Ingrese el nombre del candidato " + (i + 1) + ": ");
                    candidatos[i] = scanner.next();

                    // Verificar duplicados
                    for (int j = 0; j < i; j++) {
                        if (candidatos[i].equalsIgnoreCase(candidatos[j])) {
                            System.out.println("Error: El nombre ya ha sido ingresado. Ingrese uno diferente.");
                            nombreDuplicado = true;
                            break;
                        }
                    }
                } while (nombreDuplicado);
            }

            // Leer nombres de municipios y verificar duplicados
            for (int i = 0; i < numMunicipios; i++) {
                boolean nombreDuplicado;
                do {
                    nombreDuplicado = false;
                    System.out.print("Ingrese el nombre del municipio " + (i + 1) + ": ");
                    municipios[i] = scanner.next();

                    // Verificar duplicados
                    for (int j = 0; j < i; j++) {
                        if (municipios[i].equalsIgnoreCase(municipios[j])) {
                            System.out.println("Error: El municipio ya ha sido ingresado. Ingrese uno diferente.");
                            nombreDuplicado = true;
                            break;
                        }
                    }
                } while (nombreDuplicado);
            }

            // Crear instancia de Votaciones
            votaciones = new Votaciones(candidatos, municipios);

        } catch (InputMismatchException e) {
            System.out.println("Error: Ingrese un número válido.");
            scanner.close();
            System.exit(1);
            return;
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            scanner.close();
            System.exit(1);
            return;
        }

        int opcion;
        do {
            // Menú de opciones
            System.out.println("\nSeleccione una opción:");
            System.out.println("1. Visualizar candidato ganador");
            System.out.println("2. Visualizar votaciones");
            System.out.println("3. Consultar candidato");
            System.out.println("4. Visualizar candidatos ordenados por votos");
            System.out.println("5. Total de votos por municipio");
            System.out.println("0. Salir");

            // Captura de excepciones para asegurar entrada válida
            try {
                opcion = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Error: Ingrese un número válido.");
                opcion = -1;
                scanner.next();
                continue;
            }

            switch (opcion) {
                case 1:
                    votaciones.visualizarCandidatoGanador();
                    break;
                case 2:
                    votaciones.visualizarVotaciones();
                    break;
                case 3:
                    System.out.print("Ingrese el nombre del candidato a consultar: ");
                    String candidatoConsulta = scanner.next();
                    votaciones.consultarCandidato(candidatoConsulta);
                    break;
                case 4:
                    votaciones.visualizarCandidatosOrdenadosPorVotos();
                    break;
                case 5:
                    votaciones.totalVotosPorMunicipio();
                    break;
                case 0:
                    System.out.println("¡Adiós!");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 0);

        scanner.close();
    }
}
