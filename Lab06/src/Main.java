import base.Baralho;
import base.Carta;
import base.cartas.Lacaio;
import base.cartas.magias.Buff;
import base.cartas.magias.Dano;
import base.cartas.magias.DanoArea;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by rafael on 29/04/17.
 */
public class Main {
    public static final String ARRAY_LIST = ArrayList.class.toString();
    public static final String LINKED_LIST = LinkedList.class.toGenericString();
    public static final String HASH_SET = HashSet.class.toString();
    public static final String TREE_SET = HashSet.class.toString();

    public static void main (String[] args) {

        //Teste para os métodos equals() de Carta e Baralho
        Carta c1 = new Carta(new UUID(2, 0), "Prueba Uno", 3);
        Carta c3 = new Carta(new UUID(2, 0), "Prueba Dos", 3);
        Carta c2 = new Carta(new UUID(2, 0), "Prueba Tres", 3);

        System.out.println ("C1 equals C2: " + c1.equals(c2));
        System.out.println ("C1 equals C3: " + c1.equals(c3));

        Baralho b1 = new Baralho();
        b1.adicionarCarta(c1);
        b1.adicionarCarta(c2);
        b1.adicionarCarta(c3);

        Baralho b2 = new Baralho();
        b2.adicionarCarta(c1);
        b2.adicionarCarta(c3);
        b2.adicionarCarta(c2);

        System.out.println ("B1 equals B2: " + b1.equals(b2));

        //Tarefa 2 itens 1-4:
        ArrayList<Carta> arrayList = (ArrayList<Carta>)initializeCollection10000Items(ArrayList.class);
        LinkedList<Carta> linkedList = (LinkedList<Carta>) initializeCollection10000Items(LinkedList.class);

        printTimeToGetListItems(arrayList);
        printTimeToGetListItems(linkedList);
        printTimeToContainsCollectionItems(arrayList);
        printTimeToContainsCollectionItems(linkedList);

        //Tarefa 2 item 5
        ArrayList<Carta> arrayListRepeated = (ArrayList<Carta>) initializeListRepeatedItems(ArrayList.class);
        LinkedList<Carta> linkedListRepeated = (LinkedList<Carta>) initializeListRepeatedItems(LinkedList.class);
        Vector<Carta> vectorListRepeated = (Vector<Carta>) initializeListRepeatedItems(Vector.class);

        System.out.println(arrayListRepeated);
        System.out.println(linkedListRepeated);
        System.out.println(vectorListRepeated);

        //Tarefa 3 itens 1 e 2:
        HashSet<Carta> hashSet = (HashSet<Carta>) initializeCollection10000Items(HashSet.class);
        TreeSet<Carta> treeSet = (TreeSet<Carta>) initializeCollection10000Items(TreeSet.class);

        printTimeToContainsCollectionItems(hashSet);
        printTimeToContainsCollectionItems(treeSet);

        HashSet<Carta> hashSetRepeated = (HashSet<Carta>) initializeListRepeatedItems(HashSet.class);
        TreeSet<Carta> treeSetRepeated = (TreeSet<Carta>) initializeListRepeatedItems(TreeSet.class);

        System.out.println(hashSetRepeated);
        System.out.println(treeSetRepeated);

        //Atividade 4:
        ArrayList<Carta> cardCollection = initializeCardCollection();

         Carta lacaio = cardCollection.stream()
                .filter(carta -> carta.getClass().equals(Lacaio.class))
                .sorted((carta1, carta2) -> {
                    Lacaio lac1 = (Lacaio) carta1;
                    Lacaio lac2 = (Lacaio) carta2;
                    return lac2.getAtaque() - lac1.getAtaque();
                })
                .findFirst()
                 .orElse(null);

        System.out.println(lacaio);

         int ataqueTotalLacaios = cardCollection.stream()
                 .filter(carta -> carta.getClass().equals(Lacaio.class))
                 .mapToInt(carta -> {
                     Lacaio lac = (Lacaio) carta;
                     return lac.getAtaque();
                 })
                 .sum();

        System.out.println("A soma total dos ataques é: " + ataqueTotalLacaios);

        List<Carta> lacaiosOrdenadosPorVida = cardCollection.stream()
                .filter(carta -> carta.getClass().equals(Lacaio.class))
                .sorted((carta1, carta2) -> {
                    Lacaio lacaio1 = (Lacaio) carta1;
                    Lacaio lacaio2 = (Lacaio) carta2;
                    return lacaio1.getVidaAtual()-lacaio2.getVidaAtual();
                })
                .collect(Collectors.toList());

        System.out.println(lacaiosOrdenadosPorVida);
    }

    static void printTimeToGetListItems(List<Carta> list) {
        long initialTime = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            list.get(i);
        }
        System.out.println("A operação \"get\" com " + list.getClass().getSimpleName() + " demorou: " + (System.nanoTime()-initialTime)/1000000 + "ms");
    }

    static void printTimeToContainsCollectionItems(Collection<Carta> collection) {
        long totalTime =0, initialTime;
        for (Carta carta: collection) {
            initialTime = System.nanoTime();
            collection.contains(carta);
            totalTime+=(System.nanoTime()-initialTime);
        }
        System.out.println("A operação \"contains\" com " + collection.getClass().getSimpleName() + " demorou: " + totalTime/1000000 + "ms");
    }

    static Collection<Carta> initializeCollection10000Items (Class model) {
        Collection<Carta> collection;
        switch (model.getSimpleName()) {
            case "ArrayList":
                collection = new ArrayList<>();
                break;
            case "LinkedList":
                collection = new LinkedList<>();
                break;
            case "HashSet":
                collection = new HashSet<>();
                break;
            case "TreeSet":
                collection = new TreeSet<>((Carta c1, Carta c2) -> {
                    return c1.getID().compareTo(c2.getID());
                });
                break;
            default:
                collection = Collections.emptyList();
        }

        for (int i = 0; i < 10000; i++) {
            collection.add(new Carta(new UUID(i, 0), "LUL", 3));
        }
        return collection;
    }

    static Collection<Carta> initializeListRepeatedItems (Class model) {
        Collection<Carta> collection;

        switch (model.getSimpleName()) {
            case "ArrayList":
                collection = new ArrayList<>();
                break;
            case "LinkedList":
                collection = new LinkedList<>();
                break;
            case "Vector":
                collection = new Vector<>();
                break;
            case "HashSet":
                collection = new HashSet<>();
                break;
            case "TreeSet":
                collection = new TreeSet<>();
                break;
            default:
                collection = Collections.emptyList();
        }

        Carta carta = new Carta (new UUID(1, 0), "Carta Repetida", 2);
        collection.add(carta);
        collection.add(carta);
        return collection;
    }

    static ArrayList<Carta> initializeCardCollection () {
        ArrayList<Carta> cardCollection = new ArrayList<>();
        cardCollection.add(new Lacaio("Gimli", 4, 3, 4, 4));
        cardCollection.add(new Lacaio("Legolas", 5, 6, 3, 3));
        cardCollection.add(new Lacaio("Aragorn", 5, 4, 5, 5));
        cardCollection.add(new Buff("Encanto de Sauron", 3, 2, 2));
        cardCollection.add(new Dano("Fúria dos Oliphaunts", 4, 7));
        cardCollection.add(new DanoArea("Ataque das Águias", 5, 4));
        return cardCollection;
    }
}
