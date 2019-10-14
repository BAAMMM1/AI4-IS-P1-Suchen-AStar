package mvc.model.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import mvc.model.field.Field;

import java.io.File;
import java.io.IOException;

public class IO {


    public Field load(String file) {

        try {
            return new ObjectMapper().readValue(new File(file), Field.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void save(String file, Field field){

        try {
            new ObjectMapper().writeValue(new File(file +".json"), field);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {
        Field field = new Field(15);
        IO io = new IO();
        io.save("db/field", field);

        System.out.println(io.load("db/field.json").getColumns());
    }

}
