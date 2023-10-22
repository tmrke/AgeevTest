package ru.ageev.AgeevTest.controllers;

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

        if (input.equals(InputOutputType.HTML)) {
            model.addAttribute("input", InputOutputType.HTML_WITH_DATA);
            model.addAttribute("output", output);
            model.addAttribute("operation", operation);

            return "input_form";
        }

        OutputResult result = service.calculate(operation, input, output, numbersString);
        redirectAttributes.addFlashAttribute("result", result);

        return "redirect:result";
    }

    @GetMapping("result")
    public String result(Model model) {
        OutputResult outputResult = (OutputResult) model.getAttribute("result");
        model.addAttribute("result", outputResult);

        return "result";
    }
}

