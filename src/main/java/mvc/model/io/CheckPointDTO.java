package mvc.model.io;

import mvc.model.field.Field;

import java.util.Set;

public class CheckPointDTO {

    private int gridSize;
    private int gridFieldSize;
    private Set<Integer> blockSet;
    private Integer source;
    private Integer target;

    public CheckPointDTO() {
    }

    public CheckPointDTO(int gridSize, int gridFieldSize, Set<Integer> blockSet, Integer source, Integer target) {
        this.gridSize = gridSize;
        this.gridFieldSize = gridFieldSize;
        this.blockSet = blockSet;
        this.source = source;
        this.target = target;
    }

    public int getGridFieldSize() {
        return gridFieldSize;
    }

    public int getGridSize() {
        return gridSize;
    }

    public Set<Integer> getBlockSet() {
        return blockSet;
    }

    public Integer getSource() {
        return source;
    }

    public Integer getTarget() {
        return target;
    }

}
