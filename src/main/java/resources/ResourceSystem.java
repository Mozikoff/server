package resources;

import vfs.VFS;
import vfs.VFSImpl;
import xml.ReadXmlFileSAX;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ResourceSystem {
    private VFS vfs;
    private static ResourceSystem instance;
    private final Map<String, Resource> resourceMap = new HashMap<>();

    private ResourceSystem(String root) {
        this.vfs = new VFSImpl(root);
        initResourceMap();
    }

    private ResourceSystem() {
        this("./data");
    }

    public static ResourceSystem getInstance() {
        if (instance == null) {
            instance = new ResourceSystem();
        }
        return instance;
    }

    private void initResourceMap() {
        Iterator<String> iterator = vfs.getIterator("./data");
        while (iterator.hasNext()) {
            String fileName = iterator.next();
            Resource resource = (Resource) ReadXmlFileSAX.readXml(fileName);
            resourceMap.put(fileName, resource);
        }
    }
}
