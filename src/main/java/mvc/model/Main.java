package mvc.model;

import mvc.model.algorithm.AStar;
import mvc.model.algorithm.Bfs;
import mvc.model.algorithm.SucheMitEinheitlichenKosten;
import mvc.model.algorithm.Tfs;
import mvc.model.problem.Problem;

public class Main {

    public static void main(String[] args) {


        // 1. Feld setzten
        Problem problem = new Problem(50);
        /*
        // Felder blocken
        problem.blockNode(5);
        problem.blockNode(6);
        problem.blockNode(89);
        problem.blockNode(12);
        problem.blockNode(22);
        problem.blockNode(32);
        problem.blockNode(33);
        problem.blockNode(34);
        problem.blockNode(81);
        problem.blockNode(91);
        problem.blockNode(71);
        problem.blockNode(61);
        problem.blockNode(51);

        // Feld ausgeben
        //problem.drawField();

        // Algorithmus durchlaufen lassen
        Bfs bfs = new Bfs();
        bfs.calculate(problem, 1520, 18);

        // Algorithmus in das Feld eintragen
        problem.paintAlgorithmInToField(bfs);

        // Feld ausgeben
        System.out.println();
        problem.drawField();
        /*
        // Feld leeren vom Algortihmus
        System.out.println();
        problem.clearFieldFromAlgorithm();

        // Feld ausgeben
        System.out.println();
        problem.drawField();

        Tfs tfs = new Tfs();
        tfs.calculate(problem, 1520, 18);
        problem.paintAlgorithmInToField(tfs);
        System.out.println();
        problem.drawField();
        */

        System.out.println();
        problem.clearFieldFromAlgorithm();

        // Algorithmus durchlaufen lassen
        SucheMitEinheitlichenKosten smek = new SucheMitEinheitlichenKosten();
        smek.calculate(problem, 1520, 18);

        // Algorithmus in das Feld eintragen
        problem.paintAlgorithmInToField(smek);

        // Feld ausgeben
        System.out.println();
        problem.drawField();

        System.out.println();
        problem.clearFieldFromAlgorithm();


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
        AStar aStar = new AStar();
        aStar.calculate(problem, 1520, 22);


        // Algorithmus in das Feld eintragen
        problem.paintAlgorithmInToField(aStar);

        // Feld ausgeben
        System.out.println();
        problem.drawField();
    }
}
