package edu.csuohio.photomanager.data;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

/**
 * Base document class for the document based database. Extend this to inherit
 * the serializable <code>ID</code> field in your document classes. The default
 * format supported by MongoDB is <code>String</code>
 *
 * @param <ID> A serializable object to be used as the ID of the document.
 */
public class BaseDocument<ID extends Serializable> {

	@Id
	ID id;

	public ID getId() {
		return id;
	}

	@Override
	public String toString() {
		return "BaseDocument [id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		BaseDocument<?> other = (BaseDocument<?>) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}
}
