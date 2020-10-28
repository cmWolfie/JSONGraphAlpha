package source;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
//TODO comment
public class JSONNodeArray implements JSONNode{
    private final String name;
    private final int depth;
    private List<JSONNode> children;
    private JSONNode parent;
    private JSONGraph daddy = null;

    public boolean addChild(JSONNode child){
        return this.children.add(child);
    }

    public JSONNode getChildByName(String name){
        for(JSONNode child : this.children){
            if(child.getName().equals(name)){
                return child;
            }
        }
        return null;
    }

    public JSONNodeArray(String name, int depth){
        this.name = name;
        this.depth = depth;
        this.children = new ArrayList<>();
    }

    public JSONNodeArray(String name, int depth, JSONArray content){
        this(name,depth);
        this.createSubStructure(content);
    }

    public JSONNodeArray(String name, int depth, JSONArray content, JSONGraph daddy){
        this(name,depth);
        this.daddy = daddy;
        this.createSubStructure(content);
    }

    public JSONNodeArray(String name, int depth, JSONArray content, JSONGraph daddy, JSONNode parent){
        this(name,depth);
        this.daddy = daddy;
        this.parent = parent;
        this.createSubStructure(content);
    }

    public void createSubStructure(JSONArray content){
        for(Object child : content){
            String childType = "";
            try {
                childType = child.getClass().getSimpleName();
            }catch(Exception ignored){}
            switch (childType) {
                case "JSONObject": {
                    JSONObject jChild = (JSONObject) child;
                    this.addChild(new JSONNodeObject(String.valueOf(content.indexOf(child)), this.getDepth() + 1, jChild, this.daddy,this));
                    break;
                }
                case "JSONArray": {
                    JSONArray jChild = (JSONArray) child;
                    this.addChild(new JSONNodeArray(String.valueOf(content.indexOf(child)), this.getDepth() + 1, jChild, this.daddy,this));
                    break;
                }
                case "null":{
                    this.addChild(new JSONNodeParameter(String.valueOf(content.indexOf(child)), this.getDepth() + 1, "null",this));
                    break;
                }
                default: this.addChild(new JSONNodeParameter(String.valueOf(content.indexOf(child)), this.getDepth() + 1, String.valueOf(child),this));
            }
        }
    }

    public String getName() {
        return this.name;
    }

    public int getDepth() {
        return this.depth;
    }

    public String toString(){
        StringBuilder s = new StringBuilder(this.name+" ("+this.depth+")\n");
        for(JSONNode child : this.children) {
            s.append(child.toString());
        }
        return s.toString();
    }

    public String toPrettyString(){
        StringBuilder s = new StringBuilder(JSONTextHelper.currentOffset(this.depth));
        s.append(this.name).append(": [\n");
        for(JSONNode child : this.children) {
            s.append(child.toPrettyString());
        }
        s.append(JSONTextHelper.currentOffset(this.depth));
        s.append("]\n");
        return s.toString();
    }

    @Override
    public JSONNode findFirstNode(String key) {
        if(this.name.equals(key)){
            return this;
        }else{
            for(JSONNode child : this.children){
                JSONNode j = child.findFirstNode(key);
                if(j!=null){
                    return j;
                }
            }
        }
        return null;
    }

    @Override
    public ArrayList<JSONNode> findAllNodes(String key) {
        ArrayList<JSONNode> r = new ArrayList<>();
        if(this.name.equals(key)){
            r.add(this);
        }else{
            for(JSONNode child : this.children){
                ArrayList<JSONNode> childR = child.findAllNodes(key);
                if(!childR.isEmpty()){
                    for(JSONNode node : childR){
                        r.add(node);
                    }
                }
            }
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
        if(path[0].equals(this.getName())){
            if(path.length==1) {
                return this;
            }else if(path.length>1){
                for (JSONNode child : this.children) {
                    if (child.getName().equals(path[1])){
                        String[] newPath = new String[path.length-1];
                        System.arraycopy(path,1,newPath,0,newPath.length);
                        return child.findNode(newPath);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public int getChildCount() {
        return this.children.size();
    }

    @Override
    public JSONObject getSubStructure() {
        JSONObject res = new JSONObject();
        JSONArray sub = new JSONArray();
        for(JSONNode node : this.children){
            JSONObject child = node.getSubStructure();
            sub.add(child.get(node.getName()));
        }
        res.put(this.getName(),sub);
        return res;
    }
}
