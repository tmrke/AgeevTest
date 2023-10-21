package ru.ageev.AgeevTest.services;

import org.springframework.stereotype.Service;
import ru.ageev.AgeevTest.models.InputNumber;
import ru.ageev.AgeevTest.models.OutputResult;
import ru.ageev.AgeevTest.type.OperationType;

import java.util.Date;
import java.util.List;

@Service
public class CalculateService {
   public OutputResult addition(List<InputNumber> numbers) {
        OutputResult outputResult = new OutputResult();

        try {
            double result = numbers.stream().mapToDouble(InputNumber::getNumber).sum();
            setDataOutputResult(result, OperationType.ADDITION.name(), outputResult);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(e.getMessage());
        }

        return outputResult;
    }

    public OutputResult multiplication(List<InputNumber> numbers) {
        double result = 1;
        OutputResult outputResult = new OutputResult();

        try {
            for (InputNumber number : numbers) {
                result *= number.getNumber();
            }

            setDataOutputResult(result, OperationType.MULTIPLICATION.name(), outputResult);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(e.getMessage());
        }

        return outputResult;
    }

    public OutputResult multiplicationAndAddition(List<InputNumber> numbers) {
        OutputResult outputResult = new OutputResult();

        try {
            double result = numbers.get(0).getNumber() * numbers.get(1).getNumber() + numbers.get(2).getNumber();
            setDataOutputResult(result, OperationType.MULTIPLICATION_AND_ADDITION.name(), outputResult);
        } catch (NumberFormatException | NullPointerException e) {
            throw new NumberFormatException(e.getMessage());
        }

        return outputResult;
    }

    public OutputResult average(List<InputNumber> numbers) {
        OutputResult outputResult = new OutputResult();

        try {
            double result = numbers.stream().mapToDouble(InputNumber::getNumber).average().orElse(0);
            setDataOutputResult(result, OperationType.AVERAGE.name(), outputResult);
        } catch (NumberFormatException | NullPointerException e) {
            throw new NumberFormatException(e.getMessage());
        }

        return outputResult;
    }

    private void setDataOutputResult(double result, String operation, OutputResult outputResult) {
        outputResult.setResult(result);
        outputResult.setOperation(operation);
        outputResult.setDate(new Date(System.currentTimeMillis()));
    }
}
