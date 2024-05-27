package jpose.semantics.types;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.SequencedMap;

import jpose.syntax.SyDeclClass;
import jpose.syntax.SyDeclClassLit;
import jpose.syntax.SyDeclVariable;
import jpose.syntax.SyDeclVariableLit;
import jpose.syntax.SyProgram;
import jpose.syntax.SyProgramLit;
import jpose.syntax.SyValue;
import jpose.syntax.SyValueUnassumed;

public final class SemObject {
	private final SyProgramLit syProgramLit;
	private final LinkedHashMap<String, SyValue> memory;
	private String className;
	
	public SemObject(SyProgram syProgram, String className, boolean symbolic) {
		Objects.requireNonNull(syProgram);
		Objects.requireNonNull(className);
		
		switch (syProgram) {
		case SyProgramLit syProgramLit:
			this.syProgramLit = syProgramLit;
			var syDeclClassOptional = syProgramLit.cdecl(className);
			var fields = syDeclClassOptional.map(c -> {
				switch (c) {
				case SyDeclClassLit syDeclClassLit:
					return syDeclClassLit.fields();
				default: throw new AssertionError();
				}
			}).orElseThrow(() -> { throw new RuntimeException("Class " + className + " does not exist."); });
			this.memory = new LinkedHashMap<>();
			this.className = className;
			for (SyDeclVariable syDeclVariable : fields) {
				switch (syDeclVariable) {
				case SyDeclVariableLit syDeclVariableLit:
					this.memory.put(syDeclVariableLit.variableName(), (symbolic ? new SyValueUnassumed() : syDeclVariableLit.syType().ini()));
					break;
				default: throw new AssertionError();
				}
			}
			break;
		default: throw new AssertionError();	
		}
	}
	
	public SemObject(SemObject other) {
		this.syProgramLit = other.syProgramLit;
		this.memory = new LinkedHashMap<>(other.memory);
		this.className = other.className;
	}
	
	public Optional<SyValue> get(String fieldName) {
		Objects.requireNonNull(fieldName);
		
		return Optional.ofNullable(this.memory.get(fieldName));
	}
	
	public void upd(String fieldName, SyValue syValue) {
		Objects.requireNonNull(fieldName);
		Objects.requireNonNull(syValue);
		
		if (this.memory.containsKey(fieldName)) {
			this.memory.put(fieldName, syValue);
		} else {
			throw new RuntimeException("Tried to modify nonexistent field " + fieldName);
		}
	}
	
	public SequencedMap<String, SyValue> memory() {
		return Collections.unmodifiableSequencedMap(this.memory);
	}
	
	public String className() {
		return this.className;
	}
	
	public void refine(String subclassName) {
		Objects.requireNonNull(subclassName);
		
		for (SyDeclClass syDeclClass : this.syProgramLit.classes()) {
			switch (syDeclClass) {
			case SyDeclClassLit syDeclClassLit:
				if (this.syProgramLit.isSubclass(syDeclClassLit.className(), this.className) &&
				    this.syProgramLit.isSubclass(subclassName, syDeclClassLit.className()) &&
				    !this.className.equals(syDeclClassLit.className())) {
					for (SyDeclVariable syDeclVariable : syDeclClassLit.fields()) {
						switch (syDeclVariable) {
						case SyDeclVariableLit syDeclVariableLit:
							this.memory.put(syDeclVariableLit.variableName(), new SyValueUnassumed());
							break;
						default: throw new AssertionError();
						}
					}
				}
			default: throw new AssertionError();
			}
		}
	}

	public boolean equivalent(SemObject other) {
		Objects.requireNonNull(other);
		
		return Objects.equals(this.className, other.className) && 
		       Objects.equals(this.memory, other.memory) && 
		       Objects.equals(this.syProgramLit, other.syProgramLit);
	}

	public boolean equivalentExcept(SemObject other, String fieldName) {
		Objects.requireNonNull(other);
		Objects.requireNonNull(fieldName);
		
		if (!Objects.equals(this.className, other.className)) {
			return false;
		}
		if (!Objects.equals(this.syProgramLit, other.syProgramLit)) {
			return false;
		}
		if (!this.memory.containsKey(fieldName) || !other.memory.containsKey(fieldName)) {
			return false;
		}
		var otherMemoryClone = new LinkedHashMap<String, SyValue>(other.memory);
		otherMemoryClone.put(fieldName, this.memory.get(fieldName));
		if (!Objects.equals(this.memory, otherMemoryClone)) {
			return false;
		}
		
		return true;
	}
}
