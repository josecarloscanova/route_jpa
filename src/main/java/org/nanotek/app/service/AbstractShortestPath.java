package org.nanotek.app.service;

import com.google.common.collect.Table;
import com.google.common.graph.ValueGraph;

public abstract class AbstractShortestPath<N,E> implements Function<ValueGraph<N,Integer>,Table<N,N,E>> {
}
