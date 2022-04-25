package Task5;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
public @interface ElementField {

    String name() default "ElementField";

}
