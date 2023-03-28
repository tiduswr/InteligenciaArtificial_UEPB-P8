package tiduswr.entity;

import tiduswr.entity.listeners.IterationListener;
import tiduswr.exception.DuplicatedVerticeException;
import tiduswr.util.MatcherUtil;

import java.util.*;

@SuppressWarnings("unused")
public class Grafo<E> {

    private final Map<E, Vertice<E>> vertices;
    private final List<Aresta<E>> arestas;
    private final List<IterationListener> iterationListeners;

    //Records são classes otimizadas para dados imutaveis
    public record Aresta<E>(Vertice<E> origem, Vertice<E> destino) {}
    public record Vertice<E>(E value) {
        @Override
        public String toString(){
            return String.valueOf(value);
        }
    }

    public Grafo(E[] verticesParams) throws DuplicatedVerticeException{

        //Verifica se tem valores duplicados ou nulo
        if(MatcherUtil.hasAnyDuplicateNumber(verticesParams))
            throw new DuplicatedVerticeException("Não é permitido dois vertices com mesmo valor");

        //Cria uma lista de vértices
        this.vertices = new HashMap<>();
        for(E vertice : verticesParams){
            //Popula a lista com os vértices
            vertices.put(vertice, new Vertice<>(vertice));
        }

        //Inicializa arestas
        this.arestas = new ArrayList<>();
        //E o listener
        this.iterationListeners = new ArrayList<>();
    }

    private void addAresta(E origem, E destino){
        Vertice<E> start = vertices.get(origem);
        Vertice<E> end = vertices.get(destino);
        if(start == null || end == null)
            throw new NullPointerException("Precisa ser um vértice que existe no grafo");

        this.arestas.add(new Aresta<>(start, end));
    }

    public void addArestasDirecionadas(E[][] arestas){
        this.arestas.clear();
        for (E[] aresta : arestas) {
            addAresta(aresta[0], aresta[1]);
        }
    }

    public void addArestasNaoDirecionadas(E[][] arestas){
        this.arestas.clear();
        for (E[] aresta : arestas) {
            addAresta(aresta[0], aresta[1]);
            addAresta(aresta[1], aresta[0]);
        }
    }

    public List<Aresta<E>> getArestas(){
        return this.arestas;
    }

    public List<Vertice<E>> getVertices() {
        return new ArrayList<>(vertices.values());
    }

    public void buscaEmLarguraCompleta(E verticeInicio) throws NullPointerException{
        //Verifica se o vértice existe no grafo
        Vertice<E> start = vertices.get(verticeInicio);
        if(start == null)
            throw new NullPointerException("Precisa ser um vértice que existe no grafo");

        //Mapa de vértices visitados e de proximos vertices
        Set<Vertice<E>> visitados = new LinkedHashSet<>();
        Queue<Vertice<E>> proximos = new LinkedList<>();

        //Inicia as listas de visitados e proximos
        visitados.add(start);
        proximos.add(start);

        while(!proximos.isEmpty()){
            //Retira da lista o nó atual de verificação
            Vertice<E> atual = proximos.poll();

            //Notifica listeners
            iterationListeners.forEach(e -> e.listenVertice(atual));

            //Adiciona os filhos a lista de próximos
            arestas.stream()
                .filter(aresta -> aresta.origem().equals(atual) && !visitados.contains(aresta.destino()))
                .forEach(aresta -> {
                    visitados.add(aresta.destino());
                    proximos.add(aresta.destino());
                });
        }
    }

    public void addIterationListener(IterationListener iterationListener){
        iterationListeners.add(iterationListener);
    }

}
