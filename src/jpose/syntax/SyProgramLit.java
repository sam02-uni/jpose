package jpose.syntax;

import java.lang.AssertionError;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.SequencedSet;

public record SyProgramLit(List<SyDeclClass> classes, SyExpression syExpression) implements SyProgram {
	public SyProgramLit {
		Objects.requireNonNull(classes);
		Objects.requireNonNull(syExpression);
		for (SyDeclClass syDeclClass : classes) {
			Objects.requireNonNull(syDeclClass);
		}
	}
	
	public boolean isSubclass(String possibleSubclassName, String possibleSuperclassName) {
		Objects.requireNonNull(possibleSubclassName);
		Objects.requireNonNull(possibleSuperclassName);
		
		for (SyDeclClass syDeclClass : this.classes) {
			switch (syDeclClass) {
			case SyDeclClassLit syDeclClassLit:
				if (possibleSubclassName.equals(syDeclClassLit.className())) {
					if (possibleSuperclassName.equals(syDeclClassLit.superclassName())) {
						return true;
					} else {
						return isSubclass(syDeclClassLit.superclassName(), possibleSuperclassName);
					}
				}
				break;
			default: throw new AssertionError();
			}
		}
		return false;
	}
	
	public boolean hasClass(String possibleClassName) {
		Objects.requireNonNull(possibleClassName);
		
		for (SyDeclClass syDeclClass : this.classes) {
			switch (syDeclClass) {
			case SyDeclClassLit syDeclClassLit:
				if (possibleClassName.equals(syDeclClassLit.className())) {
					return true;
				}
				break;
			default: throw new AssertionError();
			}
		}
		return false;
	}
	
	public Optional<SyDeclClass> cdecl(String possibleClassName) {
		Objects.requireNonNull(possibleClassName);
		
		for (SyDeclClass syDeclClass : this.classes) {
			switch (syDeclClass) {
			case SyDeclClassLit syDeclClassLit:
				if (possibleClassName.equals(syDeclClassLit.className())) {
					return Optional.of(syDeclClassLit);
				}
				break;
			default: throw new AssertionError();
			}
		}
		return Optional.empty();
	}
	
	public boolean seesMethod(String possibleMethodName, String possibleSubclassName, String possibleSuperclassName) {
		Objects.requireNonNull(possibleMethodName);
		Objects.requireNonNull(possibleSubclassName);
		Objects.requireNonNull(possibleSuperclassName);
		
		return isSubclass(possibleSubclassName, possibleSuperclassName) &&
		       cdecl(possibleSuperclassName).map(c -> {
					switch (c) {
					case SyDeclClassLit syDeclClassLit:
						return syDeclClassLit.hasMethod(possibleMethodName);
					default: throw new AssertionError();
					}
		       }).orElse(false) &&
		       this.classes.stream().allMatch(c -> {
					switch (c) {
					case SyDeclClassLit syDeclClassLit:
						return possibleSubclassName.equals(syDeclClassLit.className()) ||
						       possibleSuperclassName.equals(syDeclClassLit.className()) ||
						       !isSubclass(syDeclClassLit.className(), possibleSuperclassName) ||
						       !isSubclass(possibleSubclassName, syDeclClassLit.className()) ||
						       !syDeclClassLit.hasMethod(possibleMethodName);
					default: throw new AssertionError();
					}
		       });
	}
	
	public boolean recvMethod(String possibleMethodName, String possibleSubclassName, String possibleSuperclassName) {
		Objects.requireNonNull(possibleMethodName);
		Objects.requireNonNull(possibleSubclassName);
		Objects.requireNonNull(possibleSuperclassName);
		
		return (!possibleSubclassName.equals(possibleSuperclassName) &&
		       seesMethod(possibleMethodName, possibleSubclassName, possibleSuperclassName)) ||
		       (possibleSubclassName.equals(possibleSuperclassName) &&
		       cdecl(possibleSubclassName).map(c -> {
                   switch (c) {
                   case SyDeclClassLit syDeclClassLit:
                       return syDeclClassLit.hasMethod(possibleMethodName);
                   default: throw new AssertionError();
                   }
               }).orElse(false));
	}
	
	public Optional<SyDeclClass> methodProvider(String possibleMethodName, String possibleSubclassName) {
		Objects.requireNonNull(possibleMethodName);
		Objects.requireNonNull(possibleSubclassName);
		
		for (SyDeclClass syDeclClass : this.classes) {
			switch (syDeclClass) {
			case SyDeclClassLit syDeclClassLit:
				if (recvMethod(possibleMethodName, possibleSubclassName, syDeclClassLit.className())) {
					return Optional.of(syDeclClassLit);
				}
				break;
			default: throw new AssertionError();
			}
		}
		return Optional.empty();
	}
	
	public Optional<SyDeclClass> classWithField(String possibleFieldName) {
		Objects.requireNonNull(possibleFieldName);
		
		for (SyDeclClass syDeclClass : this.classes) {
			switch (syDeclClass) {
			case SyDeclClassLit syDeclClassLit:
				if (syDeclClassLit.hasField(possibleFieldName)) {
					return Optional.of(syDeclClassLit);
				}
				break;
			default: throw new AssertionError();
			}
		}
		return Optional.empty();
	}
	
	public SequencedSet<SyDeclClass> implementors(String possibleMethodName) {
		Objects.requireNonNull(possibleMethodName);
		
		final LinkedHashSet<SyDeclClass> retVal = new LinkedHashSet<>();
		for (SyDeclClass syDeclClass : this.classes) {
			switch (syDeclClass) {
			case SyDeclClassLit syDeclClassLit:
				if (syDeclClassLit.hasMethod(possibleMethodName)) {
					retVal.add(syDeclClassLit);
				}
				break;
			default: throw new AssertionError();
			}
		}
		return retVal;
	}
	
	public SequencedSet<SyDeclClass> overriders(String possibleMethodName, String possibleSuperclassName) {
		Objects.requireNonNull(possibleMethodName);
		Objects.requireNonNull(possibleSuperclassName);
		
		final LinkedHashSet<SyDeclClass> retVal = new LinkedHashSet<>();
		for (SyDeclClass syDeclClass : this.classes) {
			switch (syDeclClass) {
			case SyDeclClassLit syDeclClassLit:
				if (seesMethod(possibleMethodName, syDeclClassLit.className(), possibleSuperclassName) &&
				    syDeclClassLit.hasMethod(possibleMethodName) &&
				    !possibleSuperclassName.equals(syDeclClassLit.className())) {
					retVal.add(syDeclClassLit);
				}
				break;
			default: throw new AssertionError();
			}
		}
		return retVal;
	}
}
