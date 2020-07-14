package com.busfor.pagination;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PaginationTest {

	List<Integer> list = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
	
	@Test
	public void paginationTestSame() {
		Pagination<Integer> pagination = new Pagination<Integer>(list, 0, 0);
		List<Integer> page = pagination.page();
		assertEquals(list.size(), page.size());
	}
	
	@Test
	public void paginationTest() {
		Pagination<Integer> pagination = new Pagination<Integer>(list, 1, 5);
		List<Integer> page = pagination.page();
		assertEquals(2, page.get(2));
	}
	
	@Test
	public void paginationTestLastPage() {
		Pagination<Integer> pagination = new Pagination<Integer>(list, 3, 5);
		List<Integer> page = pagination.page();
		assertEquals(1, page.size());
	}
	
	@Test
	public void paginationTestEmpty() {
		Pagination<Integer> pagination = new Pagination<Integer>(list, 4, 5);
		List<Integer> page = pagination.page();
		assertTrue(page.isEmpty());
	}
}
