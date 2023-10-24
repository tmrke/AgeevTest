package ru.ageev.AgeevTest.controllers;

import com.google.gson.Gson;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.ageev.AgeevTest.models.OutputResult;
import ru.ageev.AgeevTest.services.AppService;
import ru.ageev.AgeevTest.type.InputOutputType;
import ru.ageev.AgeevTest.type.OperationType;

@org.springframework.stereotype.Controller
@RequestMapping()
public class AppController {
    private final AppService service;
    private final static String RESULT_STRING = "result";

    public AppController(AppService service) {
        this.service = service;
    }

    @GetMapping("calculator")
    public String start() {
        return "calculator";
    }

    @PostMapping("calculate")
    public String calculate(
            @RequestParam("operation") String operationString,
            @RequestParam("input") String inputString,
            @RequestParam("output") String outputString,
            @RequestParam(value = "numbers", required = false) String numbersString,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        OperationType operation = OperationType.valueOf(operationString);
        InputOutputType input = InputOutputType.valueOf(inputString);
        InputOutputType output = InputOutputType.valueOf(outputString);

        model.addAttribute("output", output);
        model.addAttribute("operation", operation);

        switch (input) {
            case HTML -> {
                model.addAttribute("input", InputOutputType.HTML_WITH_DATA);

                return "input_form";
            }
            case JSON -> {
                model.addAttribute("input", InputOutputType.JSON_WITH_DATA);

                return "input_json";
            }
        }

        OutputResult result = service.calculate(operation, input, output, numbersString);
        redirectAttributes.addFlashAttribute(RESULT_STRING, result);

        if (output.equals(InputOutputType.JSON)) {
            return "redirect:json_result";
        }

        return "redirect:result";
    }

    @GetMapping(RESULT_STRING)
    public String getResult(Model model) {
        OutputResult outputResult = (OutputResult) model.getAttribute(RESULT_STRING);
        model.addAttribute(RESULT_STRING, outputResult);

        return RESULT_STRING;
    }

    @GetMapping("json_result")
    public String getJsonResult(Model model) {
        OutputResult outputResult = (OutputResult) model.getAttribute(RESULT_STRING);
        Gson gson = new Gson();

        model.addAttribute(RESULT_STRING, gson.toJson(outputResult));

        return "json_result";
    }

    @GetMapping("input_url")
    public String getResultFromUrl(@RequestParam("operation") String operationString,
                                   @RequestParam("output") String outputString,
                                   @RequestParam(value = "numbers") String numbersString,
                                   RedirectAttributes redirectAttributes) {
        OperationType operation = OperationType.valueOf(operationString);
        InputOutputType output = InputOutputType.valueOf(outputString);

        OutputResult result = service.calculate(operation, InputOutputType.URL, output, numbersString);
        redirectAttributes.addFlashAttribute(RESULT_STRING, result);

        if (output.equals(InputOutputType.JSON)) {
            return "redirect:json_result";
        }

        return "redirect:result";
    }
}

