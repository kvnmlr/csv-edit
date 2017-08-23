package csvedit;

import org.apache.commons.cli.*;

public class Main {
	public static void main(String[] args) {
		Options options = new Options();

		Option input = new Option("i", "input", true, "input file path");
		input.setRequired(true);
		options.addOption(input);

		Option output = new Option("o", "output", true, "output file");
		output.setRequired(true);
		options.addOption(output);

		Option delimiter= new Option("d", "delimiter", true, "csv delimiter");
		delimiter.setRequired(false);
		options.addOption(delimiter);

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd;

		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			formatter.printHelp("edit csv files", options);

			System.exit(1);
			return;
		}

		String inputFilePath = cmd.getOptionValue("input");
		String outputFilePath = cmd.getOptionValue("output");
		String delimiterToken = cmd.getOptionValue("delimiter");

		System.out.println("CSV Edit\n");

		CSVEditor editor = new CSVEditor(inputFilePath, outputFilePath, delimiterToken);
		editor.start();
	}
}
