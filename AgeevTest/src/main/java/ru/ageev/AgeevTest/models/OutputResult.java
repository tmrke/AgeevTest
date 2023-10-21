package ru.ageev.AgeevTest.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "result")
public class OutputResult {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "result")
    private double result;

    @Column(name = "operation")
    private String operation;

    @Column(name = "date")
    private Date date;

    public OutputResult() {

    }

    public OutputResult(double result, String operation, Date date) {
        this.result = result;
        this.operation = operation;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public void setData(OutputResult outputResult) {
        this.result = outputResult.getResult();
        this.operation = outputResult.getOperation();
        this.date = outputResult.getDate();
    }
}
