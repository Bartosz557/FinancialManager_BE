package com.example.FinancialManager.userService;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class SubCategories {

    private int groceries;
    private int diningOut;
    private int transport;
    private int entertainment;
    private int clothes;
    private int traveling;
    private int hobby;
    private int miscellaneous;

    public List<Integer> getLimitsArray() {
        Integer[] limitsArray = {groceries, diningOut, transport, entertainment, clothes, traveling, hobby, miscellaneous};
        return Arrays.asList(limitsArray);
    }
}
