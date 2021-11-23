package com.klm.cases.df.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//constructors needed for creating test data
@AllArgsConstructor
@NoArgsConstructor
public class Location {
	String code, name, description;
}
