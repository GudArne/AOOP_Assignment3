package Task5;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
public @interface SubElements {

    String name() default "SubElements";

}
