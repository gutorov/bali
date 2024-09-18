package bot.task.bali.service.bot.wazzap;

import bot.task.bali.dto.WazzupBody;
import bot.task.bali.entities.WhatsAppUser;
import bot.task.bali.repo.whatsapp.GetterWhatsAppUser;
import bot.task.bali.service.ai.main.AiBotService;
import bot.task.bali.service.bot.adapter.ConvertAppUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WazzupProcessor implements WazzupUpdateProcessor {

    private final GetterWhatsAppUser getterWhatsAppUser;
    private final ConvertAppUser convertAppUser;
    private final AiBotService aiBotService;
    private final WazzupResponseService responseService;


    @Override
    public void update(WazzupBody body) {
        WhatsAppUser user = getterWhatsAppUser.getByBody(body);

        var text = body.getText();

        var userPromtDTO = convertAppUser.convertToUserPrompt(user);
        var responseFromGpt = aiBotService.generateResponse(userPromtDTO, text);

        var responseBody = body.copy();
        responseBody.setText(responseFromGpt);

        responseService.sendMessage(responseBody);
    }
}
