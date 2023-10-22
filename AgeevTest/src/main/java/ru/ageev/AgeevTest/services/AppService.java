package ru.ageev.AgeevTest.services;

import org.springframework.stereotype.Service;
import ru.ageev.AgeevTest.models.InputNumber;
import ru.ageev.AgeevTest.models.OutputResult;
import ru.ageev.AgeevTest.repositories.NumberRepositories;
import ru.ageev.AgeevTest.repositories.ResultRepositories;
import ru.ageev.AgeevTest.type.InputOutputType;
import ru.ageev.AgeevTest.type.OperationType;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppService {
    private final NumberRepositories numberRepositories;
    private final ResultRepositories resultRepositories;
    private final CalculateService calculateService;

    public AppService(ResultRepositories resultRepositories, NumberRepositories numberRepositories, CalculateService calculateService) {
        this.numberRepositories = numberRepositories;
        this.resultRepositories = resultRepositories;
        this.calculateService = calculateService;
    }

    public OutputResult calculate(OperationType operation, InputOutputType input, InputOutputType output, String numbersString) {
        OutputResult outputResult = new OutputResult();
        outputResult.setData(getResult(operation, input, numbersString));

        switch (output) {
            case DATABASE -> {
                resultRepositories.save(outputResult);
            }
            case JSON -> {
            }
        }

        return outputResult;
    }

    private OutputResult getResult(OperationType operation, InputOutputType input, String numbersString) {
        List<InputNumber> numbers = new ArrayList<>();

        switch (input) {
            case DATABASE -> {
                numbers = getInputNumbersFromDb();
            }
            case HTML_WITH_DATA -> {
                numbers = getNumbersFromString(numbersString);
            }
        }

        switch (operation) {
            case ADDITION -> {
                return calculateService.addition(numbers);
            }
            case MULTIPLICATION -> {
                return calculateService.multiplication(numbers);
            }
            case MULTIPLICATION_AND_ADDITION -> {
                return calculateService.multiplicationAndAddition(numbers);
            }
            case AVERAGE -> {
                return calculateService.average(numbers);
            }
        }

        return new OutputResult();
    }

    private List<InputNumber> getInputNumbersFromDb() {
        return numberRepositories.findAll();
    }

    private List<InputNumber> getNumbersFromString(String numbersString) {
        String[] numberStringArray = numbersString.split(" ");

        List<InputNumber> numbers = new ArrayList<>();

        for (String numberString : numberStringArray) {
            try {
                double currentNumber = Double.parseDouble(numberString);
                numbers.add(new InputNumber(currentNumber));
            } catch (NumberFormatException e) {
                System.out.println(numbersString + " не является числом");
            }
        }

        return numbers;
    }

    private List<InputNumber> getInputNumbersFromJson() {
        return null;
    }
}
