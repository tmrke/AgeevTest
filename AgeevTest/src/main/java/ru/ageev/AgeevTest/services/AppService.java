package ru.ageev.AgeevTest.services;

import org.springframework.stereotype.Service;
import ru.ageev.AgeevTest.models.InputNumber;
import ru.ageev.AgeevTest.models.OutputResult;
import ru.ageev.AgeevTest.repositories.NumberRepositories;
import ru.ageev.AgeevTest.repositories.ResultRepositories;
import ru.ageev.AgeevTest.type.InputOutputType;
import ru.ageev.AgeevTest.type.OperationType;

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

    public OutputResult calculate(OperationType operation, InputOutputType input, InputOutputType output) {

        OutputResult outputResult = new OutputResult();

        switch (input) {
            case  DATABASE -> {
                outputResult.setData(getResult(operation, input));
            }

            case JSON, HTML -> {
                new OutputResult();
            }

            default -> new OutputResult();
        }

        switch (output) {
            case DATABASE -> {
                resultRepositories.save(outputResult);
            }
            case JSON -> {
            }
            case HTML -> {
            }
        }

        return outputResult;
    }


    private List<InputNumber> getInputNumbers(InputOutputType input) {
        switch (input) {
            case DATABASE -> {
                return numberRepositories.findAll();
            }
            case JSON -> {
            }

        }

        return null;
    }

    private OutputResult getResult(OperationType operation, InputOutputType input) {
        List<InputNumber> numbers = getInputNumbers(input);
        switch (operation) {
            case ADDITION -> {
                if (numbers != null) {
                    return calculateService.addition(numbers);
                }

                return new OutputResult();
            }
            case MULTIPLICATION -> {
                if (numbers != null) {
                    return calculateService.multiplication(numbers);
                }

                return new OutputResult();
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
}
