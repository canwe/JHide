package jhide;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class MainClass {
	private static String version = "1.0.6";					//Global version identifier

	
	
	
	public static void main(String[] args) throws IOException {
		File image = null;
		File archive = null;
		File output = null;
		String imageextension = null;
		boolean imagesupplied = false;
		boolean archivesupplied = false;
		boolean outputallowed = false;
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
		if(containsCaseInsensitive("--benchmark", Arrays.asList(args))){
			try{
				benchmark();
			}catch(Exception e){
				e.printStackTrace();
			}
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
				DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(output)));
				out.write(Files.readAllBytes(image.toPath()));
				out.write(Files.readAllBytes(archive.toPath()));
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	private static void benchmark() throws MalformedURLException, IOException, InterruptedException{
		int downloadlength = 0;
		String[] files = new String[] {"512K.jpg", "1M.jpg", "2M.jpg", "4M.jpg", "512k.rar", "1M.rar", "2M.rar", "4M.rar"};
		ArrayList<Integer> missingfile = new ArrayList<Integer>();
		boolean runlargetest = false;
		missingfile.addAll(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0));
		Scanner reader = new Scanner(System.in);
		
		for(int i = 0; i < files.length; i++){
			if(new File(System.getProperty("java.io.tmpdir") + "/" + files[i]).getAbsoluteFile().exists() == false){
				downloadlength = downloadlength + getFileSize(new URL("https://github.com/Mongogamer/JHide/raw/Development/benchmark/" + files[i]));
				missingfile.set(i, 1);
			}else{
				missingfile.set(i, 0);
			}
		}
		
		if(missingfile.contains(1)){
			System.out.println("One or more files used for advanced benchmarking are missing. Do you wish to download them? (This will consume an additional " + humanReadableByteCount(downloadlength) + " of storage) (Y/N)");
			String input = reader.nextLine();
			if(input.equalsIgnoreCase("Y")){
				int progress = 0;
				for(int x = 0; x < files.length; x++){
					if(missingfile.get(x).equals(1)){
						URL website = new URL("https://github.com/Mongogamer/JHide/raw/Development/benchmark/" + files[x]);
						ReadableByteChannel rbc = Channels.newChannel(website.openStream());
						FileOutputStream fos = new FileOutputStream((System.getProperty("java.io.tmpdir") + "/" + files[x]));
						fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
						fos.close();
						progress++;
						progressbar(progress, missingfile);
					}
				}
				System.out.println();
				
			}else{
				System.out.println("Benchmarking cancelled.");
				System.exit(0);
			}
		}
		
		if(new File(System.getProperty("java.io.tmpdir") + "/" + "25M.xyz").getAbsoluteFile().exists() == false){
			URL website = new URL("https://github.com/Mongogamer/JHide/raw/Development/benchmark/25M.xyz");
			System.out.println("Optional benchmarking files available. Do you wish to download them? (This will consume an additional " + humanReadableByteCount(getFileSize(website)) + " of storage) (Y/N)");
			String input = reader.nextLine();
			if(input.equalsIgnoreCase("Y")){
				int size = getFileSize(website);
				int x = 0;
				SizeInputStream sizeInputStream = new SizeInputStream(website.openConnection().getInputStream(), getFileSize(website));
				FileOutputStream fos = new FileOutputStream((System.getProperty("java.io.tmpdir") + "/25M.xyz"));
				byte[] b = new byte[102400];
				while(sizeInputStream.available() != 0){
					fos.write(b, 0, sizeInputStream.read(b));
					x = (int) (100 - (long) ((double) sizeInputStream.available()/size*100));
					System.out.print("[");
					Stream.generate(() -> "=").limit(x / 5).forEach(System.out::print);
					System.out.print(">");
					Stream.generate(() -> " ").limit(20-(x/5)).forEach(System.out::print);
					System.out.print("]   " + x + "%\r");
					fos.flush();
				}
				fos.close();
				sizeInputStream.close();
				runlargetest = true;
				System.out.println();
			}else{
				System.out.println("Skipping 25M");
			}
		}else{
			runlargetest = true;
		}
		
		reader.close();
		File image;
		File archive;
		File output = new File(System.getProperty("java.io.tmpdir") + "/output");
		long time;
		long time2;
		for(int i = 0; i < 4; i++){
			image = new File(System.getProperty("java.io.tmpdir") + "/" + files[i]);
			archive = new File(System.getProperty("java.io.tmpdir") + "/" + files[i+4]);
			
			time = System.currentTimeMillis();
			DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(output)));
			out.write(Files.readAllBytes(image.toPath()));
			out.write(Files.readAllBytes(archive.toPath()));
			out.close();
			time2 = System.currentTimeMillis();
			output.delete();
			System.out.print("Merging " + humanReadableByteCount(image.length() + archive.length()) + " took " + (time2-time) + "ms\n");
			Thread.sleep(1000);
		}
		
		if(runlargetest){
			image = new File(System.getProperty("java.io.tmpdir") + "/25M.xyz");
			archive = new File(System.getProperty("java.io.tmpdir") + "/25M.xyz");
			
			time = System.currentTimeMillis();
			DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(output)));
			out.write(Files.readAllBytes(image.toPath()));
			out.write(Files.readAllBytes(archive.toPath()));
			out.close();
			time2 = System.currentTimeMillis();
			output.delete();
			System.out.print("Merging " + humanReadableByteCount(image.length() + archive.length()) + " took " + (time2-time) + "ms\n");
		}
		
		System.exit(0);
	}
	
	private static void printhelp(){
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
	
	private static int getFileSize(URL url) {
	    HttpURLConnection conn = null;
	    try {
	        conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("HEAD");
	        conn.getInputStream();
	        return conn.getContentLength();
	    } catch (IOException e) {
	        return -1;
	    } finally {
	        conn.disconnect();
	    }
	}
	
	public static String humanReadableByteCount(long bytes) {
	    int unit = 1024;
	    if (bytes < unit) return bytes + " B";
	    int exp = (int) (Math.log(bytes) / Math.log(unit));
	    String pre = ("KMGTPE").charAt(exp-1) + ("i");
	    return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}
	
	private static void progressbar(int x, ArrayList<Integer> arr){
		double d = (double) x/Collections.frequency(arr, 1) * 100;
		System.out.print("[");
		Stream.generate(() -> "=").limit(x * 4).forEach(System.out::print);
		System.out.print(">");
		Stream.generate(() -> " ").limit((Collections.frequency(arr, 1) - x) * 4).forEach(System.out::print);
		System.out.print("]   " + d + "%\r");
	}
		
	private static boolean containsCaseInsensitive(String s, List<String> l){
	     for (String string : l){
	        if (string.equalsIgnoreCase(s)){
	            return true;
	         }
	     }
	    return false;
	  }
	
	}