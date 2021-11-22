package com.klm.cases.df.model;

import lombok.Data;

@Data
public class Fare {
	  double amount;
	  Currency currency;
	  String origin, destination;
}
