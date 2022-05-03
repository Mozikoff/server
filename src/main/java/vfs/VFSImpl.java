package vfs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class VFSImpl implements VFS {
    private final Logger logger = LogManager.getLogger(VFSImpl.class.getName());
    private final String root;

    public VFSImpl(String root) {
        this.root = root;
    }

    @Override
    public boolean isExist(String path) {
        return new File(root + path).exists();
    }

    @Override
    public boolean isDirectory(String path) {
        return new File(root + path).isDirectory();
    }

    @Override
    public String getAbsolutePath(String file) {
        return new File(root + file).getAbsolutePath();
    }

    @Override
    public byte[] getBytes(String file) {
        try (FileInputStream stream = new FileInputStream(new File(root + file))) {
            return stream.readAllBytes();
        }
        catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public String getUTF8Text(String file) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(new File(root + file)))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        }
        catch (IOException e) {
            logger.error(e.getMessage());
        }
        return sb.toString();
    }

    @Override
    public Iterator<String> getIterator(String startDir) {
        return new FileIterator(startDir);
    }

    public class FileIterator implements Iterator<String> {
        private Queue<File> files = new LinkedList<>();

        public FileIterator(String path) {
            files.add(new File(root + path));
        }

        @Override
        public boolean hasNext() {
            return !files.isEmpty();
        }

        @Override
        public String next() {
            File file = files.peek();
            if (file.isDirectory()) {
                for (File subfile : file.listFiles()) {
                    files.add((subfile));
                }
            }
            return files.poll().getAbsolutePath();
        }
    }
}
