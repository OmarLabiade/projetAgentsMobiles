package agent;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

public class AgentInputStream extends ObjectInputStream {
    private ClassLoader classLoader;

    public AgentInputStream(InputStream in, ClassLoader cl) throws IOException {
        super(in);
        this.classLoader = cl;
    }

    @Override
    protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
        try {
            return classLoader.loadClass(desc.getName());
        } catch (ClassNotFoundException e) {
            return super.resolveClass(desc);
        }
    }
}