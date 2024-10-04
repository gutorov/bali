package bot.task.bali.service.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.ClientRequest;
import reactor.core.publisher.Mono;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
class SignatureManager implements SignatureHeaders {

    private final String SECRET_KEY;

    public SignatureManager(@Value("${amo.chat.secret.key}") String secretKey) {
        this.SECRET_KEY = secretKey;
    }

    @Override
    public void addSignatureHeader(WebClient webClient) {
        webClient.mutate()
                .filter((request, next) -> addHeaders(request)
                        .flatMap(next::exchange))
                .build();
    }

    private Mono<ClientRequest> addHeaders(ClientRequest request) {
        String dateHeader = getCurrentDate();
        String contentTypeHeader = "application/json";
        String contentMD5Header = calculateMD5(request);
        String signatureHeader = calculateSignature(request, dateHeader, contentTypeHeader, contentMD5Header);

        ClientRequest modifiedRequest = ClientRequest.from(request)
                .header("Date", dateHeader)
                .header("Content-Type", contentTypeHeader)
                .header("Content-MD5", contentMD5Header)
                .header("X-Signature", signatureHeader)
                .build();

        return Mono.just(modifiedRequest);
    }

    private String getCurrentDate() {
        return ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME);
    }

    private String calculateMD5(ClientRequest request) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            // Предположим, что тело запроса передается как строка JSON.
            // Вам нужно убедиться, что тело запроса доступно как строка
            request.body();
            byte[] requestBodyBytes = request.body().toString().getBytes(StandardCharsets.UTF_8);
            byte[] digest = md.digest(requestBodyBytes);
            return bytesToHex(digest).toLowerCase();  // Преобразуем в шестнадцатеричный формат
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при вычислении MD5", e);
        }
    }

    private String calculateSignature(ClientRequest request, String dateHeader, String contentTypeHeader, String contentMD5Header) {
        try {
            String method = request.method().name();
            String path = request.url().getPath(); // Путь без параметров GET

            String stringToSign = String.join("\n",
                    method.toUpperCase(),
                    contentMD5Header,
                    contentTypeHeader,
                    dateHeader,
                    path
            );

            Mac sha1_HMAC = Mac.getInstance("HmacSHA1");
            SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA1");
            sha1_HMAC.init(secretKeySpec);

            byte[] hmacData = sha1_HMAC.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hmacData).toLowerCase();  // Преобразуем в шестнадцатеричный формат
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при вычислении подписи", e);
        }
    }

    // Метод для конвертации байтов в шестнадцатеричную строку
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}