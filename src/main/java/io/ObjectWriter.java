package io;

import java.io.*;

public class ObjectWriter {
    public static void main(String[] args) {
//        stringExample();
//        descriptorExample();
    }

    private static void stringExample() {
        write("This is an example of serializing!", "./string.bin");
        String from = (String) read("./string.bin");
        System.out.println(from);
    }

    private static void descriptorExample() {
        Descriptor descriptor = new Descriptor(1, "Evgeniy");
        write(descriptor, "./descriptor.bin");
        Descriptor from = (Descriptor) read("./descriptor.bin");
        System.out.println(from);
    }

    public static void write(Object object, String fileName) {
        try (FileOutputStream out = new FileOutputStream(fileName)) {
            BufferedOutputStream bufferedOut = new BufferedOutputStream(out);
            ObjectOutputStream objOut = new ObjectOutputStream(bufferedOut);
            objOut.writeObject(object);
            objOut.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object read(String fileName) {
        try (FileInputStream in = new FileInputStream(fileName)) {
            BufferedInputStream bufferedIn = new BufferedInputStream(in);
            ObjectInputStream objIn = new ObjectInputStream(bufferedIn);
            return objIn.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
