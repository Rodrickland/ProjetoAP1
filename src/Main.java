import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        final int MAX_PESSOAS = 20;
        final int MAX_REDES = 10;

        String[] pessoas = new String[MAX_PESSOAS];
        String[] redes = new String[MAX_REDES];
        int[][] tempos = new int[MAX_PESSOAS][MAX_REDES];

        int totalPessoas = 5;
        int totalRedes = 4;

        // a) Leitura dos dados
        lerDados(scanner, pessoas, redes, tempos, totalPessoas, totalRedes);

        // b) Social Media Usage (minutes/day)
        imprimirTabela(pessoas, redes, tempos, totalPessoas, totalRedes);

        // c) Daily usage
        mostrarTotaisEMediasPorPessoa(pessoas, tempos, totalPessoas, totalRedes);

        // d) Most active Users
        mostrarMaximosPorRede(pessoas, redes, tempos, totalPessoas, totalRedes);

        // e) Users consistently above average
        mostrarPessoasAcimaDaMediaEmTodasAsRedes(pessoas, tempos, totalPessoas, totalRedes);

        // f) Most used social network
        mostrarRedesMaisUtilizadasPorPessoa(pessoas, redes, tempos, totalPessoas, totalRedes);

        // g) Percentage of use
        mostrarPercentagensUtilizacaoPorPessoa(pessoas, redes, tempos, totalPessoas, totalRedes);

        // h) Users profile
        mostrarPerfisUtilizadores(pessoas, tempos, totalPessoas, totalRedes);

        scanner.close();
    }

    // --- ALÍNEA A ---
    public static void lerDados(Scanner scanner, String[] pessoas, String[] redes,
                                int[][] tempos, int totalPessoas, int totalRedes) {
        for (int i = 0; i < totalPessoas; i++) {
            pessoas[i] = scanner.next();
        }

        for (int j = 0; j < totalRedes; j++) {
            redes[j] = scanner.next();
        }

        for (int i = 0; i < totalPessoas; i++) {
            for (int j = 0; j < totalRedes; j++) {
                tempos[i][j] = scanner.nextInt();
            }
        }
    }

    // --- ALÍNEA B ---
    public static void imprimirTabela(String[] pessoas, String[] redes, int[][] tempos,
                                      int totalPessoas, int totalRedes) {
        System.out.println("b) Social Media Usage (minutes/day)");
        System.out.printf("%-10s", "");
        for (int j = 0; j < totalRedes; j++) {
            System.out.printf("%12s", redes[j]);
        }
        System.out.println();

        for (int i = 0; i < totalPessoas; i++) {
            System.out.printf("%-10s", pessoas[i]);
            for (int j = 0; j < totalRedes; j++) {
                System.out.printf("%12d", tempos[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    // --- ALÍNEA C ---
    public static void mostrarTotaisEMediasPorPessoa(String[] pessoas, int[][] tempos,
                                                     int totalPessoas, int totalRedes) {
        System.out.println("c) Daily usage");
        for (int i = 0; i < totalPessoas; i++) {
            int totalMinutosPessoa = 0;
            for (int j = 0; j < totalRedes; j++) {
                totalMinutosPessoa += tempos[i][j];
            }
            double mediaMinutosPessoa = totalRedes > 0 ? (double) totalMinutosPessoa / totalRedes : 0.0;

            System.out.printf("%-10s : %4d min (%5.1f min average/network)%n",
                    pessoas[i], totalMinutosPessoa, mediaMinutosPessoa);
        }
        System.out.println();
    }

    // --- ALÍNEA D ---
    public static void mostrarMaximosPorRede(String[] pessoas, String[] redes, int[][] tempos,
                                             int totalPessoas, int totalRedes) {
        System.out.println("d) Most active Users");
        for (int j = 0; j < totalRedes; j++) {
            int maxMinutos = tempos[0][j];
            for (int i = 1; i < totalPessoas; i++) {
                if (tempos[i][j] > maxMinutos) {
                    maxMinutos = tempos[i][j];
                }
            }

            System.out.printf("%-10s : ", redes[j]);
            boolean primeira = true;
            for (int i = 0; i < totalPessoas; i++) {
                if (tempos[i][j] == maxMinutos) {
                    if (!primeira) {
                        System.out.print(" ");
                    }
                    System.out.print("\"" + pessoas[i] + "\"");
                    primeira = false;
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    // --- ALÍNEA E ---
    public static void mostrarPessoasAcimaDaMediaEmTodasAsRedes(String[] pessoas, int[][] tempos,
                                                                int totalPessoas, int totalRedes) {
        System.out.println("e) Users consistently above average");

        double[] mediasRedes = new double[totalRedes];
        for (int j = 0; j < totalRedes; j++) {
            int somaRede = 0;
            for (int i = 0; i < totalPessoas; i++) {
                somaRede += tempos[i][j];
            }
            mediasRedes[j] = totalPessoas > 0 ? (double) somaRede / totalPessoas : 0.0;
        }

        boolean encontrouAlguma = false;
        for (int i = 0; i < totalPessoas; i++) {
            boolean acimaEmTodas = true;
            int j = 0;

            while (acimaEmTodas && j < totalRedes) {
                if (tempos[i][j] <= mediasRedes[j]) {
                    acimaEmTodas = false;
                }
                j++;
            }

            if (acimaEmTodas) {
                System.out.println("\"" + pessoas[i] + "\"");
                encontrouAlguma = true;
            }
        }

        if (!encontrouAlguma) {
            System.out.println("none");
        }
        System.out.println();
    }

    // --- ALÍNEA F ---
    public static void mostrarRedesMaisUtilizadasPorPessoa(String[] pessoas, String[] redes, int[][] tempos,
                                                           int totalPessoas, int totalRedes) {
        System.out.println("f) Most used social network");
        for (int i = 0; i < totalPessoas; i++) {
            int maxMinutosPessoa = tempos[i][0];
            for (int j = 1; j < totalRedes; j++) {
                if (tempos[i][j] > maxMinutosPessoa) {
                    maxMinutosPessoa = tempos[i][j];
                }
            }

            System.out.printf("%-10s : ", pessoas[i]);
            for (int j = 0; j < totalRedes; j++) {
                if (tempos[i][j] == maxMinutosPessoa) {
                    System.out.print("[" + redes[j] + "] ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    // --- ALÍNEA G ---
    public static void mostrarPercentagensUtilizacaoPorPessoa(String[] pessoas, String[] redes, int[][] tempos,
                                                              int totalPessoas, int totalRedes) {
        System.out.println("g) Percentage of use");
        System.out.printf("%-10s", "");
        for (int j = 0; j < totalRedes; j++) {
            System.out.printf("%12s", redes[j]);
        }
        System.out.println();

        for (int i = 0; i < totalPessoas; i++) {
            int totalMinutosPessoa = 0;
            for (int j = 0; j < totalRedes; j++) {
                totalMinutosPessoa += tempos[i][j];
            }

            System.out.printf("%-10s", pessoas[i]);
            for (int j = 0; j < totalRedes; j++) {
                if (totalMinutosPessoa > 0 && tempos[i][j] > 0) {
                    double pct = ((double) tempos[i][j] / totalMinutosPessoa) * 100.0;
                    System.out.printf("%11.2f%%", pct);
                } else {
                    System.out.printf("%12s", "");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    // --- ALÍNEA H ---
    public static void mostrarPerfisUtilizadores(String[] pessoas, int[][] tempos,
                                                 int totalPessoas, int totalRedes) {
        System.out.println("h) Users profile");

        final int LARGURA_COLUNA = 15;
        final int NUM_PERFIS = 4;

        String[] nomesPerfis = {"Moderate", "Regular", "Intensive", "Very Intensive"};
        String[][] grelhaPerfis = new String[totalPessoas][NUM_PERFIS];
        int[] contadoresColuna = new int[NUM_PERFIS];

        for (int i = 0; i < totalPessoas; i++) {
            int totalDiario = 0;
            for (int j = 0; j < totalRedes; j++) {
                totalDiario += tempos[i][j];
            }

            int indicePerfil = 3; // > 300 ("Very Intensive")
            if (totalDiario < 90) {
                indicePerfil = 0; // < 90 ("Moderate")
            } else if (totalDiario <= 180) {
                indicePerfil = 1; // 90 a 180 ("Regular")
            } else if (totalDiario <= 300) {
                indicePerfil = 2; // > 180 até 300 ("Intensive")
            }

            int linhaInsercao = contadoresColuna[indicePerfil];
            grelhaPerfis[linhaInsercao][indicePerfil] = pessoas[i];
            contadoresColuna[indicePerfil]++;
        }

        int maxLinhas = 0;
        for (int p = 0; p < NUM_PERFIS; p++) {
            if (contadoresColuna[p] > maxLinhas) {
                maxLinhas = contadoresColuna[p];
            }
        }

        for (int p = 0; p < NUM_PERFIS; p++) {
            System.out.printf("%-" + LARGURA_COLUNA + "s", nomesPerfis[p]);
        }
        System.out.println();

        for (int p = 0; p < NUM_PERFIS; p++) {
            String linhaTracos = "--------------";
            if (p < NUM_PERFIS - 1) {
                System.out.printf("%-" + LARGURA_COLUNA + "s", linhaTracos + "|");
            } else {
                System.out.printf("%-" + LARGURA_COLUNA + "s", linhaTracos);
            }
        }
        System.out.println();

        for (int linha = 0; linha < maxLinhas; linha++) {
            for (int p = 0; p < NUM_PERFIS; p++) {
                String nome = grelhaPerfis[linha][p] != null ? grelhaPerfis[linha][p] : "";
                System.out.printf("%-" + LARGURA_COLUNA + "s", nome);
            }
            System.out.println();
        }
        System.out.println();
    }
    // --- ALÍNEA I ---
    public static void mostrarUtilizacaoRedes(String[] redes, int[][] tempos,
                                              int totalPessoas, int totalRedes) {
        System.out.println("i) Use of social networks");

        String[] nomesOrdenados = new String[totalRedes];
        int[] totaisRede = new int[totalRedes];
        int[] qtdUtilizadores = new int[totalRedes];
        int totalGlobal = 0;

        // 1. Calcular total de minutos, quantidade de utilizadores (> 0) e total global
        for (int j = 0; j < totalRedes; j++) {
            nomesOrdenados[j] = redes[j];
            int soma = 0;
            int utilizadores = 0;

            for (int i = 0; i < totalPessoas; i++) {
                soma += tempos[i][j];
                if (tempos[i][j] > 0) {
                    utilizadores++;
                }
            }

            totaisRede[j] = soma;
            qtdUtilizadores[j] = utilizadores;
            totalGlobal += soma;
        }

        // 2. Ordenar por ordem decrescente: (1) tempo, (2) qtd utilizadores, (3) ordem alfabética
        for (int a = 0; a < totalRedes - 1; a++) {
            for (int b = a + 1; b < totalRedes; b++) {
                boolean deveTrocar = false;

                if (totaisRede[b] != totaisRede[a]) {
                    deveTrocar = totaisRede[b] > totaisRede[a];
                } else if (qtdUtilizadores[b] != qtdUtilizadores[a]) {
                    deveTrocar = qtdUtilizadores[b] > qtdUtilizadores[a];
                } else {
                    deveTrocar = nomesOrdenados[a].compareToIgnoreCase(nomesOrdenados[b]) > 0;
                }

                if (deveTrocar) {
                    String tempNome = nomesOrdenados[a];
                    nomesOrdenados[a] = nomesOrdenados[b];
                    nomesOrdenados[b] = tempNome;

                    int tempTotal = totaisRede[a];
                    totaisRede[a] = totaisRede[b];
                    totaisRede[b] = tempTotal;

                    int tempQtd = qtdUtilizadores[a];
                    qtdUtilizadores[a] = qtdUtilizadores[b];
                    qtdUtilizadores[b] = tempQtd;
                }
            }
        }

        // 3. Desenhar o histograma na horizontal (escala 1/5 -> cada '*' = 5%, máximo 20 '*')
        final int TAMANHO_BARRA = 20;
        final double PASSO_ESCALA = 5.0;

        for (int j = 0; j < totalRedes; j++) {
            double pct = totalGlobal > 0 ? ((double) totaisRede[j] / totalGlobal) * 100.0 : 0.0;
            int numEstrelas = (int) Math.round(pct / PASSO_ESCALA);
            if (numEstrelas > TAMANHO_BARRA) {
                numEstrelas = TAMANHO_BARRA;
            }

            String barra = "";
            for (int k = 0; k < numEstrelas; k++) {
                barra += "*";
            }

            System.out.printf("%-10s|%-" + TAMANHO_BARRA + "s %6.2f%%%n",
                    nomesOrdenados[j], barra, pct);
        }

        // 4. Legenda horizontal com intervalos de 25% (escala 1/5)
        System.out.printf("%-10s+----+----+----+----+%n", "");
        System.out.printf("%-10s%-5s%-5s%-5s%-5s%s%n", "", "%", "25", "50", "75", "100");
        System.out.println();
    }
}

//A BEA PORTAL E FEIA
//A BIA TEM I NO NOME