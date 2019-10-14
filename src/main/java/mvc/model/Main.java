package mvc.model;

import mvc.model.algorithm.informed.AStar;
import mvc.model.algorithm.informed.DistanceFromTarget;
import mvc.model.algorithm.uninformed.BreadthFirst;
import mvc.model.algorithm.uninformed.UniformCost;
import mvc.model.algorithm.uninformed.DepthFirst;
import mvc.model.field.Field;

public class Main {

    public static void main(String[] args) {


        // 1. Feld setzten
        Field field = new Field(50);


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
        System.out.println(1);
        // Algorithmus durchlaufen lassen
        BreadthFirst bfs = new BreadthFirst(field, 1520, 18);
        bfs.calculate();

        // Algorithmus in das Feld eintragen
        field.paintAlgorithmInToFieldWithDepth(bfs);

        // Feld ausgeben
        System.out.println();
        field.drawField();


        // Feld leeren vom Algortihmus
        System.out.println();
        field.clearFieldFromAlgorithm();

        field = new Field(50);


        DepthFirst tfs = new DepthFirst(field, 1520, 18);
        tfs.calculate();
        field.paintAlgorithmInToField(tfs);
        System.out.println();
        field.drawField();


        System.out.println();
        field.clearFieldFromAlgorithm();


        field = new Field(50);
        // Algorithmus durchlaufen lassen
        UniformCost smek = new UniformCost(field, 1520, 18);
        smek.calculate();

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
        AStar aStar = new AStar(field, 1520, 22, new DistanceFromTarget(field.getField()[22], field.getColumns()));
        aStar.calculate();


        // Algorithmus in das Feld eintragen
        field.paintAlgorithmInToFieldWithDepth(aStar);

        // Feld ausgeben
        System.out.println();
        field.drawField();


    }
}
