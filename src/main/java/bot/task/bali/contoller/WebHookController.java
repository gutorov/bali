package bot.task.bali.contoller;

import bot.task.bali.service.bot.telegram.api.UpdateProcessor;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
@Log4j2
@AllArgsConstructor
public class WebHookController {

    private final UpdateProcessor updateProcessor;

    @RequestMapping(value = "/callback/update", method = RequestMethod.POST) // main
    public ResponseEntity<?> onUpdateReceived(@RequestBody Update update) {
        if (update.hasMessage() && update.getMessage().getChat().getType().equals("private")
                || update.hasCallbackQuery()
                        && update.getCallbackQuery().getMessage().isUserMessage()) {
            updateProcessor.processUpdate(update);
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<>("cannot handle non private messages", HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public String home() {
        return "Zorinov Bot is running";
    }
}
