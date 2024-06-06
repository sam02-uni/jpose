package jpose.smt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import jpose.semantics.types.SemConfiguration;
import jpose.syntax.SyProgram;
import jpose.syntax.SyValue;

public final class SmtSolverPlain implements SmtSolver {
	private final Path solverPath;
	private final SmtPrinter smtPrinter;
	private Process solver;
	private BufferedReader solverInputFile;
	private BufferedWriter solverOutputFile;
	private long totalSolverTimeMillis;
	private long totalNumberOfQueries;
	private long totalNumberOfQueriesSat;
	
	public SmtSolverPlain(Path solverPath) {
		this.solverPath = solverPath;
		this.smtPrinter = new SmtPrinter();
		runSolver();
		this.totalSolverTimeMillis = 0L;
		this.totalNumberOfQueries = 0L;
		this.totalNumberOfQueriesSat = 0L;
	}
	
	@Override
	public boolean querySat(SemConfiguration J) {
		++this.totalNumberOfQueries;
		//System.out.print("*");
		var smtQuery = this.smtPrinter.configToSmt(J);
		final long startMillis = System.currentTimeMillis();
		sendToSolver(smtQuery);
		var sat = readResult();
		this.totalSolverTimeMillis += System.currentTimeMillis() - startMillis;
		if (sat) {
			++this.totalNumberOfQueriesSat;
		}
		return sat;
	}
	
	boolean querySat(SyProgram P, List<SyValue> pathCondition) {
		++this.totalNumberOfQueries;
		//System.out.print("*");
		var smtQuery = this.smtPrinter.pathConditionToSmt(P, pathCondition);
		final long startMillis = System.currentTimeMillis();
		sendToSolver(smtQuery);
		var sat = readResult();
		this.totalSolverTimeMillis += System.currentTimeMillis() - startMillis;
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
	
	private void runSolver() {
		final ArrayList<String> commandLine = new ArrayList<>();
		commandLine.add(this.solverPath.toString());
		commandLine.add("-smt2");
		commandLine.add("-in");
		final ProcessBuilder pb = new ProcessBuilder(commandLine).redirectErrorStream(true);
		try {
			this.solver = pb.start();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		this.solverInputFile = new BufferedReader(new InputStreamReader(this.solver.getInputStream()));
		this.solverOutputFile = new BufferedWriter(new OutputStreamWriter(this.solver.getOutputStream()));
	}
	
	private void sendToSolver(String smtQuery) {
		try {
			this.solverOutputFile.write("(reset)\n");
			this.solverOutputFile.write(smtQuery);
			this.solverOutputFile.write("(check-sat)\n");
			this.solverOutputFile.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private boolean readResult() {
		final String answer;
		try {
			answer = this.solverInputFile.readLine().trim();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		if ("unsat".equals(answer)) {
			return false;
		} else if ("sat".equals(answer)) {
			return true;
		} else {
			throw new RuntimeException("Unrecognized answer from smt solver: " + answer);
		}
	}
	
	@Override
	public void quit() {
		try {
			this.solverOutputFile.write("(exit)\n");
			this.solverOutputFile.flush();
			while (this.solverInputFile.readLine() != null) {
				//do nothing
			}
			this.solverInputFile.close();
			this.solverOutputFile.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		try {
			this.solver.waitFor();
		} catch (InterruptedException e) {
			//do nothing
		}
	}
}
