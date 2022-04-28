package chat;

import org.eclipse.jetty.websocket.server.JettyWebSocketServlet;
import org.eclipse.jetty.websocket.server.JettyWebSocketServletFactory;

import javax.servlet.annotation.WebServlet;
import java.time.Duration;

@WebServlet(name = "WebSocketChatServlet", urlPatterns = {"/chat"})
public class WebSocketChatServlet extends JettyWebSocketServlet {
    private final static Duration LOGOUT_TIME = Duration.ofMinutes(10);
    private final ChatService chatService;
    public final static String PAGE_URL = "/chat";

    public WebSocketChatServlet() { this.chatService = new ChatService(); }

    @Override
    public void configure(JettyWebSocketServletFactory factory) {
        factory.setIdleTimeout(LOGOUT_TIME);
        factory.addMapping(PAGE_URL, (req, res) -> new ChatWebSocket(chatService));
    }
}
