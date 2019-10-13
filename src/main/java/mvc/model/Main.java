package mvc.model;

import mvc.model.algorithm.informed.AStar;
import mvc.model.algorithm.uninformed.BreadthFirst;
import mvc.model.algorithm.uninformed.UniformCost;
import mvc.model.algorithm.uninformed.DepthFirst;
import mvc.model.problem.Problem;

public class Main {

    public static void main(String[] args) {


        // 1. Feld setzten
        Problem problem = new Problem(50);


        // Felder blocken
        problem.blockNode(5);
        problem.blockNode(6);
        problem.blockNode(89);
        problem.blockNode(12);
        problem.blockNode(29);
        //problem.blockNode(22);
        problem.blockNode(32);
        problem.blockNode(33);
        problem.blockNode(34);
        problem.blockNode(81);
        problem.blockNode(91);
        problem.blockNode(71);
        problem.blockNode(70);
        problem.blockNode(61);
        problem.blockNode(51);

        // Feld ausgeben
        //problem.drawField();
        System.out.println(1);
        // Algorithmus durchlaufen lassen
        BreadthFirst bfs = new BreadthFirst(problem, 1520, 18);
        bfs.calculate();

        // Algorithmus in das Feld eintragen
        problem.paintAlgorithmInToFieldWithDepth(bfs);

        // Feld ausgeben
        System.out.println();
        problem.drawField();

        // Feld leeren vom Algortihmus
        System.out.println();
        problem.clearFieldFromAlgorithm();

        problem = new Problem(50);


        DepthFirst tfs = new DepthFirst(problem, 1520, 18);
        tfs.calculate();
        problem.paintAlgorithmInToField(tfs);
        System.out.println();
        problem.drawField();


        System.out.println();
        problem.clearFieldFromAlgorithm();

        problem = new Problem(50);
        // Algorithmus durchlaufen lassen
        UniformCost smek = new UniformCost(problem, 1520, 18);
        smek.calculate();

        // Algorithmus in das Feld eintragen
        problem.paintAlgorithmInToField(smek);

        // Feld ausgeben
        System.out.println();
        problem.drawField();

        // Feld leeren vom Algortihmus
        System.out.println();
        problem.clearFieldFromAlgorithm();

        problem = new Problem(50);

        problem.blockNode(67);
        problem.blockNode(68);
        problem.blockNode(69);
        problem.blockNode(70);
        problem.blockNode(71);
        problem.blockNode(121);
        problem.blockNode(122);
        problem.blockNode(123);
        problem.blockNode(124);
        problem.blockNode(125);
        problem.blockNode(126);
        problem.blockNode(127);
        problem.blockNode(128);



        // Algorithmus durchlaufen lassen
        AStar aStar = new AStar(problem, 1520, 22);
        aStar.calculate();


        // Algorithmus in das Feld eintragen
        problem.paintAlgorithmInToField(aStar);

        // Feld ausgeben
        System.out.println();
        problem.drawField();
    }
}
