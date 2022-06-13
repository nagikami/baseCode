package nagi.netty.protocoltcp;

import lombok.Data;

@Data
public class MessageProtocol {
    private int length;
    private byte[] content;
}
