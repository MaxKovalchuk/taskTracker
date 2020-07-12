package com.busfor.db.select;

import java.util.List;
import java.util.function.Predicate;

public interface QuerySelect<T> {

	List<T> select();
}
