package com.klm.cases.df.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class ExceptionResponse {
	 Date timestamp;
	 String message;
	 String details;
}
