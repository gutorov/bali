package bot.task.bali.entities.enums;

import lombok.Getter;

public enum AmoColumnQualification {

    NEW_LEAD("66150730"), // начальная колонка, скипается
    FIRST_TOUCH("67197858"), // первый контакт: колонка когда бот написал сообщение
    CONTACT_ESTABLISHED("67129898"), // контакт установлен: когда человек клиент ответил боту
    QUALIFIED("143");

    @Getter
    private final Long value;

    AmoColumnQualification(String value) {
        this.value = Long.parseLong(value);
    }
}
