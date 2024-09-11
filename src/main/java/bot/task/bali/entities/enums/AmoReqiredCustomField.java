package bot.task.bali.entities.enums;

import lombok.Getter;

public enum AmoReqiredCustomField {

    SOURCE("500569"), // Источник заявки
    PROJECT("716849"), // Проект
    PURPOSE("716851"), // Цель покупки
    PURCHASE_TERM("716859"), // Срок покупки
    REGION("729033"), // Район
    COUNTRY("716855"), // Страна
    CURRENTLY_IN_BALI("716857"), // Сейчас на Бали?
    PURCHASE_ONLINE("716853"), // Покупка online
    REJECTION_REASON("716939"), // Причина отказа
    DEAL_TYPE("727753"), // Тип сделки
    CLIENT_LANGUAGE("729031"); // Язык клиента

    @Getter
    private final Long value;

    AmoReqiredCustomField(String value) {
        this.value = Long.parseLong(value);
    }
}