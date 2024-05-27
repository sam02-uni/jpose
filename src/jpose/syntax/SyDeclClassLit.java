package jpose.syntax;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public record SyDeclClassLit(String className, String superclassName, List<SyDeclVariable> fields, List<SyDeclMethod> methods) implements SyDeclClass {
	public SyDeclClassLit {
		Objects.requireNonNull(className);
		Objects.requireNonNull(superclassName);
		Objects.requireNonNull(fields);
		Objects.requireNonNull(methods);
		for (SyDeclVariable field : fields) {
			Objects.requireNonNull(field);
		}
		for (SyDeclMethod method : methods) {
			Objects.requireNonNull(method);
		}
	}

	public boolean hasField(String possibleFieldName) {
		Objects.requireNonNull(possibleFieldName);
		
		for (SyDeclVariable field : this.fields) {
			switch (field) {
			case SyDeclVariableLit syDeclVariableLit:
				if (possibleFieldName.equals(syDeclVariableLit.variableName())) {
					return true;
				}
				break;
			default: throw new AssertionError();
			}
		}
		return false;
	}

	public boolean hasMethod(String possibleMethodName) {
		Objects.requireNonNull(possibleMethodName);
		
		for (SyDeclMethod method : this.methods) {
			switch (method) {
			case SyDeclMethodLit syDeclMethodLit:
				if (possibleMethodName.equals(syDeclMethodLit.methodName())) {
					return true;
				}
				break;
			default: throw new AssertionError();
			}
		}
		return false;
	}
	
	public Optional<SyDeclVariable> fdecl(String possibleFieldName) {
		Objects.requireNonNull(possibleFieldName);
		
		for (SyDeclVariable field : this.fields) {
			switch (field) {
			case SyDeclVariableLit syDeclVariableLit:
				if (possibleFieldName.equals(syDeclVariableLit.variableName())) {
					return Optional.of(syDeclVariableLit);
				}
				break;
			default: throw new AssertionError();
			}
		}
		return Optional.empty();
	}

	public Optional<SyDeclMethod> mdecl(String possibleMethodName) {
		Objects.requireNonNull(possibleMethodName);
		
		for (SyDeclMethod method : this.methods) {
			switch (method) {
			case SyDeclMethodLit syDeclMethodLit:
				if (possibleMethodName.equals(syDeclMethodLit.methodName())) {
					return Optional.of(syDeclMethodLit);
				}
				break;
			default: throw new AssertionError();
			}
		}
		return Optional.empty();
	}
}
