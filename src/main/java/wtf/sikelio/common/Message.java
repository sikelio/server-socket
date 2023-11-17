package wtf.sikelio.common;

import java.io.Serializable;

public class Message implements Serializable {
    private String _sender;
    private String _content;

    public Message(String sender, String content) {
        this._sender = sender;
        this._content = content;
    }

    @Override
    public String toString() {
        return this._sender + ": " + this._content;
    }

    public void setSender(String sender) {
        this._sender = sender;
    }

    public String getContent() {
        return this._content;
    }
}
