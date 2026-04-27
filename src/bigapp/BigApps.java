package bigapp;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class BigApps {

    /*private static ClassLoader classLoader;
    private static Object  installerObject;*/

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {

        /*Path libDir = Paths.get("");

        URL[] jarUrls;
        try (Stream<Path> files = Files.list(libDir)) {
            jarUrls = files.filter(path -> path.toString().endsWith(".jar"))
                    .map(path -> {
                        try {
                            return path.toUri().toURL();
                        } catch (MalformedURLException e) {
                            throw new UncheckedIOException(e);
                        }
                    }).toArray(URL[]::new);
        }
        // Create a class loader using all those jars and set the parent to the
        // current class loader's parent.
        ClassLoader classLoader = new URLClassLoader(jarUrls, JibArtifactManager.class.getClassLoader().getParent());

        // Temporarily replace the context classLoader
        Thread currentThread = Thread.currentThread();
        ClassLoader oldContextLoader = currentThread.getContextClassLoader();
        currentThread.setContextClassLoader(classLoader);

        Class jibServiceFactory = classLoader.loadClass(JIB_SERVICE_FACTORY);
        try {
            Object jibArtifactInstaller = jibServiceFactory.getMethod("createJibArtifactInstaller").invoke(null);
            return new JibArtifactManager(jibArtifactInstaller, classLoader);
        } finally {
            currentThread.setContextClassLoader(oldContextLoader);
        }


        HashMap<String, Object> artifactDescription = new HashMap<>();
        artifactDescription.put("module", "dacapo");
        artifactDescription.put("organization", "org");
        artifactDescription.put("ext", "jar");
        artifactDescription.put("revision", "9.12");
        artifactDescription.put("classifier", "MR1-bach-be3db084");



        Method m = classLoader.loadClass("com.oracle.jib.api.JibArtifactInstaller")
                .getMethod("download", String.class, Map.class);
        m.invoke(installerObject, "1.0", artifactDescription);*/
    }
}
