import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DK_1261441_1260918 {

    public static void main(String[] args) throws FileNotFoundException {
        final String FILE_IN = "input.txt";
        Scanner teclado = new Scanner(System.in);

        System.out.println("Escolha a origem dos dados:");
        System.out.println("1 - Ficheiro (" + FILE_IN + ")");
        System.out.println("2 - Introducao manual");

        String opcao = teclado.nextLine().trim();
        while (!opcao.equals("1") && !opcao.equals("2")) {
            System.out.println("Opcao invalida. Escolha 1 ou 2:");
            opcao = teclado.nextLine().trim();
        }

        boolean leuDeFicheiro = opcao.equals("1");
        Scanner scanner = teclado;
        if (leuDeFicheiro) {
            scanner = new Scanner(new File(FILE_IN));
        } else {
            System.out.println("Introduza os dados:");
        }

        // a) Leitura dos dados (qualquer número de pessoas e de redes sociais)
        String[] pessoas = lerNomes(scanner);
        String[] redes = lerNomes(scanner);
        int totalPessoas = pessoas.length;
        int totalRedes = redes.length;
        int[][] tempos = new int[totalPessoas][totalRedes];
        lerDados(scanner, tempos, totalPessoas, totalRedes);

        if (leuDeFicheiro) {
            scanner.close();
        }

        System.out.println();

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

        // i) Use of social networks
        mostrarUtilizacaoRedes(redes, tempos, totalPessoas, totalRedes);

        // j) Most balanced users
        mostrarPessoasMaisEquilibradas(pessoas, tempos, totalPessoas, totalRedes);
    }

    // --- AUXILIARES DE FORMATAÇÃO ---
    // Largura necessária para alinhar os nomes (nunca inferior ao mínimo)
    public static int larguraMaxima(String[] nomes, int minimo) {
        int largura = minimo;
        for (String nome : nomes) {
            if (nome.length() + 1 > largura) {
                largura = nome.length() + 1;
            }
        }
        return largura;
    }

    public static void escreverRepetido(String simbolo, int vezes) {
        for (int i = 0; i < vezes; i++) {
            System.out.print(simbolo);
        }
    }

    // --- ALÍNEA A ---
    // Lê a próxima linha que não esteja vazia
    public static String lerLinhaNaoVazia(Scanner scanner) {
        String linha;
        do {
            linha = scanner.nextLine();
        } while (linha.trim().equals(""));
        return linha;
    }

    // Conta os elementos de uma linha separados por vírgula
    public static int contarElementos(String linha) {
        int quantidade = 1;
        for (int i = 0; i < linha.length(); i++) {
            if (linha.charAt(i) == ',') {
                quantidade++;
            }
        }
        return quantidade;
    }

    // Lê uma linha de nomes separados por vírgula
    public static String[] lerNomes(Scanner scanner) {
        String linha = lerLinhaNaoVazia(scanner);
        String[] nomes = new String[contarElementos(linha)];

        Scanner leitorLinha = new Scanner(linha);
        leitorLinha.useDelimiter(",");
        for (int i = 0; i < nomes.length; i++) {
            nomes[i] = leitorLinha.next().trim();
        }
        return nomes;
    }

    // Lê a matriz: uma linha por pessoa, valores separados por espaço
    public static void lerDados(Scanner scanner, int[][] tempos, int totalPessoas, int totalRedes) {
        for (int i = 0; i < totalPessoas; i++) {
            Scanner leitorLinha = new Scanner(lerLinhaNaoVazia(scanner));
            for (int j = 0; j < totalRedes; j++) {
                tempos[i][j] = leitorLinha.nextInt();
            }
        }
    }

    // --- ALÍNEA B ---
    public static void imprimirTabela(String[] pessoas, String[] redes, int[][] tempos,
                                      int totalPessoas, int totalRedes) {
        int larguraNome = larguraMaxima(pessoas, 13);
        int larguraColuna = larguraMaxima(redes, 12);

        System.out.println("b) Social Media Usage (minutes/day)");
        System.out.printf("%-" + larguraNome + "s", "");
        for (int j = 0; j < totalRedes; j++) {
            System.out.printf("%" + larguraColuna + "s", redes[j]);
        }
        System.out.println();

        for (int i = 0; i < totalPessoas; i++) {
            System.out.printf("%-" + larguraNome + "s", pessoas[i]);
            for (int j = 0; j < totalRedes; j++) {
                System.out.printf("%" + larguraColuna + "d", tempos[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    // --- ALÍNEA C ---
    public static void mostrarTotaisEMediasPorPessoa(String[] pessoas, int[][] tempos,
                                                     int totalPessoas, int totalRedes) {
        int larguraNome = larguraMaxima(pessoas, 13);

        System.out.println("c) Daily usage");
        for (int i = 0; i < totalPessoas; i++) {
            int totalMinutosPessoa = 0;
            for (int j = 0; j < totalRedes; j++) {
                totalMinutosPessoa += tempos[i][j];
            }
            double mediaMinutosPessoa = 0.0;
            if (totalRedes > 0) {
                mediaMinutosPessoa = (double) totalMinutosPessoa / totalRedes;
            }

            System.out.printf("%-" + larguraNome + "s: %5d min (%5.1f min average/network)%n",
                    pessoas[i], totalMinutosPessoa, mediaMinutosPessoa);
        }
        System.out.println();
    }

    // --- ALÍNEA D ---
    public static void mostrarMaximosPorRede(String[] pessoas, String[] redes, int[][] tempos,
                                             int totalPessoas, int totalRedes) {
        int larguraNomeRede = larguraMaxima(redes, 13);

        System.out.println("d) Most active Users");
        for (int j = 0; j < totalRedes; j++) {
            int maxMinutos = tempos[0][j];
            for (int i = 1; i < totalPessoas; i++) {
                if (tempos[i][j] > maxMinutos) {
                    maxMinutos = tempos[i][j];
                }
            }

            System.out.printf("%-" + larguraNomeRede + "s: ", redes[j]);
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
            if (totalPessoas > 0) {
                mediasRedes[j] = (double) somaRede / totalPessoas;
            }
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
        int larguraNome = larguraMaxima(pessoas, 13);

        System.out.println("f) Most used social network");
        for (int i = 0; i < totalPessoas; i++) {
            int maxMinutosPessoa = tempos[i][0];
            for (int j = 1; j < totalRedes; j++) {
                if (tempos[i][j] > maxMinutosPessoa) {
                    maxMinutosPessoa = tempos[i][j];
                }
            }

            System.out.printf("%-" + larguraNome + "s: ", pessoas[i]);
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
        int larguraNome = larguraMaxima(pessoas, 13);
        int larguraColuna = larguraMaxima(redes, 12);

        System.out.println("g) Percentage of use");
        System.out.printf("%-" + larguraNome + "s", "");
        for (int j = 0; j < totalRedes; j++) {
            System.out.printf("%" + larguraColuna + "s", redes[j]);
        }
        System.out.println();

        for (int i = 0; i < totalPessoas; i++) {
            int totalMinutosPessoa = 0;
            for (int j = 0; j < totalRedes; j++) {
                totalMinutosPessoa += tempos[i][j];
            }

            System.out.printf("%-" + larguraNome + "s", pessoas[i]);
            for (int j = 0; j < totalRedes; j++) {
                if (totalMinutosPessoa > 0 && tempos[i][j] > 0) {
                    double pct = ((double) tempos[i][j] / totalMinutosPessoa) * 100.0;
                    System.out.printf("%" + (larguraColuna - 1) + ".2f%%", pct);
                } else {
                    System.out.printf("%" + larguraColuna + "s", "");
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

        final int NUM_PERFIS = 4;
        final int LIMITE_MODERATE = 90;
        final int LIMITE_REGULAR = 180;
        final int LIMITE_INTENSIVE = 300;
        int larguraColuna = larguraMaxima(pessoas, 13);

        String[] nomesPerfis = {"Moderate", "Regular", "Intensive", "Very Intensive"};

        for (int p = 0; p < NUM_PERFIS; p++) {
            System.out.printf("%-" + larguraColuna + "s", nomesPerfis[p]);
        }
        System.out.println();

        for (int p = 0; p < NUM_PERFIS; p++) {
            escreverRepetido("-", larguraColuna - 1);
            if (p < NUM_PERFIS - 1) {
                System.out.print("|");
            }
        }
        System.out.println();

        // Uma linha por pessoa, alinhada com o respetivo perfil
        for (int i = 0; i < totalPessoas; i++) {
            int totalDiario = 0;
            for (int j = 0; j < totalRedes; j++) {
                totalDiario += tempos[i][j];
            }

            int indicePerfil = 3;
            if (totalDiario < LIMITE_MODERATE) {
                indicePerfil = 0;
            } else if (totalDiario <= LIMITE_REGULAR) {
                indicePerfil = 1;
            } else if (totalDiario <= LIMITE_INTENSIVE) {
                indicePerfil = 2;
            }

            escreverRepetido(" ", indicePerfil * larguraColuna);
            System.out.println(pessoas[i]);
        }
        System.out.println();
    }

    // --- ALÍNEA I ---
    public static void mostrarUtilizacaoRedes(String[] redes, int[][] tempos,
                                              int totalPessoas, int totalRedes) {
        System.out.println("i) Use of social networks");

        final double PASSO_ESCALA = 5.0;
        final double CEM_POR_CENTO = 100.0;
        final String CARATER_BARRA = "*";
        int larguraNomeRede = larguraMaxima(redes, 13);

        String[] nomesOrdenados = new String[totalRedes];
        int[] totaisRede = new int[totalRedes];
        int[] qtdUtilizadores = new int[totalRedes];
        int totalGlobal = 0;

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

        for (int a = 0; a < totalRedes - 1; a++) {
            for (int b = a + 1; b < totalRedes; b++) {
                boolean deveTrocar = false;

                if (totaisRede[b] != totaisRede[a]) {
                    deveTrocar = totaisRede[b] > totaisRede[a];
                } else if (qtdUtilizadores[b] != qtdUtilizadores[a]) {
                    deveTrocar = qtdUtilizadores[b] > qtdUtilizadores[a];
                } else {
                    deveTrocar = nomesOrdenados[a].compareTo(nomesOrdenados[b]) > 0;
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

        for (int j = 0; j < totalRedes; j++) {
            double pct = 0.0;
            if (totalGlobal > 0) {
                pct = ((double) totaisRede[j] / totalGlobal) * CEM_POR_CENTO;
            }
            int numEstrelas = (int) Math.round(pct / PASSO_ESCALA);

            System.out.printf("%-" + larguraNomeRede + "s|", nomesOrdenados[j]);
            escreverRepetido(CARATER_BARRA, numEstrelas);
            System.out.printf(" %.2f%%%n", pct);
        }

        escreverRepetido(" ", larguraNomeRede - 1);
        System.out.println("+----+----+----+----+");
        escreverRepetido(" ", larguraNomeRede - 2);
        System.out.println("%    25   50   75  100");
        System.out.println();
    }

    // --- ALÍNEA J ---
    public static void mostrarPessoasMaisEquilibradas(String[] pessoas, int[][] tempos,
                                                      int totalPessoas, int totalRedes) {
        System.out.println("j) Most balanced users");

        int[] diferencas = new int[totalPessoas];
        for (int i = 0; i < totalPessoas; i++) {
            int maxMinutos = tempos[i][0];
            int minMinutos = tempos[i][0];
            for (int j = 1; j < totalRedes; j++) {
                if (tempos[i][j] > maxMinutos) {
                    maxMinutos = tempos[i][j];
                }
                if (tempos[i][j] < minMinutos) {
                    minMinutos = tempos[i][j];
                }
            }
            diferencas[i] = maxMinutos - minMinutos;
        }

        int menorDiferenca = diferencas[0];
        for (int i = 1; i < totalPessoas; i++) {
            if (diferencas[i] < menorDiferenca) {
                menorDiferenca = diferencas[i];
            }
        }

        System.out.print("{");
        boolean primeira = true;
        for (int i = 0; i < totalPessoas; i++) {
            if (diferencas[i] == menorDiferenca) {
                if (!primeira) {
                    System.out.print(", ");
                }
                System.out.print("\"" + pessoas[i] + "\"");
                primeira = false;
            }
        }
        System.out.println("}");
        System.out.println();
    }
}