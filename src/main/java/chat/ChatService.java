package chat;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChatService {
    private Set<ChatWebSocket> webSockets;

    public ChatService() { this.webSockets = Collections.newSetFromMap(new ConcurrentHashMap<>()); }

    public void sendMessage(String msg) {
        for (ChatWebSocket user : webSockets) {
            try {
                user.sendString(msg);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void add(ChatWebSocket newUser) { webSockets.add(newUser); }

    public void remove(ChatWebSocket user) { webSockets.remove(user); }

}
