package tiduswr.util;

import java.util.ArrayList;
import java.util.List;

public class MatcherUtil {
    public static boolean hasAnyDuplicateNumber(Object[] params){
        List<Object> list = new ArrayList<>();

        for (Object param : params) {
            if (list.contains(param)) {
                return true;
            } else {
                list.add(param);
            }
        }

        return false;
    }
}
