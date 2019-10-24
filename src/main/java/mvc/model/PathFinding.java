package mvc.model;

import mvc.model.algorithm.SearchAlgorithm;
import mvc.model.algorithm.informed.AStar;
import mvc.model.algorithm.informed.InformedAlgorithm;
import mvc.model.algorithm.informed.heurisitc.DiagonalDistance;
import mvc.model.algorithm.informed.heurisitc.EuclideanDistance;
import mvc.model.algorithm.informed.heurisitc.Heuristic;
import mvc.model.algorithm.informed.heurisitc.ManhattenDistance;
import mvc.model.algorithm.uninformed.BreadthFirst;
import mvc.model.algorithm.uninformed.DepthFirst;
import mvc.model.algorithm.uninformed.UniformCost;
import mvc.model.algorithm.uninformed.UninformedAlgorithm;
import mvc.model.field.Field;
import mvc.model.field.Node;
import mvc.model.field.NodeSnapShot;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Diese Klasse stellt die Logik für die GUI zur Verfügung.
 * Dadurch ist es möglich in der GUI nur über das Pathfinding-Objekt mit der Logik zu kommunizieren.
 *
 * @author Christian Graumann
 * @created 10.2019
 */
public class PathFinding {


    private Map<String, String> uninformedMap;
    private Map<String, String> informedMap;
    private Map<String, String> heuristicMap;

    private SearchAlgorithm searchAlgorithm;


    public PathFinding() {
        uninformedMap = new HashMap<>();
        heuristicMap = new HashMap<>();
        informedMap = new HashMap<>();
        initUninformedAlgorithms();
        initInformedAlgorithm();
        initHeuristic();
    }

    // commit
    public void uninformedCalc(int fieldColumns, Set<Integer> blockSet, String uninformedAlgorithmName, int source, int target) throws Exception {
        System.out.println("Pathfinding uninformiert: FieldRows: " + fieldColumns + " Algorithmus: "
                + uninformedAlgorithmName + " Source: " + source + " Target: " + target);

        if (!uninformedMap.containsKey(uninformedAlgorithmName))
            throw new IllegalArgumentException("not correct uninformied algortihm");

        Field field = new Field(fieldColumns);
        field.blockNode(blockSet);

        searchAlgorithm = loadUnInformedAlgorithm(uninformedAlgorithmName, field, source, target);
        searchAlgorithm.calculate();

    }


    public void informedCalc(int fieldColumns, Set<Integer> blockSet, String informedAlgorithmName, String heuristicName, int source, int target) throws Exception {
        System.out.println("Pathfinding informiert: FieldRows: " + fieldColumns + " Algorithmus: "
                + informedAlgorithmName + " Heuristik: " + heuristicName + " Source: " + source + " Target: " + target);

        if (!informedMap.containsKey(informedAlgorithmName))
            throw new IllegalArgumentException("not correct informed algorithm");
        if (!heuristicMap.containsKey(heuristicName))
            throw new IllegalArgumentException("not correct heuristic");

        Field field = new Field(fieldColumns);
        field.blockNode(blockSet);

        searchAlgorithm = loadInformedAlgorithm(informedAlgorithmName, heuristicName, field, source, target);
        searchAlgorithm.calculate();

    }


    private void initUninformedAlgorithms() {

        Set<Class<? extends UninformedAlgorithm>> searchAlgortihms = new Reflections("mvc.model.algorithm",
                new SubTypesScanner(false))
                .getSubTypesOf(UninformedAlgorithm.class);

        for (Class object : searchAlgortihms) {
            if (!Modifier.isAbstract(object.getModifiers())) {
                uninformedMap.put(object.getSimpleName(), object.getName());
            }
        }
    }


    private void initHeuristic() {

        Set<Class<? extends Heuristic>> informedAlgorithms = new Reflections("mvc.model.algorithm",
                new SubTypesScanner(false))
                .getSubTypesOf(Heuristic.class);

        for (Class object : informedAlgorithms) {
            if (!Modifier.isAbstract(object.getModifiers())) {
                heuristicMap.put(object.getSimpleName(), object.getName());
            }
        }
    }

    private void initInformedAlgorithm() {

        Set<Class<? extends InformedAlgorithm>> heuristics = new Reflections("mvc.model.algorithm",
                new SubTypesScanner(false))
                .getSubTypesOf(InformedAlgorithm.class);

        for (Class object : heuristics) {
            if (!Modifier.isAbstract(object.getModifiers())) {
                informedMap.put(object.getSimpleName(), object.getName());
            }
        }
    }


