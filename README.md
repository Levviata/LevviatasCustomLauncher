# Levviata's Custom Launcher

This is a custom launcher that I made to aid me in downloading external mods without user input, you can run it from the command line and provide arguments in the following format:

- To specify the output path where the downloaded file should be saved, use: path=your_output_path
- To provide the ID and filename for the file to download, use the format: ID+filename

Here's an example of how you can use the program from the command line:

> java CustomLauncher path=/your/output/path 1234+exampleFile.jar

In this example:

- path=/your/output/path specifies the output path where the downloaded file will be saved.
- 1234+exampleFile.jar provides the ID (1234) and the filename (exampleFile.jar) for the file to download.
- You can run the program with different combinations of output paths and ID+filename pairs to download files accordingly.

If no path was specified the program will just save the file on the same path it was run in.
