package com.example.converter;

import com.example.converter.domain.Currency;
import com.example.converter.domain.History;
import com.example.converter.repos.CurrencyRepo;
import com.example.converter.repos.HistoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private CurrencyRepo currencyRepo;
    @Autowired
    private HistoryRepo historyRepo;

    private Date date;

    @GetMapping("/")
    public String hello(Map<String, Object> model) {
        return "hello";
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model) {
        Iterable<Currency> currencies = currencyRepo.findAll();
        model.put("currencies", currencies);
        date = new Date();
        return "main";
    }

    @GetMapping("/history")
    public String history(Map<String, Object> model) {
        Iterable<History> histories = historyRepo.findAll();
        model.put("histories", histories);
        return "history";
    }

    @PostMapping("/main")
    public String add(@RequestParam List<String> charCode, @RequestParam String count, Map<String, Object> model)
    {
        Iterable<Currency> currencies = currencyRepo.findAll();
        model.put("currencies", currencies);

        Date date1 = new Date();

        if (date1.getDay() != date.getDay())
        {
            Application.req_to_the_bank();
            date = new Date();
        }

        Currency raw_value_from = currencyRepo.findByCharCode(charCode.get(0));
        Currency raw_value_to = currencyRepo.findByCharCode(charCode.get(1));

        Double value_from = Double.parseDouble(raw_value_from.getValue().replace(",", "."));
        Double value_to = Double.parseDouble(raw_value_to.getValue().replace(",", "."));

        Double result = value_from / value_to * Double.parseDouble(count);
        result = Math.round(result * 100.0) / 100.0;

        History history = new History(count, result.toString(), raw_value_from.getCharCode(), raw_value_to.getCharCode(), date1);
        historyRepo.save(history);

        return "main";
    }
}
