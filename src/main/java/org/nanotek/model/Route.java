package org.nanotek.model;

import java.util.Objects;

/**
 * Stored a "unweweighted information about" a route on the problem.
 * @author Usuario
 *
 */
public class Route<T> {

	protected T from; 
	
	protected T to; 
	
	public Route () {
	}
	
	public Route(T from, T to) {
		super();
		this.from = from;
		this.to = to;
	}

	public T getFrom() {
		return from;
	}

	public void setFrom(T from) {
		this.from = from;
	}

	public T getTo() {
		return to;
	}


	public void setTo(T to) {
		this.to = to;
	}

	@Override
	public int hashCode() {
		return Objects.hash(from , to);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Route<?> other = (Route<?>) obj;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Route [from=" + from + "-" + to + "]";
	}
}
