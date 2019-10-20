package mvc.model;

import mvc.model.algorithm.SearchAlgorithm;
import mvc.model.algorithm.informed.InformedAlgorithm;
import mvc.model.algorithm.informed.heurisitc.Heuristic;
import mvc.model.algorithm.uninformed.UninformedAlgorithm;
import mvc.model.field.Field;
import mvc.model.field.Node;
import mvc.model.field.NodeSnapShot;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

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
}
