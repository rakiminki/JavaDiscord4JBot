package manager;

import files.ObjectWriter;
import model.ChatResponse;
import model.ReturnResult;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ResponseManager {
    private Map<String, ChatResponse> responseMap = new HashMap<>();
    public static Map<String, ResponseManager> accessMap = new HashMap<>();

    public ResponseManager(String key,String filename) {
        ReturnResult result = ObjectWriter.ReadObjectFromFile(filename);
        if(result.isGood()){
            responseMap = (Map<String, ChatResponse>) result.getResult();
        }
        accessMap.put(key,this);
    }
    public ReturnResult save(String filename){
        return ObjectWriter.WriteObjectToFile(responseMap,filename);
    }

    public void add(ChatResponse response){
        responseMap.put(response.getTrigger(),response);
    }
    public void remove(String trigger){
        responseMap.remove(trigger);
    }
    public void changeMessage(String trigger, String newMessage){
        responseMap.put(trigger,new ChatResponse(trigger,newMessage));
    }
    public Set<String> getTrigger(){
        return responseMap.keySet();
    }
    public Set<Map.Entry<String,ChatResponse>> getTriggersWithResponse(){
        return responseMap.entrySet();
    }
    public String getMessage(String trigger){
        return responseMap.get(trigger).getMessage();
    }
}
