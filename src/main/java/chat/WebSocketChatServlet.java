package chat;

import org.eclipse.jetty.websocket.server.JettyWebSocketServlet;
import org.eclipse.jetty.websocket.server.JettyWebSocketServletFactory;

import javax.servlet.annotation.WebServlet;
import java.time.Duration;

@WebServlet(name = "WebSocketChatServlet", urlPatterns = {"/chat"})
public class WebSocketChatServlet extends JettyWebSocketServlet {
//    private final static int LOGOUT_TIME = 10 * 60 * 1000;
    private final static Duration LOGOUT_TIME = Duration.ofMinutes(10);
    private final ChatService chatService;

    public WebSocketChatServlet() { this.chatService = new ChatService(); }

    @Override
    public void configure(JettyWebSocketServletFactory factory) {
        factory.setIdleTimeout(LOGOUT_TIME);
//        factory.setCreator((req, resp) -> new ChatWebSocket(chatService));
        factory.addMapping("/chat", (req, res) -> new ChatWebSocket(chatService));
    }
}
