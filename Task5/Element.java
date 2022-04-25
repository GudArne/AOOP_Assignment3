package Task5;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)

public @interface Element {

    String name() default "ElementName" ;
}
  