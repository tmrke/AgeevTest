package ru.ageev.AgeevTest.services;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ageev.AgeevTest.models.InputNumber;
import ru.ageev.AgeevTest.models.OutputResult;
import ru.ageev.AgeevTest.repositories.NumberRepositories;
import ru.ageev.AgeevTest.repositories.ResultRepositories;
import ru.ageev.AgeevTest.type.InputOutputType;
import ru.ageev.AgeevTest.type.OperationType;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
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

        if (output.equals(InputOutputType.DATABASE)) {
            resultRepositories.save(outputResult);
        }

        return outputResult;
    }

    private OutputResult getResult(OperationType operation, InputOutputType input, String numbersString) {
        List<InputNumber> numbers = new ArrayList<>();

        switch (input) {
            case DATABASE -> numbers = getInputNumbersFromDb();

            case HTML_WITH_DATA -> numbers = getNumbersFromString(numbersString);

            case URL -> numbers = getNumbersFromQueryParams(numbersString);

            case JSON_WITH_DATA -> {
                try {
                    numbers = getInputNumbersFromJson(numbersString);
                } catch (IllegalStateException | JsonSyntaxException | NullPointerException | NumberFormatException e) {
                    OutputResult outputResult = new OutputResult();
                    outputResult.setMessage("Ошибка формата JSON");

                    return outputResult;
                }
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

        return getFillInputNumbers(numbersString, numberStringArray);
    }

    private List<InputNumber> getNumbersFromQueryParams(String numbersString) {
        String[] numberStringArray = numbersString.split(",");

        return getFillInputNumbers(numbersString, numberStringArray);
    }

    private List<InputNumber> getFillInputNumbers(String numbersString, String[] numberStringArray) {
        List<InputNumber> numbers = new ArrayList<>();

        for (String numberString : numberStringArray) {
            try {
                double currentNumber = Double.parseDouble(numberString);
                numbers.add(new InputNumber(currentNumber));
            } catch (NumberFormatException e) {
                log.info(numberString + " не является числом");
            }
        }

        return numbers;
    }

    private List<InputNumber> getInputNumbersFromJson(String numbersString) throws NumberFormatException, IllegalStateException, NullPointerException {
        Gson gson = new Gson();
        List<InputNumber> inputNumbers = new ArrayList<>();
        List<Double> doubleNumbers;

        doubleNumbers = gson.fromJson(numbersString, new TypeToken<List<Double>>() {
        }.getType());

        for (Double doubleNumber : doubleNumbers) {
            inputNumbers.add(new InputNumber(doubleNumber));
        }

        return inputNumbers;
    }
}
