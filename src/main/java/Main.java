import algorithm.Bfs;
import algorithm.Tfs;
import problem.Problem;

public class Main {

    public static void main(String[] args) {

        // 1. Feld setzten
        Problem problem = new Problem(10);

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
        problem.drawField();

        // Algorithmus durchlaufen lassen
        Bfs bfs = new Bfs();
        bfs.calculate(problem, 55, 18);

        // Algorithmus in das Feld eintragen
        problem.paintAlgorithmInToField(bfs);

        // Feld ausgeben
        System.out.println();
        problem.drawField();

        // Feld leeren vom Algortihmus
        System.out.println();
        problem.clearFieldFromAlgorithm();

        // Feld ausgeben
        System.out.println();
        problem.drawField();

        Tfs tfs = new Tfs();
        tfs.calculate(problem, 55, 18);
        problem.paintAlgorithmInToField(tfs);
        System.out.println();
        problem.drawField();

    }
}
