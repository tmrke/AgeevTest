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

        if (numbers.isEmpty()) {
            outputResult.setMessage("Список чисел пуст, вычисление невозможно");
            return outputResult;
        }

        double result = numbers.stream().mapToDouble(InputNumber::getNumber).sum();
        setDataOutputResult(result, OperationType.ADDITION.name(), outputResult);

        return outputResult;
    }

    public OutputResult multiplication(List<InputNumber> numbers) {
        double result = 1;
        OutputResult outputResult = new OutputResult();

        if (numbers.isEmpty()) {
            outputResult.setMessage("Список чисел пуст, вычисление невозможно");
            return outputResult;
        }

        for (InputNumber number : numbers) {
            result *= number.getNumber();
        }

        setDataOutputResult(result, OperationType.MULTIPLICATION.name(), outputResult);

        return outputResult;
    }

    public OutputResult multiplicationAndAddition(List<InputNumber> numbers) {
        OutputResult outputResult = new OutputResult();

        if (numbers.isEmpty()) {
            outputResult.setMessage("Список чисел пуст, вычисление невозможно");
            return outputResult;
        }

        try {
            double result = numbers.get(0).getNumber() * numbers.get(1).getNumber() + numbers.get(2).getNumber();
            setDataOutputResult(result, OperationType.MULTIPLICATION_AND_ADDITION.name(), outputResult);
        } catch (IndexOutOfBoundsException e) {
            outputResult.setMessage("Введено менее трех значений, вычисление невозможно");
        }

        return outputResult;
    }

    public OutputResult average(List<InputNumber> numbers) {
        OutputResult outputResult = new OutputResult();

        if (numbers.isEmpty()) {
            outputResult.setMessage("Список чисел пуст, вычисление невозможно");
            return outputResult;
        }

        double result = numbers.stream().mapToDouble(InputNumber::getNumber).average().orElse(0);
        setDataOutputResult(result, OperationType.AVERAGE.name(), outputResult);

        return outputResult;
    }

    private void setDataOutputResult(double result, String operation, OutputResult outputResult) {
        outputResult.setResult(result);
        outputResult.setOperation(operation);
        outputResult.setDate(new Date(System.currentTimeMillis()));
    }
}
