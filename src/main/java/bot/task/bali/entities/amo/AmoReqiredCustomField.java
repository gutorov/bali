package bot.task.bali.entities.amo;

import lombok.Getter;

/** Enum, представляющий обязательные настраиваемые поля AmoCRM. */
public enum AmoReqiredCustomField {

    /** Источник заявки. */
    SOURCE("500569"),

    /** Проект. */
    PROJECT("716849"),

    /** Цель покупки. */
    PURPOSE("716851"),

    /** Срок покупки. */
    PURCHASE_TERM("716859"),

    /** Район. */
    REGION("729033"),

    /** Страна. */
    COUNTRY("716855"),

    /** Сейчас на Бали? */
    CURRENTLY_IN_BALI("716857"),

    /** Покупка online. */
    PURCHASE_ONLINE("716853"),

    /** Причина отказа. */
    REJECTION_REASON("716939"),

    /** Тип сделки. */
    DEAL_TYPE("727753"),

    /** Язык клиента. */
    CLIENT_LANGUAGE("729031");

    @Getter private final Long value;

    AmoReqiredCustomField(String value) {
        this.value = Long.parseLong(value);
    }
}