    private UninformedAlgorithm loadUnInformedAlgorithm(String algorithmName, Field field, int source, int target) throws Exception {

        return (UninformedAlgorithm) Class.forName(uninformedMap.get(algorithmName))
                .getConstructors()[0].newInstance(field, source, target);

    }

    private InformedAlgorithm loadInformedAlgorithm(String algorithmName, String heuristicName, Field field, int source, int target) throws Exception {

        Heuristic heuristic = loadHeuristic(heuristicName, field.getField()[target], field.getColumns());

        return (InformedAlgorithm) Class.forName(informedMap.get(algorithmName))
                .getConstructors()[0].newInstance(field, source, target, heuristic);

    }

    private Heuristic loadHeuristic(String heuristicName, Node target, int columns) throws Exception {

        return (Heuristic) Class.forName(heuristicMap.get(heuristicName))
                .getConstructors()[0].newInstance(target, columns);

    }

    public List<String> getUninformedAlgorithm() {
        return new ArrayList<>(uninformedMap.keySet());
    }

    public List<String> getHeuristic() {
        return new ArrayList<>(heuristicMap.keySet());
    }

    public List<String> getInformedAlgorithm() {
        return new ArrayList<>(informedMap.keySet());
    }

    public List<Integer> getOpenList() {
        if(searchAlgorithm == null) return new ArrayList<>();
        return searchAlgorithm.getOpenList().stream().map(Node::getZustand).collect(Collectors.toList());
    }

    public List<Integer> getCloseList() {
        if(searchAlgorithm == null) return new ArrayList<>();
        return searchAlgorithm.getCloseList().stream().map(Node::getZustand).collect(Collectors.toList());
    }

    public List<Integer> getPath() {
        if(searchAlgorithm == null) return new ArrayList<>();
        return searchAlgorithm.getPath().stream().map(Node::getZustand).collect(Collectors.toList());
    }

    public List<NodeSnapShot> getSnapShots(){
        return searchAlgorithm.getSnapShots();
    }




    public SearchAlgorithm getSearchAlgorithm() {
        return searchAlgorithm;
    }

    public long getMeasuredTime(){
        return searchAlgorithm.getTime();
    }

    public int getNodeSize(){
        return searchAlgorithm.getField().getField().length;
    }

    public int getBlockedSize(){
        return searchAlgorithm.getField().getBlockSet().size();
    }

    public int getFreeSize(){
        return getNodeSize() - getBlockedSize();
    }

