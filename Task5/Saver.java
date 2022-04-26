package Task5;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class Saver<T> {

    int index = 0;
    int level = 0;
    Boolean isEnd = false;

    /*
        May work different on PC and in IDE. 
        The declared methods stored in the array do seem to swap the order of the methods on different pc.
        The top method is the last method in the array for me, therefor the program reads it last. 
    */
    public String save(Object o){
        Class aClass = o.getClass();
        Annotation[] annotations = aClass.getAnnotations();
        String retEle = "";
        String retSub = "";

        for(int i = 0; i < annotations.length; i++){
            Method[] methods = aClass.getDeclaredMethods();
            for(Method method : methods){
                if(method.getAnnotation(ElementField.class) != null){
                    try {
                        retEle+= show(level, method.invoke(o).toString(), isEnd);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if(method.getAnnotation(SubElements.class) != null){
                    try {
                        Object[] children = (Object[]) method.invoke(o);
                        if(children != null){
                            level++;
                            isEnd = true;
                            for(Object child : children){
                                retSub += save(child);
                            }
                        retSub += indent(level)+"</subnodes> \n";
                        retSub += indent(level-1)+"</node> \n";
                        isEnd = false;   
                        level --;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return retEle + retSub;
    }
    public String indent(int level){
        String retval = "";
        for(int i = 0; i < level; i++){
            retval += "\t";
        }
        return retval;
    }

    String end = "";
    String subIndent = "";
	public String show(int lvl, String value, Boolean isEnd) {

		String indent = "";
        end = isEnd ? "'/>" : "'>";

        if(!isEnd){
            subIndent = indent(lvl+1);
            end += "\n" + subIndent + "<subnodes>";
            lvl--;
        }
        indent = indent(lvl+1);

		return indent+"<node value='"+value+end+"\n";
	}
    public static void main(String[] args){

        Tree<String> t =
        new Tree<String>("top",
            new Tree[] {
                new Tree("sub1"),
                new Tree("sub2")
            });
            Saver s = new Saver();
            String r = s.save(t);
            
            System.out.println(r);
    }
}