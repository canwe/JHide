# JHide
A command line tool written in Java to hide archive files in images.

## Getting Started
Head over to [The download section](https://github.com/Mongogamer/JHide/releases) and download the latest release.

### Prerequisites
This application requires the Java Runtime Environment version 1.8.0_121 or higher. Java can be downloaded [here](https://java.com/en/download/).

### Running JHide
`JHide.exe [arguments]`

`java -jar JHide.jar [arguments]`

Example: `Jhide.exe -ii image.png -ia archive.zip -o output`

### Options
| Option | Description|
|:---------:|:-----------------------------------------------:|
| -h     	| Prints the list of flags and exits.       	|
| --help    |                                               |
| -v     	| Prints the current version and exits.      	|
| --version |                                               |
| -ii       | Sets the path to the image file to be used.   |
| --inputimage|                                             |
| -ia       | Sets the path to the archive file to be used. |
| --inputarchive|                                           |
| -o       	| Sets the name and path of the output file.    |
| --output  |                                               |
| --supportedformats | Prints the list of supported file formats.|
| --benchmark| Runs a series of tests with varying filesizes and outputs the speed of the action.|

## Built With
* [Java](https://www.java.com/) - The language used
* [Eclipse](https://www.eclipse.org/) - IDE

## License
This project is licensed under the MIT License - see the [License](License) file for details

## Acknowledgments
* Thanks to Stackoverflow for certain samples of code used to improve the project.
* This project was originally created for the sake of using the switch-case statement.