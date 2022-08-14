package files;

import enums.Locations;
import model.ChatResponse;
import model.ReturnResult;
import java.io.*;

public class ObjectWriter {
    private static final String basepath = Locations.DATA.path;

    public static ReturnResult WriteObjectToFile(Object serObj, String filename){
        try {
            File location = new File(basepath + filename);
            if (location.exists()) {
                location.delete();
            }
            FileOutputStream f = new FileOutputStream(location);
            ObjectOutputStream o = new ObjectOutputStream(f);


            o.writeObject(serObj);

            o.close();
            f.close();
        } catch (FileNotFoundException e) {
            return ReturnResult.bad("File not found");
        } catch (IOException e) {
            return ReturnResult.bad("Error initializing stream");
        }
        return ReturnResult.good();
    }

    public static ReturnResult ReadObjectFromFile(String filename) {

        try {
            FileInputStream fi = new FileInputStream(new File(basepath + filename));
            ObjectInputStream oi = new ObjectInputStream(fi);
            Object content = oi.readObject();

            oi.close();
            fi.close();
            return ReturnResult.good(content);
        } catch (FileNotFoundException e) {
            return ReturnResult.bad("File not found");
        } catch (IOException e) {
            return ReturnResult.bad("Error initializing stream");
        } catch (ClassNotFoundException e) {
            return ReturnResult.bad("Error with Reading data, cant find class");
        }
    }
}
