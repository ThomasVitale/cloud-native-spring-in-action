package com.polarbookshop.quoteservice.domain;

public record Quote (
	String content,
	String author,
	Genre genre
){}