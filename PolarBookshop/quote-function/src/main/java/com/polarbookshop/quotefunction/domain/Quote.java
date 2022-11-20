package com.polarbookshop.quotefunction.domain;

public record Quote (
	String content,
	String author,
	Genre genre
){}
