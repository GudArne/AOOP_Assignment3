package Task5;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME) // accessible during runtime
@Target(ElementType.TYPE) // can only annotate a class, enumeration, or interface.

public @interface Element {

    String name() default "ElementName" ;
}
  