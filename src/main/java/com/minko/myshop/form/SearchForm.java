package com.minko.myshop.form;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchForm {

	private String query;
	private List<Integer> categories;
	private List<Integer> producers;

	public SearchForm(String query, String[] categories, String[] producers) {
		super();
		this.query = query;
		this.categories = convert(categories);
		this.producers = convert(producers);
	}

	private List<Integer> convert(String[] args) {		
		if (args == null) {
			return Collections.emptyList();
		} else {
			List<Integer> result = new ArrayList<>(args.length);
			for (String str : args) {
				result.add(Integer.parseInt(str));
			}
			return result;
		}
	}
	
	public boolean isCategoriesEmpty() {
		return categories.isEmpty();
	}
	
	public boolean isProducersEmpty() {
		return producers.isEmpty();
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public List<Integer> getCategories() {
		return categories;
	}

	public void setCategories(List<Integer> categories) {
		this.categories = categories;
	}

	public List<Integer> getProducers() {
		return producers;
	}

	public void setProducers(List<Integer> producers) {
		this.producers = producers;
	}

}
