package pl.training.chat;

import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;

import java.util.Map;

public class WebSocketUtils {

    private static final String SIMP_CONNECT_MESSAGE_HEADER = "simpConnectMessage";

    public static String getSocketId(AbstractSubProtocolEvent event) {
        return stompHeaderAccessor(event).getSessionId();
    }

    public static Map<String, Object> getAttributes(AbstractSubProtocolEvent event) {
        return stompHeaderAccessor(event).getSessionAttributes();
    }

    public static Map<String, Object> getNativeAttributes(AbstractSubProtocolEvent event) {
        var simpConnectMessage = getStompMessage(event);
        return StompHeaderAccessor.wrap(simpConnectMessage).getSessionAttributes();
    }

    public static String getNativeHeader(AbstractSubProtocolEvent event, String headerName) {
        var simpConnectMessage = getStompMessage(event);
        return StompHeaderAccessor.wrap(simpConnectMessage).getFirstNativeHeader(headerName);
    }

    private static StompHeaderAccessor stompHeaderAccessor(AbstractSubProtocolEvent event) {
        return StompHeaderAccessor.wrap(event.getMessage());
    }

    private static Message<?> getStompMessage(AbstractSubProtocolEvent event) {
        return (Message<?>) stompHeaderAccessor(event).getHeader(SIMP_CONNECT_MESSAGE_HEADER);
    }

}
