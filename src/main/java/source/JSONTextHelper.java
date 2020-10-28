package source;

/**
 * useless helper class
 */
public class JSONTextHelper {
    /**
     * @param depth number of spaces
     * @return a string containing depth number of spaces
     */
    public static String currentOffset(int depth){
        StringBuilder s = new StringBuilder();
        for(int i = 0 ; i<depth;i++) {
            s.append(" ");
        }
        return s.toString();
    }
}
