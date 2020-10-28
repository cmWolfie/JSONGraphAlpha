package source;

import org.json.simple.JSONObject;

import java.util.ArrayList;

/**
 * iferface for JSONNodes used by {@link JSONGraph}
 */
public interface JSONNode {
    //getter
    String getName();
    int getDepth();
    JSONObject getSubStructure();

    /**
     * @return path of the parent node
     */
    String getParentDir();

    /**
     * @return path of this node
     */
    String getPath();

    /**
     * number of child nodes
     * @return
     */
    int getChildCount();

    /**
     * get a pretty string of this and all subNodes
     * @return said pretty string
     */
    String toPrettyString();

    /**
     * find first node [this and subNodes are searched] with respective key
     * @param key said key
     * @return node if found (else null)
     */
    JSONNode findFirstNode(String key);

    /**
     * return a list of all nodes [this and subNodes are searched] with respective key
     * @param key said key
     * @return list of all found nodes
     */
    ArrayList<JSONNode> findAllNodes(String key);

    /**
     * get a node by base path
     * @param path said path
     * @return found node
     */
    JSONNode findNode(String[] path);
}
