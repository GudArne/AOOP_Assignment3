package Task5;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class Saver<T> {

    int index = 0;
    int level = 0;
    Boolean isEnd = false;

    public String save(Object o){
        Class aClass = o.getClass();
        Annotation[] annotations = aClass.getAnnotations();
        String retEle = "";
        String retSub = "";
        boolean first = true;
        

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
                                //retval += "\t" + save(child);
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
            // for(int i=0; i<lvl+1; i++) {
            //     subIndent += "\t"; 
            // }
            end += "\n" + subIndent + "<subnodes>";
            lvl--;
        }
        indent = indent(lvl+1);
        // for(int i=0; i<lvl+1; i++) {
		// 	indent += "\t"; 
		// }

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

/*
package Task5;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


public class Saver <T>{

    int index = 0;

    public String save(Object o){
        Class aClass = o.getClass();
        Annotation[] annotations = aClass.getAnnotations();
        String retval = "";

        for(int i = 0; i < annotations.length; i++){
            Method[] methods = aClass.getDeclaredMethods();
            for(Method method : methods){
                if(method.getName().equals("getValue")){
                    try {
                        retval += show(index, "node",method.invoke(o).toString(), false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if(method.getName().equals("getChildren")){
                    try {
                        Object[] children = (Object[]) method.invoke(o);
                        if(children != null){
                            index++;
                            retval += show(index, "subnodes");
                            int saveLevel = index;
                            index++;
                            for (Object sub : children) {
                                retval += save(sub);
                            }
                            retval += show(saveLevel, "/subnodes");
                            retval += show(0, "/node");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return retval;
    }

    public String show(int level, String t) {
        String indent = "";
        for(int i=0; i<level; i++) {
            indent += "\t";
        }
        return indent+"<"+t+">"+"\n";
    }

    public String show(int level, String t ,String v, boolean isEnd) {
        String indent = "";
        for(int i=0; i<level; i++) {
            indent += "\t";
        }
        String returnString = indent+"<"+t+" value="+'"'+v+'"';
        if (isEnd) {
            returnString += "/>\n";
        }  else {
            returnString += ">\n";
        }
        return  returnString;
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
*/
