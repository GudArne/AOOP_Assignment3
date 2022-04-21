package Task5;

public class Saver {


    public String save(Object o) {

        Tree<String> t =
            new Tree<String>("top",
                new Tree[] {
                    new Tree("sub1"),
                    new Tree("sub2")
            });
            
        Saver s = new Saver();
        String r = s.save(t);
        System.out.println(r);
        return r;
    }

    public static <T> void main(String[] args) {
        Saver s = new Saver();

        Tree<T>[] trees = new Tree[] {
            new Tree<T>((T)"sub1"),
            new Tree<T>((T) "sub2")
        };

        T t = (T)"test";
        Tree<T> tree = new Tree<T>(t, trees);
        s.save(null);
    }
    
}
