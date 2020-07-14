package com.busfor.pagination;

import java.util.ArrayList;
import java.util.List;

public class Pagination<T> {

	private final List<T> source;
	private final int page;
	private final int pageLimit;

	public Pagination(List<T> source, int page, int pageLimit) {
		this.source = source;
		this.page = page;
		this.pageLimit = pageLimit;
	}

	public List<T> page() {
		List<T> sublist;
		if (page > 0 && pageLimit > 0) {
			int firstIndex = firstIndex();
			int lastIndex = lastIndex();
			if (lastIndex > source.size()) {
				lastIndex = source.size();
			}
			if (firstIndex > source.size()) {
				sublist = new ArrayList<T>();
			} else {
				sublist = source.subList(firstIndex, lastIndex);
			}
		} else {
			sublist = source;
		}
		return sublist;
	}

	private int firstIndex() {
		return pageLimit * (page - 1);
	}

	private int lastIndex() {
		return firstIndex() + pageLimit;
	}
}
