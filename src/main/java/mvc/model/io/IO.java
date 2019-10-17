package mvc.model.io;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class IO {


    public CheckPointDTO load(String file) {

        try {
            return new ObjectMapper().readValue(new File(file), CheckPointDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    public void save(String file, int gridSize, Set<Integer> blockSet, Integer source, Integer target){

        try {
            new ObjectMapper().writeValue(new File(file +".json"), new CheckPointDTO(gridSize, blockSet, source, target));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {
        IO io = new IO();
        io.save("db/field", 15, new HashSet<Integer>(), 10, 15);
        io.save("db/field", 0, null, null, null);

        System.out.println(io.load("db/field.json").getGridSize());
        System.out.println(io.load("db/field.json").getBlockSet());
        System.out.println(io.load("db/field.json").getSource());
        System.out.println(io.load("db/field.json").getTarget());
    }

}