    public static void main(String[] args) throws Exception {

        PathFinding pathFinding = new PathFinding();

        pathFinding.uninformedCalc(15, null, "BreadthFirst", 1, 10);
        System.out.println(pathFinding.getOpenList());

        pathFinding.uninformedCalc(15, null, "DepthFirst", 1, 10);
        System.out.println(pathFinding.getOpenList());

        pathFinding.uninformedCalc(15, null, "BreadthFirst", 1, 10);
        System.out.println(pathFinding.getOpenList());

        pathFinding.informedCalc(15, null, "AStar", "ManhattenDistance", 1, 10);
        System.out.println(pathFinding.getOpenList());

        pathFinding.informedCalc(15, null, "AStar", "EuclideanDistance", 1, 10);
        System.out.println(pathFinding.getOpenList());

        // 1. Feld setzten
        Field field = new Field(10);

       // Felder blocken
        field.blockNode(5);
        field.blockNode(6);
        field.blockNode(89);
        field.blockNode(12);
        field.blockNode(29);
        //problem.blockNode(22);
        field.blockNode(32);
        field.blockNode(33);
        field.blockNode(34);
        field.blockNode(81);
        field.blockNode(91);
        field.blockNode(71);
        field.blockNode(70);
        field.blockNode(61);
        field.blockNode(51);

        // Feld ausgeben
        //problem.drawField();
        // Algorithmus durchlaufen lassen
        SearchAlgorithm searchAlgorithm = new BreadthFirst(field, 0, 24);
        searchAlgorithm.calculate();
        //System.out.println(searchAlgorithm.getSnapShots());
        //System.out.println(searchAlgorithm.getOpenList());
        System.out.println("BFS");
        System.out.println("Storage: " + searchAlgorithm.getStorageComplexity());
        System.out.println("Time: " + searchAlgorithm.getTime());
        System.out.println(searchAlgorithm.getPath());
        System.out.println(searchAlgorithm.getSnapShots());
        System.out.println(searchAlgorithm.getOpenList().size());
        System.out.println(searchAlgorithm.getCloseList().size());

        // Algorithmus in das Feld eintragen
        field.paintAlgorithmInToFieldWithDepth(searchAlgorithm);

        // Feld ausgeben
        System.out.println();
        field.drawField();

        // Feld leeren vom Algortihmus
        System.out.println();
        field.clearFieldFromAlgorithm();

        field = new Field(50);

        DepthFirst tfs = new DepthFirst(field, 1520, 18);
        tfs.calculate();
        System.out.println("TFS");
        System.out.println("Storage: " + tfs.getStorageComplexity());
        System.out.println("Time: " + tfs.getTime());

        field.paintAlgorithmInToField(tfs);
        System.out.println();
        field.drawField();

        System.out.println();
        field.clearFieldFromAlgorithm();

        field = new Field(50);
        // Algorithmus durchlaufen lassen
        UniformCost smek = new UniformCost(field, 1520, 18);
        smek.calculate();

        System.out.println("SMEK");
        System.out.println("Storage: " + smek.getStorageComplexity());
        System.out.println("Time: " + smek.getTime());

        // Algorithmus in das Feld eintragen
        field.paintAlgorithmInToFieldWithDepth(smek);

        // Feld ausgeben
        System.out.println();
        field.drawField();

        // Feld leeren vom Algortihmus
        System.out.println();
        field.clearFieldFromAlgorithm();

        field = new Field(50);

        field.blockNode(67);
        field.blockNode(68);
        field.blockNode(69);
        field.blockNode(70);
        field.blockNode(71);
        field.blockNode(121);
        field.blockNode(122);
        field.blockNode(123);
        field.blockNode(124);
        field.blockNode(125);
        field.blockNode(126);
        field.blockNode(127);
        field.blockNode(128);

        // Algorithmus durchlaufen lassen
        AStar aStar = new AStar(field, 1520, 22, new ManhattenDistance(field.getField()[22], field.getColumns()));
        aStar.calculate();
        System.out.println("AStar");
        System.out.println("Storage: " + aStar.getStorageComplexity());
        System.out.println("Time: " + aStar.getTime());

        // Algorithmus in das Feld eintragen
        field.paintAlgorithmInToFieldWithDepth(aStar);

        // Feld ausgeben
        System.out.println();
        field.drawField();

        // Feld leeren vom Algortihmus
        System.out.println();
        field.clearFieldFromAlgorithm();

        field = new Field(50);

        field.blockNode(67);
        field.blockNode(68);
        field.blockNode(69);
        field.blockNode(70);
        field.blockNode(71);
        field.blockNode(121);
        field.blockNode(122);
        field.blockNode(123);
        field.blockNode(124);
        field.blockNode(125);
        field.blockNode(126);
        field.blockNode(127);
        field.blockNode(128);


        // Algorithmus durchlaufen lassen
        AStar aStar2 = new AStar(field, 1520, 22, new DiagonalDistance(field.getField()[22], field.getColumns()));
        aStar2.calculate();

        // Algorithmus in das Feld eintragen
        field.paintAlgorithmInToFieldWithDepth(aStar2);

        // Feld ausgeben
        System.out.println();
        field.drawField();

        // Feld leeren vom Algortihmus
        System.out.println();
        field.clearFieldFromAlgorithm();

        field = new Field(50);

        field.blockNode(67);
        field.blockNode(68);
        field.blockNode(69);
        field.blockNode(70);
        field.blockNode(71);
        field.blockNode(121);
        field.blockNode(122);
        field.blockNode(123);
        field.blockNode(124);
        field.blockNode(125);
        field.blockNode(126);
        field.blockNode(127);
        field.blockNode(128);

        // Algorithmus durchlaufen lassen
        AStar aStar4 = new AStar(field, 1520, 22, new EuclideanDistance(field.getField()[22], field.getColumns()));
        aStar4.calculate();

        // Algorithmus in das Feld eintragen
        field.paintAlgorithmInToFieldWithDepth(aStar4);

        // Feld ausgeben
        System.out.println();
        field.drawField();

        // Feld leeren vom Algortihmus
        System.out.println();
        field.clearFieldFromAlgorithm();

        field = new Field(50);

        field.blockNode(67);
        field.blockNode(68);
        field.blockNode(69);
        field.blockNode(70);
        field.blockNode(71);
        field.blockNode(121);
        field.blockNode(122);
        field.blockNode(123);
        field.blockNode(124);
        field.blockNode(125);
        field.blockNode(126);
        field.blockNode(127);
        field.blockNode(128);

        // Algorithmus durchlaufen lassen
        AStar aStar5 = new AStar(field, 1520, 22, new ManhattenDistance(field.getField()[22], field.getColumns()));
        aStar5.calculate();

        // Algorithmus in das Feld eintragen
        field.paintAlgorithmInToFieldWithDepth(aStar5);

        // Feld ausgeben
        System.out.println();
        field.drawField();

    }
}
