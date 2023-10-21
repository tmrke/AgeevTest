package ru.ageev.AgeevTest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
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

import java.net.http.HttpClient;

@org.springframework.stereotype.Controller
@RequestMapping()
public class AppController {
    @Autowired
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
            RedirectAttributes redirectAttributes
    ) {
        OperationType operation = OperationType.valueOf(operationString);
        InputOutputType input = InputOutputType.valueOf(inputString);
        InputOutputType output = InputOutputType.valueOf(outputString);

        OutputResult outputResult = service.calculate(operation, input, output);
        double result = outputResult.getResult();

        redirectAttributes.addFlashAttribute("result", result);

        return "redirect:result";
    }

    @GetMapping("result")
    public String result(Model model) {
        Double result = (Double) model.getAttribute("result");
        model.addAttribute("result", result);

        return "result";
    }
}

