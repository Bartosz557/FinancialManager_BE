package com.example.FinancialManager.DTO.RequestBody.ConfigurationRequestModel;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class SubCategoriesModelDTO {

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
