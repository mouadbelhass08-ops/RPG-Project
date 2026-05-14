package RPG.engine.system;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {
    public static <T> T load(String path) {
        Path p=Paths.get(path);
        try (ObjectInputStream in=new ObjectInputStream(Files.newInputStream(p))) {
            return (T) in.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static <T> void save(T object,String path) {
        Path p=Paths.get(path);
        try (ObjectOutputStream out=new ObjectOutputStream(Files.newOutputStream(p))) {
            out.writeObject(object);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}