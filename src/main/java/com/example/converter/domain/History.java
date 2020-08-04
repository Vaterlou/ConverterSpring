package com.example.converter.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String from_currency;
    private String to_currency;
    private String from_value;
    private String to_value;
    private Date date;

    public History() {
    }

    public History(String from_value, String to_value, String from_currency, String to_currency, Date date) {
        this.from_value = from_value;
        this.to_value = to_value;
        this.from_currency = from_currency;
        this.to_currency = to_currency;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFrom_value() {
        return from_value;
    }

    public void setFrom_value(String from_value) {
        this.from_value = from_value;
    }

    public String getTo_value() {
        return to_value;
    }

    public void setTo_value(String to_value) {
        this.to_value = to_value;
    }

    public String getFrom_currency() {
        return from_currency;
    }

    public void setFrom_currency(String from_currency) {
        this.from_currency = from_currency;
    }

    public String getTo_currency() {
        return to_currency;
    }

    public void setTo_currency(String to_currency) {
        this.to_currency = to_currency;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
