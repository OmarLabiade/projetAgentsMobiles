package agent;

public class AgentClassLoader extends ClassLoader {
    private byte[] byteCode;
    private String className;

    public AgentClassLoader(String className, byte[] byteCode) {
        this.className = className;
        this.byteCode = byteCode;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (name.equals(className)) {
            return defineClass(name, byteCode, 0, byteCode.length);
        }
        return super.findClass(name);
    }
}