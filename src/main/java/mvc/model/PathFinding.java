package mvc.model;

import mvc.model.algorithm.SearchAlgorithm;
import mvc.model.field.Field;
import mvc.model.field.Node;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class PathFinding {


    private Map<String, String> algorithmMap;
    private List<Integer> openList;
    private List<Integer> closeList;
    private List<Integer> path;

    public PathFinding() {
        algorithmMap = new HashMap<String, String>();
        openList = new ArrayList<>();
        closeList = new ArrayList<>();
        path = new ArrayList<>();
        initAlgorithms();
    }


    public void calc(int fieldColumns, Set<Integer> blockSet, String algortihmName, int source, int target){
        System.out.println("Führe Pathfinding aus: FieldRows: " + fieldColumns + " Algorithmus: " + algortihmName + " Source: " + source + " Target: " + target);
        Field field = new Field(fieldColumns);

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
    
    public List<String> avaiableAlgortihm(){
        List<String> result = new ArrayList<>();
        result.addAll(algorithmMap.keySet());
        System.out.println("Übergebe Algorithmen: " + result);
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


    public static void main(String[] args) {
        PathFinding pathFinding = new PathFinding();

        pathFinding.calc(15,null, "BreadthFirst", 1, 10);



    }
}
