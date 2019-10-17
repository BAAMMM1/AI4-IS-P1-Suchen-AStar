package mvc.model.io;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

public class IO {


    public CheckPointDTO load(URI file) {

        try {
            return new ObjectMapper().readValue(new File(file), CheckPointDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void save(URI file, int gridSize, Set<Integer> blockSet, Integer source, Integer target){

        System.out.println("--> " + file.toString());
        File fil2 = new File(file);
        System.out.println(fil2.toString());
        try {
            new ObjectMapper().writeValue(new File(file), new CheckPointDTO(gridSize, blockSet, source, target));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {
        IO io = new IO();
        /*
        io.save("db/field", 15, new HashSet<Integer>(), 10, 15);
        io.save("db/field", 0, null, null, null);
        io.save("d:/Audio/example", 15, null, 10, 15);
         */

        /*
        System.out.println(io.load("db/field.json").getGridSize());
        System.out.println(io.load("db/field.json").getBlockSet());
        System.out.println(io.load("db/field.json").getSource());
        System.out.println(io.load("db/field.json").getTarget());

        System.out.println(io.load("d:/Audio/example").getGridSize());
        */

    }

}
