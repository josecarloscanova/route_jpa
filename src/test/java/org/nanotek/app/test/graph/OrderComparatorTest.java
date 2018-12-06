package org.nanotek.app.test.graph;

import java.util.LinkedList;
import java.util.stream.Stream;

import org.junit.Test;

public class OrderComparatorTest {

	@Test
	public void test() {
		Integer[] orderOfInsertion = {3, 1 , 2 , 4, 0};
		LinkedList<Integer> theList = new LinkedList<>();
		Stream.of(orderOfInsertion).forEach(x -> theList.add(x));
		LinkedListComparator<Integer> comparator = new LinkedListComparator<Integer>(theList);
		int result = 0;
		int value = 0;
		for (int i = 0 ; i < orderOfInsertion.length -1; i++) { 
			System.out.println(comparator.compare(value, theList.get(i)));
		}
	}

}
