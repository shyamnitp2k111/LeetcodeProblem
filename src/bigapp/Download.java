package bigapp;

import java.io.UncheckedIOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Download {

    URLClassLoader classLoader ;
    Object jibArtifactInstaller ;

    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        new Download().logic();
    }

    private void logic() throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        /*loadLibrary();

        HashMap<String, Object> artifactDescription = new HashMap<>();
        artifactDescription.put("module", "dacapo");
        artifactDescription.put("organization", "org");
        artifactDescription.put("ext", "jar");
        artifactDescription.put("revision", "9.12");
        artifactDescription.put("classifier", "MR1-bach-be3db084");


        //String url = "/Users/skishor/Downloads/jib-3.0-SNAPSHOT-distribution/lib/jib-3.0-SNAPSHOT.jar";

       *//* // 1. Load the JAR file
        URL jarURL = new URL("file://" + url);
        URLClassLoader classLoader = new URLClassLoader(new URL[]{jarURL});*//*

        // 2. Load the class
        Class<?> loadedClass = Class.forName("com.oracle.jib.api.JibArtifactInstaller", true, classLoader);

        // 3. Create an instance (if needed)
        Object jibArtifactInstaller = JibServiceFactory.createJibArtifactInstaller();

        // 4. Get the method
        Method method = loadedClass.getMethod("download", String.class, Map.class);

        // 5. Call the method
        Object result = method.invoke(jibArtifactInstaller, "1.0", artifactDescription);

        // Print the result
        System.out.println("Result: " + result);*/

    }

    public void loadLibrary()  {


        /*Path jibInstallDir = Paths.get("/Users/skishor/Downloads/jib-3.0-SNAPSHOT-distribution");
        Path libDir = jibInstallDir.resolve("lib");

        if (!Files.isDirectory(libDir)) {
            throw new ClassNotFoundException("com.oracle.jib.api.JibServiceFactory");
        }
        try {
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
            classLoader = new URLClassLoader(jarUrls);


            Class jibServiceFactory = classLoader.loadClass("com.oracle.jib.api.JibServiceFactory");

            jibArtifactInstaller = jibServiceFactory.getMethod("createJibArtifactInstaller").invoke(null);

           // jibArtifactInstaller = JibServiceFactory.createJibArtifactInstaller();


        } catch (Exception e) {
            throw new ClassNotFoundException("com.oracle.jib.api.JibServiceFactory", e);
        }*/
    }
}
