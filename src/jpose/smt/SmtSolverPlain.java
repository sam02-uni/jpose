package jpose.smt;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import jpose.semantics.types.SemConfiguration;
import jpose.syntax.SyProgram;
import jpose.syntax.SyValue;

public final class SmtSolverPlain implements SmtSolver {
	private final Path solverPath;
	private final SmtPrinter smtPrinter;
	private final Path solverInputFile;
	private final Path solverOutputFile;
	private long totalSolverTimeMillis;
	private long totalNumberOfQueries;
	private long totalNumberOfQueriesSat;
	
	public SmtSolverPlain(Path solverPath) {
		this.solverPath = solverPath;
		this.smtPrinter = new SmtPrinter();
		try {
			this.solverInputFile = Files.createTempFile("query", ".smt");
			this.solverOutputFile = Files.createTempFile("answer", ".smt");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		this.totalSolverTimeMillis = 0L;
		this.totalNumberOfQueries = 0L;
		this.totalNumberOfQueriesSat = 0L;
	}
	
	@Override
	public boolean querySat(SemConfiguration J) {
		++this.totalNumberOfQueries;
		System.out.print("*");
		var smtQuery = this.smtPrinter.configToSmt(J);
		saveToFile(smtQuery);
		runSolver();
		var sat = readResult();
		if (sat) {
			++this.totalNumberOfQueriesSat;
		}
		return sat;
	}
	
	boolean querySat(SyProgram P, List<SyValue> pathCondition) {
		++this.totalNumberOfQueries;
		System.out.print("*");
		var smtQuery = this.smtPrinter.pathConditionToSmt(P, pathCondition);
		saveToFile(smtQuery);
		runSolver();
		var sat = readResult();
		if (sat) {
			++this.totalNumberOfQueriesSat;
		}
		return sat;
	}
	
	@Override
	public long totalSolverTimeMillis() {
		return this.totalSolverTimeMillis;
	}
	
	@Override
	public long totalNumberOfQueries() {
		return this.totalNumberOfQueries;
	}
	
	@Override
	public long totalNumberOfQueriesSat() {
		return this.totalNumberOfQueriesSat;
	}
	
	private void saveToFile(String smtQuery) {
		try (final BufferedWriter w = Files.newBufferedWriter(this.solverInputFile)) {
			w.write(smtQuery);
			w.write("(check-sat)\n");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void runSolver() {
		final ArrayList<String> commandLine = new ArrayList<>();
		commandLine.add(this.solverPath.toString());
		commandLine.add("-smt2");
		commandLine.add(this.solverInputFile.toString());
		final ProcessBuilder pb = new ProcessBuilder(commandLine).redirectErrorStream(true).redirectOutput(this.solverOutputFile.toFile());
		final Process p;
		final long startTime = System.currentTimeMillis();
		try {
			p = pb.start();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		try {
			p.waitFor();
		} catch (InterruptedException e) {
			//this should never happen
			throw new AssertionError(e);
		}
		final long elapsedTime = System.currentTimeMillis() - startTime;
		this.totalSolverTimeMillis += elapsedTime;
	}
	
	private boolean readResult() {
		final String answer;
		try {
			answer = Files.readString(this.solverOutputFile).trim();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		if ("unsat".equals(answer)) {
			return false;
		} else if ("sat".equals(answer)) {
			return true;
		} else {
			throw new RuntimeException("Unrecognized answer from smt solver, see files " + solverInputFile.toString() + " and " + solverOutputFile.toString());
		}
	}
}
