package mvc.model;

import mvc.model.algorithm.SearchAlgorithm;
import mvc.model.algorithm.informed.AStar;
import mvc.model.algorithm.informed.AStarHeuristic;
import mvc.model.field.Field;
import mvc.model.field.Node;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class PathFinding {


    private Map<String, String> algorithmMap;
    private Map<String, String> heuristicMap;
    private List<Integer> openList;
    private List<Integer> closeList;
    private List<Integer> path;

    public PathFinding() {
        algorithmMap = new HashMap<String, String>();
        heuristicMap = new HashMap<String, String>();
        openList = new ArrayList<>();
        closeList = new ArrayList<>();
        path = new ArrayList<>();
        initAlgorithms();
        initHeuristic();
    }


    public void calc(int fieldColumns, Set<Integer> blockSet, String algortihmName, int source, int target){
        System.out.println("Führe Pathfinding aus: FieldRows: " + fieldColumns + " Algorithmus: " + algortihmName + " Source: " + source + " Target: " + target);
        Field field = new Field(fieldColumns);
        openList = new ArrayList<>();
        closeList = new ArrayList<>();
        path = new ArrayList<>();

        field.blockNode(blockSet);

        SearchAlgorithm algorithm = getAlgorithm(algortihmName, field, source, target);
        algorithm.calculate();

        for(Node node: algorithm.getOpenList()){
            openList.add(node.getZustand());
        }

        for(Node node: algorithm.getCloseList()){
            closeList.add(node.getZustand());
        }

        for(Node node: algorithm.getPath()){
            path.add(node.getZustand());
        }


    }

    public void calc(int fieldColumns, Set<Integer> blockSet, String algortihmName, String aStarHeuristic, int source, int target){

        System.out.println("Führe Pathfinding aus: FieldRows: " + fieldColumns + " Algorithmus: " + algortihmName + " Source: " + source + " Target: " + target);
        Field field = new Field(fieldColumns);
        openList = new ArrayList<>();
        closeList = new ArrayList<>();
        path = new ArrayList<>();

        field.blockNode(blockSet);

        SearchAlgorithm algorithm;

        if(aStarHeuristic != null && algortihmName.equals("AStar")){

            algorithm = new AStar(field, source, target, getHeuristic(aStarHeuristic, field.getField()[target], fieldColumns));
            algorithm.calculate();

            for(Node node: algorithm.getOpenList()){
                openList.add(node.getZustand());
            }

            for(Node node: algorithm.getCloseList()){
                closeList.add(node.getZustand());
            }

            for(Node node: algorithm.getPath()){
                path.add(node.getZustand());
            }

        } else {

            algorithm = getAlgorithm(algortihmName, field, source, target);
            algorithm.calculate();

            for(Node node: algorithm.getOpenList()){
                openList.add(node.getZustand());
            }

            for(Node node: algorithm.getCloseList()){
                closeList.add(node.getZustand());
            }

            for(Node node: algorithm.getPath()){
                path.add(node.getZustand());
            }
        }







    }


    public List<Integer> getOpenList() {
        return openList;
    }

    public List<Integer> getCloseList() {
        return closeList;
    }

    public List<Integer> getPath() {
        return path;
    }

    private void initAlgorithms()  {

        Reflections reflections = new Reflections("mvc.model.algorithm", new SubTypesScanner(false));
        Set<Class<? extends SearchAlgorithm>> searchAlgortihms = reflections.getSubTypesOf(SearchAlgorithm.class);

        for(Class object: searchAlgortihms){
            algorithmMap.put(object.getSimpleName(), object.getName());
        }

        System.out.println("Verfügbare Algorithmen: " + algorithmMap.keySet());
    }

    private void initHeuristic()  {

        Reflections reflections = new Reflections("mvc.model.algorithm", new SubTypesScanner(false));
        Set<Class<? extends AStarHeuristic>> heuristics = reflections.getSubTypesOf(AStarHeuristic.class);

        for(Class object: heuristics){
            heuristicMap.put(object.getSimpleName(), object.getName());
        }

        System.out.println("Verfügbare Heuristiken: " + heuristicMap.keySet());
    }
    
    public List<String> avaiableAlgortihm(){
        List<String> result = new ArrayList<>();
        result.addAll(algorithmMap.keySet());
        System.out.println("Übergebe Algorithmen: " + result);
        return result;
    }

    public List<String> avaiableHeuristic(){
        List<String> result = new ArrayList<>();
        result.addAll(heuristicMap.keySet());
        System.out.println("Übergebe Heuristiken: " + result);
        return result;
    }

    public List<String> avaiableInformedAlgorithm(){
        List<String> result = new ArrayList<>();
        result.add("AStar");
        System.out.println("Übergebe informierte Algorithmen: " + result);
        return result;
    }

    private SearchAlgorithm getAlgorithm(String algorithmName, Field field, int source, int target){


        SearchAlgorithm algorithm = null;

        try {
            Class<?> clazz = Class.forName(algorithmMap.get(algorithmName));

            Constructor<?> ctor = clazz.getConstructors()[0];

            algorithm = (SearchAlgorithm) ctor.newInstance( field, source, target);

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return algorithm;

    }

    private AStarHeuristic getHeuristic(String heuristicName, Node target, int columns){


        AStarHeuristic heuristic = null;

        try {
            Class<?> clazz = Class.forName(heuristicMap.get(heuristicName));

            Constructor<?> ctor = clazz.getConstructors()[0];

            heuristic = (AStarHeuristic) ctor.newInstance(target, columns);

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return heuristic;

    }


    public static void main(String[] args) {
        PathFinding pathFinding = new PathFinding();

        System.out.println(pathFinding.avaiableAlgortihm());
        System.out.println(pathFinding.avaiableHeuristic());

        pathFinding.calc(15,null, "DepthFirst", null, 1, 10);
        pathFinding.calc(15,null, "UniformCost", null, 1, 10);
        pathFinding.calc(15,null, "AStar", null, 1, 10);
        pathFinding.calc(15,null, "BreadthFirst", null, 1, 10);
        pathFinding.calc(15,null, "AStar", "EuclideanDistance", 1, 10);




    }
}
