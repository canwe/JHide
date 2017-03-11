package jhide;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class MainClass {
	private static String version = "1.0.0";					//Global version identifier
	
	
	
	public static void main(String[] args) throws IOException {
		File image = null;
		File archive = null;
		File output = null;
		boolean imagesupplied = false;
		boolean archivesupplied = false;
		boolean outputallowed = false;
		String archiveext = "[7z, bz, bz2, lzh, jar, rar, gz, tar, zip]";
		String imageext = "[tif, tiff, gif, jpeg, jpg, jif, jfif, jp2, jpx, j2k, j2c, fpx, pcd, png, pdf]";
		
		
		System.out.println("     _ _   _ _     _      \n    | | | | (_) __| | ___ \n _  | | |_| | |/ _` |/ _ \\\n| |_| |  _  | | (_| |  __/\n \\___/|_| |_|_|\\__,_|\\___|");
		System.out.println("By Mongo_gamer\n");
		
		if(args.length == 0 || Arrays.asList(args).contains("-h") || Arrays.asList(args).contains("--help")){
			printhelp();
		}
		
		if(Arrays.asList(args).contains("-v") || Arrays.asList(args).contains("--version")){
			System.out.println(version);
			System.exit(0);
		}
		if(Arrays.asList(args).contains("--supportedformats")){
			System.out.println("Supported archives: " + archiveext + "\n" + "Supported images: " + imageext);
			System.exit(0);
		}
		
		String switchstring;
		for(int i = 0; i < args.length - 1; i++){
			switchstring = args[i].toString();
			switch(switchstring){
			
			case "-ii":
				image = new File(args[i+1]);
				if(image.getAbsoluteFile().exists() == false || image.getAbsoluteFile().isDirectory() == true || (imageext.indexOf(image.getAbsolutePath().replaceAll("^.*\\.(.*)$", "$1")) < 0)){
					 System.out.println("Invalid or unsupported input file : " + image.getAbsolutePath());
					 System.exit(1);
				 }
				 imagesupplied = true;
				 break;
			
			case "--inputimage":
				image = new File(args[i+1]);
				if(image.getAbsoluteFile().exists() == false || image.getAbsoluteFile().isDirectory() == true || (imageext.indexOf(image.getAbsolutePath().replaceAll("^.*\\.(.*)$", "$1")) < 0)){
					 System.out.println("Invalid or unsupported input file : " + image.getAbsolutePath());
					 System.exit(1);
				 }
				 imagesupplied = true;
				 break;
				 
			case "-ia":
				archive = new File(args[i+1]);
				if(archive.getAbsoluteFile().exists() == false || archive.getAbsoluteFile().isDirectory() == true || (archiveext.indexOf(archive.getAbsolutePath().replaceAll("^.*\\.(.*)$", "$1")) < 0)){
					 System.out.println("Invalid or unsupported input file : " + archive.getAbsolutePath());
					 System.exit(1);
				 }
				archivesupplied = true;
				break;
				
			case "--inputarchive":
				archive = new File(args[i+1]);
				if(archive.getAbsoluteFile().exists() == false || archive.getAbsoluteFile().isDirectory() == true || (archiveext.indexOf(archive.getAbsolutePath().replaceAll("^.*\\.(.*)$", "$1")) < 0)){
					 System.out.println("Invalid or unsupported input archive file : " + archive.getAbsolutePath());
					 System.exit(1);
				 }
				archivesupplied = true;
				break;
				
			case "-o":
				output = new File(args[i+1]);
				if(output.getAbsoluteFile().isDirectory() == true || (imageext.indexOf(output.getAbsolutePath().replaceAll("^.*\\.(.*)$", "$1")) < 0)){
					 System.out.println("Invalid or unsupported input file : " + output.getAbsolutePath());
					 System.exit(1);
				 }
				if(output.getAbsoluteFile().exists()){
					Scanner reader = new Scanner(System.in);  // Reading from System.in
					System.out.println("A file with the same name already exists. Do you wish to overwrite it? (Y/N)");
					String input = reader.nextLine();
					if(input.equalsIgnoreCase("y")){
						outputallowed = true;
					}else if(input.equalsIgnoreCase("N")){
						System.exit(0);
					}
					reader.close();
				}else{
					outputallowed = true;
				}
				break;
				
			case "--output":
				output = new File(args[i+1]);
				if(output.getAbsoluteFile().isDirectory() == true || (imageext.indexOf(output.getAbsolutePath().replaceAll("^.*\\.(.*)$", "$1")) < 0)){
					 System.out.println("Invalid or unsupported input file : " + output.getAbsolutePath());
					 System.exit(1);
				 }
				if(output.getAbsoluteFile().exists()){
					Scanner reader = new Scanner(System.in);  // Reading from System.in
					System.out.println("A file with the same name already exists. Do you wish to overwrite it? (Y/N)");
					String input = reader.nextLine();
					if(input.equalsIgnoreCase("y")){
						outputallowed = true;
					}else if(input.equalsIgnoreCase("N")){
						System.exit(0);
					}
					reader.close();
				}else{
					outputallowed = true;
				}
				break;
			}
		}
		
		if(imagesupplied && archivesupplied && outputallowed){
			try {
				@SuppressWarnings("unused")
				String line;
			       Process p = Runtime.getRuntime().exec("cmd /C copy /B " + "\"" + image.getAbsolutePath() + "\"" + "+" + "\"" + archive.getAbsolutePath() + "\"" + " " + "\"" + output.getAbsolutePath() + "\""); //ugly but wouldnt work with normal exec

			       BufferedReader in = new BufferedReader(	
			               new InputStreamReader(p.getInputStream()) );
			       while ((line = in.readLine()) != null) {
			       }
			       in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
		public static void printhelp(){
			System.out.println(	"-h:				Displays this help section\n"
					+	"--help				\n"
					+	"-v				Displays the installed version of JHide"
					+	"--version			\n"
					+	"-ii				Sets the path to the input image\n"
					+ 	"--inputimage		\n"
					+ 	"-ia				Sets the path to the input archive\n"
					+ 	"--inputarchive		\n"
					+ 	"-o				Sets the path for the output file. If no output is specified\n				the file will be created in the current directory.\n"
					+ 	"--output			\n"
					+	"--supportedformats		Prints out a list of all supported file formats\n\n"
					+	"Usage examples:\n"
					+	"JHide image.png zip.zip				Outputs the hidden archive as combined.png\n"
					+	"JHide -ii \"path/to/image.*\" --inputarchive \"path/to/archive.*\" -o \"path/to/output.*\"");
			System.exit(0);
		}
	
	}