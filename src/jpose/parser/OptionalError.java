package jpose.parser;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;

public abstract class OptionalError<T> {
	private static class OptionalErrorSome<T> extends OptionalError<T> {
		private final T value;
		
		OptionalErrorSome(T value) {
			this.value = value;
		}

		@Override
		public T get() {
			return this.value;
		}

		@Override
		public boolean isPresent() {
			return true;
		}

		@Override
		public String getErrorMessage() {
			throw new NoSuchElementException("No error message present");
		}

		@Override
		public <U> OptionalErrorSome<U> map(Function<T, U> f) {
			return new OptionalErrorSome<U>(f.apply(this.value));
		}
	}
	
	private static class OptionalErrorNone<T> extends OptionalError<T> {
		private final String errorMessage;
		
		OptionalErrorNone(String errorMessage) {
			this.errorMessage = errorMessage;
		}

		@Override
		public T get() {
			throw new NoSuchElementException("No value present");
		}

		@Override
		public boolean isPresent() {
			return false;
		}

		@Override
		public String getErrorMessage() {
			return this.errorMessage;
		}

		@SuppressWarnings("unchecked")
		@Override
		public <U> OptionalErrorNone<U> map(Function<T, U> f) {
			return (OptionalErrorNone<U>) this;
		}
	}

	public static <T> OptionalError<T> of() {
		return new OptionalErrorSome<>(null);
	}
	
	public static <T> OptionalError<T> of(T value) {
        return new OptionalErrorSome<>(Objects.requireNonNull(value));
    }

	public static<T> OptionalError<T> error(String errorMessage) {
        return new OptionalErrorNone<>(Objects.requireNonNull(errorMessage));
	}
	
	public abstract T get();
	
	public abstract String getErrorMessage();

    public abstract boolean isPresent();

	public abstract <U> OptionalError<U> map(Function<T, U> f);
}
