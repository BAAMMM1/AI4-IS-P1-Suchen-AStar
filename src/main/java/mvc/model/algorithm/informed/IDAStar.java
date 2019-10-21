package mvc.model.algorithm.informed;

import mvc.model.algorithm.informed.heurisitc.Heuristic;
import mvc.model.field.Field;

public class IDAStar extends InformedAlgorithm {

    public IDAStar(Field field, int source, int target, Heuristic heuristic) {
        super(field, source, target, heuristic);
    }

    /*
     procedure ida_star(root)
       bound := h(root)
       path := [root]
       loop
         t := search(path, 0, bound)
         if t = FOUND then return (path, bound)
         if t = ∞ then return NOT_FOUND
         bound := t
       end loop
     end procedure

     function search(path, g, bound)
       node := path.last
       f := g + h(node)
       if f > bound then return f
       if is_goal(node) then return FOUND
       min := ∞
       for succ in successors(node) do
         if succ not in path then
           path.push(succ)
           t := search(path, g + cost(node, succ), bound)
           if t = FOUND then return FOUND
           if t < min then min := t
           path.pop()
         end if
       end for
       return min
     end function
     */
    @Override
    public void execute() {
        int bound = heuristic.hCost(source);
        boolean run = true;

        while(run){



        }



    }

}
