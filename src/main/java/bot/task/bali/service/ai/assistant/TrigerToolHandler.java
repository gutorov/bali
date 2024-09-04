package bot.task.bali.service.ai.assistant;

import dev.langchain4j.agent.tool.Tool;


public class TrigerToolHandler {

    @Tool("Я готов купить")
    String readyToBy() {
        System.out.println("Executing tool 'buy'");
        return "Я перенаправил вас в колонку CRM";
    }
}
