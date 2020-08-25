package designPattern.singleton;

import java.io.*;

public class SeriableDemo1 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./tempFile"));
        Singleton s1 = Singleton.getSingleton();
        oos.writeObject(s1);

        File file = new File("tempFile");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        Singleton s2 = (Singleton) ois.readObject();
        System.out.println(s2 == Singleton.getSingleton());
    }
}
