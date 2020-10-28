package source;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Vanilla graph for JSON handling
 */
public class JSONGraph {
    /**
     * The graphs start node (head)
     */
    protected JSONNode head;

    /**
     * default (do not use normally)
     */
    public JSONGraph(){};

    /**
     * Generate graph with JSONObject
     * @param content said Object
     * @param rootName root node name
     */
    public JSONGraph(JSONObject content, String rootName){
        this.head = new JSONNodeObject(rootName, 0, content);
    }

    /**
     *  Generate graph with JSONArray
     * @param content said array
     * @param rootName root node name
     */
    public JSONGraph(JSONArray content, String rootName){
        this.head = new JSONNodeArray(rootName, 0, content);
    }

    /**
     * Print this graph
     * @return this graph as string
     */
    public String toString(){
        return this.head.toString();
    }

    /**
     * Print this graph, but in pretty :)
     * @return this graph as pretty string
     */
    public String toPrettyString(){
        return this.head.toPrettyString();
    }

    public JSONNode getNode(String basePath){
        String[] path = basePath.split(":");
        if(!path[0].equals(this.head.getName())){
            return null;
        }else{
            return this.head.findNode(path);
        }
    }

    public JSONNode findFirst(String nodeName){return this.head.findFirstNode(nodeName);}
}
