package jhide;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MainClass {
	private static String version = "1.0.4";					//Global version identifier

	
	
	
	public static void main(String[] args) throws IOException {
		File image = null;
		File archive = null;
		File output = null;
		String imageextension = null;
		boolean imagesupplied = false;
		boolean archivesupplied = false;
		boolean outputallowed = false;
		boolean benchmark = false;
		List<String> arglist = Arrays.asList(args);
		String archiveext = "[7z, bz, bz2, lzh, jar, rar, gz, tar, zip]";
		String imageext = "[tif, tiff, gif, jpeg, jpg, jif, jfif, jp2, jpx, j2k, j2c, fpx, pcd, png, pdf]";
		
		
		System.out.println("     _ _   _ _     _      \n    | | | | (_) __| | ___ \n _  | | |_| | |/ _` |/ _ \\\n| |_| |  _  | | (_| |  __/\n \\___/|_| |_|_|\\__,_|\\___|");
		System.out.println("By Mongo_gamer\n");
		
		if(args.length == 0 || containsCaseInsensitive("-h", Arrays.asList(args)) || containsCaseInsensitive("--help", Arrays.asList(args))){
			printhelp();
		}
		
		if(containsCaseInsensitive("-v", Arrays.asList(args)) || containsCaseInsensitive("--version", Arrays.asList(args))){
			System.out.println(version);
			System.exit(0);
		}
		if(containsCaseInsensitive("--supportedformats", Arrays.asList(args))){
			System.out.println("Supported archives: " + archiveext + "\n" + "Supported images: " + imageext);
			System.exit(0);
		}
		
		String switchstring;
		for(int i = 0; i < arglist.size(); i++){
			switchstring = arglist.get(i);
			switch(switchstring.toLowerCase()){
			
			case "-ii":
				image = new File(args[i+1]);
				imageextension = image.getAbsolutePath().replaceAll("^.*\\.(.*)$", "$1");
				if(image.getAbsoluteFile().exists() == false || image.getAbsoluteFile().isDirectory() == true || (imageext.indexOf(image.getAbsolutePath().replaceAll("^.*\\.(.*)$", "$1")) < 0)){
					 System.out.println("Invalid or unsupported input image file : " + image.getAbsolutePath());
					 System.exit(1);
				 }
				 imagesupplied = true;
				 i++;
				 break;
			
			case "--inputimage":
				image = new File(args[i+1]);
				imageextension = image.getAbsolutePath().replaceAll("^.*\\.(.*)$", "$1");
				if(image.getAbsoluteFile().exists() == false || image.getAbsoluteFile().isDirectory() == true || (imageext.indexOf(image.getAbsolutePath().replaceAll("^.*\\.(.*)$", "$1")) < 0)){
					 System.out.println("Invalid or unsupported input image file : " + image.getAbsolutePath());
					 System.exit(1);
				 }
				 imagesupplied = true;
				 i++;
				 break;
				 
			case "-ia":
				archive = new File(args[i+1]);
				if(archive.getAbsoluteFile().exists() == false || archive.getAbsoluteFile().isDirectory() == true || (archiveext.indexOf(archive.getAbsolutePath().replaceAll("^.*\\.(.*)$", "$1")) < 0)){
					 System.out.println("Invalid or unsupported input archive file : " + archive.getAbsolutePath());
					 System.exit(1);
				 }
				archivesupplied = true;
				i++;
				break;
				
			case "--inputarchive":
				archive = new File(args[i+1]);
				if(archive.getAbsoluteFile().exists() == false || archive.getAbsoluteFile().isDirectory() == true || (archiveext.indexOf(archive.getAbsolutePath().replaceAll("^.*\\.(.*)$", "$1")) < 0)){
					 System.out.println("Invalid or unsupported input archive file : " + archive.getAbsolutePath());
					 System.exit(1);
				 }
				archivesupplied = true;
				i++;
				break;
				
			case "-o":
				output = new File(args[i+1] + "." + imageextension);
				if(output.getAbsoluteFile().isDirectory() == true || (imageext.indexOf((output.getAbsolutePath()).replaceAll("^.*\\.(.*)$", "$1")) < 0)){
					 System.out.println("Invalid or unsupported output file : " + output.getAbsolutePath());
					 System.exit(1);
				 }
				if(output.getAbsoluteFile().exists()){
					Scanner reader = new Scanner(System.in);	//Reading from System.in
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
				i++;
				break;
				
			case "--output":
				output = new File(args[i+1] + "." + imageextension);
				if(output.getAbsoluteFile().isDirectory() == true || (imageext.indexOf((output.getAbsolutePath()).replaceAll("^.*\\.(.*)$", "$1")) < 0)){
					 System.out.println("Invalid or unsupported output file : " + output.getAbsolutePath());
					 System.exit(1);
				 }
				if(output.getAbsoluteFile().exists()){
					Scanner reader = new Scanner(System.in);	//Reading from System.in
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
				i++;
				break;
				
			case "--benchmark":
				benchmark = true;
				break;
				
			default:
				System.out.print("Invalid parameter: " + arglist.get(i));
				System.exit(1);
				
			}
		}
		
		if(outputallowed == false){
			output = new File("combined." + imageextension);
			outputallowed = true;
		}
		
		if(imagesupplied && archivesupplied && outputallowed){
			try {
				long time = 0;
				if(benchmark == true)
					time = System.currentTimeMillis();
				DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(output)));
				out.write(Files.readAllBytes(image.toPath()));
				out.write(Files.readAllBytes(archive.toPath()));
				out.close();
				if(benchmark == true)
					System.out.println("Action took " + (System.currentTimeMillis() - time) + "ms");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
		public static void printhelp(){
			System.out.println(	"-h				Displays this help section\n"
					+	"--help				\n"
					+	"-v				Displays the installed version of JHide\n"
					+	"--version			\n"
					+	"-ii				Sets the path to the input image\n"
					+ 	"--inputimage		\n"
					+ 	"-ia				Sets the path to the input archive\n"
					+ 	"--inputarchive		\n"
					+ 	"-o				Sets the path for the output file. (optional)\n"
					+ 	"--output			\n"
					+	"--supportedformats		Prints out a list of all supported file formats\n"
					+	"--benchmark			Displays the speed of the action in milliseconds\n\n"		
					+	"Usage examples:\n"
					+	"JHide -ii image.png -ia zip.zip				Outputs the hidden archive as combined.png\n"
					+	"JHide -ii \"path/to/image.*\" --inputarchive \"path/to/archive.*\" -o \"path/to/output.*\"");
			System.exit(0);
		}
		
		public static boolean containsCaseInsensitive(String s, List<String> l){
		     for (String string : l){
		        if (string.equalsIgnoreCase(s)){
		            return true;
		         }
		     }
		    return false;
		  }
	
	}