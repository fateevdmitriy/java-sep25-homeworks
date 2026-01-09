package ru.otus;

import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.homework.HistoryListener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;
import ru.otus.processor.homework.ProcessorEvenSeconds;
import ru.otus.processor.homework.ProcessorSwapFields;

public class HomeWork {
    private static final Logger logger = LoggerFactory.getLogger(HomeWork.class);
    /*
    Реализовать to do:
      1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage)
      2. Сделать процессор, который поменяет местами значения field11 и field12
      3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
            Секунда должна определяться во время выполнения.
            Тест - важная часть задания
            Обязательно посмотрите пример к паттерну Мементо!
      4. Сделать Listener для ведения истории (подумайте, как сделать, чтобы сообщения не портились)
         Уже есть заготовка - класс HistoryListener, надо сделать его реализацию
         Для него уже есть тест, убедитесь, что тест проходит
    */

    public static void main(String[] args) {
        /*
          по аналогии с Demo.class
          из элементов "to do" создать new ComplexProcessor и обработать сообщение
        */

        var processors = List.of(new ProcessorSwapFields(), new ProcessorEvenSeconds(LocalDateTime::now));

        var complexProcessor = new ComplexProcessor(processors, ex -> {});

        var historyListener = new HistoryListener();
        complexProcessor.addListener(historyListener);

        List<String> sampleData = List.of("sampleData1", "sampleData2", "sampleData3", "sampleData4", "sampleData5");
        ObjectForMessage objectForMessage = new ObjectForMessage();
        objectForMessage.setData(sampleData);

        var message = new Message.Builder(1L)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field4("field4")
                .field5("field5")
                .field6("field6")
                .field7("field7")
                .field8("field8")
                .field9("field9")
                .field10("field10")
                .field11("field11")
                .field12("field12")
                .field13(objectForMessage)
                .build();

        var result = complexProcessor.handle(message);
        logger.info("result:{}", result);

        complexProcessor.removeListener(historyListener);
    }
}
