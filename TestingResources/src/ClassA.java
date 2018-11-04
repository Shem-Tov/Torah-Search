import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
public class ClassA {

    private static final String line = "-----------------------------------------";

    private void loadResource (String resource) {
        URL u = this.getClass().getResource(resource);
        loadResourceByUrl(u, resource);
    }

    private void loadResourceWithContextLoader (String resource){
        URL u = Thread.currentThread().getContextClassLoader().getResource(resource);
        loadResourceByUrl(u, resource);
    }

    private void loadResourceWithSystemClassLoader (String resource) {
        URL u = ClassLoader.getSystemClassLoader().getResource(resource);
        loadResourceByUrl(u, resource);
    }

    private void loadResourceByUrl (URL u, String resource){
        System.out.println("-> attempting input resource: "+resource);
        if (u != null) {
            String path = u.getPath();
            path = path.replaceFirst("^/(.:/)", "$1");
            System.out.println("    absolute resource path found :\n    " + path);
            String s="Could not find file...";
			try {
				s = new String(Files.readAllBytes(Paths.get(path)));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            System.out.println("    file content: "+s);
        } else {
            System.out.println("    no resource found: " + resource);
        }
    }

    public static void main (String[] args) {
    	final String folder="";
    	ClassA a = new ClassA();
        System.out.println(line+"\nusing this.getClass().getResource\n"+line);
        a.loadResource(folder+"test-pkg-resource.txt");
        a.loadResource("/"+folder+"test-pkg-resource.txt");
        a.loadResource(folder+"root-resource.txt");
        a.loadResource("/"+folder+"root-resource.txt");

        System.out.println(line+"\n using current thread context loader\n"+line);
        a.loadResourceWithContextLoader(folder+"test-pkg-resource.txt");
        a.loadResourceWithContextLoader("/"+folder+"test-pkg-resource.txt");
        a.loadResourceWithContextLoader(folder+"root-resource.txt");
        a.loadResourceWithContextLoader("/"+folder+"root-resource.txt");


        System.out.println(line+"\n using ClassLoader.getSystemClassLoader()\n"+line);
        a.loadResourceWithSystemClassLoader(folder+"test-pkg-resource.txt");
        a.loadResourceWithSystemClassLoader("/"+folder+"test-pkg-resource.txt");
        a.loadResourceWithSystemClassLoader(folder+"root-resource.txt");
        a.loadResourceWithSystemClassLoader("/"+folder+"root-resource.txt");
    }
}