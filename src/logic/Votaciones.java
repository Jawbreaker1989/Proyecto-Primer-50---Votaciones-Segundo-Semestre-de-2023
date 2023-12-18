package logic;

/**
 * Clase que representa un sistema de votaciones.
 */
public class Votaciones {
    private String[] candidatos;
    private String[] municipios;
    private int[][] matrizVotos;

    /**
     * Constructor de la clase Votaciones.
     *
     * @param nombresCandidatos   Array con los nombres de los candidatos.
     * @param nombresMunicipios   Array con los nombres de los municipios.
     * @throws IllegalArgumentException Si las matrices de candidatos o municipios tienen tamaño cero.
     */
    public Votaciones(String[] nombresCandidatos, String[] nombresMunicipios) {
        if (nombresCandidatos.length == 0 || nombresMunicipios.length == 0) {
            throw new IllegalArgumentException("Las matrices de candidatos y municipios no pueden tener tamaño cero.");
        }

        candidatos = nombresCandidatos.clone();
        municipios = nombresMunicipios.clone();
        matrizVotos = new int[candidatos.length][municipios.length];
        generarVotosAleatorios(); // Genera votos aleatorios al instanciar la clase
    }

    /**
     * Visualiza al candidato ganador junto con la cantidad total de votos obtenidos.
     */
    public void visualizarCandidatoGanador() {
        int totalVotosMax = 0;
        int indiceGanador = -1;

        for (int i = 0; i < candidatos.length; i++) {
            int totalVotosCandidato = 0;
            for (int j = 0; j < municipios.length; j++) {
                totalVotosCandidato += matrizVotos[i][j];
            }

            if (totalVotosCandidato > totalVotosMax) {
                totalVotosMax = totalVotosCandidato;
                indiceGanador = i;
            }
        }

        if (indiceGanador != -1) {
            System.out.println("Candidato ganador: " + candidatos[indiceGanador] + " con " + totalVotosMax + " votos.");
        } else {
            System.out.println("No hay votos registrados.");
        }
    }

    /**
     * Visualiza los datos de votaciones en una tabla, mostrando los votos por
     * candidato en cada municipio y el total por candidato.
     */
    public void visualizarVotaciones() {
        System.out.println("Datos de votaciones por candidato y municipio:");

        // Encabezados de columnas
        System.out.print("Candidato\\Municipio\t");
        for (String municipio : municipios) {
            System.out.print(municipio + "\t");
        }
        System.out.println("Total");

        // Datos de votaciones y totales por candidato
        for (int i = 0; i < candidatos.length; i++) {
            System.out.print(candidatos[i] + "\t\t\t");
            int totalVotosCandidato = 0;

            for (int j = 0; j < municipios.length; j++) {
                System.out.print(matrizVotos[i][j] + "\t");
                totalVotosCandidato += matrizVotos[i][j];
            }

            System.out.println(totalVotosCandidato);
        }
    }

    /**
     * Consulta y muestra información específica sobre un candidato, como la mayor y
     * menor votación, y el promedio de votos.
     *
     * @param nombreCandidato El nombre del candidato a consultar.
     * @throws IllegalArgumentException Si el nombre del candidato es nulo.
     */
    public void consultarCandidato(String nombreCandidato) {
        if (nombreCandidato == null) {
            throw new IllegalArgumentException("El nombre del candidato no puede ser nulo.");
        }

        int indiceCandidato = -1;

        for (int i = 0; i < candidatos.length; i++) {
            if (candidatos[i].equals(nombreCandidato)) {
                indiceCandidato = i;
                break;
            }
        }

        if (indiceCandidato != -1) {
            int[] votosCandidato = matrizVotos[indiceCandidato];

            if (votosCandidato.length > 0) {
                int mayorVotacion = encontrarMayorVotacion(votosCandidato);
                int menorVotacion = encontrarMenorVotacion(votosCandidato);

                int indiceMayor = encontrarIndiceMunicipioConVotacion(votosCandidato, mayorVotacion);
                int indiceMenor = encontrarIndiceMunicipioConVotacion(votosCandidato, menorVotacion);

                System.out.println("Candidato: " + candidatos[indiceCandidato]);

                if (mayorVotacion > 0) {
                    System.out.println("Municipio con mayor votación: " + municipios[indiceMayor] + " con "
                            + mayorVotacion + " votos.");
                } else {
                    System.out.println("No hay votos registrados.");
                }

                if (menorVotacion > 0) {
                    System.out.println("Municipio con menor votación: " + municipios[indiceMenor] + " con "
                            + menorVotacion + " votos.");
                } else {
                    System.out.println("No hay votos registrados.");
                }

                double promedioVotaciones = calcularPromedioVotaciones(votosCandidato);
                System.out.println("Promedio de votaciones: " + promedioVotaciones);
            } else {
                System.out.println("No hay votos registrados para el candidato.");
            }
        } else {
            System.out.println("Candidato no encontrado.");
        }
    }

