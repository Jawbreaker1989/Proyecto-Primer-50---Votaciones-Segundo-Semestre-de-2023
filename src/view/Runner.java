package view;

import java.util.Scanner;
import logic.Votaciones;

public class Runner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        
        System.out.print("Ingrese el número de candidatos: ");
        int numCandidatos = scanner.nextInt();
        System.out.print("Ingrese el número de municipios: ");
        int numMunicipios = scanner.nextInt();

        String[] candidatos = new String[numCandidatos];
        String[] municipios = new String[numMunicipios];

        // Leer nombres de candidatos
        for (int i = 0; i < numCandidatos; i++) {
            System.out.print("Ingrese el nombre del candidato " + (i + 1) + ": ");
            candidatos[i] = scanner.next();
        }

        // Leer nombres de municipios
        for (int i = 0; i < numMunicipios; i++) {
            System.out.print("Ingrese el nombre del municipio " + (i + 1) + ": ");
            municipios[i] = scanner.next();
        }

        // Crear instancia de Votaciones
        Votaciones votaciones = new Votaciones(candidatos, municipios);

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

            opcion = scanner.nextInt();

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
