package source;

import org.json.simple.JSONObject;

import java.util.ArrayList;
//TODO comment
public class JSONNodeParameter implements JSONNode {
    private final String name;
    private final int depth;
    private final String value;
    private JSONNode parent;

    public String getValue() {
        return this.value;
    }

    public  JSONNodeParameter(String name, int depth, String value){
        this.name = name;
        this.depth = depth;
        this.value = value;
    }

    public  JSONNodeParameter(String name, int depth, String value, JSONNode parent){
        this(name,depth,value);
        this.parent = parent;
    }


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getDepth() {
        return this.depth;
    }

    public String toString(){
        return this.name+":"+this.value+" ("+this.depth+")\n";
    }

    public String toPrettyString(){
        return JSONTextHelper.currentOffset(this.depth)+this.name+":\""+this.value+"\"\n";
    }

    @Override
    public JSONNode findFirstNode(String key) {
        if(this.name.equals(key)){
            System.out.println(this.name+" : "+this.depth);
            return this;
        }else {
            return null;
        }
    }

    @Override
    public ArrayList<JSONNode> findAllNodes(String key) {
        ArrayList<JSONNode> r = new ArrayList<>();
        if(this.name.equals(key)){
            r.add(this);
        }
        return r;
    }

    @Override
    public String getParentDir() {
        if (this.parent != null){
            String ppDir = this.parent.getParentDir();
            StringBuilder s = new StringBuilder(ppDir);
            if(!ppDir.equals("")) {
                s.append(":");
            }
            s.append(this.parent.getName());
            return s.toString();
        }else{
            return "";
        }
    }

    @Override
    public String getPath() {
        return this.getParentDir()+":"+this.getName();
    }

    @Override
    public JSONNode findNode(String[] path) {
        if(path.length==1&&path[0].equals(this.getName())){
            return this;
        }else{
            return null;
        }
    }

    @Override
    public int getChildCount() {
        return 0;
    }

    @Override
    public JSONObject getSubStructure() {
        JSONObject sub = new JSONObject();
        sub.put(this.getName(),this.getValue());
        return sub;
    }
}
