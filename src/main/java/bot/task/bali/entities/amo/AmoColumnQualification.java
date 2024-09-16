package bot.task.bali.entities.amo;

import lombok.Getter;

public enum AmoColumnQualification {

    /** начальная колонка, скипается */
    NEW_LEAD("66150730"),
    /** первый контакт: колонка когда бот написал сообщение */
    FIRST_TOUCH("67197858"),
    /**
     * контакт установлен: когда человек клиент ответил боту. Фактически это начальная колонка для
     * бота
     */
    CONTACT_ESTABLISHED("67129898"),

    /** Квалифицированный, все поля заполнены */
    QUALIFIED("142"),
    /** Переход в основную воронку */
    TRANSFER_TO_MAIN("143");

    @Getter private final Long value;

    AmoColumnQualification(String value) {
        this.value = Long.parseLong(value);
    }
}
