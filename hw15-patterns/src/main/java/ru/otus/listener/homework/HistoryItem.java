package ru.otus.listener.homework;

import java.util.ArrayList;
import java.util.List;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

public record HistoryItem(Message message) {
    public HistoryItem(Message message) {
        this.message = deepCopy(message);
    }

    private Message deepCopy(Message msg) {
        if (msg == null) {
            return null;
        }
        ObjectForMessage objectForMessageCopy = new ObjectForMessage();
        List<String> dataCopy = msg.getField13() == null
                ? null
                : new ArrayList<>(msg.getField13().getData());
        if (dataCopy != null) {
            objectForMessageCopy.setData(dataCopy);
        }
        return new Message.Builder(msg.getId())
                .field1(msg.getField1())
                .field2(msg.getField2())
                .field3(msg.getField3())
                .field4(msg.getField4())
                .field5(msg.getField5())
                .field6(msg.getField6())
                .field7(msg.getField7())
                .field8(msg.getField8())
                .field9(msg.getField9())
                .field10(msg.getField10())
                .field11(msg.getField11())
                .field12(msg.getField12())
                .field13(objectForMessageCopy)
                .build();
    }
}
