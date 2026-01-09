package ru.otus.listener.homework;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import ru.otus.listener.Listener;
import ru.otus.model.Message;

public class HistoryListener implements Listener, HistoryReader {

    private final List<HistoryItem> historyItems = new ArrayList<>();

    @Override
    public void onUpdated(Message msg) {
        historyItems.add(new HistoryItem(msg));
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return historyItems.stream()
                .map(HistoryItem::message)
                .filter(msg -> msg.getId() == id)
                .findFirst();
    }
}