    /**
     * Visualiza la lista de candidatos ordenados por la cantidad total de votos en
     * forma descendente.
     */
    public void visualizarCandidatosOrdenadosPorVotos() {
        int n = candidatos.length;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                int votosCandidatoJ = sumarVotos(matrizVotos[j]);
                int votosCandidatoJ1 = sumarVotos(matrizVotos[j + 1]);

                if (votosCandidatoJ < votosCandidatoJ1) {
                    intercambiarCandidatos(j, j + 1);
                }
            }
        }

        System.out.println("Candidatos ordenados por votos en forma descendente:");
        for (int i = 0; i < n; i++) {
            int totalVotos = sumarVotos(matrizVotos[i]);
            System.out.println(candidatos[i] + ": " + totalVotos + " votos");
        }
    }

    /**
     * Visualiza el total de votos por municipio en una tabla.
     */
    public void totalVotosPorMunicipio() {
        System.out.println("Total de votos por municipio:");

        System.out.print("\t");
        for (String municipio : municipios) {
            System.out.print(municipio + "\t");
        }
        System.out.println();

        for (int j = 0; j < municipios.length; j++) {
            int totalVotosMunicipio = 0;
            System.out.print(municipios[j] + "\t");
            for (int i = 0; i < candidatos.length; i++) {
                System.out.print(matrizVotos[i][j] + "\t");
                totalVotosMunicipio += matrizVotos[i][j];
            }
            System.out.println(totalVotosMunicipio);
        }
    }

    /**
     * Calcula la suma total de votos en un array de votos.
     *
     * @param votos Array de votos.
     * @return La suma total de votos.
     */
    private int sumarVotos(int[] votos) {
        int totalVotos = 0;
        for (int voto : votos) {
            totalVotos += voto;
        }
        return totalVotos;
    }

    /**
     * Encuentra la mayor votación en un array de votos.
     *
     * @param votos Array de votos.
     * @return La mayor votación.
     */
    private int encontrarMayorVotacion(int[] votos) {
        int mayorVotacion = 0;
        for (int voto : votos) {
            if (voto > mayorVotacion) {
                mayorVotacion = voto;
            }
        }
        return mayorVotacion;
    }

    /**
     * Encuentra la menor votación en un array de votos.
     *
     * @param votos Array de votos.
     * @return La menor votación.
     */
    private int encontrarMenorVotacion(int[] votos) {
        int menorVotacion = Integer.MAX_VALUE;
        for (int voto : votos) {
            if (voto < menorVotacion) {
                menorVotacion = voto;
            }
        }
        return menorVotacion == Integer.MAX_VALUE ? 0 : menorVotacion;
    }

    /**
     * Encuentra el índice del municipio que tiene una votación específica en un
     * array de votos.
     *
     * @param votos    Array de votos.
     * @param votacion La votación específica a buscar.
     * @return El índice del municipio o -1 si no se encuentra.
     */
    private int encontrarIndiceMunicipioConVotacion(int[] votos, int votacion) {
        for (int i = 0; i < votos.length; i++) {
            if (votos[i] == votacion) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Calcula el promedio de votaciones en un array de votos.
     *
     * @param votos Array de votos.
     * @return El promedio de votaciones.
     */
    private double calcularPromedioVotaciones(int[] votos) {
        int totalVotos = sumarVotos(votos);
        return votos.length > 0 ? (double) totalVotos / votos.length : 0.0;
    }

    /**
     * Intercambia dos candidatos y sus respectivos votos en las matrices.
     *
     * @param indice1 Índice del primer candidato.
     * @param indice2 Índice del segundo candidato.
     */
    private void intercambiarCandidatos(int indice1, int indice2) {
        // Intercambiar candidatos
        String tempCandidato = candidatos[indice1];
        candidatos[indice1] = candidatos[indice2];
        candidatos[indice2] = tempCandidato;

        // Intercambiar votos
        int[] tempVotos = matrizVotos[indice1];
        matrizVotos[indice1] = matrizVotos[indice2];
        matrizVotos[indice2] = tempVotos;
    }

    /**
     * Genera votos aleatorios en la matriz de votos.
     */
    private void generarVotosAleatorios() {
        for (int i = 0; i < candidatos.length; i++) {
            for (int j = 0; j < municipios.length; j++) {
                matrizVotos[i][j] = (int) (Math.random() * 101);
            }
        }
    }
}
