package mvc.model;

import mvc.model.algorithm.SearchAlgorithm;
import mvc.model.algorithm.uninformed.BreadthFirst;
import mvc.model.algorithm.uninformed.IterativDeepeningDepthFirstRekursive;
import mvc.model.algorithm.uninformed.IterativeDeepeningDepthFirst;
import mvc.model.algorithm.uninformed.UninformedAlgorithm;
import mvc.model.field.Field;

public class Main {


    public static void main(String[] args) {


        // 1. Feld setzten
        Field field = new Field(5);


/*        // Felder blocken
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
        field.blockNode(51);*/

        // Feld ausgeben
        //problem.drawField();
        // Algorithmus durchlaufen lassen
        SearchAlgorithm searchAlgorithm = new IterativDeepeningDepthFirstRekursive(field, 5, 24);
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

        /*

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
        AStar aStar = new AStar(field, 1520, 22, new DistanceFromTarget(field.getField()[22], field.getColumns()));
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
        */


    }
}
