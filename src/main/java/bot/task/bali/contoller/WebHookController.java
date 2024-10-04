package bot.task.bali.contoller;

import bot.task.bali.dto.WazzupBody;
import bot.task.bali.service.bot.wazzap.WazzupUpdateProcessor;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Log4j2
@AllArgsConstructor
public class WebHookController {

    private final WazzupUpdateProcessor wazzupUpdateProcessor;

    @PostMapping(value = "/api/v1/wazzup") // main
    public ResponseEntity<?> onUpdateReceivedFromWazzap(@RequestBody(required = false) WazzupBody body) {
        if (body == null) {
            return new ResponseEntity<>("No body", HttpStatus.OK);
        }
        wazzupUpdateProcessor.update(body);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/api/v1/wazzup") // main
    public ResponseEntity<?> get() {
//        wazzupUpdateProcessor.update(body);
        return new ResponseEntity<>("get method", HttpStatus.OK);
    }


    @GetMapping
    public String home() {
        return "Zorinov Bot is running";
    }
//
//    @GetMapping("/api/v1/balibot/{scope_id}")
//    public ResponseEntity<String> getBalibotData(
//            @PathVariable("scope_id") String scopeId,
//            @RequestBody(required = false) String body,
//            HttpServletRequest request) {
//
//        // Логирование заголовков
////        log.debug(request);
//        logHeaders(request);
//
//        if (body != null) {
//            log.debug("Body: " + body);
//        }
//
//        log.debug("/api/v1/balibot/" + scopeId);
//
//        return ResponseEntity.ok("Запрос обработан. scopeId: " + scopeId + ", Body: " + body);
//    }
//
//    private void logHeaders(HttpServletRequest request) {
//        Enumeration<String> headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String headerName = headerNames.nextElement();
//            String headerValue = request.getHeader(headerName);
//            log.debug("Header: " + headerName + " = " + headerValue);
//        }
//    }
//
//    @PostMapping("/api/v1/balibot")
//    public ResponseEntity<String> getBalibotData(HttpServletRequest request, @RequestBody(required = false) String body) {
//        log.debug("Executing POST with body: " + body);
//        logHeaders(request);
//        return ResponseEntity.ok("Запрос обработан^ body: " + body);
//    }
}
