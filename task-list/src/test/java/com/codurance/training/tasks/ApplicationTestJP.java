package com.codurance.training.tasks;


import static java.lang.System.lineSeparator;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;

import org.junit.Before;
import org.junit.Test;

public final class ApplicationTestJP {
	public static final String PROMPT = "> ";
	private PipedOutputStream inStream;
	private PrintWriter inWriter;
	private PipedInputStream outStream;
	private BufferedReader outReader;

	@Before
	public void startApplication() throws IOException {
		inStream = new PipedOutputStream();
		inWriter = new PrintWriter(inStream, true);
		outStream = new PipedInputStream();
		outReader = new BufferedReader(new InputStreamReader(outStream));
		BufferedReader in = new BufferedReader(new InputStreamReader(
				new PipedInputStream(inStream)));
		PrintWriter out = new PrintWriter(new PipedOutputStream(outStream),
				true);
		TaskList taskList = new TaskList(in, out);
		new Thread(taskList).start();
	}

	//Teste la supression
	@Test
	public void itWorks() throws IOException {
		execute("show");

		execute("add project secrets");
		execute("add task secrets Eat more donuts.");
		execute("add task secrets Destroy all humans.");

		execute("show");
		readLines("secrets", "    [ ] 1: Eat more donuts.",
				"    [ ] 2: Destroy all humans.", "");
		
		
		
		
		
		execute("quit");
	}

	private void execute(String command) throws IOException {
		read(PROMPT);
		write(command);
	}

	private void read(String expectedOutput) throws IOException {
		int length = expectedOutput.length();
		char[] buffer = new char[length];
		outReader.read(buffer, 0, length);
		assertThat(String.valueOf(buffer), is(expectedOutput));
	}

	private void readLines(String... expectedOutput) throws IOException {
		for (String line : expectedOutput) {
			read(line + lineSeparator());
		}
	}

	private void write(String input) {
		inWriter.println(input);
	}
}
