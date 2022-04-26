package Task5;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@SuppressWarnings("unchecked")
public class Saver<T> {

    int level = 0; // start indent-level
    Boolean isEnd = false; // end of a node?

    /*
        May work different on other PC and in IDE. 
        The declared methods stored in the array do seem to swap the order of the methods on different pc.
        The top method (getValue) is the last method in the array for me, therefore the program reads it last. 
    */
    String ElementFieldAnnoName = "";
    String SubElementsAnnoName = "";
    String ElementAnnoName = "";
    
    public String save(Object o){
        Class<?> clazz = o.getClass();
        Annotation[] annotations = clazz.getAnnotations();
        String retEle = "";
        String retSub = "";
        ElementAnnoName = clazz.getAnnotation(Element.class).name();
        

        for(int i = 0; i < annotations.length; i++){
            Method[] methods = clazz.getDeclaredMethods();
            for(Method method : methods){
                if(method.getAnnotation(ElementField.class) != null){
                    try {
                        ElementFieldAnnoName = method.getAnnotation(ElementField.class).name();
                        retEle+= show(level, method.invoke(o).toString(), isEnd);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if(method.getAnnotation(SubElements.class) != null){
                    try {
                        SubElementsAnnoName = method.getAnnotation(SubElements.class).name();
                        Object[] children = (Object[]) method.invoke(o);
                        if(children != null){
                            level++;
                            isEnd = true;

                                for(Object child : children){
                                    retSub += save(child);
                                }
                            retSub += indent(level)+ "</"+SubElementsAnnoName +"> \n";
                            retSub += indent(level-1)+ "</"+ElementAnnoName +"> \n";
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

	public String show(int lvl, String value, Boolean isEnd) {

		String indent = "";
        String end = isEnd ? "'/>" : "'>";

        if(!isEnd){
            String subIndent = indent(lvl+1);
            end += "\n" + subIndent + "<"+SubElementsAnnoName+">";
            lvl--;
        }
        indent = indent(lvl+1);

		return indent+"<"+ElementAnnoName+ " "+ElementFieldAnnoName+"='"+value+end+"\n";
	}
    public static void main(String[] args){

        Tree<String> t =
        new Tree<String>("top",
            new Tree[] {
                new Tree("sub1"),
                new Tree("sub2")
            });
            Saver<?> s = new Saver();
            String r = s.save(t);
            
            System.out.println(r);
    }
}