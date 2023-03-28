package tiduswr;

import tiduswr.entity.Grafo;
import tiduswr.exception.DuplicatedVerticeException;

import java.util.Map;

@SuppressWarnings("unused")
public class Main {
    public static void main(String[] args) throws DuplicatedVerticeException {
        grafoLetras();
        System.out.println("");
        grafoNumeros();
    }

    public static void grafoLetras() throws DuplicatedVerticeException {
        Grafo<String> grafo = new Grafo<>(new String[]{"A","B","C","D","E","F"});
        grafo.addArestasNaoDirecionadas(new String[][]{
                {"A", "B"}, {"A", "C"}, {"B", "C"}, {"B", "D"},
                {"C", "E"}, {"D", "E"}, {"D", "F"}, {"E", "F"}
        });
        grafo.addIterationListener(v -> System.out.print(v + " "));
        grafo.buscaEmLarguraCompleta("C");
    }

    public static void grafoNumeros() throws DuplicatedVerticeException {
        Grafo<Integer> grafo = new Grafo<>(new Integer[]{1,2,3,4,5,6});
        grafo.addArestasNaoDirecionadas(new Integer[][]{
                {1, 2}, {1, 3}, {2, 3}, {2, 4},
                {3, 5}, {4, 5}, {4, 6}, {5, 6}
        });
        grafo.addIterationListener(v -> System.out.print(v + " "));
        grafo.buscaEmLarguraCompleta(6);
    }

}
