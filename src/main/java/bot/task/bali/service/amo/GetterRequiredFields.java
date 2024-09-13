package bot.task.bali.service.amo;

import bot.task.bali.entities.AppUser;
import bot.task.bali.entities.amo.AmoReqiredCustomField;

public interface GetterRequiredFields {
    /**
     * Этот метод должен вернуть на основе {@link AmoReqiredCustomField} вернуть незаполненные
     * @param appUser юзер, для которого необходимо
     * @return json с незаполненными полями
     */
    String getJsonFields(AppUser appUser);
}
