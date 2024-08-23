package com.levviata;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class CustomLauncher {

	public static void downloadFile(String fileUrl, String outputPath) {
		try {
			// Create a URL object from the file URL
			URL url = new URL(fileUrl);

			// Open a connection to the URL
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestMethod("GET");

			// Check the response code
			if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				// Open input stream from the connection
				try (InputStream inputStream = httpConn.getInputStream()) {
					// Define the output path
					Path outputFilePath = Paths.get(outputPath);

					// Use Files.copy to save the input stream to the output file
					Files.copy(inputStream, outputFilePath, StandardCopyOption.REPLACE_EXISTING);
					System.out.println("File downloaded to " + outputPath);
				}
			} else {
				System.out.println("No file to download. Server replied HTTP code: " + httpConn.getResponseCode());
			}
			// Disconnect the connection
			httpConn.disconnect();

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error occurred while downloading the file.");
		}
	}

	public static void main(String[] args) {
		String defaultFileUrl = "https://media.forgecdn.net/files/3807/586/Incorporeal-3-fabric-1.18.2-2-SNAPSHOT-894c974c.jar"; // Default file URL
		String outputPath = ""; // Initialize outputPath
		boolean shouldDownload = true; // Default action should be to download the file

		// Processing command-line arguments
		if (args.length > 0) {
			for (String arg : args) {
				if (arg.contains("path=")) {
					outputPath = arg.replace("path=", "").trim();
					System.out.println("path is: " + outputPath);
				} else {
					String[] parts = arg.split("\\+");
					if (parts.length < 2) {
						System.out.println("Invalid argument format: " + arg + ". Expected format: ID+filename");
						continue; // Skip to the next argument
					}

					String id = parts[0].trim(); // ID part
					String filename = parts[1].trim(); // Filename part

					// Avoid IndexOutOfBoundsException
					if (id.length() < 4) {
						System.out.println("ID too short, must have at least 4 characters: " + id);
						continue; // Skip to the next argument
					}

					// Extract first digit and last three digits from the ID
					String firstDigit = id.substring(0, 1); // First digit
					String lastThreeDigits = id.substring(id.length() - 3); // Last three digits

					// Construct the URL
					String url = String.format("https://media.forgecdn.net/files/%s/%s/%s",
							firstDigit + id.substring(1, id.length() - 3),
							lastThreeDigits,
							filename);

					// Output the result
					System.out.println("Constructed URL: " + url);
					if (shouldDownload) {
						// Make sure outputPath is valid
						if (outputPath.isEmpty()) {
							System.out.println("Output path is empty. Downloading to current directory by default.");
							outputPath = "./"; // Set to current directory if not provided
						}

						// Ensure the output path ends with a file separator if it's a directory
						if (!outputPath.endsWith("/") && !outputPath.endsWith("\\")) {
							outputPath += "/";
						}

						// Construct the full output file path
						String fullOutputPath = outputPath + filename;

						// Download the file
						downloadFile(url, fullOutputPath);
					}
				}
			}
		} else {
			System.out.println("No arguments provided.");
		}
	}
}
