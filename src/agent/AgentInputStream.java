package agent;
import java.io.*;

public class AgentInputStream extends ObjectInputStream {

    private ClassLoader classLoader;

    public AgentInputStream(InputStream in, ClassLoader cl) throws IOException {
        super(in);
        this.classLoader = cl;
    }

    @Override
    public Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
        try {
            return classLoader.loadClass(desc.getName());
        } catch (ClassNotFoundException e) {
            return super.resolveClass(desc);
        }
    }
}