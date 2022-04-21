package Task5;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class Saver<T> {

    int index = 0;

    public String save(Object o){
        Class aClass = o.getClass();
        Annotation[] annotations = aClass.getAnnotations();
        String retval = "";
        boolean first = true;

        for(int i = 0; i < annotations.length; i++){
            Method[] methods = aClass.getDeclaredMethods();
            for(Method method : methods){
                if(method.getName().equals("getValue")){
                    try {
                        //retval += "<node value= " + method.invoke(o) + ">\n";
                        retval = show(1, method.invoke(o).toString(), retval);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if(method.getName().equals("getChildren")){
                    try {
                        Object[] children = (Object[]) method.invoke(o);
                        if(children != null){
                            for(Object child : children){
                                retval += save(child);
                                //retval += "\t" + save(child);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return retval;
    }
    
	public String show(int level, String value, String result) {

		String indent = "";
		for(int i=0; i<level; i++) {
			indent += "\t"; 
		}

		return indent+value+"\n"+result;
		
		
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
