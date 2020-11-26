package model.algorithm;

import model.field.Node;
import model.field.Field;
import model.field.NodeSnapShot;
import model.field.NodeType;

import java.util.*;

/**
 * Die Klasse SearchAlgortihm stellt als abstrakte Oberklasse Funktionen für die konkreten Algorithmen zur Verfügung.
 * Dabei muss jeder Such-Algorithmus der vom SearchAlgorithmus ableitet, seine Berechnung in der execute()-Methode definieren.
 *
 * @author Christian Graumann
 * @created 10.2019
 */
public abstract class SearchAlgorithm {

    protected Field field;

    protected Node source;
    protected Node target;

    protected List<Node> openList;
    protected List<Node> closeList;
    protected List<Node> path;

    /**
     * Die SnapShot-Liste ist verantwortlich für das Anzeigen des Algorithmus in der GUI. Sie enthält den
     * jeweiligen Status (OpenList, CloseLst ...) eines Knoten während der Berechnung des Algorithmus.
     * D.h. Wenn ein Algorithmus implementiert wird und ein Knoten z.B. in die openlist hinzugefügt wird,
     * dann muss er in der snapShot-Liste auch als SnapShot hinzugefügt werden.
     */
    private List<NodeSnapShot> snapShots;

    private long startTime;
    private long endTime;

    public SearchAlgorithm(Field field, int source, int target) {
        this.field = field;
        this.source = field.getField()[source];
        this.target = field.getField()[target];
        this.openList = new ArrayList<>();
        this.closeList = new ArrayList<>();
        this.path = new ArrayList<>();
        this.snapShots = new ArrayList<>();
    }

    public void calculate() {
        this.openList = new ArrayList<>();
        this.closeList = new ArrayList<>();
        this.path = new ArrayList<>();
        this.snapShots = new ArrayList<>();

        snapShots.add(new NodeSnapShot(source, NodeType.SOURCE));
        snapShots.add(new NodeSnapShot(target, NodeType.TARGET));

        startTime();
        execute();
        endTime();

    }

    /**
     * Diese Methode muss in jedem konkreten Such-Algorithmus die Handlungsvorschrift enthalten.
     */
    protected abstract void execute();

    /**
     * Diese Methode identifiziert für einen Algorithmus den berechneten kürzesten Pfad. Dabei wird ausgehend vom
     * Target-Knote alle Parent-Knoten bis zum Source-Knoten in eine Liste eingetragen.
     *
     * @param node
     */
    protected void tracePath(Node node) {

        List<Node> result = new ArrayList<>();
        Node currenNode = node;

        result.add(node);

        while (currenNode.getParent() != null) {
            result.add(currenNode.getParent());
            currenNode = currenNode.getParent();
            snapShots.add(new NodeSnapShot(currenNode, NodeType.PATH));
        }

        Collections.reverse(result);

        path = result;
    }


    protected void snapShotsAdd(Node node) {
        snapShots.add(new NodeSnapShot(node, node.getType()));
    }


    protected void closeListAdd(Node node) {
        snapShots.add(new NodeSnapShot(node, NodeType.CLOSELIST));
        closeList.add(node);
    }


    protected void pathAdd(Node node) {
        path.add(node);
        snapShots.add(new NodeSnapShot(node, NodeType.PATH));
    }

    public Field getField() {
        return field;
    }

    public Node getSource() {
        return source;
    }

    public Node getTarget() {
        return target;
    }

    public List<Node> getOpenList() {
        return openList;
    }

    public List<Node> getCloseList() {
        return closeList;
    }

    public List<Node> getPath() {
        return path;
    }

    public int getStorageComplexity() {
        return openList.size() + closeList.size();
    }

    private void startTime() {
        startTime = System.nanoTime();
    }

    private void endTime() {
        endTime = System.nanoTime();
    }

    public long getTime() {
        return endTime - startTime;
    }

    public List<NodeSnapShot> getSnapShots() {
        return snapShots;
    }

}
